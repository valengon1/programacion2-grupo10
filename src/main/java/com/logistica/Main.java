package com.logistica;

import com.logistica.estructuras.ArbolDepositos;
import com.logistica.estructuras.Camion;
import com.logistica.estructuras.CentroDistribucion;
import com.logistica.estructuras.GrafoRutas;
import com.logistica.modelo.Deposito;
import com.logistica.modelo.Paquete;
import com.logistica.util.JsonLoader;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        CentroDistribucion centro = new CentroDistribucion();
        Camion camion = new Camion();
        ArbolDepositos arbolDepositos = new ArbolDepositos();
        GrafoRutas grafoRutas = new GrafoRutas();
        ArrayList<String> idsUsados = new ArrayList<>();

        cargarDepositosDePrueba(arbolDepositos);
        cargarRutasDePrueba(grafoRutas);

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
            System.out.println("5. Listar paquetes demorados");
            System.out.println("6. Auditar depositos pendientes");
            System.out.println("7. Mostrar depositos por nivel");
            System.out.println("8. Mostrar red de rutas");
            System.out.println("9. Calcular distancia minima entre depositos");
            System.out.println("0. Salir");

            opcion = leerEntero(scanner, "Opcion: ");

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

                    if (siguiente != null) {
                        System.out.println("Siguiente paquete: " + siguiente);
                    } else {
                        System.out.println("No hay paquetes pendientes");
                    }
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

                case 5:
                    List<Paquete<?>> demorados = centro.listarPaquetesDemorados();

                    if (demorados.isEmpty()) {
                        System.out.println("No hay paquetes demorados");
                    } else {
                        System.out.println("Paquetes demorados (mas de 30 minutos):");
                        for (Paquete<?> p : demorados) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 6:
                    List<Deposito> auditados = arbolDepositos.auditarDepositosPendientes();

                    if (auditados.isEmpty()) {
                        System.out.println("No hay depositos pendientes de auditoria");
                    } else {
                        System.out.println("Depositos auditados:");
                        for (Deposito d : auditados) {
                            System.out.println(d);
                        }
                    }
                    break;

                case 7:
                    int nivel = leerEnteroNoNegativo(scanner, "Ingrese nivel del arbol: ");
                    List<Deposito> depositosNivel = arbolDepositos.obtenerDepositosPorNivel(nivel);

                    if (depositosNivel.isEmpty()) {
                        System.out.println("No hay depositos en el nivel " + nivel);
                    } else {
                        System.out.println("Depositos en nivel " + nivel + ":");
                        for (Deposito d : depositosNivel) {
                            System.out.println(d);
                        }
                    }
                    break;

                case 8:
                    System.out.println("Red de rutas:");
                    grafoRutas.mostrarGrafo();
                    break;

                case 9:
                    int origen = leerEntero(scanner, "ID deposito origen: ");
                    int destino = leerEntero(scanner, "ID deposito destino: ");

                    int distancia = grafoRutas.calcularDistanciaMinima(origen, destino);

                    if (distancia == -1) {
                        System.out.println("No existe ruta entre los depositos indicados");
                    } else {
                        System.out.println("Distancia minima: " + distancia + " km");
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

    public static void cargarDepositosDePrueba(ArbolDepositos arbol) {
        arbol.insertar(new Deposito(50, "Deposito Central", LocalDateTime.now().minusDays(40)));
        arbol.insertar(new Deposito(30, "Deposito Oeste", LocalDateTime.now().minusDays(10)));
        arbol.insertar(new Deposito(70, "Deposito Norte", LocalDateTime.now().minusDays(60)));
        arbol.insertar(new Deposito(20, "Deposito Sur", LocalDateTime.now().minusDays(35)));
        arbol.insertar(new Deposito(40, "Deposito Este", LocalDateTime.now().minusDays(5)));
    }

    public static void cargarRutasDePrueba(GrafoRutas grafo) {
        grafo.conectarDepositos(50, 30, 10);
        grafo.conectarDepositos(50, 70, 20);
        grafo.conectarDepositos(30, 20, 15);
        grafo.conectarDepositos(30, 40, 8);
        grafo.conectarDepositos(40, 70, 12);
    }

    public static int leerEntero(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido");
            }
        }
    }

    public static double leerDoublePositivo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();

            try {
                double valor = Double.parseDouble(input);

                if (valor > 0) {
                    return valor;
                } else {
                    System.out.println("El peso debe ser mayor a 0");
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

            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Este campo no puede estar vacio");
            }
        }
    }

    public static boolean leerUrgente(Scanner scanner) {
        while (true) {
            System.out.print("Urgente (s/n): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("s")) {
                return true;
            }

            if (input.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Ingrese 's' para si o 'n' para no");
        }
    }

    public static void agregarPaquete(Scanner scanner, CentroDistribucion centro, ArrayList<String> idsUsados) {

        String id = generarId(idsUsados);
        System.out.println("ID generado: " + id);

        double peso = leerDoublePositivo(scanner, "Peso: ");
        String destino = leerStringNoVacio(scanner, "Destino: ");
        boolean urgente = leerUrgente(scanner);
        String contenido = leerStringNoVacio(scanner, "Contenido: ");
        int minutosIngreso = leerEnteroNoNegativo(scanner, "Minutos desde ingreso: ");

        Paquete<String> nuevo = new Paquete<>(id, peso, destino, urgente, contenido, minutosIngreso);

        centro.agregarPaquete(nuevo);
        idsUsados.add(id);

        System.out.println("Paquete agregado correctamente");
    }

    public static int leerEnteroNoNegativo(Scanner scanner, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String input = scanner.nextLine().trim();

            try {
                int valor = Integer.parseInt(input);

                if (valor >= 0) {
                    return valor;
                } else {
                    System.out.println("El valor no puede ser negativo");
                }

            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero valido");
            }
        }
    }

    public static String generarId(ArrayList<String> idsUsados) {
        Random random = new Random();
        String id;

        do {
            int numero = random.nextInt(9999) + 1;
            id = String.format("%04d", numero);
        } while (idsUsados.contains(id));

        return id;
    }
}8