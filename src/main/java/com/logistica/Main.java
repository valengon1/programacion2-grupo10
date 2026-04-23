package com.logistica;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logistica.estructuras.Camion;
import com.logistica.estructuras.CentroDistribucion;
import com.logistica.modelo.Paquete;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static List<Paquete<String>> cargarPaquetes(String ruta) {
        Gson gson = new Gson();

        try {
            FileReader reader = new FileReader(ruta);
            Type tipoLista = new TypeToken<List<Paquete<String>>>() {}.getType();
            List<Paquete<String>> paquetes = gson.fromJson(reader, tipoLista);
            reader.close();

            if (paquetes == null) {
                return new ArrayList<>();
            }

            return paquetes;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void guardarPaquetes(String ruta, List<Paquete<String>> paquetes) {
        Gson gson = new Gson();

        try {
            String json = gson.toJson(paquetes);
            FileWriter writer = new FileWriter(ruta);
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CentroDistribucion centro = new CentroDistribucion();
        Camion camion = new Camion();

        List<Paquete<String>> paquetes = cargarPaquetes("inventario.json");

        for (Paquete<String> p : paquetes) {
            centro.agregarPaquete(p);
        }

        int opcion;

        do {
            System.out.println("\nMENU");
            System.out.println("1. Procesar paquete");
            System.out.println("2. Ver siguiente paquete");
            System.out.println("3. Agregar paquete");
            System.out.println("4. Deshacer ultima carga");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    Paquete<?> procesado = centro.procesarPaquete();
                    if (procesado != null) {
                        camion.cargar(procesado);
                        System.out.println("Paquete cargado en camion: " + procesado);
                    } else {
                        System.out.println("No hay paquetes");
                    }
                    break;

                case 2:
                    Paquete<?> siguiente = centro.verSiguiente();
                    if (siguiente != null) {
                        System.out.println("Siguiente: " + siguiente);
                    } else {
                        System.out.println("No hay paquetes");
                    }
                    break;

                case 3:
                    scanner.nextLine();

                    System.out.print("Peso: ");
                    double peso = scanner.nextDouble();

                    scanner.nextLine();

                    System.out.print("Destino: ");
                    String destino = scanner.nextLine();

                    System.out.print("Urgente (si/no): ");
                    String prioridad = scanner.nextLine();

                    boolean urgente = prioridad.equalsIgnoreCase("si");

                    System.out.print("Contenido: ");
                    String contenido = scanner.nextLine();

                    Paquete<String> nuevo = new Paquete<>(peso, destino, urgente, contenido);
                    centro.agregarPaquete(nuevo);
                    paquetes.add(nuevo);
                    guardarPaquetes("inventario.json", paquetes);

                    System.out.println("Paquete agregado correctamente con ID: " + nuevo.getId());
                    break;

                case 4:
                    Paquete<?> deshecho = camion.deshacerCarga();
                    if (deshecho != null) {
                        centro.agregarPaquete(deshecho);
                        System.out.println("Se deshizo la ultima carga y el paquete volvio al centro: " + deshecho);
                    } else {
                        System.out.println("El camion esta vacio");
                    }
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);

        scanner.close();
    }
}