package scripts.main_package.item;

import lombok.Getter;
import org.tribot.script.sdk.Log;
import scripts.main_package.a_quest_data.requirement.Requirement;

import java.util.ArrayList;

@Getter
public class UntradeableItem extends DetailedItem {

    UntradeableItemTask untradeableItemTask; // TODO load steps

    ArrayList<DetailedItem> materialList = new ArrayList<>();

    //TODO handler for this, use the one for main task or implement this into main task?
    private ArrayList<Requirement> requirementList = new ArrayList<>();

    public UntradeableItem(int itemId) {
        this(itemId, 1);
    }

    public UntradeableItem(int itemId, int quantity) {
        super(itemId, quantity);
        this.depletes = false; // Most likely will always be an item that does not deplete
        untradeableItemTask = new UntradeableItemTask(this); // Init
    }

    public UntradeableItem addMaterial(int id) {
        return addMaterial(id, 1);
    }

    public UntradeableItem addMaterial(int id, int amount) {
        this.materialList.add(new DetailedItem(id, amount));
        return this;
    }

    public UntradeableItem addMaterial(DetailedItem detailedItem) {
        materialList.add(detailedItem);
        return this;
    }

    public UntradeableItem addRequirement(Requirement requirement) {
        requirementList.add(requirement);
        return this;
    }

    public int getMaterialCost() {
        return materialList.stream()
                .mapToInt(DetailedItem::getCost)
                .sum();
    }

    // Gets the current requirements and the requirements for materials if any
    public ArrayList<Requirement> getAllRequirements() {
        ArrayList<Requirement> list = new ArrayList<>(requirementList);

        for (DetailedItem material : materialList) {
            if (material instanceof UntradeableItem) {
                list.addAll(((UntradeableItem) material).getAllRequirements());
            }
        }

        return list;
    }


    //TODO a function to return the requirements needed for this item
    // and potentially all the requirements needed for the materials

    // Task used to hold info for getting an untradeable item
    // Can either be through buying from an entity, combing items, or etc...
    public static class UntradeableItemTask {

        @Getter
        UntradeableItem untradeableItem;

        public UntradeableItemTask(UntradeableItem untradeableItem) {
            this.untradeableItem = untradeableItem;
        }

        //TODO make this handle method into a main task
        // by using the basic main task by using the provided data
        public boolean handle() {
            return false;
        }

        private boolean isComplete() {
            return untradeableItem.isHave(true);
        }

    }

    /** Example of advanced Usage
     UntradeableItem untradeableItemFirst = new UntradeableItem(ItemID.AVAS_ACCUMULATOR);
     untradeableItemFirst.addRequirement(new SkillRequirement(Skill.FLETCHING, 20));
     untradeableItemFirst.addMaterial(995, 10000);

     UntradeableItem untradeableItemSecond = new UntradeableItem(ItemID.AVAS_ASSEMBLER);
     untradeableItemSecond.addMaterial(untradeableItemFirst);
     untradeableItemSecond.addMaterial(995, 50);
     untradeableItemSecond.addRequirement(new SkillRequirement(Skill.COOKING, 20));

     UntradeableItem untradeableItemThird = new UntradeableItem(ItemID.AVAS_ATTRACTOR);
     untradeableItemThird.addMaterial(untradeableItemSecond);
     untradeableItemThird.addMaterial(995, 5000);
     untradeableItemThird.addRequirement(new QuestRequirement(Quest.DRAGON_SLAYER_I));

     Log.info("Cost of first: " + untradeableItemFirst.getCost());
     Log.info("Cost of second: " + untradeableItemSecond.getCost());
     Log.info("Cost of third: " + untradeableItemThird.getCost());

     Log.info("Reqs of first: " + untradeableItemFirst.getRequirements());
     Log.info("Reqs of second: " + untradeableItemSecond.getRequirements());
     Log.info("Reqs of third: " + untradeableItemThird.getRequirements());
     */

}
