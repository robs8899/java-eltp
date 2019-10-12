package de.robs.eltp.test;

import java.util.Date;

public class TestUtils {

  private TestUtils() {}
  
  public static String sayHello(Date date) {
    return "Hello from TestUtils, today is " + date.toString(); 
  }
  
}
