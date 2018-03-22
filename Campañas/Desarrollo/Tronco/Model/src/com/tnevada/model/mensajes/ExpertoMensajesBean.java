package com.tnevada.model.mensajes;

import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasBase;
import com.tnevada.model.entidades.CampaniasNoSocios;
import com.tnevada.model.entidades.Consulta;
import com.tnevada.model.entidades.ListaNegraTelefonos;
import com.tnevada.model.entidades.Menp004m;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.Parp006m;
import com.tnevada.model.entidades.Parp008m;
import com.tnevada.model.entidades.RelacionMensajeCampania;
import com.tnevada.model.entidades.ServiciosProductosDTO;
import com.tnevada.model.entidades.TipificacionesMenDetalle;
import com.tnevada.model.entidades.TipificacionesMensajesCall;
import com.tnevada.model.util.Util;

import java.io.FileOutputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.sql.DataSource;

import libreriautilmodel.loggers.LoggerType;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Stateless(name = "ExpertoMensajes", mappedName = "Campania-Model-ExpertoMensajes")
public class ExpertoMensajesBean implements ExpertoMensajesLocal {
    @Resource
    SessionContext context;
    @Resource(name = "jdbc/nevadaCoreDS")
    private DataSource ds;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    /*@Inject @Any
    Event<EventObserver> events;*/

    public ExpertoMensajesBean() {
    }

    public List<Parametros> getParametros() throws MensajesNoResultException {
        try {
            return em.createQuery("select o from Parametros o where o.parCodigo = 'MEN_ORIGEN'").getResultList();
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }
    
    public List<Parametros> getCondicionLaboral() throws MensajesNoResultException {
        try {
            return em.createQuery("select o from Parametros o where o.parCodigo = 'COD_LABORAL'").getResultList();
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }
    
    public List<Parp008m> getEstadoCivil() throws MensajesNoResultException {
        try {
            return em.createQuery("select o from Parp008m o where o.pa08FecBaj = 0").getResultList();
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }
    
    public List<Parp006m> getTipoDocumento() throws MensajesNoResultException {
        try {
            return em.createQuery("select o from Parp006m o where o.pa06FecBaj = 0 and o.pa06TipDoc <> 6").getResultList();
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    } 

    public List<Menp004m> getMenp004m() throws MensajesNoResultException {
        try {
            return em.createQuery("select o from Menp004m o where o.me04FecBaj = 0").getResultList();
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }

    public List<TipificacionesMensajesCall> getTipificaciones() throws MensajesNoResultException {
        try {
            return em.createQuery("select o from TipificacionesMensajesCall o").getResultList();
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }

    public List<TipificacionesMenDetalle> getTipificaciones(String[] acciones,
                                                            Long camp) throws MensajesNoResultException {
        try {
            if(acciones == null){
                String acc[] = {"1", "2"};
                acciones = acc;
            }/*DESARROLLO*/
            StringBuilder s = new StringBuilder();            
            for (int i = 0; i < acciones.length; i++) {
                s.append("((','||t0.acciones||',') like '%,"+acciones[i].toString()+",%')\n");
                if (i != acciones.length - 1) {
                    s.append("or\n");
                }
            }
            
            StringBuilder sb = new StringBuilder();
            sb.append("select t1.*\n");
            sb.append("from Relacion_Tipificacion_Acciones t0, Tipificaciones_Men_Detalle t1\n");
            sb.append("where t1.tipdet_Id = t0.id_Tipificacion\n");
            sb.append("and("+s+")\n");             
            sb.append("and t0.id_Tipo_Camp = ?");
            
            Campanias c = em.find(Campanias.class, camp);
            return em.createNativeQuery(sb.toString(),TipificacionesMenDetalle.class)
                .setParameter(1,c.getTipoCampanias().getTipocampId())
                .getResultList();

        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }
    
    public TipificacionesMenDetalle getTipificaciones(Long id) throws MensajesNoResultException {
        try {            
            return em.find(TipificacionesMenDetalle.class, id);
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }

    public List<String> getGestiones(String user, Long camp, boolean panel) throws MensajesNoResultException {
        List<String> l = new ArrayList<String>();
        try {
            StringBuilder sb = new StringBuilder();         
            sb.append("  SELECT descripcion, SUM (cantidad)\n"); 
            sb.append("    FROM (  SELECT t1.tipmen_descripcion AS descripcion,\n");
            sb.append("                   COUNT (t1.tipmen_id) AS cantidad\n");
            sb.append("              FROM campanias_no_socios_mensajes t0,\n"); 
            sb.append("                   tipificaciones_men t1,\n");
            sb.append("                   tipificaciones_men_detalle t2\n");
            sb.append("             WHERE     cansm_usuario = (SELECT vend_numero\n");
            sb.append("                                          FROM mvendtn\n");
            sb.append("                                         WHERE vend_codigo_usuario = '"+user+"')\n");
            sb.append("                   AND t1.tipmen_id = t2.tipdet_tipmen_id\n");
            sb.append("                   AND t2.tipdet_id = t0.cansm_tipificacion\n");
            sb.append("                   AND TRUNC(cansm_fecha) = TRUNC (SYSDATE)\n");
            sb.append("          GROUP BY t1.tipmen_descripcion\n");
            sb.append("          UNION ALL\n");
            sb.append("            SELECT t4.tipmen_descripcion AS descripcion,\n");
            sb.append("                   COUNT (t3.clasificacion_id) AS cantidad\n");
            sb.append("              FROM vw_consulta_mensajes_campanias t3, tipificaciones_men t4\n");
            sb.append("             WHERE     t4.tipmen_id = t3.clasificacion_id\n");
            sb.append("                   AND t3.fecha = TO_NUMBER (TO_CHAR (SYSDATE, 'yyyymmdd'))\n");
            sb.append("                   AND t3.usuario = '"+user+"'\n");
            sb.append("          GROUP BY t4.tipmen_descripcion)\n");
            sb.append("GROUP BY (descripcion)");
            
            Collection<Object[]> r = em.createNativeQuery(sb.toString()).setParameter(1, user).getResultList();

            for (Object[] v : r) {
                if (panel) {
                    if (v[0] != null)
                        l.add(v[0].toString() + "@" + v[1].toString());
                } else {
                    l.add("<span style=\"font-weight: bold;color:" + Util.colorRandom() + "\">" +
                          (v[0] == null ? "GESTIONES " + v[1].toString() : v[0].toString() + " " + v[1].toString()) +
                          "</span>");
                }
            }
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
        return l.isEmpty() ? null : l;
    }
    
    @Asynchronous
    public Future<String> getVistaMensajesDTO(EventMensajes.CallBack c, String url, Date fechaDesde,
                                                      Date fechaHasta, Long documento, String tipoOrigen,
                                                      Long clasificacionId,
                                                      Campanias campId,Long tipo)throws MensajesNoResultException{
        Connection con = null;
        Statement st = null;
        ResultSet res = null, resNS = null;
        try {
            con = ds.getConnection();
            con.setAutoCommit(false);
            /*st = con.createStatement(
                               ResultSet.TYPE_FORWARD_ONLY,
                               ResultSet.CONCUR_READ_ONLY);*/
            st = con.createStatement();
            //st.setFetchSize(Integer.MAX_VALUE);
            Long fdesde, fhasta;
            fdesde = fechaDesde == null ? Util.convertirDateToLong(fechaHasta) : Util.convertirDateToLong(fechaDesde);
            fhasta = fechaHasta == null ? Util.convertirDateToLong(fechaDesde) : Util.convertirDateToLong(fechaHasta);
            if (fdesde > fhasta) {
                throw new MensajesNoResultException("La fecha desde no debe ser mayor a la fecha hasta.",
                                                    LoggerType.INFO);
            }
            
            StringBuilder sb = new StringBuilder();
            if((tipo != null && tipo == 5) || (campId == null && tipo == null)){
                sb.append("SELECT DISTINCT\n");
                sb.append("       t1.cans_nro_documento AS documento,\n");
                sb.append("       TRIM (t1.cans_nombres) || ',' || TRIM (t1.cans_apellidos) AS nombre,\n");
                sb.append("       DECODE (t2.cansm_codigo_origen, 'P', 'Predictivo', 'Manual')\n");
                sb.append("          AS tipo_origen,\n");
                sb.append("       'No Socios' AS tipo_campania,\n");
                sb.append("       5 AS tipo_campania_id,\n");
                sb.append("       t4.camp_descripcion AS campania,\n");
                sb.append("       'Alta' AS importancia,\n");
                sb.append("       t5.vend_codigo_usuario AS usuario,\n");
                sb.append("       t5.vend_numero AS usuario_legajo,\n");
                sb.append("       t5.vend_nombre AS usuario_nombre,\n");
                sb.append("       TO_NUMBER(TO_CHAR(t2.cansm_fecha,'yyyymmdd')) AS fecha,\n");
                sb.append("       t2.cansm_id AS referencia,\n");
                sb.append("       TO_CHAR (t2.cansm_fecha, 'HH:MI:SS') AS hora_carga,\n");
                sb.append("       (SELECT loc_nombre\n");
                sb.append("          FROM mcoam\n");
                sb.append("         WHERE     loc_sucursal = t5.vend_local_sucu\n");
                sb.append("               AND loc_local = t5.vend_local_salto\n");
                sb.append("               AND loc_digito = t5.vend_local_digi)\n");
                sb.append("          AS local_usuario,\n");
                sb.append("       t5.vend_numero AS visitador_legajo,\n");
                sb.append("       t5.vend_nombre AS visitador_nombre,\n");
                sb.append("       (SELECT tipdet_descripcion\n");
                sb.append("          FROM tipificaciones_men_detalle\n");
                sb.append("         WHERE tipdet_id = t2.cansm_tipificacion)\n");
                sb.append("          AS clasificacion,\n");
                sb.append("       TO_NUMBER(TO_CHAR(t2.cansm_fecha_compromiso,'yyyymmdd')) AS fecha_compromiso,\n");
                sb.append("       t2.cansm_mensaje AS mensaje,\n");
                sb.append("       t4.camp_id AS camp_id,\n"); 
                sb.append("       t2.cansm_codigo_origen AS tipo_origen_id,\n");
                sb.append("       t2.cansm_tipificacion AS clasificacion_id,\n"); 
                sb.append("       t2.cansm_tipificacion AS tipificacion_id\n");
                sb.append("  FROM campanias_no_socios t1,\n");
                sb.append("       campanias_no_socios_mensajes t2,\n");
                sb.append("       campanias_base t3,\n");
                sb.append("       campanias t4,\n");
                sb.append("       mvendtn t5\n");
                sb.append(" WHERE     t1.cans_id = t2.cansm_cans_id\n");
                sb.append("       AND t1.cans_id = t3.base_cans_id\n"); 
                sb.append("       AND t3.base_camp_id = t4.camp_id\n");                
                sb.append("       AND t5.vend_numero = t2.cansm_usuario");
                sb.append("       AND TO_NUMBER(TO_CHAR(t2.cansm_fecha,'yyyymmdd')) BETWEEN " + fdesde + " AND " + fhasta + "\n");
                
                if(campId != null){
                    sb.append("AND t4.camp_id = "+campId.getCampId()+"\n");
                }
                
                if(tipo != null){
                    sb.append("AND t4.camp_tipocamp_id = "+tipo+"\n");
                }
                
                if (tipoOrigen != null && !tipoOrigen.equals("")) {
                    sb.append("AND TRIM(LOWER(t2.cansm_codigo_origen)) = '" + tipoOrigen.toLowerCase().trim() +"' \n");
                }
                if (clasificacionId != null) {
                    sb.append("AND t2.cansm_tipificacion = " + clasificacionId + "\n");
                }
                if (documento != null) {
                    sb.append("AND t1.cans_nro_documento = " + documento + "\n");
                }
                
                if(campId == null && tipo == null){
                    sb.append("UNION ALL\n");
                }
                
            }
            
            if((tipo != null && tipo != 5) || (campId == null && tipo == null)){                       
                sb.append("select t0.* from vw_consulta_mensajes_campanias t0 where 1 = 1\n");
                if (fechaDesde != null || fechaHasta != null) {
                    sb.append("and t0.fecha between " + fdesde + " and " + fhasta + "\n");
                }
                if (documento != null) {
                    sb.append("and t0.documento = " + documento.toString() + "\n");
                }
                if (tipoOrigen != null && !tipoOrigen.equals("")) {
                    sb.append("and trim(lower(t0.tipo_origen_id)) = " + tipoOrigen.toLowerCase().trim() + "\n");
                }
                if (clasificacionId != null) {
                    sb.append("and t0.clasificacion_id = " + clasificacionId + "\n");
                }
                if (campId != null) {
                    sb.append("and t0.camp_id = " + campId.getCampId() + "\n");
                }
                
                if (tipo != null) {
                    sb.append("and t0.tipo_campania_id = " + tipo + "\n");
                }
            }
            //System.out.println(sb.toString());
            res = st.executeQuery(sb.toString());            

            Workbook wb = new XSSFWorkbook();
            short i = 0, h = 0, n = 1, cel = 0;
            int cantidad = 0;
            Sheet sheet = null;
            boolean cabecera = false;
            DataFormat format = wb.createDataFormat();
            CellStyle dateStyle;
            Cell cell;
            dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("m/d/yy"));
            while (res.next()) {
                cantidad++;
                if (h == 0) {
                    sheet = wb.createSheet("Hoja" + n);
                    h++;
                }
                Row row = sheet.createRow(i);
                
                if(!cabecera){
                    row.createCell(cel++).setCellValue("DOCUMENTO");
                    row.createCell(cel++).setCellValue("NOMBRE");
                    row.createCell(cel++).setCellValue("TIPO_ORIGEN");
                    row.createCell(cel++).setCellValue("TIPO_CAMPANIA");
                    row.createCell(cel++).setCellValue("CAMPAÑA");
                    row.createCell(cel++).setCellValue("IMPORTANCIA");
                    row.createCell(cel++).setCellValue("USUARIO");
                    row.createCell(cel++).setCellValue("USUARIO_LEGAJO");
                    row.createCell(cel++).setCellValue("USUARIO_NOMBRE");
                    row.createCell(cel++).setCellValue("FECHA");
                    row.createCell(cel++).setCellValue("REFERENCIA");
                    row.createCell(cel++).setCellValue("HORA_CARGA");
                    row.createCell(cel++).setCellValue("LOCAL_USUARIO");
                    row.createCell(cel++).setCellValue("VISITADOR_LEGAJO");
                    row.createCell(cel++).setCellValue("VISITADOR_NOMBRE");
                    row.createCell(cel++).setCellValue("CLASIFICACION");
                    row.createCell(cel++).setCellValue("FECHA_COMPROMISO");
                    row.createCell(cel++).setCellValue("MENSAJE");
                    cabecera = true;
                    i++;
                    cel = 0;
                    row = sheet.createRow(i);
                }                
                row.createCell(cel++).setCellValue(res.getLong("DOCUMENTO"));
                row.createCell(cel++).setCellValue(res.getString("NOMBRE"));
                row.createCell(cel++).setCellValue(res.getString("TIPO_ORIGEN"));
                row.createCell(cel++).setCellValue(res.getString("TIPO_CAMPANIA"));
                row.createCell(cel++).setCellValue(res.getString("CAMPANIA"));
                row.createCell(cel++).setCellValue(res.getString("IMPORTANCIA"));
                row.createCell(cel++).setCellValue(res.getString("USUARIO"));
                row.createCell(cel++).setCellValue(res.getLong("USUARIO_LEGAJO"));
                row.createCell(cel++).setCellValue(res.getString("USUARIO_NOMBRE"));                
                cell = row.createCell(cel++);                                
                cell.setCellStyle(dateStyle);
                cell.setCellValue(Util.convertirLongToDate(res.getLong("FECHA")));                
                row.createCell(cel++).setCellValue(res.getLong("REFERENCIA"));
                row.createCell(cel++).setCellValue(res.getString("HORA_CARGA"));
                row.createCell(cel++).setCellValue(res.getString("LOCAL_USUARIO"));
                row.createCell(cel++).setCellValue(res.getLong("VISITADOR_LEGAJO"));
                row.createCell(cel++).setCellValue(res.getString("VISITADOR_NOMBRE"));
                row.createCell(cel++).setCellValue(res.getString("CLASIFICACION"));                
                if(Util.convertirLongToDate(res.getLong("FECHA_COMPROMISO"))!=null){
                    cell = row.createCell(cel++);                                
                    cell.setCellStyle(dateStyle);                    
                    cell.setCellValue(Util.convertirLongToDate(res.getLong("FECHA_COMPROMISO")));
                }else{
                    row.createCell(cel++).setCellValue("");
                }                
                row.createCell(cel++).setCellValue(res.getString("MENSAJE"));
                i++;
                cel = 0;
                if (i == 30000) {
                    n++;
                    i = 0;
                    h = 0;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(url);
            wb.write(fileOut);
            fileOut.close();            

            return new AsyncResult<String>(String.valueOf(cantidad));
           // events.fire(new EventObserver("from bean " + System.currentTimeMillis()));
        } catch (Exception e) {       
            e.printStackTrace();
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        } finally {
            try {
                if (!res.isClosed()) {
                    res.close();
                }
                if (!st.isClosed()) {
                    st.close();
                }
                if (!con.isClosed()) {
                    con.close();
                }
            } catch (SQLException e) {
                throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
            }
        }
    }

    public Object getDatos(Long id, String dni) throws MensajesNoResultException {
        try {
            String consulta =
                "select cast(cons_consulta as varchar2(2000)) from consulta\n" + "where trim(cons_nombre) = ?1\n" +
                "and cons_sector = 80\n" + "and trim(cons_tipo) = ?2";

            String resultado = null;
            String sql = null;
            CampaniasBase cb = null;
            
            if(dni==null){
                cb = (CampaniasBase)em.createQuery("SELECT o FROM CampaniasBase o WHERE o.baseId = :id").setParameter("id",id).getSingleResult();
                if(cb.getCampania().getTipoCampanias().getTipocampId()==5L){
                    return cb.getCampaniasNoSocios();                    
                }
            }
            
            if (dni != null || cb.getCampania().getCampEntidadDestino() == 1L /*Titular*/) {
                sql =
                    (String)em.createNativeQuery(consulta).setParameter(1, "DATOS_CLIENTE").setParameter(2,
                                                                                                         "CLIENTE").getSingleResult();
            } else if (cb.getCampania().getCampEntidadDestino() == 2L /*Comercio*/) {
                sql =
                    (String)em.createNativeQuery(consulta).setParameter(1, "DATOS_COMERCIO").setParameter(2,
                                                                                                          "COMERCIO").getSingleResult();

            } else if (cb.getCampania().getCampEntidadDestino() == 5L /*NoCliente*/) {
                return cb.getBaseDatos();
            }

            resultado = (String)em.createNativeQuery(sql).setParameter(1,cb==null?dni:cb.getBaseIdentificacion()).getSingleResult();

            return resultado;
        } catch (Exception e) {            
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }

    public void guardarRelacionMensajeCampania(RelacionMensajeCampania o) throws MensajesException {
        try {
            em.persist(o);
        } catch (Exception e) {
            throw new MensajesException("Error al guardar la Relacion "+Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }
    }
    
    public CampaniasNoSocios mergeNoSocios(CampaniasNoSocios o) throws MensajesException {
        try {
            return em.merge(o);
        } catch (Exception e) {
            throw new MensajesException("Error al guardar No Socios "+Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }
    }
    
    public void actualizarRelacionMensajeCampania(RelacionMensajeCampania o) throws MensajesException {
        try {
            RelacionMensajeCampania r =
                (RelacionMensajeCampania)em.createQuery("select o from RelacionMensajeCampania o where o.relMe01NroRef = :nro and o.relCampId = :campId")
                .setParameter("nro",o.getRelMe01NroRef()).setParameter("campId", o.getRelCampId()) .getSingleResult();
            r.setRelTelefono(o.getRelTelefono());
            em.merge(r);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensajesException("Error al guardar la Relacion "+Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }
    }

    public void guardarListaNegra(ListaNegraTelefonos o) throws MensajesException {
        try {
            ListaNegraTelefonos l = (ListaNegraTelefonos)em.createQuery("select o from ListaNegraTelefonos o where o.telefono = :tel")
                                        .setParameter("tel",o.getTelefono())
                                        .getSingleResult();            
            l.setFecha(new Date());
            em.merge(l);
            
            } catch(NoResultException e){
                em.persist(o);
            }catch (Exception e) {
               throw new MensajesException("Error al guardar la lista negra "+Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }
    }
    
    public List<ServiciosProductosDTO> getServiciosProductos(Integer dni) throws MensajesNoResultException {
        List<ServiciosProductosDTO> lista = new ArrayList<ServiciosProductosDTO>();
        try {
            String sql = getConsulta("ofrecer productos", 83, "cliente");
            Collection<Object[]> resultado = em.createNativeQuery(sql.toString()).setParameter("DOCUMENTO", dni).getResultList();

            for (Object[] v : resultado) {
                lista.add(convertirVector(v));
            }

        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
        return lista;
    }
    
    public Consulta getConsulta(String id) throws MensajesNoResultException {
        try{
            return em.find(Consulta.class, id);
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }
    
    private String getConsulta(String nombre, Integer sector,
                               String tipo) throws MensajesNoResultException {
        try {
            return (String)em.createNativeQuery("SELECT cons_consulta" +
                                                " FROM consulta" +
                                                " WHERE LOWER (TRIM (cons_nombre)) = '" +
                                                nombre.toLowerCase().trim() +
                                                "'" + " AND cons_sector = " +
                                                sector +
                                                " AND LOWER (TRIM (cons_tipo)) = '" +
                                                tipo.toLowerCase().trim() +
                                                "'").getSingleResult();
        } catch (Exception e) {
            throw new MensajesNoResultException(Util.getStackTraceMessage(e), LoggerType.INFO);
        }
    }

    private ServiciosProductosDTO convertirVector(Object[] v) {
        ServiciosProductosDTO sp = new ServiciosProductosDTO();
        sp.setProducto(v[0] == null ? "" : v[0].toString());
        sp.setEstado(v[1] == null ? "" : v[1].toString());
        sp.setAccion(v[2] == null ? "" : v[2].toString());
        sp.setUrl(v[3] == null ? "" : Util.urlConcatena(v[3].toString(),getAmbienteDesarrollo()));
        
        if(sp.getEstado().trim().equalsIgnoreCase("ADHERIDO") || sp.getEstado().trim().equalsIgnoreCase("SI")){
          sp.setColor("Green");
        }
        
        if(sp.getEstado().trim().equalsIgnoreCase("NO ADHERIDO") || sp.getEstado().trim().equalsIgnoreCase("NO")){
          sp.setColor("Red");
        }
        
        return sp;
    }
    
    private String getAmbienteDesarrollo() {
        
        Object resul;
        String ambiente;
        
        String sql = "    select \n" + 
        "       case when upper(trim(sys_context('USERENV', 'CURRENT_SCHEMA'))|| '-' ||\n" + 
        "       substr(trim(sys_context('USERENV', 'SERVER_HOST')),0,7)) = 'PNEVADA-RACPROD'then \n" + 
        "        'PRODUCCION'\n" + 
        "       else\n" + 
        "        'DESARROLLO'\n" + 
        "       end esquema  \n" + 
        "       from dual";
        
        resul = em.createNativeQuery(sql).getSingleResult();

        if(resul != null){
            ambiente = resul.toString();
        }else{
            ambiente = "DESARROLLO";
        }
            
        return ambiente;
    }

}
