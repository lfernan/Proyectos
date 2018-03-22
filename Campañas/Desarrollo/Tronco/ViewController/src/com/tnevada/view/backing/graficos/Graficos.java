package com.tnevada.view.backing.graficos;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.ConsultaDTO;
import com.tnevada.model.entidades.TipoCampanias;
import com.tnevada.view.backing.mensajes.Panel;
import com.tnevada.view.util.CachingServiceLocator;
import com.tnevada.view.util.Util;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import oracle.adf.view.rich.component.rich.RichDocument;
import oracle.adf.view.rich.component.rich.RichForm;
import oracle.adf.view.rich.component.rich.input.RichSelectManyChoice;
import oracle.adf.view.rich.component.rich.input.RichSelectOneChoice;
import oracle.adf.view.rich.component.rich.layout.RichPanelDashboard;
import oracle.adf.view.rich.component.rich.layout.RichPanelGroupLayout;

public class Graficos {
    private RichSelectOneChoice bindingTipo;
    private RichSelectOneChoice bindingProducto;
    private RichSelectOneChoice bindingCampania;
    private RichSelectManyChoice bindingGraficos;
    private RichPanelDashboard grid;
    private RichPanelGroupLayout deck;

    public Graficos() {
        try {
            setBindingProducto(new RichSelectOneChoice());
            setBindingCampania(new RichSelectOneChoice());
            setDeck(new RichPanelGroupLayout());
            setGrid(new RichPanelDashboard());
            getBindingProducto().setDisabled(true);
            getBindingCampania().setDisabled(true);
            getGrid().setVisible(false);
            getDeck().setVisible(false);
            Util.setValuePageFlowScope("refresh", true);
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('" + e.getMessage() + "');");
        }
    }

    public void tipoChangeListener(ValueChangeEvent valueChangeEvent) {
        if (getBindingTipo() != null && getBindingTipo().getValue() != null) {
            getBindingProducto().resetValue();
            getBindingProducto().setDisabled(false);
        }
    }

    public void productoChangeListener(ValueChangeEvent valueChangeEvent) {
        if (getBindingProducto() != null && getBindingProducto().getValue() != null) {
            getBindingCampania().resetValue();
            getBindingCampania().setDisabled(false);
        }
    }

    public void campaniaChangeListener(ValueChangeEvent valueChangeEvent) {
        if (getBindingCampania().getValue() != null) {
            Util.setValuePageFlowScope("camp", ((Campanias) getBindingCampania().getValue()).getCampId());
            Util.setValuePageFlowScope("prod", ((Campanias) getBindingCampania().getValue()).getCampCartera());
        }
    }

    public List<SelectItem> getItemProductos() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            if (getBindingTipo().getValue() != null) {
                for (ConsultaDTO o :
                     ((ExpertoCamapaniasLocal) CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).getProductos(((TipoCampanias) getBindingTipo().getValue()).getTipocampId())) {
                    l.add(new SelectItem(o, o.getNombre()));
                }
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
        return l;
    }

    public List<SelectItem> getItemTipo() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            for (TipoCampanias o :
                 ((ExpertoCamapaniasLocal) CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).getTipoCampanias()) {
                if (o.getTipocampId() == 2L || o.getTipocampId() == 3L || o.getTipocampId() == 5L) {
                    l.add(new SelectItem(o, o.getTipocampDescripcion()));
                }
            }
        } catch (Exception e) {
            Util.ejecutarJs("alertas.error('Error" + e.getMessage() + "');");
        }
        return l;
    }

    public List<SelectItem> getItemCampania() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        try {
            if (getBindingProducto() != null && getBindingProducto().getValue() != null) {
                List<Campanias> c =
                    ((ExpertoCamapaniasLocal) CachingServiceLocator.getInstance().lookup("ExpertoCamapanias")).find(null,
                                                                                                                    null,
                                                                                                                    null,
                                                                                                                    null,
                                                                                                                    null,
                                                                                                                    true,
                                                                                                                    ((ConsultaDTO) getBindingProducto().getValue()).getId());
                for (Campanias o : c) {
                    l.add(new SelectItem(o, o.getCampDescripcion()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public void graficosChangeListener(ValueChangeEvent v) {
        Util.setValuePageFlowScope("historico", false);
        Util.setValuePageFlowScope("produccion", false);
        Util.setValuePageFlowScope("tipificacion", false);
        Util.setValuePageFlowScope("trabajado", false);
        for (String s : (List<String>) getBindingGraficos().getValue()) {
            switch (s) {
            case "historico":
                Util.setValuePageFlowScope("historico", true);
                break;
            case "produccion":
                Util.setValuePageFlowScope("produccion", true);
                break;
            case "tipificacion":
                Util.setValuePageFlowScope("tipificacion", true);
                break;
            case "trabajado":
                Util.setValuePageFlowScope("trabajado", true);
                break;
            default:
            }
        }
    }

    public String grid() {
        refreshRegions(true, false);
        getGrid().setVisible(true);
        Util.refreshUIComponent(getGrid());
        getDeck().setVisible(false);
        Util.refreshUIComponent(getDeck());
        return null;
    }

    public String deck() {
        refreshRegions(false, true);
        getGrid().setVisible(false);
        Util.refreshUIComponent(getGrid());
        getDeck().setVisible(true);
        Util.refreshUIComponent(getDeck());
        return null;
    }

    private void refreshRegions(boolean r1, boolean r2) {
        Util.setValuePageFlowScope("pollrender", true);
        Util.setValuePageFlowScope("tipificaciones1", r1);
        Util.setValuePageFlowScope("historico1", r1);
        Util.setValuePageFlowScope("produccion1", r1);
        Util.setValuePageFlowScope("trabajado1", r1);
        Util.setValuePageFlowScope("tipificaciones2", r2);
        Util.setValuePageFlowScope("historico2", r2);
        Util.setValuePageFlowScope("produccion2", r2);
        Util.setValuePageFlowScope("trabajado2", r2);
    }

    public void setBindingTipo(RichSelectOneChoice bindingTipo) {
        this.bindingTipo = bindingTipo;
    }

    public RichSelectOneChoice getBindingTipo() {
        return bindingTipo;
    }

    public void setBindingProducto(RichSelectOneChoice bindingProducto) {
        this.bindingProducto = bindingProducto;
    }

    public RichSelectOneChoice getBindingProducto() {
        return bindingProducto;
    }

    public void setBindingCampania(RichSelectOneChoice bindingCampania) {
        this.bindingCampania = bindingCampania;
    }

    public RichSelectOneChoice getBindingCampania() {
        return bindingCampania;
    }

    public void setBindingGraficos(RichSelectManyChoice bindingGraficos) {
        this.bindingGraficos = bindingGraficos;
    }

    public RichSelectManyChoice getBindingGraficos() {
        return bindingGraficos;
    }

    public void setGrid(RichPanelDashboard grid) {
        this.grid = grid;
    }

    public RichPanelDashboard getGrid() {
        return grid;
    }

    public void setDeck(RichPanelGroupLayout deck) {
        this.deck = deck;
    }

    public RichPanelGroupLayout getDeck() {
        return deck;
    }
}
