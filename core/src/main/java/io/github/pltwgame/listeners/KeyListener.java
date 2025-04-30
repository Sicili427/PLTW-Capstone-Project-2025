package io.github.pltwgame.listeners;

import com.artemis.Entity;
import com.badlogic.gdx.Gdx;
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
import io.github.pltwgame.loaders.JsonLoader;
import io.github.pltwgame.ui.Taskbar;
import org.mariuszgromada.math.mxparser.Function;

import java.util.ArrayList;
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
                    line.color.a = 0.6f;

                    if(taskbar.stage.getRoot().findActor("equationImage") != null){
                        taskbar.stage.getRoot().findActor("equationImage").remove();
                    }

                    if(!line.linePoints.isEmpty() && line.linePoints.get(0)[0].x < 33){
                        createEntity(line);
                    } else {
                        grid.removeLine(taskbar.lastIndex);
                    }

                    taskbar.lastIndex = "";
                    taskbar.errorLabel.setVisible(false);
                    taskbar.errorDuration = 0;
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

    private void createEntity(Line line){
        String card = gameWorld.getDeck().get(gameWorld.getDeck().size()-1);
        int inkCost = JsonLoader.getJson("/entities/" + card + ".json").get("Components").get("InkComponent").getInt("inkCost", 1);

        if(gameWorld.currentInk >= inkCost){
            gameWorld.entityHandler.place(card, line);
            gameWorld.currentInk -= inkCost;

            gameWorld.getDeck().remove(gameWorld.getDeck().size()-1);
            taskbar.updateDeckTable();
        } else {
            grid.removeLine(taskbar.lastIndex);
        }

    }
}
