
package io.github.pltwgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.*;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.pltwgame.components.*;
import io.github.pltwgame.gameCore.GameWorld;

@All({VelocityComponent.class, PositionComponent.class})
public class MovementSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<LineComponent> lm;
    private ComponentMapper<FollowComponent> fm;
    private ComponentMapper<SpriteComponent> sm;
    private ComponentMapper<HealthComponent> hm;
    private ComponentMapper<TeamComponent> tm;
    private ComponentMapper<CombatComponent> cm;

    private GameWorld gameWorld;

    public MovementSystem(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }

    @Override
    protected void process(int entityId) {
        if(fm.has(entityId)){
            FollowComponent follow = fm.get(entityId);

            PositionComponent targPos = pm.get(follow.target);

            if(targPos == null) return;

            follow.targX = targPos.x;
            follow.targY = targPos.y;
            moveToTarget(entityId,follow.targX, follow.targY, follow.stopRadius, getWorld().delta);
        } else{
            followLine(entityId, world.getDelta());
        }

        checkBounds(entityId);
    }

    private void followLine(int entityId, float delta) {
        LineComponent line = lm.get(entityId);
        PositionComponent position = pm.get(entityId);
        VelocityComponent velocity = vm.get(entityId);

        if (line == null || line.path == null || line.path.size() == 0) return;

        float targetX = line.path.get(line.currentIndex).x;
        float targetY = line.path.get(line.currentIndex).y;

        Vector2 difference = getDifference(position.x, position.y, targetX, targetY);
        float distance = difference.len();

        position.angle = difference.angleDeg() - 90;

        if(distance < velocity.speed * delta){
            while(distance < velocity.speed * delta && line.currentIndex < line.path.size() - 1){
                line.currentIndex++;
                targetX = line.path.get(line.currentIndex).x;
                targetY = line.path.get(line.currentIndex).y;

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

    private void checkBounds(int entityId){
        PositionComponent position = pm.get(entityId);
        HealthComponent health = hm.get(entityId);
        TeamComponent team = tm.get(entityId);
        CombatComponent combat = cm.get(entityId);
        LineComponent line = lm.get(entityId);

        if(line.currentIndex == line.path.size() - 1){
            if(team.team.equals("red") && position.x < 33){
                int damage = (combat.baseDamage * health.health/health.maxHealth);
                if(gameWorld.currentBaseHealth - damage < 0){
                    gameWorld.currentBaseHealth = 0;
                } else {
                    gameWorld.currentBaseHealth -= damage;
                }
            }
            health.health = 0;
        } else if (position.y < 216 || position.y > 720){
            health.health = 0;
        }
    }
}
