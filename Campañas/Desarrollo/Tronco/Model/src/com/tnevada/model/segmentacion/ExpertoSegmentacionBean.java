package com.tnevada.model.segmentacion;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasSegmentacion;
import com.tnevada.model.entidades.SegmentoDTO;
import com.tnevada.model.entidades.SucursalDTO;
import com.tnevada.model.util.Util;

import java.io.FileOutputStream;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import javax.sql.DataSource;

import libreriautilmodel.loggers.LoggerType;

import oracle.jdbc.OracleTypes;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Stateless(name = "ExpertoSegmentacion", mappedName = "Campania-Model-ExpertoSegmentacion")
public class ExpertoSegmentacionBean implements ExpertoSegmentacionLocal, ConsultasSegmentacion {

    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    @Resource(name = "jdbc/nevadaCoreDS")
    private DataSource ds;
    @EJB
    private ExpertoCamapaniasLocal expertoCampania;
    private List<SegmentoDTO> reporte;      

    public ExpertoSegmentacionBean() {
    }

    public List<SucursalDTO> getSucursalesDTO() throws SegmentacionNoResultException {
        try {
            return em.createQuery(SUCURSALES_DTO).getResultList();
        } catch (Exception e) {
            throw new SegmentacionNoResultException(Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }
    }    
    
    private String getSucursales(List sucursales){
        String sucId = "";
        if (sucursales != null && !sucursales.isEmpty()) {                
            for (int i = 0; i < sucursales.size(); i++) {
                sucId += "'" + sucursales.get(i) + "'";
                if (i != sucursales.size() - 1) {
                    sucId += ",";
                }
            }                
        } 
        return sucId;
    }
    
    public String getDatos(List sucursales, String pp, Campanias campania, String resumen, String edad) throws SegmentacionNoResultException {
        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            String suc = getSucursales(sucursales);            
            conn = ds.getConnection();
            cstmt = conn.prepareCall("BEGIN PKG_CAMPANIAS_CALL.P_BASE ( ?, ?, ?, ?, ?, ?, ? ); END;");           
            cstmt.setString(1, suc.equals("")?null:suc);
            cstmt.setString(2, pp);
            cstmt.setLong(3, campania.getCampId());
            cstmt.setLong(4, campania.getTipoCampanias().getTipocampId());
            cstmt.setString(5, edad);
            cstmt.setString(6, resumen);            
            cstmt.registerOutParameter(7, OracleTypes.VARCHAR); 
            cstmt.execute();
            return (String)cstmt.getObject(7);
            
        } catch (Exception e) {
            throw new SegmentacionNoResultException(Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
                if(cstmt!=null){
                    cstmt.close();
                }
            }catch(SQLException e){
                throw new SegmentacionNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
            }catch(Exception e){
                throw new SegmentacionNoResultException(Util.getStackTraceMessage(e), LoggerType.SEVERE);    
            }
        }        
    }
    
    public Long segmentar(int cantidad, String usuario, List sucursales, String pp, Campanias campania, String url, String edades, String resumen) throws SegmentacionException {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {                        
            String suc = getSucursales(sucursales);
            conn = ds.getConnection();              
            cstmt = conn.prepareCall("BEGIN PKG_CAMPANIAS_CALL.P_SEGMENTAR (?,?,?,?,?,?,?,?,?,?); END;");
            cstmt.setInt(1, cantidad);
            cstmt.setString(2, pp);
            cstmt.setLong(3, campania.getCampId());
            cstmt.setLong(4, expertoCampania.getLegajoPorNombreDeUsuario(usuario));
            cstmt.setString(5, campania.getCampDescripcion());            
            cstmt.setString(6, suc.equals("")?null:suc);    
            cstmt.setLong(7, campania.getTipoCampanias().getTipocampId());
            cstmt.setString(8, edades);
            cstmt.setString(9, resumen);
            cstmt.registerOutParameter(10, OracleTypes.NUMBER); 
            cstmt.execute();           

            getSegmentacionBase(((BigDecimal)cstmt.getObject(10)).longValue(),url,campania.getTipoCampanias().getTipocampId());  
            return ((BigDecimal)cstmt.getObject(10)).longValue();
            
        } catch (Exception e) {                        
            throw new SegmentacionException(Util.getStackTraceMessage(e), LoggerType.SEVERE);            
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
                if(cstmt!=null){
                    cstmt.close();
                }
            }catch(SQLException e){
                throw new SegmentacionException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
            }catch(Exception e){
                throw new SegmentacionException(Util.getStackTraceMessage(e), LoggerType.SEVERE);    
            }
        }
    }
    
    public void reciclarCampania(Campanias campania) throws SegmentacionException {
        Connection conn = null;
        CallableStatement cstmt = null;
        try {
        
            conn = ds.getConnection();
            cstmt = conn.prepareCall("BEGIN PKG_CAMPANIAS_CALL.P_RECICLAR_CAMPANIA (?,?); END;");
            cstmt.setLong(1, campania.getCampId());
            cstmt.setLong(2, campania.getTipoCampanias().getTipocampId());
            cstmt.execute();
            
        } catch (Exception e) {
            throw new SegmentacionException(Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }finally{
            try{
                if(conn!=null){
                    conn.close();
                }
                if(cstmt!=null){
                    cstmt.close();
                }
            }catch(SQLException e){
                throw new SegmentacionException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
            }catch(Exception e){
                throw new SegmentacionException(Util.getStackTraceMessage(e), LoggerType.SEVERE);    
            }
        }
    }

    private void generarExcel(String url, Long tipoCamp) throws Exception {
        try {
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Base");
            short i = 1, c = 0;
            Row cabecera = sheet.createRow(0);
            if(tipoCamp == 5L){
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
                for (SegmentoDTO o:reporte) {
                    Row row = sheet.createRow(i);                            
                    row.createCell(c++).setCellValue(o.getBaseId());                                    
                    row.createCell(c++).setCellValue(o.getCansNroDocumento() ==null?"":o.getCansNroDocumento().toString());                    
                    row.createCell(c++).setCellValue(o.getCansNombres());
                    row.createCell(c++).setCellValue(o.getCansApellidos());                    
                    row.createCell(c++).setCellValue(o.getCansNroTel1()==null?"":o.getCansNroTel1().toString());                                        
                    row.createCell(c++).setCellValue(o.getCansNroTel2()==null?"":o.getCansNroTel2().toString());                                                            
                    row.createCell(c++).setCellValue(o.getCansNroTel3()==null?"":o.getCansNroTel3().toString());                                        
                    row.createCell(c++).setCellValue(o.getCansNroTel4()==null?"":o.getCansNroTel4().toString());
                    row.createCell(c++).setCellValue(o.getCansProvincia()==null?"":o.getCansProvincia().toString());
                    row.createCell(c++).setCellValue(o.getCansSexo()==null?"":o.getCansSexo().toString());                                        
                    row.createCell(c++).setCellValue(o.getCansDomicilio()==null?"":o.getCansDomicilio().toString()); 
                    c = 0;
                    i++;                            
                }
            }else{
                cabecera.createCell(0);
                cabecera.createCell(0).setCellValue("Documentos");
                for (SegmentoDTO o : reporte) {
                    Row row = sheet.createRow(i);
                    row.createCell(0);
                    row.createCell(0).setCellValue(o.getDni());
                    i++;
                }
            }
            reporte.clear();
            FileOutputStream fileOut = new FileOutputStream(url);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<CampaniasSegmentacion> getSegmentacion(Campanias c, Date fdesde,
                                                       Date fhasta) throws SegmentacionNoResultException {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT o FROM CampaniasSegmentacion o WHERE o.campId =:camp\n");
            if (fdesde != null && fhasta != null) {
                sb.append("AND o.fecha BETWEEN :fdesde AND :fhasta ORDER BY o.fecha DESC");
                return em.createQuery(sb.toString()).setParameter("fdesde", fdesde)
                                                    .setParameter("fhasta",fhasta)
                                                    .setParameter("camp",c.getCampId()).getResultList();
            }

            return em.createQuery("SELECT o FROM CampaniasSegmentacion o WHERE o.campId =:camp ORDER BY o.fecha DESC")
                     .setParameter("camp",c.getCampId()).getResultList();
        } catch (Exception e) {
            throw new SegmentacionNoResultException(Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }
    }

    public void getSegmentacionBase(Long segmento, String url, Long tipoCamp) throws SegmentacionNoResultException {
        try {
            reporte = new ArrayList<SegmentoDTO>();
            if(tipoCamp == 5L){
                Collection<Object[]> r = em.createNativeQuery("  SELECT t1.base_id,\n" + 
                                                            "         t2.cans_nro_documento,\n" + 
                                                            "         t2.cans_nombres,\n" + 
                                                            "         t2.cans_apellidos,\n" + 
                                                            "         t2.cans_nro_tel_1,\n" + 
                                                            "         t2.cans_nro_tel_2,\n" + 
                                                            "         t2.cans_nro_tel_3,\n" + 
                                                            "         t2.cans_nro_tel_4,\n" + 
                                                            "         t2.cans_provincia,\n" + 
                                                            "         t2.cans_sexo,\n" + 
                                                            "         t2.cans_domicilio\n" + 
                                                            "    FROM campanias_segmentacion_base t0,\n" + 
                                                            "         campanias_base t1,\n" + 
                                                            "         campanias_no_socios t2\n" + 
                                                            "   WHERE     t0.caseb_base_id = t1.base_id\n" + 
                                                            "         AND t0.caseb_case_id = ?segmento\n" + 
                                                            "         AND T2.CANS_ID = T1.BASE_CANS_ID\n" + 
                                                            "ORDER BY t0.caseb_orden ASC").setParameter("segmento",segmento).getResultList();
                for (Object[] v : r) {
                    SegmentoDTO s = new SegmentoDTO();
                    s.setBaseId(((BigDecimal)v[0]).longValue());
                    s.setCansNroDocumento(v[1]==null?"":v[1].toString());
                    s.setCansNombres(v[2]==null?"":v[2].toString());
                    s.setCansApellidos(v[3]==null?"":v[3].toString());
                    s.setCansNroTel1(v[4]==null?"":((BigDecimal)v[4]).toString());
                    s.setCansNroTel2(v[5]==null?"":((BigDecimal)v[5]).toString());
                    s.setCansNroTel3(v[6]==null?"":((BigDecimal)v[6]).toString());
                    s.setCansNroTel4(v[7]==null?"":((BigDecimal)v[7]).toString());
                    s.setCansProvincia(v[8]==null?"":v[8].toString());
                    s.setCansSexo(v[9]==null?"":v[9].toString());
                    s.setCansDomicilio(v[10]==null?"":v[10].toString());
                    reporte.add(s);
                }
            }else{
                Collection<BigDecimal> r = em.createNativeQuery("  SELECT TO_NUMBER (TRIM (base_identificacion))\n" + 
                                                                "    FROM campanias_segmentacion_base t0, campanias_base t1\n" + 
                                                                "   WHERE t0.caseb_base_id = t1.base_id AND t0.caseb_case_id = ?segmento\n" + 
                                                                "ORDER BY t0.caseb_orden ASC").setParameter("segmento",segmento).getResultList();
                for (BigDecimal v : r) {
                    if(v!=null){                    
                        reporte.add(new SegmentoDTO((v).longValue(), null, null, null, null, null, null));
                    }
                }
            }
            generarExcel(url,tipoCamp);
        } catch (Exception e) {
            throw new SegmentacionNoResultException(Util.getStackTraceMessage(e), LoggerType.SEVERE);
        }
    }
}
