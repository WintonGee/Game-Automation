package scripts.main_package.item;

import scripts.main_package.a_quest_data.requirement.Requirement;

import java.util.ArrayList;

// This will be used to store information on items
// that have to be gathered through questing.
// AKA. Untradeable items.
public class QuestItem extends DetailedItem {

    ArrayList<Requirement> requirementArrayList = new ArrayList<>();
    ArrayList<DetailedItem> itemMaterials = new ArrayList<>();

    public QuestItem(int id) {
        super(id);
    }

    public QuestItem(int id, int quantity) {
        super(id, quantity);
    }

    public boolean isComplete() {
        return isHaveMainItems();
    }

    public boolean isHaveMaterials() {
        return itemMaterials.stream().allMatch(material -> material.isHaveMainItems());
    }

    public QuestItem addRequirement(Requirement... requirements) {
        for (Requirement requirement : requirements)
            requirementArrayList.add(requirement);
        return this;
    }

    public QuestItem addMaterial(DetailedItem... detailedItems) {
        for (DetailedItem detailedItem : detailedItems)
            itemMaterials.add(detailedItem);
        return this;
    }

}
