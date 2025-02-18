package io.github.pltwgame;

import io.github.pltwgame.systems.*;
import io.github.pltwgame.components.*;

import com.artemis.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
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

    Texture bgImage;
    SpriteBatch bgBatch;

    TextureRegion textureRegion;
    ShapeDrawer shapeDrawer;

    //FPSLogger fpsLogger;

    Grid grid;
    Taskbar taskbar;
    TopUI topUI;
    @Override
    public void create() {
        Gdx.app.log("Status", "Create Triggered");

        JsonValue json = JsonLoader.getJson("gameConfig.json");

        Gdx.app.setLogLevel(json.getInt("logLevel"));

        JsonValue windowSize = json.get("windowSize");
        SCREEN_WIDTH = windowSize.getInt("width");
        SCREEN_HEIGHT = windowSize.getInt("height");

        License.iConfirmNonCommercialUse("Team 7");

        ScreenViewport screenViewport = new ScreenViewport();
        taskbarUI = new Stage(screenViewport);

        Gdx.input.setInputProcessor(taskbarUI);

        texture = new Texture("pixel.png");
        batch = new SpriteBatch();
        textureRegion = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, textureRegion);

        bgImage = new Texture(Gdx.files.internal("battlefieldbg.jpg"));
        bgBatch = new SpriteBatch();

        fpsLogger = new FPSLogger();
      
        WorldConfiguration config = new WorldConfigurationBuilder()
            .with(new SpriteSystem())
            .build();
        world = new World(config);

        grid = new Grid(shapeDrawer, SCREEN_WIDTH, SCREEN_HEIGHT,64,2,1);
        grid.setOffsetY((int)(SCREEN_HEIGHT*0.225));
        grid.generateGrid();
        grid.centerOriginY();

        taskbar = new Taskbar(shapeDrawer, taskbarUI);
        topUI = new TopUI(shapeDrawer, taskbarUI);

        Gdx.app.debug("Status", "Create Finished");
    }

    @Override
    public void resize(int width, int height) {
        taskbarUI.getViewport().update(width,height,true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(1,1,1,1);

        world.setDelta(delta);
        world.process();

        drawBoard();

        taskbarUI.act(delta);
        taskbarUI.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            grid.addLine(taskbar.function1.getText());
            taskbar.function1.setText("");

            int entityId = world.create();

            SpriteComponent sprite = world.edit(entityId).create(SpriteComponent.class);
            PositionComponent position = world.edit(entityId).create(PositionComponent.class);

            sprite.texture = new Texture("libgdx.png");
            position.x = 640;
            position.y = 360;
        }

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
    }

    private void drawBoard() {
        bgBatch.begin();
        bgBatch.draw(bgImage, 0,0,1280, 720);
        bgBatch.end();
        grid.renderGrid(true);
        grid.renderLines();
        taskbar.draw();
        topUI.draw();
    }

}
