package controller.stack;

import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

public class OperationStack<E> extends LinkedList<E> {
    private Integer top;
    
    public OperationStack() {
        this.top = top;
    }

    public Boolean verify(){
        return getSize() <= 0;
    }

    public void push(E datos) throws ListEmptyException{
        if(verify()){
            add(datos);
        }else{
            throw new ListEmptyException("La pila esta llena");
        }
    }

    public E pop() throws ListEmptyException{
        if(verify()){
            throw new ListEmptyException("La pila esta vacia");
        }else{
            return deleteLast();
        }
    }

    Integer getTop() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
