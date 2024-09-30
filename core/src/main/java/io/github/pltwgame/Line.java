package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Function;

public class Line{

    Grid parentGrid;

    Vector2[] points;

    ShapeRenderer shapeRenderer = new ShapeRenderer();

    public Line(Grid initGrid, Function<Double, Float> equation) {
        parentGrid = initGrid;
        int resolution = 100;
        int size = parentGrid.numVertLines * resolution;
        points = new Vector2[size];

        for(int i = 0; i < size; i++) {
            double input = i / (double) resolution;
            float y = equation.apply(input);
            points[i] = new Vector2((float) input, y);
        }
    }


    public void generateLine() {
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
