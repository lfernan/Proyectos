package com.tnevada.view.util;

import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasBase;

import java.io.File;
import java.io.FileOutputStream;

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
            File archivo = new File(Util.getPath(),  "Base de Clientes.xlsx");
            if (archivo.exists()) {
                archivo.delete();
            }
            url = archivo.toString();

            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Base");
            short i = 1, c = 0;
            Row cabecera = sheet.createRow(0);
            if(campania.getTipoCampanias().getTipocampId()==5L){                
                cabecera.createCell(c++).setCellValue("ID");                
                cabecera.createCell(c++).setCellValue("DOCUMENTO");                
                cabecera.createCell(c++).setCellValue("NOMBRE");                
                cabecera.createCell(c++).setCellValue("APELLIDO");
                cabecera.createCell(c++).setCellValue("TELEFONO 1");
                cabecera.createCell(c++).setCellValue("TELEFONO 2");
                cabecera.createCell(c++).setCellValue("TELEFONO 3");
                cabecera.createCell(c++).setCellValue("TELEFONO 4");
                cabecera.createCell(c++).setCellValue("PROVINCIA");
                cabecera.createCell(c++).setCellValue("SEXO");
                cabecera.createCell(c++).setCellValue("DOMICILIO");
                c = 0;
                for (CampaniasBase o : campania.getCampaniaBase()) {
                    Row row = sheet.createRow(i);                            
                    row.createCell(c++).setCellValue(o.getBaseId());                                    
                    row.createCell(c++).setCellValue(o.getBaseIdentificacion()==null?"":o.getBaseIdentificacion());                    
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansNombres());
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansApellidos());                    
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansNroTel1()==null?"":o.getCampaniasNoSocios().getCansNroTel1().toString());                                        
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansNroTel2()==null?"":o.getCampaniasNoSocios().getCansNroTel2().toString());                                                            
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansNroTel3()==null?"":o.getCampaniasNoSocios().getCansNroTel3().toString());                                        
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansNroTel4()==null?"":o.getCampaniasNoSocios().getCansNroTel4().toString());
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansProvincia()==null?"":o.getCampaniasNoSocios().getCansProvincia().toString());
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansSexo()==null?"":o.getCampaniasNoSocios().getCansSexo().toString());                                        
                    row.createCell(c++).setCellValue(o.getCampaniasNoSocios().getCansDomicilio()==null?"":o.getCampaniasNoSocios().getCansDomicilio().toString()); 
                    c = 0;
                    i++;                            
                }                
            }else{
                cabecera.createCell(0);
                cabecera.createCell(0).setCellValue("Documentos");
                
                for (CampaniasBase o : campania.getCampaniaBase()) {
                    Row row = sheet.createRow(i);
                    row.createCell(0);
                    if(o.getBaseIdentificacion()!=null && !o.getBaseIdentificacion().trim().equals("")){                    
                        row.createCell(0).setCellValue(o.getBaseIdentificacion());
                        i++;
                    }                
                }
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
