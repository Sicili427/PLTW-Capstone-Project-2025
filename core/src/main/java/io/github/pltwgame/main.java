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

        generateLine(grid);
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
            points[i] = new Vector2((float) input, (float) Math.sin(Math.sqrt(input)));
        }
        // translates points to grid
        float x1 = 0;
        float y1 = 0;

        float x2 = 0;
        float y2 = 0;

        int offsetX = 0;
        int offsetY = grid.horzLines.length/2;

        for(int i = 0; i < points.length; i++) {
            x1 = initGrid.vertLines[(int) points[i].x + offsetX].x + (points[i].x - (int) points[i].x) * initGrid.CellX;
            y1 = initGrid.horzLines[(int) points[i].y + offsetY].y + (points[i].y - (int) points[i].y) * initGrid.CellY;

            x2 = initGrid.vertLines[(int) points[i+1].x + offsetX].x + (points[i+1].x - (int) points[i+1].x) * initGrid.CellX;;
            y2 = initGrid.horzLines[(int) points[i+1].y + offsetY].y + (points[i+1].y - (int) points[i+1].y) * initGrid.CellY;;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.line(x1,y1,x2,y2);
            shapeRenderer.end();
        }
    }
}
