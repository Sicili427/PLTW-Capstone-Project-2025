package io.github.pltwgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
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



    Stage taskbarUI;

    Texture texture;
    SpriteBatch batch;
    TextureRegion textureRegion;
    ShapeDrawer shapeDrawer;

    FPSLogger fpsLogger;

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

        fpsLogger = new FPSLogger();

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

        drawBoard();

        grid.renderLines();

        taskbarUI.act(delta);
        taskbarUI.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            grid.addLine(input -> (float) (Math.tan(input) + Math.pow(Math.sin(input),2)));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Expression e = new Expression(taskbar.function1.getText());
            Gdx.app.debug("Value", e.calculate() + "");
        }

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
        taskbarUI.dispose();
    }

    private void drawBoard() {
        grid.renderGrid(true);
        taskbar.draw();
    }

}
