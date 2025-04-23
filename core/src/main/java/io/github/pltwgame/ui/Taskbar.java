
package io.github.pltwgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pltwgame.gameCore.GameWorld;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.gameCore.Line;
import space.earlygrey.shapedrawer.ShapeDrawer;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;

public class Taskbar {
    private TextureAtlas atlas;
    public Stage stage;
    private GameWorld gameWorld;

    private HorizontalGroup barGroup;

    private Image taskbarBg;

    private Table buttonTable;
    private String[] labelArr = {"sin()", "cos()", "tan()", "ln()", "log()", "abs()"};

    private Table equationTable;

    private TextField equationField;
    private Label errorLabel;

    private Window box;
    private Table deckTable;

    private Table uiTable;

    private float errorDuration = 0;
    private String lastValid = "";
    private String lastIndex = "";

    public Taskbar(Skin skin, ShapeDrawer shapeDrawer, SpriteBatch batch, Viewport viewport, Grid grid, GameWorld gameWorld) {
        atlas = new TextureAtlas("uiSkin/uiSkin.atlas");
        stage = new Stage(viewport);
        this.gameWorld = gameWorld;

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

        box = new Window("", skin);
        box.setPosition(50,108 - box.getHeight() * 0.5f);
        box.setWidth(315);

        createDeckTable(gameWorld.getDeck());

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
        updateErrorLabel(delta);
        updateDeckTable();

        stage.act(delta);
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
                        // on valid input
                        if(function.checkSyntax()){
                            Line line = grid.getLine(lastIndex);
                            line.color.a = 1;

                            lastIndex = "";
                            errorLabel.setVisible(false);
                            errorDuration = 0;

                            gameWorld.getDeck().remove(gameWorld.getDeck().size()-1);
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
                    line.color.a = 0.5f;
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

    public void createDeckTable(ArrayList<String> deck){
        int cardCount = deck.size();
        float width = 63;
        float height = width * 1.333f;

        deckTable = new Table();

        for(int i = 0; i < cardCount; i++){
            Card card = new Card(atlas);

            float pad = -width * 0.5f;
            if(i == cardCount-1){
                pad = 2.5f;
                width = width * 1.1f;
                height = height * 1.1f;
            } else {
                card.setColor(0.75f, 0.75f, 0.75f, 1);
            }

            card.setZIndex(i);
            card.setName("card" + i);
            deckTable.add(card).size(width,height).padRight(pad);
        }
        box.add(deckTable).right().expandX();
    }

    public void updateDeckTable(){
        SnapshotArray<Actor> cardActors = deckTable.getChildren();
        if(cardActors.size > gameWorld.getDeck().size()){
            cardActors.pop().remove();

            deckTable.clearChildren();
            deckTable.add();
            for(Actor actor: cardActors){
                deckTable.add(actor).padRight(31.5f);
            }
        } else if(cardActors.isEmpty()){
            box.removeActor(deckTable);
            createDeckTable(gameWorld.getDeck());
        }
    }

    public void updateErrorLabel(float delta){
        if (errorLabel.isVisible() && errorDuration > 0){
            errorDuration -= delta;
        } else {
            errorLabel.setVisible(false);
        }
    }
}
