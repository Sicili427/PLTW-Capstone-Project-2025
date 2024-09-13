package io.github.pltwgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);

    Grid grid;

    Stage stage;

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
        stage = new Stage(viewport);
        grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT, 64, 2, 1);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        line = new Texture("pixel.png");

        Gdx.app.setLogLevel(Application.LOG_INFO);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f);
        camera.update();

        drawBoard();

        generateLine();
    }

    @Override
    public void dispose() {
        batch.dispose();
        line.dispose();
        shapeRenderer.dispose();
    }

    private void drawBoard() {
        grid.generateGrid();
    }

    private void generateLine(Grid initGrid) {
        // finds and puts points in an array
        int resolution = SCREEN_WIDTH * 10;
        Vector2[] points = new Vector2[resolution];

        for(int i = 0; i < resolution; i++) {
            double input = i / 10.0;
            points[i] = new Vector2((float )input, (float) Math.sin(input));
        }
        // translates points to grid
        float x = 0;
        float y = 0;

        Grid grid = initGrid;

        for(int i = 0; i < points.length; i++) {
            
        }
    }
}
