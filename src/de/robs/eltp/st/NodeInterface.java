package de.robs.eltp.st;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

public interface NodeInterface {

  public abstract String evaluate(ELContext elContext,
      ExpressionFactory expressionFactory);
  
}
