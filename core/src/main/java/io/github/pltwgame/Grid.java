package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import space.earlygrey.shapedrawer.ShapeDrawer;

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

    int offsetX = 0;
    int offsetY = 0;

    int originOffsetX = 0;
    int originOffsetY = 0;

    int gridXMin = 0;
    int gridXMax = 0;

    int gridYMin = 0;
    int gridYMax = 0;

    boolean isRendered = false;

    ShapeDrawer shapeDrawer;

    Vector2[] vertLines;
    Vector2[] horzLines;

    ArrayList<Line> lines = new ArrayList<>();


    public Grid(ShapeDrawer initRenderer, int maxWidth, int maxHeight, int initVertLines, int initHorzLines) {
        shapeDrawer = initRenderer;
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        numVertLines = initVertLines;
        numHorzLines = initHorzLines;
        vertLines = new Vector2[numVertLines + 1];
        horzLines = new Vector2[numHorzLines + 1];
        CellX = (float) maxWidth / initVertLines;
        CellY = (float) maxHeight / initHorzLines;
        id = gridIndex;
        gridIndex++;
    }

    public Grid(ShapeDrawer initRenderer, int maxWidth, int maxHeight, int initVertLines, int ratioX, int ratioY) {
        shapeDrawer = initRenderer;
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        numVertLines = initVertLines;
        numHorzLines = (int) Math.ceil((double) (initVertLines * ratioY) / ratioX);
        vertLines = new Vector2[numVertLines + 1];
        horzLines = new Vector2[numHorzLines + 1];
        CellX = (float) maxWidth / numVertLines;
        CellY = (float) maxHeight / numHorzLines;
        id = gridIndex;
        gridIndex++;
    }

    public Grid(ShapeDrawer initRenderer, int maxWidth, int maxHeight, int cellSize) {
        shapeDrawer = initRenderer;
        gridWidth = maxWidth;
        gridHeight = maxHeight;
        CellX = (float) cellSize;
        CellY = (float) cellSize;
        numVertLines = (int) Math.ceil((double) maxWidth / cellSize);
        numHorzLines = (int) Math.ceil((double) maxHeight / cellSize);
        vertLines = new Vector2[numVertLines + 1];
        horzLines = new Vector2[numHorzLines + 1];
        id = gridIndex;
        gridIndex++;
    }

    public void centerOriginY() {
        originOffsetY = numHorzLines / 2;
        gridYMax = numHorzLines - originOffsetY;
        gridYMin = -originOffsetY;
    }

    public void centerOriginX() {
        originOffsetX = numVertLines / 2;
        gridXMax = numVertLines - originOffsetX;
        gridXMin = -originOffsetX;
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
        for (int i = 0; i <= numVertLines; i++) {
            float x = i * CellX;
            vertLines[i] = new Vector2(x + offsetX, gridHeight);
        }
        for (int i = 0; i <= numHorzLines; i++) {
            float y = i * CellY;
            horzLines[i] = new Vector2(gridWidth, y + offsetY);
        }
    }

    public void renderGrid(boolean recursiveRender) {
        if(!isRendered || recursiveRender) {
            ScreenUtils.clear(1f, 1f, 1f, 1f);
            // generates grid
            shapeDrawer.getBatch().begin();
            shapeDrawer.setColor(Color.GRAY);
            for (int i = 0; i <= numVertLines; i++) {
                shapeDrawer.line(vertLines[i].x, offsetY, vertLines[i].x, gridHeight);
            }
            for (int i = 0; i < numHorzLines; i++) {
                shapeDrawer.line(offsetX, horzLines[i].y, gridWidth, horzLines[i].y);
            }
            shapeDrawer.getBatch().end();
            isRendered = true;
        }
    }

    public void addLine() {
        Line temp = new Line(shapeDrawer, this,100, input -> (float) Math.tan(input));
        lines.add(temp);
        temp.generateLine();
    }
}
