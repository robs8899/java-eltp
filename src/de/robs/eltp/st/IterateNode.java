package de.robs.eltp.st;

import java.util.Collection;
import java.util.Iterator;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

public class IterateNode implements NodeInterface {

  private final String collectionExpression;
  private final String itemName;
  private final String indexName;
  private final NodeInterface repeatNode;

  public IterateNode(String collectionExpression,
      String variableName, String indexName, NodeInterface repeatNode) {
    
    this.collectionExpression = collectionExpression;
    this.itemName = variableName;
    this.indexName = indexName;
    this.repeatNode = repeatNode;
    
  }

  @Override
  public String evaluate(ELContext elContext, ExpressionFactory expressionFactory) {

    Collection<?> c = (Collection<?>)expressionFactory.createValueExpression(
        elContext, collectionExpression, Collection.class).getValue(elContext);
    
    StringBuilder sb = new StringBuilder();
    Iterator<?> iterator = c.iterator();
    int index = 0;

    while (iterator.hasNext()) {

      elContext.getVariableMapper().setVariable(itemName,
          expressionFactory.createValueExpression(iterator.next(), Object.class));

      elContext.getVariableMapper().setVariable(indexName,
          expressionFactory.createValueExpression(index++, Integer.class));
      
      String result = repeatNode.evaluate(elContext, expressionFactory);
      if (result != null) sb.append(result);

    }

    return sb.toString();

  }

}
