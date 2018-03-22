package com.tnevada.model.predictivo;

import com.tnevada.model.entidades.Campanias;
import com.tnevada.model.entidades.CampaniasSegmentacion;
import com.tnevada.model.entidades.TipoDeTelefono;
import com.tnevada.model.util.Util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import java.util.List;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.sql.DataSource;

import libreriautilmodel.loggers.LoggerType;

@Stateless(name = "ExpertoPredictivo", mappedName = "Campania-Model-ExpertoPredictivo")
public class ExpertoPredictivoBean implements ExpertoPredictivoLocal {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    @Resource(name = "jdbc/nevadaCoreDS")
    private DataSource ds;

    public ExpertoPredictivoBean() {
    }
    
    public List<TipoDeTelefono> getTipoDeTelefonos() throws PredictivoNoResultException {
        try {
            return em.createQuery("select t from TipoDeTelefono t").getResultList();            
        } catch (Exception e) {            
            throw new PredictivoNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }
    }
    
    public void generarPredictivo(File path,Long segmento,List<TipoDeTelefono> telefonos) throws PredictivoNoResultException{
        try{
            
            Connection conn = null;
            Statement st = null;
            ResultSet res = null;
            conn = ds.getConnection(); 
            Campanias campania = em.find(Campanias.class, 
                                ((CampaniasSegmentacion)em.find(CampaniasSegmentacion.class, segmento)).getCampId());
            boolean mora = campania.getTipoCampanias().getTipocampId()==1L?true:false;
            StringBuilder sb = new StringBuilder(); 
            File archivoTxt = new File (path,"ivr.txt");                
            FileWriter fw = new FileWriter(archivoTxt);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter salida = new PrintWriter(bw);
            StringBuilder fila = new StringBuilder();            
            if(campania.getTipoCampanias().getTipocampId()==5L){                                
                sb.append("SELECT CASE\n");
                sb.append("          WHEN t2.cans_nro_tel_1 IS NULL\n");
                sb.append("          THEN\n");
                sb.append("             NULL\n");
                sb.append("          WHEN t2.cans_nro_tel_1 LIKE '261%'\n");
                sb.append("          THEN\n");
                sb.append("             SUBSTR (t2.cans_nro_tel_1, 4, LENGTH (t2.cans_nro_tel_1))\n");
                sb.append("          WHEN t2.cans_nro_tel_1 LIKE '15%'\n");
                sb.append("          THEN\n");
                sb.append("             TO_CHAR (t2.cans_nro_tel_1)\n");
                sb.append("          ELSE\n");
                sb.append("             '0' || TO_CHAR (t2.cans_nro_tel_1)\n");
                sb.append("       END\n");
                sb.append("          tel_1,\n");
                sb.append("       CASE\n");
                sb.append("          WHEN t2.cans_nro_tel_2 IS NULL\n");
                sb.append("          THEN\n");
                sb.append("             NULL\n");
                sb.append("          WHEN t2.cans_nro_tel_2 LIKE '261%'\n");
                sb.append("          THEN\n");
                sb.append("             SUBSTR (t2.cans_nro_tel_2, 4, LENGTH (t2.cans_nro_tel_2))\n");
                sb.append("          WHEN t2.cans_nro_tel_2 LIKE '15%'\n");
                sb.append("          THEN\n");
                sb.append("             TO_CHAR (t2.cans_nro_tel_2)\n");
                sb.append("          ELSE\n");
                sb.append("             '0' || TO_CHAR (t2.cans_nro_tel_2)\n");
                sb.append("       END\n");
                sb.append("          tel_2,\n");
                sb.append("       CASE\n");
                sb.append("          WHEN t2.cans_nro_tel_3 IS NULL\n");
                sb.append("          THEN\n");
                sb.append("             NULL\n");
                sb.append("          WHEN t2.cans_nro_tel_3 LIKE '261%'\n");
                sb.append("          THEN\n");
                sb.append("             SUBSTR (t2.cans_nro_tel_3, 4, LENGTH (t2.cans_nro_tel_3))\n");
                sb.append("          WHEN t2.cans_nro_tel_3 LIKE '15%'\n");
                sb.append("          THEN\n");
                sb.append("             TO_CHAR (t2.cans_nro_tel_3)\n");
                sb.append("          ELSE\n");
                sb.append("             '0' || TO_CHAR (t2.cans_nro_tel_3)\n");
                sb.append("       END\n");
                sb.append("          tel_3,\n");
                sb.append("       CASE\n");
                sb.append("          WHEN t2.cans_nro_tel_4 IS NULL\n");
                sb.append("          THEN\n");
                sb.append("             NULL\n");
                sb.append("          WHEN t2.cans_nro_tel_4 LIKE '261%'\n");
                sb.append("          THEN\n");
                sb.append("             SUBSTR (t2.cans_nro_tel_4, 4, LENGTH (t2.cans_nro_tel_4))\n");
                sb.append("          WHEN t2.cans_nro_tel_4 LIKE '15%'\n");
                sb.append("          THEN\n");
                sb.append("             TO_CHAR (t2.cans_nro_tel_4)\n");
                sb.append("          ELSE\n");
                sb.append("             '0' || TO_CHAR (t2.cans_nro_tel_4)\n");
                sb.append("       END tel_4,\n");
                sb.append("       t1.base_id AS id,\n");
                sb.append("       t2.cans_nro_documento AS dni,\n");
                sb.append("       TRIM (t2.cans_nombres) || ' ' || TRIM (t2.cans_apellidos) AS nombre,\n");
                sb.append("       'NS' AS tipo\n");
                sb.append("  FROM campanias_segmentacion_base t0,\n");
                sb.append("       campanias_base t1,\n");
                sb.append("       campanias_no_socios t2\n");
                sb.append(" WHERE     t0.caseb_base_id = t1.base_id\n");
                sb.append("       AND t0.caseb_case_id = "+segmento+"\n");
                sb.append("       AND t2.cans_id = t1.base_cans_id");
            }else{
                sb.append("SELECT\n");
                for(TipoDeTelefono t:telefonos){
                    sb.append("(SELECT CASE WHEN cl11_cod_are = '0261' OR cl11_cod_are = '0' THEN \n");
                    sb.append("TO_CHAR (cl11_nro_tel) \n");
                    sb.append("ELSE\n");
                    sb.append("TRIM (cl11_cod_are) || TO_CHAR (cl11_nro_tel) \n");
                    sb.append("END\n");
                    sb.append("FROM clip011m\n");
                    sb.append("WHERE cl11_nro_doc = tar_documento \n");
                    sb.append("AND cl11_cod_tel = "+t.getCodigo()+" \n");
                    sb.append("AND cl11_mar_ver = 1 \n");                                
                    sb.append("AND cl11_tel_err = 0) AS "+replaceColumn(t.getDescripcion())+",");                
                }            
                if(mora){
                    sb.append("base_id id,\n");
                    sb.append("tar_documento dni,\n");            
                    sb.append("TRIM (tar_nom) || ' ' || TRIM (tar_ape) nombre,\n");
                    sb.append("'(LISTIN:' || tar_estado_listin || ')' listin,\n");
                    sb.append("'(deuda_mora:$' || deuda_mora || ')' deuda_mora,\n"); 
                    sb.append("'(deuda_venc:$' || deuda_venc || ')' deuda_venc,\n");
                    sb.append("'(deuda_futura:$' || deuda_futura || ')' deuda_futura,\n");
                    sb.append("'(deuda_total:$' || deuda_total || ')' deuda_total\n");
                    sb.append("FROM campanias_base tcb,\n");
                    sb.append("campanias_segmentacion_base tcsb,\n");
                    sb.append("mtarje tmt,\n");
                    sb.append("mora_perfil_temp tmr\n");
                    sb.append("WHERE tcb.base_id = tcsb.caseb_base_id AND tcb.base_gestion IS NULL\n");
                    sb.append("AND TRIM (TO_NUMBER (tcb.base_identificacion)) = tmt.tar_documento\n"); 
                    sb.append("AND TO_NUMBER (TRIM (tcb.base_identificacion)) = tmr.dni(+)\n");
                    sb.append("AND tmt.tar_documento = tmr.dni\n");
                    sb.append("AND tcsb.caseb_case_id ="+segmento);
                }else{
                    sb.append("base_id id,\n");
                    sb.append("tar_documento dni,\n");            
                    sb.append("TRIM (tar_nom) || ' ' || TRIM (tar_ape) nombre,\n");
                    sb.append("'(LISTIN:' || tar_estado_listin || ')' listin\n");
                    sb.append("FROM campanias_base tcb,\n");
                    sb.append("campanias_segmentacion_base tcsb,\n");
                    sb.append("mtarje tmt\n");
                    sb.append("WHERE tcb.base_id = tcsb.caseb_base_id AND tcb.base_gestion IS NULL\n");
                    sb.append("AND TRIM (TO_NUMBER (tcb.base_identificacion)) = tmt.tar_documento\n"); 
                    sb.append("AND tcsb.caseb_case_id ="+segmento);
    
                }
            }
            System.out.println(sb.toString());
            st = conn.createStatement();
            res = st.executeQuery(sb.toString());                        
            ResultSetMetaData rsMetaData = res.getMetaData();
            int c = rsMetaData.getColumnCount();            
            for (int i = 1; i < c + 1; i++) {                                                            
                fila.append(rsMetaData.getColumnName(i));
                if(i!=c){
                    fila.append(",");
                }
            }
            salida.println(fila.toString().trim()+"\r");
            fila = new StringBuilder();            
            boolean incluir = true;
            int tel = 0;
            while (res.next()) {
                
                for(int k = 1; k <= c; k++){
                    if(rsMetaData.getColumnName(k).equalsIgnoreCase("id")){
                        break; 
                    }
                    if(res.getObject(k) instanceof String){
                        if(res.getObject(k)!=null && !((String)res.getObject(k)).trim().equals("")){
                            tel++;
                        }
                    }else if(res.getObject(k) instanceof BigDecimal){
                        if(res.getObject(k)!=null){
                            tel++;
                        }
                    }
                    if(!mora){
                        if(res.getObject(k)!=null){
                            if(res.getObject(k) instanceof String){
                                incluir = validarTelefono((String)res.getObject(k),segmento);
                            }else if(res.getObject(k) instanceof BigDecimal){
                                incluir = validarTelefono(((BigDecimal)res.getObject(k)).toString(),segmento);
                            }
                        }
                    }
                    if(!incluir){
                        break;
                    }
                }
                
                if(incluir && tel>0){
                    for(int j = 1; j <= c; j++){                    
                        if(res.getObject(j) instanceof String){
                            fila.append(res.getObject(j)==null?"":res.getString(j).replaceAll(",", "."));
                        }else{
                            fila.append(res.getObject(j)==null?"":res.getObject(j));
                        }
                        if(j<c){
                            fila.append(",");
                        }
                    }                
                    salida.println(fila.toString().trim()+"\r");
                    fila = new StringBuilder();
                }
                tel = 0;
            }                                            
            salida.close();

        }catch(Exception e){
            throw new PredictivoNoResultException(Util.getStackTraceMessage(e),LoggerType.SEVERE);
        }
    }
    
    private boolean validarTelefono(String tel,Long segmento){
        try{
            BigDecimal r =
                (BigDecimal)em.createNativeQuery("SELECT COUNT(*)\n" + 
                "  FROM (SELECT 1\n" + 
                "          FROM lista_negra_telefonos\n" + 
                "         WHERE telefono = '"+tel+"'\n" + 
                "               AND TRUNC (SYSDATE) - FECHA < 60\n" + 
                "        UNION\n" + 
                "        SELECT 1\n" + 
                "          FROM telefonos_no_llame\n" + 
                "         WHERE telefono_numero = '"+Long.valueOf(tel)+"')\n").getSingleResult();
            if(r != null && r.intValue()!=0){
                return false;
            }else{
                return true;
            }
        }catch(NoResultException e){
            return true;
        }        
    }
    
    private String replaceColumn(String s){
        String c = s.replaceAll("^\\W+|\\W+$","").replaceAll("\\W+", "_");
        if(c.length()>30){
            c = c.substring(0, 30);
        }
        return c;
    }
}
