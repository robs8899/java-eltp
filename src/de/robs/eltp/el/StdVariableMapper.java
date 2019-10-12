package de.robs.eltp.el;

import java.util.HashMap;
import java.util.Map;

import javax.el.ValueExpression;
import javax.el.VariableMapper;

public class StdVariableMapper extends VariableMapper {

  private final Map<String, Object> variables = new HashMap<String, Object>();

  public ValueExpression resolveVariable(String s) {
    return (ValueExpression)variables.get(s);
  }

  public ValueExpression setVariable(String s, ValueExpression valueExpression) {
    return (ValueExpression)variables.put(s, valueExpression);
  }

}
