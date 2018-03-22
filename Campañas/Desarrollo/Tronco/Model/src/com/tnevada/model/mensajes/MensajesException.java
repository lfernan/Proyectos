package com.tnevada.model.mensajes;

import com.tnevada.model.campanias.CampaniasException;

import libreriautilmodel.exceptions.TransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class MensajesException extends TransactionalExceptionService {
    public MensajesException(String msg, LoggerType log) {
        super(msg, log);
    }

    protected String getModulo() {
        return "CAMPANIAS";
    }
}
