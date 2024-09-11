package io.github.pltwgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    final float SCREEN_WIDTH = 1280;
    final float SCREEN_HEIGHT = Math.round((9 * SCREEN_WIDTH) / 16);

    final float GRID_WIDTH = 128;
    final float GRID_HEIGHT = 64;
    float circleX = 100;
    float circleY = 100;
    Viewport viewport;

    SpriteBatch batch;
    Texture line;
    ShapeRenderer shapeRenderer;

    MyInputProcessor inputProcessor = new MyInputProcessor();

    @Override
    public void create() {
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT);
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

        renderLine();
        drawBoard();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(200, 0, 1000, 900);
        shapeRenderer.end();
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            circleX+=SCREEN_WIDTH/320;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            circleX-=SCREEN_WIDTH/320;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            circleY-=SCREEN_HEIGHT/320;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            circleY+=SCREEN_HEIGHT/320;
        }
            circleX = Gdx.input.getX();
            circleY = SCREEN_HEIGHT-Gdx.input.getY();
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                Gdx.input.setCursorPosition(100, 100);
            }
    }

    @Override
    public void dispose() {
        batch.dispose();
        line.dispose();
        shapeRenderer.dispose();
    }

    private void drawBoard() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BROWN);
        shapeRenderer.circle(circleX, circleY, 50);
        shapeRenderer.end();
    }

    private void renderLine() {
        int resolution = 100;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        for(int i = 0; i < resolution; i++) {
            shapeRenderer.line(i * SCREEN_WIDTH/resolution, 0, i * SCREEN_WIDTH/resolution, SCREEN_HEIGHT);
        }
        shapeRenderer.end();
    }

}

