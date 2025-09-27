// ENRIQUE CIPOLLA MARTINS - 10427834
// HENRIQUE FERREIRA MARCIANO - 10439797

public class Avaliador {
    private String expressao;

    public void setExpressao(String expressao) {
        this.expressao = expressao.replaceAll("\\s+", "");
    }

    public boolean verificar() {
        try {
            String posfixa = converterParaPosfixa();
            validarEstruturaPosfixa(posfixa); // Novo passo de validação!
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double resolver(Repl repl) throws Exception {
        verificarVariaveisDefinidas(repl);
        String posfixa = converterParaPosfixa();
        return avaliarPosfixa(posfixa, repl);
    }

    private void verificarVariaveisDefinidas(Repl repl) throws Exception {
        StringBuilder erros = new StringBuilder();
        boolean[] jaVerificado = new boolean[26];

        for (char c : this.expressao.toCharArray()) {
            if (Character.isLetter(c)) {
                char var = Character.toUpperCase(c);
                int indice = var - 'A';
                if (indice >= 0 && indice < 26 && !jaVerificado[indice]) {
                    try {
                        repl.consultarValor(var);
                    } catch (Exception e) {
                        if (erros.length() > 0) erros.append("\n");
                        erros.append(e.getMessage());
                    }
                    jaVerificado[indice] = true;
                }
            }
        }
        if (erros.length() > 0) {
            throw new Exception(erros.toString());
        }
    }

    // --- CORREÇÃO: NOVO MÉTODO DE VALIDAÇÃO ESTRUTURAL ---
    private void validarEstruturaPosfixa(String posfixa) throws Exception {
        if (posfixa.isEmpty()) {
            throw new Exception("Expressão inválida.");
        }
        int operandos = 0;
        int operadores = 0;

        for (char c : posfixa.toCharArray()) {
            if (Character.isLetter(c)) {
                operandos++;
            } else if (isOperador(c)) {
                operadores++;
            }
        }
        
        // Uma expressão com operadores deve ter (operandos = operadores + 1)
        if (operadores > 0 && operandos != operadores + 1) {
             throw new Exception("Expressão inválida.");
        }
        // Uma expressão sem operadores não pode ter mais de 1 operando
        if (operadores == 0 && operandos > 1) {
            throw new Exception("Expressão inválida.");
        }
    }

    private String converterParaPosfixa() throws Exception {
        Pilha<Character> pilha = new Pilha<>();
        StringBuilder saida = new StringBuilder();
        for (char c : this.expressao.toCharArray()) {
            if (Character.isLetter(c)) {
                saida.append(c);
            } else if (c == '(') {
                pilha.push(c);
            } else if (c == ')') {
                while (!pilha.isEmpty() && pilha.peek() != '(') {
                    saida.append(pilha.pop());
                }
                if (pilha.isEmpty()) throw new Exception("Expressão inválida.");
                pilha.pop();
            } else if (isOperador(c)) {
                while (!pilha.isEmpty() && getPrecedencia(c) <= getPrecedencia(pilha.peek())) {
                    saida.append(pilha.pop());
                }
                pilha.push(c);
            } else {
                throw new Exception("Operador inválido.");
            }
        }
        while (!pilha.isEmpty()) {
            char operador = pilha.pop();
            if (operador == '(') throw new Exception("Expressão inválida.");
            saida.append(operador);
        }
        return saida.toString();
    }

    private double avaliarPosfixa(String posfixa, Repl repl) throws Exception {
        Pilha<Double> pilha = new Pilha<>();
        for (char c : posfixa.toCharArray()) {
            if (Character.isLetter(c)) {
                double valor = repl.consultarValor(Character.toUpperCase(c));
                pilha.push(valor);
            } else if (isOperador(c)) {
                if (pilha.sizeElements() < 2) throw new Exception("Expressão inválida.");
                double op2 = pilha.pop();
                double op1 = pilha.pop();
                double resultado = executarOperacao(c, op1, op2);
                pilha.push(resultado);
            }
        }
        if (pilha.sizeElements() != 1) throw new Exception("Expressão inválida.");
        return pilha.pop();
    }

    private int getPrecedencia(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        if (op == '^') return 3;
        return 0;
    }

    private boolean isOperador(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    private double executarOperacao(char op, double a, double b) throws Exception {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
            case '/':
                if (b == 0) throw new Exception("Divisão por zero.");
                return a / b;
            case '^': return Math.pow(a, b);
            default: throw new Exception("Operador inválido.");
        }
    }
}