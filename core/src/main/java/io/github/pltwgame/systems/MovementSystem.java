package io.github.pltwgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.*;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import io.github.pltwgame.components.LineComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.VelocityComponent;

@All({VelocityComponent.class, PositionComponent.class})
public class MovementSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<VelocityComponent> vm;
    private ComponentMapper<LineComponent> fm;

    public MovementSystem() {
    }

    @Override
    protected void process(int entityId) {
        PositionComponent pos = pm.get(entityId);
        VelocityComponent vel = vm.get(entityId);
        LineComponent line = fm.get(entityId);

        followLine(pos, vel, line, world.getDelta());
    }

    private void wander() {

    }

    private void followLine(PositionComponent position, VelocityComponent movement, LineComponent line, float deltaTime) {
        if (line == null || line.path == null || line.path.length == 0) return;

        Vector2 currentPosition = new Vector2(position.x, position.y);
        Vector2 target = line.path[line.currentIndex];

        movement.direction = new Vector2(target).sub(currentPosition);
        float distance = movement.direction.len();

        if (distance < movement.speed * deltaTime) {
            // Move directly to the target instead of overshooting
            position.x = target.x;
            position.y = target.y;
            line.currentIndex++;

            if (line.currentIndex >= line.path.length) {
                line.currentIndex = line.path.length - 1;
            }
        } else {
            // Move normally
            movement.direction.nor();
            position.x += movement.direction.x * movement.speed * deltaTime;
            position.y += movement.direction.y * movement.speed * deltaTime;
        }

        position.angle = movement.direction.angleDeg() - 90;
    }
}
