package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.JsonValue;

public class HealthComponent extends Component {
    public int maxHealth;
    public int health;
    public int shield;
    public int armor;
    public boolean invincible = false;

    public static HealthComponent fromJson(JsonValue json) {
        HealthComponent hc = new HealthComponent();
        hc.maxHealth = json.getInt("maxHealth", 100);
        hc.health = json.getInt("health", hc.maxHealth);
        hc.shield = json.getInt("shield", 0);
        hc.armor = json.getInt("armor", 5);
        hc.invincible = json.getBoolean("invincible", false);
        return hc;
    }
}
