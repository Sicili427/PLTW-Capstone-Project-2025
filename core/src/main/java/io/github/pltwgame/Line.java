package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
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
    boolean hidden = false;

    Function<Double, Float> equation;

    Vector2[] virtualPoints;
    Vector2[] realPoints;

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, Function<Double, Float> initEquation, boolean isHidden) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        equation = initEquation;
        hidden = isHidden;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, Function<Double, Float> initEquation) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        equation = initEquation;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, boolean isHidden) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        equation = input -> (float) Math.sin(input);
        hidden = isHidden;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        equation = input -> (float) Math.sin(input);
        findVirtualPoints(resolution);
        findRealPoints(resolution);
    }

    public String toString() {
        return "id: " + id + " | parented to grid" + parentGrid.id + " | is rendered? " + isRendered;
    }

    private void findVirtualPoints(int resolution) {
        // calculates the virtualPoints for the line from a given equation
        int size = resolution * parentGrid.numVertLines;
        virtualPoints = new Vector2[size];
        double step = (double) 1 / resolution;
        for(int i = 0; i < size; i++) {
            double input = i * step;
            float y = equation.apply(input);
            if (Float.isFinite(y) && i > 0) {
                float slope = (y - virtualPoints[i-1].y) / ((float) input - virtualPoints[i-1].x);
                if (Math.floor(y) > parentGrid.gridYMax) {
                    // finds x for a given y (the height of the grid) and point with equation x = (y-b-ma)/m
                    float x = (parentGrid.gridYMax - virtualPoints[i - 1].y + (slope * virtualPoints[i - 1].x)) / slope;
                    virtualPoints[i] = new Vector2(x, parentGrid.gridYMax);
                    continue;
                } else if (Math.ceil(y) < parentGrid.gridYMin) {
                    // finds x for a given y (the height of the grid) and point with equation x = (y-b-ma)/m
                    float x = (parentGrid.gridYMin - virtualPoints[i - 1].y + (slope * virtualPoints[i - 1].x)) / slope;
                    virtualPoints[i] = new Vector2(x, parentGrid.gridYMin);
                    continue;
                }
            }
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
        if(!hidden) {
            // translates virtualPoints to a grid
            shapeDrawer.getBatch().begin();
            shapeDrawer.setColor(Color.RED);
            for (int i = 0; i < realPoints.length - 1; i++) {
                float slope = (virtualPoints[i + 1].y - virtualPoints[i].y) / (virtualPoints[i + 1].x - virtualPoints[i].x);
                //Gdx.app.debug("Diff", "" + Math.abs(derive(virtualPoints[i].x) - slope) + " at " + virtualPoints[i].x);
                if (Float.isFinite(realPoints[i].y) && Math.abs(derive(virtualPoints[i].x) - slope) < 1000) {
                    shapeDrawer.line(realPoints[i], realPoints[i + 1]);
                }
            }
            shapeDrawer.getBatch().end();
            isRendered = true;
        }
    }

    private double derive(double x) {
        return (equation.apply(x + 0.0001) - equation.apply(x))*10000;
    }

    public void throwToAI(TestAI ai){
        ai.addPoints(realPoints);
    }
}
