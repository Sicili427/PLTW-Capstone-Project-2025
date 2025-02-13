package io.github.pltwgame.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.*;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.pltwgame.components.FollowLineComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.VelocityComponent;
import io.github.pltwgame.components.WanderComponent;

@All({VelocityComponent.class, PositionComponent.class})
public class MovementSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<FollowLineComponent> fm;

    public MovementSystem() {
        super(Aspect.all(PositionComponent.class, VelocityComponent.class));
    }

    @Override
    protected void process(int entityId) {
        Entity entity = world.getEntity(entityId);

        PositionComponent pos = pm.get(entityId);
        VelocityComponent vel = vm.get(entityId);

        if (entity.getComponent(WanderComponent.class) != null){
            wander();
        } else {
            followLine(entityId, pos, vel);
        }
    }

    private void wander() {

    }

    private void followLine(int entityId, PositionComponent position, VelocityComponent movement) {
        FollowLineComponent followLine = fm.get(entityId);

        if (followLine.path == null || followLine.path.length == 0) return;

        Vector2 currentPosition = new Vector2(position.x, position.y);
        Vector2 target = followLine.path[followLine.currentIndex];

        float distance = currentPosition.dst(target);
        if (distance < movement.speed * world.getDelta()) {
            followLine.currentIndex++;
            if (followLine.currentIndex >= followLine.path.length) {
                followLine.currentIndex = followLine.path.length - 1; // Stay at last point
                return;
            }
            target = followLine.path[followLine.currentIndex];
        }

        Vector2 direction = new Vector2(target).sub(currentPosition).nor();
        position.x += direction.x * movement.speed * world.getDelta();
        position.y += direction.y * movement.speed * world.getDelta();
    }
}
