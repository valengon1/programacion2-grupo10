package com.logistica.estructuras;

import java.util.*;

public class GrafoRutas {

    private Map<Integer, List<Ruta>> adyacencias;

    public GrafoRutas() {
        this.adyacencias = new HashMap<>();
    }

    public void agregarDeposito(int idDeposito) {
        adyacencias.putIfAbsent(idDeposito, new ArrayList<>());
    }

    public void conectarDepositos(int origen, int destino, int distanciaKm) {
        agregarDeposito(origen);
        agregarDeposito(destino);

        adyacencias.get(origen).add(new Ruta(destino, distanciaKm));
        adyacencias.get(destino).add(new Ruta(origen, distanciaKm));
    }

    public int calcularDistanciaMinima(int origen, int destino) {
        if (!adyacencias.containsKey(origen) || !adyacencias.containsKey(destino)) {
            return -1;
        }

        Map<Integer, Integer> distancias = new HashMap<>();
        PriorityQueue<NodoDistancia> cola = new PriorityQueue<>(
                Comparator.comparingInt(NodoDistancia::getDistancia)
        );

        for (Integer deposito : adyacencias.keySet()) {
            distancias.put(deposito, Integer.MAX_VALUE);
        }

        distancias.put(origen, 0);
        cola.add(new NodoDistancia(origen, 0));

        while (!cola.isEmpty()) {
            NodoDistancia actual = cola.poll();

            if (actual.getIdDeposito() == destino) {
                return actual.getDistancia();
            }

            if (actual.getDistancia() > distancias.get(actual.getIdDeposito())) {
                continue;
            }

            for (Ruta ruta : adyacencias.get(actual.getIdDeposito())) {
                int nuevaDistancia = actual.getDistancia() + ruta.getDistanciaKm();

                if (nuevaDistancia < distancias.get(ruta.getDestino())) {
                    distancias.put(ruta.getDestino(), nuevaDistancia);
                    cola.add(new NodoDistancia(ruta.getDestino(), nuevaDistancia));
                }
            }
        }

        return -1;
    }

    public void mostrarGrafo() {
        for (Integer deposito : adyacencias.keySet()) {
            System.out.print("Deposito " + deposito + " conectado con: ");

            for (Ruta ruta : adyacencias.get(deposito)) {
                System.out.print(ruta.getDestino() + "(" + ruta.getDistanciaKm() + "km) ");
            }

            System.out.println();
        }
    }

    private static class Ruta {
        private int destino;
        private int distanciaKm;

        public Ruta(int destino, int distanciaKm) {
            this.destino = destino;
            this.distanciaKm = distanciaKm;
        }

        public int getDestino() {
            return destino;
        }

        public int getDistanciaKm() {
            return distanciaKm;
        }
    }

    private static class NodoDistancia {
        private int idDeposito;
        private int distancia;

        public NodoDistancia(int idDeposito, int distancia) {
            this.idDeposito = idDeposito;
            this.distancia = distancia;
        }

        public int getIdDeposito() {
            return idDeposito;
        }

        public int getDistancia() {
            return distancia;
        }
    }
}