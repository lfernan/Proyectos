package com.tnevada.model.entidades;

import java.io.Serializable;

public class ParametrosPK implements Serializable {
    private String parCodigo;
    private String parValor;

    public ParametrosPK() {
    }

    public ParametrosPK(String parCodigo, String parValor) {
        this.parCodigo = parCodigo;
        this.parValor = parValor;
    }

    public boolean equals(Object other) {
        if (other instanceof ParametrosPK) {
            final ParametrosPK otherParametrosPK = (ParametrosPK)other;
            final boolean areEqual = true;
            return areEqual;
        }
        return false;
    }

    public int hashCode() {
        return super.hashCode();
    }

    public String getParCodigo() {
        return parCodigo;
    }

    public void setParCodigo(String parCodigo) {
        this.parCodigo = parCodigo;
    }

    public String getParValor() {
        return parValor;
    }

    public void setParValor(String parValor) {
        this.parValor = parValor;
    }
}
