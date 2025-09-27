import java.util.HashMap;

public class Avaliador {
    private String expression;

    public Avaliador() {
        this.expression = "";
    }

    public Avaliador(String expression) {
        this.expression = expression.replaceAll("\\s+", "");
    }

    public void setExpressao(String expression) {
        this.expression = expression.replaceAll("\\s+", "");
    }

    public boolean verificar() {
        try {
            // Este método já lida com a exceção usando try-catch, então está correto!
            convertToPostfix();
            return true;
        } catch (Exception e) { 
            return false;
        }
    }

    // CORREÇÃO: Adicionado "throws Exception"
    public double resolver(Repl repl) throws Exception {
        String postfix = convertToPostfix();
        HashMap<Character, Double> variaveis = repl.getVariaveis();
        return evaluatePostfix(postfix, variaveis);
    }

    public String getInfixa() {
        return this.expression;
    }

    // CORREÇÃO: Adicionado "throws Exception"
    public String convertToPostfix() throws Exception {
        Pilha<Character> stack = new Pilha<>();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                output.append(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    output.append(stack.pop());
                }
                if (!stack.isEmpty() && stack.peek() == '(') {
                    stack.pop();
                } else {
                    throw new IllegalArgumentException("Parênteses desbalanceados.");
                }
            } else if (isOperator(c)) {
                while (!stack.isEmpty() && stack.peek() != '(' && getPrecedence(stack.peek()) >= getPrecedence(c)) {
                    output.append(stack.pop());
                }
                stack.push(c);
            }
        }

        while (!stack.isEmpty()) {
            char c = stack.pop();
            if (c == '(') {
                throw new IllegalArgumentException("Parênteses desbalanceados.");
            }
            output.append(c);
        }

        return output.toString();
    }

    // CORREÇÃO: Adicionado "throws Exception"
    public double evaluatePostfix(String postfixExpression, HashMap<Character, Double> variables) throws Exception {
        Pilha<Double> stack = new Pilha<>();

        for (int i = 0; i < postfixExpression.length(); i++) {
            char c = postfixExpression.charAt(i);

            if (Character.isLetter(c)) {
                char var = Character.toUpperCase(c);
                if (!variables.containsKey(var)) {
                    throw new IllegalArgumentException("Variável " + c + " não definida.");
                }
                stack.push(variables.get(var));
            } else if(Character.isDigit(c)) {
                stack.push((double)Character.getNumericValue(c));
            } 
            else if (isOperator(c)) {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Expressão inválida para avaliação.");
                }
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result = performOperation(c, operand1, operand2);
                stack.push(result);
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Expressão inválida.");
        }

        return stack.pop();
    }

    private int getPrecedence(char operator) {
        switch (operator) {
            case '+': case '-': return 1;
            case '*': case '/': return 2;
            case '^': return 3;
            default: return 0;
        }
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }
    
    private double performOperation(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+': return operand1 + operand2;
            case '-': return operand1 - operand2;
            case '*': return operand1 * operand2;
            case '/':
                if (operand2 == 0) throw new IllegalArgumentException("Divisão por zero.");
                return operand1 / operand2;
            case '^': return Math.pow(operand1, operand2);
            default: throw new IllegalArgumentException("Operador inválido.");
        }
    }
}