// Este import será necessário para a classe Avaliador que você criará
// import seu.pacote.Avaliador; 

public class Repl {
    
    private String[] dicionario = new String[50]; //Armazena variáveis e valores
    private int indice = 0;

    private Fila filaDeGravacao; // Usamos a Fila para REC/PLAY
    private boolean modoGravacao = false;


    //Construtor da classe Repl.
    public Repl() {
        //A fila só é inicializada quando o comando REC é usado.
        this.filaDeGravacao = new Fila();
    }

    /**
     * Processa a atribuição de uma variável (ex: "A=10").
     * @param atribuicao A string completa da atribuição.
     */
    public void escreverVariavel(String atribuicao) {
        // Remove espaços para garantir o formato "VAR=VALOR"
        String[] partes = atribuicao.replaceAll(" ", "").split("=");
        
        if (partes.length == 2) {
            // Armazena a variável (ex: "A") na posição par
            this.dicionario[this.indice] = partes[0].toUpperCase();
            // Armazena o valor (ex: "10") na posição ímpar seguinte
            this.dicionario[this.indice + 1] = partes[1];
            this.indice += 2; // Avança o índice em 2
            System.out.println(partes[0].toUpperCase() + "=" + partes[1]);
        } else {
            System.out.println("Erro: Atribuição de variável em formato inválido.");
        }
    }


    // Retorna o valor da variável ou lança uma exceção se não for encontrada.
    public double consultarValor(String nomeVariavel) throws Exception {
        String varMaiuscula = nomeVariavel.toUpperCase();
        for (int i = 0; i < this.indice; i += 2) {
            if (varMaiuscula.equals(this.dicionario[i])) {
                return Double.parseDouble(this.dicionario[i + 1]);
            }
        }
        // Mensagem de erro
        throw new Exception("Erro: variável " + nomeVariavel + " não definida.");
    }


    //Lista todas as variáveis e seus respectivos valores definidos.
    public void listarVariaveis() {
        if (this.indice == 0) {
            System.out.println("Nenhuma variável definida.");
            return;
        }
        for (int i = 0; i < indice; i += 2) {
            System.out.println(dicionario[i] + " = " + dicionario[i + 1]);
        }
    }

    //Reinicia todas as variáveis, limpando o dicionário.
    public void reiniciarVariaveis() {
        this.indice = 0;
        System.out.println("Variáveis reiniciadas."); // [cite: 89]
    }

    //Gravação de Comandos
    public boolean estaEmModoGravacao() {
        return this.modoGravacao;
    }

    public void iniciarGravacao() {
        this.modoGravacao = true;
        this.filaDeGravacao = new Fila(); //Cria uma nova fila limpa
        System.out.println("Iniciando gravação... (REC: " + filaDeGravacao.sizeElements() + "/10)");
    }

    public void pararGravacao() {
        this.modoGravacao = false;
        System.out.println("Encerrando gravação... (REC: " + filaDeGravacao.sizeElements() + "/10)");
    }
    
    public void gravarComando(String comando) {
        try {
            if (filaDeGravacao.isFull()) {
                System.out.println("Limite de gravação atingido. Encerrando gravação automaticamente.");
                pararGravacao();
                return;
            }
            filaDeGravacao.inserir(comando);
            //Exibe o status da gravação como no exemplo
            System.out.println("(REC: " + filaDeGravacao.sizeElements() + "/10) " + comando);
        } catch (Exception e) {
            System.out.println("Erro ao gravar comando: " + e.getMessage());
        }
    }

    public void apagarGravacao() {
        this.filaDeGravacao = new Fila(); //Simplesmente substitui por uma nova fila vazia
        System.out.println("Gravação apagada.");
    }

    //Reproduz os comandos gravados na fila.
    public void reproduzirGravacao() {
        if (filaDeGravacao.isEmpty()) {
            System.out.println("Não há gravação para ser reproduzida.");
            return;
        }

        System.out.println("Reproduzindo gravação...");
        
        //Lógica para executar sem perder o conteúdo da fila
        int tamanhoAtual = filaDeGravacao.sizeElements();
        try {
            for (int i = 0; i < tamanhoAtual; i++) {
                String comando = filaDeGravacao.remover(); //Tira do início
                
                System.out.println("> " + comando); //Mostra o comando sendo executado
                
                //Processa o comando
                processarComandoGravado(comando);

                filaDeGravacao.inserir(comando); //Coloca de volta no fim
            }
        } catch (Exception e) {
            System.err.println("Ocorreu um erro durante a reprodução: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para processar um comando vindo da fila do PLAY.
     * Você precisará conectar a classe Avaliador aqui quando ela estiver pronta.
     */
    private void processarComandoGravado(String comando) {
        comando = comando.trim().toUpperCase();

        if (comando.equalsIgnoreCase("VARS")) {
            this.listarVariaveis();
        } else if (comando.equalsIgnoreCase("RESET")) {
            this.reiniciarVariaveis();
        } else if (comando.contains("=")) {
            this.escreverVariavel(comando);
        } else {
            // Aqui entrará a lógica para o Avaliador de Expressões
            System.out.println("-> Lógica de avaliação da expressão '" + comando + "' entra aqui.");
        }
    }
}