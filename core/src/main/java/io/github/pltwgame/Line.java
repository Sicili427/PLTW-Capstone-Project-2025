package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.function.Function;

public class Line{

    public static int lineIndex = 0;

    ShapeDrawer shapeDrawer;

    Grid parentGrid;

    String id;

    boolean isRendered = false;

    Function<Double, Float> equation;

    Vector2[] virtualPoints;
    Vector2[] realPoints;

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, Function<Double, Float> initEquation) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        equation = initEquation;
        findVirtualPoints(resolution);
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = id = "line" + lineIndex;
        lineIndex++;
        equation = input -> (float) Math.sin(input);
        findVirtualPoints(resolution);
    }

    public String toString() {
        return id + " | parented to grid" + parentGrid.id + " | with equation " + equation.toString() + " | is rendered? " + isRendered;
    }

    private void findVirtualPoints(int resolution) {
        // calculates the virtualPoints for the line from a given equation
        int size = resolution * parentGrid.numVertLines;
        virtualPoints = new Vector2[size];
        for(int i = 0; i < size; i++) {
            double input = i / (double) resolution;
            float y = equation.apply(input);
            virtualPoints[i] = new Vector2((float) input, y);
        }
    }

    public void generateLine() {
        if (!isRendered) {
            parentGrid.generateGrid(true);
            // translates virtualPoints to a grid
            shapeDrawer.getBatch().begin();
            shapeDrawer.setColor(Color.RED);
            for (int i = 0; i < virtualPoints.length - 1; i++) {
                if (Float.isFinite(virtualPoints[i].y)) {
                    // checks if point is above grid vertical range
                    if (Math.floor(virtualPoints[i].y) >= parentGrid.numHorzLines - parentGrid.originOffsetY || Math.floor(virtualPoints[i + 1].y) >= parentGrid.numHorzLines - parentGrid.originOffsetY) {
                        continue;
                        // checks if point is below the grid vertical range
                    } else if (Math.floor(Math.abs(virtualPoints[i].y)) >= parentGrid.originOffsetY || Math.floor(Math.abs(virtualPoints[i + 1].y)) >= parentGrid.originOffsetY) {
                        continue;
                    }

                    float x1 = parentGrid.vertLines[(int) virtualPoints[i].x + parentGrid.originOffsetX].x + (virtualPoints[i].x - (int) virtualPoints[i].x) * parentGrid.CellX;
                    float y1 = parentGrid.horzLines[(int) virtualPoints[i].y + parentGrid.originOffsetY].y + (virtualPoints[i].y - (int) virtualPoints[i].y) * parentGrid.CellY;

                    float x2 = parentGrid.vertLines[(int) virtualPoints[i + 1].x + parentGrid.originOffsetX].x + (virtualPoints[i + 1].x - (int) virtualPoints[i + 1].x) * parentGrid.CellX;
                    float y2 = parentGrid.horzLines[(int) virtualPoints[i + 1].y + parentGrid.originOffsetY].y + (virtualPoints[i + 1].y - (int) virtualPoints[i + 1].y) * parentGrid.CellY;

                    shapeDrawer.line(x1, y1, x2, y2);
                }
            }
            shapeDrawer.getBatch().end();
        }
        isRendered = true;
    }
}
