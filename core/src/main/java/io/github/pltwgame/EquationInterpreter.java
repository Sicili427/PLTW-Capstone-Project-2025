package io.github.pltwgame;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;

public class EquationInterpreter {
    String[] operators = {"(", "[", "{", ")", "]", "}", "+", "-", "*", "/", "^", "!", "|"};

    String[] mathFunctions = {"abs", "sqrt", "log", "ln", "sin", "cos", "tan", "csc", "sec", "cot", "arcsin", "arccos", "arctan", "arccsc", "arcsec", "arccot"};

    public static void stringToEquation(String input) {
        String equationString = input.toLowerCase();
        ArrayList<String> equationBits = new ArrayList<>();

        while(!equationString.isEmpty()) {
            char character = equationString.charAt(0);
            if(Character.isLetter(character)) {

            }
            else if (Character.isDigit(character)){

            }
            else {
                equationBits.add(Character.toString(character));
            }
        }
        Gdx.app.debug("test", equationBits.toString());
    }
}
