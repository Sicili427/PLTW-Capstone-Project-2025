package io.github.pltwgame.systems;

import com.artemis.*;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import io.github.pltwgame.components.HealthComponent;


public class HealthSystem extends IteratingSystem {
    private ComponentMapper<HealthComponent> hm;

    public HealthSystem() {
        super(Aspect.all(HealthComponent.class));
    }

    @Override
    protected void process(int entityId) {
        HealthComponent health = hm.get(entityId);
        health.health -= 1;

        if(health.health <= 0) {
            Gdx.app.debug("Death", "Entity " + entityId + " has died");
            world.delete(entityId);
        }
    }
}


