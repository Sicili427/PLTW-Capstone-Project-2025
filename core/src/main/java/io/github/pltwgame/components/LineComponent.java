package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

public class LineComponent extends Component {
    public String lineId;
    public Vector2[] path;
    public int currentIndex;
}
