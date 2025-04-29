
package io.github.pltwgame.components;

import com.artemis.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.JsonValue;

public class SpriteComponent extends Component{
    public Sprite sprite;
    public int scale;

    public static SpriteComponent fromJson(JsonValue json) {
        SpriteComponent sc = new SpriteComponent();
        TextureAtlas atlas = new TextureAtlas("entities/entities.atlas");

        String spriteName = json.getString("texture", "placeholder");

        TextureRegion texture = new TextureRegion(atlas.findRegion(spriteName));
        sc.sprite = new Sprite(texture);
        sc.scale = json.getInt("scale", 1);
        return sc;
    }
}
