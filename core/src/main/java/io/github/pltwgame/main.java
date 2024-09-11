package io.github.pltwgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);

    final int GRID_WIDTH = 64;
    final int GRID_HEIGHT = 32;

    Viewport viewport;
    OrthographicCamera camera;

    SpriteBatch batch;
    Texture line;
    ShapeRenderer shapeRenderer;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        line = new Texture("pixel.png");
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        camera.update();

        drawBoard();
    }

    @Override
    public void dispose() {
        batch.dispose();
        line.dispose();
        shapeRenderer.dispose();
    }

    private void drawBoard() {
        GenerateGrid();
    }

    private void GenerateGrid() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for(int i = 0; i <= GRID_WIDTH; i++) {
            shapeRenderer.line((float) (i * (SCREEN_WIDTH / GRID_WIDTH)), 0, (float) (i * (SCREEN_WIDTH /GRID_WIDTH)), SCREEN_HEIGHT);
        }
        for (int i = 0; i <= GRID_HEIGHT; i++) {
            shapeRenderer.line(0, (float) (i * (SCREEN_HEIGHT / GRID_HEIGHT)), SCREEN_WIDTH, (float) (i * (SCREEN_HEIGHT /GRID_HEIGHT)));
        }
        shapeRenderer.end();
    }
}
