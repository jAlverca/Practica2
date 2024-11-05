from flask import Blueprint, json, render_template, request, redirect, flash
from flask import jsonify  # para el debug, opcional
import requests
import datetime

router = Blueprint("router", __name__)


@router.route('/', methods=['GET'])
def list_person(msg=''):
    r = requests.get("http://localhost:8083/api/person/list")
    print("Status Code:", r.status_code)

    # Comprueba el código de estado antes de intentar parsear JSON
    if r.status_code == 200:
        try:
            data = r.json()
            print("Data:", data)
            return render_template('index.html', lista=data["data"])
        except requests.exceptions.JSONDecodeError:
            print("Error: la respuesta no es un JSON válido.")
            return jsonify({"error": "Respuesta no es JSON válida"}), 500
    else:
        print("Error en la respuesta:", r.status_code)
        return jsonify({"error": "Error en la respuesta del servidor"}), 500


@router.route('/person/save', methods=['POST'])
def save_person():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    dataF = {"nombre": form['nombre'], "apellido": form['apellido'], "cedula": form['cedula'], "telefono": form['telefono']}

    r = requests.post("http://localhost:8083/api/person/save", data=json.dumps(dataF), headers=headers)
    dat = r.json()
    if r.status_code == 200:
        flash('Persona guardada correctamente', category='info')
        # Registrar transacción
        dataT = {"tipo": "CREATE", "tabla": "Persona", "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "detalles": f"Se ha creado la persona {form['nombre']} {form['apellido']}" }
        tr = requests.post("http://localhost:8083/api/transaccion/save", data=json.dumps(dataT), headers=headers)
        
        if tr.status_code == 200:
            flash('Transacción guardada correctamente', category='info')
            
        return redirect('/')
        
    else:
        flash('Error al guardar persona', category='error')
        return redirect('/')
    
@router.route('/person/register')
def register():
    r = requests.get("http://localhost:8083/api/person/list")
    data = r.json()
    return render_template('/persona/guardar.html', lista = data["data"])

@router.route('/person/edit/<int:idPersona>', methods=['GET', 'POST'])
def edit_person(idPersona):
    print(request.method)
    print(f"ID: {idPersona}")
    
    if request.method == 'GET':
        r = requests.get(f"http://localhost:8083/api/person/get/{idPersona}")
        data = r.json()
        print(data)
        return render_template('/persona/editar.html', person = data["data"])
    else:
        print("entro")
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataF = {"nombre": form['nombre'], "apellido": form['apellido'], "cedula": form['cedula'], "telefono": form['telefono']}
        r = requests.put(f"http://localhost:8083/api/person/update/{idPersona}", data=json.dumps(dataF), headers=headers)
        dat = r.json()
        if r.status_code == 200:
            flash('Persona actualizada correctamente', category='info')
            
            # Registrar transacción
            dataT = {"tipo": "UPDATE", "tabla": "Persona", "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "detalles": f"Se ha actualizado la persona {form['nombre']} {form['apellido']}" }
            tr = requests.post("http://localhost:8083/api/transaccion/save", data=json.dumps(dataT), headers=headers)
            
            if tr.status_code == 200:
                flash('Transacción guardada correctamente', category='info')
            
            return redirect('/')
        else:
            flash('Error al actualizar persona', category='error')
            return redirect('/')
        
@router.route('/person/delete/<int:idPersona>', methods=['POST'])
def delete_person(idPersona):
    
    print(f"ID a eliminar: {idPersona}")
    
    headers = {'Content-Type': 'application/json'}
    r = requests.delete(f"http://localhost:8083/api/person/delete/{idPersona}", headers=headers)
    dat = r.json()
        
    # Registrar transacción
    dataT = {"tipo": "DELETE", "tabla": "Persona", "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "detalles": f"Se ha eliminado la persona con ID {idPersona}" }
    tr = requests.post("http://localhost:8083/api/transaccion/save", data=json.dumps(dataT), headers=headers)
    
    if tr.status_code == 200:
        print("Transacción guardada correctamente")
        flash('Transacción guardada correctamente', category='info')
        return redirect('/')

#!Proyectos -----------------------------------------

@router.route('/proyecto/list')
def list_proyecto(msg=''):
    r = requests.get("http://localhost:8083/api/proyecto/list")
    data = r.json()
    return render_template('/proyecto/listaProyecto.html', lista = data["data"])

@router.route('/proyecto/save', methods=['POST'])
def save_proyecto():
    headers = {'Content-Type': 'application/json'}
    print("1")
    form = request.form
    print("2")
    dataF = {
        "nombre": form.get('nombre'),
        "inversion": form.get('inversion'),
        "tiempoVida": form.get('tiempoVida'),
        "fechaInicio": form.get('fechaInicio'),
        "fechaFinal": form.get('fechaFinal'),
        "costo": form.get('costo'),
        "electricidadDia": form.get('electricidadDia')
    }
    print("3")
    try:
        r = requests.post("http://localhost:8083/api/proyecto/save", data=json.dumps(dataF), headers=headers)
        print("4")
        r.raise_for_status()  # Esto lanzará una excepción para códigos de estado HTTP 4xx/5xx
        dat = r.json()
        print("5")
        if r.status_code == 200:
            flash('Proyecto guardado correctamente', category='info')
            
            # Registrar transacción
            dataT = {"tipo": "CREATE", "tabla": "Proyecto", "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "detalles": f"Se ha creado el proyecto {form.get('nombre')}" }
            tr = requests.post("http://localhost:8083/api/transaccion/save", data=json.dumps(dataT), headers=headers)
            
            if tr.status_code == 200:
                flash('Transacción guardada correctamente', category='info')
                
            
            print("6")
            return redirect('/proyecto/list')
        else:
            flash('Error al guardar proyecto', category='error')
            print("7")
            return redirect('/proyecto/list')
    except requests.exceptions.RequestException as e:
        print(f"Error en la solicitud: {e}")
        flash('Error al guardar proyecto', category='error')
        return redirect('/proyecto/list')

@router.route('/proyecto/register')
def register_proyecto():
    r = requests.get("http://localhost:8083/api/proyecto/list")
    data = r.json()
    return render_template('/proyecto/guardarProyecto.html', lista = data["data"])


@router.route('/proyecto/edit/<int:idProyecto>', methods=['GET', 'POST'])
def edit_proyecto(idProyecto):
    print(request.method)
    print(f"ID: {idProyecto}")
    
    if request.method == 'GET':
        r = requests.get(f"http://localhost:8083/api/proyecto/get/{idProyecto}")
        data = r.json()
        print(data)
        return render_template('/proyecto/editar.html', proyecto = data["data"])
    else:
        print("entro")
        headers = {'Content-Type': 'application/json'}
        form = request.form
        dataF = {
        "nombre": form.get('nombre'),
        "inversion": form.get('inversion'),
        "tiempoVida": form.get('tiempoVida'),
        "fechaInicio": form.get('fechaInicio'),
        "fechaFinal": form.get('fechaFinal'),
        "costo": form.get('costo'),
        "electricidadDia": form.get('electricidadDia')
        }
        r = requests.put(f"http://localhost:8083/api/proyecto/update/{idProyecto}", data=json.dumps(dataF), headers=headers)
        dat = r.json()
        if r.status_code == 200:
            flash('Proyecto actualizado correctamente', category='info')
            
            # Registrar transacción
            dataT = {"tipo": "UPDATE", "tabla": "Proyecto", "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "detalles": f"Se ha actualizado el proyecto{form['nombre']}" }
            tr = requests.post("http://localhost:8083/api/transaccion/save", data=json.dumps(dataT), headers=headers)
            
            if tr.status_code == 200:
                flash('Transacción guardada correctamente', category='info')
            
            return redirect('/')
        else:
            flash('Error al actualizar proyecto', category='error')
            return redirect('/')

@router.route('/proyecto/delete/<int:idProyecto>', methods=['POST'])
def delete_proyecto(idProyecto):
    
    print(f"ID a eliminar: {idProyecto}")
    
    headers = {'Content-Type': 'application/json'}
    r = requests.delete(f"http://localhost:8083/api/proyecto/delete/{idProyecto}", headers=headers)
    dat = r.json()
        
    # Registrar transacción
    dataT = {"tipo": "DELETE", "tabla": "Proyecto", "fecha": datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S"), "detalles": f"Se ha eliminado el proyecto con ID {idProyecto}" }
    tr = requests.post("http://localhost:8083/api/transaccion/save", data=json.dumps(dataT), headers=headers)
    
    if tr.status_code == 200:
        print("Transacción guardada correctamente")
        flash('Transacción guardada correctamente', category='info')

        return redirect('/proyecto/list')

    
    

# Transacciones
@router.route('/transaccion/list')
def lista_transaccion():
    r = requests.get("http://localhost:8083/api/transaccion/list")
    data = r.json()
    return render_template('/transacciones/listaTransacciones.html', lista = data["data"])