package calculator;

import java.util.Scanner;
import java.util.regex.Pattern;

import static utils.DialogHelper.*;

public class InputAnalyzer {
    private static final Scanner SCANNER = new Scanner(System.in);

    protected String getInput() {
        return SCANNER.nextLine();
    }

    protected boolean isVariable(String userInput) {
        var expression = spacesCleaner(userInput);
        if (expression.matches("^(?![a-zA-Z]+\\b)[a-zA-Z0-9]*")) {
            invalidIdentifier();
            return false;
        }
        if (expression.contains("=")) {
            var splittedExpression = expression.split("=");
            if (splittedExpression.length != 2) {
                invalidAssignment();
                return false;
            }
            var variable = expression.split("=")[0];
            var value = expression.split("=")[1];
            if (variable.matches("[a-zA-Z]+")) {
                if (value.matches("[a-zA-Z]+")) {
                    return true;
                } else if (value.matches("^[+-]?(?:[0-9]+|[0-9]+)")) {
                    return true;
                } else {
                    invalidAssignment();
                    return false;
                }
            } else {
                invalidIdentifier();
                return false;
            }
        }
        return false;
    }

    protected boolean isExpression(String expression) {
        if (expression.contains("=")) {
            return false;
        }
        if (expression.matches("^(?![a-zA-Z]+\\b)[a-zA-Z0-9]*")) {
            return false;
        }
        if (!(expression.equals("/exit") || expression.equals("/help")) && expression.matches("^/[\\w]+")) {
            unknownCommand();
            return false;
        } else if (expression.matches("\\w+[\\s|\\+|\\-]+")) {
            invalidExpression();
            return false;
        } else if (expression.matches(".*(?:\\*{2,}|\\/{2,}).*")) {
            invalidExpression();
            return false;
        } else if (expression.matches("(.*?\\+\\s*?|.*?-\\s*?)")) {
            invalidExpression();
            return false;
        } else if (!checkClosedParentheses(expression)) {
            invalidExpression();
            return false;
        }
        return true;
    }

    private boolean checkClosedParentheses(String expression) {
        var counter = 0;
        var symbols = expression.split("");
        for (String s : symbols) {
            if (s.equals("(")) {
                counter++;
            }
            if (s.equals(")")) {
                counter--;
            }
            if (counter < 0) {
                return false;
            }
        }
        return counter == 0;
    }

    protected String spacesCleaner(String userInput) {
        //Will remove all spaces between the expression.
        return userInput.replaceAll("\\s+", "");
    }

    protected String regexCleaner(String userInput) {
        //Will remove all spaces between the expression.
        var extraSpace = Pattern.compile("\\s+");
        var matchSpace = extraSpace.matcher(userInput);
        var spaceCleaner = matchSpace.replaceAll(" ");

        //Will merge all the Extra +
        var patternPlus = Pattern.compile("\\+{2,}");
        var matcherPlus = patternPlus.matcher(spaceCleaner);
        var mergePlus = matcherPlus.replaceAll("+");

        //Will merge all the Extra -
        var patternMinus = Pattern.compile("-{3}");
        var matcherMinus = patternMinus.matcher(mergePlus);
        var mergeMinus = matcherMinus.replaceAll("-");

        //Will replace - by + if we've 2 minus next to each one.
        var patternDoubleMinus = Pattern.compile("-{2}");
        var matcherDoubleMinus = patternDoubleMinus.matcher(mergeMinus);
        var mergeDoubleMinus = matcherDoubleMinus.replaceAll("+");

        //Will replace - by 0 -.
        //var patternStartByMinus = Pattern.compile("^-.*?");
        var patternStartByMinus = Pattern.compile("-(?=\\d+)");
        var matcherStartByMinus = patternStartByMinus.matcher(mergeDoubleMinus);
        var refactoringZero = matcherStartByMinus.replaceFirst("0 - ");

        //Will replace -+ by -.
        var plusMinusCheck = Pattern.compile("(-\\+|\\+-)");
        var plusMinusMatch = plusMinusCheck.matcher(refactoringZero);
        var plusMinus = plusMinusMatch.replaceAll("-");

        if (plusMinus.matches("^(\\+\\d+)")) {
            return plusMinus.replaceAll("\\+", "");
        }
        return plusMinus;
    }
}
