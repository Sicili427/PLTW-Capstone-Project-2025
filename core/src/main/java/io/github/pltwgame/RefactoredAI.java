package io.github.pltwgame;

import com.artemis.World;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.ArrayList;


public class RefactoredAI {
    World world;


    float moveSpeed = 5;
    ArrayList<Vector2> stack = new ArrayList<>();
    final int maxBoundX;
    final int maxBoundY;

    float xPos;
    float yPos;

    ArrayList<Vector2> points = new ArrayList<>();
    int lineIndex;

    public RefactoredAI(float x, float y, int boundX, int boundY, int lineindex) {
        maxBoundX = boundX;
        maxBoundY = boundY;
        xPos = x;
        yPos = y;
        lineIndex = lineindex;
    }

    public void drawAI (ShapeDrawer renderer){
        renderer.getBatch().begin();
        renderer.setColor(Color.BLUE);
        renderer.circle(xPos, yPos, 10);
        renderer.getBatch().end();
    }

    public void setPos (float x, float y){
        xPos = x;
        yPos = y;
    }

    public void setPos (Vector2 vector2) {
        xPos = vector2.x;
        yPos = vector2.y;
    }

    public void addPoint (float x, float y, boolean mouse){
        Vector2 vector2 = new Vector2(x, y);
        if (mouse) adjustMouseVector(vector2);
        points.add(vector2);
    }
    public void addPoint(Vector2 vector2, boolean mouse) {
        if (mouse) adjustMouseVector(vector2);
        points.add(vector2);
    }

    public void moveToPoint (){
        if (!points.isEmpty()) {
            setPos(findNextPoint());
        }
    }

    public void moveToPoint (Vector2 pointtogo){
        if (!points.isEmpty()) {
            setPos(findNextPoint(pointtogo));
        }
    }

    public Vector2 findNextPoint() {
        if (points.isEmpty()) return new Vector2(xPos, yPos);

        Vector2 target = points.get(0);
        float distance = target.dst(xPos, yPos);

        // Skip points that are too close
        while (distance < moveSpeed && !points.isEmpty()) {
            points.remove(0);
            if (!points.isEmpty()) {
                target = points.get(0);
                distance = target.dst(xPos, yPos);
            }
        }

        float alpha = Math.min(1, moveSpeed / distance); // Dynamic interpolation factor

        xPos = lerp(xPos, target.x, alpha);
        yPos = lerp(yPos, target.y, alpha);

        return new Vector2(xPos, yPos);
    }

    public Vector2 findNextPoint(Vector2 pointToGo) {
        adjustMouseVector(pointToGo);
        float distance = pointToGo.dst(xPos, yPos);
        float alpha = Math.min(1, moveSpeed / distance); // Dynamic interpolation factor

        xPos = lerp(xPos, pointToGo.x, alpha);
        yPos = lerp(yPos, pointToGo.y, alpha);

        return new Vector2(xPos, yPos);
    }

    private float findAngle(Vector2 point1) {
        return (float) Math.atan2(point1.y - yPos, point1.x - xPos);
    }

    private void adjustMouseVector(Vector2 adjustable) {
        adjustable.x -= xPos;
        adjustable.y = maxBoundY - adjustable.y;
        adjustable.y -= yPos;
    }

    private void deAdjustMouseVector(Vector2 adjustable) {
        adjustable.x += xPos;
        adjustable.y = maxBoundY + adjustable.y;
        adjustable.y += yPos;
    }

    private float lerp(float start, float end, float alpha) {

        return start + alpha * (end - start);
    }

    public void addPoints(Vector2[] points){
        for (Vector2 point : points) {
            if(point != null) {
                addPoint(point, false);
            }
        }
    }
}

