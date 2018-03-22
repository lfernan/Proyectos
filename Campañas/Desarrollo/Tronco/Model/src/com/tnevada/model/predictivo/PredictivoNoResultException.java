package com.tnevada.model.predictivo;

import libreriautilmodel.exceptions.NotTransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class PredictivoNoResultException extends NotTransactionalExceptionService {
    public PredictivoNoResultException(String msg, LoggerType log) {
        super(msg, log);
    }

    protected String getModulo() {
        return "CAMPANIAS";
    }
}
