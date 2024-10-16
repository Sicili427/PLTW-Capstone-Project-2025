package io.github.pltwgame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.Arrays;
import java.util.function.Function;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);
    float circleX = 100;
    float circleY = 100;

    Grid grid;

    Stage stage;

    Line line;

    int stupid = 0;
    int stupid2 = 0;

    ArrayList<TestAI> testAIS = new ArrayList<>();

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
        shapeRenderer = new ShapeRenderer();

        grid = new Grid(shapeRenderer, SCREEN_WIDTH,SCREEN_HEIGHT,64,2,1);
        grid.centerOriginY();

        testAIS.add(new TestAI(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0));

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
        if(stupid == 0){
            stupid++;
            grid.addLine(input -> (float) Math.sin(1/(input/100)));
            grid.throwLinesToAI(testAIS.get(0));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            if(stupid2 == 0){
                stupid2++;
                grid.addLine(input -> (float) Math.cos(input));
                testAIS.add(new TestAI(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 1));
                grid.throwLinesToAI(testAIS.get(1));
            }
        }
        circleX = Gdx.input.getX();
        circleY = SCREEN_HEIGHT-Gdx.input.getY();
        grid.drawLines();
        for(TestAI testAI : testAIS) {
            testAI.moveToPoint();
            testAI.drawAI(shapeRenderer);
        }
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

}
