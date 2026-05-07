package com.logistica.modelo;

import java.time.LocalDateTime;

public class Deposito {

    private int id;
    private String nombre;
    private boolean visitado;
    private LocalDateTime fechaUltimaAuditoria;

    public Deposito(int id, String nombre, LocalDateTime fechaUltimaAuditoria) {
        this.id = id;
        this.nombre = nombre;
        this.visitado = false;
        this.fechaUltimaAuditoria = fechaUltimaAuditoria;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public LocalDateTime getFechaUltimaAuditoria() {
        return fechaUltimaAuditoria;
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public void setFechaUltimaAuditoria(LocalDateTime fechaUltimaAuditoria) {
        this.fechaUltimaAuditoria = fechaUltimaAuditoria;
    }

    @Override
    public String toString() {
        return "Deposito{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", visitado=" + visitado +
                ", fechaUltimaAuditoria=" + fechaUltimaAuditoria +
                '}';
    }
}