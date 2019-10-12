package de.robs.eltp.el;

import java.beans.FeatureDescriptor;
import java.util.Iterator;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ELResolver;

/**
 * Resolve Models in a ModelMap.
 * @author Robert Schoch
 */
public class StdELResolver extends ELResolver {

  private final Map<String, Object> modelMap;
  
  public StdELResolver(Map<String, Object> modelMap) {
    this.modelMap = modelMap;
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
    return modelMap.get((String)property);
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
    if (!modelMap.containsKey((String)property)) return false; // unknown property
    return true;
  }
  
}
