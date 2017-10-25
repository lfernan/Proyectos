package model.session;

public interface Queries {
    public static final String VALIDAR_DATOS = "SELECT CASE \n" + 
            "          WHEN     (SELECT valida_codigo_seguridad (?,?,?) \n" + 
            "                      FROM DUAL) = 1 \n" + 
            "               AND (SELECT 1 \n" + 
            "                      FROM codseg_tarjeta \n" + 
            "                     WHERE     codseg_tarjeta = ? \n" + 
            "                           AND codseg_fecha_hasta = ? ) = 1 \n" + 
            "               AND (SELECT VALIDAR_NOMBRE(?,?) FROM dual) = 1 \n" + 
            "          THEN 1 ELSE 0 \n" + 
            "       END\n" + 
            "  FROM DUAL";
    
    public static final String LOGIN = "SELECT valida_usuario_gateway (?,?) AS resultado FROM DUAL";
    
    //public static final String VERIFICAR_OPERACION = "SELECT verificar_compra_gateway (?,?,?,?,?) from dual";
            public static final String VERIFICAR_OPERACION = "SELECT gat_codigo_autorizacion as autorizacion,\n" + 
            "       to_char(gat_fecha_operacion,'yyyy/mm/dd hh:mm:ss') as fecha,\n" + 
            "       gat_codigo_entidad as entidad,\n" + 
            "       gat_monto_total as monto,\n" + 
            "       gat_operacion_id as operacion\n" + 
            "  FROM gateway_pago, mpermiso\n" + 
            " WHERE     gat_monto_total = ?\n" + 
            "       AND gat_operacion_id = ?\n" + 
            "       AND per_codigo = gat_codigo_autorizacion\n" + 
            "       AND ROWNUM < 2";
    
    public static final String GRABAR_LOGIN_ACEPTADO = "BEGIN GRABAR_LOG_GATEWAY(?,?,?,?,?,?,?,?,?); END;";
    
    public static final String PLANES = "BEGIN MOSTRAR_OPCIONES_PLAN (?,?,?,?); END;";
    
}
