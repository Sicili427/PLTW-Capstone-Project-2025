package io.github.pltwgame.gameCore;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import io.github.pltwgame.components.LineComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.TeamComponent;
import io.github.pltwgame.loaders.EntityFactory;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameWorld {
    Grid grid;
    public World world;

    int maxBaseHealth = 100;
    public int currentBaseHealth = maxBaseHealth;

    int maxInk = 100;
    public float currentInk = maxInk;
    int inkPerSecond = 5;

    public EntityHandler entityHandler;
    ArrayList<String> deck;

    float enemyCooldown = 5;
    String[] enemyEquations = {"sin(x)", "cos(x)", "(x-64)", "(x-64)", "(x-64)", "(x-64)", "(x-64)", "ln(-(x-64))"};

    public GameWorld(Grid grid){
        this.grid = grid;
    }

    public void process(float delta){
        if(deck == null || deck.isEmpty()) {
            deck = entityHandler.generateBucket(8);
            Gdx.app.debug("deck", deck.toString() + "");
        }

        if(currentBaseHealth > 0) {
            if (currentInk < maxInk) {
                currentInk += inkPerSecond * delta;
            } else {
                currentInk = maxInk;
            }
        } else {
            currentBaseHealth = 0;
            currentInk = 0;
        }

        if(enemyCooldown > 0){
            enemyCooldown -= delta;
        } else{
            spawnEnemy();
            enemyCooldown = (int) (Math.random() * 5) + 5;
        }
    }

    public ArrayList<String> getDeck(){
        return deck;
    }

    public void setWorld(World world){
        this.world = world;

        String[] cardDeck = {"triangle", "square"};
        entityHandler = new EntityHandler(world, grid, cardDeck);
        deck = entityHandler.generateBucket(8);
    }

    private void spawnEnemy(){
        Function function2 = new Function("f", randomEquation(), "x");
        Line line2 = grid.addLine(function2);
        line2.color.a = 0.6f;

        Entity entity = EntityFactory.createEntityFromJson(world, "square.json");

        LineComponent lc = entity.edit().create(LineComponent.class);
        lc.lineId = line2.id;
        ArrayList<Vector2> pathArray = line2.trimmedRealPoints;
        Collections.reverse(pathArray);
        lc.path = pathArray;

        PositionComponent pc = entity.edit().create(PositionComponent.class);
        pc.x = pathArray.get(0).x;
        pc.y = pathArray.get(0).y;

        TeamComponent tc = entity.edit().create(TeamComponent.class);
        tc.team = "red";
    }

    private String randomEquation(){
        String result;

        int index = (int) (Math.random() * (enemyEquations.length-1)) + 1;
        result = enemyEquations[index];

        if(!result.equals("ln(-(x-64))")){
            int multiplier = (int) (Math.random() * 15) + 1;
            int yShift = (int) (Math.random() * (16-multiplier)) + 1;

            if(result.equals("(x-64)")){
                return "1/" + multiplier + result + "+" + yShift;
            }

            result = multiplier + result + "+" + yShift;
        }

        return result;
    }
}
