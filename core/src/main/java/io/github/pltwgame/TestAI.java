package io.github.pltwgame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

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

    public Vector2 findNextPoint (){
        float finalX = 0;
        float finalY = 0;
        float angleTo = findAngle(stack.get(0));

            finalX = (float) (1*Math.cos(angleTo));

            finalY = (float) (1*Math.sin(angleTo));
            //DOESNT WORK YET NEED TO MAKE ACTUALLY ADJUSTED TO NEW POINT
            if(xPos < stack.get(0).x + 100 && yPos < stack.get(0).y + 100 && xPos > stack.get(0).x - 100 && yPos > stack.get(0).y - 100){
                stack.remove(0);
            }
            return stack.get(0);
    }

    public Vector2 findNextPoint (Vector2 pointToGo){
        float finalX = 0;
        float finalY = 0;
        adjustMouseVector(pointToGo);
        float angleTo = findAngle(pointToGo);

        finalX = (float) (1*Math.cos(angleTo));

        finalY = (float) (1*Math.sin(angleTo));
        return new Vector2(finalX+xPos, finalY+yPos);
    }

    private float findAngle (Vector2 point1){
        return point1.angleRad();
    }
    private void adjustMouseVector (Vector2 adjustable){
        adjustable.x -= xPos;
        adjustable.y = maxBoundY - adjustable.y;
        adjustable.y -= yPos;
    }
    private void deAdjustMouseVector (Vector2 adjustable) {
        adjustable.x += xPos;
        adjustable.y = maxBoundY + adjustable.y;
        adjustable.y += yPos;
    }
}
