package io.github.pltwgame;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Map;

public class EquationInterpreter {
    static String equationString;
    static ArrayList<String> equationBits;

    static final Map<Character,Character> identifiers = Map.of(
        '(', '(',
        ')', ')',
        '|', '|'
    );

    static final Map<Character,Character> operators = Map.of(
        '!', '!',
        '*', '*',
        '+', '+',
        '-', '-',
        '/', '/',
        '^', '^'
    );

    static final Map<String, String> trigFunctions = Map.of(
        "abs", "abs",
        "sqrt", "sqrt",
        "log", "log",
        "ln", "ln",
        "sin", "sin",
        "cos", "cos",
        "tan", "tan",
        "csc", "csc",
        "sec", "sec",
        "cot", "cot"
    );

    public static void stringToEquation(String input) {
        equationString = removeWhiteSpace(input.toLowerCase());
        Gdx.app.debug("equationString", equationString);

        equationBits = stringToBits();
        Gdx.app.debug("equationBits", String.valueOf(equationBits));

        Gdx.app.debug("validIdentifiers", "" + checkParentheses(equationBits));
    }

    public static ArrayList<String> stringToBits() {
        ArrayList<String> bits = new ArrayList<>();

        while (!equationString.isEmpty()) {
            char character = equationString.charAt(0);

            // Handle identifiers and operators
            if (identifiers.containsValue(character) || operators.containsValue(character)) {
                bits.add(String.valueOf(character));
                equationString = equationString.substring(1);

                // Handle numbers
            } else if (Character.isDigit(character)) {
                StringBuilder tempNum = new StringBuilder();
                tempNum.append(character);
                equationString = equationString.substring(1);

                while (!equationString.isEmpty() && Character.isDigit(equationString.charAt(0))) {
                    tempNum.append(equationString.charAt(0));
                    equationString = equationString.substring(1);
                }
                bits.add(tempNum.toString());

                // Handle letters (variables or trigonometric functions)
            } else if (Character.isLetter(character)) {
                if (character == 'x' || character == 'e') {
                    bits.add(String.valueOf(character));
                    equationString = equationString.substring(1);
                    continue;
                }

                // Attempt to find a trigonometric function
                int i = 1;
                String matchedFunction = null;
                while (i <= 4 && i <= equationString.length()) { // Limit search length
                    matchedFunction = trigFunctions.get(equationString.substring(0, i));
                    if (matchedFunction != null) break;
                    i++;
                }

                if (matchedFunction != null) {
                    bits.add(matchedFunction);
                    equationString = equationString.substring(i);
                } else {
                    return null;
                }

                // Handle unexpected cases
            } else {
                return null;
            }
        }


        return bits;
    }

    //////////////////////////////////////////////////////
    // NOTE: For actually parsing and calculating the
    // functions put the stuff that is in parentheses
    // first in the arraylist like x(x+1) -> {x, +, 1, x}
    // and maybe add some character to tell parser that
    // the first part is all multiplied by the next
    //////////////////////////////////////////////////////

    public static ArrayList<String> parseBits(ArrayList<String> input) {
        ArrayList<String> output = new ArrayList<>();
        if(checkParentheses(input)) {
            return null;
        }
        return output;
    }

    public static boolean checkParentheses(ArrayList<String> input) {
        int count = 0;
        int absCount = 0;
        for (String target : input) {
            switch (target) {
                case "(":
                    count++;
                    break;
                case ")":
                    count--;
                    if (count < 0) {
                        return false;
                    }
                    break;
                case "|":
                    absCount++;
                    break;
            }
        }
        return count == 0 && absCount % 2 == 0;
    }

    public static String removeWhiteSpace(String input) {
        return input.replaceAll("\\s+", "");
    }
}
