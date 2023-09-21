package utils;

import java.util.List;

public enum Operator {
    ADD('+'),
    SUBTRACT('-'),
    MULTIPLY('*'),
    DIVIDE('/'),
    MOD('%');

    private char operation;

    Operator(char operation) {
        this.operation = operation;
    }

    public char getOperation() {
        return operation;
    }

    public static boolean isOperator(char operator) {
        return List.of(ADD.getOperation(), SUBTRACT.getOperation(), MOD.getOperation(), MULTIPLY.getOperation(), DIVIDE.getOperation())
                .contains(operator);
    }
}
