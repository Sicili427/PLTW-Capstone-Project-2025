package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Function;

public class Grid {
    public static int gridIndex = 0;

    final int id;

    final int gridWidth;
    final int gridHeight;

    final int numVertLines;
    final int numHorzLines;

    float CellX;
    float CellY;

    private int offsetX = 0;
    private int offsetY = 0;

    int originOffsetX = 0;
    int originOffsetY = 0;

    ShapeRenderer shapeRenderer;

    Vector2[] vertLines;
    Vector2[] horzLines;

    ArrayList<Line> lines = new ArrayList<>();


    public Grid(ShapeRenderer initRenderer, int maxWidth, int maxHeight, int initVertLines, int initHorzLines) {
        shapeRenderer = initRenderer;
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        numVertLines = initVertLines;
        numHorzLines = initHorzLines;
        CellX = (float) maxWidth / initVertLines;
        CellY = (float) maxHeight / initHorzLines;
        id = gridIndex;
        gridIndex++;
    }

    public Grid(ShapeRenderer initRenderer, int maxWidth, int maxHeight, int initVertLines, int ratioX, int ratioY) {
        shapeRenderer = initRenderer;
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        numVertLines = initVertLines;
        numHorzLines = (int) Math.ceil((double) (initVertLines * ratioY) / ratioX);
        CellX = (float) maxWidth / numVertLines;
        CellY = (float) maxHeight / numHorzLines;
        id = gridIndex;
        gridIndex++;
    }

    public Grid(ShapeRenderer initRenderer, int maxWidth, int maxHeight, int cellSize) {
        shapeRenderer = initRenderer;
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        CellX = (float) cellSize;
        CellY = (float) cellSize;
        numVertLines = (int) Math.ceil((double) maxWidth / cellSize);
        numHorzLines = (int) Math.ceil((double) maxHeight / cellSize);
        id = gridIndex;
        gridIndex++;
    }

    public void centerOriginY() {
        originOffsetY = numHorzLines / 2;
    }

    public void centerOriginX() {
        originOffsetX = numVertLines / 2;
    }

    public void setOffsetX(int num) {
        offsetX = num;
        CellX = (float) (gridWidth - offsetX) / numVertLines;
    }

    public void setOffsetY(int num) {
        offsetY = num;
        CellY = (float) (gridHeight - offsetY) / numHorzLines;
    }

    public void generateGrid() {
        // generates a list for horizontal and vertical lines
        vertLines = new Vector2[numVertLines];
        horzLines = new Vector2[numHorzLines];
        // generates grid
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.GRAY);
        for(int i = 0; i < numVertLines; i++) {
            float x = i * CellX;
            vertLines[i] = new Vector2(x + offsetX, gridHeight);
            shapeRenderer.line(x + offsetX, offsetY, x + offsetX, gridHeight);
        }
        for (int i = 0; i < numHorzLines; i++) {
            float y = i * CellY;
            horzLines[i] = new Vector2(gridWidth, y + offsetY);
            shapeRenderer.line(offsetX, y + offsetY, gridWidth, y + offsetY);
        }
        shapeRenderer.end();
    }

    public void addLine() {
        String name = "line" + Line.lineIndex;
        Line temp = new Line(shapeRenderer, this, name, 100);
        lines.add(temp);
        temp.generateLine();
    }
}
