/*package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class TestAI {
    float moveSpeed = 5;
    ArrayList<Vector2> stack = new ArrayList<>();
    final int maxBoundX;
    final int maxBoundY;

    float xPos;
    float yPos;

    ArrayList<Vector2> points = new ArrayList<>();

    public TestAI (float x, float y, int boundX, int boundY) {
        maxBoundX = boundX;
        maxBoundY = boundY;
        xPos = x;
        yPos = y;
        addPoint(0,0, false);
        addPoint(maxBoundX/2, maxBoundY/2, false);

        addPoint(maxBoundX, 0, false);
        addPoint(0, maxBoundY, false);
    }

    public void drawAI (ShapeRenderer renderer){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.circle(xPos, yPos, 50);
        renderer.end();
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
        float angleTo = findAngle(target);


        float finalX = (float) (xPos + moveSpeed * Math.cos(angleTo));
        float finalY = (float) (yPos + moveSpeed * Math.sin(angleTo));

        while (xPos < target.x + moveSpeed && yPos < target.y + moveSpeed && xPos > target.x - moveSpeed && yPos > target.y - moveSpeed) {
            points.remove(0);
            if (points.isEmpty()) return new Vector2(xPos, yPos);
            target = points.get(0);
        }

        return new Vector2(finalX, finalY);
    }

    public Vector2 findNextPoint(Vector2 pointToGo) {
        adjustMouseVector(pointToGo);
        float angleTo = findAngle(pointToGo);

        float finalX = (float) (xPos + moveSpeed * Math.cos(angleTo));
        float finalY = (float) (yPos + moveSpeed * Math.sin(angleTo));

        return new Vector2(finalX, finalY);
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

    public void addPoints(Vector2[] points){
        for (Vector2 point : points) {
            if(point != null) {
                addPoint(point, false);
            }
        }
    }
}*/

package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class TestAI {
    float moveSpeed = 5;
    ArrayList<Vector2> stack = new ArrayList<>();
    final int maxBoundX;
    final int maxBoundY;

    float xPos;
    float yPos;

    ArrayList<Vector2> points = new ArrayList<>();
    int lineIndex;

    public TestAI (float x, float y, int boundX, int boundY, int lineindex) {
        maxBoundX = boundX;
        maxBoundY = boundY;
        xPos = x;
        yPos = y;
        lineIndex = lineindex;
    }

    public void drawAI (ShapeRenderer renderer){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.circle(xPos, yPos, 50);
        renderer.end();
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

