package calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

import static utils.Operator.*;

public class Evaluator {
    protected String infixToPostfix(String infix) {
        var stack = new Stack<Character>();
        var postFixList = new ArrayList<String>();
        var flag = false;
        for (int i = 0; i < infix.length(); i++) {
            char word = infix.charAt(i);
            if (word == ' ') {
                continue;
            }
            if (word == '(') {
                stack.push(word);
                flag = false;
            } else if (word == ')') {
                flag = false;
                while (!stack.isEmpty()) {
                    if (stack.peek() == '(') {
                        stack.pop();
                        break;
                    } else {
                        postFixList.add(stack.pop() + "");
                    }
                }
            } else if (isOperator(word)) {
                flag = false;
                if (stack.isEmpty()) {
                    stack.push(word);
                } else {
                    while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(word)) {
                        postFixList.add(stack.pop() + "");
                    }
                    stack.push(word);
                }
            } else {
                if (flag) {
                    var lastNumber = postFixList.get(postFixList.size() - 1);
                    lastNumber += word;
                    postFixList.set(postFixList.size() - 1, lastNumber);
                } else
                    postFixList.add(word + "");
                flag = true;
            }
        }
        while (!stack.isEmpty()) {
            postFixList.add(stack.pop() + "");
        }
        return String.join(" ", postFixList);
    }

    private static int precedence(char c) {
        if (c == ADD.getOperation() || c == SUBTRACT.getOperation()) return 1;
        else if (c == MULTIPLY.getOperation() || c == DIVIDE.getOperation()) return 2;
        else if (c == MOD.getOperation()) return 3;
        else return -1;
    }

    protected BigDecimal calculatePostFix(String postfix) {
        var postFixList = Arrays.stream(postfix.split(" ")).toList();
        var stack = new Stack<BigDecimal>();
        for (var word : postFixList) {
            if (word.length() == 1 && (isOperator(word.charAt(0)))) {
                BigDecimal number2 = stack.pop();
                BigDecimal number1 = stack.pop();
                if (word.charAt(0) == ADD.getOperation()) {
                    stack.push(number1.add(number2));
                } else if (word.charAt(0) == SUBTRACT.getOperation()) {
                    stack.push(number1.subtract(number2));
                } else if (word.charAt(0) == MULTIPLY.getOperation()) {
                    stack.push(number1.multiply(number2));
                } else if (word.charAt(0) == DIVIDE.getOperation()) {
                    stack.push(number1.divide(number2, RoundingMode.UNNECESSARY));
                }
            } else {
                stack.push(new BigDecimal(word));
            }
        }
        return stack.peek();
    }
}
