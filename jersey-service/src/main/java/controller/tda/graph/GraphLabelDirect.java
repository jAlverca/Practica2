package controller.tda.graph;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.HashMap;

import controller.tda.list.LinkedList;

public class GraphLabelDirect<E> extends GraphDirect {
    protected E labels[];
    protected HashMap<E, Integer> dictVertices;
    private Class<E> clazz;

    public GraphLabelDirect(Integer nro_vertices, Class<E> clazz) {
        super(nro_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, nro_vertices + 1);
        dictVertices = new HashMap<>();

    }

    public Boolean is_edgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            return super.is_edge(getVerticeL(v1), getVerticeL(v2));
        } else {
            throw new Exception("No se han asignado etiquetas a los vertices");
        }
    }

    public void insertEdgeL(E v1, E v2, Float weight) throws Exception {
        if (isLabelsGraph()) {
            add_edge(getVerticeL(v1), getVerticeL(v2), weight);
        } else {
            throw new Exception("No se han asignado etiquetas a los vertices");
        }
    }

    public void insertEdgeL(E v1, E v2) throws Exception {
        if (isLabelsGraph()) {
            add_edge(getVerticeL(v1), getVerticeL(v2), Float.NaN);
        } else {
            throw new Exception("No se han asignado etiquetas a los vertices");
        }
    }

    public LinkedList<Adyacencia> adyancencias(E v) throws Exception {
        if (isLabelsGraph()) {
            return adyacencias(getVerticeL(v));
        } else {
            throw new Exception("No se han asignado etiquetas a los vertices");
        }
    }

    public void labelsVertices(Integer v, E data) {
        labels[v] = data;
        dictVertices.put(data, v);
    }

    public Boolean isLabelsGraph() {
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    public Integer getVerticeL(E label) {
        return dictVertices.get(label);
    }

    public E getLabelL(Integer v1) {
        return labels[v1];
    }

    @Override
    public String toString() {
        String grafo = "";
        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo += "V" + i + " [" + getLabelL(i).toString() + "]" + "\n";
                LinkedList<Adyacencia> lista = this.adyacencias(i);
                if (!lista.isEmpty()) { // Aquí el cambio
                    Adyacencia[] ady = lista.toArray();
                    for (int j = 0; j < ady.length; j++) { // también corregí i por j en el for
                        Adyacencia a = ady[j];
                        grafo += "ady V" + a.getDestination() + "(" + a.getWeight() + ")\n";
                    }
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return grafo;
    }

    public String drawGrafo() {
        String grafo = "var nodes = new vis.DataSet([";

        try {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                grafo += "{ id: " + i + ", label: " + i + '"' + getLabelL(i).toString() + '"' + " }, " + "\n";
            }
            grafo += "]}; " + " \n";

            grafo += "var edges = new vis.DataSet([" + "\n";
            for (int i = 1; i <= this.nro_vertices(); i++) {
                LinkedList<Adyacencia> lista = adyacencias(i);
            }

            grafo += "]}; " + " \n";

            grafo += "var container = document.getElementById('mynetwork');\n";
            grafo += "var data = {" + "\n";
            grafo += "nodes: nodes," + "\n";
            grafo += "edges: edges, " + "\n";
            grafo += "};\n";
            grafo += "var options = {};\n";
            grafo += "var network = new vis.Network(container, data, options);\n";

            FileWriter file = new FileWriter("resources" + File.separator + "graph" + File.separator + "grafo.js");
            file.write(grafo);
            file.flush();
            file.close();

        } catch (Exception e) {
            // TODO: handle exception
        }
        return grafo;
    }

    // Metodo de recorrdio Floyd

    public Float[][] caminofloyd() {
        Float[][] matriz = new Float[this.nro_vertices() + 1][this.nro_vertices() + 1];
        for (int i = 1; i <= this.nro_vertices(); i++) {
            for (int j = 1; j <= this.nro_vertices(); j++) {
                if (i == j) {
                    matriz[i][j] = 0f;
                } else {
                    matriz[i][j] = Float.POSITIVE_INFINITY;
                }
            }
        }

        for (int i = 1; i <= this.nro_vertices(); i++) {
            LinkedList<Adyacencia> lista = adyacencias(i);
            if (!lista.isEmpty()) {
                Adyacencia[] ady = lista.toArray();
                for (int j = 0; j < ady.length; j++) {
                    Adyacencia a = ady[j];
                    matriz[i][a.getDestination()] = a.getWeight();
                }
            }
        }

        for (int k = 1; k <= this.nro_vertices(); k++) {
            for (int i = 1; i <= this.nro_vertices(); i++) {
                for (int j = 1; j <= this.nro_vertices(); j++) {
                    if (matriz[i][j] > matriz[i][k] + matriz[k][j]) {
                        matriz[i][j] = matriz[i][k] + matriz[k][j];
                    }
                }
            }
        }
        return matriz;
    }

}
