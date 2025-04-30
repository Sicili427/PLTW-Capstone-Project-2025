
package io.github.pltwgame.systems;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.pltwgame.components.CombatComponent;
import io.github.pltwgame.components.HealthComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.SpriteComponent;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({PositionComponent.class, SpriteComponent.class})
public class SpriteSystem extends IteratingSystem {
    private ComponentMapper<PositionComponent> pm;
    private ComponentMapper<SpriteComponent> sm;
    private ComponentMapper<HealthComponent> hm;
    private ComponentMapper<CombatComponent> cm;

    private final SpriteBatch batch;
    private final ShapeDrawer shapeDrawer;

    public SpriteSystem(ShapeDrawer shapeDrawer) {
        this.batch = new SpriteBatch();
        this.shapeDrawer = shapeDrawer;
    }

    @Override
    protected void begin() {
        shapeDrawer.getBatch().begin();
        batch.begin();
    }

    @Override
    protected void process(int entityId) {
        PositionComponent position = pm.get(entityId);
        SpriteComponent sprite = sm.get(entityId);

        if(cm.has(entityId)){
            CombatComponent combat = cm.get(entityId);
            Color color = new Color(0.5f,0.5f,0.5f,0.5f);

            shapeDrawer.filledCircle(position.x, position.y, combat.detectionRange, color);
            shapeDrawer.filledCircle(position.x, position.y, combat.range, color);
        }

        sprite.sprite.setColor(Color.WHITE);
        sprite.sprite.setScale(sprite.scale);
        sprite.sprite.setPosition(position.x - 0.5f * sprite.sprite.getWidth(), position.y - 0.5f * sprite.sprite.getHeight());
        sprite.sprite.setRotation(position.angle);
        sprite.sprite.draw(batch);

        if(hm.has(entityId)){
            HealthComponent health = hm.get(entityId);
            float width = 50;
            float height = 7.5f;
            if(health.health < health.maxHealth){
                shapeDrawer.filledRectangle(position.x - .5f * width, position.y - sprite.sprite.getHeight(), width, height, Color.GRAY);
                if(health.invincible){
                    shapeDrawer.filledRectangle(position.x - .5f * width, position.y - sprite.sprite.getHeight(), width * health.health/health.maxHealth, height, Color.YELLOW);
                } else{
                    shapeDrawer.filledRectangle(position.x - .5f * width, position.y - sprite.sprite.getHeight(), width * health.health/health.maxHealth, height, Color.GREEN);
                }
            }
        }
    }

    @Override
    protected void end() {
        shapeDrawer.getBatch().end();
        batch.end();
    }

    @Override
    protected void dispose() {
        batch.dispose();
        shapeDrawer.getBatch().dispose();
    }
}
