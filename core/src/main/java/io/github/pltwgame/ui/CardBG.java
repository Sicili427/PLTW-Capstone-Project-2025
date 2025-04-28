package io.github.pltwgame.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CardBG extends Actor {
    NinePatch ninePatch;

    public CardBG(TextureAtlas atlas) {
        TextureRegion texture = new TextureRegion(atlas.findRegion("card"));
        ninePatch = new NinePatch(texture, 5, 5, 5,5);
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        super.draw(batch, parentAlpha);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        ninePatch.draw(batch, getX(), getY(), getWidth(), getHeight());
    }
}
