package com.tnevada.model.segmentacion;


public interface ConsultasSegmentacion {
    public static String INSERT_CAMPANIAS_SEGMENTACION = "insert into campanias_segmentacion \n" + 
    "(case_id, case_camp_id, case_nombre_segmento, case_fecha, case_usuario,case_filtros) \n" + 
    "values (?,?,?,?,?,?)";
    
    public static String SUCURSALES_DTO = "select new com.tnevada.model.entidades.SucursalDTO(s.sucNumero,s.sucDenominacion) from Sucursales s where s.sucActiva = 'S' order by 2 asc";
    
    public static String INSERT_CAMPANIAS_SEGMENTACION_BASE = "insert into campanias_segmentacion_base (caseb_case_id, caseb_base_id) values (?,?)";
    
    public static String UPDATE_MENP001M = "update menp001m set me01_fec_com = 0 where me01_nro_ref = ?nroRef";
    
    public static String RECICLAR_CAMPANIA = "update campanias_segmentacion t1\n" + "   set t1.case_barrido =\n" +
                                 "          (select case\n" +
                                 "                     when max (t2.case_barrido) is null then 1\n" +
                                 "                     else max (t2.case_barrido) + 1 end\n" +
                                 "             from campanias_segmentacion t2\n" +
                                 "            where t2.case_camp_id = ?campId)\n" +
                                 "where t1.case_camp_id = ?campId\n" +
                                 "and t1.case_barrido is null";
    
    public static String AGENDADOS = "  SELECT /*+ NO_CPU_COSTING */\n" + 
    "        TO_NUMBER (TRIM (t2.base_identificacion)), t2.base_id, t1.me01_nro_ref\n" + 
    "    FROM menp001m t1,\n" + 
    "         campanias_base t2,\n" + 
    "         campanias_segmentacion_base t3,\n" + 
    "         mora_perfil_temp t5\n" + 
    "   WHERE     t1.me01_nro_doc = TO_NUMBER (TRIM (t2.base_identificacion))\n" + 
    "         AND t1.me01_fec_com =\n" + 
    "                TO_NUMBER (TRUNC (TO_CHAR (SYSDATE, 'yyyymmdd')))\n" + 
    "         AND EXISTS\n" + 
    "                (SELECT 1\n" + 
    "                   FROM relacion_mensaje_campania t4\n" + 
    "                  WHERE     t4.rel_me01_nro_ref = t1.me01_nro_ref\n" + 
    "                        AND t4.rel_camp_id = t2.base_camp_id\n" + 
    "                        AND t4.rel_me01_nro_doc = t1.me01_nro_doc)\n" + 
    "         AND t5.dni = t1.me01_nro_doc\n" + 
    "         AND t2.base_id = t3.caseb_base_id\n" + 
    "         AND t2.base_camp_id = ?campId\n" + 
    "GROUP BY t2.base_identificacion, t2.base_id, t1.me01_nro_ref";
        
        /*"SELECT \n" + 
    "        TO_NUMBER (TRIM (t2.base_identificacion)), t2.base_id, t1.me01_nro_ref\n" + 
    "    FROM menp001m t1, campanias_base t2, campanias_segmentacion_base t3\n" + 
    "   WHERE     t1.me01_nro_doc = TO_NUMBER (TRIM (t2.base_identificacion))\n" + 
    "         AND t1.me01_fec_com =\n" + 
    "                TO_NUMBER (TRUNC (TO_CHAR (SYSDATE, 'yyyymmdd')))\n" + 
    "         AND EXISTS\n" + 
    "                (SELECT 1\n" + 
    "                   FROM relacion_mensaje_campania t4\n" + 
    "                  WHERE     t4.rel_me01_nro_ref = t1.me01_nro_ref\n" + 
    "                        AND t4.rel_camp_id = ?\n" + 
    "                        AND t4.rel_me01_nro_doc = t1.me01_nro_doc)\n" + 
    "         AND EXISTS\n" + 
    "                (SELECT 1\n" + 
    "                   FROM mora_perfil_temp t5\n" + 
    "                  WHERE t5.dni = t1.me01_nro_doc)\n" + 
    "         AND t2.base_id = t3.caseb_base_id\n" + 
    "         AND t2.base_camp_id = ?\n" + 
    "GROUP BY t2.base_identificacion, t2.base_id, t1.me01_nro_ref ";*/
        
    /*"SELECT  DISTINCT TO_NUMBER(TRIM(t1.base_identificacion)), \n" + 
    "       t1.base_id, \n" + 
    "       (SELECT t3.me01_nro_ref \n" + 
    "          FROM menp001m t3 \n" + 
    "         WHERE t3.me01_nro_doc = TO_NUMBER(TRIM(t1.base_identificacion)) \n" + 
    "           AND t3.me01_fec_com = TO_NUMBER(TRUNC(TO_CHAR(SYSDATE, 'yyyymmdd')))) AS nroRef \n" + 
    "  FROM campanias_segmentacion_base t0, \n" + 
    "       campanias_base t1 \n" + 
    " WHERE t1.base_id = t0.caseb_base_id \n" + 
    "   AND t1.base_camp_id = ? \n" + 
    "   AND EXISTS (SELECT 1 \n" + 
    "                 FROM menp001m t2 \n" + 
    "                WHERE t2.me01_nro_doc = TO_NUMBER(TRIM(t1.base_identificacion)) \n" + 
    "                  AND t2.me01_fec_com = TO_NUMBER(TRUNC(TO_CHAR(SYSDATE, 'yyyymmdd'))) \n" + 
    "                  AND EXISTS (SELECT 1 \n" + 
    "                                FROM relacion_mensaje_campania t4 \n" + 
    "                               WHERE t4.rel_me01_nro_ref = t2.me01_nro_ref \n" + 
    "                                 AND t4.rel_camp_id = ? \n" + 
    "                                 AND t4.rel_me01_nro_doc = t2.me01_nro_doc)) \n" + 
    "   AND EXISTS (SELECT 1 \n" + 
    "                 FROM mora_perfil_temp t5 \n" + 
    "                WHERE t5.dni = TO_NUMBER(TRIM(t1.base_identificacion))) \n" + 
    " ORDER BY T1.BASE_ID\n";*/
    
    public static String TRABAJADOS = "SELECT COUNT (t1.base_id)\n" + 
    "  FROM campanias_base t1, campanias_segmentacion_base t2\n" + 
    " WHERE     t1.base_id = t2.caseb_base_id\n" + 
    "       AND EXISTS\n" + 
    "              (SELECT 1\n" + 
    "                 FROM campanias_segmentacion t3\n" + 
    "                WHERE     t3.case_id = t2.caseb_case_id\n" + 
    "                      AND t3.case_barrido IS NULL)\n" + 
    "       AND t1.base_camp_id = ?campId";
}
