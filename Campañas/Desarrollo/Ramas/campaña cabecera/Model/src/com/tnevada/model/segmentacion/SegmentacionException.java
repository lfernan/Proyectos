package com.tnevada.model.segmentacion;

import libreriautilmodel.exceptions.TransactionalExceptionService;

import libreriautilmodel.loggers.LoggerType;

public class SegmentacionException extends TransactionalExceptionService {
    @SuppressWarnings("compatibility:-2825155803365352625")
    private static final long serialVersionUID = 1L;

    public SegmentacionException(String msg, LoggerType log) {
        super(msg, log);
    }

    @Override
    protected String getModulo() {
        return "CAMPANIAS";
    }
}
