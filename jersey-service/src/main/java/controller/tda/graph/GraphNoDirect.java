package controller.tda.graph;

public class GraphNoDirect extends GraphDirect {
    public GraphNoDirect(Integer nro_vertices) {
        super(nro_vertices);
    }

    public void add_edge(Integer v1, Integer v2, Float weight) throws Exception {
        if (v1.intValue() <= nro_vertices() && v2.intValue() <= nro_vertices()) {
            if (!is_edge(v1, v2)) {
                Adyacencia ady = new Adyacencia(v2, weight);
                setNro_edges(nro_edges() + 1);

                Adyacencia aux = new Adyacencia();
                aux.setDestination(v2);
                aux.setWeight(weight);
                getListAdyacencias()[v1].add(aux);

                Adyacencia auxD = new Adyacencia();
                aux.setDestination(v1);
                aux.setWeight(weight);
                getListAdyacencias()[v1].add(auxD);

            }
        }
    }
}
