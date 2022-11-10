package scripts.main_package.api.tracker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.tribot.script.sdk.Log;
import org.tribot.script.sdk.Tribot;
import org.tribot.script.sdk.util.ScriptSettings;
import scripts.main_package.api.tracker.data.JwtContainer;
import scripts.main_package.api.tracker.data.UserCredentials;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DaxTracker {

    private static final long SECOND_IN_MILLIS = 1000;
    private static final long MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS;

    // IMPORTANT: Anything faster will start to drop data and produce inaccurate results
    private static final long FIXED_UPLOAD_PERIOD = 5 * MINUTE_IN_MILLIS;

    private final DaxTrackerClient daxTrackerClient;
    private final Map<String, Long> data;
    private final Lock lock;
    private final Gson gson;

    private JwtContainer jwtContainer;
    private UserCredentials userCredentials;

    private final ExecutorService executorService;
    private boolean stopped;

    public DaxTracker(String scriptId, String secret) {
        this.daxTrackerClient = new DaxTrackerClient(scriptId, secret);
        this.data = new HashMap<>();
        this.lock = new ReentrantLock();
        this.gson = new GsonBuilder().disableHtmlEscaping().create();
        this.executorService = Executors.newSingleThreadExecutor();
        try {
            init();
        } catch (IOException e) {
            Log.error("Unable to obtain DaxTracker account. Please try again later or try deleting: " + getSettingsFilePath());
            e.printStackTrace();
            return;
        }
        run();
    }

    public void trackData(String name, long value) {
        this.lock.lock();
        try {
            data.compute(name, (s, initial) -> initial != null ? initial + value : value);
        } finally {
            this.lock.unlock();
        }
    }

    private void run() {
        Log.info("Started DaxTracker with update frequency of " + FIXED_UPLOAD_PERIOD);
        executorService.submit(() -> {
            try {
                while (!stopped) {
                    task();
                    Thread.sleep(FIXED_UPLOAD_PERIOD);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void stop() {
        stopped = true;
        Log.info("Stopping DaxTracker... Uploading remaining data before exit");
        executorService.shutdown();
        // run one last time
        task();
    }

    private void task() {
        lock.lock();
        try {
            Log.info("Uploading data to servers...");
            if (data.isEmpty()) return;
            if (track(data)) data.clear();
        } finally {
            lock.unlock();
        }
    }

    private boolean track(Map<String, Long> map) {
        try {
            this.daxTrackerClient.trackData(jwtContainer, this.userCredentials.getId(), map);
            Log.info("Successfully uploaded data to DaxTracker servers");
        } catch (Exception e) {
            Log.warn("Unable to track data... trying again next iteration. Error " + e.getMessage());
            return false;
        }
        return true;
    }

    private void init() throws IOException {
        this.userCredentials = getExistingAccount();

        if (this.userCredentials == null) {
            Log.warn("No existing DaxTracker account. Creating one...");
            this.userCredentials = this.daxTrackerClient.createUser(Tribot.getUsername());
            DaxLogger.info(userCredentials.getSecretKey());
            String contents = gson.toJson(this.userCredentials);
            DaxLogger.info("Created User: %s", contents);
            Files.writeString(Path.of(getSettingsFilePath()), contents);
        }

        Log.info("Loaded DaxTracker account from " + getSettingsFilePath());
        this.jwtContainer = daxTrackerClient.login(userCredentials.getId(), userCredentials.getSecretKey());
    }

    private UserCredentials getExistingAccount() {
        try {
            return new Gson().fromJson(getSettingsFileContents(), UserCredentials.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getSettingsFileContents() throws IOException {
        String contents = Files.readString(Path.of(getSettingsFilePath()));
        DaxLogger.info("Loaded contents for user credentials: %s", contents);
        return contents;
    }

    private String getSettingsFilePath() {
        String path = ScriptSettings.getDefault().getDirectory().getAbsolutePath();
        return String.format("%s%s%s", path, File.separator, "daxTrackerConfig.json");
//        return String.format("%s%s%s", Util.getWorkingDirectory(), File.separator, "daxTrackerConfig.json");
    }

}
