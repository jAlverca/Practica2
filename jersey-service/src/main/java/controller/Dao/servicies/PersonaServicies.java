package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import models.Persona;
import controller.Dao.PersonaDao;

public class PersonaServicies {
    private PersonaDao obj;

    public PersonaServicies() {
        obj = new PersonaDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList<Persona> listAll() {
        return obj.getlistAll();

    }

    public Persona getPersona() {
        return obj.getPersona();
    }

    public void setPersona(Persona persona) {
        obj.setPersona(persona);
    }

    public Persona get(Integer idPersona) throws Exception {
        return obj.getDao(idPersona);
    }

    public void merge(Persona persona, Integer id) throws Exception {
        obj.merge(persona, id);
    }

    public void delete(Integer id) throws Exception {
        obj.delete(id);
    }

    public LinkedList<Persona> quickSort(LinkedList<Persona> lista, Integer type_order, String atributo)
            throws Exception {
        LinkedList<Persona> lista_ordenada = obj.quickSort(lista, type_order, atributo);
        return lista_ordenada;
    }

    public LinkedList<Persona> mergeSort(Integer type_order, String atributo) throws Exception {
        LinkedList<Persona> lista_ordenada = obj.mergeSort(type_order, atributo);
        return lista_ordenada;
    }

    public LinkedList<Persona> shellSort(Integer type_order, String atributo) throws Exception {
        LinkedList<Persona> lista_ordenada = obj.shellSort(type_order, atributo);
        return lista_ordenada;
    }

    public LinkedList<Persona> busquedaLinealBinaria(String atributo, String value) throws Exception {
        return obj.busquedaLinealBinaria(atributo, value);
    }

    public Persona busquedaBinaria(String atributo, String value) throws Exception {
        return obj.busquedaBinaria(atributo, value);
    }

}
