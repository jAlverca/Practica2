package com.example.rest;

import controller.Dao.servicies.PersonaServicies;
import controller.Dao.servicies.TransaccionServices;
import controller.tda.list.LinkedList;
import models.Persona;

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

import com.google.gson.Gson;

@Path("/person")
public class PersonaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            PersonaServicies ps = new PersonaServicies();
            LinkedList<Persona> personas = ps.listAll();
            map.put("msg", "OK");
            map.put("data", personas != null ? personas.toArray() : new Object[] {});

            return Response.ok(map).build();
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir stack trace para detectar problemas
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener las personas: " + e.getMessage());
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
        PersonaServicies ps = new PersonaServicies();
        try {
            ps.getPersona().setNombre(map.get(("nombre")).toString());
            ps.getPersona().setApellido(map.get(("apellido")).toString());
            ps.getPersona().setCedula(map.get(("cedula")).toString());
            ps.getPersona().setTelefono(map.get(("telefono")).toString());
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Persona guardada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al guardar la persona: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{idPersona}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerson(@PathParam("idPersona") Integer idPersona) {
        HashMap map = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        try {
            System.out.println("Obteniendo persona con id: " + idPersona);
            Persona p = ps.get(idPersona);
            System.out.println("Persona: " + p);
            map.put("msg", "OK");
            map.put("data", p);
            return Response.ok(map).build();
        } catch (Exception e) {
            map.put("msg", "ERROR");
            map.put("data", "Error al obtener la persona: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }

    }

    @Path("/update/{idPersona}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePerson(@PathParam("idPersona") Integer idPersona, HashMap map) {
        HashMap res = new HashMap<>();
        Gson g = new Gson();
        String a = g.toJson(map);
        PersonaServicies ps = new PersonaServicies();
        try {
            Persona p = ps.get(idPersona);
            p.setNombre(map.get(("nombre")).toString());
            p.setApellido(map.get(("apellido")).toString());
            p.setCedula(map.get(("cedula")).toString());
            p.setTelefono(map.get(("telefono")).toString());
            ps.setPersona(p);
            ps.merge(p, idPersona);
            res.put("msg", "OK");
            res.put("data", "Persona actualizada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            e.printStackTrace(); // Esto imprimirá el error en la consola del servidor
            res.put("msg", "ERROR");
            res.put("data", "Error al actualizar la persona: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/delete/{idPersona}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("idPersona") Integer idPersona) {
        HashMap res = new HashMap<>();
        PersonaServicies ps = new PersonaServicies();
        try {
            ps.delete(idPersona);
            res.put("msg", "OK");
            res.put("data", "Persona eliminada correctamente");

            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "ERROR");
            res.put("data", "Error al eliminar la persona: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
