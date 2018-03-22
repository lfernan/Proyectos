package com.tnevada.view.backing.mensajes;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.ListaNegraTelefonos;
import com.tnevada.model.entidades.RelacionMensajeCampania;
import com.tnevada.model.entidades.TipificacionesMenDetalle;
import com.tnevada.model.mensajes.ExpertoMensajesLocal;
import com.tnevada.view.managed.SessionBean;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.Util;

import consultamensaje.consultas.backing.view.AddMensajeElement;
import consultamensaje.consultas.backing.view.AddMensajeResponseElement;
import consultamensaje.consultas.backing.view.GetMensajesElement;
import consultamensaje.consultas.backing.view.GetMensajesResponseElement;
import consultamensaje.consultas.backing.view.MensajePacket;
import consultamensaje.consultas.backing.view.MensajesWS;
import consultamensaje.consultas.backing.view.MensajesWS_Service;
import consultamensaje.consultas.backing.view.types.MensajeDTO;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "mensajes")
@RequestScoped
public class Mensajes implements Serializable {
    private HtmlSelectOneMenu  campania;
    private HtmlSelectOneMenu  tipificacion;
    private HtmlSelectOneMenu  carga;
    private HtmlInputTextarea mensaje;
    private HtmlSelectBooleanCheckbox checkBlack;
    private HtmlInputText agenda;
    private HtmlInputText fecha;
    private SessionBean session;

    public Mensajes() {
        try {
            if (!FacesContext.getCurrentInstance().isPostback()) {
                Util.setValueSessionScope("id", Util.getUrlParameter("id"));
                Util.setValueSessionScope("tel", Util.getUrlParameter("tel"));
                Util.setValueSessionScope("dni", Util.getUrlParameter("pdni"));

                if (Util.getValueSessionScope("id") != null | Util.getValueSessionScope("dni") != null) {
                    List<String> datos = new ArrayList<String>();
                    Long id =
                        Util.getValueSessionScope("id") == null ? null :
                        Long.valueOf((String)Util.getValueSessionScope("id"));
                    String dni =
                        Util.getValueSessionScope("dni") == null ? null : (String)Util.getValueSessionScope("dni");
                    String json = (String)
                        ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getDatos(id,
                                                                                                                       dni);

                    Map map = Util.jsonParse(json);
                    for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                        Map.Entry entry = (Map.Entry)it.next();
                        Object key = entry.getKey();
                        Object value = entry.getValue();
                        datos.add(key + ":" + value);
                        if (((String)key).equalsIgnoreCase("documento")) {
                            Util.setValueSessionScope("dni", value);
                        }
                    }
                    if (Util.getValueSessionScope("tel") != null &&
                        !((String)Util.getValueSessionScope("tel")).equals("")) {
                        datos.add("Teléfono:" + (String)Util.getValueSessionScope("tel"));
                    } else {
                        datos.add("Teléfono:-");
                    }

                    Util.setValueSessionScope("datos", datos);
                    Util.setValueSessionScope("productos",
                                              ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getServiciosProductos(Integer.valueOf((String)Util.getValueSessionScope("dni"))));
                    listarMensajes();
                }

                /*setAgenda(new RichChooseDate());
                getAgenda().setVisible(false);*/
                /*setAgenda(new HtmlInputTextarea());
                setFecha(new HtmlInputTextarea());*/
                setCheckBlack(new HtmlSelectBooleanCheckbox());
                setMensaje(new HtmlInputTextarea());
                setTipificacion(new HtmlSelectOneMenu ());
                setCampania(new HtmlSelectOneMenu ());
                if (getCampania() != null && Util.getValueSessionScope("campania") != null) {
                    getCampania().setValue(Util.getValueSessionScope("campania"));
                    Util.refreshUIComponent(getCampania());
                }
                setCarga(new HtmlSelectOneMenu ());
                if (getCarga() != null && Util.getValueSessionScope("carga") != null) {
                    getCarga().setValue(Util.getValueSessionScope("carga"));
                    Util.refreshUIComponent(getCarga());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String grabar() {
        try {
            AddMensajeElement parametros = new AddMensajeElement();
            parametros.setDni(Long.valueOf((String)Util.getValuePageFlowScope("dni")));
            parametros.setCodUsu((String)Util.resolveExpression("#{SessionBean.usuario}"));
            parametros.setOcod(getCarga().getValue().toString());
            parametros.setOdes(getCarga().getValue().toString());
            parametros.setIcod("A");
            parametros.setIdes("Alta");
            parametros.setCcod(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetId().toString());
            parametros.setCdes(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetId().toString());
            parametros.setCfc(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetAgendar() == 1 ?
                              "true" : "false");
            parametros.setMensaje(getMensaje().getValue() == null ? "-" : getMensaje().getValue().toString());
            parametros.setIdCampania((Long)getCampania().getValue());
            parametros.setFComp(getFecha().getValue() == null ? null :
                                Util.convertirDateToXMLGregorianCalendar((Date)getFecha().getValue()));
            getFecha().setValue(null);
            AddMensajeResponseElement respuesta = getMensajesWS().addMensaje(parametros);
            if (respuesta.getResult().getIdReferencia() != null) {
                Util.ejecutarJs("alertas.info('Numero de Ref. " + respuesta.getResult().getIdReferencia() + "');");
                RelacionMensajeCampania rmc = new RelacionMensajeCampania();
                rmc.setRelMe01NroDoc(Integer.valueOf((String)Util.getValuePageFlowScope("dni")));
                rmc.setRelMe01NroRef(respuesta.getResult().getIdReferencia().intValue());
                rmc.setRelTelefono((String)Util.getValuePageFlowScope("tel"));
                ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).actualizarRelacionMensajeCampania(rmc);
                if (getCheckBlack().isSelected()) {
                    ListaNegraTelefonos l = new ListaNegraTelefonos();
                    l.setTelefono((String)Util.getValuePageFlowScope("tel"));
                    l.setFecha(new Date());
                    ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).guardarListaNegra(l);
                }
            }
            listarMensajes();

            Util.setValuePageFlowScope("id", null);
            Util.setValuePageFlowScope("tel", null);
            Util.setValuePageFlowScope("dni", null);

        } catch (Exception e) {
            e.printStackTrace();            
        } finally {
            Util.setValuePageFlowScope("id", null);
            Util.setValuePageFlowScope("tel", null);
            Util.setValuePageFlowScope("dni", null);
        }
        return null;
    }

    private void renderLista() {
        boolean render = false;
        if (Util.getValuePageFlowScope("listacampanias") != null) {
            for (Campanias c : (List<Campanias>)Util.getValuePageFlowScope("listacampanias")) {
                if (!c.getTipoCampanias().getTipocampDescripcion().equals("MORA") &&
                    c.getCampId() == ((Long)Util.getValuePageFlowScope("campania")).longValue()) {
                    render = true;
                    break;
                }
            }
        }
        Util.setValuePageFlowScope("renderlista", render);
    }        
    
    public final void campaniaChangeListener(final AjaxBehaviorEvent event) {
        Util.setValuePageFlowScope("campania", getCampania().getValue());
        System.out.println(event.getSource());
        renderLista();
    }
    
    public final void tipificacionChangeListener(final AjaxBehaviorEvent event) {
        //if (((TipificacionesMenDetalle)v.getNewValue()).getTipdetAgendar() == 1) {
            /*getAgenda().setVisible(true);
            getFecha().setRequired(true);
            getFecha().setRequiredMessageDetail("Seleccione una Fecha");            
            getFecha().setContentStyle("width:0px;height:0px;opacity:0;");            
            getFecha().setVisible(true); */
        //} else {
            /*getAgenda().setVisible(false);
            getFecha().setRequired(false);*/
        //}
       // Util.refreshUIComponent(getAgenda());
    }

    public void cargaChangeListener(ValueChangeEvent v) {
        Util.setValuePageFlowScope("carga", v.getNewValue());
    }

    /*public void tipificacionChangeListener(ValueChangeEvent v) {
        if (((TipificacionesMenDetalle)v.getNewValue()).getTipdetAgendar() == 1) {
            /*getAgenda().setVisible(true);
            getFecha().setRequired(true);
            getFecha().setRequiredMessageDetail("Seleccione una Fecha");            
            getFecha().setContentStyle("width:0px;height:0px;opacity:0;");            
            getFecha().setVisible(true); */
        //} else {
            /*getAgenda().setVisible(false);
            getFecha().setRequired(false);*/
        /*}
        Util.refreshUIComponent(getAgenda());
    }*/

    private MensajesWS getMensajesWS() {
        MensajesWS_Service mensajesWS_Service = new MensajesWS_Service();
        return mensajesWS_Service.getMensajesWSSoap12HttpPort();
    }

    private void listarMensajes() throws Exception {
        try {
            List<MensajeDTO> mensajes = new ArrayList<MensajeDTO>();
            GetMensajesElement e = new GetMensajesElement();
            e.setDni(Long.valueOf((String)Util.getValueSessionScope("dni")));
            GetMensajesResponseElement g = getMensajesWS().getMensajes(e);
            MensajePacket packet = g.getResult();
            for (Object o : packet.getLista().getItem()) {
                mensajes.add((MensajeDTO)o);
            }
            Util.setValueSessionScope("mensajes", mensajes.isEmpty() ? null : mensajes);
            Util.setValueSessionScope("gestiones",
                                      ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getGestiones(/*(String)Util.resolveExpression("#{SessionBean.usuario}")*/"lefernan",
                                                                                                                                         null,
                                                                                                                                         false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SelectItem> getItemCampania() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            List<Campanias> c =
                ((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).find(null,
                                                                                                               null,
                                                                                                               null,
                                                                                                               null,
                                                                                                               (String)Util.getValueSessionScope("id"),
                                                                                                               false, null);
            Util.setValueSessionScope("listacampanias", c);
            for (Campanias o : c) {
                l.add(new SelectItem(o.getCampId(), o.getCampDescripcion()));
            }
            if (c.size() == 1) {
                getCampania().setValue((c.get(0)).getCampId());
                Util.setValueSessionScope("campania", (c.get(0)).getCampId());
                renderLista();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public List<SelectItem> getItemTipificacion() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        String[] acciones = null;
        try {
            if (FacesContext.getCurrentInstance().getExternalContext().getRequestServerName().startsWith("apps")) {
                acciones = session.obtenerAcciones("12003");
            }            
            if (getCampania().getValue() != null) {
                Util.setValuePageFlowScope("campania", getCampania().getValue());
                for (TipificacionesMenDetalle d :
                     ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getTipificaciones(acciones, Long.valueOf((String)getCampania().getValue()))){
                    if (d.getTipificacion().getTipmenId() != 8L) {
                        l.add(new SelectItem(d, d.getTipdetDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //getTipificacion().
         //   boolean add = FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add(getTipificacion().getClientId());
        return l;
    }

    public void setCampania(HtmlSelectOneMenu  campania) {
        this.campania = campania;
    }

    public HtmlSelectOneMenu  getCampania() {
        return campania;
    }

    public void setTipificacion(HtmlSelectOneMenu  tipificacion) {
        this.tipificacion = tipificacion;
    }

    public HtmlSelectOneMenu  getTipificacion() {
        return tipificacion;
    }

    public void setCarga(HtmlSelectOneMenu  carga) {
        this.carga = carga;
    }

    public HtmlSelectOneMenu  getCarga() {
        return carga;
    }

    public void setMensaje(HtmlInputTextarea mensaje) {
        this.mensaje = mensaje;
    }

    public HtmlInputTextarea getMensaje() {
        return mensaje;
    }

    public void setCheckBlack(HtmlSelectBooleanCheckbox checkBlack) {
        this.checkBlack = checkBlack;
    }

    public HtmlSelectBooleanCheckbox getCheckBlack() {
        return checkBlack;
    }

    public void setAgenda(HtmlInputText agenda) {
        this.agenda = agenda;
    }

    public HtmlInputText getAgenda() {
        return agenda;
    }

    public void setFecha(HtmlInputText fecha) {
        this.fecha = fecha;
    }

    public HtmlInputText getFecha() {
        return fecha;
    }    
}
