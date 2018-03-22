package com.tnevada.view.seguridad;

import java.util.HashMap;


public class MapeoPaginas {
    
    private static MapeoPaginas instancia;
    
    public static MapeoPaginas getInstancia() {
        if (instancia == null) {
          instancia = new MapeoPaginas();
        }
        
        return instancia;
    }

    private HashMap mapaPaginas = new HashMap();

    private MapeoPaginas() {
        /* Aqu� deben estar las urls de todas las paginas de esta aplicaci�n
         * asociadas con un id de aplicaci�n para chequear que el usuario este
         * autorizado a acceder a la misma, si el id de la aplicaci�n es 0,
         * la pagina no esta sujeta a autenticaci�n
         */
        this.mapaPaginas.put( "/index.jsf",11201 );
    }

    public long getIdUrlPrograma(String path) {
        try {
            if (this.mapaPaginas.get(path) != null)
                return Long.valueOf(this.mapaPaginas.get(path).toString()).longValue();
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }
}
