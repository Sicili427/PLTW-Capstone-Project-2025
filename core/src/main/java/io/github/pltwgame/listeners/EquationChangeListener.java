package io.github.pltwgame.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.pltwgame.gameCore.GameWorld;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.gameCore.Line;
import io.github.pltwgame.ui.Taskbar;
import org.mariuszgromada.math.mxparser.Function;

public class EquationChangeListener extends ChangeListener {
    private GameWorld gameWorld;
    private Taskbar taskbar;
    private Grid grid;

    private Texture texture;
    private Image image;

    public EquationChangeListener(GameWorld gameWorld, Taskbar taskbar, Grid grid){
        this.gameWorld = gameWorld;
        this.taskbar = taskbar;
        this.grid = grid;
        texture = new Texture("placeholder.png");
    }

    @Override
    public void changed(ChangeEvent event, Actor actor) {
        String text = taskbar.equationField.getText();
        Function function = new Function("f", text, "x");
        if(text.isBlank() && !taskbar.lastIndex.isEmpty()){
            taskbar.lastValid = "";
            grid.removeLine(taskbar.lastIndex);
            image.remove();
        }
        if(function.checkSyntax() && !taskbar.lastValid.equals(text)){
            taskbar.lastValid = text;
            if(taskbar.lastIndex != null && image != null){
                grid.removeLine(taskbar.lastIndex);
                image.remove();
            }
            Line line = grid.addLine(function);
            line.color.a = 0.5f;
            taskbar.lastIndex = line.id;

            if(!line.linePoints.isEmpty()) {
                Vector2 firstPoint = line.linePoints.get(0)[0];
                Vector2 secondPoint = line.linePoints.get(0)[1];

                float angle = (float) Math.atan((secondPoint.y - firstPoint.y) / (secondPoint.x - firstPoint.x));

                image = new Image(texture);
                image.setOrigin(1);
                image.setPosition(firstPoint.x - image.getWidth() * 0.5f, firstPoint.y - image.getHeight() * 0.5f);
                image.setRotation(57.295f * angle - 90);
                image.setColor(1f, 1f, 1f, .5f);
                image.setName("equationImage");

                taskbar.stage.addActor(image);
            } else if (image != null){
                image.remove();
            }
        }
    }
}
