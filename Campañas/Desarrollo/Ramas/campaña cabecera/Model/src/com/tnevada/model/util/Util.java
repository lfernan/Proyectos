package com.tnevada.model.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.math.BigDecimal;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class Util {
    private static Random random = new Random(System.currentTimeMillis());

    public Util() {
    }

    public static String formatDate(String formato, Date fecha) {
        if (fecha == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        return sdf.format(fecha.getTime());

    }

    public static Date convertirLongToDate(long fecha) {
        if (fecha == 0) {
            return null;
        }
        if (fecha < 10000) {
            fecha = fecha + 20000000;
        } else if (fecha < 100000) {
            fecha = fecha + 19000000;
        }
        int f = (int)fecha;
        int dia = f / 100;
        dia = f - dia * 100;
        f = f / 100;
        int anio = f / 100;
        int mes = f - anio * 100;

        Calendar calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes - 1);
        calendario.set(Calendar.DAY_OF_MONTH, dia);

        return calendario.getTime();

    }

    public static long convertirDateToLong(Date fecha) {
        DecimalFormat df = new DecimalFormat("00");
        if (fecha != null) {
            Calendar calendario = Calendar.getInstance();
            calendario.setTime(fecha);
            String fechaLong =
                calendario.get(Calendar.YEAR) + "" + df.format(calendario.get(Calendar.MONTH) + 1) + "" +
                df.format(calendario.get(Calendar.DAY_OF_MONTH));
            return new Long(fechaLong).longValue();

        } else {
            return 0;
        }
    }

    public static XMLGregorianCalendar convertirDateToXMLGregorianCalendar(final Date fecha) throws DatatypeConfigurationException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(fecha);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }

    public static String uniqueId() {
        return java.util.UUID.randomUUID().toString();
    }


    public static String formatNumber(String formato, Double valor) {
        DecimalFormat formateador = new DecimalFormat(formato);
        return formateador.format(valor);
        //DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        //simbolos.setDecimalSeparator(',');
        //simbolos.setPerMill('.');
        //"###,###,##0.00"
    }

    public static HashMap getNumeroComercio(String nroComercio) {
        if (nroComercio == null || nroComercio.trim().isEmpty())
            return null;
        try {
            HashMap map = new HashMap();
            map.put("SUCURSAL",
                    Long.valueOf(nroComercio.substring(0, (nroComercio.length() >= 3 ? 3 : nroComercio.length()))));
            if (nroComercio.length() > 4)
                map.put("LOCAL",
                        Long.valueOf(nroComercio.substring(4, (nroComercio.length() >= 9 ? 9 : nroComercio.length()))));
            if (nroComercio.length() > 10)
                map.put("DIGITO",
                        Long.valueOf(nroComercio.substring(10,
                                                           (nroComercio.length() >= 11 ? 11 : nroComercio.length()))));
            return map;
        } catch (Exception e) {
            return null;
        }
    }

    public static double redondear(double valor, int decimales) {
        BigDecimal bd = BigDecimal.valueOf(valor);
        bd = bd.setScale(decimales, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static String userRandom(int lenght) {
        char[] values = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
            'v', 'w', 'x', 'y', 'z'
        };
        String out = "";
        for (int i = 0; i < lenght; i++) {
            int idx = random.nextInt(values.length);
            out += values[idx];
        }
        return out;
    }

    public static String passwRandom(int lenght) {
        char[] values = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
            'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
        };
        String out = "";
        for (int i = 0; i < lenght; i++) {
            int idx = random.nextInt(values.length);
            out += values[idx];
        }
        return out;
    }

    public static String colorRandom() {
        String[] values = {
            "#641E16", "#1B2631", "#626567", "#6E2C00", "#784212", "#7E5109", "#7D6608", "#186A3B", "#145A32",
            "#0B5345", "#0E6251", "#1B4F72", "#154360", "#4A235A", "#512E5F", "#78281F"
        };
        String out = "";
        int idx = random.nextInt(values.length);
        out += values[idx];
        return out;
    }

    public static String formatearHora(long hora) {
        String h = String.valueOf(hora);
        for (int i = h.trim().length(); i < 6; i++) {
            h = "0" + h;
        }
        h = h.substring(0, 6);
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
        try {
            Date d = sdf.parse(h);
            SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss");
            return f.format(d);
        } catch (ParseException e) {
            e.getMessage();
        }
        return null;
    }

    public static String getStackTraceMessage(Exception e) {

        String strStackTrace = "";

        while (e != null) {
            StackTraceElement[] stackTraceArray = e.getStackTrace();
            strStackTrace = "" + e;
            for (StackTraceElement stackTraceElement : stackTraceArray) {
                strStackTrace +=
                    " \n Class Name :- " + stackTraceElement.getClassName() + ", Method Name :- " +
                    stackTraceElement.getMethodName() + " , Line Number :- " + stackTraceElement.getLineNumber();
                break;
            }

            if (e == e.getCause()) {
                e = null;
            } else {
                e = (Exception)e.getCause();
                strStackTrace += "\nCaused by ";
            }
        }
        return strStackTrace;
    }
    
    public static HashMap<String, Object> jsonParse(String json) {
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject)parser.parse(json);
        Set<Map.Entry<String, JsonElement>> set = object.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
        HashMap<String, Object> map = new HashMap<String, Object>();

        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (null != value) {
                if (!value.isJsonPrimitive()) {
                    if (value.isJsonObject()) {
                        map.put(key, jsonParse(value.toString()));
                    } else if (value.isJsonArray() && value.toString().contains(":")) {
                        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                        JsonArray array = value.getAsJsonArray();
                        if (null != array) {
                            for (JsonElement element : array) {
                                list.add(jsonParse(element.toString()));
                            }
                            map.put(key, list);
                        }
                    } else if (value.isJsonArray() && !value.toString().contains(":")) {
                        map.put(key, value.getAsJsonArray());
                    }
                } else {
                    map.put(key, value.getAsString());
                }
            }
        }
        return map;
    }
}
