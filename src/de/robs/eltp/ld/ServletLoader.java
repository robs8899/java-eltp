package de.robs.eltp.ld;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

/**
 * Resource Loader implementation for Servlet Resources.
 * This should be thread safe.
 * @author Robert Schoch
 */
public class ServletLoader implements ResourceLoader {

  private final ServletContext servletContext;
  private final String basePath;
  private final String encoding;

  public ServletLoader(ServletContext servletContext, String basePath, String encoding) {

    this.servletContext = servletContext;
    this.basePath = basePath;
    this.encoding = encoding;

  }

  @Override
  public boolean exist(String path) {

    String fullPath = basePath + path;
    URL url = null;
    
    try { url = servletContext.getResource(fullPath);
    } catch (MalformedURLException e) {
      // ignore it silently and return false
    }

    return (url != null) ? true : false;
    
  }

  @Override
  public StringBuffer load(String path) throws IOException {

    String fullPath = basePath + path;
    InputStream is = servletContext.getResourceAsStream(fullPath);
    if (is == null) throw new IOException("Resource not found: " + fullPath);

    try {

      int len = 0;
      char cb[] = new char[0x100]; // 256 Bytes buffer size
      StringWriter sw = new StringWriter(0x1000); // 4K initial size
      InputStreamReader isr = new InputStreamReader(is, encoding);
      while ((len = isr.read(cb, 0, cb.length)) > -1) sw.write(cb, 0, len);
      return sw.getBuffer();

    } finally {

      is.close();

    }

  }

}
