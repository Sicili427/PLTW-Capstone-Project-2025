
package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonValue;

public class VelocityComponent extends Component{
    public float speed; // grid squares a second? figure out speed
    public Vector2 direction; // direction of the speed that is applied

    public static VelocityComponent fromJson(JsonValue json) {
        VelocityComponent vc = new VelocityComponent();
        vc.speed = json.getFloat("speed", 0);
        vc.direction = new Vector2(0,0);
        return vc;
    }
}
