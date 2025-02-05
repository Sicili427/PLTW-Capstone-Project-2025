package io.github.pltwgame;

import io.github.pltwgame.Systems.*;
import io.github.pltwgame.Components.*;

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

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);

    World world;

    Stage taskbarUI;

    Texture texture;
    SpriteBatch batch;
    TextureRegion textureRegion;
    ShapeDrawer shapeDrawer;

    //FPSLogger fpsLogger;

    Grid grid;
    Taskbar taskbar;

    @Override
    public void create() {
        Gdx.app.setLogLevel(3);
        Gdx.app.log("Status", "Create Triggered");

        License.iConfirmNonCommercialUse("Team 7");

        ScreenViewport screenViewport = new ScreenViewport();
        taskbarUI = new Stage(screenViewport);

        Gdx.input.setInputProcessor(taskbarUI);

        texture = new Texture("pixel.png");
        batch = new SpriteBatch();
        textureRegion = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, textureRegion);

        WorldConfiguration config = new WorldConfigurationBuilder()
            .with(new HealthSystem())
            .build();
        world = new World(config);

        //fpsLogger = new FPSLogger();

        grid = new Grid(shapeDrawer, SCREEN_WIDTH, SCREEN_HEIGHT,64,2,1);
        grid.setOffsetY((int)(SCREEN_HEIGHT*0.225));
        grid.generateGrid();
        grid.centerOriginY();

        taskbar = new Taskbar(shapeDrawer, taskbarUI);

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

        grid.renderLines();

        taskbarUI.act(delta);
        taskbarUI.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            grid.addLine(taskbar.function1.getText());
            taskbar.function1.setText("");

            int entityId = world.create();

            HealthComponent health = world.edit(entityId).create(HealthComponent.class);

            health.health = 1000;
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
        grid.renderGrid(true);
        taskbar.draw();
    }

}
