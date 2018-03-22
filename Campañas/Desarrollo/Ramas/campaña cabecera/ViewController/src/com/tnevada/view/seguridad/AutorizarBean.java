package com.tnevada.view.seguridad;

import com.tnevada.segproxy.ServicioAutorizadorProxy;
import java.util.HashMap;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;


public class AutorizarBean {

  private HashMap prograAutorizados;
  private String usuario= "";
  private ServicioAutorizadorProxy autorizador;

  public AutorizarBean(String serverName) {
      try {
          autorizador =
                  //new ServicioAutorizadorProxy(Server.getInstancia().getServerServicios());//PRODUCCION
                  new ServicioAutorizadorProxy(serverName);//PRODUCCION
                  //new ServicioAutorizadorProxy("appsoasdesa.tarjetanevada.com.ar");//DESARROLLO
      } catch (Exception e) {
          // TODO: Add catch code
          e.printStackTrace();
      }
      prograAutorizados = new HashMap();
      // Setea el local a español
      Locale.setDefault(new Locale("es", "ES"));
  }

  public boolean chequearAutorizacion(String programa,
                                      HttpServletRequest request) {

      // Chequea si el programa no fue ya habilitado para este usuario
      if (this.prograAutorizados.containsKey(programa.trim())) {
          return true;
      } else {
          try {
              // Chequea si ya esta cargado en session el nombre del usuario
              // de no ser asi, es la primera vez que ingresa a la aplicación
              // HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
              if (usuario != null && !usuario.trim().equals("")) {
                  // Chequea la habilitación del usuario en el Web Service
                  if (autorizador.estaAutorizado(usuario, programa)) {
                      // Si estaba autorizado, lo guarda en el mapa de programas autorizados
                      prograAutorizados.put(programa.trim(), null);
                      return true;
                  }
              } else { // si todavia no esta cargado el usuario en session,( es la primera llamada )
                  HttpServletRequest req = request;
                  if (req.getParameter("param") == null ||
                      req.getParameter("param").toString().trim().equals(""))
                      return false;
                  String userid = req.getParameter("param").toString();
                  String user =
                      autorizador.estaAutorizadoUserid(userid, programa);

                  if (user == null || user.equals("")) {
                      return false; // no lo encontro, no esta autorizado.
                  } else { // si se sencuentra autorizado, guarda el nombre de usuario en la session
                      // Si estaba autorizado, lo guarda en el mapa de programas autorizados
                      prograAutorizados.put(programa.trim(), null);
                      req.getSession().setAttribute("usuario", user);
                      this.setUsuario(user);
                      return true;
                  }
              }

          } catch (Exception e) {
              e.printStackTrace();
              return false;
          }
          return false;
      }
  }

  public String[] accionesHabilitadas(String idPrograma) {
      try {
          if (this.prograAutorizados.containsKey(idPrograma)) {
              if (this.prograAutorizados.get(idPrograma) != null)
                  return (String[])this.prograAutorizados.get(idPrograma);
              else {
                  String[] autorizados =
                      this.autorizador.accionesHabilitadas(idPrograma,
                                                           this.getUsuario());
                  this.prograAutorizados.remove(idPrograma);
                  this.prograAutorizados.put(idPrograma, autorizados);
                  return autorizados;
              }
          } else {
              String[] autorizados =
                  this.autorizador.accionesHabilitadas(idPrograma,
                                                       this.getUsuario());
              this.prograAutorizados.put(idPrograma, autorizados);
              return autorizados;
          }
      } catch (Exception e) {
          e.printStackTrace();
          return null;
      }
  }


  public String paginaDesautorizacion() {
      try {
          return this.autorizador.urlDesautorizado();
      } catch (Exception e) {
          return "";
      }
  }

  public boolean chequearAutorizacion(String programa) {

      // Chequea si el programa no fue ya habilitado para este usuario
      if (this.prograAutorizados.containsKey(programa.trim())) {
          return true;
      } else {
          try {
              // Chequea si ya esta cargado en session el nombre del usuario
              // de no ser asi, es la primera vez que ingresa a la aplicación
              // HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
              if (usuario != null && !usuario.trim().equals("")) {
                  // Chequea la habilitación del usuario en el Web Service
                  if (autorizador.estaAutorizado(usuario, programa)) {
                      // Si estaba autorizado, lo guarda en el mapa de programas autorizados
                      prograAutorizados.put(programa.trim(), null);
                      return true;
                  }
              } else { // si todavia no esta cargado el usuario en session,( es la primera llamada )
                  HttpServletRequest req =
                      (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                  if (req.getParameter("param") == null ||
                      req.getParameter("param").toString().trim().equals(""))
                      return false;
                  String userid = req.getParameter("param").toString();
                  String user =
                      autorizador.estaAutorizadoUserid(userid, programa);

                  if (user == null || user.equals("")) {
                      return false; // no lo encontro, no esta autorizado.
                  } else { // si se sencuentra autorizado, guarda el nombre de usuario en la session
                      // Si estaba autorizado, lo guarda en el mapa de programas autorizados
                      prograAutorizados.put(programa.trim(), null);
                      req.getSession().setAttribute("usuario", user);
                      this.setUsuario(user);
                      return true;
                  }
              }

          } catch (Exception e) {
              return false;
          }
          return false;
      }
  }


  public void setUsuario(String usuario) {
      this.usuario = usuario;
  }

  public String getUsuario() {
      if (usuario == null)
          return "";
      return usuario;
  }
}
