package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.utils.JsonValue;

public class InkComponent extends Component {
    public int inkCost;

    public static InkComponent fromJson(JsonValue json) {
        InkComponent ic = new InkComponent();
        ic.inkCost = json.getInt("inkCost", 0);
        return ic;
    }
}
