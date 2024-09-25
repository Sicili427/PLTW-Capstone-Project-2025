package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Grid {
    final int gridWidth;
    final int gridHeight;
    final int numVertLines;
    final int numHorzLines;
    final float CellX;
    final float CellY;
    int offsetX = 0;
    int offsetY = 0;
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
            vertLines[i] = new Vector2(x, gridHeight);
            shapeRenderer.line(x, offsetY, x, gridHeight);
        }
        for (int i = 0; i <= numHorzLines; i++) {
            float y = i * CellY;
            horzLines[i] = new Vector2(gridWidth, y);
            shapeRenderer.line(offsetX, y, gridWidth, y);
        }
        shapeRenderer.end();
    }

    public void generateLine() {
        // finds and puts points in an array
        int resolution = 1000;
        int size = numVertLines * resolution;
        Vector2[] points = new Vector2[size];

        int originOffsetX = 0;
        int originOffsetY = horzLines.length/2;

        for(int i = 0; i < size; i++) {
            double input = i / (double) resolution;
            float y = (float) (Math.sin(input));
            points[i] = new Vector2((float) input, y);
        }

        for(int i = 0; i < points.length; i++) {
            if (Float.isFinite(points[i].y)) {
                if (Math.floor(points[i].y) > horzLines.length - offsetY || Math.floor(points[i + 1].y) > horzLines.length - offsetY) {
                    continue;
                } else if (Math.floor(Math.abs(points[i].y)) > offsetY || Math.floor(Math.abs(points[i + 1].y)) > offsetY) {
                    continue;
                }

                float x1 = vertLines[(int) points[i].x + originOffsetX].x + (points[i].x - (int) points[i].x) * CellX;
                float y1 = horzLines[(int) points[i].y + originOffsetY].y + (points[i].y - (int) points[i].y) * CellY;

                float x2 = vertLines[(int) points[i + 1].x + originOffsetX].x + (points[i + 1].x - (int) points[i + 1].x) * this.CellX;
                float y2 = horzLines[(int) points[i + 1].y + originOffsetY].y + (points[i + 1].y - (int) points[i + 1].y) * this.CellY;

                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.RED);
                shapeRenderer.line(x1, y1, x2, y2);
                shapeRenderer.end();
            }
        }
    }
}
