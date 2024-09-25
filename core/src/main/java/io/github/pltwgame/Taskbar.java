package io.github.pltwgame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Taskbar {
    ShapeRenderer shapeRenderer;

    public Taskbar() {
        shapeRenderer = new ShapeRenderer();
    }
    public void generateTaskbar(int screenWidth, int screenHeight) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GRAY);
        //shapeRenderer.rect(70f, 280f, 1280f, 720f, Color.RED, Color.BLUE, Color.GREEN, Color.CYAN);
        shapeRenderer.rect(0, 0, screenWidth, (int)(screenHeight/5));

        shapeRenderer.end();
    }
}
