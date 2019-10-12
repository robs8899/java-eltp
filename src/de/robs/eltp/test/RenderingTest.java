package de.robs.eltp.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.el.ELResolver;

import de.robs.eltp.RenderingContext;
import de.robs.eltp.el.StdELResolver;
import de.robs.eltp.ld.ClassPathLoader;
import de.robs.eltp.ld.ResourceLoader;

public class RenderingTest {

  private RenderingTest() {}

  // convenience method to render a template
  private static String render(RenderingContext ctx, String templateName) throws IOException {
    return ctx.getSourceRepository().get(templateName).evaluate(
        ctx.getElContext(), ctx.getExpressionFactory());
  }

  public static void main(String[] args) {

    try {

      long t0 = System.nanoTime();

      // prepare loader, model and resolvers
      ResourceLoader loader = new ClassPathLoader("de/robs/eltp/test/", "utf-8");
      Map<String, Object> modelMap = new HashMap<String, Object>();
      Collection<ELResolver> resolvers = new ArrayList<ELResolver>();
      resolvers.add(new StdELResolver(modelMap));

      // create a new rendering context and register some utility functions
      RenderingContext ctx = new RenderingContext(loader, resolvers);
      ctx.getElContext().register(TestUtils.class);

      // --- create the model ---
      modelMap.put("dateFormat", new SimpleDateFormat("dd.MM.yyyy hh:mm:ss", Locale.GERMANY));
      modelMap.put("numberFormat", new DecimalFormat("###,##0.00", new DecimalFormatSymbols(Locale.GERMANY)));

      modelMap.put("testDate", new Date());
      modelMap.put("testNumber", new BigDecimal("123456.789"));
      modelMap.put("testList1", Arrays.asList("Element #1", "Element #2", "Element #3"));
      modelMap.put("testList2", Arrays.asList(
          Arrays.asList("Element #1a", "Element #2a", "Element #3a"),
          Arrays.asList("Element #1b", "Element #2b", "Element #3b"),
          Arrays.asList("Element #1c", "Element #2c", "Element #3c")
          ));

      modelMap.put("testBean", new TestBean(123, "HelloBean"));

      long t1 = System.nanoTime();
      System.out.println("--->> init in " + (t1-t0)/1000 + " micros.");

      // --- first rendering ---
      System.out.println(render(ctx, "template.txt"));
      long t2 = System.nanoTime();
      System.out.println("--->> first rendering in " + (t2-t1)/1000 + " micros.");

      // --- second rendering ---
      System.out.println(render(ctx, "template.txt"));
      long t3 = System.nanoTime();
      System.out.println("--->> second rendering in " + (t3-t2)/1000 + " micros.");

      // --- third rendering ---
      System.out.println(render(ctx, "template.txt"));
      long t4 = System.nanoTime();
      System.out.println("--->> third rendering in " + (t4-t3)/1000 + " micros.");

    } catch (Exception e) {

      e.printStackTrace(System.out);

    }

  }

}
