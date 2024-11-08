package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.Arrays;
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
            /* if (Float.isFinite(y) && i > 0) {
                Vector2 prevVector = virtualPoints[i-1];
                float slope = (y - prevVector.y) / ((float) input - prevVector.x);
                if (Math.floor(y) > parentGrid.gridYMax) {
                    // finds x for a given y (the height of the grid) and point with equation x = (y-b-ma)/m
                    float x = (parentGrid.gridYMax - prevVector.y + (slope * prevVector.x)) / slope;
                    virtualPoints[i] = new Vector2(x, parentGrid.gridYMax);
                    continue;
                } else if (Math.ceil(y) < parentGrid.gridYMin) {
                    // finds x for a given y (the height of the grid) and point with equation x = (y-b-ma)/m
                    float x = (parentGrid.gridYMin - prevVector.y + (slope * prevVector.x)) / slope;
                    virtualPoints[i] = new Vector2(x, parentGrid.gridYMin);
                    continue;
                }
            } */
            virtualPoints[i] = new Vector2((float) input, y);
        }
        Gdx.app.debug("virtualPoints", Arrays.toString(virtualPoints));
    }

    private void findRealPoints(int resolution) {
        int size = resolution * parentGrid.numVertLines;
        realPoints = new Vector2[size];
        for (int i = 0; i < size; i++) {
            if (Float.isFinite(virtualPoints[i].y)) {
                Vector2 currentVector = virtualPoints[i];

                float x = 0;
                float y = 0;

                int maxBound = parentGrid.numHorzLines - parentGrid.originOffsetY;
                int maxIndex = parentGrid.numHorzLines;

                if(currentVector.y < 0) {
                    maxBound = parentGrid.originOffsetY;
                    maxIndex = 0;
                }

                if(isPointInGrid(currentVector)) {
                    x = parentGrid.vertLines[(int) virtualPoints[i].x + parentGrid.originOffsetX].x + (virtualPoints[i].x - (int) virtualPoints[i].x) * parentGrid.CellX;
                    y = parentGrid.horzLines[(int) virtualPoints[i].y + parentGrid.originOffsetY].y + (virtualPoints[i].y - (int) virtualPoints[i].y) * parentGrid.CellY;
                } else if (i > 0 && i < size-1){
                    Vector2 prevVector = virtualPoints[i-1];
                    Vector2 nextVector = virtualPoints[i+1];

                    if(isPointInGrid(prevVector) || isPointInGrid(nextVector)){d
                        float slope = findSlope(currentVector, nextVector);
                        // finds x for a given y (the height of the grid) and point with equation x = (y-b+ma)/m
                        float tempX = (maxBound - nextVector.y + (slope * nextVector.x)) / slope;

                        x = parentGrid.vertLines[(int) tempX + parentGrid.originOffsetX].x + (tempX - (int) tempX) * parentGrid.CellX;
                        y = parentGrid.horzLines[maxIndex].y;
                    } else {
                        realPoints[i] = new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
                        continue;
                    }
                }
                realPoints[i] = new Vector2(x, y);
            }
            else {
                realPoints[i] = virtualPoints[i];
            }
        }

        Gdx.app.debug("realPoints", Arrays.toString(realPoints));
    }

    public void generateLine() {
        if(!hidden) {
            // renders realPoints to a grid
            shapeDrawer.getBatch().begin();
            shapeDrawer.setColor(Color.RED);
            for (int i = 0; i < realPoints.length - 1; i++) {
                float slope = findSlope(realPoints[i], realPoints[i+1]);
                if (Float.isFinite(realPoints[i].y) && Float.isFinite(realPoints[i+1].y) && Math.abs(derive(virtualPoints[i].x) - slope) < 1000) {
                    shapeDrawer.line(realPoints[i], realPoints[i + 1]);
                }
            }
            shapeDrawer.getBatch().end();
            isRendered = true;
        }
    }

    public float derive(double x) {
        return (equation.apply(x + 0.0001) - equation.apply(x))*10000;
    }

    public boolean isPointInGrid(Vector2 point){
        return (int) point.y < parentGrid.gridYMax && (int) point.y > parentGrid.gridYMin;
    }

    public float findSlope(Vector2 point1, Vector2 point2) {
        return (point2.y - point1.y) / (point2.x - point1.x);
    }

    public void throwToAI(TestAI ai){
        ai.addPoints(realPoints);
    }
}
