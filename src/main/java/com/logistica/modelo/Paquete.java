package com.logistica.modelo;

public class Paquete<T> {

    private static int siguienteId = 1;

    private String id;
    private double peso;
    private String destino;
    private boolean urgente;
    private T contenido;

    public Paquete() {
    }

    public Paquete(double peso, String destino, boolean urgente, T contenido) {
        this.id = generarId();
        this.peso = peso;
        this.destino = destino;
        this.urgente = urgente;
        this.contenido = contenido;
    }

    public Paquete(String id, double peso, String destino, boolean urgente, T contenido) {
        this.id = id;
        this.peso = peso;
        this.destino = destino;
        this.urgente = urgente;
        this.contenido = contenido;
        actualizarContador(id);
    }

    private static String generarId() {
        String idGenerado = String.format("%04d", siguienteId);
        siguienteId++;
        return idGenerado;
    }

    private static void actualizarContador(String id) {
        try {
            int numero = Integer.parseInt(id);
            if (numero >= siguienteId) {
                siguienteId = numero + 1;
            }
        } catch (NumberFormatException e) {
            // Si el ID no es numérico, no se actualiza el contador
        }
    }

    public String getId() {
        return id;
    }

    public double getPeso() {
        return peso;
    }

    public String getDestino() {
        return destino;
    }

    public boolean isUrgente() {
        return urgente;
    }

    public T getContenido() {
        return contenido;
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "id='" + id + '\'' +
                ", peso=" + peso +
                ", destino='" + destino + '\'' +
                ", urgente=" + urgente +
                ", contenido=" + contenido +
                '}';
    }
}