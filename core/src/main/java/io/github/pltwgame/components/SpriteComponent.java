package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.JsonValue;

public class SpriteComponent extends Component{
    public Sprite sprite;
    public int scale;

    public static SpriteComponent fromJson(JsonValue json) {
        SpriteComponent sc = new SpriteComponent();

        FileHandle file = new FileHandle(json.getString("texture", "placeholder.png"));

        if(!file.exists()){
            file = new FileHandle("placeholder.png");
        }

        Texture texture = new Texture(file);
        sc.sprite = new Sprite(texture, texture.getWidth(), texture.getHeight());
        sc.scale = json.getInt("scale", 1);
        return sc;
    }
}
