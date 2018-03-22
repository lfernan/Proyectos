package com.tnevada.view.backing.segmentacion;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasSegmentacion;
import com.tnevada.model.entidades.TipoCampanias;
import com.tnevada.model.predictivo.ExpertoPredictivoLocal;
import com.tnevada.model.segmentacion.ExpertoSegmentacionLocal;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.event.RowDisclosureEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;


public class ConsultarSegmentacion {
    private RichSelectOneChoice bindingTipoCampania;
    private RichSelectOneChoice bindingCampania;
    private RichInputDate bindingDesde;
    private RichInputDate bindingHasta;
    private RichTable bindingTabla;
    private RichInputDate bindingFechaCampania;

    public ConsultarSegmentacion(){
    }
    
    @PostConstruct
    public void init(){        
        try{
            if (!AdfFacesContext.getCurrentInstance().isPostback()) {
                Util.setValuePageFlowScope("disabled", true);   
                Util.setValuePageFlowScope("campania", (Campanias)Util.getValueSessionScope("campania"));  
                setBindingFechaCampania(new RichInputDate());
                if(Util.getValuePageFlowScope("campania")!=null){                
                    getBindingFechaCampania().setValue(((Campanias)Util.getValuePageFlowScope("campania")).getCampFechaAlta());
                    getBindingFechaCampania().setDisabled(Boolean.TRUE);
                }else{
                    Calendar c = Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.MONTH, -2);
                    getBindingFechaCampania().setValue(c.getTime());
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }    

    public String buscar() {
        try{
           getBindingTabla().setValue(((ExpertoSegmentacionLocal)CachingServiceLocator.getInstance().lookup("ExpertoSegmentacion")).getSegmentacion((Campanias)getBindingCampania().getValue(),
                                                                                                                                          (Date)getBindingDesde().getValue(),
                                                                                                                                          (Date)getBindingHasta().getValue()));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String limpiar() {
        if(Util.getValuePageFlowScope("campania")!=null){
            getBindingDesde().resetValue();
            getBindingHasta().resetValue();
            getBindingTabla().setValue(null);
        }
        return null;
    }
    
    public String detalle() {
        try{
            CampaniasSegmentacion cs = (CampaniasSegmentacion)Util.getValuePageFlowScope("seleccion");
            Util.setValuePageFlowScope("segmentonombre",cs.getNombre()+".xlsx");
            Util.setValuePageFlowScope("excelsegmento",Util.getPath() + cs.getNombre().trim() + ".xls");
            ((ExpertoSegmentacionLocal)CachingServiceLocator.getInstance().lookup("ExpertoSegmentacion")) .getSegmentacionBase(cs.getId(), (String)Util.getValuePageFlowScope("excelsegmento"),((Campanias)Util.getValueSessionScope("campania")).getTipoCampanias().getTipocampId());            
            Util.setValuePageFlowScope("contentType", "application/vnd.ms-excel");
            Util.ejecutarJs("document.getElementById('r1:0:descargar').click();");
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public String predictivo(){
        try{
            if(((Campanias)Util.getValuePageFlowScope("campania")).getTipoCampanias().getTipocampId()==5L){
                ((ExpertoPredictivoLocal)CachingServiceLocator.getInstance().lookup("ExpertoPredictivo")).generarPredictivo(Util.getPath(), (Long)Util.getValuePageFlowScope("segmento"), null);
                Util.setValuePageFlowScope("contentType", "text/html");
                Util.setValuePageFlowScope("segmentonombre","ivr.txt");
                Util.setValuePageFlowScope("excelsegmento", Util.getPath().getAbsolutePath()+File.separator+"ivr.txt");
                Util.ejecutarJs("document.getElementById('r1:0:descargar').click();");
            }else{
                return "predictivo";
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public void campaniaChangeListener(ValueChangeEvent valueChangeEvent) {        
    }

    public void tipoChangeListener(ValueChangeEvent valueChangeEvent) {
        Util.setValuePageFlowScope("disabled", false);
    }

    public void tablaSelectionListener(SelectionEvent selectionEvent) {
        Util.setValuePageFlowScope("segmento", ((CampaniasSegmentacion)getBindingTabla().getSelectedRowData()).getId());        
        Util.setValuePageFlowScope("seleccion", getBindingTabla().getSelectedRowData());
        if(((CampaniasSegmentacion)getBindingTabla().getSelectedRowData())!=null){
            List<String> datos = new ArrayList<String>();
            Map map = Util.jsonParse(((CampaniasSegmentacion)Util.getValuePageFlowScope("seleccion")).getFiltros());
            for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry)it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                datos.add(key + "&nbsp;<b>" + value + "</b>");                
            }
            Util.setValuePageFlowScope("filtros", datos);
            Util.clearSelection(getBindingTabla());
        }
    }    
    
    public List<SelectItem> getItemTipo() {
        try{      
            List<SelectItem> l = new ArrayList<SelectItem>();
            if(Util.getValuePageFlowScope("campania")!=null){
                TipoCampanias t = ((Campanias)Util.getValuePageFlowScope("campania")).getTipoCampanias();                
                l.add(new SelectItem(t,t.getTipocampDescripcion()));
                getBindingTipoCampania().setValue(t);
                getBindingTipoCampania().setDisabled(true);
            }else{                
                for(TipoCampanias t:((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).getTipoCampanias()){
                    l.add(new SelectItem(t,t.getTipocampDescripcion()));
                }
            }
            return l;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    public List<SelectItem> getItemCampania() {
        try {           
           List<SelectItem> l = new ArrayList<SelectItem>();
            if(Util.getValuePageFlowScope("campania")!=null){
                Campanias c = (Campanias)Util.getValuePageFlowScope("campania");
                l.add(new SelectItem(c,c.getCampDescripcion()));
                getBindingCampania().setValue(c);
                getBindingCampania().setDisabled(true);
                return l;
            }
            
            if(getBindingTipoCampania().getValue()!=null){
                for(Campanias c:((TipoCampanias)getBindingTipoCampania().getValue()).getCampanias()){
                    l.add(new SelectItem(c,c.getCampDescripcion()));
                }
            }           
            return l;
       } catch (Exception e) {            
            e.printStackTrace();
        }
        return null;
    }
    
    public void downloadAction(FacesContext facesContext, OutputStream outputStream) {
        try {
            File fichero = new File((String)Util.getValuePageFlowScope("excelsegmento"));
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
            Util.popMsgError(e.getMessage());
        }
    }

    public void setBindingTipoCampania(RichSelectOneChoice bindingTipoCampania) {
        this.bindingTipoCampania = bindingTipoCampania;
    }

    public RichSelectOneChoice getBindingTipoCampania() {
        return bindingTipoCampania;
    }

    public void setBindingCampania(RichSelectOneChoice bindingCampania) {
        this.bindingCampania = bindingCampania;
    }

    public RichSelectOneChoice getBindingCampania() {
        return bindingCampania;
    }

    public void setBindingDesde(RichInputDate bindingDesde) {
        this.bindingDesde = bindingDesde;
    }

    public RichInputDate getBindingDesde() {
        return bindingDesde;
    }

    public void setBindingHasta(RichInputDate bindingHasta) {
        this.bindingHasta = bindingHasta;
    }

    public RichInputDate getBindingHasta() {
        return bindingHasta;
    }

    public void setBindingTabla(RichTable bindingTabla) {
        this.bindingTabla = bindingTabla;
    }

    public RichTable getBindingTabla() {
        return bindingTabla;
    }

    public void setBindingFechaCampania(RichInputDate bindingFechaCampania) {
        this.bindingFechaCampania = bindingFechaCampania;
    }

    public RichInputDate getBindingFechaCampania() {
        return bindingFechaCampania;
    }

    public void disclosureListener(RowDisclosureEvent rd) {        
        if(((CampaniasSegmentacion)getBindingTabla().getSelectedRowData())!=null){
            List<String> datos = new ArrayList<String>();
            Map map = Util.jsonParse(((CampaniasSegmentacion)Util.getValuePageFlowScope("seleccion")).getFiltros());
            for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry)it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                datos.add(key + "&nbsp;<b>" + value + "</b>");                
            }
            Util.setValuePageFlowScope("filtros", datos);
            Util.clearSelection(getBindingTabla());
        }
    }
}
