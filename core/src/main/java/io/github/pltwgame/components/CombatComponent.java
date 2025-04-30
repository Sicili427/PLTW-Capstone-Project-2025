package io.github.pltwgame.components;


import com.artemis.Component;
import com.badlogic.gdx.utils.JsonValue;

public class CombatComponent extends Component {
    public int entityDamage;
    public int baseDamage;
    public float attackSpeed;
    public int detectionRange;
    public int range;
    public float attackCooldown = 0;
    public String damageType;

    public int target = -1;

    public static CombatComponent fromJson(JsonValue json){
        CombatComponent cc = new CombatComponent();
        cc.entityDamage = json.getInt("entityDamage", 1);
        cc.baseDamage = json.getInt("baseDamage", 5);
        cc.attackSpeed = json.getFloat("attackSpeed", 1);
        cc.detectionRange = json.getInt("detectionRange", 15);
        cc.range = json.getInt("range", 40);
        cc.damageType = json.getString("damageType", "normal");
        return cc;
    }
}
