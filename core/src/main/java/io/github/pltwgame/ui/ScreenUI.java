
package io.github.pltwgame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pltwgame.gameCore.GameWorld;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ScreenUI {
    private GameWorld gameworld;
    private Stage stage;

    private TextureAtlas uiAtlas;

    private Image healthBarBorder;
    private Image healthBarBG;
    private Image healthBar;
    private Image inkBarBG;
    private Image inkBar;

    private VerticalGroup leftGroup;
    private VerticalGroup rightGroup;

    public ScreenUI(GameWorld gameWorld, SpriteBatch batch, Viewport viewport){
        stage = new Stage(viewport, batch);
        this.gameworld = gameWorld;

        uiAtlas = new TextureAtlas("uiSkin/uiSkin.atlas");

        // Load Images
        healthBarBG = new Image(uiAtlas.findRegion("health_bar_bg"));
        healthBar = new Image(uiAtlas.findRegion("health_bar"));
        healthBarBorder = new Image(uiAtlas.findRegion("health_bar_border"));
        inkBarBG = new Image(uiAtlas.findRegion("ink_bar_bg"));
        inkBar = new Image(uiAtlas.findRegion("ink_bar"));

        healthBarBG.setPosition(90,668);
        healthBarBG.scaleBy(2);

        healthBar.setPosition(90,668);
        healthBar.scaleBy((54/18f)-1, 2);

        healthBarBorder.setPosition(75, 650);
        healthBarBorder.scaleBy(2);

        inkBarBG.setPosition(1017,652.5f);
        inkBarBG.scaleBy(2);

        inkBar.setPosition(1062,670.75f);
        inkBar.scaleBy(2);

        stage.addActor(healthBarBorder);
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
        updateHealth(gameworld.currentBaseHealth);
        updateInk(gameworld.currentInk);
    }

    public void dispose(){
        uiAtlas.dispose();
        stage.dispose();
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void updateHealth(int percentHealth){
        int percentWidth = 54*percentHealth/100;
        //Changes the bounds of the sprite it is taking
        uiAtlas.findRegion("health_bar").setRegion(726,123,percentWidth,4);
        //Changes the scaling to be correct
        healthBar.setScale((percentWidth/18f), 3);
    }

    public void updateInk(float percentInk){
        int percentWidth = (int) (44*percentInk/100);
        //Changes the bounds of the sprite it is taking
        uiAtlas.findRegion("ink_bar").setRegion(726,117,percentWidth,4);
        //Changes the scaling to be correct
        inkBar.setScale((3*percentWidth/44f), 3);
    }
}
