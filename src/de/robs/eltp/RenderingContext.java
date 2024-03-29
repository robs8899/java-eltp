package de.robs.eltp;

import java.io.IOException;
import java.util.Collection;

import javax.el.ELResolver;
import javax.el.ExpressionFactory;

import de.robs.eltp.el.StdELContext;
import de.robs.eltp.ld.ResourceLoader;

public class RenderingContext {

  private final ExpressionFactory expressionFactory;
  private final TemplateRepository sourceRepository;
  private final StdELContext elContext;
  
  public RenderingContext(ResourceLoader loader, Collection<ELResolver> resolvers) {

    expressionFactory = ExpressionFactory.newInstance();
    sourceRepository = new TemplateRepository(loader);
    elContext = new StdELContext(resolvers);
    
  }

  public ExpressionFactory getExpressionFactory() {
    return expressionFactory;
  }
  
  public TemplateRepository getSourceRepository() {
    return sourceRepository;
  }

  public StdELContext getElContext() {
    return elContext;
  }

  /**
   * Convenience method to render a template.
   * @param templateName - file name of a template
   * @return the rendered result
   * @throws IOException
   */
  public String render(String templateName) throws IOException {
    return sourceRepository.get(templateName).evaluate(
        elContext, expressionFactory);
  }

  
}
