package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;
import org.mariuszgromada.math.mxparser.*;

import java.util.Arrays;

public class Line{

    public static int lineIndex = 0;

    ShapeDrawer shapeDrawer;

    Grid parentGrid;

    String id;

    boolean isRendered = false;
    boolean hidden = false;

    Function function;

    Vector2[] virtualPoints;
    Vector2[] realPoints;

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, Function initFunction, boolean isHidden) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = initFunction;
        hidden = isHidden;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, Function initFunction ) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = initFunction;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, boolean isHidden) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = new Function("f", "sin(x)", "x");
        hidden = isHidden;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = new Function("f", "sin(x)", "x");
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
            float y = (float) function.calculate(input);
            virtualPoints[i] = new Vector2((float) input, y);
        }
        Gdx.app.debug("virtualPoints", Arrays.toString(virtualPoints));
    }

    private void findRealPoints(int resolution) {
        int size = resolution * parentGrid.numVertLines;
        realPoints = new Vector2[size];

        int maxBoundPositive = parentGrid.numHorzLines - parentGrid.originOffsetY;
        int maxBoundNegative = parentGrid.originOffsetY;

        for (int i = 0; i < size; i++) {
            Vector2 currentVector = virtualPoints[i];

            if (!Float.isFinite(currentVector.y)) {
                realPoints[i] = currentVector;
                continue;
            }

            int maxBound = (currentVector.y < 0) ? maxBoundNegative : maxBoundPositive;

            if(isPointInGrid(currentVector)) {
                    realPoints[i] = virtualToReal(currentVector);
            } else if (i > 0 && i < size-1){
                Vector2 prevVector = virtualPoints[i-1];
                Vector2 nextVector = virtualPoints[i+1];

                realPoints[i] = calculateRealPointWhenOutOfBounds(currentVector, prevVector, nextVector, maxBound);
            } else {
                realPoints[i] = new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
            }
        }
        Gdx.app.debug("realPoints", Arrays.toString(realPoints));
    }

    public void generateLine() {
        if(hidden) {
            return;
        }
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

    private float derive(double x) {
        return (float) (function.calculate(x + 0.0001) - function.calculate(x))*10000;
    }

    private boolean isPointInGrid(Vector2 point){
        return (int) point.y < parentGrid.gridYMax && (int) point.y > parentGrid.gridYMin;
    }

    private float findSlope(Vector2 point1, Vector2 point2) {
        return (point2.y - point1.y) / (point2.x - point1.x);
    }

    private Vector2 virtualToReal(float inputX, float inputY) {
        float x = parentGrid.vertLines[(int) inputX + parentGrid.originOffsetX].x + (inputX - (int) inputX) * parentGrid.CellX;
        float y = parentGrid.horzLines[(int) inputY + parentGrid.originOffsetY].y + (inputY - (int) inputY) * parentGrid.CellY;

        return new Vector2(x,y);
    }

    private Vector2 virtualToReal(Vector2 inputVector) {
        float x = parentGrid.vertLines[(int) inputVector.x + parentGrid.originOffsetX].x + (inputVector.x - (int) inputVector.x) * parentGrid.CellX;
        float y = parentGrid.horzLines[(int) inputVector.y + parentGrid.originOffsetY].y + (inputVector.y - (int) inputVector.y) * parentGrid.CellY;

        return new Vector2(x,y);
    }

    private Vector2 calculateRealPointWhenOutOfBounds(Vector2 currentVector, Vector2 prevVector, Vector2 nextVector, int bound){
        if(isPointInGrid(prevVector)){
            float slope = findSlope(currentVector, prevVector);
            // finds x for a given y (the height of the grid) and point with equation x = (y-b+ma)/m
            float tempX = (bound - prevVector.y + (slope * prevVector.x)) / slope;
            return virtualToReal(tempX, bound);
        } else if (isPointInGrid(nextVector)) {
            float slope = findSlope(currentVector, nextVector);
            // finds x for a given y (the height of the grid) and point with equation x = (y-b+ma)/m
            float tempX = (bound + nextVector.y + (slope * nextVector.x)) / slope;

            return virtualToReal(tempX, -bound);
        } else {
            return new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        }
    }

    public void throwToAI(TestAI ai){
        ai.addPoints(realPoints);
    }
}
