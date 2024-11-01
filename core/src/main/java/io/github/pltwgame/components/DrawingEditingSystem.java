package io.github.pltwgame.components;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;

@All({DrawInfo.class, AIInfo.class})
public class DrawingEditingSystem extends IteratingSystem {
    ComponentMapper<DrawInfo> mDrawInfo;
    ComponentMapper<AIInfo> mAIInfo;
    @Override
    protected void process(int entityId) {
        DrawInfo drawInfo = mDrawInfo.create(entityId);
        AIInfo aiInfo = mAIInfo.create(entityId);
        drawInfo.radius = aiInfo.ai.radius;
        drawInfo.posx = aiInfo.ai.xPos;
        drawInfo.posy = aiInfo.ai.yPos;
    }

}
