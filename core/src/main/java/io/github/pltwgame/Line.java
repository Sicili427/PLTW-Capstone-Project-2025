package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;
import org.mariuszgromada.math.mxparser.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Line{

    public static int lineIndex = 0;

    ShapeDrawer shapeDrawer;

    Grid parentGrid;

    String id;

    Color color;

    boolean isRendered = false;
    boolean hidden = false;

    Function function;

    Vector2[] virtualPoints;
    Vector2[] realPoints;

    ArrayList<Vector2[]> linePoints;

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, Function initFunction, boolean isHidden) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = initFunction;
        color = new Color((float) Math.random(),(float) Math.random(),(float) Math.random(), 1);
        hidden = isHidden;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
        findLines();
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, Function initFunction ) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = initFunction;
        color = new Color((float) (Math.random() * 0.5 + 0.25),(float) (Math.random() * 0.5 + 0.25),(float) (Math.random() * 0.5 + 0.25), 1);;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
        findLines();
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution, boolean isHidden) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = new Function("f", "sin(x)", "x");
        color = new Color((float) Math.random() * 255,(float) Math.random() * 255,(float) Math.random() * 255, 1);
        hidden = isHidden;
        findVirtualPoints(resolution);
        findRealPoints(resolution);
        findLines();
    }

    public Line(ShapeDrawer initRenderer, Grid initGrid, int resolution) {
        shapeDrawer = initRenderer;
        parentGrid = initGrid;
        id = "line" + lineIndex;
        lineIndex++;
        function = new Function("f", "sin(x)", "x");
        color = new Color((float) Math.random() * 255,(float) Math.random() * 255,(float) Math.random() * 255, 1);
        findVirtualPoints(resolution);
        findRealPoints(resolution);
        findLines();
    }

    public String toString() {
        return "id: " + id + " | parented to grid" + parentGrid.id + " | equation: f(x) = " + function.getFunctionExpressionString() + " | is rendered? " + isRendered;
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
        int maxBoundNegative = parentGrid.originOffsetY * -1;

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
            } /* else {
                realPoints[i] = new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
            } */
        }
        Gdx.app.debug("realPoints", Arrays.toString(realPoints));
    }

    public void findLines() {
        linePoints = new ArrayList<>();

        // finds and stores pairs of points to draw line
        for(int i = 0; i < realPoints.length-1; i++) {
            if(!Float.isFinite(realPoints[i].y)) {
                continue;
            }

            float slope = findSlope(virtualPoints[i], virtualPoints[i+1]);
            float derivative = derive(virtualPoints[i].x);

            int difference = (int) Math.abs(derivative - slope);

            if(difference < 75) {
                Vector2[] line = {realPoints[i], realPoints[i+1]};
                linePoints.add(line);
            }
        }
    }

    public void renderLine() {
        if (hidden) {
            return;
        }
        // renders realPoints to a grid
        shapeDrawer.getBatch().begin();
        shapeDrawer.setColor(color);
        for (int i = 0; i < linePoints.size(); i++) {
            Vector2 point1 = linePoints.get(i)[0];
            Vector2 point2 = linePoints.get(i)[1];

            shapeDrawer.line(point1, point2);
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
        if(!isPointInGrid(nextVector) && !isPointInGrid(prevVector)) {
            return new Vector2(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        }

        float slope = findSlope(currentVector, prevVector);
        // finds x for a given y (the height of the grid) and point with equation x = (y-b+ma)/m
        float tempX = (bound - nextVector.y + (slope * nextVector.x)) / slope;
        return virtualToReal(tempX, bound);
    }

    public void throwToAI(TestAI ai){
        ai.addPoints(realPoints);
    }
}
