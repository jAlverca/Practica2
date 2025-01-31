package controller.Dao.services;

import controller.tda.list.LinkedList;
import models.Hospital;
import controller.Dao.HospitalDao;

public class HospitalServices {
    private HospitalDao obj;

    public HospitalServices() {
        obj = new HospitalDao();
    }

    public Boolean save() throws Exception {
        return obj.save();
    }

    public LinkedList<Hospital> listAll() {
        return obj.getlistAll();

    }

    public Hospital getHospital() {
        return obj.getHospital();
    }

    public void setHospital(Hospital hospital) {
        obj.setHospital(hospital);
    }

    public Hospital get(Integer idHospital) throws Exception {
        return obj.getDao(idHospital);
    }

    public void merge(Hospital hospital, Integer id) throws Exception {
        obj.merge(hospital, id);
    }

    public void delete(Integer id) throws Exception {
        obj.delete(id);
    }

}