package de.robs.eltp.st;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

public class VarNode implements NodeInterface {

  private final String variableName;
  private final String expressionString;

  public VarNode(String variableName, String expressionString) {
    
    this.variableName = variableName;
    this.expressionString = expressionString;
    
  }

  @Override
  public String evaluate(ELContext elContext, ExpressionFactory expressionFactory) {

    Object value = expressionFactory.createValueExpression(
        elContext, expressionString, Object.class).getValue(elContext);
    
      elContext.getVariableMapper().setVariable(variableName,
          expressionFactory.createValueExpression(value, Object.class));

    return null;

  }

}
