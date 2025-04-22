package io.github.pltwgame.ui;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.pltwgame.Grid;
import io.github.pltwgame.Line;
import org.mariuszgromada.math.mxparser.Function;

public class TempListener extends ChangeListener {
    TextField equationField;
    Grid grid;

    String lastValid;
    String lastIndex;

    @Override
    public void changed(ChangeEvent changeEvent, Actor actor) {
        String text = equationField.getText();
        Function function = new Function("f", text, "x");

        if(function.checkSyntax()){
            if(lastValid.equals(text)){
                grid.removeLine(lastIndex);

                Line line = grid.addLine(function);
                line.color.a = 0.5f;

                lastIndex = line.id;
            } else {
                lastValid = text;
            }
        }
    }
}
