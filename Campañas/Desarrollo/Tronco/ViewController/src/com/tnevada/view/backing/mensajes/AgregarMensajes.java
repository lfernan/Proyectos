package com.tnevada.view.backing.mensajes;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasNoSocios;
import com.tnevada.model.entidades.CampaniasNoSociosMensajes;
import com.tnevada.model.entidades.ListaNegraTelefonos;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.Parp006m;
import com.tnevada.model.entidades.Parp008m;
import com.tnevada.model.entidades.RelacionMensajeCampania;
import com.tnevada.model.entidades.SucursalDTO;
import com.tnevada.model.entidades.TipificacionesMenDetalle;
import com.tnevada.model.mensajes.ExpertoMensajesLocal;
import com.tnevada.model.segmentacion.ExpertoSegmentacionLocal;
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

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichChooseDate;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.DisclosureEvent;

public class AgregarMensajes {
    private RichSelectOneChoice campania;
    private RichSelectOneChoice tipificacion;
    private RichSelectOneChoice carga;
    private RichInputText mensaje;
    private RichSelectBooleanCheckbox checkBlack;
    private RichChooseDate agenda;
    private RichInputDate fecha;
    private SessionBean session;
    private RichInputText documento;
    private RichSelectOneChoice tipoDoc;
    private RichInputText nombre;
    private RichInputText apellido;
    private RichSelectOneChoice sexo;
    private RichInputDate nacimiento;
    private RichSelectOneChoice condLaboral;
    private RichInputText telefono1;
    private RichSelectOneChoice sucursal;

    public AgregarMensajes() {
        try {
            if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                Util.setValuePageFlowScope("id", Util.getUrlParameter("id"));
                Util.setValuePageFlowScope("tel", Util.getUrlParameter("tel"));
                Util.setValuePageFlowScope("dni", Util.getUrlParameter("pdni"));

                if (Util.getValuePageFlowScope("id") != null | Util.getValuePageFlowScope("dni") != null) {
                    List<String> datos = new ArrayList<String>();
                    Long id =
                        Util.getValuePageFlowScope("id") == null ? null :
                        Long.valueOf((String)Util.getValuePageFlowScope("id"));
                    String dni = Util.getValuePageFlowScope("dni") == null ? null : (String)Util.getValuePageFlowScope("dni");
                    Object instancia = ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getDatos(id,dni);
                                        
                    if(instancia instanceof CampaniasNoSocios){
                        Util.setValuePageFlowScope("ns", (CampaniasNoSocios)instancia);
                        Util.setValuePageFlowScope("datos", null);   
                        Util.setValuePageFlowScope("productos",null);                               
                        
                        if(Util.getValuePageFlowScope("sucursales")==null){
                            List<SucursalDTO> sucursales = ((ExpertoSegmentacionLocal)CachingServiceLocator.getInstance().lookup("ExpertoSegmentacion")).getSucursalesDTO();                        
                            List<SelectItem> l = new ArrayList<SelectItem>();                                          
                            for (SucursalDTO s : sucursales) {
                                l.add(new SelectItem(s.getId(), s.getNombre()));
                            }                        
                            Util.setValuePageFlowScope("sucursales", l);
                            Util.setValuePageFlowScope("sucusalDTO", sucursales);
                        }
                        if(Util.getValuePageFlowScope("tipodoc")==null){
                            List<Parp006m> tipoDoc = ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getTipoDocumento();
                            List<SelectItem> ltd = new ArrayList<SelectItem>();                                          
                            for (Parp006m s : tipoDoc) {                                
                                ltd.add(new SelectItem(s.getPa06TipDoc(), s.getPa06DesTip()));
                            }                        
                            CampaniasNoSocios ns = (CampaniasNoSocios)Util.getValuePageFlowScope("ns");
                            ns.setCansTipoDocumento(1);
                            Util.setValuePageFlowScope("ns", ns);
                            Util.setValuePageFlowScope("tipodoc", ltd);
                        }
                        if(Util.getValuePageFlowScope("estadocivil")==null){
                            List<Parp008m> estadoCivil = ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getEstadoCivil();
                            List<SelectItem> lec = new ArrayList<SelectItem>();                                          
                            for (Parp008m s : estadoCivil) {
                                lec.add(new SelectItem(s.getPa08EstCiv(), s.getPa08DesCiv()));
                            }                        
                            Util.setValuePageFlowScope("estadocivil", lec);
                        }
                        if(Util.getValuePageFlowScope("condlaboral")==null){
                            List<Parametros> condLaboral = ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getCondicionLaboral();
                            List<SelectItem> lcl = new ArrayList<SelectItem>();                                          
                            for (Parametros s : condLaboral) {                                
                                lcl.add(new SelectItem(Integer.valueOf(s.getParValor()), s.getParDescripcion()));
                            }                        
                            Util.setValuePageFlowScope("condlaboral", lcl);
                        }
                    }else{
                        Util.setValuePageFlowScope("ns", null);
                        Map map = Util.jsonParse((String)instancia);
                        for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                            Map.Entry entry = (Map.Entry)it.next();
                            Object key = entry.getKey();
                            Object value = entry.getValue();
                            datos.add(key + "&nbsp;<b>" + value + "</b>");
                            if (((String)key).equalsIgnoreCase("documento")) {
                                Util.setValuePageFlowScope("dni", value);
                            }
                        }
                        if (Util.getValuePageFlowScope("tel") != null &&
                            !((String)Util.getValuePageFlowScope("tel")).equals("")) {
                            datos.add("Teléfono&nbsp;<b>" + (String)Util.getValuePageFlowScope("tel") + "</b>");
                        } else {
                            datos.add("Teléfono&nbsp;<b>-</b>");
                        }
    
                        Util.setValuePageFlowScope("datos", datos);                    
                        Util.setValuePageFlowScope("productos",
                                                   ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getServiciosProductos(Integer.valueOf((String)Util.getValuePageFlowScope("dni"))));
                    }
                    listarMensajes();
                }

                setAgenda(new RichChooseDate());
                getAgenda().setVisible(false);

                setCampania(new RichSelectOneChoice());
                if (getCampania() != null && Util.getValuePageFlowScope("campania") != null) {
                    getCampania().setValue(Util.getValuePageFlowScope("campania"));
                    Util.refreshUIComponent(getCampania());
                }
                setCarga(new RichSelectOneChoice());
                /*if (getCarga() != null && Util.getValuePageFlowScope("carga") != null) {
                    getCarga().setValue(Util.getValuePageFlowScope("carga"));
                    Util.refreshUIComponent(getCarga());
                }*/
                if (getCarga() != null && Util.getValueSessionScope("carga") != null) {
                    getCarga().setValue(Util.getValueSessionScope("carga"));
                    Util.refreshUIComponent(getCarga());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Util.ejecutarJs("alertas.error('Se produjo un error al inicializar los datos');");
        }
    }

    public String grabar() {
        try {
            if (Util.getValuePageFlowScope("ns")!=null){                                
                CampaniasNoSocios cns = (CampaniasNoSocios)Util.getValuePageFlowScope("ns");
                CampaniasNoSociosMensajes cnsm = new CampaniasNoSociosMensajes();
                cnsm.setCansmCodigoOrigen(getCarga().getValue().toString());
                cnsm.setCansmFecha(new Timestamp(new Date().getTime()));
                cnsm.setCansmFechaCompromiso((Date)getFecha().getValue());
                cnsm.setCansmMensaje(getMensaje().getValue() == null ? "-" : getMensaje().getValue().toString());
                cnsm.setCansmTipificacion(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetId());
                cnsm.setCansmUsuario(((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).getLegajoPorNombreDeUsuario((String)Util.resolveExpression("#{SessionBean.usuario}")));                      
                cns.addCampaniasNoSociosMensajes(cnsm);                
                cns = ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).mergeNoSocios(cns);
                Util.setValuePageFlowScope("ns",cns);
                RelacionMensajeCampania rmc = new RelacionMensajeCampania();
                rmc.setRelMe01NroDoc(Util.getValuePageFlowScope("dni")==null?null:Integer.valueOf((String)Util.getValuePageFlowScope("dni")));
                rmc.setRelMe01NroRef(cns.getCampaniasNoSociosMensajesList().get(cns.getCampaniasNoSociosMensajesList().size()-1).getCansmId().intValue());                
                rmc.setRelCampId((Long)getCampania().getValue());
                rmc.setRelTelefono((String)Util.getValuePageFlowScope("tel"));
                ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).guardarRelacionMensajeCampania(rmc);
                Util.ejecutarJs("alertas.info('Se guardo correctamente el mensaje.');");
            }else{         
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
                    rmc.setRelCampId((Long)getCampania().getValue());
                    ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).actualizarRelacionMensajeCampania(rmc);                 
                }
            }
            if (getCheckBlack().isSelected()) {
                ListaNegraTelefonos l = new ListaNegraTelefonos();
                l.setTelefono((String)Util.getValuePageFlowScope("tel"));
                l.setFecha(new Date());
                ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).guardarListaNegra(l);
            }
            listarMensajes();

            Util.setValuePageFlowScope("id", null);
            Util.setValuePageFlowScope("tel", null);
            Util.setValuePageFlowScope("dni", null);

        } catch (Exception e) {
            //e.printStackTrace();
            Util.ejecutarJs("alertas.error('Error al guardar el mensaje "+e.getMessage()+"');");
        } finally {
            Util.setValuePageFlowScope("id", null);
            Util.setValuePageFlowScope("tel", null);
            Util.setValuePageFlowScope("dni", null);
        }
        return null;
    }

    public String cancelar() {
        // Add event code here...
        return null;
    }

    public List<SelectItem> getItemCampania() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            List<Campanias> c =
                ((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).find(null,
                                                                                                               null,
                                                                                                               null,
                                                                                                               null,
                                                                                                               (String)Util.getValuePageFlowScope("id"),
                                                                                                               false,null);
            Util.setValuePageFlowScope("listacampanias", c);
            for (Campanias o : c) {
                l.add(new SelectItem(o.getCampId(), o.getCampDescripcion()));
            }
            if (c.size() == 1) {
                getCampania().setValue((c.get(0)).getCampId());
                Util.setValuePageFlowScope("campania", (c.get(0)).getCampId());
                if(c.get(0).getTipoCampanias().getTipocampId()==5L){
                    Util.setValuePageFlowScope("datos", null);
                }
                renderLista();
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error al cargar las campañas');");
        }
        return l;
    }

    public List<SelectItem> getItemTipificacion() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        String[] acciones = null;
        try {
            if(FacesContext.getCurrentInstance().getExternalContext().getRequestServerName().startsWith("apps")){
                acciones = session.obtenerAcciones("12003");
            }
            if (Util.getValuePageFlowScope("campania") != null) {
                for (TipificacionesMenDetalle d :
                     ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getTipificaciones( acciones,
                                                                                                                              (Long)Util.getValuePageFlowScope("campania"))) {
                    if (d.getTipificacion().getTipmenId() != 8L) {
                        l.add(new SelectItem(d, d.getTipdetDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error al cargar las tipificaciones');");
        }
        return l;
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

    public void campaniaChangeListener(ValueChangeEvent v) {
        Util.setValuePageFlowScope("campania", v.getNewValue());
        renderLista();
    }

    public void cargaChangeListener(ValueChangeEvent v) {
        Util.setValueSessionScope("carga", v.getNewValue());
    }

    public void tipificacionChangeListener(ValueChangeEvent v) { 
        if(Util.getValuePageFlowScope("ns")!=null && ((TipificacionesMenDetalle)v.getNewValue()).getTipdetId()==96L){
            Util.setValuePageFlowScope("validarns", true);           
            Util.refreshUIComponent(getDocumento());
            Util.refreshUIComponent(getTipoDoc());
            Util.refreshUIComponent(getNombre());
            Util.refreshUIComponent(getApellido());
            Util.refreshUIComponent(getSexo());
            Util.refreshUIComponent(getNacimiento());
            Util.refreshUIComponent(getCondLaboral());
            Util.refreshUIComponent(getTelefono1());
            Util.refreshUIComponent(getSucursal());         
        }else{
            Util.setValuePageFlowScope("validarns", false);
            Util.refreshUIComponent(getDocumento());
            Util.refreshUIComponent(getTipoDoc());
            Util.refreshUIComponent(getNombre());
            Util.refreshUIComponent(getApellido());
            Util.refreshUIComponent(getSexo());
            Util.refreshUIComponent(getNacimiento());
            Util.refreshUIComponent(getCondLaboral());
            Util.refreshUIComponent(getTelefono1());
            Util.refreshUIComponent(getSucursal());        
        }
        
        if (((TipificacionesMenDetalle)v.getNewValue()).getTipdetAgendar() == 1) {
            getAgenda().setVisible(true);
            getFecha().setRequired(true);
            getFecha().setRequiredMessageDetail("Seleccione una Fecha");            
            getFecha().setContentStyle("width:0px;height:0px;opacity:0;");            
            getFecha().setVisible(true); 
        } else {
            getAgenda().setVisible(false);
            getFecha().setRequired(false);
        }
        Util.refreshUIComponent(getAgenda());
    }
    
    public void sucursalChangeListener(ValueChangeEvent v) {
        CampaniasNoSocios cns = (CampaniasNoSocios)Util.getValuePageFlowScope("ns");        
        for(SucursalDTO s:(List<SucursalDTO>)Util.getValuePageFlowScope("sucusalDTO")){
            if(v.getNewValue() == s.getId()){
                cns.setCansProvincia(s.getProvincia());       
            }
        }        
        Util.setValuePageFlowScope("ns", cns);
    }

    private MensajesWS getMensajesWS() {
        MensajesWS_Service mensajesWS_Service = new MensajesWS_Service();
        return mensajesWS_Service.getMensajesWSSoap12HttpPort();
    }

    private void listarMensajes() throws Exception {
        try {            
            if(Util.getValuePageFlowScope("ns")!=null){                
                List<MensajeNsDTO> mensajes = new ArrayList<MensajeNsDTO>();
                CampaniasNoSocios cns = (CampaniasNoSocios)Util.getValuePageFlowScope("ns");                
                for(CampaniasNoSociosMensajes cnsm:cns.getCampaniasNoSociosMensajesList()){
                    MensajeNsDTO m = new MensajeNsDTO();
                    m.setId(cnsm.getCansmId());
                    m.setUsuarioLegajo(cnsm.getCansmUsuario());
                    m.setTipoDeOrigen(cnsm.getCansmCodigoOrigen());
                    m.setProducto(((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getConsulta(cnsm.getCampaniasNoSocios().getCampaniasBase().getCampania().getCampCartera()).getConsNombre());
                    m.setFechaDeCompromiso(cnsm.getCansmFechaCompromiso());                    
                    m.setFecha(cnsm.getCansmFecha());
                    m.setMensaje(cnsm.getCansmMensaje());
                    m.setClasificacion(((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getTipificaciones(cnsm.getCansmTipificacion()).getTipdetDescripcion());
                    m.setUsuario(((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).getLegajoPorNombreDeUsuario(cnsm.getCansmUsuario()));                    
                    mensajes.add(m);
                }                

                Collections.sort(mensajes);
                Util.setValuePageFlowScope("mensajes", mensajes.isEmpty() ? null : mensajes);
                
            }else{
                List<MensajeDTO> mensajes = new ArrayList<MensajeDTO>();
                GetMensajesElement e = new GetMensajesElement();
                e.setDni(Long.valueOf((String)Util.getValuePageFlowScope("dni")));
                GetMensajesResponseElement g = getMensajesWS().getMensajes(e);
                MensajePacket packet = g.getResult();
                for (Object o : packet.getLista().getItem()) {
                    mensajes.add((MensajeDTO)o);
                }
                Util.setValuePageFlowScope("mensajes", mensajes.isEmpty() ? null : mensajes);
            }            
            Util.setValuePageFlowScope("gestiones",
                                       ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getGestiones((String)Util.resolveExpression("#{SessionBean.usuario}"),
                                                                                                                                          null,
                                                                                                                                          false));
        } catch (Exception e) {
            //e.printStackTrace();
            throw new Exception("Error al listar los mensajes");
            //Util.ejecutarJs("alertas.error('Error al listar los mensajes');");
        }
    }
    
    public Date getMaxFecha(){        
        Calendar c = Calendar.getInstance();  
        c.clear();
        c.setTime(new Date()); 
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        c.set(Calendar.DATE, c.get(Calendar.DATE)-1);
        return c.getTime();
    } 
    
    public Date getMaxFechaNacimiento() {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.setTime(new Date());
        c.set(Calendar.YEAR, c.get(Calendar.YEAR) - 18);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH));
        c.set(Calendar.DATE, c.get(Calendar.DATE));
        return c.getTime();
    }
    
    public void setCampania(RichSelectOneChoice campania) {
        this.campania = campania;
    }

    public RichSelectOneChoice getCampania() {
        return campania;
    }

    public void setTipificacion(RichSelectOneChoice tipificacion) {
        this.tipificacion = tipificacion;
    }

    public RichSelectOneChoice getTipificacion() {
        return tipificacion;
    }

    public void setCarga(RichSelectOneChoice carga) {
        this.carga = carga;
    }

    public RichSelectOneChoice getCarga() {
        return carga;
    }

    public void setMensaje(RichInputText mensaje) {
        this.mensaje = mensaje;
    }

    public RichInputText getMensaje() {
        return mensaje;
    }

    public void setCheckBlack(RichSelectBooleanCheckbox checkBlack) {
        this.checkBlack = checkBlack;
    }

    public RichSelectBooleanCheckbox getCheckBlack() {
        return checkBlack;
    }

    public void setAgenda(RichChooseDate agenda) {
        this.agenda = agenda;
    }

    public RichChooseDate getAgenda() {
        return agenda;
    }

    public void setFecha(RichInputDate fecha) {
        this.fecha = fecha;
    }

    public RichInputDate getFecha() {
        return fecha;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setDocumento(RichInputText documento) {
        this.documento = documento;
    }

    public RichInputText getDocumento() {
        return documento;
    }

    public void setTipoDoc(RichSelectOneChoice tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public RichSelectOneChoice getTipoDoc() {
        return tipoDoc;
    }

    public void setNombre(RichInputText nombre) {
        this.nombre = nombre;
    }

    public RichInputText getNombre() {
        return nombre;
    }

    public void setApellido(RichInputText apellido) {
        this.apellido = apellido;
    }

    public RichInputText getApellido() {
        return apellido;
    }

    public void setSexo(RichSelectOneChoice sexo) {
        this.sexo = sexo;
    }

    public RichSelectOneChoice getSexo() {
        return sexo;
    }

    public void setNacimiento(RichInputDate nacimiento) {
        this.nacimiento = nacimiento;
    }

    public RichInputDate getNacimiento() {
        return nacimiento;
    }

    public void setCondLaboral(RichSelectOneChoice condLaboral) {
        this.condLaboral = condLaboral;
    }

    public RichSelectOneChoice getCondLaboral() {
        return condLaboral;
    }

    public void setTelefono1(RichInputText telefono1) {
        this.telefono1 = telefono1;
    }

    public RichInputText getTelefono1() {
        return telefono1;
    }

    public void setSucursal(RichSelectOneChoice sucursal) {
        this.sucursal = sucursal;
    }

    public RichSelectOneChoice getSucursal() {
        return sucursal;
    }

    public void item2DisclosureListener(DisclosureEvent ev) {
        System.out.println(ev.isExpanded());
    }
}
