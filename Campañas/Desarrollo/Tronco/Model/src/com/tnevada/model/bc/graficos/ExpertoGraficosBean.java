package com.tnevada.model.bc.graficos;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless(name = "ExpertoGraficos", mappedName = "Campania-Model-ExpertoGraficos")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ExpertoGraficosBean implements ExpertoGraficosLocal {    
    public ExpertoGraficosBean() {
    }
    
    
}
