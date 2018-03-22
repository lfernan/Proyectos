package com.tnevada.model.predictivo;

import libreriautilmodel.exceptions.TransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class PredictivoException extends TransactionalExceptionService {
    public PredictivoException(String msg, LoggerType log) {
        super(msg, log);
    }

    protected String getModulo() {
        return "CAMPANIAS";
    }
}
