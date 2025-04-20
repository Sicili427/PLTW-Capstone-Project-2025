package io.github.pltwgame.ui;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.JsonValue;
import io.github.pltwgame.EntityHandler;
import io.github.pltwgame.Grid;
import io.github.pltwgame.Line;
import io.github.pltwgame.components.LineComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.loaders.EntityFactory;
import io.github.pltwgame.loaders.JsonLoader;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Arrays;

public class Placeholder {
    private final float outline = 5;
    private final Stage stage;
    private final Skin skin;
    private final TextureAtlas textureAtlas;
    private final ShapeDrawer shapeDrawer;
    private final SpriteBatch plankBatch;
    private final Texture plankImg;
    private final TextButton showBar;
    final TextField function1;
    private final Label text1;

    private boolean shown = true;
    private float offset = 0, offsetShapes = 0;

    JsonValue uiJson = JsonLoader.getJson("uiConfig.json");

    JsonValue taskbarJson = uiJson.get("Taskbar");
    private float taskbarHeight = taskbarJson.getFloat("taskbarHeight");

    public final TextButton[] buttons;
    private final String[] buttonLabels = {"sin(x)", "cos(x)", "tan(x)", "/", "v--", "x^y", "log(x)", ">", "<", "|x|"};

    public Placeholder(ShapeDrawer shapeDrawer, Stage stage) {
        this.shapeDrawer = shapeDrawer;
        this.stage = stage;
        this.textureAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"), textureAtlas);

        buttons = new TextButton[buttonLabels.length];
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new TextButton(buttonLabels[i], skin);
            stage.addActor(buttons[i]);
        }

        showBar = new TextButton("v", skin);
        showBar.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleTaskbar();
                return true;
            }
        });
        stage.addActor(showBar);

        function1 = new TextField("", skin);
        function1.setMessageText("Enter a function...");
        stage.addActor(function1);

        text1 = new Label("f(x) = ", skin);
        text1.setColor(Color.BLACK);
        stage.addActor(text1);

        plankImg = new Texture(Gdx.files.internal("spruceplank.jpg"));
        plankBatch = new SpriteBatch();
    }

    private void toggleTaskbar() {
        float screenHeight = stage.getHeight();
        if (shown) {
            offset = taskbarHeight * screenHeight;
            offsetShapes = 160;
            showBar.setText("^");
        } else {
            offset = 0;
            offsetShapes = 0;
            showBar.setText("v");
        }
        shown = !shown;
    }

    public void draw() {
        float screenWidth = stage.getWidth(), screenHeight = stage.getHeight();
        shapeDrawer.getBatch().begin();
        shapeDrawer.filledRectangle(0, 0 - offsetShapes, screenWidth, screenHeight * taskbarHeight, Color.WHITE);
        plankBatch.begin();
        //plankBatch.draw(plankImg, 0, 0 - offsetShapes, screenWidth, screenHeight * taskbarHeight);

        float buttonsX = 15 * screenWidth / 25, buttonsY = (4 * screenHeight / 29) - offset;
        float buttonsW = 120, buttonsH = 50, buttonsM = screenWidth / 40;

        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setSize(buttonsW, buttonsH);
            buttons[i].setPosition(buttonsX + (i % 5) * (buttonsW + buttonsM), buttonsY - (i / 5) * (buttonsH + buttonsM));
        }

        showBar.setSize(30, 20);
        showBar.setPosition(screenWidth / 2 - 15, screenHeight * 0.2222f - offset);

        text1.setPosition(8 * screenWidth / 25, (3 * screenHeight / 32) - offset);
        function1.setPosition(text1.getX() + 50, text1.getY());
        function1.setSize(2 * screenWidth / 8, screenHeight / 16);

        shapeDrawer.getBatch().end();
        plankBatch.end();
    }

    public void process(Grid grid, World world) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Line line = grid.addLine(function1.getText());
            function1.setText("");

            Entity test = EntityFactory.createEntityFromJson(world, "baseEntity.json");

            PositionComponent pc = test.edit().create(PositionComponent.class);

            pc.x = 0;
            pc.y = 540;
            pc.angle = 270;

            if(line != null){
                LineComponent lc = test.edit().create(LineComponent.class);
                lc.lineId = line.id;
                lc.path = line.realPoints;
            }
        }
    }
}
