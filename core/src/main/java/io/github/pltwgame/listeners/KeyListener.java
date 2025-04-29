package io.github.pltwgame.listeners;

import com.artemis.Entity;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import io.github.pltwgame.components.LineComponent;
import io.github.pltwgame.components.PositionComponent;
import io.github.pltwgame.components.TeamComponent;
import io.github.pltwgame.gameCore.GameWorld;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.gameCore.Line;
import io.github.pltwgame.loaders.EntityFactory;
import io.github.pltwgame.ui.Taskbar;
import org.mariuszgromada.math.mxparser.Function;

import java.util.Arrays;
import java.util.Collections;

public class KeyListener extends InputListener {
    private GameWorld gameWorld;
    private Taskbar taskbar;
    private Grid grid;

    public KeyListener(GameWorld gameWorld, Taskbar taskbar, Grid grid){
        this.gameWorld = gameWorld;
        this.taskbar = taskbar;
        this.grid = grid;
    }

    @Override
    public boolean keyDown(InputEvent event, int keyCode ) {
        if(keyCode == Input.Keys.ENTER){
            String text = taskbar.equationField.getText().trim();
            taskbar.lastValid = "";

            if(text.isBlank()){
                taskbar.errorLabel.setText("Please enter an expression.");
                taskbar.errorLabel.setVisible(true);
                taskbar.errorDuration = 5;
            } else {
                Function function = new Function("f", text, "x");
                taskbar.equationField.setText("");
                // on valid input
                if(function.checkSyntax()){
                    Line line = grid.getLine(taskbar.lastIndex);
                    line.color.a = 1;

                    taskbar.lastIndex = "";
                    taskbar.errorLabel.setVisible(false);
                    taskbar.errorDuration = 0;
                    taskbar.stage.getRoot().findActor("equationImage").remove();

                    gameWorld.entityHandler.place(gameWorld.getDeck().get(gameWorld.getDeck().size()-1), line);

                    gameWorld.getDeck().remove(gameWorld.getDeck().size()-1);
                    taskbar.updateDeckTable();

                    createEnemy();
                } else {
                    taskbar.errorLabel.setText("Please enter a valid expression.");
                    taskbar.errorLabel.setVisible(true);
                    taskbar.errorDuration = 5;
                }
            }
            return true;
        }
        return false;
    }

    private void createEnemy(){
        Function function2 = new Function("f", "sin(x)", "x");
        Line line2 = grid.addLine(function2);

        Entity entity = EntityFactory.createEntityFromJson(gameWorld.world, "square.json");

        LineComponent lc = entity.edit().create(LineComponent.class);
        lc.lineId = line2.id;
        Vector2[] pathArray = line2.realPoints;
        Collections.reverse(Arrays.asList(pathArray));
        lc.path = pathArray;

        PositionComponent pc = entity.edit().create(PositionComponent.class);
        pc.x = pathArray[0].x;
        pc.y = pathArray[0].y;

        TeamComponent tc = entity.edit().create(TeamComponent.class);
        tc.team = "red";
    }
}
