
package io.github.pltwgame.gameCore;

import com.artemis.Entity;
import com.artemis.World;
import io.github.pltwgame.components.LineComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.TeamComponent;
import io.github.pltwgame.loaders.EntityFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EntityHandler {
    World world;
    Grid grid;

    public ArrayList<String> bucket;
    public ArrayList<String> deck;

    public EntityHandler(World world, Grid grid, String[] deck) {
        this.world = world;
        this.grid = grid;
        this.deck = new ArrayList<>(Arrays.asList(deck));
    }

    // generates bucket of units with the first two slots in deck occuring twice
    public ArrayList<String> generateBucket(int size){
        int count = 0;
        ArrayList<String> temp = new ArrayList<>();

        for(int i = 0; i < size; i++){
            if(count == deck.size()){
                count = 0;
            }
            temp.add(deck.get(count));
            count++;
        }

        Collections.shuffle(temp);
        bucket = temp;

        return bucket;
    }

    public Entity place(String jsonString, Line line){
        Entity entity = EntityFactory.createEntityFromJson(world, jsonString + ".json");

        PositionComponent pc = entity.edit().create(PositionComponent.class);
        pc.x = line.linePoints.get(0)[0].x;
        pc.y = line.linePoints.get(0)[1].y;

        LineComponent lc = entity.edit().create(LineComponent.class);
        lc.lineId = line.id;
        lc.path = line.trimmedRealPoints;

        TeamComponent tc = entity.edit().create(TeamComponent.class);
        tc.team = "blue";

        return entity;
    }
}
