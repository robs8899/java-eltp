package de.robs.eltp.st;

import java.text.NumberFormat;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

public class NumberNode implements NodeInterface {

  private final String numberExpression;
  private final String formatterExpression;

  public NumberNode(String numberExpression, String formatterExpression) {

    this.numberExpression = numberExpression;
    this.formatterExpression = formatterExpression;

  }

  @Override
  public String evaluate(ELContext elContext, ExpressionFactory expressionFactory) {

    Number value = (Number)expressionFactory.createValueExpression(
        elContext, numberExpression, Number.class).getValue(elContext);

    NumberFormat formatter = (NumberFormat)expressionFactory.createValueExpression(
        elContext, formatterExpression, NumberFormat.class).getValue(elContext);

    return formatter.format(value);

  }

}
