package io.github.pltwgame.components;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import space.earlygrey.shapedrawer.ShapeDrawer;

@All({DrawInfo.class, AIInfo.class})
public class DrawingSystem extends IteratingSystem {
    ComponentMapper<DrawInfo> mDrawInfo;
    ComponentMapper<AIInfo> mAIInfo;
    @Override
    protected void process(int entityId) {
        DrawInfo drawInfo = mDrawInfo.create(entityId);
        AIInfo aiInfo = mAIInfo.create(entityId);
        aiInfo.shapeDrawer.getBatch().begin();
        aiInfo.shapeDrawer.setColor(com.badlogic.gdx.graphics.Color.BLUE);
        aiInfo.shapeDrawer.circle(drawInfo.posx, drawInfo.posy, drawInfo.radius);
        aiInfo.shapeDrawer.getBatch().end();
    }
}


