package com.logistica.estructuras;

import com.logistica.modelo.Paquete;

import java.util.PriorityQueue;

public class CentroDistribucion {

    private PriorityQueue<Paquete<?>> cola;

    public CentroDistribucion() {
        cola = new PriorityQueue<>((p1, p2) -> {

            // 1. Urgentes primero
            if (p1.isUrgente() && !p2.isUrgente()) return -1;
            if (!p1.isUrgente() && p2.isUrgente()) return 1;

            // 2. Peso mayor a 50kg
            if (p1.getPeso() > 50 && p2.getPeso() <= 50) return -1;
            if (p1.getPeso() <= 50 && p2.getPeso() > 50) return 1;

            return 0;
        });
    }

    // O(log n)
    public void agregarPaquete(Paquete<?> paquete) {
        cola.add(paquete);
    }

    // O(log n)
    public Paquete<?> procesarPaquete() {
        return cola.poll();
    }

    // O(1)
    public Paquete<?> verSiguiente() {
        return cola.peek();
    }

    public boolean estaVacio() {
        return cola.isEmpty();
    }
}