// ENRIQUE CIPOLLA MARTINS - 10427834
// HENRIQUE FERREIRA MARCIANO - 10439797

public class Fila{
    // Especifica um limite de 10 comandos na fila de gravação [cite: 59]
    private final int TAM_FILA = 10;
    private String fila[];
    private int inicio;
    private int fim;
    private int total; // Contador para controlar o número de elementos

    public Fila(){
        this.fila = new String[TAM_FILA];
        this.inicio = 0;
        this.fim = 0;
        this.total = 0; // A fila começa com zero elementos
    }

    // Verifica se a fila está vazia
    public boolean isEmpty(){
        return this.total == 0;
    }

    // Verifica se a fila está cheia
    public boolean isFull(){
        return this.total == this.fila.length;
    }

    // Adiciona um elemento no fim da fila (de forma circular)
    public void inserir(String i) throws Exception{
        if (!this.isFull()){
            this.fila[this.fim] = i;
            // A mágica do circular: quando 'fim' chega no final, ele volta para 0.
            this.fim = (this.fim + 1) % this.fila.length;
            this.total++;
        } else{
            throw new Exception("Overflow - A fila está cheia");
        }
    }

    // Remove e retorna o elemento do início da fila (de forma circular)
    public String remover() throws Exception{
        if (!this.isEmpty()){
            String elemento = this.fila[this.inicio];
            // A mágica do circular: quando 'inicio' chega no final, ele volta para 0.
            this.inicio = (this.inicio + 1) % this.fila.length;
            this.total--;
            return elemento;
        } else{
            throw new Exception("Underflow - A fila está vazia");
        }
    }

    // Retorna o elemento do início da fila sem removê-lo
    public String frente() throws Exception{
        if (!this.isEmpty()){
            return this.fila[this.inicio];
        } else{
            throw new Exception("Underflow - A fila está vazia");
        }
    }

    // Retorna a quantidade de elementos atualmente na fila
    public int sizeElements(){
        return this.total;
    }
}
