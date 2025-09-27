// ENRIQUE CIPOLLA MARTINS - 10427834
// HENRIQUE FERREIRA MARCIANO - 10439797

public class Pilha<T>{
    private T[] dados;
    private int topo;
    private int capacidade;

    public Pilha(){
        this(50);
    }

    public Pilha(int capacidade){
        this.capacidade = capacidade;
        this.dados = (T[]) new Object[capacidade];
        this.topo = -1;
    }

    public boolean isEmpty(){
        return this.topo == -1;
    }

    public boolean isFull(){
        return this.topo == this.capacidade - 1;
    }

    public void push(T elemento) throws Exception{
        if (!this.isFull()){
            this.topo++;
            this.dados[this.topo] = elemento;
        } else {
            throw new Exception("Overflow - Estouro de Pilha");
        }
    }

    public T pop() throws Exception{
        if (!this.isEmpty()){
            T elementoRemovido = this.dados[this.topo];
            this.topo--;
            return elementoRemovido;
        } else{
            throw new Exception("Underflow - Esvaziamento de Pilha");
        }
    }

    public T peek() throws Exception{
        if (!this.isEmpty()){
            return this.dados[this.topo];
        } else{
            throw new Exception("Underflow - Esvaziamento de Pilha");
        }
    }
    
    public int sizeElements(){
        return this.topo + 1;
    }
}