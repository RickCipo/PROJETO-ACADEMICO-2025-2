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
        boolean isRecording = false;
        int commandsRecorded = 0;

        while (true) {
            System.out.print("> ");
            input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            // Modo de gravação
            if (isRecording) {
                if (input.equalsIgnoreCase("STOP")) {
                    System.out.println("Encerrando gravação... (REC: " + commandsRecorded + "/10)");
                    isRecording = false;
                    commandsRecorded = 0;
                    continue;
                }
                
                // Comandos não aceitos para gravação
                if (input.equalsIgnoreCase("REC") || input.equalsIgnoreCase("PLAY") || input.equalsIgnoreCase("ERASE") || input.equalsIgnoreCase("EXIT")) {
                    System.out.println("Erro: comando inválido para gravação.");
                } else {
                    repl.rec(input);
                    commandsRecorded++;
                    System.out.println("(REC: " + commandsRecorded + "/10) " + input);

                    if (commandsRecorded >= 10) {
                        System.out.println("Gravação atingiu o limite de 10 comandos. Parando gravação automaticamente.");
                        isRecording = false;
                        commandsRecorded = 0;
                    }
                }
                continue;
            }

            // Modo normal de operação
            String upperInput = input.toUpperCase();
            if (upperInput.matches("[A-Z]\\s*=\\s*(-?\\d+(\\.\\d+)?).*")) {
                String[] parts = upperInput.replaceAll("\\s+", "").split("=");
                try {
                    char var = parts[0].charAt(0);
                    double value = Double.parseDouble(parts[1]);
                    repl.setValue(var, value);
                    System.out.println(var + " = " + value);
                } catch (NumberFormatException e) {
                    System.out.println("Erro: valor inválido.");
                }
            } else if (upperInput.equals("VARS")) {
                repl.vars();
            } else if (upperInput.equals("RESET")) {
                repl.reset();
                System.out.println("Variáveis reiniciadas.");
            } else if (upperInput.equals("REC")) {
                System.out.println("Iniciando gravação... (REC: 0/10)");
                isRecording = true;
            } else if (upperInput.equals("PLAY")) {
                repl.play();
            } else if (upperInput.equals("ERASE")) {
                repl.erase();
                System.out.println("Gravação apagada.");
            } else if (upperInput.equals("EXIT")) {
                System.out.println("Encerrando o programa...");
                scanner.close();
                return;
            } else {
                try {
                    // Configura a expressão no objeto Avaliador
                    avaliador.setExpressao(input);
                    // Verifica e resolve a expressão
                    if (avaliador.verificar()) {
                        System.out.println(avaliador.resolver(repl));
                    } else {
                        System.out.println("Expressão inválida!");
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        }
    }
}