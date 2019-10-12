package de.robs.eltp.st;

import java.text.DateFormat;
import java.util.Date;

import javax.el.ELContext;
import javax.el.ExpressionFactory;

public class DateNode implements NodeInterface {

  private final String dateExpression;
  private final String formatterExpression;

  public DateNode(String numberExpression, String formatterExpression) {

    this.dateExpression = numberExpression;
    this.formatterExpression = formatterExpression;

  }

  @Override
  public String evaluate(ELContext elContext, ExpressionFactory expressionFactory) {

    Date value = (Date)expressionFactory.createValueExpression(
        elContext, dateExpression, Date.class).getValue(elContext);

    DateFormat formatter = (DateFormat)expressionFactory.createValueExpression(
        elContext, formatterExpression, DateFormat.class).getValue(elContext);

    return formatter.format(value);

  }

}
