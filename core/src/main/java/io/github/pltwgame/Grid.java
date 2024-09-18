package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Grid {
    final int gridWidth;
    final int gridHeight;
    final int numVertLines;
    final int numHorzLines;
    final float CellX;
    final float CellY;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    Vector2[] vertLines;
    Vector2[] horzLines;

    public Grid(int maxWidth, int maxHeight, int initVertLines, int initHorzLines) {
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        numVertLines = initVertLines;
        numHorzLines = initHorzLines;
        CellX = (float) maxWidth / initVertLines;
        CellY = (float) maxHeight / initHorzLines;
    }

    public Grid(int maxWidth, int maxHeight, int initHorzLines, int ratioX, int ratioY) {
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        numVertLines = initHorzLines;
        numHorzLines = (int) Math.ceil((double) (initHorzLines * ratioY) / ratioX);
        CellX = (float) maxWidth / numVertLines;
        CellY = (float) maxHeight / numHorzLines;
    }

    public Grid(int maxWidth, int maxHeight, int cellSize) {
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        CellX = (float) cellSize;
        CellY = (float) cellSize;
        numVertLines = (int) Math.ceil((double) maxWidth / cellSize);
        numHorzLines = (int) Math.ceil((double) maxHeight / cellSize);;
    }

    public void generateGrid() {
        // generates a list for horizontal and vertical lines
        vertLines = new Vector2[numVertLines];
        horzLines = new Vector2[numHorzLines];
        // generates grid
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        for(int i = 0; i <= numVertLines; i++) {
            float x = i * CellX;
            vertLines[i] = new Vector2(x, gridHeight);
            shapeRenderer.line(x, 0, x, gridHeight);
        }
        for (int i = 0; i <= numHorzLines; i++) {
            float y = i * CellY;
            horzLines[i] = new Vector2(gridWidth, y);
            shapeRenderer.line(0, y, gridWidth, y);
        }
        shapeRenderer.end();
    }
}
