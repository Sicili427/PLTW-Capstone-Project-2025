package io.github.pltwgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    //1280*720
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);
    float circleX = 100;
    float circleY = 100;

    final int GRID_WIDTH = 64;
    final int GRID_HEIGHT = 32;

    TestAI testAI = new TestAI(0, 0);

    Viewport viewport;
    OrthographicCamera camera;

    SpriteBatch batch;
    Texture line;
    ShapeRenderer shapeRenderer;

    MyInputProcessor inputProcessor = new MyInputProcessor();

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
        shapeRenderer.end();
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
                //testAI.moveToPoint(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        testAI.drawAI(shapeRenderer);
    }

    @Override
    public void dispose() {
        batch.dispose();
        line.dispose();
        shapeRenderer.dispose();
    }

    private void drawBoard() {
        GenerateGrid();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.circle(circleX, circleY, 50);
        shapeRenderer.end();
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
