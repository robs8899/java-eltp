package de.robs.eltp.el;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.el.FunctionMapper;

public class StdFunctionMapper extends FunctionMapper {

  private final Map<String, Object> functions = new HashMap<String, Object>();

  public void registerFunctions(Class<?> cls) {

    String clsName = cls.getSimpleName();
    Method[] methods = cls.getMethods();

    for(Method method : methods) {

      if((method.getModifiers() & Modifier.STATIC) > 0) {

        String mthName = method.getName();
        String key = clsName + ':' + mthName;

        if(functions.put(key, method) != null) throw new RuntimeException(
            "multiple signatures found for " + key);

      }

    }

  }

  public Method resolveFunction(String prefix, String localName) {

    String key = prefix + ':' + localName;
    Method method = (Method)functions.get(key);
    return method;

  }

}