package controller.queque;
import controller.tda.list.ListEmptyException;
import controller.tda.list.LinkedList;


public class OperationQueuque<E> extends LinkedList<E> {
    private Integer top;

    public OperationQueuque(Integer top) {
        this.top = top;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Boolean verify(){
        return getSize() <= getTop();
    }

    public void queuque(E data) throws ListEmptyException {
        if(verify()){
            addLast(data);
        }else{
            throw new ListEmptyException("La cola esta llena");
        }
    }

    public E dequeuque() throws ListEmptyException {
        if(verify()){
            throw new ListEmptyException("La cola esta vacia");
        }else{
            return deleteFirst();
        }
    }   
}
