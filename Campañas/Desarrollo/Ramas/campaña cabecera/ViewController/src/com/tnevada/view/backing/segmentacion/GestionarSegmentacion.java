package com.tnevada.view.backing.segmentacion;

import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.SucursalDTO;
import com.tnevada.model.segmentacion.ExpertoSegmentacionLocal;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.input.RichInputRangeSlider;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectManyListbox;
import oracle.adf.view.rich.component.rich.input.RichSelectOneRadio;
import oracle.adf.view.rich.event.DialogEvent;
import oracle.adf.view.rich.model.NumberRange;


public class GestionarSegmentacion {
    private RichInputText bindingCantidad;
    private RichSelectManyListbox bindingSucursales;
    private RichSelectOneRadio bindingPlanPago;
    private Campanias campania;
    private RichPopup bindingPopUp;
    private Boolean procesar;    
    private RichSelectOneRadio bindingResumen;
    private RichInputRangeSlider bindingRangoEdades;

    public GestionarSegmentacion(){
    }
    
    @PostConstruct
    public void init(){
        try {
            List<SucursalDTO> sucursales = ((ExpertoSegmentacionLocal)CachingServiceLocator.getInstance().lookup("ExpertoSegmentacion")).getSucursalesDTO();                        
            List<SelectItem> l = new ArrayList<SelectItem>();                        
            for (SucursalDTO s : sucursales) {                
                l.add(new SelectItem(s.getId(), s.getNombre()));
            }            
            Util.setValuePageFlowScope("sucursales", l);            
            Util.setValuePageFlowScope("campania", (Campanias)Util.getValueSessionScope("campania"));            
            if(Util.getValuePageFlowScope("M") == null){
                getBase(null,null,true,null,null);
            }            
            procesar = Boolean.FALSE;
            Util.setValuePageFlowScope("icoexcel", false);
            setBindingRangoEdades(new RichInputRangeSlider());
            setBindingResumen(new RichSelectOneRadio());
            setBindingPlanPago(new RichSelectOneRadio());
            
            if(((Campanias)Util.getValuePageFlowScope("campania")).getTipoCampanias().getTipocampId()==2){                
                getBindingPlanPago().setRendered(false);
                Number nd = 0;
                Number nh = 100;               
                getBindingRangoEdades().setValue(new NumberRange(nd,nh));
            }else{
                getBindingRangoEdades().setValue(null);                
                getBindingResumen().setValue(null);   
                getBindingRangoEdades().setRendered(false);                
                getBindingResumen().setRendered(false);                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void getBase(List sucursales,String pp,boolean todo,String edad,String resumen){
        try{
            Map map = null;            
            map = Util.jsonParse(((ExpertoSegmentacionLocal)CachingServiceLocator.getInstance().lookup("ExpertoSegmentacion")).getDatos(sucursales, pp,(Campanias)Util.getValuePageFlowScope("campania"),resumen,edad));            
            initMap(map,todo);            
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String generar() {
        try {            
            int todo = Integer.valueOf((String)Util.getValuePageFlowScope("FILTRADO")).compareTo(Integer.valueOf(Integer.valueOf((String)Util.getValuePageFlowScope("TOTAL"))));
            if(!procesar && getBindingCantidad().getValue() == null && todo == 0){                        
                Util.setValuePageFlowScope("mensaje", "�Desea segmentar por el total de los clientes NO TRABAJADOS?");                
                getBindingPopUp().show(new RichPopup.PopupHints());
            }else{                                
                Util.setValuePageFlowScope("path",Util.getRealPath(File.separator +"reportes" + File.separator+"Segmento.xls"));  
                ((ExpertoSegmentacionLocal)CachingServiceLocator.getInstance().lookup("ExpertoSegmentacion")).segmentar(getBindingCantidad().getValue()==null?Integer.valueOf((String)Util.getValuePageFlowScope("NTP")):Integer.valueOf((String)getBindingCantidad().getValue()),
                                                                                                             (String)Util.evaluarEL("#{SessionBean.usuario}"),
                                                                                                             (List)getBindingSucursales().getValue(), 
                                                                                                             (String)getBindingPlanPago().getValue(), 
                                                                                                             (Campanias)Util.getValuePageFlowScope("campania"),
                                                                                                             (String)Util.getValuePageFlowScope("path"),
                                                                                                             getEdades(),
                                                                                                             (String)getBindingResumen().getValue());
                Util.setValuePageFlowScope("icoexcel", true);
                getBase(null,null,true,null,null);    
            }
        } catch (Exception e) {  
            e.printStackTrace();
        }
        return null;
    }

    public String limpiar() {
        getBindingSucursales().resetValue();
        getBindingCantidad().resetValue();
        getBindingResumen().resetValue();
        getBindingResumen().setValue("A"); 
        Number nd = 0;
        Number nh = 100;               
        getBindingRangoEdades().resetValue();
        getBindingRangoEdades().setValue(new NumberRange(nd,nh));
        getBindingPlanPago().setValue("A");        
        Util.setValuePageFlowScope("icoexcel", false);
        Util.ejecutarJs("document.getElementById('r1:0:sor1:_2').click();");
        getBase(null,null,true,null,null);
        return null;
    }
    
    public String reciclar() {
        try{
            ((ExpertoSegmentacionLocal)CachingServiceLocator.getInstance().lookup("ExpertoSegmentacion")).reciclarCampania((Campanias)Util.getValuePageFlowScope("campania"));
            getBase(null,null,true,null,null);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
    private void initMap(Map m, boolean todo){        
        Util.setValuePageFlowScope("M", m);
        Util.setValuePageFlowScope("NTP",m.get("NT"));
        Util.setValuePageFlowScope("NTF",m.get("NT"));
        Util.setValuePageFlowScope("FILTRADO",m.get("NT"));
        Util.setValuePageFlowScope("TP",m.get("T"));
        Util.setValuePageFlowScope("A",m.get("A"));
        if(!Util.getContainsKeyPageFlowScope("NT") || Util.getValuePageFlowScope("NT")==null || todo){
            Util.setValuePageFlowScope("NT","<span style=\"color:red\">"+m.get("NT")+"</span>");
            Util.setValuePageFlowScope("TOTAL",m.get("NT"));
        }
        Util.setValuePageFlowScope("T","<span style=\"color:Teal\">"+m.get("T")+"</span>");
    }
    
    public void filtroChangeListener(ValueChangeEvent v) {                                            
        getBase((List)getBindingSucursales().getValue(),
                (String)getBindingPlanPago().getValue(),
                false,
                getEdades(),
                (String)getBindingResumen().getValue());        
    }  
    
    public void dialogListener(DialogEvent de) {
        if(de.getOutcome().equals(DialogEvent.Outcome.ok)){
            procesar = Boolean.TRUE;
            generar();
        }
    }
    
    private String getEdades(){
        NumberRange nr = (NumberRange)getBindingRangoEdades().getValue();
        String edades = null;
        
        if(nr != null){
            Number nd = nr.getMinimum();
            Number nh = nr.getMaximum();
            edades = nd.toString()+"-"+nh.toString();
        }else{
            edades = "18-100";
        } 
        return edades;
    }
    
    public void downloadAction(FacesContext facesContext, OutputStream outputStream) {
        try {
            File fichero = new File((String)Util.getValuePageFlowScope("path"));
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

    public void setBindingCantidad(RichInputText bindingCantidad) {
        this.bindingCantidad = bindingCantidad;
    }

    public RichInputText getBindingCantidad() {
        return bindingCantidad;
    }

    public void setBindingSucursales(RichSelectManyListbox bindingSucursales) {
        this.bindingSucursales = bindingSucursales;
    }

    public RichSelectManyListbox getBindingSucursales() {
        return bindingSucursales;
    }

    public void setBindingPlanPago(RichSelectOneRadio bindingPlanPago) {
        this.bindingPlanPago = bindingPlanPago;
    }

    public RichSelectOneRadio getBindingPlanPago() {
        return bindingPlanPago;
    }

    public void setCampania(Campanias campania) {
        this.campania = campania;
    }

    public Campanias getCampania() {
        return campania;
    }    

    public void setBindingPopUp(RichPopup bindingPopUp) {
        this.bindingPopUp = bindingPopUp;
    }

    public RichPopup getBindingPopUp() {
        return bindingPopUp;
    }

    public void setBindingResumen(RichSelectOneRadio bindingResumen) {
        this.bindingResumen = bindingResumen;
    }

    public RichSelectOneRadio getBindingResumen() {
        return bindingResumen;
    }

    public void setBindingRangoEdades(RichInputRangeSlider bindingRangoEdades) {
        this.bindingRangoEdades = bindingRangoEdades;
    }

    public RichInputRangeSlider getBindingRangoEdades() {
        return bindingRangoEdades;
    }
}
