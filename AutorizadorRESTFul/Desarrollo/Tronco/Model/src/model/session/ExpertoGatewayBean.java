package model.session;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import java.util.UUID;

import javax.annotation.Resource;

import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import javax.sql.DataSource;

import model.entities.OperacionDTO;
import model.entities.Plan;
import model.entities.TokenOperaciones;

import oracle.jdbc.OracleTypes;

@Stateless(name = "ExpertoGateway", mappedName = "AutorizadorRESTFull-Model-ExpertoGateway")
public class ExpertoGatewayBean implements ExpertoGatewayLocal, Queries {
    @Resource
    SessionContext sessionContext;
    @PersistenceContext(unitName = "Model")
    private EntityManager em;
    @Resource(name = "jdbc/nevadaCoreDS")
    private DataSource ds;

    public boolean validar(Long ocr, String nombre, Integer cvc, Integer expiracion, Integer documento) {
        try {
            return ((BigDecimal) em.createNativeQuery(VALIDAR_DATOS).setParameter(1, ocr)
                                                                    .setParameter(2,cvc)
                                                                    .setParameter(3, documento)
                                                                    .setParameter(4,ocr.toString())
                                                                    .setParameter(5,expiracion)
                                                                    .setParameter(6,nombre.trim().toUpperCase())
                                                                    .setParameter(7,ocr)
                                                                    .getSingleResult()).intValue() == 1 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean login(String entidad, String serial) {
        try {
            return ((BigDecimal) em.createNativeQuery(LOGIN)
                   .setParameter(1, entidad)
                   .setParameter(2,serial).getSingleResult()).intValue() > 0 ? true : false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public OperacionDTO verificarOperacion(Long operacion, Double monto) {
        try {
            Object c = em.createNativeQuery(VERIFICAR_OPERACION).setParameter(1, monto).setParameter(2, operacion).getSingleResult();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void log(Long comercio, int estado, Long operacion, Long ocr, Integer moneda, Double monto, Integer plan,
                    Long codigoAutorizacion, Long codigoError) {
        try {
            em.createNativeQuery(GRABAR_LOGIN_ACEPTADO).setParameter(1, comercio)
                                                        .setParameter(2,estado)
                                                        .setParameter(3,operacion)
                                                        .setParameter(4,ocr)
                                                        .setParameter(5,moneda)
                                                        .setParameter(6,monto)
                                                        .setParameter(7,plan)
                                                        .setParameter(8,codigoAutorizacion)
                                                        .setParameter(9,codigoError)
                                                        .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    public List<Plan> planes(Long comercio, Double monto, Integer documento) {
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = ds.getConnection();
            cstmt = conn.prepareCall(PLANES);
            cstmt.setInt(1, documento);
            cstmt.registerOutParameter(2, OracleTypes.CURSOR);
            cstmt.setDouble(3, monto);
            cstmt.setString(4, comercio.toString());
            cstmt.execute();
            rs = (ResultSet) cstmt.getObject(2);
            List<Plan> planes = new ArrayList<Plan>();
            while (rs.next()) {
                planes.add(new Plan(((BigDecimal) rs.getObject(1)).intValue(),
                                    ((BigDecimal) rs.getObject(2)).floatValue(),
                                    ((BigDecimal) rs.getObject(3)).floatValue()));
            }
            return planes;
        } catch (Exception e) {
            return null;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
                if (cstmt != null) {
                    cstmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }        
    }
    
    public boolean validarToken(String token){
        try{
            TokenOperaciones t =
                (TokenOperaciones) em.createQuery("select o from TokenOperaciones o where o.tkoId = '"+token.trim()+"'")                                     
                                     .getSingleResult();
            if(new Timestamp(new Date().getTime()).after(t.getTkoFechaVencimiento())){
                return false;
            }
            return true;
        }catch(NoResultException e){
            return false;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public void boorarToken(String token){
        try{
            TokenOperaciones t = (TokenOperaciones) em.find(TokenOperaciones.class, token);
            em.remove(t);
        }catch(Exception e){
            e.printStackTrace();            
        }
    }
    
    public String crearToken(){
        try{
            Calendar c = Calendar.getInstance();
            c.clear();
            c.setTime(new Date());
            c.set(Calendar.MINUTE, c.get(Calendar.MINUTE) + 10);
            String token = UUID.randomUUID().toString();
            TokenOperaciones t = new TokenOperaciones(new Timestamp(new Date().getTime()),null,new Timestamp(c.getTime().getTime()),token,null);
            em.persist(t);
            return token;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
