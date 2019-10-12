package de.robs.eltp.st;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

public class IncludeNode implements NodeInterface {

  private final NodeInterface includeNode;

  public IncludeNode(NodeInterface includeNode) {
    this.includeNode = includeNode;
  }

  @Override
  public String evaluate(ELContext elContext, ExpressionFactory expressionFactory) {

    return includeNode.evaluate(elContext, expressionFactory);

  }
  
}
