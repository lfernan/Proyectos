CREATE OR REPLACE PACKAGE BODY pkg_campanias_call
AS
   TYPE tabla_agendados IS RECORD
   (
      n_documento   PLS_INTEGER,
      n_base_id     PLS_INTEGER,
      n_nro_ref     PLS_INTEGER
   );

   TYPE tabla_no_trabajados IS RECORD
   (
      n_documento       PLS_INTEGER,
      n_base_id         PLS_INTEGER,
      n_local_emision   VARCHAR2 (32767),
      n_plan_pago       VARCHAR2 (32767),
      n_plan_pago_1     VARCHAR2 (32767)
   );

   TYPE type_agendados IS TABLE OF tabla_agendados
                             INDEX BY PLS_INTEGER;

   TYPE type_no_trabajados IS TABLE OF tabla_no_trabajados
                                 INDEX BY PLS_INTEGER;

   list_no_trabajados    type_no_trabajados;
   list_agendados        type_agendados;
   query_trabajados      VARCHAR2 (32767);
   query_no_trabajados   VARCHAR2 (32767);
   query_agendados       VARCHAR2 (32767);
   json                  VARCHAR2 (32767);
   LOG                   programas_log%ROWTYPE;
   is_segmentar          BOOLEAN;
   json_filtros          VARCHAR2 (32767);
   json_pp               VARCHAR2 (32767);
   json_suc              VARCHAR2 (32767);
   suc_row               sucursales%ROWTYPE;
   suc_query             VARCHAR2 (32767);
   count_trabajados      PLS_INTEGER;

   FUNCTION f_base (p_sucursales   IN VARCHAR2,
                    p_planpago     IN VARCHAR2,
                    p_campania     IN PLS_INTEGER,
                    p_tipo         IN PLS_INTEGER,
                    p_edad         IN VARCHAR2,
                    p_resumen      IN VARCHAR2,
                    p_cantidad     IN NUMBER)
      RETURN VARCHAR2
   AS
      TYPE suc_type IS REF CURSOR;

      suc_c      suc_type;
      v_result   VARCHAR2 (32767);
   BEGIN
      LOG.plg_hora_inicio := SYSDATE;
      json_filtros := NULL;
      json_pp := 'Ambos';
      json_suc := '"Todas"';

      SELECT COUNT (1)
        INTO count_trabajados
        FROM campanias_base t1, campanias_segmentacion_base t2
       WHERE     t1.base_id = t2.caseb_base_id
             AND EXISTS
                    (SELECT 1
                       FROM campanias_segmentacion t3
                      WHERE     t3.case_id = t2.caseb_case_id
                            AND t3.case_barrido IS NULL)
             AND t1.base_camp_id = p_campania;

      query_agendados :=
            'SELECT /*+ NO_CPU_COSTING */
                                      TO_NUMBER (TRIM (t2.base_identificacion)) AS n_documento,
                                       t2.base_id AS n_base_id,
                                       t1.me01_nro_ref AS n_nro_ref
                                  FROM menp001m t1, campanias_base t2, campanias_segmentacion_base t3
                                 WHERE     t1.me01_nro_doc = TO_NUMBER (TRIM (t2.base_identificacion))
                                       AND t1.me01_fec_com =
                                              TO_NUMBER (TRUNC (TO_CHAR (SYSDATE, ''yyyymmdd'')))
                                       AND EXISTS
                                              (SELECT 1
                                                 FROM relacion_mensaje_campania t4
                                                WHERE     t4.rel_me01_nro_ref = t1.me01_nro_ref
                                                      AND t4.rel_camp_id = t2.base_camp_id
                                                      AND t4.rel_me01_nro_doc = t1.me01_nro_doc)
                                       AND t2.base_id = t3.caseb_base_id
                                       AND t2.base_camp_id = '
         || p_campania;

      IF p_tipo = 5
      THEN
         query_agendados :=
               query_agendados
            || ' UNION ALL
                                SELECT TO_NUMBER (TRIM (t2.base_identificacion)) AS n_documento,
                                       t2.base_id AS n_base_id,
                                       t0.cans_id AS n_nro_ref
                                  FROM campanias_no_socios t0,
                                       campanias_no_socios_mensajes t1,
                                       campanias_base t2,
                                       campanias_segmentacion_base t4
                                 WHERE     t1.cansm_cans_id = t0.cans_id
                                       AND T2.BASE_CANS_ID = t0.cans_id
                                       AND t2.base_id = t4.caseb_base_id
                                       AND t2.base_camp_id = '
            || p_campania
            || 'AND t1.cansm_fecha_compromiso = TRUNC (SYSDATE) ';
      END IF;

      IF p_tipo = 1
      THEN
         /*query_agendados := query_agendados || ' AND EXISTS
                                                     (SELECT 1
                                                       FROM mora_perfil_temp t5
                                                       WHERE t5.dni = t1.me01_nro_doc)';*/
         query_agendados := query_agendados || ' AND 1 = 2';
      END IF;

      IF p_tipo <> 5
      THEN
         query_agendados :=
               query_agendados
            || ' GROUP BY t2.base_identificacion, t2.base_id, t1.me01_nro_ref';
      END IF;

      json_filtros := '{"Cantidad":' || p_cantidad;

      IF p_tipo = 2 OR p_tipo = 3 OR p_tipo = 5
      THEN
         query_no_trabajados :=
               'SELECT TO_NUMBER (TRIM (t2.base_identificacion)) AS n_documento,
                                               t2.base_id AS n_base_id,
                                               NULL AS n_local_emision,
                                               NULL AS n_plan_pago,
                                               NULL AS n_plan_pago_1
                                                FROM campanias_base t2
                                               WHERE t2.base_camp_id = '
            || p_campania
            || '
                                               AND t2.base_gestion IS NULL
                                               AND NOT EXISTS
                                                (SELECT 1
                                                   FROM campanias_segmentacion_base t3
                                                  WHERE t3.caseb_base_id = TO_NUMBER (TRIM (t2.base_id))
                                                        AND NOT EXISTS
                                                               (SELECT 1
                                                                  FROM campanias_segmentacion t4
                                                                 WHERE t4.case_id = t3.caseb_case_id AND t4.case_barrido IS NOT NULL))';

         IF p_tipo <> 5
         THEN
            IF p_sucursales IS NOT NULL
            THEN
               query_no_trabajados :=
                     query_no_trabajados
                  || 'AND EXISTS(
                                                              SELECT 1 FROM mtarje
                                                              WHERE tar_documento = TO_NUMBER (base_identificacion)
                                                              AND TO_NUMBER (tar_local_emision) IN ('
                  || p_sucursales
                  || '))';
            END IF;

            IF p_edad IS NOT NULL
            THEN
               query_no_trabajados :=
                     query_no_trabajados
                  || 'AND EXISTS (SELECT 1 FROM MTARJE
                                                                     WHERE tar_documento = TO_NUMBER (t2.base_identificacion)
                                                                     AND FLOOR (MONTHS_BETWEEN (SYSDATE, TO_DATE(tar_fecha_nac,''YYYYMMDD''))/12)
                                                                     BETWEEN SUBSTR ('''
                  || p_edad
                  || ''', 0, INSTR ('''
                  || p_edad
                  || ''', ''-'') - 1)
                                                                     AND SUBSTR ('''
                  || p_edad
                  || ''', INSTR ('''
                  || p_edad
                  || ''', ''-'') + 1, LENGTH ('''
                  || p_edad
                  || '''))
                                                                     AND tar_fecha_nac > 0
                                                                     AND tar_fecha_baja = 0)';
               json_filtros := json_filtros || ',"Rango de Edad":"' || p_edad;
            ELSE
               json_filtros := json_filtros || ',"Rango de Edad":"Todas';
            END IF;

            IF p_resumen IS NOT NULL AND p_resumen = 'S'
            THEN
               query_no_trabajados :=
                     query_no_trabajados
                  || 'AND EXISTS (SELECT DISTINCT 1
                                                                        FROM mcupon, mbase
                                                                        WHERE cup_fecha_vto > ba_fecha_vencimiento_1
                                                                        AND cup_saldo <> 0
                                                                        AND ba_numero = 0
                                                                        AND cup_titular = TO_NUMBER (t2.base_identificacion))';
               json_filtros := json_filtros || '","Con Resumen":"SI';
            ELSIF p_resumen IS NOT NULL AND p_resumen = 'N'
            THEN
               query_no_trabajados :=
                     query_no_trabajados
                  || 'AND NOT EXISTS (SELECT DISTINCT 1
                                                                        FROM mcupon, mbase
                                                                        WHERE cup_fecha_vto > ba_fecha_vencimiento_1
                                                                        AND cup_saldo <> 0
                                                                        AND ba_numero = 0
                                                                        AND cup_titular = TO_NUMBER (t2.base_identificacion))';
               json_filtros := json_filtros || '","Con Resumen":"NO';
            ELSIF p_resumen IS NOT NULL AND p_resumen = 'A'
            THEN
               json_filtros := json_filtros || '","Con Resumen":"Ambos';
            END IF;
         END IF;
      ELSIF p_tipo = 1
      THEN
         query_no_trabajados :=
               'SELECT TO_NUMBER (TRIM (t2.base_identificacion)) AS n_documento,
                                               t2.base_id AS n_base_id,
                                               t1.localemision AS n_local_emision,
                                               t1.planpago AS n_plan_pago,
                                               t1.planpago1 AS n_plan_pago_1
                                          FROM mora_perfil_temp t1, campanias_base t2
                                         WHERE TO_NUMBER (TRIM (t1.dni)) = TO_NUMBER (TRIM (t2.base_identificacion))
                                                AND t1.fechamora = (SELECT TO_NUMBER (camp_cartera) FROM campanias WHERE camp_id = t2.base_camp_id)
                                               AND t2.base_camp_id = '
            || p_campania
            || '
                                               AND t2.base_gestion IS NULL
                                               AND NOT EXISTS
                                                          (SELECT 1
                                                             FROM campanias_segmentacion_base t3
                                                            WHERE t3.caseb_base_id = TO_NUMBER (TRIM (t2.base_id))
                                                                  AND NOT EXISTS
                                                                             (SELECT 1
                                                                                FROM campanias_segmentacion t4
                                                                               WHERE t4.case_id = t3.caseb_case_id
                                                                                     AND t4.case_barrido IS NOT NULL))';

         IF p_planpago IS NOT NULL AND p_planpago = 'N'
         THEN
            query_no_trabajados :=
               query_no_trabajados || 'AND t1.planpago = ''0'' ';
            json_filtros := json_filtros || ',"Plan de Pago":"NO';
         ELSIF p_planpago IS NOT NULL AND p_planpago = 'S'
         THEN
            query_no_trabajados :=
               query_no_trabajados || ' AND t1.planpago <> ''0'' ';
            json_filtros := json_filtros || ',"Plan de Pago":"SI';
         ELSE
            json_filtros := json_filtros || ',"Plan de Pago":"Ambos';
         END IF;

         IF p_sucursales IS NOT NULL
         THEN
            query_no_trabajados :=
                  query_no_trabajados
               || ' AND TO_NUMBER(t1.localemision) IN('
               || p_sucursales
               || ')';
         END IF;

         IF is_segmentar = TRUE
         THEN
            query_no_trabajados :=
               query_no_trabajados || ' ORDER BY t1.deuda_total DESC ';
         END IF;
      END IF;

      IF p_sucursales IS NOT NULL
      THEN
         suc_query :=
               'SELECT * FROM sucursales WHERE suc_numero IN('
            || p_sucursales
            || ')';

         OPEN suc_c FOR suc_query;

         json_suc := '[';

         LOOP
            FETCH suc_c INTO suc_row;

            EXIT WHEN suc_c%NOTFOUND;
            json_suc :=
               json_suc || '"' || TRIM (suc_row.suc_denominacion) || '"';
            json_suc := json_suc || ',';
         END LOOP;

         SELECT SUBSTR (json_suc, 1, LENGTH (json_suc) - 1)
           INTO json_suc
           FROM DUAL;

         json_suc := json_suc || ']';
      END IF;

      EXECUTE IMMEDIATE query_agendados BULK COLLECT INTO list_agendados;

      EXECUTE IMMEDIATE query_no_trabajados
         BULK COLLECT INTO list_no_trabajados;

      IF p_tipo = 5
      THEN
         json_filtros := json_filtros || '}';
      ELSE
         json_filtros := json_filtros || '","Sucursales":' || json_suc || '}';
      END IF;

      json :=
            '{''NT'':'
         || list_no_trabajados.COUNT
         || ','
         || '''T'':'
         || count_trabajados
         || ','
         || '''A'':'
         || list_agendados.COUNT
         || '}';
      v_result := json;

      RETURN v_result;
   EXCEPTION
      WHEN OTHERS
      THEN
         LOG.plg_descripcion := 'PKG_CAMPANIAS_CALL.F_BASE ';
         LOG.plg_mes_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'mm'));
         LOG.plg_anio_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'yyyy'));
         LOG.plg_usuario_alta := USER;
         LOG.plg_fecha_alta := SYSDATE;
         LOG.plg_observaciones := 'Error en la funcion f_base  - ' || SQLERRM;
         LOG.plg_hora_fin := SYSDATE;
         pkg_util.p_grabar_log ('I', LOG);
   END f_base;


   PROCEDURE p_base (p_sucursales   IN            VARCHAR2,
                     p_planpago     IN            VARCHAR2,
                     p_campania     IN            PLS_INTEGER,
                     p_tipo         IN            PLS_INTEGER,
                     p_edad         IN            VARCHAR2,
                     p_resumen      IN            VARCHAR2,
                     p_datos           OUT NOCOPY VARCHAR2)
   IS
   BEGIN
      is_segmentar := FALSE;
      p_datos :=
         f_base (p_sucursales,
                 p_planpago,
                 p_campania,
                 p_tipo,
                 p_edad,
                 p_resumen,
                 NULL);
   EXCEPTION
      WHEN OTHERS
      THEN
         ROLLBACK;
   END p_base;

   PROCEDURE p_segmentar (p_cantidad          IN     PLS_INTEGER,
                          p_planpago          IN     VARCHAR2,
                          p_campania          IN     PLS_INTEGER,
                          p_legajo            IN     PLS_INTEGER,
                          p_campania_nombre   IN     VARCHAR2,
                          p_sucursales        IN     VARCHAR2,
                          p_tipo              IN     PLS_INTEGER,
                          p_edad              IN     VARCHAR2,
                          p_resumen           IN     VARCHAR2,
                          p_id_segmento          OUT NUMBER)
   IS
      cantidad   PLS_INTEGER;
      deuda      PLS_INTEGER := 1;
   BEGIN
      LOG.plg_hora_inicio := SYSDATE;
      is_segmentar := TRUE;

      json :=
         f_base (p_sucursales,
                 p_planpago,
                 p_campania,
                 p_tipo,
                 p_edad,
                 p_resumen,
                 p_cantidad);

      INSERT INTO campanias_segmentacion (case_id,
                                          case_camp_id,
                                          case_nombre_segmento,
                                          case_fecha,
                                          case_usuario,
                                          case_filtros)
           VALUES (NULL,
                   p_campania,
                   p_campania_nombre,
                   NULL,
                   p_legajo,
                   json_filtros);

      IF list_agendados.COUNT > 0 AND list_agendados.COUNT >= p_cantidad
      THEN
         cantidad := 0;
      ELSIF list_agendados.COUNT > 0 AND list_agendados.COUNT < p_cantidad
      THEN
         cantidad := (p_cantidad - list_agendados.COUNT);
      ELSE
         cantidad := p_cantidad;
      END IF;

      IF list_agendados.COUNT > 0
      THEN
         FOR i IN list_agendados.FIRST .. list_agendados.LAST
         LOOP
            INSERT
              INTO campanias_segmentacion_base (caseb_case_id,
                                                caseb_base_id,
                                                caseb_orden)
            VALUES (0, list_agendados (i).n_base_id, deuda);

            deuda := deuda + 1;

            IF p_tipo = 5
            THEN
               UPDATE campanias_no_socios_mensajes
                  SET cansm_fecha_compromiso = NULL
                WHERE cansm_cans_id = list_agendados (i).n_nro_ref;
            ELSE
               UPDATE menp001m
                  SET me01_fec_com = 0
                WHERE me01_nro_ref = list_agendados (i).n_nro_ref;
            END IF;
         END LOOP;
      END IF;

      FOR i IN list_no_trabajados.FIRST .. list_no_trabajados.LAST
      LOOP
         EXIT WHEN i = cantidad + 1;

         INSERT
           INTO campanias_segmentacion_base (caseb_case_id,
                                             caseb_base_id,
                                             caseb_orden)
         VALUES (0, list_no_trabajados (i).n_base_id, deuda);

         deuda := deuda + 1;
      END LOOP;

      SELECT SEQ_SEGMENTACION.CURRVAL INTO p_id_segmento FROM DUAL;
   EXCEPTION
      WHEN OTHERS
      THEN
         LOG.plg_descripcion := 'PKG_CAMPANIAS_CALL.P_SEGMENTAR ';
         LOG.plg_mes_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'mm'));
         LOG.plg_anio_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'yyyy'));
         LOG.plg_usuario_alta := USER;
         LOG.plg_fecha_alta := SYSDATE;
         LOG.plg_observaciones :=
            'Error en el proceso p_segmentar - ' || SQLERRM;
         LOG.plg_hora_fin := SYSDATE;
         pkg_util.p_grabar_log ('I', LOG);
         ROLLBACK;
   END p_segmentar;

   PROCEDURE p_filtrar_base_tk (p_producto     IN            VARCHAR2,
                                con_producto      OUT NOCOPY SYS_REFCURSOR,
                                en_camp           OUT NOCOPY SYS_REFCURSOR,
                                habilitados       OUT NOCOPY SYS_REFCURSOR)
   IS
      v_consulta_producto   VARCHAR2 (32767);
      v_producto            VARCHAR2 (32767);
      v_mes                 VARCHAR2 (2);
      v_dia                 VARCHAR2 (2);
      v_anio                VARCHAR2 (4);
      v_fdesde              DATE;
      v_fhasta              DATE;
   BEGIN
      LOG.plg_hora_inicio := SYSDATE;

      SELECT TO_CHAR (SYSDATE, 'yyyy'),
             TO_CHAR (SYSDATE, 'MM'),
             TO_CHAR (SYSDATE, 'dd')
        INTO v_anio, v_mes, v_dia
        FROM DUAL;

      IF TO_NUMBER (v_dia) BETWEEN 25 AND 31
      THEN
         v_fdesde := TO_DATE (v_anio || v_mes || '25', 'yyyymmdd');
         v_fhasta :=
            TO_DATE (
               v_anio || TO_CHAR (ADD_MONTHS (SYSDATE, 1), 'MM') || '24',
               'yyyymmdd');
      ELSE
         v_fdesde :=
            TO_DATE (
               v_anio || TO_CHAR (ADD_MONTHS (SYSDATE, -1), 'MM') || '25',
               'yyyymmdd');
         v_fhasta := TO_DATE (v_anio || v_mes || '24', 'yyyymmdd');
      END IF;

      DELETE FROM campania_tmp t1
            WHERE EXISTS
                     (SELECT 1
                        FROM mvendtn t2
                       WHERE     t2.vend_documento = t1.documento
                             AND t2.vend_percibe_haberes = 'S');

      DELETE FROM campania_tmp t1
            WHERE NOT EXISTS
                     (SELECT 1
                        FROM mtarje t3
                       WHERE t3.tar_documento = t1.documento);

      DELETE FROM campania_tmp t1
            WHERE EXISTS
                     (SELECT 1
                        FROM mtarje t3
                       WHERE     t3.tar_documento = t1.documento
                             AND (   t3.tar_fecha_baja <> 0
                                  OR t3.tar_estado_listin NOT IN ('SO', ' ')));

      IF p_producto IS NOT NULL
      THEN
         SELECT REPLACE (p_producto, '?pdocumento', 't1.documento')
           INTO v_producto
           FROM DUAL;

         v_consulta_producto :=
               'SELECT DISTINCT t1.documento AS Documento FROM campania_tmp t1 WHERE EXISTS ('
            || v_producto
            || ')';

         OPEN con_producto FOR v_consulta_producto;

         EXECUTE IMMEDIATE
            'DELETE FROM campania_tmp t1 WHERE EXISTS (' || v_producto || ')';
      ELSE
         v_consulta_producto :=
            'SELECT DISTINCT t1.documento AS Documento FROM campania_tmp t1 WHERE 2=1';

         OPEN con_producto FOR v_consulta_producto;
      END IF;

      OPEN en_camp FOR
         SELECT DISTINCT
                t2.base_identificacion AS Documento,
                t1.camp_descripcion AS Descripción_Campaña
           FROM campanias t1, campanias_base t2, campania_tmp t3
          WHERE     t1.camp_fecha_vigencia_desde >= TRUNC (v_fdesde)
                AND t1.camp_fecha_vigencia_hasta <= TRUNC (v_fhasta)
                AND t1.camp_tipocamp_id IN (2, 3)
                AND t2.base_camp_id = t1.camp_id
                AND TRIM (TO_NUMBER (t2.base_identificacion)) = t3.documento;

      DELETE FROM campania_tmp t1
            WHERE EXISTS
                     (SELECT 1
                        FROM campanias t2, campanias_base t3
                       WHERE     t2.camp_fecha_vigencia_desde >=
                                    TRUNC (v_fdesde)
                             AND t2.camp_fecha_vigencia_hasta <=
                                    TRUNC (v_fhasta)
                             AND t2.camp_tipocamp_id IN (2, 3)
                             AND t3.base_camp_id = t2.camp_id
                             AND TRIM (TO_NUMBER (t3.base_identificacion)) =
                                    t1.documento);

      OPEN habilitados FOR
         SELECT DISTINCT t1.documento AS Documento
           FROM campania_tmp t1;
   EXCEPTION
      WHEN OTHERS
      THEN
         LOG.plg_descripcion := 'PKG_CAMPANIAS_CALL.P_FILTRAR_BASE_TK ';
         LOG.plg_mes_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'mm'));
         LOG.plg_anio_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'yyyy'));
         LOG.plg_usuario_alta := USER;
         LOG.plg_fecha_alta := SYSDATE;
         LOG.plg_observaciones :=
            'Error en el proceso p_filtrar_base_tk  - ' || SQLERRM;
         LOG.plg_hora_fin := SYSDATE;
         pkg_util.p_grabar_log ('I', LOG);
   END p_filtrar_base_tk;

   PROCEDURE p_reciclar_campania (p_campania   IN PLS_INTEGER,
                                  p_tipo       IN PLS_INTEGER)
   IS
      CURSOR c_segmentos (pc_campania NUMBER)
      IS
         SELECT *
           FROM campanias_segmentacion t1
          WHERE t1.case_camp_id = pc_campania AND t1.case_barrido IS NULL;

      segmentos_row   campanias_segmentacion%ROWTYPE;
      counter         NUMBER;
      LOG             programas_log%ROWTYPE;
   BEGIN
      LOG.plg_hora_inicio := SYSDATE;

      SELECT CASE
                WHEN MAX (t2.case_barrido) IS NULL THEN 1
                ELSE MAX (t2.case_barrido) + 1
             END
        INTO counter
        FROM campanias_segmentacion t2
       WHERE t2.case_camp_id = p_campania;

      OPEN c_segmentos (p_campania);

      LOOP
         FETCH c_segmentos INTO segmentos_row;

         EXIT WHEN c_segmentos%NOTFOUND;

         UPDATE campanias_segmentacion
            SET case_barrido = counter
          WHERE     case_camp_id = segmentos_row.case_camp_id
                AND case_id = segmentos_row.case_id;
      END LOOP;

      CLOSE c_segmentos;

      UPDATE menp001m
         SET me01_fec_com = 0
       WHERE me01_nro_ref IN (SELECT rel_me01_nro_ref
                                FROM relacion_mensaje_campania
                               WHERE rel_camp_id = p_campania);

      IF p_tipo IS NOT NULL AND p_tipo = 2 OR p_tipo = 3
      THEN
         UPDATE campanias_base t1
            SET t1.base_gestion = 'G'
          WHERE     EXISTS
                       (SELECT 1
                          FROM menp001m t2, relacion_mensaje_campania t3
                         WHERE     t2.me01_nro_doc =
                                      TO_NUMBER (t1.base_identificacion)
                               AND t3.rel_camp_id = t1.base_camp_id
                               AND t2.me01_tip_men IN (96, 97, 99, 100, 101)
                               AND t2.me01_nro_ref = t3.rel_me01_nro_ref)
                AND t1.base_camp_id = p_campania;
      ELSIF p_tipo IS NOT NULL AND p_tipo = 5
      THEN
         UPDATE campanias_base t3
            SET t3.base_gestion = 'G'
          WHERE EXISTS
                   (SELECT t0.*
                      FROM campanias_base t0,
                           relacion_mensaje_campania t1,
                           campanias_no_socios_mensajes t2
                     WHERE     t0.base_camp_id = p_campania
                           AND t0.base_id = t3.base_id
                           AND t1.rel_camp_id = t0.base_camp_id
                           AND t2.cansm_id = t1.rel_me01_nro_ref
                           AND t0.base_cans_id = t2.cansm_cans_id
                           AND t2.cansm_tipificacion  IN (96, 97, 99, 100, 101));
      ELSIF p_tipo IS NOT NULL AND p_tipo = 1
      THEN
         UPDATE campanias_base t1
            SET t1.base_gestion = 'G'
          WHERE     EXISTS
                       (SELECT 1
                          FROM menp001m t2, relacion_mensaje_campania t3
                         WHERE     t2.me01_nro_doc =
                                      TO_NUMBER (t1.base_identificacion)
                               AND t3.rel_camp_id = t1.base_camp_id
                               AND t2.me01_tip_men IN
                                      (102, 103, 104, 105, 109, 111, 112)
                               AND t2.me01_nro_ref = t3.rel_me01_nro_ref)
                AND t1.base_camp_id = p_campania;
      END IF;
   EXCEPTION
      WHEN OTHERS
      THEN
         LOG.plg_descripcion := 'PKG_CAMPANIAS_CALL.P_RECICLAR_CAMPANIA ';
         LOG.plg_mes_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'mm'));
         LOG.plg_anio_cierre := TO_NUMBER (TO_CHAR (SYSDATE, 'yyyy'));
         LOG.plg_usuario_alta := USER;
         LOG.plg_fecha_alta := SYSDATE;
         LOG.plg_observaciones :=
            'Error en el proceso p_reciclar_campania  - ' || SQLERRM;
         LOG.plg_hora_fin := SYSDATE;
         pkg_util.p_grabar_log ('I', LOG);
         ROLLBACK;
   END p_reciclar_campania;

   PROCEDURE p_fecha_renglon (p_periodo IN PLS_INTEGER, p_fecha OUT VARCHAR2)
   IS
   BEGIN
      SELECT TO_CHAR (
                TO_DATE (
                   CAST (
                      (SELECT cr_fecha_prox_vto
                         FROM mcrono
                        WHERE     SUBSTR (
                                     TO_CHAR (
                                        ADD_MONTHS (
                                           TO_DATE (
                                              CAST (
                                                 (cr_fecha_prox_vto) AS CHAR (8)),
                                              'YYYYMMDD'),
                                           0),
                                        'YYYYMMDD'),
                                     1,
                                     4) =
                                     SUBSTR (
                                        TO_CHAR (
                                           ADD_MONTHS (
                                              (SELECT TO_DATE (
                                                         CAST (
                                                            ba_fecha_vencimiento_1 AS CHAR (8)),
                                                         'YYYYMMDD')
                                                 FROM mbase
                                                WHERE ba_numero = 0),
                                              -p_periodo),
                                           'YYYYMMDD'),
                                        1,
                                        4)
                              AND SUBSTR (
                                     TO_CHAR (
                                        ADD_MONTHS (
                                           TO_DATE (
                                              CAST (
                                                 (cr_fecha_prox_vto) AS CHAR (8)),
                                              'YYYYMMDD'),
                                           0),
                                        'YYYYMMDD'),
                                     5,
                                     2) =
                                     SUBSTR (
                                        TO_CHAR (
                                           ADD_MONTHS (
                                              (SELECT TO_DATE (
                                                         CAST (
                                                            ba_fecha_vencimiento_1 AS CHAR (8)),
                                                         'YYYYMMDD')
                                                 FROM mbase
                                                WHERE ba_numero = 0),
                                              -p_periodo),
                                           'YYYYMMDD'),
                                        5,
                                        2)
                              AND cr_nro_cierre = 1) AS CHAR (8)),
                   'YYYYMMDD'),
                'rrrrmmdd')
                AS fecha
        INTO p_fecha
        FROM DUAL;
   EXCEPTION
      WHEN OTHERS
      THEN
         DBMS_OUTPUT.put_line (
            SUBSTR (DBMS_UTILITY.format_error_stack, 1, 200));
   END p_fecha_renglon;
END pkg_campanias_call;
/