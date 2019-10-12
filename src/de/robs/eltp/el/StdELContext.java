package de.robs.eltp.el;

import java.util.Collection;

import javax.el.ArrayELResolver;
import javax.el.BeanELResolver;
import javax.el.CompositeELResolver;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.FunctionMapper;
import javax.el.ListELResolver;
import javax.el.MapELResolver;
import javax.el.ResourceBundleELResolver;
import javax.el.VariableMapper;

/**
 * A simple Implementation of an ELContext. It provides a CompositeELResolver with
 * ELResolvers in this order: all custom resolvers from constructor followed by:
 * ArrayELResolver, ListELResolver, MapELResolver, ResourceBundleELResolver
 * and finally a BeanELResolver.
 * @author Robert Schoch
 */
public class StdELContext extends ELContext {

  private final CompositeELResolver elResolver;
  private final StdFunctionMapper functionMapper;
  private final StdVariableMapper variableMapper;

  public StdELContext(Collection<ELResolver> resolvers) {

    this.elResolver = new CompositeELResolver();
    for (ELResolver r:resolvers) elResolver.add(r);

    elResolver.add(new ArrayELResolver());
    elResolver.add(new ListELResolver());
    elResolver.add(new MapELResolver()); 
    elResolver.add(new ResourceBundleELResolver());
    elResolver.add(new BeanELResolver());

    functionMapper = new StdFunctionMapper();
    variableMapper = new StdVariableMapper();

  }

  public ELResolver getELResolver() {
    return elResolver;
  }

  public FunctionMapper getFunctionMapper() {
    return functionMapper;
  }

  public VariableMapper getVariableMapper() {
    return variableMapper;
  }

  /** Register all static methods from an utility class */
  public void register(Class<?> cls) {
    functionMapper.registerFunctions(cls);
  }

}
