package controller.queque;

import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

public class Queuque<E> extends LinkedList<E> {
    private OperationQueuque<E> queuque;

    public Queuque(Integer top) {
        this.queuque = new OperationQueuque<>(top);
    }

    public Integer getTop(){
        return this.queuque.getSize();
    }

    public void queuque(E data) throws ListEmptyException {
        this.queuque.queuque(data);
    }

    public void clear(){
        this.queuque.reset();
    }
    
    public E dequeuque() throws ListEmptyException {
        return this.queuque.dequeuque();
    }
    
}
