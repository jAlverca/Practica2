package com.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import controller.tda.graph.Graph;
import controller.tda.graph.GraphDirect;
import controller.tda.list.LinkedList;


@Path("test")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        Graph graph = new GraphDirect(5);
        System.out.println(graph.toString());
        return "Got it!";
    }
}