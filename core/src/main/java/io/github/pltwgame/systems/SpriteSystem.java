package io.github.pltwgame.systems;


import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.SpriteComponent;

@All({PositionComponent.class, SpriteComponent.class})
public class SpriteSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<SpriteComponent> sm;
    private SpriteBatch batch;

    public SpriteSystem() {
        batch = new SpriteBatch();
    }

    @Override
    protected void begin() {
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        PositionComponent position = pm.get(entityId);
        SpriteComponent sprite = sm.get(entityId);

        batch.draw(sprite.texture, position.x, position.y, 32, 32); // Adjust size as needed
    }

    @Override
    protected void end() {
        batch.end();
    }

    @Override
    protected void dispose() {
        batch.dispose();
    }
}
