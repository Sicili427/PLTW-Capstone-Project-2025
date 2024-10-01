package io.github.pltwgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Function;

public class Line{

    public static int lineIndex = 0;

    ShapeRenderer shapeRenderer;

    Grid parentGrid;

    String id;

    Vector2[] points;

    public Line(ShapeRenderer initRenderer, Grid initGrid, String initId, int resolution, Function<Double, Float> equation) {
        shapeRenderer = initRenderer;
        parentGrid = initGrid;
        id = initId;
        lineIndex++;
        // calculates the points for the line from a given equation
        int size = resolution * parentGrid.numVertLines;
        points = new Vector2[size];
        for(int i = 0; i < size; i++) {
            double input = i / (double) resolution;
            float y = equation.apply(input);
            points[i] = new Vector2((float) input, y);
        }
    }

    public Line(ShapeRenderer initRenderer, Grid initGrid, String initId, int resolution) {
        shapeRenderer = initRenderer;
        parentGrid = initGrid;
        id = initId;
        lineIndex++;
        // calculates the points for the line from a given equation
        int size = resolution * parentGrid.numVertLines;
        points = new Vector2[size];
        for(int i = 0; i < size; i++) {
            double input = i / (double) resolution;
            float y = (float) Math.sin(input);
            points[i] = new Vector2((float) input, y);
        }
    }

    public static int getLineIndex(){
        return lineIndex;
    }

    public void generateLine() {
        // translates points to a grid
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        for(int i = 0; i < points.length-1; i++) {
            if (Float.isFinite(points[i].y)) {
                if (Math.floor(points[i].y) > parentGrid.horzLines.length - parentGrid.originOffsetY || Math.floor(points[i + 1].y) > parentGrid.horzLines.length - parentGrid.originOffsetY) {
                    continue;
                } else if (Math.floor(Math.abs(points[i].y)) > parentGrid.originOffsetY || Math.floor(Math.abs(points[i + 1].y)) > parentGrid.originOffsetY) {
                    continue;
                }

                float x1 = parentGrid.vertLines[(int) points[i].x + parentGrid.originOffsetX].x + (points[i].x - (int) points[i].x) * parentGrid.CellX;
                float y1 = parentGrid.horzLines[(int) points[i].y + parentGrid.originOffsetY].y + (points[i].y - (int) points[i].y) * parentGrid.CellY;

                float x2 = parentGrid.vertLines[(int) points[i + 1].x + parentGrid.originOffsetX].x + (points[i + 1].x - (int) points[i + 1].x) * parentGrid.CellX;
                float y2 = parentGrid.horzLines[(int) points[i + 1].y + parentGrid.originOffsetY].y + (points[i + 1].y - (int) points[i + 1].y) * parentGrid.CellY;

                shapeRenderer.line(x1, y1, x2, y2);
            }
        }
        shapeRenderer.end();
    }
}
