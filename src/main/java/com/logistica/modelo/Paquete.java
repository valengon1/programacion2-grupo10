package com.logistica.modelo;

public class Paquete<T> {

    private static int siguienteId = 1;

    private String id;
    private double peso;
    private String destino;
    private boolean urgente;
    private T contenido;
    private int minutosIngreso;

    // Constructor vacío necesario para leer desde JSON
    public Paquete() {
    }

    // Constructor completo con ID y minutosIngreso
    public Paquete(String id, double peso, String destino, boolean urgente, T contenido, int minutosIngreso) {
        this.id = id;
        this.peso = peso;
        this.destino = destino;
        this.urgente = urgente;
        this.contenido = contenido;
        this.minutosIngreso = minutosIngreso;
    }

    // Constructor viejo, para no romper código anterior
    public Paquete(String id, double peso, String destino, boolean urgente, T contenido) {
        this(id, peso, destino, urgente, contenido, 0);
    }

    // Constructor sin ID, por si el sistema genera ID automáticamente
    public Paquete(double peso, String destino, boolean urgente, T contenido, int minutosIngreso) {
        this.id = generarId();
        this.peso = peso;
        this.destino = destino;
        this.urgente = urgente;
        this.contenido = contenido;
        this.minutosIngreso = minutosIngreso;
    }

    // Constructor viejo sin minutosIngreso
    public Paquete(double peso, String destino, boolean urgente, T contenido) {
        this(peso, destino, urgente, contenido, 0);
    }

    private static String generarId() {
        String idGenerado = String.format("%04d", siguienteId);
        siguienteId++;
        return idGenerado;
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

    public int getMinutosIngreso() {
        return minutosIngreso;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setUrgente(boolean urgente) {
        this.urgente = urgente;
    }

    public void setContenido(T contenido) {
        this.contenido = contenido;
    }

    public void setMinutosIngreso(int minutosIngreso) {
        this.minutosIngreso = minutosIngreso;
    }

    @Override
    public String toString() {
        return "Paquete{" +
                "id='" + id + '\'' +
                ", peso=" + peso +
                ", destino='" + destino + '\'' +
                ", urgente=" + urgente +
                ", contenido=" + contenido +
                ", minutosIngreso=" + minutosIngreso +
                '}';
    }
}