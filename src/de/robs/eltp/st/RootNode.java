package de.robs.eltp.st;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

public class RootNode implements NodeInterface {

  private final List<NodeInterface> childs;

  public RootNode() {
    childs = new ArrayList<NodeInterface>();
  }

  public void addChild(NodeInterface node) {
    childs.add(node);
  }

  @Override
  public String evaluate(ELContext elContext, ExpressionFactory expressionFactory) {

    StringBuilder sb = new StringBuilder();

    for (NodeInterface node:childs) {
      String result = node.evaluate(elContext, expressionFactory);
      if (result != null) sb.append(result);
    }

    return sb.toString();

  }

}
