package io.github.pltwgame.gameCore;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

public class GameWorld {
    Grid grid;

    EntityHandler entityHandler;
    ArrayList<String> deck;

    public GameWorld(Grid grid){
        this.grid = grid;

        String[] cardDeck = {"square"};
        entityHandler = new EntityHandler(cardDeck);
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
