package com.logistica.estructuras;

import com.logistica.modelo.Deposito;

public class NodoDeposito {

    private Deposito deposito;
    private NodoDeposito izquierdo;
    private NodoDeposito derecho;

    public NodoDeposito(Deposito deposito) {
        this.deposito = deposito;
        this.izquierdo = null;
        this.derecho = null;
    }

    public Deposito getDeposito() {
        return deposito;
    }

    public NodoDeposito getIzquierdo() {
        return izquierdo;
    }

    public NodoDeposito getDerecho() {
        return derecho;
    }

    public void setIzquierdo(NodoDeposito izquierdo) {
        this.izquierdo = izquierdo;
    }

    public void setDerecho(NodoDeposito derecho) {
        this.derecho = derecho;
    }
}