package io.github.pltwgame.gameCore;

import com.artemis.World;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class GameWorld {
    Grid grid;
    public World world;

    int maxBaseHealth = 100;
    public int currentBaseHealth = maxBaseHealth;

    int maxInk = 100;
    int currentInk = maxInk;

    float coolDown = 5;

    public EntityHandler entityHandler;
    ArrayList<String> deck;

    public GameWorld(Grid grid, World world){
        this.grid = grid;
        this.world = world;

        String[] cardDeck = {"square", "triangle"};
        entityHandler = new EntityHandler(world, grid, cardDeck);
        deck = entityHandler.generateBucket(8);
    }

    public void process(float delta){
        if(deck == null || deck.isEmpty()) {
            deck = entityHandler.generateBucket(8);
            Gdx.app.debug("deck", deck.toString() + "");
        }
    }

    public ArrayList<String> getDeck(){
        return deck;
    }
}
