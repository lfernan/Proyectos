package com.tnevada.view.backing.mensajes;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.Menp004m;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.TipoCampanias;
import com.tnevada.model.mensajes.EventMensajes;
import com.tnevada.model.mensajes.EventObserver;
import com.tnevada.model.mensajes.ExpertoMensajesLocal;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import javax.enterprise.event.Observes;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

public class ConsultarMensajes implements EventMensajes.CallBack {
    private ExpertoMensajesLocal expertoMensajes;
    private ExpertoCamapaniasLocal expertoCampanias;
    private RichInputDate bindingFechaDesde;
    private RichInputDate bindingFechaHasta;
    private RichSelectOneChoice bindingTipoOrigen;
    private RichSelectOneChoice bindingClasificacion;
    private RichSelectOneChoice bindingCampania;
    private RichSelectOneChoice bindingTipoCampania;
    private RichInputText bindingDocumento;
    private RichTable bindingTabla;
    private String path;

    public ConsultarMensajes() {
        try {
            setExpertoMensajes((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes"));
            setExpertoCampanias((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias"));
            setBindingFechaDesde(new RichInputDate());
            setBindingFechaHasta(new RichInputDate());
            setBindingTipoOrigen(new RichSelectOneChoice());
            setBindingClasificacion(new RichSelectOneChoice());
            setBindingCampania(new RichSelectOneChoice());
            setBindingDocumento(new RichInputText());
            setBindingTabla(new RichTable());
            setBindingTipoCampania(new RichSelectOneChoice());
            Util.setValueSessionScope("icoexcel", false);            
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error al iniciar la aplicacion');");
        }
    }

    public String buscar() {
        try {
            if (getBindingFechaDesde().getValue() == null && getBindingFechaHasta().getValue() == null &&
                getBindingTipoOrigen().getValue() == null && getBindingClasificacion().getValue() == null &&
                getBindingCampania().getValue() == null && getBindingDocumento().getValue() == null) {                
                Util.ejecutarJs("alertas.warning('Debe seleccionar al menos un filtro');");
            } else {
                
                Util.setValueSessionScope("path",Util.getRealPath(File.separator +"reportes" + File.separator + "Base de Clientes.xlsx"));
                Future f = getExpertoMensajes().getVistaMensajesDTO(this, (String)Util.getValueSessionScope("path"),
                                                         getBindingFechaDesde().getValue() == null ? null :
                                                         (Date)getBindingFechaDesde().getValue(),
                                                         getBindingFechaHasta().getValue() == null ? null :
                                                         (Date)getBindingFechaHasta().getValue(),
                                                         getBindingDocumento().getValue() == null ? null :
                                                         Long.valueOf((String)getBindingDocumento().getValue()),
                                                         getBindingTipoOrigen().getValue() == null ? null :
                                                         (String)getBindingTipoOrigen().getValue(),
                                                         getBindingClasificacion().getValue() == null ? null :
                                                         ((Integer)getBindingClasificacion().getValue()).longValue(),
                                                         getBindingCampania().getValue() == null ? null :
                                                         (Long)getBindingCampania().getValue());
                while(!f.isDone()){                 
                }
                if(((String)f.get()).equals("0")){                    
                    Util.ejecutarJs("alertas.warning('No se encontraron mensajes para los filtros ingresados');");
                }else{
                    Util.setValueSessionScope("icoexcel", true);
                }
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error al cargar los mensajes');");
        }
        return null;
    }

    public String limpiar() {
        getBindingFechaDesde().resetValue();
        getBindingFechaHasta().resetValue();
        getBindingDocumento().resetValue();
        getBindingTipoOrigen().resetValue();
        getBindingClasificacion().resetValue();
        getBindingCampania().resetValue();
        getBindingTipoCampania().resetValue();
        getBindingTabla().setValue(null);
        return null;
    }

    @Override
    public void onCallBack(String msj) {
        try{ 
            limpiar();
            Util.setValueSessionScope("icoexcel", true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void listenToHello(@Observes EventObserver helloEvent){
        System.out.println("HelloEvent: " + helloEvent);
    }

    public void download(FacesContext facesContext, OutputStream outputStream) {
        try {
            File fichero = new File((String)Util.getValueSessionScope("path"));
            FileInputStream fis = new FileInputStream(fichero);
            byte[] bytes = new byte[32 * 1024];
            int read = 0;

            while ((read = fis.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            facesContext.responseComplete();
        } catch (Exception e) {
            Util.addMsgError(e.getMessage());
        }
    }

    public List<SelectItem> getItemTipoOrigen() {
        try {
            List<SelectItem> l = new ArrayList<SelectItem>();
            for (Parametros o : getExpertoMensajes().getParametros()) {
                l.add(new SelectItem(o.getParValor(), o.getParDescripcion()));
            }
            return l;
        } catch (Exception e) {
            Util.addMsgError(e.getMessage());
        }
        return null;
    }

    public List<SelectItem> getItemClasificacion() {
        try {
            List<SelectItem> l = new ArrayList<SelectItem>();
            for (Menp004m o : getExpertoMensajes().getMenp004m()) {
                l.add(new SelectItem(o.getMe04TipMen(), o.getMe04DesTip()));
            }
            return l;
        } catch (Exception e) {
            Util.addMsgError(e.getMessage());
        }
        return null;
    }

    public List<SelectItem> getItemCampania() {
        try {
            List<SelectItem> l = new ArrayList<SelectItem>();
            Long tipoCamp = Util.getValuePageFlowScope("tipo")==null?null:((TipoCampanias)Util.getValuePageFlowScope("tipo")).getTipocampId();            
            for (Campanias o : getExpertoCampanias().find(null, tipoCamp, null, null, null,true)) {
                l.add(new SelectItem(o.getCampId(), o.getCampDescripcion()));
            }
            return l;
        } catch (Exception e) {
            Util.addMsgError(e.getMessage());
        }
        return null;
    }
    
    public List<SelectItem> getItemTipo() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try{
            for (TipoCampanias o : getExpertoCampanias().getTipoCampanias()) {
                l.add(new SelectItem(o, o.getTipocampDescripcion()));
            }
        }catch(Exception e){
            Util.ejecutarJs("alertas.error('Error"+e.getMessage()+"');");
        }
        return l;
    }
    
    public void tipoCampaniaChangeListener(ValueChangeEvent v) {
        Util.setValuePageFlowScope("tipo",v.getNewValue());
    }        

    public void setExpertoMensajes(ExpertoMensajesLocal expertoMensajes) {
        this.expertoMensajes = expertoMensajes;
    }

    public ExpertoMensajesLocal getExpertoMensajes() {
        return expertoMensajes;
    }

    public void setBindingFechaDesde(RichInputDate bindingFechaDesde) {
        this.bindingFechaDesde = bindingFechaDesde;
    }

    public RichInputDate getBindingFechaDesde() {
        return bindingFechaDesde;
    }

    public void setBindingFechaHasta(RichInputDate bindingFechaHasta) {
        this.bindingFechaHasta = bindingFechaHasta;
    }

    public RichInputDate getBindingFechaHasta() {
        return bindingFechaHasta;
    }

    public void setBindingTipoOrigen(RichSelectOneChoice bindingTipoOrigen) {
        this.bindingTipoOrigen = bindingTipoOrigen;
    }

    public RichSelectOneChoice getBindingTipoOrigen() {
        return bindingTipoOrigen;
    }

    public void setBindingClasificacion(RichSelectOneChoice bindingClasificacion) {
        this.bindingClasificacion = bindingClasificacion;
    }

    public RichSelectOneChoice getBindingClasificacion() {
        return bindingClasificacion;
    }

    public void setBindingCampania(RichSelectOneChoice bindingCampania) {
        this.bindingCampania = bindingCampania;
    }

    public RichSelectOneChoice getBindingCampania() {
        return bindingCampania;
    }

    public void setBindingDocumento(RichInputText bindingDocumento) {
        this.bindingDocumento = bindingDocumento;
    }

    public RichInputText getBindingDocumento() {
        return bindingDocumento;
    }

    public void setBindingTabla(RichTable bindingTabla) {
        this.bindingTabla = bindingTabla;
    }

    public RichTable getBindingTabla() {
        return bindingTabla;
    }

    public void setExpertoCampanias(ExpertoCamapaniasLocal expertoCampanias) {
        this.expertoCampanias = expertoCampanias;
    }

    public ExpertoCamapaniasLocal getExpertoCampanias() {
        return expertoCampanias;
    }

    public void setBindingTipoCampania(RichSelectOneChoice bindingTipoCampania) {
        this.bindingTipoCampania = bindingTipoCampania;
    }

    public RichSelectOneChoice getBindingTipoCampania() {
        return bindingTipoCampania;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
