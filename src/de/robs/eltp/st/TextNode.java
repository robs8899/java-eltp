package de.robs.eltp.st;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

public class TextNode implements NodeInterface {

  private final String textExpression;

  public TextNode(String textExpression) {
    this.textExpression = textExpression;
  }

  @Override
  public String evaluate(ELContext elContext, ExpressionFactory expressionFactory) {
    
    ValueExpression expression = expressionFactory.createValueExpression(elContext, textExpression, Object.class);
    return expression.getValue(elContext).toString();
    
  }
  
}
