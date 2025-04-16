package io.github.pltwgame.loaders;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
import io.github.pltwgame.components.*;

import com.artemis.Component;
import com.artemis.World;
import com.badlogic.gdx.utils.JsonValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EntityFactory {
    // Maps component name to function that returns a component from JSON
    private static final Map<String, Function<JsonValue, Component>> componentConstructors = new HashMap<>();

    // Register all known components here
    static {
        componentConstructors.put("SpriteComponent", SpriteComponent::fromJson);
        componentConstructors.put("VelocityComponent", VelocityComponent::fromJson);
        componentConstructors.put("HealthComponent", HealthComponent::fromJson);
    }

    public static Entity createEntityFromJson(World world, String jsonString) {
        JsonValue root = JsonLoader.getJson("/entities/" + jsonString);

        Entity entity = world.createEntity();

        for (JsonValue componentEntry = root.child; componentEntry != null; componentEntry = componentEntry.next) {
            String componentName = componentEntry.name();
            JsonValue componentData = componentEntry;

            Function<JsonValue, Component> constructor = componentConstructors.get(componentName);

            if (constructor != null) {
                Component component = constructor.apply(componentData);
                entity.edit().add(component);
            } else {
                Gdx.app.error("Unknown component", componentName);
            }
        }

        return entity;
    }
}
