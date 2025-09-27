// ENRIQUE CIPOLLA MARTINS - 10427834
// HENRIQUE FERREIRA MARCIANO - 10439797

//Referencias para metodos que não aprendemos em aula ainda. https://www.w3schools.com/java

public class Repl {
    
    //Gerenciamento de variáveis simplificado e centralizado
    private Double[] valoresVariaveis = new Double[26];

    private Fila filaDeGravacao;
    private boolean modoGravacao = false;

    public Repl() {
        this.filaDeGravacao = new Fila();
    }

    //MÉTODOS PARA O MAIN

    public void definirValor(char var, double valor) {
        int indice = Character.toUpperCase(var) - 'A';
        if (indice >= 0 && indice < 26) {
            this.valoresVariaveis[indice] = valor;
            System.out.println(Character.toUpperCase(var) + " = " + valor);
        }
    }

    public void listarVariaveis() {
        boolean encontrou = false;
        for (int i = 0; i < 26; i++) {
            if (valoresVariaveis[i] != null) {
                System.out.println((char)('A' + i) + " = " + valoresVariaveis[i]);
                encontrou = true;
            }
        }
        if (!encontrou) {
            System.out.println("Nenhuma variável definida.");
        }
    }

    public void reiniciarVariaveis() {
        this.valoresVariaveis = new Double[26];
        System.out.println("Variáveis reiniciadas.");
    }

    //MÉTODO PARA O AVALIADOR

    public double consultarValor(char var) throws Exception {
        int indice = Character.toUpperCase(var) - 'A';
        if (indice < 0 || indice >= 26 || valoresVariaveis[indice] == null) {
            throw new Exception("Variável " + var + " não definida.");
        }
        return valoresVariaveis[indice];
    }
    
    //MÉTODOS DE GRAVAÇÃO

    public boolean estaEmModoGravacao() {
        return this.modoGravacao;
    }

    public void iniciarGravacao() {
        this.modoGravacao = true;
        this.filaDeGravacao = new Fila();
        System.out.println("Iniciando gravação... (REC: 0/10)");
    }

    public void pararGravacao() {
        this.modoGravacao = false;
        System.out.println("Encerrando gravação... (REC: " + this.filaDeGravacao.sizeElements() + "/10)");
    }

    public void gravarComando(String comando) {
        try {
            if (filaDeGravacao.isFull()) {
                System.out.println("Limite de gravação atingido. Parando gravação automaticamente.");
                pararGravacao();
                return;
            }
            filaDeGravacao.inserir(comando);
            System.out.println("(REC: " + this.filaDeGravacao.sizeElements() + "/10) " + comando);
        } catch (Exception e) {}
    }

    public void apagarGravacao() {
        this.filaDeGravacao = new Fila();
        System.out.println("Gravação apagada.");
    }

    public void reproduzirGravacao() {
        if (filaDeGravacao.isEmpty()) {
            System.out.println("Não há gravação para ser reproduzida.");
            return;
        }
        System.out.println("Reproduzindo gravação...");
        
        Avaliador avaliadorPlay = new Avaliador();
        int tamanhoAtual = filaDeGravacao.sizeElements();
        try {
            for (int i = 0; i < tamanhoAtual; i++) {
                String comando = filaDeGravacao.remover();
                System.out.println("> " + comando);
                processarComandoGravado(comando, avaliadorPlay);
                filaDeGravacao.inserir(comando);
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
    
    private void processarComandoGravado(String comando, Avaliador avaliador) throws Exception {
        String upperCmd = comando.trim().toUpperCase();

        if (upperCmd.equals("VARS")) {
            this.listarVariaveis();
        } else if (upperCmd.equals("RESET")) {
            this.reiniciarVariaveis();
        } else if (upperCmd.contains("=")) {
            String[] partes = comando.replaceAll(" ", "").split("=");
            this.definirValor(partes[0].charAt(0), Double.parseDouble(partes[1]));
        } else {
            avaliador.setExpressao(comando);
            if(avaliador.verificar()) {
                double resultado = avaliador.resolver(this);
                if (resultado == (long) resultado) System.out.println((long) resultado);
                else System.out.println(resultado);
            } else {
                throw new Exception("Operador inválido.");
            }
        }
    }
}