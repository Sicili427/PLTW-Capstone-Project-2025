
package io.github.pltwgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pltwgame.components.HealthComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.SpriteComponent;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({PositionComponent.class, SpriteComponent.class})
public class SpriteSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<SpriteComponent> sm;
    private ComponentMapper<HealthComponent> hm;
    private final SpriteBatch batch;
    private final ShapeDrawer shapeDrawer;

    public SpriteSystem(ShapeDrawer shapeDrawer) {
        this.batch = new SpriteBatch();
        this.shapeDrawer = shapeDrawer;
    }

    @Override
    protected void begin() {
        batch.begin();
        shapeDrawer.getBatch().begin();
    }

    @Override
    protected void process(int entityId) {
        PositionComponent position = pm.get(entityId);
        SpriteComponent sprite = sm.get(entityId);

        sprite.sprite.draw(batch);
        sprite.sprite.setScale(sprite.scale);
        sprite.sprite.setPosition(position.x, position.y);
        sprite.sprite.setRotation(position.angle);

        if(hm.has(entityId)){
            HealthComponent health = hm.get(entityId);
            if(health.health < health.maxHealth){
                shapeDrawer.filledRectangle((float) (position.x - (health.maxHealth * 0.1)), position.y - sprite.sprite.getHeight() - 10, (float) (health.maxHealth * 0.2), 30, Color.GRAY);
                if(health.invincible){
                    shapeDrawer.filledRectangle((float) (position.x - (health.maxHealth * 0.1)), position.y - sprite.sprite.getHeight() - 10, (float) (health.health * 0.2), 30, Color.YELLOW);
                } else{
                    shapeDrawer.filledRectangle((float) (position.x - (health.maxHealth * 0.1)), position.y - sprite.sprite.getHeight() - 10, (float) (health.health * 0.2), 30, Color.GREEN);
                }
            }
        }
    }

    @Override
    protected void end() {
        batch.end();
        shapeDrawer.getBatch().end();
    }

    @Override
    protected void dispose() {
        batch.dispose();
        shapeDrawer.getBatch().dispose();
    }
}
