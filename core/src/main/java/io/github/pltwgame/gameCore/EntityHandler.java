
package io.github.pltwgame.gameCore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntityHandler {
    public String[] bucket = new String[8];
    public ArrayList<String> deck;

    public EntityHandler(String[] deck) {
        this.deck = new ArrayList<>(Arrays.asList(deck));
    }

    // generates bucket of units with the first two slots in deck occuring twice
    public String[] generateBucket(){
        int count = 0;
        List<String> temp = new ArrayList<>();

        for(int i = 0; i < bucket.length; i++){
            if(count == deck.size()){
                count = 0;
            }
            temp.add(deck.get(count));
            count++;
        }

        Collections.shuffle(temp);
        bucket = temp.toArray(new String[bucket.length]);

        return bucket;
    }
}
