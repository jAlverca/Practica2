package controller.Dao;

import models.Proyecto;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto = new Proyecto();
    private LinkedList listAll;

    public ProyectoDao() {
        super(Proyecto.class);
    }

    public Proyecto getProyecto() {
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public LinkedList getlistAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize();
        proyecto.setIdProyecto(id);
        this.persist(this.proyecto);
        this.listAll = listAll();
        return true;
    }

}
