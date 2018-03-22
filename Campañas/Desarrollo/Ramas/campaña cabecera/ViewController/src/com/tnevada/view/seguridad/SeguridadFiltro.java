package com.tnevada.view.seguridad;


import com.tnevada.view.managed.ApplicationBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SeguridadFiltro implements Filter {

    private FilterConfig _filterConfig = null;
    private static boolean desarrollo;
    /*VARIABLE PARA DETERMINAR EL DOMINIO*/
    private static String servletRequest = null;
    
    static {
        desarrollo = false;
  
        Boolean variableDesarrollo = Boolean.valueOf(System.getProperty("tnevada.desarrollo"));
        if (variableDesarrollo != null) {
            desarrollo = variableDesarrollo;
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        _filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse)servletResponse;
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        /*SETEO LA VARIABLE DEL DOMINIO*/
        setServletRequest(req.getServerName());
        
        HttpSession session = req.getSession();
        String paginaSolicitada = req.getPathInfo();
        
        AutorizarBean autorizaBean = null;
        if (session.getAttribute("AutorizarBean") == null) {
            autorizaBean = new AutorizarBean(req.getServerName());
            session.setAttribute("AutorizarBean", autorizaBean);
            if (req.getRemoteUser() != null) {
                autorizaBean.setUsuario(req.getRemoteUser().toLowerCase().trim());
            }
        } else {
            autorizaBean = (AutorizarBean)session.getAttribute("AutorizarBean");
        }

        if (ApplicationBean.getUrlDesautorizado() == null) {
            ApplicationBean.setUrlDesautorizado(autorizaBean.paginaDesautorizacion());
        }

        long idPagina = MapeoPaginas.getInstancia().getIdUrlPrograma(paginaSolicitada);

        if (desarrollo) {
          servletRequest.setAttribute("idPagina", String.valueOf(idPagina));
          filterChain.doFilter(servletRequest, servletResponse);
        } else {
          // No encontro la pagina en el mapeo, desautorizado
          if (idPagina == -1) {
              res.sendRedirect(ApplicationBean.getUrlDesautorizado());
          } else { 
              if (idPagina == 0) { // La pagina no esta sujeta a autenticación
                  filterChain.doFilter(servletRequest, servletResponse);
              } else {
                  if (autorizaBean.chequearAutorizacion(String.valueOf(idPagina), req)) {
                      servletRequest.setAttribute("idPagina", String.valueOf(idPagina));
                      filterChain.doFilter(servletRequest, servletResponse);
                  } else {
                      res.sendRedirect(ApplicationBean.getUrlDesautorizado());
                  }
              }
          }
        }
    }

    public void destroy() {
        _filterConfig = null;
    }

    public static String getServletRequest() {
        return servletRequest;
    }

    private void setServletRequest(String servletRequest) {
        this.servletRequest = servletRequest;
    }
}
