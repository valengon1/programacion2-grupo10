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
            System.out.println("\n--- MENU ---");
            System.out.println("1. Procesar paquete");
            System.out.println("2. Ver siguiente paquete");
            System.out.println("3. Agregar paquete");
            System.out.println("0. Salir");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    Paquete<?> procesado = centro.procesarPaquete();
                    System.out.println(procesado != null ? "Procesado: " + procesado : "No hay paquetes");
                    break;

                case 2:
                    Paquete<?> siguiente = centro.verSiguiente();
                    System.out.println(siguiente != null ? "Siguiente: " + siguiente : "No hay paquetes");
                    break;

                case 3:
                    agregarPaquete(scanner, centro);
                    break;

                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion invalida");
            }

        } while (opcion != 0);
    }

    public static int leerEntero(Scanner scanner) {
        while (true) {
            System.out.print("Opcion: ");
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido");
            }
        }
    }

    public static double leerDoublePositivo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine();

            try {
                double valor = Double.parseDouble(input);
                if (valor > 0) return valor;
                else System.out.println("Debe ser mayor a 0");
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido");
            }
        }
    }

    public static String leerStringNoVacio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) return input;
            else System.out.println("No puede estar vacio");
        }
    }

    public static boolean leerBoolean(Scanner scanner) {
        while (true) {
            System.out.print("Urgente (true/false): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(input);
            } else {
                System.out.println("Ingrese true o false");
            }
        }
    }

    public static void agregarPaquete(Scanner scanner, CentroDistribucion centro) {

        String id = leerStringNoVacio(scanner, "ID: ");
        double peso = leerDoublePositivo(scanner, "Peso: ");
        String destino = leerStringNoVacio(scanner, "Destino: ");
        boolean urgente = leerBoolean(scanner);
        String contenido = leerStringNoVacio(scanner, "Contenido: ");

        Paquete<String> nuevo = new Paquete<>(id, peso, destino, urgente, contenido);
        centro.agregarPaquete(nuevo);

        System.out.println("Paquete agregado correctamente");
    }
}