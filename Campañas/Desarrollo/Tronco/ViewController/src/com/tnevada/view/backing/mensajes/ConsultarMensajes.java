package com.tnevada.view.backing.mensajes;

import com.tnevada.model.bc.OriginacionViewImpl;
import com.tnevada.model.bc.OriginacionViewRowImpl;
import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.ConsultaDTO;
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
import java.io.FileOutputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import javax.enterprise.event.Observes;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.output.RichMessage;
import oracle.adf.view.rich.render.ClientEvent;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Row;
import oracle.jbo.ViewObject;
import oracle.jbo.client.Configuration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ConsultarMensajes implements EventMensajes.CallBack {
    private ExpertoMensajesLocal expertoMensajes;
    private ExpertoCamapaniasLocal expertoCampanias;
    private RichInputDate bindingFechaDesde;
    private RichInputDate bindingFechaHasta;
    private RichSelectOneChoice bindingTipoOrigen;
    private RichSelectOneChoice bindingClasificacion;
    private RichSelectOneChoice bindingCampania;
    private RichSelectOneChoice bindingTipoCampania;
    private RichSelectOneChoice bindingProducto;
    private RichInputText bindingDocumento;
    private RichTable bindingTabla;
    private String path;
    private RichMessage bindingInfo;

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
            setBindingProducto(new RichSelectOneChoice());
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
                String url = "";
                File archivo = new File(Util.getPath().getAbsolutePath(), "Base.xlsx");

                url = archivo.toString();
                Util.setValuePageFlowScope("path", url);

                Future f =
                    getExpertoMensajes().getVistaMensajesDTO(this, (String)Util.getValuePageFlowScope("path"),
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
                                                             (Campanias)getBindingCampania().getValue(),
                                                             getBindingTipoCampania().getValue() == null ? null :
                                                             ((TipoCampanias)getBindingTipoCampania().getValue()).getTipocampId());
                while (!f.isDone()) {
                }
                if (((String)f.get()).equals("0")) {
                    Util.ejecutarJs("alertas.warning('No se encontraron mensajes para los filtros ingresados');");
                } else {
                    Util.setValueSessionScope("icoexcel", true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
            limpiar();
            Util.setValueSessionScope("icoexcel", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listenToHello(@Observes EventObserver helloEvent) {
        System.out.println("HelloEvent: " + helloEvent);
    }

    public void download(FacesContext facesContext, OutputStream outputStream) {
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
            Long tipoCamp =
                Util.getValuePageFlowScope("tipo") == null ? null :
                ((TipoCampanias)Util.getValuePageFlowScope("tipo")).getTipocampId();
            for (Campanias o : getExpertoCampanias().find(null, tipoCamp, null, null, null, true, null)) {
                l.add(new SelectItem(o, o.getCampDescripcion()));
            }
            return l;
        } catch (Exception e) {
            Util.addMsgError(e.getMessage());
        }
        return null;
    }

    public List<SelectItem> getItemTipo() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            for (TipoCampanias o : getExpertoCampanias().getTipoCampanias()) {
                l.add(new SelectItem(o, o.getTipocampDescripcion()));
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
        return l;
    }

    public List<SelectItem> getItemProductos() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            for (ConsultaDTO o :
                 ((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).getProductos(null)) {
                l.add(new SelectItem(o, o.getNombre()));
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
        return l;
    }

    public void tipoCampaniaChangeListener(ValueChangeEvent v) {
        Util.setValuePageFlowScope("tipo", v.getNewValue());
        filtrosChangeListener(null);
    }

    public void filtrosChangeListener(ValueChangeEvent v) {
        if (bindingFechaDesde != null) {
            if (getBindingFechaHasta().getValue() != null) {
                if (getBindingCampania().getValue() != null || getBindingClasificacion().getValue() != null ||
                    getBindingProducto().getValue() != null || getBindingTipoCampania().getValue() != null ||
                    getBindingDocumento().getValue() != null) {
                    Calendar c = Calendar.getInstance();
                    c.clear();
                    c.setTime((Date)getBindingFechaHasta().getValue());
                    c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
                    bindingFechaDesde.setValue(c.getTime());
                    Util.setValuePageFlowScope("info", "La fecha solo tomara un MES hacia atrás");
                } else {
                    Calendar c = Calendar.getInstance();
                    c.clear();
                    c.setTime((Date)getBindingFechaHasta().getValue());
                    c.set(Calendar.WEEK_OF_MONTH, c.get(Calendar.WEEK_OF_MONTH) - 1);
                    bindingFechaDesde.setValue(c.getTime());
                    Util.setValuePageFlowScope("info", "La fecha solo tomara una SEMANA hacia atrás");
                }
                Util.refreshUIComponent(bindingFechaDesde);
            }
        }
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

    public void handleEnterEvent(ClientEvent ce) {
        try {
            DCBindingContainer bindings = (DCBindingContainer)BindingContext.getCurrent().getCurrentBindingsEntry();
            DCIteratorBinding dcItteratorBindings = bindings.findIteratorBinding("CampaniasView1Iterator");
            ViewObject viewCampania = dcItteratorBindings.getViewObject();
            Row rowSelected = viewCampania.getCurrentRow();

            String amDef = "com.tnevada.model.bc.AppModule";
            String config = "AppModuleLocal";
            ApplicationModule am = Configuration.createRootApplicationModule(amDef, config);
            OriginacionViewImpl ov = (OriginacionViewImpl)am.findViewObject("OriginacionView1");
            ov.setcamp((Long)rowSelected.getAttribute("CampId"));
            ov.applyViewCriteria(ov.getViewCriteria("OriginacionViewCriteria"));
            ov.executeQuery();

            String url = "";
            File archivo = new File(Util.getPath().getAbsolutePath(), "Base.xlsx");

            url = archivo.toString();

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Base");
            short i = 0, c = 0;
            boolean cabecera = false;

            if (!ov.hasNext()) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(i);
                row.createCell(c++).setCellValue("SIN DATOS");
            }

            while (ov.hasNext()) {
                OriginacionViewRowImpl orw = (OriginacionViewRowImpl)ov.next();
                if (!cabecera) {
                    org.apache.poi.ss.usermodel.Row row = sheet.createRow(i);
                    row.createCell(c++).setCellValue("Documento");
                    row.createCell(c++).setCellValue("Apellido");
                    row.createCell(c++).setCellValue("Nombre");
                    row.createCell(c++).setCellValue("Tipo Documento");
                    row.createCell(c++).setCellValue("Sexo");
                    row.createCell(c++).setCellValue("Fecha Nacimiento");
                    row.createCell(c++).setCellValue("Cond. Laboral");
                    row.createCell(c++).setCellValue("Provincia");
                    row.createCell(c++).setCellValue("Sucursal");
                    row.createCell(c++).setCellValue("Telefono 1");
                    row.createCell(c++).setCellValue("Telefono 2");
                    row.createCell(c++).setCellValue("Telefono 3");
                    row.createCell(c++).setCellValue("Telefono 4");
                    cabecera = true;
                    c = 0;
                    i++;
                }
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(i);
                row.createCell(c++).setCellValue(orw.getDocumento());
                row.createCell(c++).setCellValue(orw.getApellido());
                row.createCell(c++).setCellValue(orw.getNombre());
                row.createCell(c++).setCellValue(orw.getTipoDocumento());
                row.createCell(c++).setCellValue(orw.getSexo());
                CellStyle cellStyle = wb.createCellStyle();
                CreationHelper createHelper = wb.getCreationHelper();
                cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));
                Cell cell = row.createCell(c++);
                cell.setCellValue(orw.getFechaNacimiento());
                cell.setCellStyle(cellStyle);
                //row.createCell(c++).setCellValue(orw.getFechaNacimiento());
                row.createCell(c++).setCellValue(orw.getCondLaboral());
                row.createCell(c++).setCellValue(orw.getProvincia());
                row.createCell(c++).setCellValue(orw.getSucursal());
                row.createCell(c++).setCellValue(orw.getTelefono1());
                row.createCell(c++).setCellValue(orw.getTelefono2() == null ? "" : orw.getTelefono2().toString());
                row.createCell(c++).setCellValue(orw.getTelefono3() == null ? "" : orw.getTelefono3().toString());
                row.createCell(c++).setCellValue(orw.getTelefono4() == null ? "" : orw.getTelefono1().toString());
                c = 0;
                i++;
            }

            FileOutputStream fileOut = new FileOutputStream(url);
            wb.write(fileOut);
            fileOut.close();

            Configuration.releaseRootApplicationModule(am, true);
            Util.setValuePageFlowScope("path", url);
        } catch (Exception spe) {
            spe.printStackTrace();
        }
    }

    public void setBindingProducto(RichSelectOneChoice bindingProducto) {
        this.bindingProducto = bindingProducto;
    }

    public RichSelectOneChoice getBindingProducto() {
        return bindingProducto;
    }

    public void setBindingInfo(RichMessage bindingInfo) {
        this.bindingInfo = bindingInfo;
    }

    public RichMessage getBindingInfo() {
        return bindingInfo;
    }
}
