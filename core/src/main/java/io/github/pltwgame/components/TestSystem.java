package io.github.pltwgame.components;

import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;

@All({DrawInfo.class, AIInfo.class})
public class TestSystem extends IteratingSystem {

    @Override
    protected void process(int entityId) {
        Gdx.app.debug("Working", "Yes");
    }
}
