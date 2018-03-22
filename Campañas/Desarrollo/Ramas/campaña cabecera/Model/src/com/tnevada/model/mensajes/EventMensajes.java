package com.tnevada.model.mensajes;


public class EventMensajes {
    private String message;
    CallBack callBack;

    public interface CallBack {
        void onCallBack(String msj);
    }

    public EventMensajes(String message,CallBack c) {
        this.message = message;
        this.callBack = c;
        this.callBack.onCallBack(this.message);        
    }
}
