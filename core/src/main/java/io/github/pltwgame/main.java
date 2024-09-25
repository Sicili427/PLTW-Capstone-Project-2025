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
    ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        stage = new Stage(viewport);
        grid = new Grid(SCREEN_WIDTH,(SCREEN_HEIGHT),64,2,1);
        grid.offsetY = (int)(SCREEN_HEIGHT*0.1);

        shapeRenderer = new ShapeRenderer();

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

        grid.generateLine();
        /* circleX = Gdx.input.getX();
        circleY = SCREEN_HEIGHT-Gdx.input.getY();
        testAI.moveToPoint();
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            float xSpot = (float) Math.random()*SCREEN_WIDTH;
            float ySpot = (float) Math.random()*SCREEN_HEIGHT;
            testAI.addPoint(xSpot, ySpot, false);
        }
        testAI.drawAI(shapeRenderer);
        testAI.moveToPoint(new Vector2(Gdx.input.getX(), Gdx.input.getY()));

         */
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        shapeRenderer.dispose();
    }

    private void drawBoard() {
        grid.generateGrid();
    }

}
