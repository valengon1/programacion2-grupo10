package com.logistica;

import com.logistica.estructuras.CentroDistribucion;
import com.logistica.modelo.Paquete;
import com.logistica.util.JsonLoader;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CentroDistribucion centro = new CentroDistribucion();

        List<Paquete<String>> paquetes = JsonLoader.cargarPaquetes("inventario.json");

        if (paquetes != null) {
            for (Paquete<String> p : paquetes) {
                centro.agregarPaquete(p);
            }
        }

        int opcion;

        do {
            System.out.println("\n MENU");
            System.out.println("1. Procesar paquete");
            System.out.println("2. Ver siguiente paquete");
            System.out.println("3. Agregar paquete");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    Paquete<?> procesado = centro.procesarPaquete();
                    if (procesado != null) {
                        System.out.println("Procesado: " + procesado);
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

                    System.out.print("ID: ");
                    String id = scanner.nextLine();

                    System.out.print("Peso: ");
                    double peso = scanner.nextDouble();

                    scanner.nextLine();

                    System.out.print("Destino: ");
                    String destino = scanner.nextLine();

                    System.out.print("Urgente (true/false): ");
                    boolean urgente = scanner.nextBoolean();

                    scanner.nextLine();

                    System.out.print("Contenido: ");
                    String contenido = scanner.nextLine();

                    Paquete<String> nuevo = new Paquete<>(id, peso, destino, urgente, contenido);
                    centro.agregarPaquete(nuevo);

                    System.out.println("Paquete agregado correctamente");
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