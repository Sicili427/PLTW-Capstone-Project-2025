package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class TestAI {
    final int maxBoundX;
    final int maxBoundY;
    float xPos;
    float yPos;
    ArrayList<Vector2> stack = new ArrayList<>();

    public TestAI (float x, float y, int boundX, int boundY) {
        maxBoundX = boundX;
        maxBoundY = boundY;
        xPos = x;
        yPos = y;
        addPoint(0,0, false);
        addPoint(maxBoundX/2, maxBoundY/2, false);
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
        stack.add(vector2);
    }

    public void moveToPoint (){
        if (!stack.isEmpty()) {
            setPos(findNextPoint());
        }
    }

    public void moveToPoint (Vector2 pointtogo){
        if (!stack.isEmpty()) {
            setPos(findNextPoint(pointtogo));
        }
    }

    public Vector2 findNextPoint() {
        if (stack.isEmpty()) return new Vector2(aiX, aiY);

        Vector2 target = stack.get(0);
        float angleTo = findAngle(target);

        float finalX = (float) (aiX + 1 * Math.cos(angleTo));
        float finalY = (float) (aiY + 1 * Math.sin(angleTo));

        if (aiX < target.x + 1 && aiY < target.y + 1 && aiX > target.x - 1 && aiY > target.y - 1) {
            stack.remove(0);
        }

        return new Vector2(finalX, finalY);
    }

    public Vector2 findNextPoint(Vector2 pointToGo) {
        adjustMouseVector(pointToGo);
        float angleTo = findAngle(pointToGo);

        float finalX = (float) (aiX + 1 * Math.cos(angleTo));
        float finalY = (float) (aiY + 1 * Math.sin(angleTo));

        return new Vector2(finalX, finalY);
    }

    private float findAngle(Vector2 point1) {
        return (float) Math.atan2(point1.y - aiY, point1.x - aiX);
    }

    private void adjustMouseVector(Vector2 adjustable) {
        adjustable.x -= aiX;
        adjustable.y = SCREEN_HEIGHT - adjustable.y;
        adjustable.y -= aiY;
    }

    private void deAdjustMouseVector(Vector2 adjustable) {
        adjustable.x += aiX;
        adjustable.y = SCREEN_HEIGHT + adjustable.y;
        adjustable.y += aiY;
    }
}
