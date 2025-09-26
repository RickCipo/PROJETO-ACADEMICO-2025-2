// ENRIQUE CIPOLLA MARTINS - 10427834
// HENRIQUE FERREIRA MARCIANO - 10439797

public class Pilha<T>{
    private T[] dados;
    private int topo;
    private int capacidade;

    // Construtor que inicializa a pilha com uma capacidade padrão
    public Pilha(){
        this(50); //Define uma capacidade padrão de 50, pode ser ajustada
    }

    //Construtor que permite definir a capacidade da pilha
    public Pilha(int capacidade){
        this.capacidade = capacidade;
        //A linha abaixo pode gerar um "warning", mas está correta para este caso
        this.dados = (T[]) new Object[capacidade];
        this.topo = -1; //A pilha começa vazia.
    }

    //Verifica se a pilha está vazia
    public boolean isEmpty(){
        return this.topo == -1;
    }

    //Verifica se a pilha está cheia
    public boolean isFull(){
        return this.topo == this.capacidade - 1;
    }

    // Adiciona um elemento no topo da pilha
    public void push(T elemento) throws Exception{
        if (!this.isFull()){
            this.topo++;
            this.dados[this.topo] = elemento;
        } else {
            throw new Exception("Overflow - Estouro de Pilha");
        }
    }

    // Remove e retorna o elemento do topo da pilha
    public T pop() throws Exception{
        if (!this.isEmpty()){
            T elementoRemovido = this.dados[this.topo];
            this.topo--;
            return elementoRemovido;
        } else{
            throw new Exception("Underflow - Esvaziamento de Pilha");
        }
    }

    // Retorna o elemento do topo da pilha sem removê-lo
    public T peek() throws Exception{
        if (!this.isEmpty()){
            return this.dados[this.topo];
        } else{
            throw new Exception("Underflow - Esvaziamento de Pilha");
        }
    }

    // Retorna a quantidade de elementos na pilha
    public int sizeElements(){
        return this.topo + 1;
    }
}