package com.tnevada.model.mensajes;

import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasBase;
import com.tnevada.model.entidades.ListaNegraTelefonos;
import com.tnevada.model.entidades.Menp004m;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.RelacionMensajeCampania;
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
            }
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

    public List<String> getGestiones(String user, Long camp, boolean panel) throws MensajesNoResultException {
        List<String> l = new ArrayList<String>();
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("select t1.tipmen_descripcion,\n");
            sb.append("count(t0.clasificacion_id)\n");
            sb.append("from vw_consulta_mensajes_campanias t0,tipificaciones_men t1\n");
            sb.append("where t1.tipmen_id = t0.clasificacion_id\n");
            if (!panel) {
                sb.append("and t0.fecha = to_number(to_char(sysdate,'yyyymmdd'))\n");
            }
            if (user != null) {
                sb.append("and t0.usuario = '" + user + "'\n");
            }
            if (camp != null) {
                sb.append("and camp_id = " + camp + "\n");
            }
            sb.append("group by rollup (t1.tipmen_descripcion)");
            
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
                                                      Long campId)throws MensajesNoResultException{
        Connection con = null;
        Statement st = null;
        ResultSet res = null;
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
            sb.append("select t0.* from vw_consulta_mensajes_campanias t0 where 1 = 1\n");
            if (fechaDesde != null || fechaHasta != null) {
                sb.append("and t0.fecha between " + fdesde + " and " + fhasta + "\n");
            }
            if (documento != null) {
                sb.append("and t0.documento = " + documento.toString() + "\n");
            }
            if (tipoOrigen != null && tipoOrigen.equals("")) {
                sb.append("and trim(lower(t0.tipo_origen_id)) = " + tipoOrigen.toLowerCase().trim() + "\n");
            }
            if (clasificacionId != null) {
                sb.append("and t0.clasificacion_id = " + clasificacionId + "\n");
            }
            if (campId != null) {
                sb.append("and t0.camp_id = " + campId + "\n");
            }
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
                    row.createCell(cel++).setCellValue("CAMPANIA");
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

    public String getDatos(Long id, String dni) throws MensajesNoResultException {
        try {
            String consulta =
                "select cast(cons_consulta as varchar2(2000)) from consulta\n" + "where trim(cons_nombre) = ?1\n" +
                "and cons_sector = 80\n" + "and trim(cons_tipo) = ?2";

            String resultado = null;
            String sql = null;
            CampaniasBase cb = null;
            
            if(dni==null){
                cb = (CampaniasBase)em.createQuery("select o from CampaniasBase o where o.baseId = :id").setParameter("id",id).getSingleResult();
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
            throw new MensajesException("Error al guardar la Relacion", LoggerType.SEVERE);
        }
    }
    
    public void actualizarRelacionMensajeCampania(RelacionMensajeCampania o) throws MensajesException {
        try {
            RelacionMensajeCampania r =
                (RelacionMensajeCampania)em.createQuery("select o from RelacionMensajeCampania o where o.relMe01NroRef = :nro")
                .setParameter("nro",o.getRelMe01NroRef()).getSingleResult();
            r.setRelTelefono(o.getRelTelefono());
            em.merge(r);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MensajesException("Error al guardar la Relacion", LoggerType.SEVERE);
        }
    }

    public void guardarListaNegra(ListaNegraTelefonos o) throws MensajesException {
        try {
            em.persist(o);
        } catch (Exception e) {
            throw new MensajesException("Error al guardar la lista negra", LoggerType.SEVERE);
        }
    }
}
