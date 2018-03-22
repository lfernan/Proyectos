package com.tnevada.model.campanias;

import libreriautilmodel.exceptions.TransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class CampaniasException extends TransactionalExceptionService {
    
    public CampaniasException(String msg, LoggerType log) {
        super(msg, log);
    }

    protected String getModulo() {
        return "CAMPANIAS";
    }
}
