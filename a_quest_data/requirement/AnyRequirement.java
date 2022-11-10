package scripts.main_package.a_quest_data.requirement;

import java.util.ArrayList;
import java.util.Arrays;

public class AnyRequirement extends Requirement {

    ArrayList<Requirement> reqList = new ArrayList<>();

    public AnyRequirement(Requirement... requirements) {
        reqList.addAll(Arrays.asList(requirements));
    }

    @Override
    public boolean check() {
        return reqList.stream().anyMatch(Requirement::check);
    }
}
