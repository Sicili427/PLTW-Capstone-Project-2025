
package io.github.pltwgame.gameCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class EntityHandler {
    public ArrayList<String> bucket;
    public ArrayList<String> deck;

    public EntityHandler(String[] deck) {
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
}
