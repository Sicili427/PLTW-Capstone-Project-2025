package io.github.pltwgame;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.pltwgame.components.DrawingEditingSystem;
import io.github.pltwgame.components.DrawingSystem;
import io.github.pltwgame.components.TestSystem;
import space.earlygrey.shapedrawer.ShapeDrawer;
import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);

    WorldConfiguration setup;
    World world;
    boolean doOnce = true;

    Stage stage;

    TextureAtlas textureAtlas;
    Skin skin;
    TextField textField;

    ArrayList<TestAI> testAIs = new ArrayList<>();

    Texture texture;
    SpriteBatch batch;
    TextureRegion textureRegion;
    ShapeDrawer shapeDrawer;

    FPSLogger fpsLogger;

    Grid grid;

    float circleX = 100;
    float circleY = 100;

    @Override
    public void create() {
        Gdx.app.setLogLevel(3); // logging not working idk why :/
        Gdx.app.log("Status", "Create Triggered");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        textureAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), textureAtlas);
        textField = new TextField("", skin);
        textField.setMaxLength(50);

        texture = new Texture("pixel.png");
        batch = new SpriteBatch();
        textureRegion = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, textureRegion);

        fpsLogger = new FPSLogger();

        grid = new Grid(shapeDrawer, SCREEN_WIDTH,SCREEN_HEIGHT,64,2,1);
        grid.generateGrid();

        setup = new WorldConfigurationBuilder()
            .with(new TestSystem())
            .build();
        world = new World(setup);

        textField.setMessageText("Enter text...");
        textField.setPosition(100, 150);
        textField.setSize(300, 40);

        grid.centerOriginY();

        testAIs.add(new TestAI(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, world));

        stage.addActor(textField);

        Gdx.app.log("Status", "Create Finished");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();



        circleX = Gdx.input.getX();
        circleY = SCREEN_HEIGHT-Gdx.input.getY();

        drawBoard();

        for(TestAI testAI : testAIs) {
            testAI.moveToPoint();
        }

        stage.act(delta);
        stage.draw();

        grid.addLine(input -> (float) Math.sin(input));

        if(doOnce){
            doOnce = false;
            for(TestAI testAI : testAIs) {
                grid.throwLinesToAI(testAI);
            }
        }

//        world.setDelta(delta);
//        world.process();

        fpsLogger.log();
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        textureAtlas.dispose();
        skin.dispose();
        stage.dispose();
    }

    private void drawBoard() {
        grid.renderGrid(true);
        batch.begin();
        shapeDrawer.setColor(Color.BROWN);
        shapeDrawer.circle(circleX, circleY, 50);
        batch.end();
    }

}
