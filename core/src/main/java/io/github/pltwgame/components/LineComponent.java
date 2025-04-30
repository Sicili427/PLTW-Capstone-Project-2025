package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class LineComponent extends Component {
    public String lineId;
    public ArrayList<Vector2> path;
    public int currentIndex = 0;
}
