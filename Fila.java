// ENRIQUE CIPOLLA MARTINS - 10427834
// HENRIQUE FERREIRA MARCIANO - 10439797

public class Fila{
    //Especifica um limite de 10 comandos na fila de gravação
    private final int TAM_FILA = 10;
    private String fila[];
    private int inicio; //Aponta para o primeiro elemento da fila
    private int fim;    //Aponta para a próxima posição livre no final da fila

    public Fila(){
        this.fila = new String[TAM_FILA];
        //Ambos começam em 0. A fila está vazia quando inicio == fim
        this.inicio = 0;
        this.fim = 0;
    }


    //Verifica se a fila está vazia
    public boolean isEmpty(){
        return this.inicio == this.fim;
    }
    

    //Verifica se a fila está cheia.
    public boolean isFull(){
        return this.fim == this.fila.length;
    }

    //Adiciona um elemento no fim da fila
    public void inserir(String i) throws Exception{
        if (!this.isFull()){
            this.fila[this.fim] = i;
            this.fim++;
        } else {
            throw new Exception("Overflow - A fila está cheia");
        }
    }

    //Remove e retorna o elemento do início da fila
    public String remover() throws Exception{
        if (!this.isEmpty()){
            String elemento = this.fila[this.inicio];
            this.inicio++;
            return elemento;
        } else{
            throw new Exception("Underflow - A fila está vazia");
        }
    }

    //Retorna o elemento do início da fila sem removê-lo
    public String frente() throws Exception{
        if (!this.isEmpty()){
            return this.fila[this.inicio];
        } else{
            throw new Exception("Underflow - A fila está vazia");
        }
    }
    
    //Retorna a quantidade de elementos atualmente na fila
    public int sizeElements(){
        return this.fim - this.inicio;
    }
}