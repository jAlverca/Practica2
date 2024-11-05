package com.example.rest;

import controller.Dao.servicies.PersonaServicies;
import controller.Dao.servicies.TransaccionServices;
import controller.tda.list.LinkedList;
import models.Persona;
import models.Transaccion;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

@Path("/transaccion")
public class TransaccionApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTransacciones() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            TransaccionServices ts = new TransaccionServices();
            LinkedList<Transaccion> transacciones = ts.listAll();

            map.put("msg", "OK");
            map.put("data", transacciones != null ? transacciones.toArray() : new Object[] {});
            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener las transacciones: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        TransaccionServices ts = new TransaccionServices();

        try {
            ts.getTransaccion().setFecha(map.get(("fecha")).toString());
            ts.getTransaccion().setDetalles(map.get(("detalles")).toString());
            ts.getTransaccion().setTipo(map.get(("tipo")).toString());
            ts.getTransaccion().setTabla(map.get(("tabla")).toString());
            ts.save();
            res.put("msg", "OK");
            res.put("data", "Transaccion guardada correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al guardar la transaccion: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();

        }
    }

}
