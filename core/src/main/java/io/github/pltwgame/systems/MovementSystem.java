
package io.github.pltwgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.*;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import io.github.pltwgame.components.*;

@All({VelocityComponent.class, PositionComponent.class})
public class MovementSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<LineComponent> lm;
    private ComponentMapper<FollowComponent> fm;
    private ComponentMapper<SpriteComponent> sm;

    public MovementSystem() {
    }

    @Override
    protected void process(int entityId) {
        LineComponent line = lm.get(entityId);

        if(fm.has(entityId)){
            FollowComponent follow = fm.get(entityId);
            SpriteComponent sprite = sm.get(entityId);

            PositionComponent targPos = pm.get(follow.target);

            follow.targX = targPos.x;
            follow.targY = targPos.y;
            moveToTarget(entityId,follow.targX, follow.targY, 0, getWorld().delta);
        } else{
            followLine(entityId, world.getDelta());
        }
    }

    private void wander() {

    }

    private void followLine(int entityId, float delta) {
        LineComponent line = lm.get(entityId);
        PositionComponent position = pm.get(entityId);
        VelocityComponent velocity = vm.get(entityId);

        if (line == null || line.path == null || line.path.length == 0) return;

        float targetX = line.path[line.currentIndex].x;
        float targetY = line.path[line.currentIndex].y;

        Vector2 difference = getDifference(position.x, position.y, targetX, targetY);
        float distance = difference.len();

        position.angle = difference.angleDeg() - 90;

        if(distance < velocity.speed * delta){
            while(distance < velocity.speed * delta){
                line.currentIndex++;
                targetX = line.path[line.currentIndex].x;
                targetY = line.path[line.currentIndex].y;

                difference = getDifference(position.x, position.y, targetX, targetY);
                distance = difference.len();

                position.angle = difference.angleDeg() - 90;
            }

            position.x = targetX;
            position.y = targetY;
        } else if (distance < 0.001f) {
            position.x = targetX;
            position.y = targetY;
        }else{
            difference.nor();
            position.x += difference.x * velocity.speed * delta;
            position.y += difference.y * velocity.speed * delta;
        }
    }

    private boolean moveToTarget(int entityId, float x, float y, float stopRadius, float delta){
        PositionComponent position = pm.get(entityId);
        VelocityComponent velocity = vm.get(entityId);
        SpriteComponent sprite = sm.get(entityId);

        float radius = stopRadius;

        if(radius < sprite.sprite.getWidth() * sprite.sprite.getScaleX()){
            radius = sprite.sprite.getWidth() * sprite.sprite.getScaleX();
        }

        Vector2 difference = getDifference(position.x, position.y, x, y);
        float distance = difference.len();

        position.angle = difference.angleDeg() - 90;

        if(distance < velocity.speed * delta || distance < radius){
            return true;
        } else{
            difference.nor();
            position.x += difference.x * velocity.speed * delta;
            position.y += difference.y * velocity.speed * delta;
        }

        return false;
    }

    private Vector2 getDifference(float startX, float startY, float endX, float endY){
        Vector2 currentPosition = new Vector2(startX, startY);
        Vector2 targetPosition = new Vector2(endX,endY);

        return targetPosition.sub(currentPosition);
    }
}
