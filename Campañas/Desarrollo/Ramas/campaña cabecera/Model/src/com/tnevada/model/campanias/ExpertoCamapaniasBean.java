package com.tnevada.model.campanias;

import com.tnevada.model.AbstractFacade;
import com.tnevada.model.entidades.BaseFiltradaDTO;
import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasBase;
import com.tnevada.model.entidades.ConsultaDTO;
import com.tnevada.model.entidades.Parametros;
import com.tnevada.model.entidades.TipoCampanias;
import com.tnevada.model.util.Util;

import java.io.FileOutputStream;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import javax.sql.DataSource;

import libreriautilmodel.loggers.LoggerType;

import oracle.jdbc.OracleTypes;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@Stateless(name = "ExpertoCamapanias", mappedName = "Campania-Model-ExpertoCamapanias")
public class ExpertoCamapaniasBean extends AbstractFacade<Campanias> implements ExpertoCamapaniasLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    @Resource(name = "jdbc/nevadaCoreDS")
    private DataSource ds;          
    private Set id = new HashSet();

    public ExpertoCamapaniasBean() {
        super(Campanias.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    /**
     * Listar los renglones de las campañas     
     * @return List Parametros
     * @throws
     */
    public List<Parametros> getRenglones()throws CampaniasNoResultException{
        try {
            return em.createQuery("select o from Parametros o where o.parCodigo = 'RENGLON_CAMPANIA'").getResultList();
        } catch (Exception e) {
            throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }        
    }
    
    /**
     * Listar los productos de las campañas de Marketing
     * @return List Parametros
     * @throws
     */
    public List<ConsultaDTO> getProductos()throws CampaniasNoResultException{
        try {
            List<ConsultaDTO> l = new ArrayList<ConsultaDTO>();
            Collection<Object[]> c =  em.createNativeQuery("SELECT cons_id, cons_nombre, cons_consulta\n" + 
            "  FROM consulta\n" + 
            " WHERE cons_tipo = 'CAMPANIAS'").getResultList();
            for(Object[] o:c){
                l.add(new ConsultaDTO((String)o[0],(String)o[1],(String)o[2]));
            }
            return l;
        } catch (Exception e) {
            throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }
    }

    /**
     * Listar las Campanias cargadas
     * @param p0 descripcion, p1 tipo, p2 fecha desde, p3 fecha hasta
     * @return List Campanias
     * @throws
     */
    public List<Campanias> find(String p0, Long p1, Date p2, Date p3, String p4, boolean todas)throws CampaniasNoResultException {
        try {

            StringBuilder sb = new StringBuilder();
            sb.append("select * from campanias where 1=1\n");

            if (p0 != null && !p0.equalsIgnoreCase("")) {
                sb.append("and trim(lower(camp_descripcion)) like '%" + p0.toLowerCase().trim() + "%'\n");
            }
            if (p1 != null) {
                sb.append("and camp_tipocamp_id = " + p1 + "\n");
            }
            if (p2 != null) {
                sb.append("and to_number(to_char(camp_fecha_vigencia_desde,'yyyymmdd')) >= " +
                          Util.convertirDateToLong(p2) + "\n");
            }
            if (p3 != null) {
                sb.append("and to_number(to_char(camp_fecha_vigencia_hasta,'yyyymmdd')) <= " +
                          Util.convertirDateToLong(p3) + "\n");
            }
            
            if (p4 != null) {
                sb.append("and exists(select 1 from campanias_base where base_id = " +p4 + " and base_camp_id = camp_id)\n");
            }
            
            if (p1 == null && p2 == null && !todas) {
                sb.append("and to_number(to_char(sysdate,'yyyymmdd'))\n"); 
                sb.append("between to_number(to_char(camp_fecha_vigencia_desde,'yyyymmdd'))\n"); 
                sb.append("and to_number(to_char(camp_fecha_vigencia_hasta,'yyyymmdd'))\n");
            }
            sb.append("order by camp_fecha_alta desc\n");

            return em.createNativeQuery(sb.toString(), Campanias.class).getResultList();

        } catch (Exception e) {
            throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }
    }

    /**
     * Listar TipoCampanias
     * @return List TipoCampanias
     * @throws
     */
    public List<TipoCampanias> getTipoCampanias()throws CampaniasNoResultException{
        try {
            return em.createQuery("select o from TipoCampanias o").getResultList();
        } catch (Exception e) {
            throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }        
    }
    
    /**
     * Numero de Leagajo (Sistemas)
     * @return Long
     * @throws
     */
    public Long getLegajoPorNombreDeUsuario(String usuario)throws CampaniasNoResultException{
        try {
            String sql = "SELECT vend_numero FROM MVENDTN  WHERE TRIM( vend_codigo_usuario ) = ?p0";

            Query query = em.createNativeQuery(sql);
            query.setParameter("p0", usuario);

            return ((BigDecimal)query.getSingleResult()).longValue();

        } catch (Exception e) {
            throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }
    }
    
    /**
     * filtrarBaseClientes
     * @return List<BaseFiltradaDTO>
     * @throws
     */
    public List<BaseFiltradaDTO> filtrarBaseClientes(HashSet<Long> documentos,Campanias campania,ConsultaDTO consulta,String path)throws CampaniasNoResultException{        
        Connection conn = null;
        CallableStatement cstmt = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ResultSetMetaData rsm = null;
        int columnas = 0;                
        Collection<Object[]> c = new ArrayList<Object[]>();                   
        List<BaseFiltradaDTO> retorno = new ArrayList<BaseFiltradaDTO>();        
        try{                        
            if(campania.getTipoCampanias().getTipocampId() == 2){                
                conn = ds.getConnection();                
                pst = conn.prepareStatement("INSERT INTO CAMPANIA_TMP (DOCUMENTO) VALUES (?)");
                //pst = conn.prepareStatement("INSERT INTO DNI (DOCUMENTO) VALUES (?)");
                for(Long o:documentos){
                    pst.setLong(1, o);
                    pst.addBatch();                    
                }
                pst.executeBatch();
               
                cstmt = conn.prepareCall("BEGIN PKG_CAMPANIAS_CALL_V1.P_FILTRAR_BASE_TK (?,?,?,?); END;");                            
                cstmt.setString(1, consulta.getQuery());
                cstmt.registerOutParameter(2, OracleTypes.CURSOR);
                cstmt.registerOutParameter(3, OracleTypes.CURSOR);
                cstmt.registerOutParameter(4, OracleTypes.CURSOR);
                cstmt.execute();
                rs = (ResultSet)cstmt.getObject(2);
                rsm = rs.getMetaData();
                columnas = rsm.getColumnCount();
                id.clear();
                while(rs.next()){                                        
                    Object[] o = new Object[columnas];
                    o[0] = rs.getObject(1);
                    c.add(o);  
                    id.add(((BigDecimal)o[0]).toString());
                }
                if(c.size()>0){
                    generarExcelBaseFiltrada(c, path+"ConProducto.xlsx", columnas, rsm);     
                    c.clear();
                    Set s = new HashSet(id);
                    retorno.add(new BaseFiltradaDTO(false,s,"Clientes que poseen el producto",path+"ConProducto.xlsx",id.size()));
                    id.clear();
                }
                rs = (ResultSet)cstmt.getObject(3);
                rsm = rs.getMetaData();
                columnas = rsm.getColumnCount();                
                while(rs.next()){                    
                    Object[] o = new Object[columnas];
                    o[0] = rs.getObject(1);
                    o[1] = rs.getObject(2);
                    c.add(o);      
                    id.add(o[0]);
                }
                if(c.size()>0){
                    generarExcelBaseFiltrada(c, path+"EnCampania.xlsx", columnas, rsm);                                     
                    c.clear();
                    Set s = new HashSet(id);
                    retorno.add(new BaseFiltradaDTO(false,s,"Clientes en otras campañas vigentes",path+"EnCampania.xlsx",id.size()));
                    id.clear();
                }
                rs = (ResultSet)cstmt.getObject(4);
                rsm = rs.getMetaData();
                columnas = rsm.getColumnCount();                
                while(rs.next()){                    
                    Object[] o = new Object[columnas];
                    o[0] = rs.getObject(1);
                    id.add(((BigDecimal)o[0]).toString());
                }                  
            }
            return retorno;
        }catch(Exception e){
            e.printStackTrace();
            throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }finally{
            try{
                if(conn != null){
                    conn.close();
                }
                if(cstmt != null){
                    cstmt.close();
                }
                if(pst != null){
                    pst.close();
                }
                if(rs != null){
                    rs.close();
                }            
            }catch(Exception e){
                e.printStackTrace();
            }
        }            
    }
    
    /**
     * Cargar la base de clientes
     * @return void
     * @throws
     */
    public void cargarBaseClientes(HashSet<Long> documentos,Campanias campania, List<BaseFiltradaDTO> preFiltro)throws CampaniasException{
        List<CampaniasBase> l = new ArrayList<CampaniasBase>();    
        try{           
            if(documentos == null && preFiltro != null && !preFiltro.isEmpty()){                
                for(BaseFiltradaDTO o:preFiltro){                    
                    if(o.isIncluir()){
                        for (Iterator it = o.getDocumentos().iterator(); it.hasNext();) {                                                
                            CampaniasBase cb = new CampaniasBase();
                            cb.setCampania(campania);
                            cb.setBaseIdentificacion((String)it.next());
                            cb.setBaseNombre("BaseClientes.xls");
                            l.add(cb);
                        }
                    }                       
                }
                
                for (Iterator it = id.iterator(); it.hasNext();) {
                    CampaniasBase cb = new CampaniasBase();
                    cb.setCampania(campania);
                    cb.setBaseIdentificacion((String)it.next());
                    cb.setBaseNombre("BaseClientes.xls");
                    l.add(cb);
                }
                id.clear();
            }else{            
                for(Long o:documentos){
                    CampaniasBase cb = new CampaniasBase();
                    cb.setCampania(campania);
                    cb.setBaseIdentificacion(o.toString());
                    cb.setBaseNombre("BaseClientes.xls");
                    l.add(cb);
                }
            }
            
            campania.setCampaniaBase(l);
            campania.setCampCantidadClientes(((Integer)l.size()).longValue());
            create(campania);      
            em.flush();
            em.refresh(campania);
            
        }catch(Exception e){
            e.printStackTrace();
            throw new CampaniasException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }
    }
    
    /**
     * Retorna la fecha para la cartera
     * @return String
     * @throws
     */
    public String getFechaCartera(Long periodo)throws CampaniasNoResultException{
        Connection conn = null;
        CallableStatement cstmt = null;
        try {            
            conn = ds.getConnection();            
            cstmt = conn.prepareCall("BEGIN PKG_CAMPANIAS_CALL_V1.P_FECHA_RENGLON (?, ?); END;");
            cstmt.setLong(1, periodo);
            cstmt.registerOutParameter(2, OracleTypes.VARCHAR); 
            cstmt.execute();
            
            return (String)cstmt.getObject(2);
            
        } catch (Exception e) {
            throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }finally{
            try{
                if(conn!=null /*&& !conn.isClosed()*/){
                    conn.close();
                }
                if(cstmt!=null /*&& !cstmt.isClosed()*/){
                    cstmt.close();
                }
            }catch(SQLException e){
                throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
            }catch(Exception e){
                throw new CampaniasNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
            }
        }
    }
    
    /**
     * Elimina todas las dependencias de una Campaña
     * @return void
     * @throws
     */
    public void eliminarCampaniaDependencias(Campanias c)throws CampaniasException{
        try {                        
            
            em.createNativeQuery("DELETE FROM campanias_segmentacion_base t1\n" + 
            "      WHERE t1.caseb_case_id IN (SELECT t2.case_id\n" + 
            "                                   FROM campanias_segmentacion t2\n" + 
            "                                  WHERE t2.case_camp_id = ?campId)").setParameter("campId", c.getCampId()).executeUpdate();
            
            em.createNativeQuery("DELETE FROM campanias_segmentacion\n" + 
            "      WHERE case_camp_id = ?campId").setParameter("campId", c.getCampId()).executeUpdate();
            
            em.createNativeQuery("DELETE FROM relacion_mensaje_campania t1\n" + 
            "      WHERE t1.rel_camp_id = ?campId").setParameter("campId", c.getCampId()).executeUpdate();
            
        } catch (Exception e) {
            throw new CampaniasException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }
    }
    
    private void generarExcelBaseFiltrada(Collection<Object[]> coleccion, String path, int columnas, ResultSetMetaData rsm){
        try{
            Workbook wb = new XSSFWorkbook();
            short i = 0, h = 0, n = 1, cel = 0, j = 0, c = 0;
            Sheet sheet = null;
            DataFormat format = wb.createDataFormat();
            CellStyle dateStyle;
            dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(format.getFormat("m/d/yy"));            
            sheet = wb.createSheet("Hoja" + n);                    
            
            Row cabecera = sheet.createRow(i);
            do{                                
                cabecera.createCell(cel++).setCellValue(rsm.getColumnName(++c));                                                
            }while(c!=columnas);
            cel = 0;
            i++;
            
            for(Object[] o:coleccion){                
                
                Row row = sheet.createRow(i);
                while(j!=columnas){                
                    row.createCell(cel++).setCellValue(o[j++].toString());                                                
                }
                j = 0;
                cel = 0;
                i++;                
            }
                        
            FileOutputStream fileOut = new FileOutputStream(path);
            wb.write(fileOut);
            fileOut.close(); 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
