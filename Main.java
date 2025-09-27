// Henrique Ferreira Marciano - 10439797
// Enrique Cipolla - 10427834

// Referências:
// Estrutura de Dados I - TAD Pilha - Prof. Alexandre Mignon
// Algoritmo de conversão infixa para posfixa: Material de aula (EDI-2025.2 - Apl1.pdf)
// Algoritmo de avaliação de expressão posfixa: Material de aula (EDI-2025.2 - Apl1.pdf)

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Repl repl = new Repl();
        Avaliador avaliador = new Avaliador();
        String input;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            if (repl.estaEmModoGravacao()) {
                String upperInput = input.toUpperCase();
                if (upperInput.equals("STOP")) {
                    repl.pararGravacao();
                } else if (upperInput.equals("REC") || upperInput.equals("PLAY") || upperInput.equals("ERASE") || upperInput.equals("EXIT")) {
                    System.out.println("Erro: comando inválido para gravação.");
                } else {
                    repl.gravarComando(input);
                }
                continue;
            }

            String upperInput = input.toUpperCase();

            try {
                if (upperInput.equals("EXIT")) {
                    System.out.println("Encerrando o programa...");
                    break;
                } else if (upperInput.equals("VARS")) {
                    repl.listarVariaveis();
                } else if (upperInput.equals("RESET")) {
                    repl.reiniciarVariaveis();
                } else if (upperInput.equals("REC")) {
                    repl.iniciarGravacao();
                } else if (upperInput.equals("PLAY")) {
                    repl.reproduzirGravacao();
                } else if (upperInput.equals("ERASE")) {
                    repl.apagarGravacao();
                } else if (input.contains("=")) {
                    String[] partes = input.replaceAll("\\s+", "").split("=");
                    if (partes.length != 2 || partes[0].length() != 1 || !Character.isLetter(partes[0].charAt(0))) {
                        throw new Exception("Comando de atribuição inválido.");
                    }
                    repl.definirValor(partes[0].charAt(0), Double.parseDouble(partes[1]));

                // --- LÓGICA CORRIGIDA E MAIS RESTRITA ---
                
                } else if (upperInput.matches("[A-Z]")) { // Se for apenas UMA letra
                    double resultado = repl.consultarValor(upperInput.charAt(0));
                    if (resultado == (long) resultado) System.out.println((long) resultado);
                    else System.out.println(resultado);

                } else if (input.matches(".*[+\\-*/^()].*")) { // Se CONTÉM um operador ou parênteses
                    avaliador.setExpressao(input);
                    if (avaliador.verificar()) {
                        double resultado = avaliador.resolver(repl);
                        if (resultado == (long) resultado) {
                            System.out.println((long) resultado);
                        } else {
                            System.out.println(resultado);
                        }
                    } else {
                        throw new Exception("Expressão inválida.");
                    }
                } else { // Se não for nada do que foi testado acima, o comando é inválido
                    System.out.println("Erro: comando inválido.");
                }

            } catch (Exception e) {
                String[] mensagens = e.getMessage().split("\n");
                for (String msg : mensagens) {
                    System.out.println("Erro: " + msg);
                }
            }
        }
        scanner.close();
    }
}