package io.github.pltwgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main implements ApplicationListener {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;

    Grid grid;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_INFO);
        Gdx.app.log("Status", "Create Triggered");
        Gdx.graphics.setContinuousRendering(false);
        Gdx.app.log("Status", Gdx.graphics.isContinuousRendering() + "");

        shapeRenderer = new ShapeRenderer();

        grid = new Grid(shapeRenderer, SCREEN_WIDTH,SCREEN_HEIGHT,64,2,1);
        grid.centerOriginY();

        Gdx.app.log("Status", "Create Finished");
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        Gdx.app.log("Status", "Render Triggered");
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        grid.generateGrid();
        grid.addLine();
        Gdx.app.log("Status", "Render Finished");
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
        shapeRenderer.dispose();
    }
}
