package controller.Dao;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import controller.tda.graph.Adyacencia;
import controller.tda.graph.GraphLabelNoDirect;
import controller.tda.list.LinkedList;
import models.Hospital;

public class GrafoDao {
    private GraphLabelNoDirect grafo;
    private HospitalDao hospitalControl;
    private String mediaPath;
    private String graphFile;

    public GrafoDao() {
        this.hospitalControl = new HospitalDao();
        this.mediaPath = Paths.get("media").toAbsolutePath().toString();
        this.graphFile = Paths.get(mediaPath, "grafo_hospital.json").toString();
    }

    public boolean isExistFile() {
        return new File(graphFile).exists();
    }

    @SuppressWarnings("unchecked")
    public void saveGraph() {
        try {
            JSONObject grafoDict = new JSONObject();
            grafoDict.put("num_vertex", grafo.nro_vertices());
            grafoDict.put("num_edges", grafo.nro_edges());

            JSONArray listAdjacent = new JSONArray();
            LinkedList<Integer> aristas = new LinkedList<>();

            for (int i = 1; i <= grafo.nro_vertices(); i++) {
                LinkedList<Adyacencia> adyacentes = grafo.adyacencias(i);
                Adyacencia[] adyArray = adyacentes.toArray();
                if (adyArray != null && adyArray.length > 0) {
                    for (Adyacencia edge : adyArray) {
                        int origenId = Math.min(i, edge.getDestination());
                        int destinoId = Math.max(i, edge.getDestination());

                        if (!containsEdge(aristas, origenId, destinoId)) {
                            aristas.addLast(origenId);
                            aristas.addLast(destinoId);

                            JSONObject adjacentObj = new JSONObject();
                            adjacentObj.put("vertex_id", origenId);
                            adjacentObj.put("destination_id", destinoId);
                            listAdjacent.add(adjacentObj);
                        }
                    }
                }
            }

            grafoDict.put("list_adjacent", listAdjacent);

            // Asegurarse que el directorio existe
            new File(mediaPath).mkdirs();

            try (FileWriter file = new FileWriter(graphFile)) {
                file.write(grafoDict.toJSONString());
                System.out.println("Grafo guardado en: " + graphFile);
            }
        } catch (Exception e) {
            System.out.println("Error al guardar el grafo: ");
            e.printStackTrace();
        }
    }

    public void loadGraph() {
        if (isExistFile()) {
            try {
                LinkedList<Hospital> hospitales = hospitalControl.getlistAll();
                JSONParser parser = new JSONParser();

                try (FileReader reader = new FileReader(graphFile)) {
                    JSONObject grafoDict = (JSONObject) parser.parse(reader);
                    grafo = new GraphLabelNoDirect(hospitales.getSize(), String.class);

                    // Primero asignar todas las etiquetas
                    for (Hospital hospital : hospitales.toArray()) {
                        System.out.println("Asignando etiqueta: ID " + hospital.getId() +
                                " -> " + hospital.getNombre());
                        grafo.labelsVertices(hospital.getId(), hospital.getNombre());
                    }

                    // Verificar que las etiquetas se asignaron correctamente
                    if (!grafo.isLabelsGraph()) {
                        throw new Exception("Error: No todas las etiquetas fueron asignadas");
                    }

                    // Luego agregar las aristas
                    JSONArray listAdjacent = (JSONArray) grafoDict.get("list_adjacent");
                    for (Object obj : listAdjacent) {
                        JSONObject edge = (JSONObject) obj;
                        Hospital origen = hospitalControl.busquedaBinaria("id", edge.get("vertex_id").toString());
                        Hospital destino = hospitalControl.busquedaBinaria("id", edge.get("destination_id").toString());

                        double peso = hospitalControl.getDistance(
                                origen.getLatitud(),
                                origen.getLongitud(),
                                destino.getLatitud(),
                                destino.getLongitud());

                        grafo.insertEdgeL(origen.getNombre(), destino.getNombre(), (float) peso);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error durante la carga del grafo: " + e.getMessage());
                e.printStackTrace();
                createGraph();
            }
        } else {
            System.out.println("No existe el archivo");
            createGraph();
        }
    }

    public void createGraph() {
        try {
            LinkedList<Hospital> lista = hospitalControl.getlistAll();
            if (!lista.isEmpty()) {
                grafo = new GraphLabelNoDirect(lista.getSize(), String.class);
                Hospital[] array = lista.toArray();

                // Asignar etiquetas
                for (int i = 0; i < lista.getSize(); i++) {
                    System.out.println("Asignando etiqueta: índice " + (i) + " -> " + array[i].getNombre());
                    grafo.labelsVertices(i, array[i].getNombre());
                }

                // Verificar que las etiquetas se asignaron correctamente
                if (!grafo.isLabelsGraph()) {
                    throw new Exception("Error: No todas las etiquetas fueron asignadas");
                }

                System.out.println("Grafo creado con " + lista.getSize() + " vértices");
                saveGraph();
            }
        } catch (Exception e) {
            System.out.println("Error al crear el grafo: ");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void insertEdge(Hospital origen, Hospital destino, float peso) throws Exception {
        try {
            grafo.insertEdgeL(origen.getNombre(), destino.getNombre(), peso);
        } catch (Exception e) {
            System.out.println("Error al insertar arista: ");
            e.printStackTrace();
            throw e;
        }
    }

    public Float[][] getAdjacencyMatrix() {
        int numVertex = grafo.nro_vertices();
        Float[][] matrix = new Float[numVertex][numVertex];
        for (int i = 0; i < numVertex; i++) {
            for (int j = 0; j < numVertex; j++) {
                matrix[i][j] = 0f;
            }
        }
        try {
            for (int i = 1; i <= numVertex; i++) {
                LinkedList<Adyacencia> adyacentes = grafo.adyacencias(i);
                if (!adyacentes.isEmpty()) {
                    for (Adyacencia edge : adyacentes.toArray()) {
                        matrix[i - 1][edge.getDestination() - 1] = edge.getWeight();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return matrix;
    }

    private boolean containsEdge(LinkedList<Integer> aristas, int origen, int destino) {
        Integer[] array = aristas.toArray();
        if (array != null && array.length > 0) {
            for (int i = 0; i < array.length; i += 2) {
                if (array[i] == origen && array[i + 1] == destino) {
                    return true;
                }
            }
        }
        return false;
    }

    public GraphLabelNoDirect getGrafo() {
        return grafo;
    }

    public void printGraphState() {

        for (int i = 1; i <= grafo.nro_vertices(); i++) {
            System.out.println("Vértice " + i + ": " + grafo.getLabelL(i));
            LinkedList<Adyacencia> adyacentes = grafo.adyacencias(i);
            if (!adyacentes.isEmpty()) {
                for (Adyacencia ady : adyacentes.toArray()) {
                    System.out.println("  -> " + grafo.getLabelL(ady.getDestination()) +
                            " (peso: " + ady.getWeight() + ")");
                }
            }
        }
    }

    public JSONObject getGraphAsJson() {
        JSONObject grafoDict = new JSONObject();
        grafoDict.put("num_vertex", grafo.nro_vertices());
        grafoDict.put("num_edges", grafo.nro_edges());

        // Agregar nodos con etiquetas
        JSONArray nodesArray = new JSONArray();
        for (int i = 1; i <= grafo.nro_vertices(); i++) {
            JSONObject nodeObj = new JSONObject();
            nodeObj.put("id", i);
            nodeObj.put("label", grafo.getLabelL(i)); // Obtener la etiqueta del nodo
            nodesArray.add(nodeObj);
        }
        grafoDict.put("nodes", nodesArray);

        // Agregar aristas
        JSONArray listAdjacent = new JSONArray();
        LinkedList<Integer> aristas = new LinkedList<>();

        for (int i = 1; i <= grafo.nro_vertices(); i++) {
            LinkedList<Adyacencia> adyacentes = grafo.adyacencias(i);
            if (!adyacentes.isEmpty()) {
                for (Adyacencia edge : adyacentes.toArray()) {
                    int origenId = Math.min(i, edge.getDestination());
                    int destinoId = Math.max(i, edge.getDestination());

                    if (!containsEdge(aristas, origenId, destinoId)) {
                        aristas.addLast(origenId);
                        aristas.addLast(destinoId);

                        JSONObject adjacentObj = new JSONObject();
                        adjacentObj.put("from", origenId);
                        adjacentObj.put("to", destinoId);
                        listAdjacent.add(adjacentObj);
                    }
                }
            }
        }

        grafoDict.put("edges", listAdjacent);
        return grafoDict;
    }

    //Metodo Floyd
    public Float[][] floyd() {
        Float[][] matriz = getAdjacencyMatrix();
        int n = matriz.length;
        Float[][] dist = new Float[n][n];
        int[][] next = new int[n][n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(matriz[i], 0, dist[i], 0, n);
            for (int j = 0; j < n; j++) {
                if (i != j && dist[i][j] != 0) {
                    next[i][j] = i;
                } else {
                    next[i][j] = -1;
                }
            }
        }

        for (int k = 0; k < n; k++) {
            Float[][] distAux = new Float[n][n];
            int[][] nextAux = new int[n][n];
            for (int i = 0; i < n; i++) {
                System.arraycopy(dist[i], 0, distAux[i], 0, n);
                System.arraycopy(next[i], 0, nextAux[i], 0, n);
            }

            for (int i = 0; i < n; i++) {
                if (i != k) {
                    for (int j = 0; j < n; j++) {
                        if (j != k) {
                            if (dist[i][k] != 0 && dist[k][j] != 0) {
                                if (dist[i][j] == 0 || dist[i][j] > dist[i][k] + dist[k][j]) {
                                    distAux[i][j] = dist[i][k] + dist[k][j];
                                    nextAux[i][j] = next[k][j];
                                }
                            }
                        }
                    }
                }
            }

            for (int i = 0; i < n; i++) {
                System.arraycopy(distAux[i], 0, dist[i], 0, n);
                System.arraycopy(nextAux[i], 0, next[i], 0, n);
            }
        }

        return dist;
    }


    

}