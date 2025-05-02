
package io.github.pltwgame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pltwgame.gameCore.GameWorld;

public class ScreenUI {
    private GameWorld gameworld;
    private Stage stage;

    private TextureAtlas uiAtlas;

    private Image healthBarBorder;
    private Image healthBarBG;
    private Image healthBar;

    private Image enemyHealthBarBorder;
    private Image enemyHealthBarBG;
    private Image enemyHealthBar;

    private Image inkBarBG;
    private Image inkBar;

    private VerticalGroup leftGroup;
    private VerticalGroup rightGroup;

    public ScreenUI(GameWorld gameWorld, SpriteBatch batch, Viewport viewport){
        stage = new Stage(viewport, batch);
        this.gameworld = gameWorld;

        uiAtlas = new TextureAtlas("uiSkin/uiSkin.atlas");

        // Load Images
        healthBarBorder = new Image(uiAtlas.findRegion("health_bar_border"));
        healthBarBG = new Image(uiAtlas.findRegion("health_bar_bg"));
        healthBar = new Image(uiAtlas.findRegion("health_bar"));
        enemyHealthBarBorder = new Image(uiAtlas.findRegion("health_bar_border"));
        enemyHealthBarBG = new Image(uiAtlas.findRegion("health_bar_bg"));
        enemyHealthBar = new Image(uiAtlas.findRegion("enemy_health_bar"));
        inkBarBG = new Image(uiAtlas.findRegion("ink_bar_bg"));
        inkBar = new Image(uiAtlas.findRegion("ink_bar"));

        healthBarBorder.setPosition(75, 650);
        healthBarBorder.scaleBy(2);

        healthBarBG.setPosition(90,668);
        healthBarBG.scaleBy(2);

        healthBar.setPosition(90,668);
        healthBar.scaleBy((54/18f)-1, 2);

        enemyHealthBarBorder.setPosition(1017,650);
        enemyHealthBarBorder.scaleBy(2);

        enemyHealthBarBG.setPosition(1032,668);
        enemyHealthBarBG.scaleBy(2);

        enemyHealthBar.setPosition(1032,668);
        enemyHealthBar.scaleBy((54/18f)-1, 2);

        inkBarBG.setPosition(1017,652.5f);
        inkBarBG.scaleBy(2);

        inkBar.setPosition(1062,670.75f);
        inkBar.scaleBy(2);

        stage.addActor(healthBarBorder);
        stage.addActor(healthBarBG);
        stage.addActor(healthBar);
        stage.addActor(enemyHealthBarBorder);
        stage.addActor(enemyHealthBarBG);
        stage.addActor(enemyHealthBar);

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
        updateEnemyHealth(gameworld.currentEnemyHealth);
    }

    public void dispose(){
        uiAtlas.dispose();
        stage.dispose();
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void updateHealth(int currHealth){
        int percentWidth = 54*currHealth/100;
        //Changes the bounds of the sprite it is taking
        uiAtlas.findRegion("health_bar").setRegionWidth(percentWidth);
        //Changes the scaling to be correct
        healthBar.setScale((percentWidth/18f), 3);
    }

    public void updateEnemyHealth(int currHealth){
        int percentWidth = 54*currHealth/100;
        //Changes the bounds of the sprite it is taking
        uiAtlas.findRegion("enemy_health_bar").setRegionWidth(percentWidth);
        //Changes the scaling to be correct
        enemyHealthBar.setScale((percentWidth/18f), 3);
    }
}
