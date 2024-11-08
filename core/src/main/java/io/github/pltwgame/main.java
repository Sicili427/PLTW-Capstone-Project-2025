package io.github.pltwgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);

    Stage stage;
    Stage taskbarUI;

    TextureAtlas textureAtlas;
    Skin skin;

    ArrayList<TestAI> testAIs = new ArrayList<>();

    //Taskbar taskbar = new Taskbar();
    Texture texture;
    SpriteBatch batch;
    TextureRegion textureRegion;
    ShapeDrawer shapeDrawer;

    FPSLogger fpsLogger;

    Grid grid;
    Taskbar taskbar;
    float circleX = 100;
    float circleY = 100;

    @Override
    public void create() {
        Gdx.app.setLogLevel(3);
        Gdx.app.log("Status", "Create Triggered");

        ScreenViewport screenViewport = new ScreenViewport();
        stage = new Stage(screenViewport);
        taskbarUI = new Stage(screenViewport);

        Gdx.input.setInputProcessor(taskbarUI);


        textureAtlas = new TextureAtlas(Gdx.files.internal("skin/uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"), textureAtlas);

        texture = new Texture("pixel.png");
        batch = new SpriteBatch();
        textureRegion = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, textureRegion);

        fpsLogger = new FPSLogger();

        grid = new Grid(shapeDrawer, SCREEN_WIDTH, SCREEN_HEIGHT,64,2,1);
        grid.setOffsetY((int)(SCREEN_HEIGHT*0.2));
        grid.generateGrid();
        grid.centerOriginY();

        taskbar = new Taskbar(shapeDrawer, taskbarUI);
        //taskbar.generateTaskbar(SCREEN_WIDTH,SCREEN_HEIGHT);

        testAIs.add(new TestAI(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0));

        Gdx.app.debug("Status", "Create Finished");
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
        taskbarUI.getViewport().update(width,height,true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        circleX = Gdx.input.getX();
        circleY = SCREEN_HEIGHT-Gdx.input.getY();

        ScreenUtils.clear(1,1,1,1);

        drawBoard();

        grid.renderLines();

        for(TestAI testAI : testAIs) {
            testAI.moveToPoint();
            testAI.drawAI(shapeDrawer);
        }

        stage.act(delta);
        stage.draw();
        taskbarUI.act(delta);
        taskbarUI.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            grid.addLine(input -> (float) (Math.pow(Math.sin(input),2)+Math.sin(input)));
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
        textureAtlas.dispose();
        skin.dispose();
        stage.dispose();
        taskbarUI.dispose();
    }

    private void drawBoard() {
        grid.renderGrid(true);
        taskbar.generateTaskbar(SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.begin();
        shapeDrawer.setColor(Color.BROWN);
        shapeDrawer.circle(circleX, circleY, 50);
        batch.end();
    }

}
