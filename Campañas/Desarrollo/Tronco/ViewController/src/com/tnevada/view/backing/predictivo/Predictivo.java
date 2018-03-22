package com.tnevada.view.backing.predictivo;

import com.tnevada.model.entidades.TipoDeTelefono;
import com.tnevada.model.predictivo.ExpertoPredictivoLocal;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.input.RichSelectOrderShuttle;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;

import tarjetascuyanas.fw.files.ServerPathLocator;

public class Predictivo {
    private RichSelectOrderShuttle telefonos;
    private RichCommandLink download;

    public Predictivo(){}
    
    @PostConstruct
    public void init(){
        try{
            List<SelectItem> telefonos = new ArrayList<SelectItem>();
            for(TipoDeTelefono t:((ExpertoPredictivoLocal)CachingServiceLocator.getInstance().lookup("ExpertoPredictivo")).getTipoDeTelefonos()){
                SelectItem selectItem = new SelectItem();
                selectItem.setValue(t);
                selectItem.setLabel(t.getDescripcion().substring(0,1) + 
                                    t.getDescripcion().substring(1,t.getDescripcion().length()).toLowerCase());
                telefonos.add(selectItem);                
            }
            Util.setValuePageFlowScope("telefonos", telefonos);
        }catch(Exception e){
            e.printStackTrace();
        }
    }    
    
    public String generar() {
        try {            
            ((ExpertoPredictivoLocal)CachingServiceLocator.getInstance().lookup("ExpertoPredictivo"))
            .generarPredictivo(ServerPathLocator.getInstance().getApplicationPath(13, true),
                               (Long)Util.getValuePageFlowScope("segmento"),
                               (List)getTelefonos().getValue());
            Util.ejecutarJs("var link = AdfPage.PAGE.findComponent(\"" +
                       getDownload().getClientId(FacesContext.getCurrentInstance()) +
                        "\"); " + "AdfActionEvent.queue(link, true);");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public void downloadAction(FacesContext facesContext, OutputStream outputStream) {
        try {            
            File path = ServerPathLocator.getInstance().getApplicationPath(13, true);
            File fichero = new File(path,"ivr.txt");
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
    
    public void setTelefonos(RichSelectOrderShuttle telefonos) {
        this.telefonos = telefonos;
    }

    public RichSelectOrderShuttle getTelefonos() {
        return telefonos;
    }

    public void setDownload(RichCommandLink download) {
        this.download = download;
    }

    public RichCommandLink getDownload() {
        return download;
    }
}
