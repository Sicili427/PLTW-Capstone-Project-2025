package io.github.pltwgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Taskbar {
    private ShapeDrawer shapeDrawer;
    private SpriteBatch batch;

    private Stage stage;

    TextureAtlas uiAtlas;
    Skin skin;

    private HorizontalGroup barGroup;

    private Image taskbarBg;

    private Table buttonTable;
    private String[] labelArr = {"sin", "cos", "tan", "ln", "log", "|a|"};

    public Taskbar(Skin skin, ShapeDrawer shapeDrawer, SpriteBatch batch, Viewport viewport) {
        this.skin = skin;
        this.shapeDrawer = shapeDrawer;
        this.batch = batch;
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        uiAtlas = new TextureAtlas("uiSkin/uiSkin.atlas");

        taskbarBg = new Image(uiAtlas.findRegion("taskbar_bg"));

        taskbarBg.setOrigin(0,0);
        taskbarBg.setPosition(0,0);
        taskbarBg.setScale(2);

        barGroup = Bars.createHorzWoodBar(uiAtlas, 80, 2, 0, stage.getHeight() * 0.3f, true);

        buttonTable = new Table(skin);
        buttonTable.setPosition(1050, 100);

        for(int i = 0; i < labelArr.length; i++){
            TextButton button = new TextButton("sin", skin);
            button.setName("funcButton" + i);

            if((int) (labelArr.length * 0.5) == i){
                buttonTable.row();
            }
            buttonTable.add(button).width(96).height(48).pad(10);
        }

        stage.addActor(taskbarBg);
        stage.addActor(barGroup);
        stage.addActor(buttonTable);
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void draw(){
        stage.draw();
    }

    public void update(float delta){
        stage.act(delta);
    }

    public void dispose() {
        stage.dispose();
    }
}
