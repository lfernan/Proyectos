package com.tnevada.view.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ArchivosBean {
    public ArchivosBean() {
    }

    public static void descargaArchivo(List<String> lineas, String nombreArchivo) {
        FileReader fr = null;
        FacesContext fctx = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse)fctx.getExternalContext().getResponse();

        response.setContentType("image/jpg");
        response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo);
        response.setContentType("application/x-download; charset=ISO-8859-1");

        OutputStream out = null;

        try {
            StringBuffer aux = new StringBuffer();
            out = response.getOutputStream();

            for (String linea : lineas) {
                aux.append(linea + ("\r\n"));
            }
            out.write(aux.toString().getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.INFO, e.toString());
            return;
        } finally {
            try { //el bloque finally se ejecuta siempre, por eso, si se cierra el fichero
                if (fr != null) { //al final del primer try, y ha dado un error antes, pasaría
                    fr.close(); //al 1er catch y luego saldría, dejándolo abierto. Es conveniente
                } //cerrarlo aquí, comprobando que no sea -por un error anterior, como
            } catch (IOException e) { // no tener permisos de lectura o que no exista - de valor null.
                Logger.getAnonymousLogger().log(Level.INFO, e.toString());
            }
        }

        fctx.responseComplete();
    }

    public static void escribirEnDisco(List<String> l, String nombre, String ruta) {
        PrintWriter pw = null;
        try {
            //pw = new PrintWriter(new BufferedWriter(new FileWriter("C:\\" + nombre)));
            pw = new PrintWriter(new BufferedWriter(new FileWriter(ruta + nombre)));
        } catch (IOException e) {
            Logger.getLogger("oracle").log(Level.SEVERE,
                                           "No se puede crear el archivo para escribir en Disco: " + ruta + nombre, e);
            e.printStackTrace();
        }
        StringBuffer str = new StringBuffer();
        for (int i = 0; i < l.size(); i++) {
            str.append(l.get(i) + ("\r\n"));
        }
        pw.write(str.toString());
        pw.close();
    }

    public static void anexarAArchivo_BACKUP(List<List<String>> l, String nombre, String ruta) {
        try {
            // Recuperar lo que haya previamente en el archivo
            List<String> anterior = new ArrayList<String>();
            boolean existe = true;
            BufferedReader bufInput = null;
            try {
                bufInput = new BufferedReader(new FileReader(ruta + nombre));
            } catch (FileNotFoundException e) {
                existe = false;
                e.getStackTrace();
            }
            if (existe) {
                String linea = bufInput.readLine();
                while (linea != null) {
                    anterior.add(linea);
                    linea = bufInput.readLine();
                }
                l.add(0, anterior);
            }
            // Escribir en el nuevo Archivo
            BufferedWriter bufOutput = null;
            try {
                bufOutput = new BufferedWriter(new FileWriter(ruta + nombre));
            } catch (IOException e) {
                e.getStackTrace();
            }
            // Escribir linea a linea
            boolean primeraLinea = true;
            for (int t = 0; t < l.size(); t++) {
                for (int i = 0; i < l.get(t).size(); i++) {
                    if (!primeraLinea)
                        bufOutput.newLine();
                    else
                        primeraLinea = false;
                    bufOutput.write(l.get(t).get(i));
                }
            }
            if (bufInput != null)
                bufInput.close();
            if (bufOutput != null)
                bufOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void anexarAArchivo(List<List<String>> l, String nombre, String ruta) {
        ArchivosBean.anexarAArchivo(l, nombre, ruta, null);
    }

    public static void anexarAArchivo(List<List<String>> l, String nombre, String ruta, Boolean retornoCarroFinal) {
        try {
            // Recuperar lo que haya previamente en el archivo
            List<String> anterior = new ArrayList<String>();
            boolean existe = true;
            BufferedReader bufInput = null;
            try {
                bufInput = new BufferedReader(new FileReader(ruta + nombre));
            } catch (FileNotFoundException e) {
                existe = false;
                e.getStackTrace();
            }
            if (existe) {
                String linea = bufInput.readLine();
                while (linea != null) {
                    anterior.add(linea);
                    linea = bufInput.readLine();
                }
                l.add(0, anterior);
            }
            // Escribir en el nuevo Archivo
            BufferedWriter bufOutput = null;
            try {
                bufOutput = new BufferedWriter(new FileWriter(ruta + nombre));
            } catch (IOException e) {
                e.getStackTrace();
            }
            // Escribir linea a linea
            boolean primeraLinea = true;
            for (int t = 0; t < l.size(); t++) {
                for (int i = 0; i < l.get(t).size(); i++) {
                    if (!primeraLinea)
                        bufOutput.newLine();
                    else
                        primeraLinea = false;
                    bufOutput.write(l.get(t).get(i));
                }
            }
            if (retornoCarroFinal != null && retornoCarroFinal)
                bufOutput.newLine();

            if (bufInput != null)
                bufInput.close();
            if (bufOutput != null)
                bufOutput.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void descargaArchivoExcel(String ubicacionArchivo, String nombreArchivo) {
        ServletOutputStream outs = null;
        InputStream in = null;
        try {
            File f = new File(ubicacionArchivo);

            FacesContext fctx = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)fctx.getExternalContext().getResponse();

            //Configurar el tipo de archivo.
            response.setContentType("application/vnd.ms-excel");

            //Configurar cabecera y nombre de archivo a desplegar en DialogBox.
            response.setHeader("Content-Disposition", "attachment;filename=\"" + nombreArchivo + "\"");


            in = new FileInputStream(f);
            outs = response.getOutputStream();

            int bit = 256;
            int i = 0;

            while ((bit) >= 0) {
                bit = in.read();
                outs.write(bit);
            }

            outs.flush();
            outs.close();
            in.close();

            fctx.responseComplete();
        } catch (Exception e) {
            try {
                outs.close();
                in.close();
            } catch (IOException f) {

            }
        }
    }

    //DEPRECATED

    public static void descargaArchivoTxt2(String ubicacionArchivo, String nombreArchivo) {

        try {
            File f = new File(ubicacionArchivo);

            FacesContext fctx = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)fctx.getExternalContext().getResponse();

            //Configurar el tipo de archivo.
            //response.setContentType ("text/html");
            response.setContentType("text/html;charset=UTF-8");
            //Configurar cabecera y nombre de archivo a desplegar en DialogBox.
            response.setHeader("Content-Disposition", "attachment;filename=\"" + nombreArchivo + "\"");

            InputStream in = new FileInputStream(f);
            ServletOutputStream outs = response.getOutputStream();

            int bit = 256;
            int i = 0;

            while ((bit) >= 0) {
                bit = in.read();
                outs.write(bit);
            }

            outs.flush();
            outs.close();
            in.close();

            fctx.responseComplete();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    public static void descargaArchivoTxt(String ubicacionArchivo, String nomFile) {
        try {
            File f = new File(ubicacionArchivo);
            FileInputStream archivo = new FileInputStream(f);
            int longitud = archivo.available();
            byte[] datos = new byte[longitud];
            archivo.read(datos);
            archivo.close();

            FacesContext fctx = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)fctx.getExternalContext().getResponse();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + nomFile + "\"");
            //response.setHeader("Content-Disposition","attachment;filename="+nomFile);
            ServletOutputStream ouputStream = response.getOutputStream();
            ouputStream.write(datos);

            ouputStream.flush();
            ouputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void agregarAArchivo(List<String> l, String nombre, String ruta) {
        try {
            // Recuperar lo que haya previamente en el archivo
            boolean existe = true;
            BufferedReader bufInput = null;
            try {
                bufInput = new BufferedReader(new FileReader(ruta + nombre));
            } catch (FileNotFoundException e) {
                existe = false;
                e.getStackTrace();
            }
            if (existe) {
                String linea = bufInput.readLine();
                while (linea != null) {
                    l.add(linea); //l.add(l.size(),linea);
                    linea = bufInput.readLine();
                }
            }
            // Escribir en el nuevo Archivo
            BufferedWriter bufOutput = null;
            try {
                bufOutput = new BufferedWriter(new FileWriter(ruta + nombre));
            } catch (IOException e) {
                e.getStackTrace();
            }
            // Escribir linea a linea
            boolean primeraLinea = true;
            for (int t = 0; t < l.size(); t++) {
                if (!primeraLinea)
                    bufOutput.newLine();
                else
                    primeraLinea = false;
                bufOutput.write(l.get(t));
            }
            if (bufInput != null)
                bufInput.close();
            if (bufOutput != null)
                bufOutput.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void descargaArchivoZip(String ubicacionArchivo, String nombreArchivo) {
        ServletOutputStream outs = null;
        InputStream in = null;
        try {
            File f = new File(ubicacionArchivo);

            FacesContext fctx = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)fctx.getExternalContext().getResponse();

            //Configurar el tipo de archivo.
            response.setContentType("application/zip");

            //Configurar cabecera y nombre de archivo a desplegar en DialogBox.
            response.setHeader("Content-Disposition", "attachment;filename=\"" + nombreArchivo + "\"");


            in = new FileInputStream(f);
            outs = response.getOutputStream();

            int bit = 256;
            int i = 0;

            while ((bit) >= 0) {
                bit = in.read();
                outs.write(bit);
            }

            outs.flush();
            outs.close();
            in.close();

            fctx.responseComplete();
        } catch (Exception e) {
            try {
                outs.close();
                in.close();
            } catch (IOException f) {

            }
        }
    }
}

