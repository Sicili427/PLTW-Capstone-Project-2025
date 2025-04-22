package io.github.pltwgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pltwgame.Grid;
import io.github.pltwgame.Line;
import org.mariuszgromada.math.mxparser.Function;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Taskbar {
    private ShapeDrawer shapeDrawer;
    private SpriteBatch batch;

    public Stage stage;

    TextureAtlas uiAtlas;
    Skin skin;

    private HorizontalGroup barGroup;

    private Image taskbarBg;

    private Table buttonTable;
    private String[] labelArr = {"sin", "cos", "tan", "ln", "log", "|a|"};

    String text = "sin(x)";
    String lastValid = "";
    String lastIndex = "";
    private Grid grid;
    float duration = 0;
    Line line;
    int num = 0;

    public Taskbar(Skin skin, ShapeDrawer shapeDrawer, SpriteBatch batch, Viewport viewport, Grid grid) {
        this.skin = skin;
        this.shapeDrawer = shapeDrawer;
        this.batch = batch;
        this.grid = grid;
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
            TextButton button = new TextButton(labelArr[i], skin);
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
        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            text = "sin(x) + " + num;
            Function function = new Function("f", text, "x");
            if (function.checkSyntax()) {
                if (!lastValid.equals(text)) {
                    grid.removeLine(lastIndex);

                    line = grid.addLine(function);
                    line.color.a = .3f;
                    lastIndex = line.id;
                    lastValid = text;
                    duration = 5;
                    num++;
                }
            }
        }

        if(duration > 0){
            duration -= delta;
        } else if (line != null){
            line.color.a = 1;
        }

        stage.act(delta);
    }

    public void dispose() {
        stage.dispose();
    }
}
