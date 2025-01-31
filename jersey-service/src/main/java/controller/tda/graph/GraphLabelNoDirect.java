package controller.tda.graph;

public class GraphLabelNoDirect<E> extends GraphLabelDirect<E> {
    public GraphLabelNoDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices, clazz);
    }

    @Override
    public Boolean is_edgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            return super.is_edgeL(v1, v2);
        } else {
            throw new Exception("No se han asignado etiquetas a los vertices");
        }
    }

    public void insertEdgeL(E v1, E v2, Float weight) throws Exception {
        if (isLabelsGraph()) {
            System.out.println("Insertando arista: " + v1 + " -> " + v2 + " (peso: " + weight + ")");
            add_edge(getVerticeL(v1), getVerticeL(v2), weight);
            add_edge(getVerticeL(v2), getVerticeL(v1), weight);

        } else {
            System.out.println("No se han asignado etiquetas a los vertices");
        }
    }
}