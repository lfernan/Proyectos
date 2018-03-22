package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import javax.servlet.http.HttpServletRequest;

import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.model.binding.DCParameter;

import oracle.binding.AttributeBinding;
import oracle.binding.BindingContainer;
import oracle.binding.ControlBinding;

import oracle.jbo.ApplicationModule;
import oracle.jbo.Key;
import oracle.jbo.Row;


public class JSFUtils
{

  private static final String NO_RESOURCE_FOUND = "Missing resource: ";

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching object (or creating it).
   * @param expression EL expression
   * @return Managed object
   */
  public static Object resolveExpression(String expression)
  {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    ValueExpression valueExp =
      elFactory.createValueExpression(elContext, expression, Object.class);
    return valueExp.getValue(elContext);
  }

  public static String resolveRemoteUser()
  {
    FacesContext facesContext = getFacesContext();
    ExternalContext ectx = facesContext.getExternalContext();
    return ectx.getRemoteUser();
  }

  public static String resolveUserPrincipal()
  {
    FacesContext facesContext = getFacesContext();
    ExternalContext ectx = facesContext.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) ectx.getRequest();
    return request.getUserPrincipal().getName();
  }

  public static Object resloveMethodExpression(String expression,
                                               Class returnType,
                                               Class[] argTypes,
                                               Object[] argValues)
  {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    MethodExpression methodExpression =
      elFactory.createMethodExpression(elContext, expression, returnType,
                                       argTypes);
    return methodExpression.invoke(elContext, argValues);
  }

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching Boolean.
   * @param expression EL expression
   * @return Managed object
   */
  public static Boolean resolveExpressionAsBoolean(String expression)
  {
    return (Boolean) resolveExpression(expression);
  }

  /**
   * Method for taking a reference to a JSF binding expression and returning
   * the matching String (or creating it).
   * @param expression EL expression
   * @return Managed object
   */
  public static String resolveExpressionAsString(String expression)
  {
    return (String) resolveExpression(expression);
  }

  /**
   * Convenience method for resolving a reference to a managed bean by name
   * rather than by expression.
   * @param beanName name of managed bean
   * @return Managed object
   */
  public static Object getManagedBeanValue(String beanName)
  {
    StringBuffer buff = new StringBuffer("#{");
    buff.append(beanName);
    buff.append("}");
    return resolveExpression(buff.toString());
  }

  /**
   * Method for setting a new object into a JSF managed bean
   * Note: will fail silently if the supplied object does
   * not match the type of the managed bean.
   * @param expression EL expression
   * @param newValue new value to set
   */
  public static void setExpressionValue(String expression, Object newValue)
  {
    FacesContext facesContext = getFacesContext();
    Application app = facesContext.getApplication();
    ExpressionFactory elFactory = app.getExpressionFactory();
    ELContext elContext = facesContext.getELContext();
    ValueExpression valueExp =
      elFactory.createValueExpression(elContext, expression, Object.class);

    //Check that the input newValue can be cast to the property type
    //expected by the managed bean.
    //If the managed Bean expects a primitive we rely on Auto-Unboxing
    //I could do a more comprehensive check and conversion from the object
    //to the equivilent primitive but life is too short
    Class bindClass = valueExp.getType(elContext);
    if (bindClass.isPrimitive() || bindClass.isInstance(newValue))
    {
      valueExp.setValue(elContext, newValue);
    }
  }

  /**
   * Convenience method for setting the value of a managed bean by name
   * rather than by expression.
   * @param beanName name of managed bean
   * @param newValue new value to set
   */
  public static void setManagedBeanValue(String beanName, Object newValue)
  {
    StringBuffer buff = new StringBuffer("#{");
    buff.append(beanName);
    buff.append("}");
    setExpressionValue(buff.toString(), newValue);
  }


  /**
   * Convenience method for setting Session variables.
   * @param key object key
   * @param object value to store
   */

  public static void storeOnSession(String key, Object object)
  {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    sessionState.put(key, object);
  }

  /**
   * Convenience method for getting Session variables.
   * @param key object key
   * @return session object for key
   */
  public static Object getFromSession(String key)
  {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getSessionMap();
    return sessionState.get(key);
  }

  public static String getFromHeader(String key)
  {
    FacesContext ctx = getFacesContext();
    ExternalContext ectx = ctx.getExternalContext();
    return ectx.getRequestHeaderMap().get(key);
  }

  /**
   * Convenience method for getting Request variables.
   * @param key object key
   * @return session object for key
   */
  public static Object getFromRequest(String key)
  {
    FacesContext ctx = getFacesContext();
    Map sessionState = ctx.getExternalContext().getRequestMap();
    return sessionState.get(key);
  }

  /**
   * Pulls a String resource from the property bundle that
   * is defined under the application &lt;message-bundle&gt; element in
   * the faces config. Respects Locale
   * @param key string message key
   * @return Resource value or placeholder error String
   */
  public static String getStringFromBundle(String key)
  {
    ResourceBundle bundle = getBundle();
    return getStringSafely(bundle, key, null);
  }


  /**
   * Convenience method to construct a <code>FacesMesssage</code>
   * from a defined error key and severity
   * This assumes that the error keys follow the convention of
   * using <b>_detail</b> for the detailed part of the
   * message, otherwise the main message is returned for the
   * detail as well.
   * @param key for the error message in the resource bundle
   * @param severity severity of message
   * @return Faces Message object
   */
  public static FacesMessage getMessageFromBundle(String key,
                                                  FacesMessage.Severity severity)
  {
    ResourceBundle bundle = getBundle();
    String summary = getStringSafely(bundle, key, null);
    String detail = getStringSafely(bundle, key + "_detail", summary);
    FacesMessage message = new FacesMessage(summary, detail);
    message.setSeverity(severity);
    return message;
  }

  /**
   * Add JSF info message.
   * @param msg info message string
   */
  public static void addFacesInformationMessage(String msg)
  {
    FacesContext ctx = getFacesContext();
    FacesMessage fm =
      new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
    ctx.addMessage(getRootViewComponentId(), fm);
  }

  /**
   * Add JSF error message.
   * @param msg error message string
   */
  public static void addFacesErrorMessage(String msg)
  {
    FacesContext ctx = getFacesContext();
    FacesMessage fm =
      new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
    ctx.addMessage(getRootViewComponentId(), fm);
  }

  /**
   * Add JSF error message for a specific attribute.
   * @param attrName name of attribute
   * @param msg error message string
   */
  public static void addFacesErrorMessage(String attrName, String msg)
  {
    // TODO: Need a way to associate attribute specific messages
    //       with the UIComponent's Id! For now, just using the view id.
    //TODO: make this use the internal getMessageFromBundle?
    FacesContext ctx = getFacesContext();
    FacesMessage fm =
      new FacesMessage(FacesMessage.SEVERITY_ERROR, attrName, msg);
    ctx.addMessage(getRootViewComponentId(), fm);
  }

  // Informational getters

  /**
   * Get view id of the view root.
   * @return view id of the view root
   */
  public static String getRootViewId()
  {
    return getFacesContext().getViewRoot().getViewId();
  }

  /**
   * Get component id of the view root.
   * @return component id of the view root
   */
  public static String getRootViewComponentId()
  {
    return getFacesContext().getViewRoot().getId();
  }

  /**
   * Get FacesContext.
   * @return FacesContext
   */
  public static FacesContext getFacesContext()
  {
    return FacesContext.getCurrentInstance();
  }
  /*
   * Internal method to pull out the correct local
   * message bundle
   */

  private static ResourceBundle getBundle()
  {
    FacesContext ctx = getFacesContext();
    UIViewRoot uiRoot = ctx.getViewRoot();
    Locale locale = uiRoot.getLocale();
    ClassLoader ldr = Thread.currentThread().getContextClassLoader();
    return ResourceBundle.getBundle(ctx.getApplication().getMessageBundle(),
                                    locale, ldr);
  }

  /**
   * Get an HTTP Request attribute.
   * @param name attribute name
   * @return attribute value
   */
  public static Object getRequestAttribute(String name)
  {
    return getFacesContext().getExternalContext().getRequestMap().get(name);
  }

  /**
   * Set an HTTP Request attribute.
   * @param name attribute name
   * @param value attribute value
   */
  public static void setRequestAttribute(String name, Object value)
  {
    getFacesContext().getExternalContext().getRequestMap().put(name,
                                                               value);
  }

  /*
   * Internal method to proxy for resource keys that don't exist
   */

  private static String getStringSafely(ResourceBundle bundle, String key,
                                        String defaultValue)
  {
    String resource = null;
    try
    {
      resource = bundle.getString(key);
    }
    catch (MissingResourceException mrex)
    {
      if (defaultValue != null)
      {
        resource = defaultValue;
      }
      else
      {
        resource = NO_RESOURCE_FOUND + key;
      }
    }
    return resource;
  }

  /**
   * Locate an UIComponent in view root with its component id. Use a recursive way to achieve this.
   * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
   * @param id UIComponent id
   * @return UIComponent object
   */
  public static UIComponent findComponentInRoot(String id)
  {
    UIComponent component = null;
    FacesContext facesContext = FacesContext.getCurrentInstance();
    if (facesContext != null)
    {
      UIComponent root = facesContext.getViewRoot();
      component = findComponent(root, id);
    }
    return component;
  }

  /**
   * Locate an UIComponent from its root component.
   * Taken from http://www.jroller.com/page/mert?entry=how_to_find_a_uicomponent
   * @param base root Component (parent)
   * @param id UIComponent id
   * @return UIComponent object
   */
  public static UIComponent findComponent(UIComponent base, String id)
  {
    if (id.equals(base.getId()))
      return base;

    UIComponent children = null;
    UIComponent result = null;
    Iterator childrens = base.getFacetsAndChildren();
    while (childrens.hasNext() && (result == null))
    {
      children = (UIComponent) childrens.next();
      if (id.equals(children.getId()))
      {
        result = children;
        break;
      }
      result = findComponent(children, id);
      if (result != null)
      {
        break;
      }
    }
    return result;
  }

  /**
   * Method to create a redirect URL. The assumption is that the JSF servlet mapping is
   * "faces", which is the default
   *
   * @param view the JSP or JSPX page to redirect to
   * @return a URL to redirect to
   */
  public static String getPageURL(String view)
  {
    FacesContext facesContext = getFacesContext();
    ExternalContext externalContext = facesContext.getExternalContext();
    String url =
      ((HttpServletRequest) externalContext.getRequest()).getRequestURL().toString();
    StringBuffer newUrlBuffer = new StringBuffer();
    newUrlBuffer.append(url.substring(0, url.lastIndexOf("faces/")));
    newUrlBuffer.append("faces");
    String targetPageUrl = view.startsWith("/")? view: "/" + view;
    newUrlBuffer.append(targetPageUrl);
    return newUrlBuffer.toString();
  }
  
    /**

       * Constant for signalling failed SRService checkout during eager test.

       */

      public static final String SRSERVICE_CHECKOUT_FAILED = "SRServiceFailed";



      /**

       * Get application module for an application module data control by name.

       * @param name application module data control name

       * @return ApplicationModule

       */

      public static ApplicationModule getApplicationModuleForDataControl(String name) {

        return (ApplicationModule)JSFUtils.resolveExpression("#{data."+name+".dataProvider}");

      }

      /**

       * A convenience method for getting the value of a bound attribute in the

       * current page context programatically.

       * @param attributeName of the bound value in the pageDef

       * @return value of the attribute

       */

      public static Object getBoundAttributeValue(String attributeName) {

        return findControlBinding(attributeName).getInputValue();

      }



      /**

       * A convenience method for setting the value of a bound attribute in the

       * context of the current page.

       * @param attributeName of the bound value in the pageDef

       * @param value to set

       */

      public static void setBoundAttributeValue(String attributeName, Object value) {

        findControlBinding(attributeName).setInputValue(value);

      }



      /**

       * Returns the evaluated value of a pageDef parameter.

       * @param pageDefName reference to the page definition file of the page with the parameter

       * @param parameterName name of the pagedef parameter

       * @return evaluated value of the parameter as a String

       */

      public static Object getPageDefParameterValue(String pageDefName, 

                                                    String parameterName) {

        BindingContainer bindings = findBindingContainer(pageDefName);

        DCParameter param = ((DCBindingContainer)bindings).findParameter(parameterName);

        return param.getValue();    

      }



      /**

       * Convenience method to find a DCControlBinding as an AttributeBinding

       * to get able to then call getInputValue() or setInputValue() on it.

       * @param bindingContainer binding container

       * @param attributeName name of the attribute binding.

       * @return the control value binding with the name passed in.

       *

       */

      public static AttributeBinding findControlBinding(BindingContainer bindingContainer, 

                                                        String attributeName) {

        if (attributeName != null) {

          if (bindingContainer != null) {

            ControlBinding ctrlBinding = bindingContainer.getControlBinding(attributeName);

            if (ctrlBinding instanceof AttributeBinding) {

              return (AttributeBinding)ctrlBinding;

            }

          }

        }

        return null;

      }



      /**

       * Convenience method to find a DCControlBinding as a JUCtrlValueBinding

       * to get able to then call getInputValue() or setInputValue() on it.

       * @param attributeName name of the attribute binding.

       * @return the control value binding with the name passed in.

       *

       */

      public static AttributeBinding findControlBinding(String attributeName) {

        return findControlBinding(getBindingContainer(), attributeName);

      }



      /**

       * Return the current page's binding container.

       * @return the current page's binding container

       */

      public static BindingContainer getBindingContainer() {

        return (BindingContainer)JSFUtils.resolveExpression("#{bindings}");

      }



      /**

       * Return the Binding Container as a DCBindingContainer.

       * @return current binding container as a DCBindingContainer

       */

      public static DCBindingContainer getDCBindingContainer() {

        return (DCBindingContainer)getBindingContainer();

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding.

       * 

       * Uses the value of the 'valueAttrName' attribute as the key for

       * the SelectItem key.

       * 

       * @param iteratorName ADF iterator binding name

       * @param valueAttrName name of the value attribute to use

       * @param displayAttrName name of the attribute from iterator rows to display

       * @return ADF Faces SelectItem for an iterator binding

       */

      public static List<SelectItem> selectItemsForIterator(String iteratorName, 

                                                            String valueAttrName, 

                                                            String displayAttrName) {

        return selectItemsForIterator(findIterator(iteratorName), valueAttrName, 

                                      displayAttrName);

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding with description.

       * 

       * Uses the value of the 'valueAttrName' attribute as the key for

       * the SelectItem key.

       * 

       * @param iteratorName ADF iterator binding name

       * @param valueAttrName name of the value attribute to use

       * @param displayAttrName name of the attribute from iterator rows to display

       * @param descriptionAttrName name of the attribute to use for description

       * @return ADF Faces SelectItem for an iterator binding with description

       */

      public static List<SelectItem> selectItemsForIterator(String iteratorName, 

                                                            String valueAttrName, 

                                                            String displayAttrName, 

                                                            String descriptionAttrName) {

        return selectItemsForIterator(findIterator(iteratorName), valueAttrName, 

                                      displayAttrName, descriptionAttrName);

      }



      /**

       * Get List of attribute values for an iterator.

       * @param iteratorName ADF iterator binding name

       * @param valueAttrName value attribute to use

       * @return List of attribute values for an iterator

       */

      public static List attributeListForIterator(String iteratorName, 

                                                  String valueAttrName) {

        return attributeListForIterator(findIterator(iteratorName), valueAttrName);

      }



      /**

       * Get List of Key objects for rows in an iterator.

       * @param iteratorName iterabot binding name

       * @return List of Key objects for rows

       */

      public static List<Key> keyListForIterator(String iteratorName) {

        return keyListForIterator(findIterator(iteratorName));

      }



      /**

       * Get List of Key objects for rows in an iterator.

       * @param iter iterator binding

       * @return List of Key objects for rows

       */

      public static List<Key> keyListForIterator(DCIteratorBinding iter) {

        List attributeList = new ArrayList();

        for (Row r: iter.getAllRowsInRange()) {

          attributeList.add(r.getKey());

        }

        return attributeList;

      }



      /**

       * Get List of Key objects for rows in an iterator using key attribute.

       * @param iteratorName iterator binding name

       * @param keyAttrName name of key attribute to use

       * @return List of Key objects for rows

       */

      public static List<Key> keyAttrListForIterator(String iteratorName, 

                                                     String keyAttrName) {

        return keyAttrListForIterator(findIterator(iteratorName), keyAttrName);

      }



      /**

       * Get List of Key objects for rows in an iterator using key attribute.

       * 

       * @param iter iterator binding

       * @param keyAttrName name of key attribute to use

       * @return List of Key objects for rows

       */

      public static List<Key> keyAttrListForIterator(DCIteratorBinding iter, 

                                                     String keyAttrName) {

        List attributeList = new ArrayList();

        for (Row r: iter.getAllRowsInRange()) {

          attributeList.add(new Key(new Object[] { r.getAttribute(keyAttrName) }));

        }

        return attributeList;

      }



      /**

       * Get a List of attribute values for an iterator.

       * 

       * @param iter iterator binding

       * @param valueAttrName name of value attribute to use

       * @return List of attribute values

       */

      public static List attributeListForIterator(DCIteratorBinding iter, 

                                                  String valueAttrName) {

        List attributeList = new ArrayList();

        for (Row r: iter.getAllRowsInRange()) {

          attributeList.add(r.getAttribute(valueAttrName));

        }

        return attributeList;

      }



      /**

       * Find an iterator binding in the current binding container by name.

       * 

       * @param name iterator binding name

       * @return iterator binding

       */

      public static DCIteratorBinding findIterator(String name) {

        DCIteratorBinding iter = getDCBindingContainer().findIteratorBinding(name);

        if (iter == null) {

          throw new RuntimeException("Iterator '" + name + "' not found");

        }

        return iter;

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding with description.

       * 

       * Uses the value of the 'valueAttrName' attribute as the key for

       * the SelectItem key.

       * 

       * @param iter ADF iterator binding

       * @param valueAttrName name of value attribute to use for key

       * @param displayAttrName name of the attribute from iterator rows to display

       * @param descriptionAttrName name of the attribute for description

       * @return ADF Faces SelectItem for an iterator binding with description

       */

      public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, 

                                                            String valueAttrName, 

                                                            String displayAttrName, 

                                                            String descriptionAttrName) {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        for (Row r: iter.getAllRowsInRange()) {

          selectItems.add(new SelectItem(r.getAttribute(valueAttrName), 

                                         (String)r.getAttribute(displayAttrName), 

                                         (String)r.getAttribute(descriptionAttrName)));

        }

        return selectItems;

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding.

       * 

       * Uses the value of the 'valueAttrName' attribute as the key for

       * the SelectItem key.

       * 

       * @param iter ADF iterator binding

       * @param valueAttrName name of value attribute to use for key

       * @param displayAttrName name of the attribute from iterator rows to display

       * @return ADF Faces SelectItem for an iterator binding

       */

      public static List<SelectItem> selectItemsForIterator(DCIteratorBinding iter, 

                                                            String valueAttrName, 

                                                            String displayAttrName) {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        for (Row r: iter.getAllRowsInRange()) {

          selectItems.add(new SelectItem(r.getAttribute(valueAttrName), 

                                         (String)r.getAttribute(displayAttrName)));

        }

        return selectItems;

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding.

       * 

       * Uses the rowKey of each row as the SelectItem key.

       * 

       * @param iteratorName ADF iterator binding name

       * @param displayAttrName name of the attribute from iterator rows to display

       * @return ADF Faces SelectItem for an iterator binding

       */

      public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, 

                                                                 String displayAttrName) {

        return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName);

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding with discription.

       * 

       * Uses the rowKey of each row as the SelectItem key.

       * 

       * @param iteratorName ADF iterator binding name

       * @param displayAttrName name of the attribute from iterator rows to display

       * @param descriptionAttrName name of the attribute for description

       * @return ADF Faces SelectItem for an iterator binding with discription

       */

      public static List<SelectItem> selectItemsByKeyForIterator(String iteratorName, 

                                                                 String displayAttrName, 

                                                                 String descriptionAttrName) {

        return selectItemsByKeyForIterator(findIterator(iteratorName), displayAttrName, 

                                           descriptionAttrName);

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding with discription.

       * 

       * Uses the rowKey of each row as the SelectItem key.

       * 

       * @param iter ADF iterator binding

       * @param displayAttrName name of the attribute from iterator rows to display

       * @param descriptionAttrName name of the attribute for description

       * @return ADF Faces SelectItem for an iterator binding with discription

       */

      public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, 

                                                                 String displayAttrName, 

                                                                 String descriptionAttrName) {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        for (Row r: iter.getAllRowsInRange()) {

          selectItems.add(new SelectItem(r.getKey(),

                                         (String)r.getAttribute(displayAttrName), 

                                         (String)r.getAttribute(descriptionAttrName)));

        }

        return selectItems;

      }



      /**

       * Get List of ADF Faces SelectItem for an iterator binding.

       * 

       * Uses the rowKey of each row as the SelectItem key.

       * 

       * @param iter ADF iterator binding

       * @param displayAttrName name of the attribute from iterator rows to display

       * @return List of ADF Faces SelectItem for an iterator binding

       */

      public static List<SelectItem> selectItemsByKeyForIterator(DCIteratorBinding iter, 

                                                                 String displayAttrName) {

        List<SelectItem> selectItems = new ArrayList<SelectItem>();

        for (Row r: iter.getAllRowsInRange()) {

          selectItems.add(new SelectItem(r.getKey(), 

                                         (String)r.getAttribute(displayAttrName)));

        }

        return selectItems;

      }



      /**

       * Find the BindingContainer for a page definition by name.

       *

       * Typically used to refer eagerly to page definition parameters. It is

       * not best practice to reference or set bindings in binding containers

       * that are not the one for the current page.

       *

       * @param pageDefName name of the page defintion XML file to use

       * @return BindingContainer ref for the named definition

       */

      private static BindingContainer findBindingContainer(String pageDefName) {

        BindingContext bctx = getDCBindingContainer().getBindingContext();

        BindingContainer foundContainer = bctx.findBindingContainer(pageDefName);

        return foundContainer;

      }  

}

