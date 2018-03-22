package com.tnevada.model.campanias;

import libreriautilmodel.exceptions.NotTransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class CampaniasNoResultException extends NotTransactionalExceptionService {
    
    public CampaniasNoResultException(String msg, LoggerType log) {
        super(msg, log);
    }

    protected String getModulo() {
        return "CAMPANIAS";
    }
}
