package io.github.pltwgame.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import java.util.Arrays;

public class Bars {
    public static int[] generateRandomArray(int length, int[] weights) {
        int[] result = new int[length];
        int sum = 0;

        for(int x : weights){
            sum += x;
        }

        for(int i = 0; i < length; i++){
            int rng = (int)(Math.random() * sum);
            int cumulative = 0;

            for(int j = 0; j < weights.length; j++){
                cumulative += weights[j];
                if(rng < cumulative){
                    int num = j + 1;
                    if(i > 0 && result[i-1] == num) {
                        num += 1;
                        if(num == 10){
                            num = 1;
                        }
                    }
                    result[i] = num;
                    break;
                }
            }
        }

        return result;
    }

    public static VerticalGroup createVertWoodBar(TextureAtlas atlas, int length, float scale, float x, float y, Boolean mirror){
        int[] weights = {25,10,10,25,10,25,10,1};
        int[] nums = generateRandomArray(length, weights);

        VerticalGroup imageGroup = new VerticalGroup();

        for(int i = 0; i < nums.length; i++){
            Image image = new Image(atlas.findRegion("wood_border0" + nums[i]));

            image.setScale(scale);

            if(mirror){
                image.setScaleX(-scale);
            }

            imageGroup.addActor(image);
        }

        imageGroup.columnBottom();
        imageGroup.space(16);
        imageGroup.setPosition(x,y);
        imageGroup.pack();

        return imageGroup;
    }

    public static HorizontalGroup createHorzWoodBar(TextureAtlas atlas, int length, float scale, float x, float y, Boolean mirror){
        int[] weights = {10,10,10,10,10,10,10};
        int[] nums = generateRandomArray(length, weights);

        HorizontalGroup imageGroup = new HorizontalGroup();

        for(int i = 0; i < nums.length; i++){
            Image image = new Image(atlas.findRegion("wood_border_small0" + nums[i]));

            image.setScale(scale);
            image.setRotation(90);

            if(mirror){
                image.setScaleX(-scale);
            }

            imageGroup.addActor(image);
        }

        imageGroup.rowLeft();
        imageGroup.space(8);
        imageGroup.setPosition(x,y);
        imageGroup.pack();

        return imageGroup;
    }
}
