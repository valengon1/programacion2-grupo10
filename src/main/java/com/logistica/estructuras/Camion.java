package com.logistica.estructuras;

import com.logistica.modelo.Paquete;

import java.util.Stack;

public class Camion {

    private Stack<Paquete<?>> pila;

    public Camion() {
        pila = new Stack<>();
    }

    // O(1)
    public void cargar(Paquete<?> paquete) {
        pila.push(paquete);
    }

    // O(1)
    public Paquete<?> descargar() {
        if (!pila.isEmpty()) {
            return pila.pop();
        }
        return null;
    }

    // O(1)
    public Paquete<?> deshacerCarga() {
        return descargar();
    }

    public boolean estaVacio() {
        return pila.isEmpty();
    }
}