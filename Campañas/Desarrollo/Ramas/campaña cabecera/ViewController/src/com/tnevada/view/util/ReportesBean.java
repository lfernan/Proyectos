package com.tnevada.view.util;

import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasBase;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;

import javax.faces.context.FacesContext;

import javax.naming.InitialContext;

import javax.servlet.ServletContext;

import jxlfw.FabricaHojaCalculo;
import jxlfw.HojaCalculo;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportesBean {
    public ReportesBean() {
        super();
    }

    public static String generarReporteBaseClientes(Campanias campania) {
        try {

            String url = "";
            ServletContext servletContext =
                (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("/");
            File archivo = new File(path, "reportes" + File.separator + "Base de Clientes.xlsx");
            if (archivo.exists()) {
                archivo.delete();
            }
            url = archivo.toString();

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Base");
            short i = 1;
            Row cabecera = sheet.createRow(0);
            cabecera.createCell(0);
            cabecera.createCell(0).setCellValue("Documentos");
            for (CampaniasBase o : campania.getCampaniaBase()) {
                Row row = sheet.createRow(i);
                row.createCell(0);
                row.createCell(0).setCellValue(Long.valueOf(o.getBaseIdentificacion()));
                i++;
            }
            FileOutputStream fileOut = new FileOutputStream(url);
            wb.write(fileOut);
            fileOut.close();

            return url;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
