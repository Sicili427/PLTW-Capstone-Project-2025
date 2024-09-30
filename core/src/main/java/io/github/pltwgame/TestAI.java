
package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class TestAI {
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = Math.round((float) (9 * SCREEN_WIDTH) / 16);
    float aiX;
    float aiY;
    float moveSpeed = 5;
    ArrayList<Vector2> stack = new ArrayList<>();

    public TestAI(float x, float y) {
        aiX = x;
        aiY = y;
        addPoint(0, 0, false);
        addPoint(SCREEN_WIDTH / 2, SCREEN_HEIGHT / 2, false);
    }

    public void drawAI(ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.GREEN);
        renderer.circle(aiX, aiY, 50);
        renderer.end();
    }

    public void setPos(float x, float y) {
        aiX = x;
        aiY = y;
    }

    public void setPos(Vector2 vector2) {
        aiX = vector2.x;
        aiY = vector2.y;
    }

    public void addPoint(float x, float y, boolean mouse) {
        Vector2 vector2 = new Vector2(x, y);
        if (mouse) adjustMouseVector(vector2);
        stack.add(vector2);
    }
    public void addPoint(Vector2 vector2, boolean mouse) {
        if (mouse) adjustMouseVector(vector2);
        stack.add(vector2);
    }

    public void moveToPoint() {
        if (!stack.isEmpty()) {
            setPos(findNextPoint());
        }
    }

    public void moveToPoint(Vector2 pointToGo) {
        if (!stack.isEmpty()) {
            setPos(findNextPoint(pointToGo));
        }
    }

    public Vector2 findNextPoint() {
        if (stack.isEmpty()) return new Vector2(aiX, aiY);

        Vector2 target = stack.get(0);
        float angleTo = findAngle(target);

        float finalX = (float) (aiX + moveSpeed * Math.cos(angleTo));
        float finalY = (float) (aiY + moveSpeed * Math.sin(angleTo));

        if (aiX < target.x + moveSpeed && aiY < target.y + moveSpeed && aiX > target.x - moveSpeed && aiY > target.y - moveSpeed) {
            stack.remove(0);
        }

        return new Vector2(finalX, finalY);
    }

    public Vector2 findNextPoint(Vector2 pointToGo) {
        adjustMouseVector(pointToGo);
        float angleTo = findAngle(pointToGo);

        float finalX = (float) (aiX + moveSpeed * Math.cos(angleTo));
        float finalY = (float) (aiY + moveSpeed * Math.sin(angleTo));

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

    public void addPoints(Vector2[] points){
        for (Vector2 point : points) {
            addPoint(point, false);
        }
    }
}
