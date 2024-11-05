package controller.Dao;

import controller.Dao.implement.AdapterDao;
import models.Transaccion;
import controller.tda.list.LinkedList;

public class TransaccionDao extends AdapterDao<Transaccion> {
    private Transaccion transaccion = new Transaccion();
    private LinkedList<Transaccion> listAll;

    public TransaccionDao() {
        super(Transaccion.class);
    }

    public Transaccion getTransaccion() {
        if (transaccion == null) {
            transaccion = new Transaccion();

        }
        return this.transaccion;
    }

    public void setTransaccion(Transaccion transaccion) {
        this.transaccion = transaccion;
    }

    public LinkedList<Transaccion> getlistAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize();
        transaccion.setId(id);
        this.persist(this.transaccion);
        this.listAll = listAll();
        return true;
    }
}
