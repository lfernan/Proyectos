package com.tnevada.view.backing.mensajes;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.mensajes.ExpertoMensajesLocal;
import com.tnevada.view.managed.SessionBean;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;

import org.apache.myfaces.trinidad.event.PollEvent;


public class Panel {
    private RichSelectOneChoice campania;    
    private SessionBean session;

    public Panel(){
        try{
                        
        }catch(Exception e){
            Util.ejecutarJs("alertas.error('"+e.getMessage()+"');");
        }
    }
    
    public List<SelectItem> getItemCampania() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            List<Campanias> c =
                ((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).find(null,null,null,null,null,true);
            for (Campanias o : c) {
                l.add(new SelectItem(o.getCampId(), o.getCampDescripcion()));
            }
            if (c.size() == 1) {
                getCampania().setValue((c.get(0)).getCampId());
                Util.setValueSessionScope("campania", getCampania().getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
    
    public void campaniaChangeListener(ValueChangeEvent v) {
        try{
            Util.setValueSessionScope("campania", v.getNewValue());             
        }catch(Exception e){
            Util.ejecutarJs("alertas.error('"+e.getMessage()+"');");
        }
    }
    
    public String datos(){
        try{
            List<String> l = ((ExpertoMensajesLocal)CachingServiceLocator.getInstance().lookup("ExpertoMensajes")).getGestiones(null, (Long)Util.getValueSessionScope("campania"),true);
            if(l==null){
                Util.setValueSessionScope("pollrender", false);
                Util.ejecutarJs("removePlaceholder();");
                Util.ejecutarJs("alertas.warning('Campaña sin gestiones');");
                return null;
            }
            
            StringBuilder html = new StringBuilder();
            html.append("var o = [");
            int i = 0;
            for(String s:l){                                            
                html.append("'"+s+"'");
                i++;
                if(l.size()!=i){
                    html.append(",");
                }
            }
            html.append("]");  
            
            switch ((String)Util.getValueSessionScope("grafico")) {
            case "T" :
                Util.ejecutarJs(html+";pie(o);");
                 break;
            case "P" :
                Util.ejecutarJs(html+";panel(o);");
                 break;
            case "B" :
                Util.ejecutarJs(html+";panel(o);");
                 break;
            default:
                Util.ejecutarJs(html+";panel(o);");
            }
            Util.setValueSessionScope("pollrender", true);
        }catch(Exception e){            
            Util.ejecutarJs("alertas.error('"+e.getMessage()+"');");
        }
        return null;
    }
    
    public void poll(PollEvent pollEvent) {
        datos();
    }
    
    public void setCampania(RichSelectOneChoice campania) {
        this.campania = campania;
    }

    public RichSelectOneChoice getCampania() {
        return campania;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }    
}
