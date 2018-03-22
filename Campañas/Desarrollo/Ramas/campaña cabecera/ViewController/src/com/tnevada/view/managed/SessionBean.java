package com.tnevada.view.managed;


import com.tnevada.view.seguridad.AutorizarBean;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionBean {

    private ApplicationBean application;

    private String usuario = "";
    private HashMap mapAcciones = new HashMap();
    private AutorizarBean autorizaBean = null;
    private String fecha;
    private Long legajoUsuario = null;

    public String getInstanceName() {
        return "Instancia: " + System.getProperty("oracle.ons.instancename");
    }

    public SessionBean() throws Exception {
        super();
        this.cargarFecha();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("SESSION_BEAN", this);
    }

    private void cargarFecha() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        String mes = "";
        switch (cal.get(Calendar.MONTH)) {
        case 0:
            mes = "Enero";
            break;
        case 1:
            mes = "Febrero";
            break;
        case 2:
            mes = "Marzo";
            break;
        case 3:
            mes = "Abril";
            break;
        case 4:
            mes = "Mayo";
            break;
        case 5:
            mes = "Junio";
            break;
        case 6:
            mes = "Julio";
            break;
        case 7:
            mes = "Agosto";
            break;
        case 8:
            mes = "Septiembre";
            break;
        case 9:
            mes = "Octubre";
            break;
        case 10:
            mes = "Noviembre";
            break;
        case 11:
            mes = "Diciembre";
            break;
        }
        cal.get(Calendar.MONTH);
        cal.get(Calendar.DAY_OF_MONTH);
        this.fecha = cal.get(Calendar.DAY_OF_MONTH) + " de " + mes + " de " + cal.get(Calendar.YEAR);
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        try {
          if (this.autorizaBean == null)
              this.cargarAutorizaBean();
          this.usuario = autorizaBean.getUsuario();

          if (usuario == null)
              return "";
          return usuario;
         // return "lefernan";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void cargarAutorizaBean() {

        HttpServletRequest req =
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        HttpSession session = req.getSession();
        if (session.getAttribute("AutorizarBean") != null)
            this.autorizaBean = (AutorizarBean)session.getAttribute("AutorizarBean");

    }

    public String getNombreMsg() {
        Iterator i = FacesContext.getCurrentInstance().getMessages();
        if (i == null)
            return "Mensaje de Error";
        while (i.hasNext()) {
            FacesMessage msg = (FacesMessage)i.next();
            if (msg.getSeverity().getOrdinal() == 0)
                // 0 = Información
                return "Mensaje de Informacion";
            else
                // 3 = Error
                return "Mensaje de Error";
        }
        return "";
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setLegajoUsuario(Long legajoUsuario) {
        this.legajoUsuario = legajoUsuario;
    }

    public Long getLegajoUsuario() {
        return this.legajoUsuario;
    }


    public boolean accionHabilitada(String idPrograma, String idAccion) {
        try {
            return this.accHabilitada(idPrograma, idAccion);
        } catch (Exception e) {
            return false;
        }

    }

    private boolean accHabilitada(String idPrograma, String idAccion) {

        if (this.mapAcciones.containsKey(idPrograma)) {
            HashMap hAcciones = (HashMap)this.mapAcciones.get(idPrograma);
            if (hAcciones.containsKey(idAccion))
                return true;
            else {
                String[] acciones = this.obtenerAcciones(idPrograma);
                for (int i = 0; i < acciones.length; i++) {
                    if (acciones[i].trim().equals(idAccion.trim())) {
                        this.agregarAccion(idPrograma, idAccion);
                        return true;
                    }
                }
                return false;
            }
        } else {
            String[] acciones = this.obtenerAcciones(idPrograma);
            for (int i = 0; i < acciones.length; i++) {
                if (acciones[i].trim().equals(idAccion.trim())) {
                    this.agregarAccion(idPrograma, idAccion);
                    return true;
                }
            }
            return false;
        }
    }

    public String[] obtenerAcciones(String idPrograma) {
        if (this.autorizaBean == null)
            this.cargarAutorizaBean();
        return this.autorizaBean.accionesHabilitadas(idPrograma);
    }

    private void agregarAccion(String idPrograma, String idAccion) {
        if (this.mapAcciones.containsKey(idPrograma)) {
            HashMap acciones = (HashMap)this.mapAcciones.get(idPrograma);
            acciones.put(idAccion, null);
        } else {
            HashMap acciones = new HashMap();
            acciones.put(idAccion, null);
            this.mapAcciones.put(idPrograma, acciones);
        }
    }


    public void setApplication(ApplicationBean application) {
        this.application = application;
    }

    public ApplicationBean getApplication() {
        return application;
    }

}
