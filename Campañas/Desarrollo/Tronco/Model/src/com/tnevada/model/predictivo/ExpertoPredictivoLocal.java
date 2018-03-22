package com.tnevada.model.predictivo;

import com.tnevada.model.entidades.TipoDeTelefono;

import java.io.File;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ExpertoPredictivoLocal {
    List<TipoDeTelefono> getTipoDeTelefonos() throws PredictivoNoResultException;

    void generarPredictivo(File path, Long segmento, List<TipoDeTelefono> telefonos) throws PredictivoNoResultException;
}
