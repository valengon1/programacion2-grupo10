package com.logistica.estructuras;

import com.logistica.modelo.Paquete;

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