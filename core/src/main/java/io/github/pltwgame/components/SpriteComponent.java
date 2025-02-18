package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.Texture;

public class SpriteComponent extends Component{
    public Texture texture;

    public SpriteComponent(String texturePath) {
        this.texture = new Texture(texturePath);
    }
}
