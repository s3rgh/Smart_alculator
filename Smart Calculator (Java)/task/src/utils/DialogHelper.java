package utils;

public class DialogHelper {
    private static final String HELP = "The program calculates the sum of numbers";
    private static final String EXIT = "Bye!";
    private static final String INVALID_EXPRESSION = "Invalid expression";
    private static final String UNKNOWN_COMMAND = "Unknown command";
    private static final String UNKNOWN_VARIABLE = "Unknown variable";
    private static final String INVALID_IDENTIFIER = "Invalid identifier";
    private static final String INVALID_ASSIGNMENT = "Invalid assignment";

    public static void help() {
        System.out.println(HELP);
    }

    public static void exit() {
        System.out.println(EXIT);
    }

    public static void invalidExpression() {
        System.out.println(INVALID_EXPRESSION);
    }

    public static void invalidIdentifier() {
        System.out.println(INVALID_IDENTIFIER);
    }

    public static void invalidAssignment() {
        System.out.println(INVALID_ASSIGNMENT);
    }

    public static void unknownCommand() {
        System.out.println(UNKNOWN_COMMAND);
    }

    public static void unknownVariable() {
        System.out.println(UNKNOWN_VARIABLE);
    }
}
