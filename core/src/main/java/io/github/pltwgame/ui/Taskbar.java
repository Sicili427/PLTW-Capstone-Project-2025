
package io.github.pltwgame.ui;

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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.gameCore.Line;
import space.earlygrey.shapedrawer.ShapeDrawer;
import org.mariuszgromada.math.mxparser.Function;

public class Taskbar {
    public Stage stage;

    private HorizontalGroup barGroup;

    private Image taskbarBg;

    private Table buttonTable;
    private String[] labelArr = {"sin()", "cos()", "tan()", "ln()", "log()", "abs()"};

    private Table equationTable;

    private TextField equationField;
    private Label errorLabel;

    private Window box;
    private Table cardTable;

    private Table uiTable;

    private float errorDuration = 0;
    private String lastValid = "";
    private String lastIndex = "";

    public Taskbar(Skin skin, ShapeDrawer shapeDrawer, SpriteBatch batch, Viewport viewport, Grid grid) {
        stage = new Stage(viewport);

        TextureAtlas uiAtlas = new TextureAtlas("uiSkin/uiSkin.atlas");

        taskbarBg = new Image(uiAtlas.findRegion("taskbar_bg"));
        taskbarBg.setOrigin(0,0);
        taskbarBg.setPosition(0,0);
        taskbarBg.setScale(2);

        barGroup = Bars.createHorzWoodBar(uiAtlas, 80, 2, 0, stage.getHeight() * 0.3f, true);

        createButtonTable(skin);
        createEquationFieldTable(skin, grid);

        uiTable = new Table();
        uiTable.add(equationTable).top().padTop(10).padRight(20);
        uiTable.add(buttonTable);
        uiTable.setPosition(800,108);

        createCarouselTable(skin);

        box = new Window("", skin);
        box.setPosition(50,108 - box.getHeight() * 0.5f);
        box.setWidth(315);
        box.add(cardTable).center();

        stage.addActor(taskbarBg);
        stage.addActor(box);
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

    public void createButtonTable(Skin skin){
        buttonTable = new Table();

        for(int i = 0; i < labelArr.length; i++){
            TextButton button = new TextButton(labelArr[i], skin);
            button.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y){
                    String text = equationField.getText() + button.getText();
                    equationField.setText(text);
                    equationField.setCursorPosition(text.length()-1);
                }
            });
            button.setName("funcButton" + i);

            if((int) (labelArr.length * 0.5) == i){
                buttonTable.row();
            }
            buttonTable.add(button).width(96).height(48).pad(10);
        }
    }

    public void createEquationFieldTable(Skin skin, Grid grid){
        equationField = new TextField("", skin);
        equationField.addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode){
                if(keycode == Input.Keys.ENTER){
                    String text = equationField.getText().trim();
                    lastValid = "";

                    if(text.isBlank()){
                        errorLabel.setText("Please enter an expression.");
                        errorLabel.setVisible(true);
                        errorDuration = 5;
                    } else {
                        Function function = new Function("f", text, "x");
                        equationField.setText("");

                        if(function.checkSyntax()){
                            Line line = grid.getLine(lastIndex);
                            line.color.a = 1;

                            lastIndex = "";
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
    }

    public void createCarouselTable(Skin skin){
        float width = 78;
        float height = 104;

        cardTable = new Table();
        cardTable.defaults();

        for(int i = 0; i < 6; i++){
            Window card = new Window("", skin, "card");

            float pad = -width * 0.5f;
            if(i == 5){
                pad = 0;
                width = width * 1.05f;
                height = height * 1.05f;
            } else {
                card.setColor(0.75f, 0.75f, 0.75f, 1);
            }

            card.setZIndex(i);
            card.setName("card" + i);
            cardTable.add(card).size(width,height).padRight(pad);
        }
    }
}
