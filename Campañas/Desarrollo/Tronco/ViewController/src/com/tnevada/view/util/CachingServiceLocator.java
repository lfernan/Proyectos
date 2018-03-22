package com.tnevada.view.util;

import com.tnevada.model.campanias.ExpertoCamapaniasLocal;
import com.tnevada.model.mensajes.ExpertoMensajesLocal;
import com.tnevada.model.predictivo.ExpertoPredictivoLocal;
import com.tnevada.model.segmentacion.ExpertoSegmentacionLocal;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;

import javax.sql.DataSource;

public class CachingServiceLocator {
    private InitialContext ic;
    private Map<String, Object> cache;
    private static CachingServiceLocator me;

    /*static {
        try {
            me = new CachingServiceLocator();
        } catch (NamingException se) {
            throw new RuntimeException(se);
        }
    }*/

    private CachingServiceLocator() throws NamingException {
        ic = new InitialContext();
        cache = Collections.synchronizedMap(new HashMap<String, Object>());
        cache.put("ExpertoCamapanias", (ExpertoCamapaniasLocal)new InitialContext().lookup("java:comp/env/ExpertoCamapanias"));
        cache.put("ExpertoMensajes", (ExpertoMensajesLocal)new InitialContext().lookup("java:comp/env/ExpertoMensajes"));
        cache.put("ExpertoSegmentacion",(ExpertoSegmentacionLocal)new InitialContext().lookup("java:comp/env/ExpertoSegmentacion"));
        cache.put("ExpertoPredictivo",(ExpertoPredictivoLocal)new InitialContext().lookup("java:comp/env/ExpertoPredictivo"));
    }

    public static CachingServiceLocator getInstance() {
        if(me == null){
            try {
                me = new CachingServiceLocator();
            } catch (NamingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return me;
    }

    public Object lookup(String jndiName) throws NamingException {
        Object cachedObj = cache.get(jndiName);
        if (cachedObj == null) {
            cachedObj = ic.lookup(jndiName);
            cache.put(jndiName, cachedObj);
        }
        return cachedObj;
    }

    /**
     * Returns the ejb Local home factory. If this ejb home factory has already
     * been clients need to cast to the type of EJBHome they desire
     *
     * @return the EJB Home corresponding to the homeName
     */
    public EJBLocalHome getLocalHome(String jndiHomeName) throws NamingException {
        return (EJBLocalHome)lookup(jndiHomeName);
    }

    /**
     * Returns the ejb Remote home factory. If this ejb home factory has already
     * been clients need to cast to the type of EJBHome they desire
     *
     * @return the EJB Home corresponding to the homeName
     */
    public EJBHome getRemoteHome(String jndiHomeName, Class className) throws NamingException {
        Object objref = lookup(jndiHomeName);
        return (EJBHome)PortableRemoteObject.narrow(objref, className);
    }

    /**
     * This method helps in obtaining the topic factory
     *
     * @return the factory for the factory to get topic connections from
     */
    public ConnectionFactory getConnectionFactory(String connFactoryName) throws NamingException {
        return (ConnectionFactory)lookup(connFactoryName);
    }

    /**
     * This method obtains the topc itself for a caller
     *
     * @return the Topic Destination to send messages to
     */
    public Destination getDestination(String destName) throws NamingException {
        return (Destination)lookup(destName);
    }

    /**
     * This method obtains the datasource
     *
     * @return the DataSource corresponding to the name parameter
     */
    public DataSource getDataSource(String dataSourceName) throws NamingException {
        return (DataSource)lookup(dataSourceName);
    }

    /**
     * This method obtains the mail session
     *
     * @return the Session corresponding to the name parameter
     */
    public Session getSession(String sessionName) throws NamingException {
        return (Session)lookup(sessionName);
    }

    /**
     * This method obtains the URL
     *
     * @return the URL value corresponding to the env entry name.
     */
    public URL getUrl(String envName) throws NamingException {
        return (URL)lookup(envName);
    }

    /**
     * This method obtains the boolean
     *
     * @return the boolean value corresponding to the env entry such as
     * SEND_CONFIRMATION_MAIL property.
     */
    public boolean getBoolean(String envName) throws NamingException {
        Boolean bool = (Boolean)lookup(envName);
        return bool.booleanValue();
    }

    /**
     * This method obtains the String
     *
     * @return the String value corresponding to the env entry name.
     */
    public String getString(String envName) throws NamingException {
        return (String)lookup(envName);
    }
}

