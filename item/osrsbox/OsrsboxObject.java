package scripts.main_package.item.osrsbox;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OsrsboxObject {

    private float id;
    private String name;
    private String last_updated;
    private boolean incomplete;
    private boolean members;
    private boolean tradeable;
    private boolean tradeable_on_ge;
    private boolean stackable;
    private String stacked = null;
    private boolean noted;
    private boolean noteable;
    private String linked_id_item = null;
    private float linked_id_noted;
    private float linked_id_placeholder;
    private boolean placeholder;
    private boolean equipable;
    private boolean equipable_by_player;
    private boolean equipable_weapon;
    private float cost;
    private float lowalch;
    private float highalch;
    private float weight;
    private float buy_limit;
    private boolean quest_item;
    private String release_date;
    private boolean duplicate;
    private String examine;
    private String icon;
    private String wiki_name;
    private String wiki_url;
    Equipment equipment;
    Weapon weapon;

    @Data
    private class Equipment {
        private float attack_stab;
        private float attack_slash;
        private float attack_crush;
        private float attack_magic;
        private float attack_ranged;
        private float defence_stab;
        private float defence_slash;
        private float defence_crush;
        private float defence_magic;
        private float defence_ranged;
        private float melee_strength;
        private float ranged_strength;
        private float magic_damage;
        private float prayer;
        private String slot;
        Requirements RequirementsObject;

    }

    @Data
    public class Weapon {
        private float attack_speed;
        private String weapon_type;
        ArrayList<Stance> stances = new ArrayList<Stance>();
    }

    @Data
    public class Stance {
        private String combat_style;
        private String attack_type;
        private String attack_style;
        private String experience;
        private String boosts; // This is just a description?

    }

    @Data
    public class Requirements {
        private float hunter;
        private float defence;
        private float ranged;
    }

}
