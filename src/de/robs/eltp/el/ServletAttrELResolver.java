package de.robs.eltp.el;

import java.beans.FeatureDescriptor;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Resolve Models in Servlet Attributes in the following
 * order 1:Request, 2:Session, 3:Context.
 * @author Robert Schoch
 */
public class ServletAttrELResolver extends ELResolver {

  private final HttpServletRequest request;
  private final Set<String> attrNames;

  public ServletAttrELResolver(HttpServletRequest req) {

    this.request = req;
    this.attrNames = new HashSet<String>();
    Enumeration<?> attrEnum = null;

    // Collect all Attribute Names in a single HashSet.
    
    attrEnum = request.getAttributeNames();
    while (attrEnum.hasMoreElements()) attrNames.add(
        (String)attrEnum.nextElement());

    HttpSession session = request.getSession(false);

    if (session != null) {
      attrEnum = session.getAttributeNames();
      while (attrEnum.hasMoreElements()) attrNames.add(
          (String)attrEnum.nextElement());
    }

    ServletContext context = request.getServletContext();
    attrEnum = context.getAttributeNames();
    while (attrEnum.hasMoreElements()) attrNames.add(
        (String)attrEnum.nextElement());

  }

  @Override
  public Class<?> getCommonPropertyType(ELContext context, Object base) {
    return null; // not supported.
  }

  @Override
  public Iterator<FeatureDescriptor> getFeatureDescriptors(ELContext context, Object base) {
    return null; // not supported.
  }

  @Override
  public Class<?> getType(ELContext context, Object base, Object property) {
    return null; // unused, the resolver is read only.
  }

  @Override
  public Object getValue(ELContext elContext, Object base, Object property) {

    if (!canResolve(elContext, base, property)) return null;
    elContext.setPropertyResolved(true);
    
    String key = (String)property;
    Object value = request.getAttribute(key);

    if (value == null) {
      HttpSession session = request.getSession(false);
      if (session != null) value = session.getAttribute(key);
    }

    if (value == null) value = request.getServletContext().getAttribute(key);
    return value;

  }

  @Override
  public boolean isReadOnly(ELContext elContext, Object base, Object property) {
    if (!canResolve(elContext, base, property)) return false;
    elContext.setPropertyResolved(true);
    return true;
  }

  @Override
  public void setValue(ELContext context, Object base, Object property, Object value) {
    // unused, the resolver is read only.
  }

  /** Check if we are responsible for this base/property pair. */
  private boolean canResolve(ELContext elContext, Object base, Object property) {

    if (base != null) return false; // top level properties only
    if (!(property instanceof String)) return false; // strings only
    return attrNames.contains(property); // check if property is an attribute name

  }

}
