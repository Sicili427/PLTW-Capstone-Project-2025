package io.github.pltwgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.JsonValue;
import io.github.pltwgame.loaders.JsonLoader;

public class Card extends Group {
    TextureAtlas atlas;
    TextureAtlas entityAtlas;
    Skin skin;
    JsonValue json;

    String spriteString;
    int health;
    int ink;
    int entityDamage;
    int baseDamage;
    String damageType;

    CardBG cardBg;

    public Card(TextureAtlas atlas, Skin skin, String jsonString) {
        this.atlas = atlas;
        this.skin = skin;

        entityAtlas = new TextureAtlas("entities/entities.atlas");
        json = JsonLoader.getJson("/entities/" + jsonString + ".json").get("Components");
        getCardData();
    }

    public void createCard(float width, float height, Color color){
        CardBG cardBg = new CardBG(atlas);
        cardBg.setSize(width, height);
        cardBg.setColor(color);

        Image spritePortrait = new Image(entityAtlas.findRegion(spriteString));
        spritePortrait.setPosition(0.5f * (width - spritePortrait.getWidth()), height * (85f/108) - 0.5f * (spritePortrait.getHeight()));
        spritePortrait.setColor(color);

        Label inkLabel = new Label(ink + "", skin, "small");
        inkLabel.setPosition(width * 8.75f/81 - 0.5f * inkLabel.getWidth(), height - 5.25f - inkLabel.getHeight());

        Label healthLabel = new Label(health + "", skin, "small");
        healthLabel.setPosition(width - healthLabel.getWidth(), height - healthLabel.getHeight());

        addActor(cardBg);
        addActor(spritePortrait);
        addActor(inkLabel);
        addActor(healthLabel);
    }

    private void getCardData(){
        spriteString = json.get("SpriteComponent").getString("texture", "placeholder.png");
        health = json.get("HealthComponent").getInt("maxHealth", 5);
        ink = json.get("InkComponent").getInt("inkCost", 1);
        entityDamage = json.get("DamageComponent").getInt("entityDamage", 1);
        baseDamage = json.get("DamageComponent").getInt("baseDamage", 5);
        damageType = json.get("DamageComponent").getString("damageType", "normal");
    }
}
