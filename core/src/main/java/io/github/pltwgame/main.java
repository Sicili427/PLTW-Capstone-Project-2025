package io.github.pltwgame;

import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.pltwgame.loaders.JsonLoader;
import io.github.pltwgame.systems.*;

import com.artemis.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.pltwgame.ui.ScreenUI;
import io.github.pltwgame.ui.Taskbar;
import io.github.pltwgame.ui.TopUI;
import space.earlygrey.shapedrawer.ShapeDrawer;
import org.mariuszgromada.math.mxparser.*;
import com.badlogic.gdx.utils.JsonValue;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    int SCREEN_WIDTH = 1280;
    int SCREEN_HEIGHT = 720;

    World world;

    Stage taskbarUI;

    Texture texture;
    SpriteBatch batch;
    Skin skin;

    Texture bgImage;
    SpriteBatch bgBatch;

    TextureRegion textureRegion;
    ShapeDrawer shapeDrawer;

    FPSLogger fpsLogger;

    Grid grid;
    Taskbar taskbar;
    TopUI topUI;
    ScreenUI screenUI;

    @Override
    public void create() {
        Gdx.app.log("Status", "Create Triggered");

        JsonValue json = JsonLoader.getJson("gameConfig.json");

        Gdx.app.setLogLevel(json.getInt("logLevel"));

        JsonValue windowSize = json.get("windowSize");
        SCREEN_WIDTH = windowSize.getInt("width");
        SCREEN_HEIGHT = windowSize.getInt("height");

        License.iConfirmNonCommercialUse("Team 7");

        taskbarUI = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(taskbarUI);

        texture = new Texture("pixel.png");
        batch = new SpriteBatch();
        textureRegion = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, textureRegion);

        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        //fpsLogger = new FPSLogger();

        grid = new Grid(shapeDrawer, SCREEN_WIDTH, SCREEN_HEIGHT,64,2,1);
        grid.setOffsetY((int)(SCREEN_HEIGHT*0.225));
        grid.generateGrid();
        grid.centerOriginY();

        WorldConfiguration config = new WorldConfigurationBuilder()
            .with(new SpriteSystem(shapeDrawer))
            .with(new HealthSystem(grid))
            .with(new MovementSystem())
            .build();
        world = new World(config);

        taskbar = new Taskbar(shapeDrawer, taskbarUI);
        topUI = new TopUI(shapeDrawer, taskbarUI);
        screenUI = new ScreenUI(shapeDrawer, skin, SCREEN_WIDTH, SCREEN_HEIGHT);

        Gdx.app.debug("Status", "Create Finished");
    }

    @Override
    public void resize(int width, int height) {
        taskbarUI.getViewport().update(width,height,true);
        screenUI.resize(width, height);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(1,1,1,1);

        drawBoard();

        world.setDelta(delta);
        world.process();

        taskbarUI.act(delta);
        taskbarUI.draw();
        taskbar.process(grid, world);
        screenUI.update(delta);
        screenUI.draw();

        //fpsLogger.log();
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
        taskbarUI.dispose();
        screenUI.dispose();
    }

    private void drawBoard() {;
        grid.renderGrid(true);
        taskbar.draw();
        grid.renderLines();
    }

}
