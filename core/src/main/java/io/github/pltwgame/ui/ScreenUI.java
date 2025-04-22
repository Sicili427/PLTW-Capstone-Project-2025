package io.github.pltwgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ScreenUI {
    private Viewport viewport;
    private ShapeDrawer shapeDrawer;

    private Stage stage;

    private TextureAtlas uiAtlas;

    private Image healthBarBG;
    private Image healthBar;
    private Image inkBarBG;
    private Image inkBar;

    private float maxHealth = 100f;
    private float currentHealth = 100f;
    private float maxInk = 100f;
    private float currentInk = 100f;

    private VerticalGroup leftGroup;
    private VerticalGroup rightGroup;

    public ScreenUI(ShapeDrawer shapeDrawer, SpriteBatch batch, Viewport viewport){
        stage = new Stage(viewport, batch);
        this.shapeDrawer = shapeDrawer;

        uiAtlas = new TextureAtlas("uiSkin/uiSkin.atlas");

        // Load Images
        healthBarBG = new Image(uiAtlas.findRegion("health_bar_bg"));
        healthBar = new Image(uiAtlas.findRegion("health_bar"));
        inkBarBG = new Image(uiAtlas.findRegion("ink_bar_bg"));
        inkBar = new Image(uiAtlas.findRegion("ink_bar"));

        healthBarBG.setPosition(75,650);
        healthBarBG.scaleBy(2);

        healthBar.setPosition(75,650);
        healthBar.scaleBy(2);

        inkBarBG.setPosition(1017,652.5f);
        inkBarBG.scaleBy(2);

        inkBar.setPosition(1017,652.5f);
        inkBar.scaleBy(2);

        stage.addActor(healthBarBG);
        stage.addActor(healthBar);
        stage.addActor(inkBarBG);
        stage.addActor(inkBar);

        leftGroup = Bars.createVertWoodBar(uiAtlas, 23, 2, 0, 0, false);
        rightGroup = Bars.createVertWoodBar(uiAtlas, 23, 2, stage.getWidth(), 0, true);

        stage.addActor(leftGroup);
        stage.addActor(rightGroup);
    }

    public void draw() {
        stage.draw();

        /* shapeDrawer.getBatch().begin();
        shapeDrawer.line(0,108, 1280, 108, 4);
        shapeDrawer.line(640, 0, 640, 720, 4);
        shapeDrawer.getBatch().end(); */
    }

    public void update(float delta){
        stage.act(delta);
    }

    public void dispose(){
        uiAtlas.dispose();
        stage.dispose();
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}
