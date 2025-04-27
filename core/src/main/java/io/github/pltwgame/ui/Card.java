package io.github.pltwgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonValue;

public class Card extends Group {
    TextureAtlas atlas;
    JsonValue json;

    CardBG cardBg;

    public Card(TextureAtlas atlas, String jsonString) {
        this.atlas = atlas;
    }

    public void createCard(float width, float height, Color color){
        CardBG cardBg = new CardBG(atlas);
        cardBg.setSize(width, height);
        cardBg.setColor(color);

        Image spritePortrait = new Image(atlas.findRegion("wood_border09"));
        spritePortrait.setPosition(0.5f * (width - spritePortrait.getWidth()), height * (85f/108) - 0.5f * (spritePortrait.getHeight()));
        spritePortrait.setColor(color);

        addActor(cardBg);
        addActor(spritePortrait);
    }
}
