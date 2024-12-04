package com.example.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import controller.tda.list.LinkedList;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() throws Exception {
        LinkedList<Integer> lista = generarListaRandom(25000);

        // Medir tiempo de ShellSort
        long tiempoInicio = System.currentTimeMillis();
        lista.shellSort(1);
        long tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion ShellSort: " + (tiempoFinal - tiempoInicio) + " milisegundos");

        // Medir tiempo de QuickSort
        lista = generarListaRandom(10000);
        tiempoInicio = System.currentTimeMillis();
        lista.quickSort(1);
        tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion QuickSort: " + (tiempoFinal - tiempoInicio) + " milisegundos");

        // Medir tiempo de MergeSort
        lista = generarListaRandom(10000);
        tiempoInicio = System.currentTimeMillis();
        lista.mergeSort(1);
        tiempoFinal = System.currentTimeMillis();
        System.out.println("Tiempo de ejecucion MergeSort: " + (tiempoFinal - tiempoInicio) + " milisegundos");

        // Medir tiempo de búsqueda lineal
        Integer value = (int) (Math.random() * 60000);
        lista = generarListaRandom(10000);
        tiempoInicio = System.nanoTime();
        int posicion = lista.busquedaLinealBinaria(value);
        tiempoFinal = System.nanoTime();
        System.out.println("Tiempo de ejecucion busquedaLinealBinaria: " + (tiempoFinal - tiempoInicio) + " milisegundos");

        // Medir tiempo de búsqueda binaria
        lista.mergeSort(1);
        tiempoInicio = System.nanoTime();
        posicion = lista.busquedeBinaria(value);
        tiempoFinal = System.nanoTime();
        System.out.println("Tiempo de ejecucion busquedeBinaria con mergeSort: " + (tiempoFinal - tiempoInicio) + " milisegundos");

        return "Operaciones completadas. Revisa la consola para los tiempos de ejecución.";
    }

    LinkedList<Integer> generarListaRandom(Integer cantidad) {
        LinkedList<Integer> lista = new LinkedList<>();
        for (int i = 0; i < cantidad; i++) {
            lista.add((int) (Math.random() * 60000));
        }
        return lista;
    }
}