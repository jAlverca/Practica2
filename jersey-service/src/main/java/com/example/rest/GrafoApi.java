package com.example.rest;

import controller.Dao.GrafoDao;
import controller.Dao.services.HospitalServices;
import controller.tda.list.LinkedList;
import models.Hospital;

import java.io.FileReader;
import java.nio.file.Paths;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/grafo")
public class GrafoApi {

    private GrafoDao grafoDao = new GrafoDao();
    private String mediaPath = Paths.get("media").toAbsolutePath().toString();
    private String graphFile = Paths.get(mediaPath, "grafo_hospital.json").toString();

    @Path("/ver_grafo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response ver_grafo() {
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
            map.put("data", "Error al obtener los hospitales: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
        }
    }

    @Path("/cargar_grafo")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cargar_grafo() {
        System.out.println("\nCargando grafo desde archivo...");
        try {
            grafoDao.loadGraph();
            if (!grafoDao.isExistFile()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("{\"error\": \"Archivo de grafo no encontrado\"}")
                        .build();
            }

            JSONObject grafoJson = grafoDao.getGraphAsJson();

            return Response.ok(grafoJson.toJSONString()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error al cargar el grafo\"}")
                    .build();
        }
    }

    @Path("/matriz_adyacencias")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerMatrizAdyacencias() {
        try {
            grafoDao.loadGraph();
            Float[][] matriz = grafoDao.getAdjacencyMatrix();
            HashMap<String, Object> response = new HashMap<>();
            response.put("matriz", matriz);
            return Response.ok(response).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Error al obtener la matriz de adyacencias\"}")
                    .build();
        }
    }

}