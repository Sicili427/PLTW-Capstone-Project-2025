package io.github.pltwgame.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.pltwgame.gameCore.GameWorld;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.listeners.EquationChangeListener;
import io.github.pltwgame.listeners.KeyListener;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

public class Taskbar {
    private TextureAtlas atlas;
    private Skin skin;
    public Stage stage;
    private GameWorld gameWorld;

    private HorizontalGroup barGroup;

    private Image taskbarBg;

    private Table buttonTable;
    private String[] labelArr = {"sin()", "cos()", "tan()", "ln()", "log()", "abs()"};

    private Table equationTable;

    public TextField equationField;
    public Label errorLabel;

    private Image inkBarBG;
    private Image inkBar;
    private Label inkLabel;

    private Window box;
    public Table deckTable;

    private Table uiTable;

    public float errorDuration = 0;
    public String lastValid = "";
    public String lastIndex = "";

    public Taskbar(Skin skin, ShapeDrawer shapeDrawer, SpriteBatch batch, Viewport viewport, Grid grid, GameWorld gameWorld) {
        atlas = new TextureAtlas("uiSkin/uiSkin.atlas");
        stage = new Stage(viewport);
        this.skin = skin;
        this.gameWorld = gameWorld;

        TextureAtlas uiAtlas = new TextureAtlas("uiSkin/uiSkin.atlas");

        taskbarBg = new Image(uiAtlas.findRegion("taskbar_bg"));
        taskbarBg.setOrigin(0,0);
        taskbarBg.setPosition(0,0);
        taskbarBg.setScale(2);

        barGroup = Bars.createHorzWoodBar(uiAtlas, 80, 2, 0, stage.getHeight() * 0.3f, true);

        createButtonTable();
        createEquationFieldTable(grid);

        uiTable = new Table();
        uiTable.add(equationTable).top().padTop(10).padRight(20);
        uiTable.add(buttonTable);
        uiTable.setPosition(800,108);

        box = new Window("", skin);
        box.setPosition(50,108 - box.getHeight() * 0.5f);
        box.setWidth(315);

        createDeckTable(gameWorld.getDeck());

        createInkBar();

        stage.addActor(taskbarBg);
        stage.addActor(box);
        stage.addActor(barGroup);
        stage.addActor(uiTable);
        stage.addActor(inkBarBG);
        stage.addActor(inkBar);
        stage.addActor(inkLabel);
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
        updateInk(gameWorld.currentInk);

        stage.act(delta);
    }

    public void dispose() {
        stage.dispose();
    }

    public void createButtonTable(){
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

    public void createEquationFieldTable(Grid grid){
        equationField = new TextField("", skin);
        equationField.addListener(new KeyListener(gameWorld, this, grid));
        equationField.addListener(new EquationChangeListener(gameWorld, this, grid));

        errorLabel = new Label("Please enter a valid equation.", skin);
        errorLabel.setColor(Color.RED);
        errorLabel.setVisible(false);

        equationTable = new Table();
        equationTable.add(errorLabel).center().padTop(-25);
        equationTable.row();
        equationTable.add(equationField).width(equationField.getWidth() * 3f).center();
    }

    public void createDeckTable(ArrayList<String> deck){
        int cardCount = deck.size();
        float width = 81;
        float height = width * 1.333f;
        Color color = new Color(0.75f, 0.75f, 0.75f, 1);

        deckTable = new Table();

        for(int i = 0; i < cardCount; i++){
            Card card = new Card(atlas, skin, deck.get(i));

            float pad = -width * 0.65f;
            if(i == cardCount-1){
                pad = 2.5f;
                width = width * 1.1f;
                height = height * 1.1f;
                color.set(1,1,1,1);
            }

            card.createCard(width, height, color);

            card.setZIndex(i);
            card.setName("card" + i);
            deckTable.add(card).size(width,height).padRight(pad);
        }
        box.add(deckTable).right().expandX();
    }

    public void createInkBar(){
        inkBarBG = new Image(atlas.findRegion("ink_bar_bg"));
        inkBar = new Image(atlas.findRegion("ink_bar"));
        inkLabel = new Label("100 / 100",skin);

        inkBarBG.setOrigin(1);
        inkBarBG.scaleBy(2);
        inkBarBG.setPosition(545, 60);

        inkBar.scaleBy(2,2);
        inkBar.setPosition(445.25f,62);

        inkLabel.setColor(0,0,0,1);
        inkLabel.setPosition(620 - 0.5f * inkLabel.getWidth(), 30);
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

    public void updateInk(float percentInk){
        int percentWidth = (int) (127*percentInk/100);
        //Changes the bounds of the sprite it is taking
        atlas.findRegion("ink_bar").setRegionWidth(percentWidth);
        //Changes the scaling to be correct
        inkBar.setScale(3 * percentWidth/127f, 3);

        inkLabel.setText((int) gameWorld.currentInk + " / " + gameWorld.maxInk);
    }
}
