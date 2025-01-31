package controller.Dao;

import models.Hospital;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class HospitalDao extends AdapterDao<Hospital> {
    private Hospital hospital = new Hospital();
    private LinkedList<Hospital> listAll;

    public HospitalDao() {
        super(Hospital.class);
    }

    public Hospital getHospital() {
        if (hospital == null) {
            hospital = new Hospital();
        }
        return this.hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public LinkedList<Hospital> getlistAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getlistAll().getSize() + 1;
        hospital.setId(id);
        this.persist(this.hospital);
        this.listAll = listAll();
        return true;
    }

    public Hospital busquedaBinaria(String attribute, String value) throws Exception {
        System.out.println("Busqueda binaria");
        Hospital hospital = null;
        LinkedList<Hospital> listita = listAll();
        if (!listita.isEmpty()) {
            Hospital[] aux = listita.toArray();
            int inicio = 0;
            int fin = aux.length - 1;
            while (inicio <= fin) {
                int medio = (inicio + fin) / 2;
                if (attribute.equalsIgnoreCase("id")) {
                    if (aux[medio].getId() == Integer.parseInt(value)) {
                        hospital = aux[medio];
                        break;
                    } else if (aux[medio].getId() < Integer.parseInt(value)) {
                        inicio = medio + 1;
                    } else {
                        fin = medio - 1;
                    }
                }
            }
        }
        return hospital;
    }

    public float getDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        Double latDistance = Math.toRadians(lat2 - lat1);
        Double lonDistance = Math.toRadians(lon2 - lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2)
                        * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000;
        distance = Math.pow(distance, 2);
        return (float) Math.sqrt(distance);
    }
}