package controller.tda.list;


public class LinkedList<E> {
    private Node<E> header; // Nodo cabecera (el primer nodo de la lista)
    private Node<E> last; // Nodo último (el último nodo de la lista)
    private Integer size; // Tamaño de la lista (cuenta el número de nodos en la lista)

    // Constructor de la clase LinkedList
    public LinkedList() {
        this.header = null; // Inicialmente, la cabecera es nula (no hay nodos en la lista)
        this.last = null; // Inicialmente, el último nodo es nulo
        this.size = 0; // Inicialmente, el tamaño de la lista es 0
    }

    // Método para verificar si la lista está vacía
    public Boolean isEmpty() {
        // Retorna verdadero si la cabecera es nula o el tamaño es 0, es decir, si la
        // lista está vacía
        return (this.header == null || this.size == 0);
    }

    // Método privado para agregar un elemento al principio de la lista
    private void addHeader(E dato) {
        Node<E> help; // Nodo de ayuda para insertar el nuevo dato

        // Si la lista está vacía
        if (isEmpty()) {
            help = new Node<>(dato); // Crea un nuevo nodo con el dato
            header = help; // El nuevo nodo se convierte en el nodo cabecera
            this.size++; // Incrementa el tamaño de la lista
        } else {
            // Si la lista no está vacía
            Node<E> healpHeader = this.header; // Guarda el nodo cabecera actual en una variable auxiliar
            help = new Node<>(dato, healpHeader); // Crea un nuevo nodo que apunta al nodo cabecera actual
            this.header = help; // El nuevo nodo se convierte en la nueva cabecera
        }
        this.size++; // Incrementa el tamaño de la lista
    }

    public void addLast(E info) {
        Node<E> help = new Node<>(info, null); // Crea un nuevo nodo
        if (isEmpty()) {
            header = help; // Si la lista está vacía, el nuevo nodo se convierte en la cabecera
            last = help; // Y también en el último
        } else {
            last.setNext(help); // Enlaza el nodo actual al nuevo nodo
            last = help; // El nuevo nodo se convierte en el último
        }
        this.size++; // Incrementa el tamaño
    }

    public void add(E info) {
        addLast(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (index == null) {
            throw new IllegalArgumentException("El índice no puede ser nulo.");
        }

        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index == 0) {
            return header.getInfo();
        } else if (index == this.size - 1) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            // Recorre hasta el nodo en el índice solicitado
            while (cont < index) {
                search = search.getNext();
                cont++;
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    /*** END BYPOSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data ");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo().toString()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class<?> clazz = header.getInfo().getClass(); // Obtiene la clase del tipo genérico
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, this.size); // Inicializa el array
            Node<E> aux = this.header; // Comienza desde el nodo cabecera

            for (int i = 0; i < this.size; i++) { // Itera hasta size
                if (aux != null) { // Verifica que aux no sea null antes de acceder a sus atributos
                    matrix[i] = aux.getInfo(); // Asigna el valor del nodo al array
                    aux = aux.getNext(); // Avanza al siguiente nodo
                }
            }
        }
        return matrix; // Retorna el array, que será null si la lista está vacía
    }

    public LinkedList<E> toList(E[] matrix) {
        reset();
        for (int i = 0; i < matrix.length; i++) {
            this.add(matrix[i]);
        }
        return this;
    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else {
            E info = header.getInfo();
            Node<E> aux = header.getNext();
            this.header = aux;
            if (this.size == 1) {
                this.last = null;
            }
            size--;
            return info;
        }
    }

    public E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else {
            E info = last.getInfo();
            if (this.size == 1) {
                reset();
            } else {
                Node<E> aux = getNode(size - 2);
                aux.setNext(null);
                last = aux;
                size--;
            }
            return info;
        }
    }

    public E delete(Integer index) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() == 0) {
            return deleteFirst();
        } else if (index.intValue() == (this.size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(index - 1);
            Node<E> delete = preview.getNext();
            preview.setNext(delete.getNext());
            size--;
            return delete.getInfo();
        }
    }

    public void update(E data, Integer pos) throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista Vacia");
        } else if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException("Error, esta fuera de los limites");
        } else if (pos == 0) {
            header.setInfo(data);
        } else if (pos == (size - 1)) {
            last.setInfo(data);
        } else {
            Node<E> search = header;
            Integer cont = 0;
            while (cont < pos) {
                cont++;
                search = search.getNext();
            }
            search.setInfo(data);
        }
    }

    public LinkedList<E> cloneList() {
        LinkedList<E> clone = new LinkedList<>();
        Node<E> aux = header;
        while (aux != null) {
            clone.add(aux.getInfo());
            aux = aux.getNext();
        }
        return clone;
    }

    public void shellSort(Integer type) {
        int n = this.size;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                try {
                    E temp = get(i);
                    int j;
                    for (j = i; j >= gap && compare(get(j - gap), temp, type); j -= gap) {
                        update(get(j - gap), j);
                    }
                    update(temp, j);
                } catch (ListEmptyException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void quickSort(Integer type) {
        quickSort(0, this.size - 1, type);
    }

    private void quickSort(int low, int high, Integer type) {
        if (low < high) {
            try {
                int pi = partition(low, high, type);
                quickSort(low, pi - 1, type);
                quickSort(pi + 1, high, type);
            } catch (ListEmptyException e) {
                e.printStackTrace();
            }
        }
    }

    private int partition(int low, int high, Integer type) throws ListEmptyException {
        E pivot = get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (compare(get(j), pivot, type)) {
                i++;
                E temp = get(i);
                update(get(j), i);
                update(temp, j);
            }
        }
        E temp = get(i + 1);
        update(get(high), i + 1);
        update(temp, high);
        return i + 1;
    }

    public void mergeSort(Integer type) {
        mergeSort(0, this.size - 1, type);
    }

    private void mergeSort(int l, int r, Integer type) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(l, m, type);
            mergeSort(m + 1, r, type);
            try {
                merge(l, m, r, type);
            } catch (ListEmptyException e) {
                e.printStackTrace();
            }
        }
    }

    private void merge(int l, int m, int r, Integer type) throws ListEmptyException {
        int n1 = m - l + 1;
        int n2 = r - m;
        E L[] = (E[]) new Object[n1];
        E R[] = (E[]) new Object[n2];
        for (int i = 0; i < n1; i++) {
            L[i] = get(l + i);
        }
        for (int j = 0; j < n2; j++) {
            R[j] = get(m + 1 + j);
        }
        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (compare(L[i], R[j], type)) {
                update(L[i], k);
                i++;
            } else {
                update(R[j], k);
                j++;
            }
            k++;
        }
        while (i < n1) {
            update(L[i], k);
            i++;
            k++;
        }
        while (j < n2) {
            update(R[j], k);
            j++;
            k++;
        }
    }

    public Integer busquedaLinealBinaria(E value) {
        Node<E> aux = header;
        Integer cont = 0;
        while (aux != null) {
            if (aux.getInfo().equals(value)) {
                return cont;
            }
            cont++;
            aux = aux.getNext();
        }
        return -1;
    }

    public Integer busquedeBinaria(E value) {
        return busquedeBinaria(value, 0, this.size - 1);
    }

    private Integer busquedeBinaria(E value, Integer low, Integer high) {
        if (high >= low) {
            try {
                int mid = low + (high - low) / 2;
                if (get(mid).equals(value)) {
                    return mid;
                }
                if (compare(get(mid), value, 1)) {
                    return busquedeBinaria(value, low, mid - 1);
                }
                return busquedeBinaria(value, mid + 1, high);
            } catch (ListEmptyException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private boolean compare(E a, E b, Integer type) {
        if (type == 1) {
            return ((Comparable<E>) a).compareTo(b) > 0;
        } else {
            return ((Comparable<E>) a).compareTo(b) < 0;
        }
    }
    
}


