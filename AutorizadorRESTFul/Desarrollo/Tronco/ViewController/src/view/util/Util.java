package view.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.GeneralSecurityException;

import java.text.DecimalFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import javax.servlet.http.HttpServletRequest;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Util {
    private static final char[] PASSWORD = "enfldsgbnlsngdlksdsgm".toCharArray();
    private static final byte[] SALT = {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
    };

    public static Date convertirLongToDate(long fecha) {
        if (fecha == 0) {
            return null;
        }
        if (fecha < 10000) {
            fecha = fecha + 20000000;
        } else if (fecha < 100000) {
            fecha = fecha + 19000000;
        }
        int f = (int) fecha;
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

    public static String encrypt(String property) throws GeneralSecurityException, UnsupportedEncodingException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES"); //("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return base64Encode(pbeCipher.doFinal(property.getBytes("UTF-8")));
    }

    private static String base64Encode(byte[] bytes) {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Encoder().encode(bytes);
    }

    public static String decrypt(String property) throws GeneralSecurityException, IOException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
        Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        // NB: This class is internal, and you probably should use another impl
        return new BASE64Decoder().decodeBuffer(property);
    }

    public static Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    public static String getToken() {
        return UUID.randomUUID().toString();
    }
}
