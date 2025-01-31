// package controller.Dao;

// import models.Hospital;

// public class TestGrafoHospital {
// public static void main(String[] args) {
// try {
// GrafoDao grafoDao = new GrafoDao();
// HospitalDao hospitalDao = new HospitalDao();

// // Crear algunos hospitales de prueba si no existen
// if (hospitalDao.getlistAll().isEmpty()) {
// System.out.println("\nCreando hospitales de prueba...");

// Hospital h1 = hospitalDao.getHospital();
// h1.setNombre("Hospital General");
// h1.setDireccion("Calle Principal 123");
// h1.setLatitud(-2.897733);
// h1.setLongitud(-79.004890);
// hospitalDao.save();
// System.out.println("Hospital 1 creado: " + h1.getNombre());

// Hospital h2 = hospitalDao.getHospital();
// h2.setNombre("Hospital del IESS");
// h2.setDireccion("Av. 12 de Abril");
// h2.setLatitud(-2.904385);
// h2.setLongitud(-79.000559);
// hospitalDao.save();
// System.out.println("Hospital 2 creado: " + h2.getNombre());

// Hospital h3 = hospitalDao.getHospital();
// h3.setNombre("Hospital del Río");
// h3.setDireccion("Av. 24 de Mayo");
// h3.setLatitud(-2.889421);
// h3.setLongitud(-78.977213);
// hospitalDao.save();
// System.out.println("Hospital 3 creado: " + h3.getNombre());
// }

// System.out.println("\nCargando grafo...");
// grafoDao.loadGraph();

// System.out.println("\nEstado del grafo después de cargar:");
// grafoDao.printGraphState();

// System.out.println("\nCreando conexiones entre hospitales...");
// Hospital h1 = hospitalDao.busquedaBinaria("id", "1");
// System.out.println("Hospital 1: " + h1.getNombre());
// Hospital h2 = hospitalDao.busquedaBinaria("id", "2");
// System.out.println("Hospital 2: " + h2.getNombre());
// Hospital h3 = hospitalDao.busquedaBinaria("id", "3");
// System.out.println("Hospital 3: " + h3.getNombre());

// System.out.println("\nConectando hospitales con sus distancias...");
// float dist1_2 = hospitalDao.getDistance(
// (float) h1.getLatitud(), (float) h1.getLongitud(),
// (float) h2.getLatitud(), (float) h2.getLongitud());
// grafoDao.insertEdge(h1, h2, dist1_2);
// System.out.println("Distancia entre " + h1.getNombre() + " y " +
// h2.getNombre() + ": " + dist1_2);

// float dist2_3 = hospitalDao.getDistance(
// (float) h2.getLatitud(), (float) h2.getLongitud(),
// (float) h3.getLatitud(), (float) h3.getLongitud());

// grafoDao.insertEdge(h2, h3, dist2_3);
// System.out.println("Distancia entre " + h2.getNombre() + " y " +
// h3.getNombre() + ": " + dist2_3);

// float dist1_3 = hospitalDao.getDistance(
// (float) h1.getLatitud(), (float) h1.getLongitud(),
// (float) h3.getLatitud(), (float) h3.getLongitud());
// grafoDao.insertEdge(h1, h3, dist1_3);
// System.out.println("Distancia entre " + h1.getNombre() + " y " +
// h3.getNombre() + ": " + dist1_3);

// System.out.println("\nGuardando grafo...");
// grafoDao.saveGraph();

// System.out.println("\nEstado del grafo después de insertar aristas:");
// grafoDao.printGraphState();

// System.out.println("\nMatriz de adyacencia:");
// Float[][] matriz = grafoDao.getAdjacencyMatrix();
// for (int i = 0; i < matriz.length; i++) {
// for (int j = 0; j < matriz[i].length; j++) {
// System.out.printf("%8.2f ", matriz[i][j]);
// }
// System.out.println();
// }

// System.out.println("\nPrueba completada exitosamente!");

// } catch (Exception e) {
// System.out.println("Error durante la prueba: " + e.getMessage());
// e.printStackTrace();
// }
// }
// }