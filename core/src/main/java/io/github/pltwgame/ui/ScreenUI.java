package io.github.pltwgame.ui;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class ScreenUI {
    public int defaultWidth;
    public int defaultHeight;

    public ShapeDrawer shapeDrawer;
    public Skin skin;
    public Stage stage;

    public ScreenUI(ShapeDrawer shapeDrawer, Skin skin, int defaultWidth, int defaultHeight){
        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
        this.shapeDrawer = shapeDrawer;
        this.skin = skin;

        stage = new Stage(new FitViewport(defaultWidth, defaultHeight));

        ProgressBar healthBar = new ProgressBar(0, 100, 1, false, skin);
        healthBar.setSize(300, 300);
        healthBar.setPosition(100, 100);

        stage.addActor(healthBar);
    }

    public void draw() {
        stage.draw();
    }

    public void update(float delta){
        stage.act(delta);
    }

    public void dispose(){
        stage.dispose();
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }
}
