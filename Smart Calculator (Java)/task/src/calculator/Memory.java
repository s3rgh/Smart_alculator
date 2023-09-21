package calculator;

import java.util.HashMap;
import java.util.Map;

import static utils.DialogHelper.unknownVariable;

public class Memory {
    private final Map<String, String> memoryMap;

    public Memory() {
        this.memoryMap = new HashMap<>();
    }

    public boolean isVariableInMemory(String expression) {
        return memoryMap.containsKey(expression.split("=")[1]);
    }

    public String changeVariablesToValues(String eval) {
        var array = eval.split(" ");
        var string = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            if (memoryMap.containsKey(array[i])) {
                array[i] = memoryMap.get(array[i]);
                string.append(array[i]).append(" ");
            } else {
                string.append(array[i]).append(" ");
            }
        }
        return string.toString().trim();
    }

    public void addNewVariable(String expression) {
        var variable = expression.split("=")[0];
        var value = expression.split("=")[1];
        if (memoryMap.containsKey(value)) {
            String oldValue = memoryMap.get(value);
            memoryMap.put(variable, oldValue);
            return;
        } else {
            if (value.matches("[a-zA-Z]+")) {
                unknownVariable();
            }
        }
        memoryMap.put(variable, value);
    }

    public boolean isVariableRedefinition(String expression) {
        var variable = expression.split("=")[0];
        var value = expression.split("=")[1];
        return variable.matches("[a-zA-Z]+") && value.matches("[a-zA-Z]+");
    }

    public boolean evaluationVariablesAreKnown(String evaluation) {
        var variables = evaluation.replaceAll("[\\(\\)\\*\\+\\-\\=\\/\\%\\s]+", " ").split(" ");
        for (String s : variables) {
            if (s.matches("[0-9]+")) {
                continue;
            }
            if (!memoryMap.containsKey(s)) {
                unknownVariable();
                return false;
            }
        }
        return true;
    }
}
