from email import utils
from flask import Blueprint, json, render_template, request, redirect, flash
from flask import jsonify  # para el debug, opcional
import requests

from utils.Utils import print_graph

router = Blueprint("router", __name__)


@router.route('/', methods=['GET'])
def list_hospitales(msg=''):
    r = requests.get("http://localhost:8083/api/hospital/list")
    print("Status Code:", r.status_code)
    if r.status_code == 200:
        try:
            data = r.json()
            lista = data["data"] if data["data"] is not None else []
            return render_template('index.html', lista=lista)
        except requests.exceptions.JSONDecodeError:
            print("Error: la respuesta no es un JSON válido.")
            return jsonify({"error": "Respuesta no es JSON válida"}), 500
    else:
        print("Error en la respuesta:", r.status_code)
        return jsonify({"error": "Error en la respuesta del servidor"}), 500

@router.route('/hospital/save', methods=['POST'])
def save_proyecto():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataF = {
        "nombre": form["nombre"],
        "direccion": form["direccion"],
        "longitud": form["longitud"],
        "latitud": form["latitud"]
    }
    print("Data to be sent:", dataF)
    try:
        response = requests.post("http://localhost:8083/api/hospital/save", headers=headers, json=dataF)
        print("Response Status Code:", response.status_code)
        if response.status_code == 200:
            flash("Hospital guardada con éxito", "success")
            return redirect('/')
        else:
            flash("Error al guardar la hospital", "danger")
    except requests.exceptions.RequestException as e:
        print("Error en la solicitud:", e)
        flash("Error en la solicitud: " + str(e), "danger")
        return redirect('/')
    
@router.route('/hospital/register')
def register():
    r = requests.get("http://localhost:8083/api/hospital/list")
    data = r.json()
    return render_template('/persona/guardar.html', lista = data["data"])


#* Grafos
@router.route('/administrar_grafo/')
def administrar_grafo():
    r = requests.get("http://localhost:8083/api/hospital/list")
    if r == None:
        return render_template('administrar_grafo.html', hospitales = [])
    return render_template('administrar_grafo.html', hospitales = r.json()["data"])

@router.route('/cargar_grafo/')
def cargar_grafo():
    r = requests.get("http://localhost:8083/api/grafo/cargar_grafo")
    data = r.json()
    print_graph(data)
    return render_template('grafo.html')

