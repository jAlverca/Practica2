package controller.Dao.servicies;

import controller.Dao.ProyectoDao;
import controller.tda.list.LinkedList;
import models.Persona;
import models.Proyecto;

public class ProyectoServicies {
    private ProyectoDao obj;

    public ProyectoServicies() {
        obj = new ProyectoDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList listAll() {
        return obj.getlistAll();

    }

    public Proyecto getProyecto() {
        return obj.getProyecto();
    }
        public void merge(Proyecto proyecto, Integer id) throws Exception {
        obj.merge(proyecto, id);
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    public Proyecto get(Integer id) throws Exception {
        return obj.getDao(id);
    }

    public void delete(Integer id) throws Exception {
        obj.delete(id);
    }
}
