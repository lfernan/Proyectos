package com.tnevada.view.util;

import oracle.adf.view.rich.context.AdfFacesContext;


public abstract class GestorCache<P extends Object> {
    protected P filtro;

    public GestorCache() {
        if (!AdfFacesContext.getCurrentInstance().isPostback()) {
            filtro = iniciarFiltro();
            Util.getSessionMap().put(getClass().getName() + ".filtro", filtro);
        } else {
            filtro = (P)Util.getSessionMap().get(getClass().getName() + ".filtro");
        }
    }

    public void guardarEnCache(String key, Object object) {
        Util.getSessionMap().put(getClass().getName() + "." + key, object);
    }

    public Object recuperarDeCahce(String key) {
        return Util.getSessionMap().get(getClass().getName() + "." + key);
    }

    public void setFiltro(P filtro) {
        this.filtro = filtro;
    }

    public P getFiltro() {
        return filtro;
    }

    public String cmdLimpiar_action() {
        filtro = iniciarFiltro();
        Util.getSessionMap().put(getClass().getName() + ".filtro", filtro);
        return null;
    }

    public String cmdBuscar_action() {
        try {
            Util.getSessionMap().put(getClass().getName() + ".filtro", filtro);
        } catch (Exception e) {
            Util.addMsgInfo(e.getMessage());
        }
        return null;
    }

    public abstract P iniciarFiltro();

}
