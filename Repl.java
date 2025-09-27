import java.util.HashMap;

public class Repl {
    
    private String[] dicionario = new String[50];
    private int indice = 0;

    private Fila filaDeGravacao;

    // Construtor da classe Repl.
    public Repl() {
        this.filaDeGravacao = new Fila();
    }

    // --- MÉTODOS NOVOS E RENOMEADOS ---

    /**
     * Define ou atualiza o valor de uma variável.
     * Chamado diretamente pelo Main.
     */
    public void setValue(char var, double value) {
        String varName = String.valueOf(var).toUpperCase();
        String varValue = String.valueOf(value);

        // Procura se a variável já existe para atualizá-la
        for (int i = 0; i < this.indice; i += 2) {
            if (this.dicionario[i].equals(varName)) {
                this.dicionario[i + 1] = varValue; // Atualiza o valor
                return; // Termina o método
            }
        }

        // Se não encontrou, adiciona como uma nova variável
        if (this.indice < this.dicionario.length - 1) {
            this.dicionario[this.indice] = varName;
            this.dicionario[this.indice + 1] = varValue;
            this.indice += 2;
        }
    }

    /**
     * Retorna um HashMap das variáveis para ser usado pelo Avaliador.
     */
    public HashMap<Character, Double> getVariaveis() {
        HashMap<Character, Double> mapaDeVariaveis = new HashMap<>();
        for (int i = 0; i < this.indice; i += 2) {
            char var = this.dicionario[i].charAt(0);
            double val = Double.parseDouble(this.dicionario[i + 1]);
            mapaDeVariaveis.put(var, val);
        }
        return mapaDeVariaveis;
    }

    /**
     * Lista todas as variáveis e seus respectivos valores definidos. (Antigo listarVariaveis)
     */
    public void vars() {
        if (this.indice == 0) {
            System.out.println("Nenhuma variável definida.");
            return;
        }
        for (int i = 0; i < indice; i += 2) {
            System.out.println(dicionario[i] + " = " + dicionario[i + 1]);
        }
    }

    /**
     * Reinicia todas as variáveis, limpando o dicionário. (Antigo reiniciarVariaveis)
     */
    public void reset() {
        this.indice = 0;
        // A mensagem de "Variáveis reiniciadas." já é impressa pelo Main.java
    }

    /**
     * Grava um comando na fila. (Antigo gravarComando)
     */
    public void rec(String comando) {
        try {
            if (filaDeGravacao.isFull()) {
                // A lógica de parar a gravação já está no Main.java
                return;
            }
            filaDeGravacao.inserir(comando);
        } catch (Exception e) {
            System.out.println("Erro ao gravar comando: " + e.getMessage());
        }
    }

    /**
     * Limpa a fila de gravação. (Antigo apagarGravacao)
     */
    public void erase() {
        this.filaDeGravacao = new Fila(); // Simplesmente substitui por uma nova fila vazia
    }

    /**
     * Reproduz os comandos gravados na fila. (Antigo reproduzirGravacao)
     */
    public void play() {
        if (filaDeGravacao.isEmpty()) {
            System.out.println("Não há gravação para ser reproduzida.");
            return;
        }

        System.out.println("Reproduzindo gravação...");
        
        int tamanhoAtual = filaDeGravacao.sizeElements();
        try {
            for (int i = 0; i < tamanhoAtual; i++) {
                String comando = filaDeGravacao.remover();
                
                System.out.println("> " + comando);
                
                processarComandoGravado(comando);

                filaDeGravacao.inserir(comando); // Coloca de volta no fim
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro durante a reprodução: " + e.getMessage());
        }
    }
    
    // --- MÉTODOS AUXILIARES INTERNOS ---

    /**
     * Processa a atribuição de uma variável (ex: "A=10"). Usado pelo método play().
     */
    private void escreverVariavel(String atribuicao) {
        String[] partes = atribuicao.replaceAll(" ", "").split("=");
        if (partes.length == 2) {
            char var = partes[0].toUpperCase().charAt(0);
            double val = Double.parseDouble(partes[1]);
            setValue(var, val); // Reutiliza o método setValue
            System.out.println(var + " = " + val);
        } else {
            System.out.println("Erro: Atribuição de variável em formato inválido.");
        }
    }
    
    private void processarComandoGravado(String comando) {
        if (comando.equalsIgnoreCase("VARS")) {
            this.vars();
        } else if (comando.equalsIgnoreCase("RESET")) {
            this.reset();
            System.out.println("Variáveis reiniciadas.");
        } else if (comando.contains("=")) {
            this.escreverVariavel(comando);
        } else {
            // Lógica para o Avaliador de Expressões
            try {
                Avaliador avaliadorPlay = new Avaliador();
                avaliadorPlay.setExpressao(comando);
                if (avaliadorPlay.verificar()) {
                    System.out.println(avaliadorPlay.resolver(this));
                } else {
                    System.out.println("Expressão inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
    
    // Métodos não utilizados pelo Main, podem ser removidos se não forem usados em outro lugar
    // consultarValor, iniciarGravacao, pararGravacao, estaEmModoGravacao
}