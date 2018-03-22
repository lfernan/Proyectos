package com.tnevada.model.segmentacion;

import libreriautilmodel.exceptions.NotTransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class SegmentacionNoResultException extends NotTransactionalExceptionService {
    @SuppressWarnings("compatibility:7658747825457011967")
    private static final long serialVersionUID = 1L;

    public SegmentacionNoResultException(String msg, LoggerType log) {
        super(msg, log);
    }

    @Override
    protected String getModulo() {
        return "CAMPANIAS";        
    }
}
