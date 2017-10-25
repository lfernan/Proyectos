package view.servicios;

import com.google.gson.Gson;

import com.oracle.xmlns.autorizarcompra.AutorizarCompra;
import com.oracle.xmlns.autorizarcompra.AutorizarCompraProcessRequest;
import com.oracle.xmlns.autorizarcompra.AutorizarCompraProcessResponse;
import com.oracle.xmlns.autorizarcompra.AutorizarCompra_Service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import javax.xml.datatype.DatatypeFactory;

import model.entities.Plan;

import model.session.Constantes;
import model.session.ExpertoGatewayLocal;

import view.dto.Datos;
import view.dto.Respuesta;

import view.util.Util;

@Path("api")
public class ServicioGateway {
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("autorizarCompra")
    public Response autorizarCompra(@BeanParam Datos user) {
        Gson gson = new Gson();
        Respuesta json = new Respuesta();
        AutorizarCompraProcessResponse response = null;
        try {
            if(!getSession().validarToken(user.getToken())){
                json.setCodigoAutorizacion(0L);
                json.setCodigoError(2);
                json.setObservacion("Su sesión caducó");
                return Response.ok(gson.toJson(json)).build();
            }
            AutorizarCompra_Service servicio = new AutorizarCompra_Service();
            AutorizarCompra autorizar = servicio.getAutorizarCompraPort();
            AutorizarCompraProcessRequest request = new AutorizarCompraProcessRequest();
            request.setTarjeta(user.getTarjeta());
            request.setComercio(user.getComercio());
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(new Date());
            request.setFecha(DatatypeFactory.newInstance().newXMLGregorianCalendar(c));
            request.setImporte(user.getImporte());
            request.setPlan(user.getPlan());
            request.setMoneda(user.getMoneda());
            request.setTerminal(user.getTerminal());
            request.setNroTransaccion(user.getCodOperacion());
            request.setDiasDiferimiento(0);
            request.setLegajo(6924);
            request.setTipoAutorizador(3);
            request.setMarcaLecturaPorBandaMagnetica(0);
            request.setMarcaTransaccionOffLine(0);
            request.setMarcaLlamadaConTarjeta(0);
            request.setCodigoAutorizacion(0L);
            request.setOmitePermiso(null);
            response = autorizar.process(request);
            json.setCodigoAutorizacion(response.getCodigoAutorizacion());
            json.setCodigoError(response.getCodigoError());
            json.setObservacion(response.getObservacion());
        } catch (Exception e) {
            json.setCodigoAutorizacion(0L);
            json.setCodigoError(1);
            json.setObservacion(e.getMessage());
        } finally {
            int estado = 0;
            if (json.getCodigoError() != 0) {
                estado = Constantes.PAGO_INVALIDO;
            } else {
                estado = Constantes.PAGO_EXITOSO;
            }
            getSession().boorarToken(user.getToken());
            getSession().log(user.getComercio(), estado, 123L, user.getTarjeta(), user.getMoneda(), user.getImporte(), user.getPlan(), response.getCodigoAutorizacion(),
                             response.getCodigoError().longValue());
        }
        //peticiones.remove(user.token);
        return Response.ok(gson.toJson(json)).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("validar")
    //@JWTTokenNeeded 
    public Response validar(@BeanParam Datos datos) {
        Respuesta response = new Respuesta();
        Gson gson = new Gson();        
        if(!getSession().validarToken(datos.getToken())){
            response.setCodigoAutorizacion(0L);
            response.setCodigoError(2);
            response.setObservacion("Su sesión caducó");
            return Response.ok(gson.toJson(response),MediaType.APPLICATION_JSON).build();
        }
        try {
            if (getSession().validar(datos.getOcr(), datos.getNombre(), datos.getCvc(), datos.getExpiracion(), datos.getDni())) {
                response.setCodigoAutorizacion(1L);
                response.setCodigoError(0);
                response.setObservacion("Datos Validos");
            } else {
                response.setCodigoAutorizacion(0L);
                response.setCodigoError(1);
                response.setObservacion("Los datos ingresados son incorrectos");
            }
        } catch (Exception e) {
            response.setCodigoAutorizacion(0L);
            response.setCodigoError(1);
            response.setObservacion(e.getMessage());
        } finally {

        }
        return Response.ok(gson.toJson(response),MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    @Path("login")
    public Response login( @FormParam("entidad") String entidad, @FormParam("serial") String serial) {
        Respuesta response = new Respuesta();
        try {
            if (getSession().login(entidad, Util.decrypt(serial)/*serial*/)) {
                response.setCodigoAutorizacion(1L);
                response.setCodigoError(0);
                response.setObservacion("Usuario Autorizado");
                String token = getSession().crearToken();//Util.getToken();
                //tokens(token, new Date(), true);
                return Response.ok("{\"token\":\""+token+"\"}",MediaType.APPLICATION_JSON).build();
            } else {
                response.setCodigoAutorizacion(0L);
                response.setCodigoError(2);
                response.setObservacion("Desautorizado");
                return Response.status(401).entity("Desautorizado").build();
            }
        } catch (Exception e) {
            response.setCodigoAutorizacion(0L);
            response.setCodigoError(1);
            response.setObservacion(e.getMessage());
            return Response.status(401).entity("Desautorizado").build();
        } finally {
            getSession().log(600000017L,
                             response.getCodigoError() == 0 ? Constantes.ESTADO_LOGIN_ACEPTADO :
                             Constantes.ESTADO_LOGIN_DESAUTORIZADO, 123L, null, null, null, null, null, null);
        }
    }

    @GET
    @Produces("application/json")
    @Path("verificarOperacion/{monto}/{codOperacion}")
    public Response verificarOperacion(@PathParam("codOperacion") Long operacion, @PathParam("monto") Double monto) {
        Respuesta response = new Respuesta();
        Gson gson = new Gson();
        try {
            /*if (getSession().verificarOperacion(operacion, monto)) {
                response.setCodigoAutorizacion(operacion);
                response.setCodigoError(0);
                response.setObservacion("VERIFICACION DE OPERACION EXITOSA");
                return Response.ok(gson.toJson(response)).build();
            } else {
                response.setCodigoAutorizacion(0L);
                response.setCodigoError(1);
                response.setObservacion("PAGO INVÁLIDO");
                return Response.ok(gson.toJson(response)).build();
            }*/
            //getSession().verificarOperacion(123L, 8000F);
            return null;
            
        } catch (Exception e) {
            response.setCodigoAutorizacion(0L);
            response.setCodigoError(1);
            response.setObservacion(e.getMessage());
            return Response.ok(gson.toJson(response)).build();
        } finally {
            getSession().log(600000017L,
                             response.getCodigoError() == 0 ? Constantes.VERIFICACION_DE_OPERACION_EXITOSA :
                             Constantes.VERIFICACION_DE_OPERACION_INCORRECTA, operacion, null, null, monto, null, null,
                             null);
        }
    }

    @GET
    @Produces("application/json")
    @Path("planes/{documento}/{comercio}/{monto}")
    public Response planes(@PathParam("documento") Integer documento, @PathParam("comercio") Long comercio,
                           @PathParam("monto") Double monto) {
        Respuesta response = new Respuesta();
        Gson gson = new Gson();
        List<Plan> planes = null;
        try {
            planes = getSession().planes(comercio, monto, documento);
        } catch (Exception e) {
            response.setCodigoAutorizacion(0L);
            response.setCodigoError(1);
            response.setObservacion(e.getMessage());
            return Response.ok(gson.toJson(response)).build();
        } finally {

        }
        return Response.ok(gson.toJson(planes)).build();
    }
    
    @GET
    @Produces("text/plain")
    @Path("test")
    public Response getData() {
        return Response.ok("todo ok").build();
    }
    
    private ExpertoGatewayLocal getSession() {
        try {
            return (ExpertoGatewayLocal) new InitialContext().lookup("java:comp/env/ExpertoGateway");
        } catch (NamingException e) {
            return null;
        }
    }

    private int tokens(String token, Date time, boolean login) {
        /*if (peticiones == null) {
            peticiones = new HashMap<String, Date>();
        }*/
        /*if (peticiones.containsKey(token)) {
            long diff = new Date().getTime() - ((Date) peticiones.get(token)).getTime();
            long diffSeconds = diff / 1000;
            long diffMinutes = diff / (60 * 1000);
            long diffHours = diff / (60 * 60 * 1000);
            peticiones.remove(token);
            if (diffMinutes >= 5) {
                return 1;
            } else {
                peticiones.put(token, new Date());
            }
        } else if (login) {
            //peticiones.put(token, time);
        } else {
            return 1;
        }*/
        return 0;
    }
}
