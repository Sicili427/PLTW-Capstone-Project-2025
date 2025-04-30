
package io.github.pltwgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.components.HealthComponent;
import io.github.pltwgame.components.LineComponent;
import io.github.pltwgame.components.PositionComponent;

@All({HealthComponent.class})
public class HealthSystem extends IteratingSystem {
    private ComponentMapper<HealthComponent> hm;
    private ComponentMapper<LineComponent> lm;
    private ComponentMapper<PositionComponent> pm;
    private Grid grid;

    public HealthSystem() {
    }

    public HealthSystem(Grid grid){
        this.grid = grid;
    }

    @Override
    protected void process(int entityId) {
        HealthComponent health = hm.get(entityId);
        PositionComponent position = pm.get(entityId);

        if(position.y > 720 || position.y < 216){
            health.health = 0;
        }

        if (health.health <= 0) {
            Gdx.app.debug("Death", "Entity " + entityId + " has died");
            world.delete(entityId);
            if (lm.has(entityId)) {
                LineComponent line = lm.get(entityId);
                grid.removeLine(line.lineId);
            }
        }
    }
}
