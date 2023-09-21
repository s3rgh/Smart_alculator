package calculator;

import static utils.DialogHelper.*;

public class Calculator {
    private final Evaluator evaluator;
    private final InputAnalyzer analyzer;
    private final Memory memory;

    public Calculator() {
        this.evaluator = new Evaluator();
        this.analyzer = new InputAnalyzer();
        this.memory = new Memory();
    }

    public void run() {
        while (true) {
            var input = analyzer.getInput();
            if (input.isEmpty()) {
                continue;
            }

            if (input.equals("/help")) {
                help();
                continue;
            }

            if (input.equals("/exit")) {
                exit();
                break;
            }

            var isVariable = analyzer.isVariable(input);

            if (isVariable) {
                var expression = analyzer.spacesCleaner(input);
                if (memory.isVariableRedefinition(expression) && !memory.isVariableInMemory(expression)) {
                    unknownVariable();
                } else {
                    memory.addNewVariable(expression);
                }
            }

            if (analyzer.isExpression(input)) {
                var evaluation = analyzer.regexCleaner(input);
                if (memory.evaluationVariablesAreKnown(evaluation.trim())) {
                    var s = memory.changeVariablesToValues(evaluation);
                    var m = analyzer.regexCleaner(s);
                    var postfix = evaluator.infixToPostfix(m);
                    var goal = evaluator.calculatePostFix(postfix);
                    System.out.println(goal);
                }
            }
        }
    }
}
