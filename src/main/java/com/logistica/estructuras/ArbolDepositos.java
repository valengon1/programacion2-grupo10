package com.logistica.estructuras;

import com.logistica.modelo.Deposito;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArbolDepositos {

    private NodoDeposito raiz;

    public ArbolDepositos() {
        this.raiz = null;
    }

    public void insertar(Deposito deposito) {
        raiz = insertarRecursivo(raiz, deposito);
    }

    private NodoDeposito insertarRecursivo(NodoDeposito nodo, Deposito deposito) {
        if (nodo == null) {
            return new NodoDeposito(deposito);
        }

        if (deposito.getId() < nodo.getDeposito().getId()) {
            nodo.setIzquierdo(insertarRecursivo(nodo.getIzquierdo(), deposito));
        } else if (deposito.getId() > nodo.getDeposito().getId()) {
            nodo.setDerecho(insertarRecursivo(nodo.getDerecho(), deposito));
        }

        return nodo;
    }

    public Deposito buscar(int id) {
        return buscarRecursivo(raiz, id);
    }

    private Deposito buscarRecursivo(NodoDeposito nodo, int id) {
        if (nodo == null) {
            return null;
        }

        if (id == nodo.getDeposito().getId()) {
            return nodo.getDeposito();
        }

        if (id < nodo.getDeposito().getId()) {
            return buscarRecursivo(nodo.getIzquierdo(), id);
        } else {
            return buscarRecursivo(nodo.getDerecho(), id);
        }
    }

    public List<Deposito> auditarDepositosPendientes() {
        List<Deposito> auditados = new ArrayList<>();
        auditarPostOrden(raiz, auditados);
        return auditados;
    }

    private void auditarPostOrden(NodoDeposito nodo, List<Deposito> auditados) {
        if (nodo == null) {
            return;
        }

        auditarPostOrden(nodo.getIzquierdo(), auditados);
        auditarPostOrden(nodo.getDerecho(), auditados);

        Deposito deposito = nodo.getDeposito();
        LocalDateTime limite = LocalDateTime.now().minusDays(30);

        if (deposito.getFechaUltimaAuditoria().isBefore(limite)) {
            deposito.setVisitado(true);
            deposito.setFechaUltimaAuditoria(LocalDateTime.now());
            auditados.add(deposito);
        }
    }

    public List<Deposito> obtenerDepositosPorNivel(int nivelBuscado) {
        List<Deposito> depositos = new ArrayList<>();
        obtenerPorNivelRecursivo(raiz, 0, nivelBuscado, depositos);
        return depositos;
    }

    private void obtenerPorNivelRecursivo(NodoDeposito nodo, int nivelActual, int nivelBuscado, List<Deposito> depositos) {
        if (nodo == null) {
            return;
        }

        if (nivelActual == nivelBuscado) {
            depositos.add(nodo.getDeposito());
            return;
        }

        obtenerPorNivelRecursivo(nodo.getIzquierdo(), nivelActual + 1, nivelBuscado, depositos);
        obtenerPorNivelRecursivo(nodo.getDerecho(), nivelActual + 1, nivelBuscado, depositos);
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}