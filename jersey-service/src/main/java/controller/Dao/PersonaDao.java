package controller.Dao;

import models.Persona;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class PersonaDao extends AdapterDao<Persona> {
    private Persona persona = new Persona();
    private LinkedList<Persona> listAll;

    public PersonaDao() {
        super(Persona.class);
    }

    public Persona getPersona() {
        if (persona == null) {
            persona = new Persona();
        }
        return this.persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public LinkedList<Persona> getlistAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize();
        persona.setIdPersona(id);
        this.persist(this.persona);
        this.listAll = listAll();
        return true;
    }

    public LinkedList order(Integer type_order, String atributo) {
        LinkedList listita = listAll();
        if (!listAll().isEmpty()) {
            Persona[] lista = (Persona[]) listita.toArray();
            listita.reset();
            for (int i = 1; i < lista.length; i++) {
                Persona aux = lista[i]; // valor a ordenar
                int j = i - 1; // índice anterior
                while (j >= 0 && (verify(lista[j], aux, type_order, atributo))) {
                    lista[j + 1] = lista[j--]; // desplaza elementos hacia la derecha
                }
                lista[j + 1] = aux; // inserta el valor en su posición correcta
            }

            listita.toList(lista);
        }
        return listita;
    }

    private Boolean verify(Persona a, Persona b, Integer type_order, String atributo) {
        if (type_order == 1) {
            if (atributo.equalsIgnoreCase("apellidos")) {
                return a.getApellido().compareTo(b.getApellido()) > 0;
            } else if (atributo.equalsIgnoreCase("nombres")) {
                return a.getNombre().compareTo(b.getNombre()) > 0;
            } else if (atributo.equalsIgnoreCase("id")) {
                return a.getIdPersona() > b.getIdPersona();
            }
        } else {
            if (atributo.equalsIgnoreCase("apellidos")) {
                return a.getApellido().compareTo(b.getApellido()) < 0;
            } else if (atributo.equalsIgnoreCase("nombres")) {
                return a.getNombre().compareTo(b.getNombre()) < 0;
            } else if (atributo.equalsIgnoreCase("id")) {
                return a.getIdPersona() < b.getIdPersona();
            }
        }
        return false;
    }

    public LinkedList<Persona> buscar_nombre(String texto) {
        LinkedList<Persona> lista = new LinkedList<>();
        LinkedList<Persona> listita = listAll();
        if (!listita.isEmpty()) {
            Persona[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {

                if (aux[i].getNombre().toLowerCase().startsWith(texto.toLowerCase())) {
                    // System.out.println("**** "+aux[i].get);
                    lista.add(aux[i]);
                }
            }
        }
        return lista;
    }

    // Funcion para comparar los atribuitos
    private boolean compare(Persona a, Persona b, Integer type, String field) {
        if (type == 1) {
            if (field.equalsIgnoreCase("apellido")) {
                return a.getApellido().compareTo(b.getApellido()) < 0;
            } else if (field.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) < 0;
            } else if (field.equalsIgnoreCase("telefono")) {
                return a.getNombre().compareTo(b.getNombre()) < 0;
            } else if (field.equalsIgnoreCase("id")) {
                return a.getIdPersona() < b.getIdPersona();
            }
        } else {
            if (field.equalsIgnoreCase("apellido")) {
                return a.getApellido().compareTo(b.getApellido()) > 0;
            } else if (field.equalsIgnoreCase("nombre")) {
                return a.getNombre().compareTo(b.getNombre()) > 0;
            } else if (field.equalsIgnoreCase("telefono")) {
                return a.getNombre().compareTo(b.getNombre()) > 0;
            } else if (field.equalsIgnoreCase("id")) {
                return a.getIdPersona() > b.getIdPersona();
            }
        }
        return false;
    }

    // Metodo de ordenacion Quicksort

    public LinkedList<Persona> quickSort(LinkedList<Persona> lista, Integer type, String field) throws Exception {

        Persona[] m = lista.toArray();
        quicksort(m, 0, m.length - 1, type, field);
        lista.toList(m);
        return lista;
    }

    private void quicksort(Persona[] m, int low, int high, Integer type, String field) {
        if (low < high) {
            int pivotIndex = partition(m, low, high, type, field);
            quicksort(m, low, pivotIndex - 1, type, field);
            quicksort(m, pivotIndex + 1, high, type, field);
        }
    }

    private int partition(Persona[] array, int low, int high, Integer type, String field) {
        Persona pivote = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(array[j], pivote, type, field)) {
                i++;
                swap(array, i, j);
            }
        }

        swap(array, i + 1, high);
        return i + 1;
    }

    private void swap(Persona[] array, int i, int j) {
        Persona temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    

    // Metodo de ordenacion MergeSort

    public LinkedList<Persona> mergeSort(Integer type, String field) throws Exception {
        LinkedList<Persona> lista = listAll();
        Persona[] array = (Persona[]) lista.toArray();
        mergeSort(array, 0, array.length - 1, type, field);
        lista.toList(array);
        return lista;
    }

    private void mergeSort(Persona[] array, int low, int high, Integer type, String field) {
        if (low < high) {
            int mid = (low + high) / 2;
            mergeSort(array, low, mid, type, field);
            mergeSort(array, mid + 1, high, type, field);
            merge(array, low, mid, high, type, field);
        }
    }

    private void merge(Persona[] array, int low, int mid, int high, Integer type, String field) {
        Persona[] left = new Persona[mid - low + 1];
        Persona[] right = new Persona[high - mid];

        for (int i = 0; i < left.length; i++) {
            left[i] = array[low + i];
        }

        for (int i = 0; i < right.length; i++) {
            right[i] = array[mid + 1 + i];
        }

        int i = 0, j = 0, k = low;
        while (i < left.length && j < right.length) {
            if (compare(left[i], right[j], type, field)) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }

    // Metodo de ordenacion ShellSort
    public LinkedList<Persona> shellSort(Integer type, String field) throws Exception {
        LinkedList<Persona> lista = listAll();
        Persona[] array = (Persona[]) lista.toArray();
        shellSort(array, type, field);
        lista.toList(array);
        return lista;
    }

    private void shellSort(Persona[] array, Integer type, String field) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                Persona temp = array[i];
                int j;
                for (j = i; j >= gap && compare(array[j - gap], temp, type, field); j -= gap) {
                    array[j] = array[j - gap];
                }
                array[j] = temp;
            }
        }
    }

    // Metodo ShellSort para ordenar numeros

    // Metodo de busqeuda binaria para cedula
    public Persona busquedaBinaria(String attribute, String value) throws Exception {
        LinkedList<Persona> listita = listAll();

        // Ordenar la lista por cedula usando el algoritmo de inserción
        Persona[] aux = listita.toArray();
        for (int i = 1; i < aux.length; i++) {
            Persona key = aux[i];
            int j = i - 1;
            while (j >= 0 && aux[j].getCedula().compareTo(key.getCedula()) > 0) {
                aux[j + 1] = aux[j];
                j = j - 1;
            }
            aux[j + 1] = key;
        }

        int low = 0;
        int high = aux.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (attribute.equalsIgnoreCase("cedula")) {
                if (aux[mid].getCedula().equals(value)) {
                    return aux[mid];
                } else if (aux[mid].getCedula().compareTo(value) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return null;
    }

    // Metodo de busqueda lineal binaria
    public LinkedList<Persona> busquedaLinealBinaria(String attribute, String value) throws Exception {

        LinkedList<Persona> lista = new LinkedList<>();
        LinkedList<Persona> listita = listAll();
        if (!listita.isEmpty()) {
            Persona[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (attribute.equalsIgnoreCase("id")) {
                    if (aux[i].getIdPersona() == Integer.parseInt(value)) {
                        lista.add(aux[i]);
                    }
                } else if (attribute.equalsIgnoreCase("nombre")) {
                    if (aux[i].getNombre().equalsIgnoreCase(value)) {
                        lista.add(aux[i]);
                    }
                } else if (attribute.equalsIgnoreCase("apellido")) {
                    if (aux[i].getApellido().equalsIgnoreCase(value)) {
                        lista.add(aux[i]);
                    }
                } else if (attribute.equalsIgnoreCase("telefono")) {
                    if (aux[i].getTelefono().equalsIgnoreCase(value)) {
                        lista.add(aux[i]);
                    }
                }
            }
        }
        return lista;
    }

}
