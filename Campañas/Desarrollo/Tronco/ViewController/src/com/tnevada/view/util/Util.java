package com.tnevada.view.util;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.lang.reflect.Field;

import java.math.BigDecimal;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.text.Normalizer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import javax.mail.Message;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.data.RichTable;
import oracle.adf.view.rich.context.AdfFacesContext;

import org.apache.myfaces.trinidad.context.RequestContext;
import org.apache.myfaces.trinidad.model.RowKeySet;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

import tarjetascuyanas.fw.files.ServerPathException;
import tarjetascuyanas.fw.files.ServerPathLocator;


public class Util extends com.tnevada.model.util.Util {
    private static Session session = null;
    private static Transport transporte = null;

    public static String urlConcatena(String url) {
        if (url.startsWith("http")) {
            return url;
        }
        return "http://" +
               ((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServerName() +
               url;
    }

    public static File getPath() throws ServerPathException {
        return ServerPathLocator.getInstance().getApplicationPath(13, true);
    }

    /**
     * Muestra un mensaje de información en pantalla
     */
    public static void addMsgInfo(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                                                     new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", msg));
    }

    /**
     * Muestra un mensaje de error en pantalla
     */
    public static void addMsgError(String msg) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    /**
     * Muestra un mensaje de información en pantalla
     */
    public static void popMsgInfo(String msg) {
        mostrarMensaje("Información", msg);
    }

    /**
     * Muestra un mensaje de error en pantalla
     */
    public static void popMsgError(String msg) {
        mostrarMensaje("Error", msg);
    }


    /**
     * Muestra un mensaje por pantalla
     */
    public static void mostrarMensaje(String titulo, String msg) {
        RequestContext.getCurrentInstance().getPageFlowScope().put("gMsgTitulo", titulo);
        RequestContext.getCurrentInstance().getPageFlowScope().put("gMsg", msg);
        FacesContext context = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(context, ExtendedRenderKitService.class);
        service.addScript(context, "var popup = AdfPage.PAGE.findComponent('pt1:pMsg'); popup.show();");
    }

    /**
     * Agrega una variable a PageFlowScope
     */
    public static void setValuePageFlowScope(String clave, Object valor) {
        RequestContext.getCurrentInstance().getPageFlowScope().put(clave, valor);
    }

    /**
     * Obtiene el valor de una variable de PageFlowScope
     */
    public static Object getValuePageFlowScope(String clave) {
        return RequestContext.getCurrentInstance().getPageFlowScope().get(clave);
    }

    public static Map getPageFlowScope() {
        return RequestContext.getCurrentInstance().getPageFlowScope();
    }

    public static boolean getContainsKeyPageFlowScope(String clave) {
        return RequestContext.getCurrentInstance().getPageFlowScope().containsKey(clave);
    }

    public static void removeKeyPageFlowScope(String clave) {
        RequestContext.getCurrentInstance().getPageFlowScope().remove(clave);
    }

    /**
     * Agrega una variable a SessionScope
     */
    public static void setValueSessionScope(String clave, Object valor) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        Map sessionScope = ectx.getSessionMap();
        sessionScope.put(clave, valor);
    }

    /**
     * Obtiene el valor de una variable de SessionScope
     */
    public static Object getValueSessionScope(String clave) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        Map sessionScope = ectx.getSessionMap();
        if (!sessionScope.containsKey(clave) || sessionScope.isEmpty()) {
            return null;
        }
        return sessionScope.get(clave);
    }

    public static Map getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    /**
     * Ejecuta el código javascript recibido como parámetro
     */
    public static void ejecutarJs(String js) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(context, ExtendedRenderKitService.class);
        service.addScript(context, js);
    }

    /**
     * Pone el foco en el componente pasado por parametro
     * @param component El componente debe tener el atributo clientComponent en true
     */
    public static void setFocusOnComponent(UIComponent component) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
        String idComponent = component.getClientId(facesContext);
        StringBuffer script = new StringBuffer();
        script.append("var component = AdfPage.PAGE.findComponent('" + idComponent + "');");
        script.append("setTimeout(function() {component.focus();}, 2);");
        service.addScript(facesContext, script.toString());
    }

    public static void fireClick(UIComponent component) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExtendedRenderKitService service = Service.getRenderKitService(facesContext, ExtendedRenderKitService.class);
        String idComponent = component.getClientId(facesContext);
        StringBuffer script = new StringBuffer();
        script.append("document.getElementById('" + idComponent + "').click();");
        service.addScript(facesContext, script.toString());
    }

    /**
     * Synchronizes the table UI component row selection with the
     * selection in the ADF binding layer
     * @param selectionEvent event object created by the table
     * component upon row selection
     */
    public static boolean validatorGenericRexExp(FacesContext facesContext, UIComponent uIComponent, Object object,
                                                 String regex, String mensa) {

        String valor = String.valueOf(object);

        if (valor.matches(regex)) {
            return true;
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, null, mensa);
            facesContext.addMessage(uIComponent.getClientId(facesContext), msg);
            return false;
        }

    }

    /**
     * Allow Navigation to Location. Provides a way to navigate programmatically
     * to a page.
     * @param navCase Page to navigate
     */
    public static void navigateTo(String navCase) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        NavigationHandler navHandler = application.getNavigationHandler();
        navHandler.handleNavigation(facesContext, null, navCase);
    }

    public static String getContextPath() {
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        ServletContext sctx = (ServletContext)ectx.getContext();
        return sctx.getContextPath() + "/";
    }

    public static String getRealPath(String add) {
        String url = "";
        ServletContext servletContext =
            (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("/");
        if (add != null && !add.equals("")) {
            url = path + add;
            return url;
        }
        return path + File.separator;
    }

    public static String obtenerTrace(Exception e) {
        StringBuffer mensaje = new StringBuffer("");
        Exception exChequear = e;

        if (e.getCause() != null && e.getCause().getClass().getName().indexOf("TimedOutException") == -1) {
            exChequear = (Exception)e.getCause();
            if (exChequear.getCause() != null) {
                exChequear = (Exception)exChequear.getCause();
            }
        }

        StackTraceElement[] varios = exChequear.getStackTrace();
        mensaje.append(e.getMessage() + "\r\n");
        for (int i = 0; i < varios.length; i++) {
            StackTraceElement stackTraceElement = varios[i];
            mensaje.append(stackTraceElement.getClassName() + " - " + stackTraceElement.getMethodName() + " - (" +
                           stackTraceElement.getLineNumber() + ")" + "\r\n");
        }
        return mensaje.toString();
    }

    public static String obtenerTrace(Exception e, Object obj) {
        String inf = "";
        inf += obtenerAtributosObjeto(obj);
        inf += obtenerTrace(e);
        return inf;
    }

    public static String obtenerAtributosObjeto(Object obj) {
        StringBuffer sb = new StringBuffer();
        if (obj == null)
            return "Objeto NULO\n";
        Class cls = obj.getClass();
        sb.append("CLASS = " + cls.getName() + "\n");
        for (Field fld : cls.getDeclaredFields()) {
            fld.setAccessible(true);
            if (!(fld.getName().startsWith("_persistence_") || fld.getName().startsWith("serialVersionUID"))) {
                sb.append(fld.getName() + " = ");
                try {
                    sb.append(fld.get(obj) != null ? fld.get(obj).toString() : null);
                } catch (IllegalAccessException IAe) {
                    sb.append(IAe.getMessage());
                }
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public static double redondear(double valor, int decimales) {
        if (Double.valueOf(valor).isNaN())
            return 0D;
        BigDecimal bd = BigDecimal.valueOf(valor);
        bd = bd.setScale(decimales, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }

    public static Map contextMap() {
        FacesContext fctx = FacesContext.getCurrentInstance();
        ExternalContext ectx = fctx.getExternalContext();
        Map sessionScope = ectx.getSessionMap();
        return sessionScope;
    }

    public static void refreshUIComponent(UIComponent component) {
        AdfFacesContext.getCurrentInstance().addPartialTarget(component);
        AdfFacesContext.getCurrentInstance().partialUpdateNotify(component);
    }

    /*METODOS PARA CARGAR Y RECUPERAR VALORES DEL PAGEFLOW*/

    //Method on the button

    public void doCounting(ActionEvent actionEvent) {
        Number value = (Number)getPageFlowScopeValue("myCounter");
        if (value.intValue() >= 0) {
            setPageFlowScopeValue("myCounter", value.intValue() + 1);
        }
        setManagedBeanValue("pageFlowScope.pFlowBean.counter", getPageFlowScopeValue("myCounter"));
    }

    //Method to set the value of page flow scope created on runtime

    public void setPageFlowScopeValue(String name, Number value) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        pageFlowScope.put(name, value);
    }

    //method to get the value of page flow scope created on runtime

    public Object getPageFlowScopeValue(String name) {
        ADFContext adfCtx = ADFContext.getCurrent();
        Map pageFlowScope = adfCtx.getPageFlowScope();
        Object val = pageFlowScope.get(name);
        if (val == null)
            return 0;
        else
            return val;
    }

    //Methods used to get and set the values in a Managed bean

    public static Object getManagedBeanValue(String beanName) {
        StringBuffer buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append("}");
        return resolveExpression(buff.toString());
    }

    public static Object resolveExpression(String expression) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        return valueExp.getValue(elContext);
    }

    public static Object resloveMethodExpression(String expression, Class returnType, Class[] argTypes,
                                                 Object[] argValues) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        MethodExpression methodExpression =
            elFactory.createMethodExpression(elContext, expression, returnType, argTypes);

        return methodExpression.invoke(elContext, argValues);
    }

    public static void setManagedBeanValue(String beanName, Object newValue) {
        StringBuffer buff = new StringBuffer("#{");
        buff.append(beanName);
        buff.append("}");
        setExpressionValue(buff.toString(), newValue);
    }

    public static void setExpressionValue(String expression, Object newValue) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application app = facesContext.getApplication();
        ExpressionFactory elFactory = app.getExpressionFactory();
        ELContext elContext = facesContext.getELContext();
        ValueExpression valueExp = elFactory.createValueExpression(elContext, expression, Object.class);
        Class bindClass = valueExp.getType(elContext);
        if (bindClass.isPrimitive() || bindClass.isInstance(newValue)) {
            valueExp.setValue(elContext, newValue);
        }
    }

    public static Long fechaToLong(Date fecha) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha);
        Long resultado = 0L;
        resultado = new Long(calendario.get(Calendar.YEAR) * 10000);
        resultado += new Long((calendario.get(Calendar.MONTH) + 1) * 100);
        resultado += new Long(calendario.get(Calendar.DAY_OF_MONTH));

        return resultado;
    }

    /**
     *
     * @param el - La expresión que se desea evaluar
     * @return El valor obtenido despues de evaluarla
     */
    public static Object evaluarEL(String el) {
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ELContext elContext = facesContext.getELContext();
            ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
            ValueExpression exp = expressionFactory.createValueExpression(elContext, el, Object.class);

            return exp.getValue(elContext);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void invokePopup(String popupId, String align, String alignId) {
        if (popupId != null) {
            ExtendedRenderKitService service =
                Service.getRenderKitService(FacesContext.getCurrentInstance(), ExtendedRenderKitService.class);

            StringBuffer showPopup = new StringBuffer();
            showPopup.append("var hints = new Object();");
            //Add hints only if specified - see javadoc for AdfRichPopup js for details on vali
            if (align != null && alignId != null) {
                showPopup.append("hints[AdfRichPopup.HINT_ALIGN] = " + align + ";");
                showPopup.append("hints[AdfRichPopup.HINT_ALIGN_ID] ='" + alignId + "';");
            }
            showPopup.append("var popupObj=AdfPage.PAGE.findComponent('" + popupId + "'); popupObj.show(hints);");
            service.addScript(FacesContext.getCurrentInstance(), showPopup.toString());
        }
    }

    /**
     * Hides the specified popup.
     * @param popupId is the clientId of the popup to be hidden
     * clientId is derived from backing bean for the af:popup using getClientId method
     */
    public static void hidePopup(String popupId) {
        if (popupId != null) {
            ExtendedRenderKitService service =
                Service.getRenderKitService(FacesContext.getCurrentInstance(), ExtendedRenderKitService.class);

            String hidePopup = "var popupObj=AdfPage.PAGE.findComponent('" + popupId + "'); popupObj.hide();";
            service.addScript(FacesContext.getCurrentInstance(), hidePopup);
        }
    }

    public static void clearSelection(RichTable t) {
        if (t != null && t.getSelectedRowKeys() != null) {
            t.getSelectedRowKeys().clear();
        }
    }

    private static Session getSession() {
        if (session == null) {
            Properties props = new Properties();
            props.put("mail.smtp.host", "130.100.1.30");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "25");
            props.setProperty("mail.smtp.user", "usuario");

            session = Session.getDefaultInstance(props, null);
        }
        return session;
    }

    public static String enviarMailComoHTML(String de, String para, String asunto, String mensaje) throws Exception {
        try {
            MimeMessage message = new MimeMessage(getSession());
            // Quien envia el correo
            message.setFrom(new InternetAddress(de));

            String[] destinatarios = para.split(";");

            for (int i = 0; i < destinatarios.length; i++) {
                // A quien va dirigido
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatarios[i]));
            }
            message.setSubject(asunto, "Cp1252");
            message.setContent(mensaje, "text/html");
            getSession();
            getTransporte().connect();
            getTransporte().sendMessage(message, message.getAllRecipients());
            getTransporte().close();

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return null;
    }

    private static Transport getTransporte() throws NoSuchProviderException {
        if (transporte == null) {
            transporte = getSession().getTransport("smtp");
        }
        return transporte;
    }

    public static void disclosedRowKeys(RichTable t) {
        RowKeySet disclosedRowKeys = t.getDisclosedRowKeys();
        disclosedRowKeys.clear();
        refreshUIComponent(t);
    }

    public static String removeAc(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    public static void openDetailTable(RichTable t) {
        RowKeySet disclosedRowKeys = t.getDisclosedRowKeys();
        disclosedRowKeys.invertAll();
        AdfFacesContext.getCurrentInstance().addPartialTarget(t.getParent());
    }

    public static Pattern createPattern(String pattern) {
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        return p;
    }

    public static boolean chkEXLFile(String name) {
        String pattern = "(.*)\\.([a-z0-9]{3,4})$";
        String filename = name;
        Pattern p = createPattern(pattern);
        Matcher chkFile = p.matcher(filename);

        if (chkFile.find()) {
            String file_ext = chkFile.group(2).toLowerCase();
            if (file_ext.equals("xls")) {
                return true;
            } else if (file_ext.equals("xlsx")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static String getUrlParameter(String key) {
        HttpServletRequest req =
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getParameterMap().isEmpty() || req.getParameter(key) == null) {
            return null;
        } else {
            return req.getParameter(key);
        }
    }

    public static boolean getUrlContains(String key) {
        HttpServletRequest req =
            (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getParameterMap().containsKey(key)) {
            return true;
        } else {
            return false;
        }
    }

    public static HashMap<String, Object> jsonParse(String json) {
        JsonParser parser = new JsonParser();
        JsonObject object = (JsonObject)parser.parse(json);
        Set<Map.Entry<String, JsonElement>> set = object.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
        HashMap<String, Object> map = new HashMap<String, Object>();

        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> entry = iterator.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();

            if (null != value) {
                if (!value.isJsonPrimitive()) {
                    if (value.isJsonObject()) {
                        map.put(key, jsonParse(value.toString()));
                    } else if (value.isJsonArray() && value.toString().contains(":")) {
                        List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
                        JsonArray array = value.getAsJsonArray();
                        if (null != array) {
                            for (JsonElement element : array) {
                                list.add(jsonParse(element.toString()));
                            }
                            map.put(key, list);
                        }
                    } else if (value.isJsonArray() && !value.toString().contains(":")) {
                        map.put(key, value.getAsJsonArray());
                    }
                } else {
                    map.put(key, value.getAsString());
                }
            }
        }
        return map;
    }

    public static <T> List<T> jsonParseArray(String json, Class<T> clase) throws Exception {
        List<T> lista = new ArrayList<T>();
        try {
            JsonParser parser = new JsonParser();
            JsonElement elemento = parser.parse(json);
            JsonArray array = elemento.getAsJsonArray();
            Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                T o = obtenerEntidad(entrada.toString(), clase);
                lista.add(o);
            }
        } catch (Exception e) {
            throw new Exception("No se pudo convertir a la lista de objetos.");
        }
        return lista;
    }

    public static String generarJson(Object objetoSolomon) throws Exception {
        String retorno = null;
        try {
            Gson gson = new Gson();
            retorno = gson.toJson(objetoSolomon);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No se pudo convertir objeto " + objetoSolomon + " a JSON.");
        }
        return retorno;
    }

    public static JsonElement getJsonElement(String json, String clave) throws Exception {
        JsonObject objeto = null;
        try {
            JsonParser parser = new JsonParser();
            JsonElement elemento = parser.parse(json);
            objeto = elemento.getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No se pudo interpretar la respuesta de Gestar.");
        }
        return objeto.get(clave);
    }

    public static <T> T obtenerEntidad(String json, Class<T> clase) throws Exception {
        T objeto = null;
        try {
            Gson gson = new Gson();
            objeto = gson.fromJson(json, clase);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("No se pudo obtener respuesta como " + clase.getName());
        }
        return objeto;
    }

    public static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            HttpURLConnection http = prepararConexion(urlString, "");
            reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null)
                reader.close();
        }
        return null;
    }

    public static HttpURLConnection prepararConexion(String url, String longitudContenido) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //conn.setRequestProperty("Content-Type", "application/json; charset=utf8");
            conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
            //conn.setRequestProperty("Accept", "application/json");
            //conn.setRequestProperty("Content-Length", longitudContenido);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Long getNumero(String s) {
        Pattern p2 = Pattern.compile("[0-9]+");
        Matcher m2 = p2.matcher(s);
        StringBuilder sb = new StringBuilder();
        while (m2.find()) {
            sb.append(m2.group());
        }
        if (sb.toString().equals("")) {
            return 0L;
        }
        return Long.valueOf(sb.toString());
    }
    
    public static String getTexto(String s){
        if(s == null){
            return "";
        }
        String tmp = s.replaceAll("[0-9]+", "");
        return tmp;
    }
    
    public static String uniqueId() {
        return java.util.UUID.randomUUID().toString();
    }
}
