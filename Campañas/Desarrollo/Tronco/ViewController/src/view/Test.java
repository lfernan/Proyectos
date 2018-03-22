package view;

import com.tnevada.view.util.Util;

import oracle.adf.view.rich.component.rich.RichForm;

import tarjetascuyanas.fw.files.ServerPathLocator;

public class Test {
    private RichForm form;

    public Test() {
        super();
        try {
            Util.setValuePageFlowScope("AbsolutePath", ServerPathLocator.getInstance().getApplicationPath(13, true).getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setForm(RichForm form) {
        this.form = form;
    }

    public RichForm getForm() {
        return form;
    }
}
