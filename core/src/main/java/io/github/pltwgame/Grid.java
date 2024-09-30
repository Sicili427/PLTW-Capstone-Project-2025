package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
// import io.github.pltwgame.Line;

public class Grid {
    final int gridWidth;
    final int gridHeight;
    final int numVertLines;
    final int numHorzLines;
    final float CellX;
    final float CellY;

    int offsetX = 0;
    int offsetY = 0;

    int originOffsetX;
    int originOffsetY;

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
        numHorzLines = (int) Math.ceil((double) maxHeight / cellSize);
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
            vertLines[i] = new Vector2(x + offsetX, gridHeight);
            shapeRenderer.line(x + offsetX, offsetY, x + offsetX, gridHeight);
        }
        for (int i = 0; i <= numHorzLines; i++) {
            float y = i * CellY;
            horzLines[i] = new Vector2(gridWidth, y + offsetY);
            shapeRenderer.line(offsetX, y + offsetY, gridWidth, y + offsetY);
        }
        shapeRenderer.end();
        Gdx.app.log("xLines", Arrays.toString(horzLines));
    }

    public void drawLines(){
        for (Line line : lines) {
            line.generateLine();
        }
    }

    public void newLine(Function<Double, Float> equation) {
        lines.add(new Line(this, equation));
    }
}
