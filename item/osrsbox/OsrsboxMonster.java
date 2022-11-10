package scripts.main_package.item.osrsbox;

import lombok.Data;

import java.util.ArrayList;

@Data
public class OsrsboxMonster {
    private float id;
    private String name;
    private String last_updated;
    private boolean incomplete;
    private boolean members;
    private String release_date;
    private float combat_level;
    private float size;
    private float hitpoints;
    private float max_hit;
    ArrayList<String> attack_type = new ArrayList<String>();
    private float attack_speed;
    private boolean aggressive;
    private boolean poisonous;
    private boolean venomous;
    private boolean immune_poison;
    private boolean immune_venom;
    ArrayList<String> attributes = new ArrayList<String>();
    ArrayList<String> category = new ArrayList<String>();
    private boolean slayer_monster;
    private float slayer_level;
    private float slayer_xp;
    ArrayList<String> slayer_masters = new ArrayList<String>();
    private boolean duplicate;
    private String examine;
    private String wiki_name;
    private String wiki_url;
    private float attack_level;
    private float strength_level;
    private float defence_level;
    private float magic_level;
    private float ranged_level;
    private float attack_bonus;
    private float strength_bonus;
    private float attack_magic;
    private float magic_bonus;
    private float attack_ranged;
    private float ranged_bonus;
    private float defence_stab;
    private float defence_slash;
    private float defence_crush;
    private float defence_magic;
    private float defence_ranged;
    ArrayList<Drop> drops = new ArrayList<Drop>();

    @Data
    public class Drop {
        int id;
        String name;
        boolean members;
        String quantity;
        boolean noted;
        double rarity;
        int rolls;
    }

}
