
package io.github.pltwgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pltwgame.Grid;
import io.github.pltwgame.Line;
import space.earlygrey.shapedrawer.ShapeDrawer;
import org.mariuszgromada.math.mxparser.Function;

public class Taskbar {

    public Stage stage;

    private HorizontalGroup barGroup;

    private Image taskbarBg;

    private Table buttonTable;
    private String[] labelArr = {"sin", "cos", "tan", "ln", "log", "|a|"};

    private Table equationTable;

    private TextField equationField;
    private Label errorLabel;

    private Table uiTable;

    private float errorDuration = 0;
    private String lastValid = "";
    private String lastIndex = "";

    public Taskbar(Skin skin, ShapeDrawer shapeDrawer, SpriteBatch batch, Viewport viewport, Grid grid) {
        stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        TextureAtlas uiAtlas = new TextureAtlas("uiSkin/uiSkin.atlas");

        taskbarBg = new Image(uiAtlas.findRegion("taskbar_bg"));
        taskbarBg.setOrigin(0,0);
        taskbarBg.setPosition(0,0);
        taskbarBg.setScale(2);

        barGroup = Bars.createHorzWoodBar(uiAtlas, 80, 2, 0, stage.getHeight() * 0.3f, true);

        buttonTable = new Table();

        for(int i = 0; i < labelArr.length; i++){
            TextButton button = new TextButton(labelArr[i], skin);
            button.setName("funcButton" + i);

            if((int) (labelArr.length * 0.5) == i){
                buttonTable.row();
            }
            buttonTable.add(button).width(96).height(48).pad(10);
        }

        equationField = new TextField("", skin);
        equationField.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode){
                if(keycode == Input.Keys.ENTER){
                    String text = equationField.getText().trim();

                    if(text.isEmpty()){
                        errorLabel.setText("Please enter an expression.");
                        errorLabel.setVisible(true);
                        errorDuration = 5;
                    } else {
                        Function function = new Function("f", text, "x");
                        equationField.setText("");

                        if(function.checkSyntax()){
                            grid.addLine(function);
                            errorLabel.setVisible(false);
                            errorDuration = 0;
                        } else {
                            errorLabel.setText("Please enter a valid expression.");
                            errorLabel.setVisible(true);
                            errorDuration = 5;
                        }
                    }
                }
                return true;
            }
        });
        equationField.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String text = equationField.getText();
                Function function = new Function("f", text, "x");
                if(text.isBlank() && !lastIndex.isEmpty()){
                    grid.removeLine(lastIndex);
                }
                if(function.checkSyntax() && !lastValid.equals(text)){
                    lastValid = text;
                    if(lastIndex != null){
                        grid.removeLine(lastIndex);
                    }
                    Line line = grid.addLine(function);
                    line.color.a = 0.3f;
                    lastIndex = line.id;
                }
            }
        });

        errorLabel = new Label("Please enter a valid equation.", skin);
        errorLabel.setColor(Color.RED);
        errorLabel.setVisible(false);

        equationTable = new Table();
        equationTable.add(equationField).width(equationField.getWidth() * 3f).center();
        equationTable.row();
        equationTable.add(errorLabel).center().padTop(10);

        uiTable = new Table();
        uiTable.add(equationTable).top().padTop(10).padRight(25);
        uiTable.add(buttonTable);
        uiTable.setPosition(800,108);

        stage.addActor(taskbarBg);
        stage.addActor(barGroup);
        stage.addActor(uiTable);
    }

    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
    }

    public void draw(){
        stage.draw();
    }

    public void update(float delta){
        stage.act(delta);

        if (errorLabel.isVisible() && errorDuration > 0){
            errorDuration -= delta;
        } else {
            errorLabel.setVisible(false);
        }
    }

    public void dispose() {
        stage.dispose();
    }
}
