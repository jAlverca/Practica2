package com.example.rest;

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
import javax.ws.rs.core.Response; // Import correcto para Response
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import controller.Dao.servicies.PersonaServicies;
import controller.Dao.servicies.ProyectoServicies;
import models.Persona;
import models.Proyecto;

@Path("/proyecto")
public class ProyectoApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyectos() {
        HashMap map = new HashMap<>();
        ProyectoServicies ps = new ProyectoServicies();
        map.put("msg", "OK");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        ProyectoServicies pr = new ProyectoServicies();
        try {
            pr.getProyecto().setNombre(map.get(("nombre")).toString());
            pr.getProyecto().setInversion(map.get(("inversion")).toString());
            pr.getProyecto().setTiempoVida(map.get(("tiempoVida")).toString());
            pr.getProyecto().setFechaFinal(map.get(("fechaFinal")).toString());
            pr.getProyecto().setFechaInicio(map.get(("fechaInicio")).toString());
            pr.getProyecto().setCosto(map.get(("costo")).toString());
            pr.getProyecto().setElectricidadDia(map.get(("electricidadDia")).toString());
            pr.save();
            res.put("msg", "OK");
            res.put("data", "Proyecto guardada correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al guardar la proyecto: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{idProyecto}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProyecto(@PathParam("idProyecto") Integer idProyecto) {
        HashMap map = new HashMap<>();
        ProyectoServicies ps = new ProyectoServicies();
        try {
            System.out.println("Obteniendo persona con id: " + idProyecto);
            Proyecto p = ps.get(idProyecto);
            System.out.println("Proyecto: " + p);
            map.put("msg", "OK");
            map.put("data", p);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener el pryecto: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

    }
    @Path("/update/{idProyecto}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProyecto(@PathParam("idProyecto") Integer idProyecto, HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        ProyectoServicies ps = new ProyectoServicies();
        try {
            Proyecto p = ps.get(idProyecto);
            p.setNombre(map.get(("nombre")).toString());
            p.setInversion(map.get(("inversion")).toString());
            p.setTiempoVida(map.get(("tiempoVida")).toString());
            p.setFechaFinal(map.get(("fechaFinal")).toString());
            p.setFechaInicio(map.get(("fechaInicio")).toString());
            p.setCosto(map.get(("costo")).toString());
            p.setElectricidadDia(map.get(("electricidadDia")).toString());

            ps.merge(p, idProyecto);
            res.put("msg", "OK");
            res.put("data", "Proyecto actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el error en la consola del servidor
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar el proyecto: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{idProyecto}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProyecto(@PathParam("idProyecto") Integer idProyecto) {
        HashMap res = new HashMap<>();
        ProyectoServicies ps = new ProyectoServicies();
        try {
            ps.delete(idProyecto);
            res.put("msg", "OK");
            res.put("data", "Proyecto eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar el proyecto: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
