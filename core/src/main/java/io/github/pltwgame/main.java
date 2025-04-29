
package io.github.pltwgame;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.github.pltwgame.gameCore.GameWorld;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.systems.*;
import com.artemis.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.pltwgame.ui.ScreenUI;
import io.github.pltwgame.ui.Taskbar;
import space.earlygrey.shapedrawer.ShapeDrawer;
import org.mariuszgromada.math.mxparser.*;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    int SCREEN_WIDTH = 1280;
    int SCREEN_HEIGHT = 720;

    GameWorld gameWorld;

    World world;
    FitViewport worldViewport;

    Texture texture;
    SpriteBatch batch;
    Skin skin;

    ShapeDrawer shapeDrawer;

    FPSLogger fpsLogger;

    Grid grid;
    Taskbar taskbar;
    ScreenUI screenUI;

    @Override
    public void create() {
        Gdx.app.setLogLevel(3);

        Gdx.app.debug("Status", "Create Triggered");
        // signs agreement for mx-parser
        License.iConfirmNonCommercialUse("Team 7");

        // creates shapeDrawer
        batch = new SpriteBatch();
        texture = new Texture("pixel.png");
        TextureRegion textureRegion = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, textureRegion);

        // skin
        skin = new Skin(Gdx.files.internal("testSkin/testSkin.json"));

        for(Texture texture : skin.getAtlas().getTextures()){
            texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        }

        // viewport
        worldViewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);

        // grid
        grid = new Grid(shapeDrawer, SCREEN_WIDTH-32, SCREEN_HEIGHT,64,2,1);
        grid.setOffsetY((int)(SCREEN_HEIGHT*0.30));
        grid.setOffsetX(32);
        grid.generateGrid();
        grid.centerOriginY();

        // Artemis-ODB world configuration
        WorldConfiguration config = new WorldConfigurationBuilder()
            .with(new SpriteSystem(shapeDrawer))
            .with(new HealthSystem(grid))
            .with(new MovementSystem())
            .with(new CombatSystem())
            .build();
        world = new World(config);

        gameWorld = new GameWorld(grid, world);

        // taskbar + screenUI
        taskbar = new Taskbar(skin, shapeDrawer, batch, worldViewport, grid, gameWorld);
        screenUI = new ScreenUI(shapeDrawer, batch, worldViewport);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(taskbar.stage);

        Gdx.input.setInputProcessor(multiplexer);

        Gdx.app.debug("Status", "Create Finished");
    }

    @Override
    public void resize(int width, int height) {
        taskbar.resize(width, height);
        screenUI.resize(width, height);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(1,1,1,1);

        grid.renderGrid(true);
        grid.renderLines();

        world.setDelta(delta);
        world.process();

        taskbar.update(delta);
        taskbar.draw();

        screenUI.update(delta);
        screenUI.draw();

        gameWorld.process(delta);

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
        taskbar.dispose();
        screenUI.dispose();
    }
}
