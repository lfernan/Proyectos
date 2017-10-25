package view.servicios;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

/*@Provider
@PreMatching*/
public class ResponseFilter /*implements ContainerResponseFilter*/ {

    /*@Override
    public void filter(ContainerRequestContext resquest,
                       ContainerResponseContext response) throws IOException {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
        response.getHeaders().add("Access-Control-Allow-Credentials", "false");
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.getHeaders().add("Access-Control-Allow-Headers", "token, content-type");
    }*/
}
