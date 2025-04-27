package io.github.pltwgame.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import io.github.pltwgame.gameCore.GameWorld;
import io.github.pltwgame.gameCore.Grid;
import io.github.pltwgame.gameCore.Line;
import io.github.pltwgame.ui.Taskbar;
import org.mariuszgromada.math.mxparser.Function;

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

                    gameWorld.getDeck().remove(gameWorld.getDeck().size()-1);
                    taskbar.updateDeckTable();
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
}
