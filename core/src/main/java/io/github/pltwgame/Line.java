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

    private void findRealPoints(int resolution) {
        int size = resolution * parentGrid.numVertLines;
        realPoints = new Vector2[size];
        for (int i = 0; i < virtualPoints.length; i++) {
            if (Float.isFinite(virtualPoints[i].y)) {
                float x = parentGrid.vertLines[(int) virtualPoints[i].x + parentGrid.originOffsetX].x + (virtualPoints[i].x - (int) virtualPoints[i].x) * parentGrid.CellX;
                float y = parentGrid.horzLines[(int) virtualPoints[i].y + parentGrid.originOffsetY].y + (virtualPoints[i].y - (int) virtualPoints[i].y) * parentGrid.CellY;

                realPoints[i] = new Vector2(x, y);
            }
            else {
                realPoints[i] = new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
            }
        }
    }

    public void generateLine() {
        if (!isRendered) {
            parentGrid.renderGrid(true);
            // translates virtualPoints to a grid
            shapeDrawer.getBatch().begin();
            shapeDrawer.setColor(Color.RED);
            for (int i = 0; i < realPoints.length - 1; i++) {
                if (Float.isFinite(realPoints[i].y)) {
                    shapeDrawer.line(realPoints[i], realPoints[i + 1]);
                }
            }
            shapeDrawer.getBatch().end();
        }
        isRendered = true;
    }

    private double derive(double x) {
        return (equation.apply(x + 0.0001) - equation.apply(x))*10000;
    }

    public void throwToAI(TestAI ai){
        ai.addPoints(virtualPoints);
    }
}
