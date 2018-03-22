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

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichChooseDate;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectBooleanCheckbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

public class AgregarMensajes {
    private RichSelectOneChoice campania;
    private RichSelectOneChoice tipificacion;
    private RichSelectOneChoice carga;
    private RichInputText mensaje;
    private RichSelectBooleanCheckbox checkBlack;
    private RichChooseDate agenda;
    private RichInputDate fecha;
    private SessionBean session;

    public AgregarMensajes() {
        try {
   
            if(Util.getValuePageFlowScope("id")==null && Util.getValuePageFlowScope("tel")==null && Util.getValuePageFlowScope("dni")==null){
                Util.setValuePageFlowScope("id", Util.getUrlParameter("id"));
                Util.setValuePageFlowScope("tel", Util.getUrlParameter("tel"));
                Util.setValuePageFlowScope("dni", Util.getUrlParameter("pdni"));            
            }
            
            if(Util.getValuePageFlowScope("id") !=null | Util.getValuePageFlowScope("dni") !=null){            
                List<String> datos = new ArrayList<String>();            
                Long id =Util.getValuePageFlowScope("id")==null?null:Long.valueOf((String)Util.getValuePageFlowScope("id"));
                String dni = Util.getValuePageFlowScope("dni")==null?null:(String)Util.getValuePageFlowScope("dni");
                String json = ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getDatos(id,dni);
                
                Map map = Util.jsonParse(json);
                for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                    Map.Entry entry = (Map.Entry)it.next();
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    datos.add(key + "&nbsp;<b>" + value + "</b>");
                    if(((String)key).equalsIgnoreCase("documento")){
                        Util.setValuePageFlowScope("dni", value);
                    }
                }
                if(Util.getValuePageFlowScope("tel")!=null && !((String)Util.getValuePageFlowScope("tel")).equals("")){
                    datos.add("Telefono&nbsp;<b>" +(String) Util.getValuePageFlowScope("tel") + "</b>");
                }else{
                    datos.add("Telefono&nbsp;<b>-</b>");
                }
                
                Util.setValuePageFlowScope("datos", datos);
                listarMensajes();
            }
            
            setAgenda(new RichChooseDate());
            getAgenda().setVisible(false);                                
            
            setCampania(new RichSelectOneChoice());
            if (getCampania() != null && Util.getValuePageFlowScope("campania")!=null) {
                getCampania().setValue(Util.getValuePageFlowScope("campania"));
                Util.refreshUIComponent(getCampania());
            }
            setCarga(new RichSelectOneChoice());
            if (getCarga() != null && Util.getValuePageFlowScope("carga")!=null) {
                getCarga().setValue(Util.getValuePageFlowScope("carga"));
                Util.refreshUIComponent(getCarga());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Util.ejecutarJs("alertas.error('Se produjo un error al inicializar los datos');");
        }
    }

    public String grabar() {
        try {
            if(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetAgendar()==1 && getFecha().getValue()==null){
                Util.ejecutarJs("alertas.warning('Seleccione una fecha');");
                return null;
            }
            AddMensajeElement parametros = new AddMensajeElement();            
            parametros.setDni(Long.valueOf((String)Util.getValuePageFlowScope("dni")));
            parametros.setCodUsu((String)Util.resolveExpression("#{SessionBean.usuario}"));
            parametros.setOcod(getCarga().getValue().toString());
            parametros.setOdes(getCarga().getValue().toString());
            parametros.setIcod("A");
            parametros.setIdes("Alta");
            parametros.setCcod(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetId().toString());
            parametros.setCdes(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetId().toString());
            parametros.setCfc(((TipificacionesMenDetalle)getTipificacion().getValue()).getTipdetAgendar()==1?"true":"false");
            parametros.setMensaje(getMensaje().getValue()==null?"-":getMensaje().getValue().toString());
            parametros.setIdCampania((Long)getCampania().getValue());             
            parametros.setFComp(getFecha().getValue()==null?null:Util.convertirDateToXMLGregorianCalendar((Date)getFecha().getValue()));            
            getFecha().setValue(null);
            AddMensajeResponseElement respuesta = getMensajesWS().addMensaje(parametros);
            if(respuesta.getResult().getIdReferencia()!=null){
                Util.ejecutarJs("alertas.info('Numero de Ref. "+respuesta.getResult().getIdReferencia()+"');");                
                RelacionMensajeCampania rmc = new RelacionMensajeCampania();                
                rmc.setRelMe01NroDoc(Integer.valueOf((String)Util.getValuePageFlowScope("dni")));
                rmc.setRelMe01NroRef(respuesta.getResult().getIdReferencia().intValue());
                rmc.setRelTelefono((String)Util.getValuePageFlowScope("tel"));
                ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).actualizarRelacionMensajeCampania(rmc);               
                if(getCheckBlack().isSelected()){
                    ListaNegraTelefonos l = new ListaNegraTelefonos();
                    l.setTelefono((String)Util.getValuePageFlowScope("tel"));
                    ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).guardarListaNegra(l);
                }
            }          
            listarMensajes();
            
            Util.setValuePageFlowScope("id", null);
            Util.setValuePageFlowScope("tel", null);
            Util.setValuePageFlowScope("dni", null); 
            
        } catch (Exception e) {            
            Util.ejecutarJs("alertas.error('Error al guardar el mensaje');");
        }finally{
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
            List<Campanias> c = ((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).find(null,null,null,null,(String)Util.getValuePageFlowScope("id"),false);
            Util.setValuePageFlowScope("listacampanias",c);
            for (Campanias o : c) {
                l.add(new SelectItem(o.getCampId(), o.getCampDescripcion()));
            }
            if (c.size() == 1) {
                getCampania().setValue((c.get(0)).getCampId());
                Util.setValuePageFlowScope("campania", (c.get(0)).getCampId());
                renderLista();
            }                        
        } catch (Exception e) {            
            Util.ejecutarJs("alertas.error('Error al cargar las campañas');");
        }
        return l;
    }

    public List<SelectItem> getItemTipificacion() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try { 
            if(Util.getValuePageFlowScope("campania")!=null){
                for (TipificacionesMenDetalle d : ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getTipificaciones(/*session.obtenerAcciones("12003")*/null,(Long)Util.getValuePageFlowScope("campania"))) {
                    if (d.getTipificacion().getTipmenId() != 8L){
                        l.add(new SelectItem(d, d.getTipdetDescripcion()));
                    }
                }
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error al cargar las tipificaciones');");
        }
        return l;
    }
    
    private void renderLista(){
        boolean render = false;
        if(Util.getValuePageFlowScope("listacampanias")!=null){
            for(Campanias c:(List<Campanias>)Util.getValuePageFlowScope("listacampanias")){
                if(!c.getTipoCampanias().getTipocampDescripcion().equals("MORA") && c.getCampId()==((Long)Util.getValuePageFlowScope("campania")).longValue()){
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
        Util.setValuePageFlowScope("carga", v.getNewValue());
    }
    
    public void tipificacionChangeListener(ValueChangeEvent v) {
        if(((TipificacionesMenDetalle)v.getNewValue()).getTipdetAgendar()==1){
            getAgenda().setVisible(true);
        }else{
            getAgenda().setVisible(false);
        }
        Util.refreshUIComponent(getAgenda());
    }
    
    private MensajesWS getMensajesWS(){
        MensajesWS_Service mensajesWS_Service = new MensajesWS_Service();
        return mensajesWS_Service.getMensajesWSSoap12HttpPort();
    }
    
    private void listarMensajes() throws Exception {
        try{
        List<MensajeDTO> mensajes = new ArrayList<MensajeDTO>();                        
        GetMensajesElement e = new GetMensajesElement();
        e.setDni(Long.valueOf((String)Util.getValuePageFlowScope("dni")));
        GetMensajesResponseElement g = getMensajesWS().getMensajes(e);
        MensajePacket packet = g.getResult();
        for(Object o:packet.getLista().getItem()){
            mensajes.add((MensajeDTO)o);
        }
        Util.setValuePageFlowScope("mensajes", mensajes.isEmpty()?null:mensajes);
        Util.setValuePageFlowScope("gestiones", ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getGestiones((String)Util.resolveExpression("#{SessionBean.usuario}"), null,false));
        }catch(Exception e){
            e.printStackTrace();
            Util.ejecutarJs("alertas.error('Error al listar los mensajes');");
        }
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
}
