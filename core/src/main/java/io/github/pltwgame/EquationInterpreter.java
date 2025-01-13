package io.github.pltwgame;

import com.badlogic.gdx.Gdx;

import org.mariuszgromada.math.mxparser.*;

public class EquationInterpreter {
    String equationString;
    Function function  ;

    public void createFunction (String string) {
        function = new Function("f", equationString, "x");
    }


}
