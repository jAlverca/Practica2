package controller.tda.graph;

import controller.tda.list.LinkedList;

public class GraphDirect extends Graph {
    private Integer nro_vertices;
    private Integer nro_edges;
    private LinkedList<Adyacencia> listAdyacencias[];

    public GraphDirect(Integer nro_vertices) {
        this.nro_vertices = nro_vertices;
        this.nro_edges = 0;
        listAdyacencias = new LinkedList[nro_vertices + 1];
        for (int i = 1; i <= nro_vertices; i++) {
            listAdyacencias[i] = new LinkedList<Adyacencia>();
        }
    }

    public Integer nro_edges() {
        return this.nro_edges;
    }

    public Integer nro_vertices() {
        return this.nro_vertices;
    }

    public Boolean is_edge(Integer v1, Integer v2) {
        Boolean band = false;
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (!lista.isEmpty()) { // Cambiado de isEmpty() a !isEmpty()
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestination().intValue() == v2.intValue()) {
                        band = true;
                        break;
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Los vertices estan fuera de rango");
        }
        return band;
    }

    public Float wieght_edge(Integer v1, Integer v2) throws Exception {
        float weight = Float.NaN;
        if (is_edge(v1, v2)) {
            LinkedList<Adyacencia> lista = listAdyacencias[v1];
            if (lista.isEmpty()) {
                Adyacencia[] matrix = lista.toArray();
                for (int i = 0; i < matrix.length; i++) {
                    Adyacencia aux = matrix[i];
                    if (aux.getDestination().intValue() == v2.intValue()) {
                        weight = aux.getWeight();
                        break;
                    }
                }

            }
        }
        return weight;

    }

    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if (v1.intValue() <= nro_vertices && v2.intValue() <= nro_vertices) {
            if (!is_edge(v1, v2)) { // Cambiado de is_edge a !is_edge
                nro_edges++;
                Adyacencia aux = new Adyacencia();
                aux.setWeight(weight);
                aux.setDestination(v2);
                listAdyacencias[v1].add(aux);
            }
        } else {
            throw new IllegalArgumentException("Los vertices estan fuera de rango");
        }
    }

    public void add_edge(Integer v1, Integer v2) throws Exception {
        this.add_edge(v1, v2, Float.NaN);
    }

    @Override
    public LinkedList<Adyacencia> adyacencias(Integer v1) {
        return listAdyacencias[v1];
    }

    public void setNro_edges(Integer nro_edges) {
        this.nro_edges = nro_edges;
    }

    public LinkedList<Adyacencia>[] getListAdyacencias() {
        return this.listAdyacencias;
    }

    public void setListAdyacencias(LinkedList<Adyacencia>[] listAdyacencias) {
        this.listAdyacencias = listAdyacencias;
    }
}