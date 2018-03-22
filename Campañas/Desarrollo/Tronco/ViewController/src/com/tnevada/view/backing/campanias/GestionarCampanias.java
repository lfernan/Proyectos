package com.tnevada.view.backing.campanias;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.BaseFiltradaDTO;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasBase;
import com.tnevada.model.entidades.CampaniasNoSocios;
import com.tnevada.model.entidades.ConsultaDTO;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.TipoCampanias;
import com.tnevada.view.managed.SessionBean;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.ReportesBean;
import com.tnevada.view.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichPanelWindow;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.component.rich.input.RichInputDate;
import oracle.adf.view.rich.component.rich.input.RichInputFile;
import oracle.adf.view.rich.component.rich.input.RichInputText;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelFormLayout;
import oracle.adf.view.rich.component.rich.nav.RichButton;
import oracle.adf.view.rich.component.rich.nav.RichCommandLink;
import oracle.adf.view.rich.component.rich.output.RichOutputText;

import org.apache.myfaces.trinidad.event.ReturnEvent;
import org.apache.myfaces.trinidad.event.SelectionEvent;
import org.apache.myfaces.trinidad.model.UploadedFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class GestionarCampanias {
    private SessionBean session;
    private ExpertoCamapaniasLocal expertoCampanias;
    private RichTable bindingTabla;
    private RichInputText bindingDescripcion;
    private RichSelectOneChoice bindingTipo;
    private RichSelectOneChoice bindingRenglon;
    private RichSelectOneChoice bindingProducto;
    private RichInputDate bindingFechaDesde;
    private RichInputDate bindingFechaHasta;
    private RichPopup bindingPopUp;
    private RichOutputText bindingTextoDialog;
    private RichButton bindingModificar;
    private RichButton bindingEliminar;
    private RichPanelFormLayout bindingPanelForm;
    private Campanias campania;
    private RichInputFile bindingArchivos;
    private UploadedFile archivo;
    private RichButton bindingSegmentar;
    private RichCommandLink bindingLinkActualizar;
    private RichPopup bindingPopUpFiltro;
    private RichPanelWindow bindingPWPopUp;
    private RichPanelWindow bindingPWPopUpFiltro;
    private RichTable bindingTablaFiltro;
    //private RichMessage bindingMensajePopUp;

    public GestionarCampanias() {
        try {
            setExpertoCampanias((ExpertoCamapaniasLocal)CachingServiceLocator.getInstance().lookup("ExpertoCamapanias"));
            setBindingTabla(new RichTable());
            setBindingDescripcion(new RichInputText());
            setBindingTipo(new RichSelectOneChoice());
            setBindingRenglon(new RichSelectOneChoice());
            setBindingProducto(new RichSelectOneChoice());
            setBindingFechaDesde(new RichInputDate());
            setBindingFechaHasta(new RichInputDate());
            setBindingPopUp(new RichPopup());
            setBindingTextoDialog(new RichOutputText());
            setBindingModificar(new RichButton());
            setBindingEliminar(new RichButton());
            setBindingPanelForm(new RichPanelFormLayout());
            setBindingArchivos(new RichInputFile());
            setBindingSegmentar(new RichButton());
            setBindingPopUpFiltro(new RichPopup());
            actualizarBotones(Boolean.TRUE);
            getBindingTextoDialog().setRendered(false);
            getBindingProducto().setDisabled(true);
            getBindingRenglon().setDisabled(true);
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error al iniciar la aplicación');");
        }
    }

    public String buscar() {
        try {
            actualizarDatos();
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }

        return null;
    }

    public String limpiar() {
        Util.setValueSessionScope("descripcion", null);
        Util.setValueSessionScope("tipo", null);
        Util.setValueSessionScope("fdesde", null);
        Util.setValueSessionScope("fhasta", null);
        actualizarBotones(Boolean.TRUE);
        getBindingTabla().setValue(null);
        Util.clearSelection(getBindingTabla());
        Util.refreshUIComponent(getBindingTabla());
        return null;
    }

    public String modificar() {
        //getBindingMensajePopUp().setVisible(false);
        getBindingPWPopUp().setTitle("Modificar Campaña");
        getBindingPopUp().show(new RichPopup.PopupHints());
        getBindingTextoDialog().setRendered(false);
        getBindingArchivos().setRendered(false);
        getBindingRenglon().setRendered(false);
        getBindingProducto().setRendered(false);
        getBindingTipo().setRendered(false);
        getBindingPanelForm().setRendered(Boolean.TRUE);
        setCampania((Campanias)Util.getValueSessionScope("campania"));
        getBindingDescripcion().setValue(getCampania().getCampDescripcion());
        getBindingDescripcion().setDisabled(true);
        //getBindingTipo().setValue(getCampania().getTipoCampanias());
        getBindingFechaDesde().setValue(getCampania().getCampFechaVigenciaDesde());
        getBindingFechaHasta().setValue(getCampania().getCampFechaVigenciaHasta());
        Util.setValuePageFlowScope("accion", 2);
        Util.setValuePageFlowScope("campania", getCampania());
        return null;
    }

    public String agregar() {
        //getBindingMensajePopUp().setVisible(false);
        getBindingPWPopUp().setTitle("Agregar Campaña");
        getBindingPopUp().show(new RichPopup.PopupHints());
        getBindingTextoDialog().setRendered(false);
        getBindingArchivos().setRendered(true);
        getBindingRenglon().setRendered(true);
        getBindingTipo().setRendered(true);
        getBindingProducto().setRendered(true);
        getBindingPanelForm().setRendered(Boolean.TRUE);
        getBindingDescripcion().setDisabled(false);
        getBindingDescripcion().resetValue();
        getBindingTipo().resetValue();
        getBindingFechaDesde().resetValue();
        getBindingFechaHasta().resetValue();
        Util.setValuePageFlowScope("disabled", true);
        Util.setValuePageFlowScope("accion", 1);
        Util.setValuePageFlowScope("campania", new Campanias());
        return null;
    }

    public String eliminar() {
        //getBindingMensajePopUp().setVisible(false);
        getBindingPWPopUp().setTitle("Eliminar Campaña");
        getBindingPopUp().show(new RichPopup.PopupHints());
        getBindingTextoDialog().setRendered(true);
        getBindingPanelForm().setRendered(Boolean.FALSE);
        setCampania((Campanias)Util.getValueSessionScope("campania"));
        Util.setValuePageFlowScope("accion", 3);
        Util.setValuePageFlowScope("campania", getCampania());
        return null;
    }

    public String abm() {
        try {
            setCampania((Campanias)Util.getValuePageFlowScope("campania"));
            if (Util.getValuePageFlowScope("accion") == 1) {
                getCampania().setCampDescripcion((String)getBindingDescripcion().getValue());
                getCampania().setCampFechaVigenciaDesde((Date)getBindingFechaDesde().getValue());
                getCampania().setCampFechaVigenciaHasta((Date)getBindingFechaHasta().getValue());
                getCampania().setCampFechaAlta(new Date());
                getCampania().setTipoCampanias((TipoCampanias)getBindingTipo().getValue());
                getCampania().setCampLegajoUsuario(getExpertoCampanias().getLegajoPorNombreDeUsuario((String)Util.evaluarEL("#{SessionBean.usuario}")).intValue());
                getCampania().setCampEntidadDestino(1L); //FIXME
                if (getBindingProducto().getValue() != null) {
                    getCampania().setCampCartera(((ConsultaDTO)getBindingProducto().getValue()).getId());
                } else if (getBindingRenglon().getValue() != null) {
                    getCampania().setCampCartera(getExpertoCampanias().getFechaCartera(Long.valueOf((String)getBindingRenglon().getValue())));
                }
                procesar(getCampania());
            } else if (Util.getValuePageFlowScope("accion") == 2) {
                getCampania().setCampDescripcion((String)getBindingDescripcion().getValue());
                getCampania().setCampFechaVigenciaDesde((Date)getBindingFechaDesde().getValue());
                getCampania().setCampFechaVigenciaHasta((Date)getBindingFechaHasta().getValue());
                getCampania().setTipoCampanias((TipoCampanias)getBindingTipo().getValue() == null ?
                                               getCampania().getTipoCampanias() :
                                               (TipoCampanias)getBindingTipo().getValue());
                getExpertoCampanias().edit(getCampania());
                actualizarDatos();
            } else if (Util.getValuePageFlowScope("accion") == 3) {
                getExpertoCampanias().remove(getCampania());
                getExpertoCampanias().eliminarCampaniaDependencias(getCampania());
                actualizarDatos();
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error - " + e.getMessage() + "');");
        }
        return null;
    }

    public String preFiltro() {
        try {
            getExpertoCampanias().cargarBaseClientes(null, (Campanias)Util.getValuePageFlowScope("campania"),
                                                     (List<BaseFiltradaDTO>)Util.getValuePageFlowScope("prefiltro"));
            cancelar();
            actualizarDatos();
        } catch (Exception e) {
            e.printStackTrace();
            Util.ejecutarJs("alertas.error('Error - " + e.getMessage() + "');");
        }
        return null;
    }

    public String cancelar() {
        try {
            if (getBindingPopUpFiltro().isRendered()) {
                getBindingPopUpFiltro().cancel();
                Util.refreshUIComponent(getBindingPopUpFiltro());
            }
            if (getBindingPopUp().isRendered()) {
                getBindingPopUp().cancel();
                Util.refreshUIComponent(getBindingPopUp());
            }
            getBindingRenglon().resetValue();
            getBindingProducto().resetValue();
            getBindingRenglon().setDisabled(true);
            getBindingProducto().setDisabled(true);
            getBindingRenglon().setRequired(false);
            getBindingProducto().setRequired(false);
            getBindingArchivos().resetValue();
            getBindingDescripcion().resetValue();
            getBindingFechaDesde().resetValue();
            getBindingFechaHasta().resetValue();
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error - " + e.getMessage() + "');");
        }
        return null;
    }

    private void actualizarDatos() {
        try {
            getBindingTabla().setValue(getExpertoCampanias().find(Util.getValueSessionScope("descripcion") == null ?
                                                                  null :
                                                                  (String)Util.getValueSessionScope("descripcion"),
                                                                  Util.getValueSessionScope("tipo") == null ? null :
                                                                  ((TipoCampanias)Util.getValueSessionScope("tipo")).getTipocampId(),
                                                                  Util.getValueSessionScope("fdesde") == null ? null :
                                                                  (Date)Util.getValueSessionScope("fdesde"),
                                                                  (Date)Util.getValueSessionScope("fhasta") == null ?
                                                                  null : (Date)Util.getValueSessionScope("fhasta"),
                                                                  null, true, null));
            Util.clearSelection(getBindingTabla());
            Util.refreshUIComponent(getBindingTabla());
            cancelar();
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
    }

    private void actualizarBotones(Boolean v) {
        getBindingEliminar().setDisabled(v);
        getBindingModificar().setDisabled(v);
        Util.refreshUIComponent(getBindingEliminar());
        Util.refreshUIComponent(getBindingModificar());
        if (getBindingSegmentar() != null && getBindingSegmentar().isRendered()) {
            getBindingSegmentar().setDisabled(v);
            Util.refreshUIComponent(getBindingSegmentar());
        }
    }

    public void tableSelectionListener(SelectionEvent selectionEvent) {
        actualizarBotones(Boolean.FALSE);
        Util.setValueSessionScope("campania", getBindingTabla().getSelectedRowData());
    }

    public void tablaFiltroSelectionListener(SelectionEvent selectionEvent) {
        Util.setValuePageFlowScope("archivo",
                                   ((BaseFiltradaDTO)getBindingTablaFiltro().getSelectedRowData()).getArchivo());
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

    public List<SelectItem> getItemRenglones() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            for (Parametros o : getExpertoCampanias().getRenglones()) {
                l.add(new SelectItem(o.getParValor(), o.getParDescripcion()));
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
        return l;
    }

    public List<SelectItem> getItemProductos() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            if (getBindingTipo().getValue() != null) {
                for (ConsultaDTO o :
                     getExpertoCampanias().getProductos(((TipoCampanias)getBindingTipo().getValue()).getTipocampId())) {
                    l.add(new SelectItem(o, o.getNombre()));
                }
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
        return l;
    }

    public String detalle() {
        try {
            Util.setValuePageFlowScope("archivo",
                                       ReportesBean.generarReporteBaseClientes((Campanias)Util.getValueSessionScope("campania")));
            Util.ejecutarJs("customHandler(event,'pt1:r1:0:HiddenBtn');");
            Util.clearSelection(getBindingTabla());
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
        return null;
    }

    private Long procesar(Campanias campania) {
        try {
            HashSet<Long> documentos = new HashSet<Long>();
            if (!Util.chkEXLFile(getArchivo().getFilename())) {
                Util.addMsgError("El formato debe ser formato xls o xlsx.");
                return null;
            }

            Workbook wb = WorkbookFactory.create(getArchivo().getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            HashSet<CampaniasBase> lns = new HashSet<CampaniasBase>();
            for (Row row : sheet) {
                CampaniasBase cb = new CampaniasBase();
                cb.setCampania(campania);
                CampaniasNoSocios cns = new CampaniasNoSocios();
                for (Cell cell : row) {
                    if (cell.getColumnIndex() == 0) {
                        switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            String v = cell.getRichStringCellValue().getString();
                            Long doc = Util.getNumero(v);
                            if (doc != 0L) {
                                if (((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 5L) {
                                    if (doc.toString().length() <= 13) {
                                        cns.setCansNroDocumento(doc);
                                        cb.setBaseIdentificacion(doc.toString());
                                    }
                                } else {
                                    documentos.add(doc.longValue());
                                }
                            }
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            if (((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 5L) {
                                if (((Double)(cell.getNumericCellValue())).toString().length() <= 13) {
                                    cns.setCansNroDocumento(((Double)(cell.getNumericCellValue())).longValue());
                                    cb.setBaseIdentificacion(String.valueOf(((Double)cell.getNumericCellValue()).intValue()));
                                }
                            } else {
                                documentos.add(((Double)(cell.getNumericCellValue())).longValue());
                            }
                            break;
                        default:
                            System.out.println("");
                        }
                    }
                    if (((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 5L) {
                        switch (cell.getColumnIndex()) {
                        case 1:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                String nombre = Util.getTexto(cell.getRichStringCellValue().getString());
                                if(!nombre.equals("")){
                                    cns.setCansNombres(nombre);
                                }
                            }
                            break;
                        case 2:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                String apellido = Util.getTexto(cell.getRichStringCellValue().getString());
                                if(!apellido.equals("")){
                                    cns.setCansApellidos(apellido);
                                }
                            }
                            break;
                        case 3:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                Long tel = Util.getNumero(cell.getRichStringCellValue().getString());
                                if (tel.toString().length() <= 15) {
                                    if (tel != 0L) {
                                        cns.setCansNroTel1(tel);
                                    }
                                }
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (((Double)(cell.getNumericCellValue())) != null &&
                                    ((Double)(cell.getNumericCellValue())).compareTo(0D) != 0) {
                                    if (((Double)(cell.getNumericCellValue())).toString().length() <= 15) {
                                        cns.setCansNroTel1(((Double)(cell.getNumericCellValue())).longValue());
                                    }
                                }
                            }
                            break;
                        case 4:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                Long tel = Util.getNumero(cell.getRichStringCellValue().getString());
                                if (tel.toString().length() <= 15) {
                                    if (tel != 0L) {
                                        cns.setCansNroTel2(tel);
                                    }
                                }
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (((Double)(cell.getNumericCellValue())) != null &&
                                    ((Double)(cell.getNumericCellValue())).compareTo(0D) != 0) {
                                    if (((Double)(cell.getNumericCellValue())).toString().length() <= 15) {
                                        cns.setCansNroTel2(((Double)(cell.getNumericCellValue())).longValue());
                                    }
                                }
                            }
                            break;
                        case 5:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                Long tel = Util.getNumero(cell.getRichStringCellValue().getString());
                                if (tel.toString().length() <= 15) {
                                    if (tel != 0L) {
                                        cns.setCansNroTel3(tel);
                                    }
                                }
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (((Double)(cell.getNumericCellValue())) != null &&
                                    ((Double)(cell.getNumericCellValue())).compareTo(0D) != 0) {
                                    if (((Double)(cell.getNumericCellValue())).toString().length() <= 15) {
                                        cns.setCansNroTel3(((Double)(cell.getNumericCellValue())).longValue());
                                    }
                                }
                            }
                            break;
                        case 6:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                Long tel = Util.getNumero(cell.getRichStringCellValue().getString());
                                if (tel.toString().length() <= 15) {
                                    if (tel != 0L) {
                                        cns.setCansNroTel4(tel);
                                    }
                                }
                            } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                                if (((Double)(cell.getNumericCellValue())) != null &&
                                    ((Double)(cell.getNumericCellValue())).compareTo(0D) != 0) {
                                    if (((Double)(cell.getNumericCellValue())).toString().length() <= 15) {
                                        cns.setCansNroTel4(((Double)(cell.getNumericCellValue())).longValue());
                                    }
                                }
                            }
                            break;
                        case 7:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                cns.setCansProvincia(cell.getRichStringCellValue().getString());
                            }
                            break;
                        case 8:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                                if (cell.getRichStringCellValue().getString().length() > 1) {
                                    String ss = cell.getRichStringCellValue().getString().substring(0, 1);
                                    if (ss.equalsIgnoreCase("F") || ss.equalsIgnoreCase("M")) {
                                        cns.setCansSexo(ss);
                                    }
                                } else {
                                    if (cell.getRichStringCellValue().getString().equalsIgnoreCase("F") ||
                                        cell.getRichStringCellValue().getString().equalsIgnoreCase("M")) {
                                        cns.setCansSexo(cell.getRichStringCellValue().getString());
                                    }
                                }
                            }
                            break;
                        case 9:
                            if (cell.getCellType() == Cell.CELL_TYPE_STRING || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                                cns.setCansDomicilio(cell.getRichStringCellValue().getString());
                            }
                            break;
                        }
                    }
                }
                if (((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 5L &&
                    !(cns.getCansNroTel1() == null && cns.getCansNroTel2() == null && cns.getCansNroTel3() == null &&
                      cns.getCansNroTel4() == null) && cns.getCansNombres() != null && cns.getCansApellidos() != null &&
                    !getExpertoCampanias().esSoscios(cns.getCansNroDocumento())) {
                    cb.setCampaniasNoSocios(cns);
                    cns.setCampaniasBase(cb);
                    lns.add(cb);
                }
            }

            if (getBindingProducto().getValue() != null) {
                List<BaseFiltradaDTO> pf =
                    getExpertoCampanias().filtrarBaseClientes(documentos, campania,
                                                              (ConsultaDTO)getBindingProducto().getValue(),
                                                              Util.getPath().getAbsolutePath());
                if (pf != null && !pf.isEmpty()) {
                    Util.setValuePageFlowScope("prefiltro", pf);
                    getBindingTablaFiltro().setValue(pf);
                    Util.setValuePageFlowScope("campania", campania);
                    getBindingPWPopUpFiltro().setTitle("Clientes Filtrados");
                    getBindingPopUpFiltro().show(new RichPopup.PopupHints());
                    getBindingProducto().setRequired(false);
                    return null;
                }
            }
            if (((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 5L) {
                getExpertoCampanias().cargarBaseClientes(lns);
            } else {
                getExpertoCampanias().cargarBaseClientes(documentos, campania, null);
            }
            actualizarDatos();
        } catch (java.lang.ArrayIndexOutOfBoundsException e) {
            Util.popMsgInfo("El archivo no debe contener espacios en blanco entre cada legajo.");
        } catch (Exception e) {
            Util.popMsgError(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void downloadAction(FacesContext facesContext, OutputStream outputStream) {
        try {
            File fichero = new File((String)Util.getValuePageFlowScope("archivo"));
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

    public String segmentar() {
        if(((Campanias)Util.getValueSessionScope("campania")).getTipoCampanias().getTipocampId()==5L){
            Util.setValuePageFlowScope("height", "200");
            Util.setValuePageFlowScope("width", "700");
        }else{
            Util.setValuePageFlowScope("height", "460");
            Util.setValuePageFlowScope("width", "700");
        }
        if (Util.getValueSessionScope("campania") != null &&
            Util.fechaToLong(((Campanias)Util.getValueSessionScope("campania")).getCampFechaVigenciaHasta()) <
            Util.fechaToLong(new Date())) {
            Util.ejecutarJs("alertas.warning('La campaña se encuentra fuera de vigencia');");
            return null;
        } else {
            return "segmentar";
        }
    }

    public void returnSegmentar(ReturnEvent returnEvent) {
        actualizarBotones(Boolean.TRUE);
        actualizarDatos();
    }

    public void tipoChangeListener(ValueChangeEvent valueChangeEvent) {
        if (getBindingTipo() != null && getBindingTipo().getValue() != null &&
            ((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 1) {
            getBindingRenglon().setDisabled(false);
            getBindingRenglon().setRequired(true);
            getBindingRenglon().setRequiredMessageDetail("Debe seleccionar un renglón");
            getBindingProducto().resetValue();
            getBindingProducto().setDisabled(true);
            getBindingProducto().setRequired(false);
        } else if (getBindingTipo() != null && getBindingTipo().getValue() != null &&
                   ((TipoCampanias)getBindingTipo().getValue()).getTipocampId() != 1) {
            getBindingProducto().setDisabled(false);
            getBindingProducto().setRequired(true);
            getBindingProducto().setRequiredMessageDetail("Debe seleccionar un producto");
            getBindingRenglon().resetValue();
            getBindingRenglon().setDisabled(true);
            getBindingRenglon().setRequired(false);
        } else {
            getBindingProducto().resetValue();
            getBindingProducto().setDisabled(true);
            getBindingProducto().setRequired(false);
            getBindingRenglon().resetValue();
            getBindingRenglon().setDisabled(true);
            getBindingRenglon().setRequired(false);
        }
    }

    private boolean validarFechas() {
        try {
            if (getBindingFechaDesde().getValue() != null && getBindingFechaHasta().getValue() != null) {
                Date desde = (Date)getBindingFechaDesde().getValue();
                Date hasta = (Date)getBindingFechaHasta().getValue();
                /*getBindingMensajePopUp().setMessage("Mensajes Binding");
                getBindingMensajePopUp().setVisible(true);
                Util.refreshUIComponent(getBindingMensajePopUp());*/
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public Date getMinFechaHasta() {
        Calendar c = Calendar.getInstance();
        if (getBindingFechaDesde().getValue() != null) {
            c.setTime((Date)getBindingFechaDesde().getValue());
        } else {
            c.setTime(new Date());
        }
        return c.getTime();
    }

    public Date getMaxFechaHasta() {
        Calendar c = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        if (getBindingTipo().getValue() != null &&
            (((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 2 ||
             ((TipoCampanias)getBindingTipo().getValue()).getTipocampId() == 3)) {
            if (getBindingFechaDesde().getValue() != null) {
                c2.clear();
                c.setTime((Date)getBindingFechaDesde().getValue());
                c2.set(Calendar.YEAR, c.get(Calendar.YEAR));
                if (c.get(Calendar.DATE) >= 25 && c.get(Calendar.DATE) <= 31) {
                    c2.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
                } else {
                    c2.set(Calendar.MONTH, c.get(Calendar.MONTH));
                }
                c2.set(Calendar.DATE, 25);
            }
        } else {
            c2.clear();
            c.setTime(new Date());
            c2.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
            c2.set(Calendar.MONTH, c.get(Calendar.MONTH));
            c2.set(Calendar.DATE, c.get(Calendar.DATE));
        }
        return c2.getTime();
    }

    public Date getMinFechaDesde() {
        Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    public Date getMaxFechaDesde() {
        Calendar c = Calendar.getInstance();
        if (getBindingFechaHasta().getValue() != null) {
            c.clear();
            c.setTime((Date)getBindingFechaHasta().getValue());
            c.set(Calendar.DATE, c.get(Calendar.DATE) + 1);
        } else {
            c.clear();
            c.setTime(new Date());
            c.set(Calendar.YEAR, c.get(Calendar.YEAR) + 1);
            c.set(Calendar.MONTH, c.get(Calendar.MONTH));
            c.set(Calendar.DATE, c.get(Calendar.DATE));
        }
        return c.getTime();
    }

    public void fechaDesdeChangeListener(ValueChangeEvent valueChangeEvent) {

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

    public void setBindingDescripcion(RichInputText bindingDescripcion) {
        this.bindingDescripcion = bindingDescripcion;
    }

    public RichInputText getBindingDescripcion() {
        return bindingDescripcion;
    }

    public void setBindingTipo(RichSelectOneChoice bindingTipo) {
        this.bindingTipo = bindingTipo;
    }

    public RichSelectOneChoice getBindingTipo() {
        return bindingTipo;
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

    public void setBindingPopUp(RichPopup bindingPopUp) {
        this.bindingPopUp = bindingPopUp;
    }

    public RichPopup getBindingPopUp() {
        return bindingPopUp;
    }

    public void setBindingTextoDialog(RichOutputText bindingTextoDialog) {
        this.bindingTextoDialog = bindingTextoDialog;
    }

    public RichOutputText getBindingTextoDialog() {
        return bindingTextoDialog;
    }

    public void setBindingModificar(RichButton bindingModificar) {
        this.bindingModificar = bindingModificar;
    }

    public RichButton getBindingModificar() {
        return bindingModificar;
    }

    public void setBindingEliminar(RichButton bindingEliminar) {
        this.bindingEliminar = bindingEliminar;
    }

    public RichButton getBindingEliminar() {
        return bindingEliminar;
    }

    public void setBindingPanelForm(RichPanelFormLayout bindingPanelForm) {
        this.bindingPanelForm = bindingPanelForm;
    }

    public RichPanelFormLayout getBindingPanelForm() {
        return bindingPanelForm;
    }

    public void setCampania(Campanias campania) {
        this.campania = campania;
    }

    public Campanias getCampania() {
        return campania;
    }

    public void setBindingArchivos(RichInputFile bindingArchivos) {
        this.bindingArchivos = bindingArchivos;
    }

    public RichInputFile getBindingArchivos() {
        return bindingArchivos;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setBindingSegmentar(RichButton bindingSegmentar) {
        this.bindingSegmentar = bindingSegmentar;
    }

    public RichButton getBindingSegmentar() {
        return bindingSegmentar;
    }

    public void setBindingLinkActualizar(RichCommandLink bindingLinkActualizar) {
        this.bindingLinkActualizar = bindingLinkActualizar;
    }

    public RichCommandLink getBindingLinkActualizar() {
        return bindingLinkActualizar;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setBindingRenglon(RichSelectOneChoice bindingRenglon) {
        this.bindingRenglon = bindingRenglon;
    }

    public RichSelectOneChoice getBindingRenglon() {
        return bindingRenglon;
    }

    public void setBindingProducto(RichSelectOneChoice bindingProducto) {
        this.bindingProducto = bindingProducto;
    }

    public RichSelectOneChoice getBindingProducto() {
        return bindingProducto;
    }

    public boolean getRenderSegmentar() {
        boolean permiso = true;
        if (FacesContext.getCurrentInstance().getExternalContext().getRequestServerName().startsWith("apps")) {
            permiso = session.accionHabilitada("11201", "1");
        }

        if (session != null && permiso && getBindingSegmentar() != null) {
            return true;
        } else {
            return false;
        }
    }

    public void setBindingPopUpFiltro(RichPopup bindingPopUpFiltro) {
        this.bindingPopUpFiltro = bindingPopUpFiltro;
    }

    public RichPopup getBindingPopUpFiltro() {
        return bindingPopUpFiltro;
    }

    public void setBindingPWPopUp(RichPanelWindow bindingPWPopUp) {
        this.bindingPWPopUp = bindingPWPopUp;
    }

    public RichPanelWindow getBindingPWPopUp() {
        return bindingPWPopUp;
    }

    public void setBindingPWPopUpFiltro(RichPanelWindow bindingPWPopUpFiltro) {
        this.bindingPWPopUpFiltro = bindingPWPopUpFiltro;
    }

    public RichPanelWindow getBindingPWPopUpFiltro() {
        return bindingPWPopUpFiltro;
    }

    public void setBindingTablaFiltro(RichTable bindingTablaFiltro) {
        this.bindingTablaFiltro = bindingTablaFiltro;
    }

    public RichTable getBindingTablaFiltro() {
        return bindingTablaFiltro;
    }

    /*public void setBindingMensajePopUp(RichMessage bindingMensajePopUp) {
        this.bindingMensajePopUp = bindingMensajePopUp;
    }

    public RichMessage getBindingMensajePopUp() {
        return bindingMensajePopUp;
    }*/
}
