package com.logistica.estructuras;

import com.logistica.modelo.Paquete;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class CentroDistribucion {

    private PriorityQueue<Paquete<?>> cola;

    public CentroDistribucion() {
        cola = new PriorityQueue<>((p1, p2) -> {

            if (p1.isUrgente() && !p2.isUrgente()) {
                return -1;
            }

            if (!p1.isUrgente() && p2.isUrgente()) {
                return 1;
            }

            if (p1.getPeso() > 50 && p2.getPeso() <= 50) {
                return -1;
            }

            if (p1.getPeso() <= 50 && p2.getPeso() > 50) {
                return 1;
            }

            return p1.getId().compareTo(p2.getId());
        });
    }

    public void agregarPaquete(Paquete<?> paquete) {
        cola.add(paquete);
    }

    public Paquete<?> procesarPaquete() {
        return cola.poll();
    }

    public Paquete<?> verSiguiente() {
        return cola.peek();
    }

    public boolean estaVacio() {
        return cola.isEmpty();
    }

    public List<Paquete<?>> listarPaquetesDemorados() {
        List<Paquete<?>> demorados = new ArrayList<>();

        for (Paquete<?> paquete : cola) {
            if (paquete.getMinutosIngreso() > 30) {
                demorados.add(paquete);
            }
        }

        return demorados;
    }
}