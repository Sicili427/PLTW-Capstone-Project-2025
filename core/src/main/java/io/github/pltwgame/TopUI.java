package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import space.earlygrey.shapedrawer.ShapeDrawer;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.awt.*;

public class TopUI {
    ShapeDrawer shapeDrawer;
    Stage stage;
    Label healthLabel;
    Label enemyLabel;
    Skin skin;
    TextureAtlas textureAtlas;
    float fullHealth;
    float health;
    float enemyFullHealth;
    float enemyHealth;
    float x;
    float y;

    public TopUI(ShapeDrawer shapeDrawer, Stage stage) {
        this.shapeDrawer = shapeDrawer;
        this.stage = stage;
        textureAtlas = new TextureAtlas(Gdx.files.internal("skin/rainbow-ui.atlas"));
        skin = new Skin(Gdx.files.internal("skin/rainbow-ui.json"), textureAtlas);
        fullHealth = 100;
        health = 100;
        enemyHealth = 100;
        enemyFullHealth = 100;

        healthLabel = new Label("HEALTH " , skin);
        enemyLabel = new Label("ENEMY " , skin);

        stage.addActor(healthLabel);
        stage.addActor(enemyLabel);

    }


    public void draw() {
        float screenWidth = stage.getWidth();
        float screenHeight = stage.getHeight();

        x = screenWidth/15;
        //y = (screenHeight)*((float)19/20);
        x = 50;
        y = 670;
        shapeDrawer.getBatch().begin();
        shapeDrawer.setColor(new Color(0,0,0,0.5f));

        healthLabel.setColor(Color.BLACK);
        healthLabel.setSize(screenWidth/25, screenHeight/18);
        //healthLabel.
        healthLabel.setFontScale(1.25f*(screenWidth/1280), 1.5f);
        healthLabel.setPosition(screenWidth/30, (screenHeight)*((float)670/720));

        shapeDrawer.filledRectangle(x+80, y, 300, 40, Color.LIGHT_GRAY);
        shapeDrawer.filledRectangle(x+80, y, (300)*(health/fullHealth), 40f, Color.CYAN, Color.BLUE);
        //Gdx.app.debug("200", ""+(200)*(health/fullHealth));
        //shapeDrawer.
        shapeDrawer.rectangle(x+80, y, 300, 40, Color.DARK_GRAY, 2);

        shapeDrawer.filledRectangle(x+880, y, 300, 40, Color.BLACK);
        //shapeDrawer.filledRectangle(x+550, y,(300-(300)*(enemyHealth/enemyFullHealth)), y, (300)*(enemyHealth/enemyFullHealth), 40f, Color.DARK_GRAY, Color.RED);
        shapeDrawer.filledRectangle((x+880)+(300-(300f)*(enemyHealth/enemyFullHealth)), y, (300f)*(enemyHealth/enemyFullHealth), 40f, Color.DARK_GRAY, Color.RED);
        //shapeDrawer.
        shapeDrawer.rectangle(x+880, y, 300, 40, Color.DARK_GRAY, 2);

        enemyLabel.setColor(Color.BLACK);
        enemyLabel.setSize(screenWidth/25, screenHeight/18);
        //healthLabel.
        enemyLabel.setFontScale(1.25f*(screenWidth/1280), 1.5f);
        enemyLabel.setPosition(20*screenWidth/30, (screenHeight)*((float)670/720));

        shapeDrawer.getBatch().end();
        //stage.clear();
    }




}


