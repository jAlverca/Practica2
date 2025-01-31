import os

def print_graph(graph_json):
    # Ruta del archivo .js que se generará.
    path = os.path.abspath(
        os.path.join(os.path.dirname(__file__), "../static")
    )
    path = os.path.join(path, r"d3/grafo.js")

    data = ""

    # Generar nodos con etiquetas reales
    data += "var nodes = ["
    for node in graph_json["nodes"]:
        data += "{id: " + str(node["id"]) + ", label: '" + node["label"] + "'},"
    data += "];"

    # Generar aristas
    data += "var edges = ["
    for edge in graph_json["edges"]:
        data += "{from: " + str(edge["from"]) + ", to: " + str(edge["to"]) + "},"
    data += "];"

    # Configuración de visualización con vis.js
    data += "var container = document.getElementById('mynetwork');"
    data += "var data = {nodes: nodes, edges: edges};"
    data += "var options = {nodes: {shape: 'dot', size: 10}, physics: false};"
    data += "var network = new vis.Network(container, data, options);"

    with open(path, "w") as f:
        f.write(data)

    return path
