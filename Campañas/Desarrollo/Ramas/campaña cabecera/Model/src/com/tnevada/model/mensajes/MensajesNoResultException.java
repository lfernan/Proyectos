package com.tnevada.model.mensajes;

import com.tnevada.model.campanias.CampaniasNoResultException;

import libreriautilmodel.exceptions.NotTransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class MensajesNoResultException extends NotTransactionalExceptionService {

    public MensajesNoResultException(String msg, LoggerType log) {
        super(msg, log);
    }

    protected String getModulo() {
        return "CAMPANIAS";
    }
}
