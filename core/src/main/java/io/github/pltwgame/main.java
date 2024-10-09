package io.github.pltwgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import space.earlygrey.shapedrawer.ShapeDrawer;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    // 1280x720px
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);
    float circleX = 100;
    float circleY = 100;

    Grid grid;
    Taskbar taskbar;

    Stage stage;

    TextureAtlas textureAtlas;
    Skin skin;
    TextField textField;

    Texture texture;
    SpriteBatch batch;
    TextureRegion textureRegion;
    ShapeDrawer shapeDrawer;

    FPSLogger fpsLogger;

    Grid grid;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_INFO); // logging not working idk why :/
        Gdx.app.log("Status", "Create Triggered");

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        textureAtlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin(Gdx.files.internal("uiskin.json"), textureAtlas);
        textField = new TextField("", skin);

        texture = new Texture("pixel.png");
        batch = new SpriteBatch();
        textureRegion = new TextureRegion(texture, 0, 0, 1, 1);
        shapeDrawer = new ShapeDrawer(batch, textureRegion);

        fpsLogger = new FPSLogger();

        grid = new Grid(shapeDrawer, SCREEN_WIDTH,SCREEN_HEIGHT,64,2,1);
        shapeRenderer = new ShapeRenderer();
        taskbar = new Taskbar();
        batch = new SpriteBatch();

        textField.setMessageText("Enter text...");
        textField.setPosition(100, 150);  // Position the text input on the screen
        textField.setSize(300, 40);

        grid.centerOriginY();

        Gdx.app.log("Status", "Create Finished");
    }

    @Override
    public void resize(int width, int height) {
        // Resize your application here. The parameters represent the new window size.
    }

    @Override
    public void render() {
        grid.generateGrid(false);

        stage.act();
        stage.draw();
        ScreenUtils.clear(0.1f, 1f, 0.1f, 0.5f);
        camera.update();
        drawBoard();

        if(Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            grid.addLine();
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
    }

    private void drawBoard() {
        grid.generateGrid();
        taskbar.generateTaskbar(SCREEN_WIDTH, SCREEN_HEIGHT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.circle(circleX, circleY, 50);
        shapeRenderer.end();
    }

    private void generateLine(Grid initGrid) {
        // finds and puts points in an array
        int resolution = 1000;
        int size = grid.numVertLines * resolution;
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
