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

}
