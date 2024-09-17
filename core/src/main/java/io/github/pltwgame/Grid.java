package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Grid {
    final int GRID_WIDTH;
    final int GRID_HEIGHT;
    final int SCREEN_WIDTH;
    final int SCREEN_HEIGHT;
    final float CellX;
    final float CellY;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    Vector2[] vertLines;
    Vector2[] horzLines;

    public Grid(int screenWidth, int screenHeight, int gridWidth, int gridHeight) {
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        GRID_WIDTH = gridWidth;
        GRID_HEIGHT = gridHeight;
        CellX = (float) SCREEN_WIDTH / GRID_WIDTH;
        CellY = (float) SCREEN_HEIGHT / GRID_HEIGHT;
    }

    public Grid(int screenWidth, int screenHeight, int gridWidth, int ratioX, int ratioY) {
        SCREEN_WIDTH = screenWidth;
        SCREEN_HEIGHT = screenHeight;
        GRID_WIDTH = gridWidth;
        GRID_HEIGHT = (gridWidth * ratioY) / ratioX;
        CellX = (float) SCREEN_WIDTH / GRID_WIDTH;
        CellY = (float) SCREEN_HEIGHT / GRID_HEIGHT;
    }

    public void generateGrid() {
        // generates a list for horizontal and vertical lines
        vertLines = new Vector2[GRID_WIDTH];
        horzLines = new Vector2[GRID_HEIGHT];
        // generates
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        for(int i = 0; i < GRID_WIDTH; i++) {
            float coords = i * CellX;
            vertLines[i] = new Vector2(coords, SCREEN_HEIGHT);
            shapeRenderer.line(coords, 0, coords, SCREEN_HEIGHT);
        }
        for (int i = 0; i < GRID_HEIGHT; i++) {
            float coords = i * CellY;
            horzLines[i] = new Vector2(SCREEN_WIDTH, coords);
            shapeRenderer.line(0, coords, SCREEN_WIDTH, coords);
        }
        shapeRenderer.end();
    }
}
