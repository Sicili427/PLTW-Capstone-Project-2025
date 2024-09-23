package io.github.pltwgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Arrays;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);
    float circleX = 100;
    float circleY = 100;

    Grid grid;

    Stage stage;

    TestAI testAI = new TestAI(0, 0, SCREEN_HEIGHT, SCREEN_WIDTH);

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
        grid = new Grid(SCREEN_WIDTH, SCREEN_HEIGHT,64,2,1);

        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

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
        circleX = Gdx.input.getX();
        circleY = SCREEN_HEIGHT-Gdx.input.getY();
        testAI.moveToPoint();
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            float xSpot = (float) Math.random()*SCREEN_WIDTH;
            float ySpot = (float) Math.random()*SCREEN_HEIGHT;
            testAI.addPoint(xSpot, ySpot, false);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.BROWN);
            shapeRenderer.circle(xSpot, ySpot, 50);
            shapeRenderer.end();
        }
        testAI.drawAI(shapeRenderer);
        testAI.moveToPoint(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }

    private void drawBoard() {
        grid.generateGrid();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.circle(circleX, circleY, 50);
        shapeRenderer.end();
    }

    private void generateLine(Grid initGrid) {
        // finds and puts points in an array
        int resolution = 1000;
        int size = grid.numVertLines * 1000;
        Vector2[] points = new Vector2[size];

        int offsetX = 0;
        int offsetY = initGrid.horzLines.length/2;

        for(int i = 0; i < size; i++) {
            double input = i / (double) resolution;
            float y = (float) (Math.tan(input));
            points[i] = new Vector2((float) input, y);
        }

        for(int i = 0; i < points.length; i++) {
            if (!Float.isInfinite(points[i].y) || !Float.isNaN(points[i].y)) {
                if (Math.floor(points[i].y) > initGrid.horzLines.length - offsetY || Math.floor(points[i + 1].y) > initGrid.horzLines.length - offsetY) {
                    continue;
                } else if (Math.floor(Math.abs(points[i].y)) > offsetY || Math.floor(Math.abs(points[i + 1].y)) > offsetY) {
                    continue;
                }

                float x1 = initGrid.vertLines[(int) points[i].x + offsetX].x + (points[i].x - (int) points[i].x) * initGrid.CellX;
                float y1 = initGrid.horzLines[(int) points[i].y + offsetY].y + (points[i].y - (int) points[i].y) * initGrid.CellY;

                float x2 = initGrid.vertLines[(int) points[i + 1].x + offsetX].x + (points[i + 1].x - (int) points[i + 1].x) * initGrid.CellX;
                float y2 = initGrid.horzLines[(int) points[i + 1].y + offsetY].y + (points[i + 1].y - (int) points[i + 1].y) * initGrid.CellY;

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.line(x1, y1, x2, y2);
                shapeRenderer.end();
            }
        }
    }
}
