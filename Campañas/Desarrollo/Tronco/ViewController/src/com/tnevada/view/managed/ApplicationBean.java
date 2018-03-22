package com.tnevada.view.managed;


import javax.faces.context.FacesContext;

import javax.servlet.ServletContext;

public class ApplicationBean {
    private String urlServicioAyuda = null;
    private String APP_VERSION = "3.0.9";
    private static String urlDesautorizado;

    public ApplicationBean() {
    }

    public String getUrlServicioAyuda() {

        try {
            if (urlServicioAyuda == null) {
                //FIXME
                /*Parametros param =
                    getExpertoParametro().buscarParametro("URL", "SERVICIO_AYUDA");
                if (param != null)
                    this.urlServicioAyuda = param.getParDescripcion();*/
            }
        } catch (Exception e) {
            return "";
        }

        return urlServicioAyuda;
    }

    public static String getPathServlet() {
        ServletContext servletContext =
            (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("/");
        return path;
    }

    public void setAPP_VERSION(String APP_VERSION) {
        this.APP_VERSION = APP_VERSION;
    }

    public String getAPP_VERSION() {
        return APP_VERSION;
    }


    public static void setUrlDesautorizado(String urlDesautorizado) {
        ApplicationBean.urlDesautorizado = urlDesautorizado;
    }

    public static String getUrlDesautorizado() {
        return urlDesautorizado;
    }

    public void setUrlServicioAyuda(String urlServicioAyuda) {
        this.urlServicioAyuda = urlServicioAyuda;
    }
}
