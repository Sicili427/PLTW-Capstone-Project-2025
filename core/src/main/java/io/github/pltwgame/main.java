package io.github.pltwgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/** {@link ApplicationListener} implementation shared by all platforms. */
public class main extends ApplicationAdapter {
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture line;
    private ShapeRenderer shapeRenderer;


    @Override
    public void create() {
        viewport = new FillViewport(1600, 900);
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
        // Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        renderLine();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.line(200, 0, 1000, 900);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        line.dispose();
        shapeRenderer.dispose();
    }

    private void renderLine() {
        int resolution = 100;

        batch.begin();
        for(int i = 0; i < resolution; i++) {
            batch.draw(line, (float) (i * (1600/resolution)), (float) (20*Math.sin(i) + 200));
        }
        batch.end();
    }
}
