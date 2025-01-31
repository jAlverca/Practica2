package com.example.rest;

import controller.Dao.GrafoDao;
import controller.Dao.HospitalDao;
import controller.Dao.services.HospitalServices;
import controller.tda.list.LinkedList;
import models.Hospital;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/hospital")
public class HospitalApi {

    private GrafoDao grafoDao = new GrafoDao();
    HospitalDao hospitalDao = new HospitalDao();

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllHospital() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            HospitalServices ps = new HospitalServices();
            LinkedList<Hospital> hospital = ps.listAll();
            map.put("msg", "OK");
            map.put("data", hospital != null ? hospital.toArray() : new Object[] {});

            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener las personas: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        HospitalServices ps = new HospitalServices();
        Hospital p = ps.getHospital();
        Boolean guardado = false;
        try {
            System.out.println("Guardando hospital...");
            System.out.println("Map: " + map);
            p.setNombre(map.get(("nombre")).toString());
            p.setDireccion(map.get(("direccion")).toString());
            p.setLongitud(Float.parseFloat(map.get(("longitud")).toString()));
            p.setLatitud(Float.parseFloat(map.get(("latitud")).toString()));
            guardado = ps.save();
            res.put("msg", "OK");
            res.put("data", "Hospital guardada correctamente");

            if (guardado) {
                // Cargar grafo
                grafoDao.loadGraph();

                // Crear conexiones entre el nuevo hospital y 3 hospitales existentes aleatorios
                LinkedList<Hospital> hospitales = ps.listAll();
                for (int i = 0; i < 3; i++) {
                    int randomIndex = (int) (Math.random() * hospitales.getSize());
                    Hospital h = hospitales.get(randomIndex);
                    float dist = hospitalDao.getDistance(
                            (float) p.getLatitud(), (float) p.getLongitud(),
                            (float) h.getLatitud(), (float) h.getLongitud());
                    grafoDao.insertEdge(p, h, dist);

                    grafoDao.saveGraph();
                }
            }

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al guardar la persona: " + e.toString());
            System.out.println("Error al guardar la persona: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Path("/get/{idHospital}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("idHospital") Integer idHospital) {
        HashMap map = new HashMap<>();
        HospitalServices ps = new HospitalServices();
        try {
            System.out.println("Obteniendo persona con id: " + idHospital);
            Hospital p = ps.get(idHospital);
            System.out.println("Hospital: " + p);
            map.put("msg", "OK");
            map.put("data", p);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener la persona: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Path("/update/{idHospital}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("idHospital") Integer idHospital, HashMap map) {
        HashMap res = new HashMap<>();
        HospitalServices ps = new HospitalServices();
        try {
            Hospital p = ps.get(idHospital);
            p.setNombre(map.get(("nombre")).toString());
            p.setDireccion(map.get(("direccion")).toString());
            p.setLongitud(Float.parseFloat(map.get(("longitud")).toString()));
            p.setLatitud(Float.parseFloat(map.get(("latitud")).toString()));
            ps.setHospital(p);
            ps.merge(p, idHospital);
            res.put("msg", "OK");
            res.put("data", "Hospital actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el error en la consola del servidor
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar la persona: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Path("/delete/{idHospital}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteHospital(@PathParam("idHospital") Integer idHospital) {
        HashMap res = new HashMap<>();
        HospitalServices ps = new HospitalServices();
        try {
            ps.delete(idHospital);
            res.put("msg", "OK");
            res.put("data", "Hospital eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar la persona: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
