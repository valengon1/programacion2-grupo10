package com.logistica;

import com.logistica.estructuras.Camion;
import com.logistica.estructuras.CentroDistribucion;
import com.logistica.modelo.Paquete;
import com.logistica.util.JsonLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CentroDistribucion centro = new CentroDistribucion();
        Camion camion = new Camion();

        ArrayList<String> idsUsados = new ArrayList<>();

        List<Paquete<String>> paquetes = JsonLoader.cargarPaquetes("inventario.json");

        if (paquetes != null) {
            for (Paquete<String> p : paquetes) {
                centro.agregarPaquete(p);
                idsUsados.add(p.getId());
            }
        }

        int opcion;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Procesar paquete y cargar al camion");
            System.out.println("2. Ver siguiente paquete");
            System.out.println("3. Agregar paquete manualmente");
            System.out.println("4. Deshacer ultima carga del camion");
            System.out.println("0. Salir");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    Paquete<?> procesado = centro.procesarPaquete();

                    if (procesado != null) {
                        camion.cargar(procesado);
                        System.out.println("Paquete cargado al camion: " + procesado);
                    } else {
                        System.out.println("No hay paquetes para procesar");
                    }
                    break;

                case 2:
                    Paquete<?> siguiente = centro.verSiguiente();
                    System.out.println(siguiente != null ? "Siguiente: " + siguiente : "No hay paquetes");
                    break;

                case 3:
                    agregarPaquete(scanner, centro, idsUsados);
                    break;

                case 4:
                    Paquete<?> eliminado = camion.deshacerCarga();

                    if (eliminado != null) {
                        System.out.println("Se deshizo la ultima carga: " + eliminado);
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
            String input = scanner.nextLine().trim();

            if (input.equals("-1")) {
                return -1;
            }

            try {
                double valor = Double.parseDouble(input);

                if (valor > 0) {
                    return valor;
                } else {
                    System.out.println("Debe ser mayor a 0");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido");
            }
        }
    }

    public static String leerStringNoVacio(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();

            if (input.equals("-1")) {
                return null;
            }

            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("No puede estar vacio");
            }
        }
    }

    public static int leerUrgente(Scanner scanner) {
        while (true) {
            System.out.print("Urgente? (s/n) o -1 para cancelar: ");
            String input = scanner.nextLine().trim();

            if (input.equals("-1")) {
                return -1;
            }

            if (input.equalsIgnoreCase("s")) {
                return 1;
            }

            if (input.equalsIgnoreCase("n")) {
                return 0;
            }

            System.out.println("Ingrese s, n o -1");
        }
    }

    public static void agregarPaquete(Scanner scanner, CentroDistribucion centro, ArrayList<String> idsUsados) {

        System.out.println("\nCarga manual de paquete");
        System.out.println("En cualquier momento escribi -1 para cancelar");

        String id = generarId(idsUsados);

        double peso = leerDoublePositivo(scanner, "Peso: ");
        if (peso == -1) {
            System.out.println("Carga cancelada");
            return;
        }

        String destino = leerStringNoVacio(scanner, "Destino: ");
        if (destino == null) {
            System.out.println("Carga cancelada");
            return;
        }

        int urgenteInt = leerUrgente(scanner);
        if (urgenteInt == -1) {
            System.out.println("Carga cancelada");
            return;
        }

        boolean urgente = urgenteInt == 1;

        String contenido = leerStringNoVacio(scanner, "Contenido: ");
        if (contenido == null) {
            System.out.println("Carga cancelada");
            return;
        }

        Paquete<String> nuevo = new Paquete<>(id, peso, destino, urgente, contenido);
        centro.agregarPaquete(nuevo);
        idsUsados.add(id);

        System.out.println("Paquete agregado correctamente con ID: " + id);
    }

    public static String generarId(ArrayList<String> idsUsados) {
        Random random = new Random();
        String id;

        do {
            int numero = random.nextInt(9999) + 1;
            id = formatearId(numero);
        } while (idsUsados.contains(id));

        return id;
    }

    public static String formatearId(int numero) {
        if (numero < 10) {
            return "000" + numero;
        } else if (numero < 100) {
            return "00" + numero;
        } else if (numero < 1000) {
            return "0" + numero;
        } else {
            return "" + numero;
        }
    }
}