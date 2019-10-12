package de.robs.eltp.ld;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;

/**
 * Resource Loader implementation for Class Path Resources.
 * This should be thread safe.
 * @author Robert Schoch
 */
public class ClassPathLoader implements ResourceLoader {

  private final String basePath;
  private final String encoding;

  public ClassPathLoader(String basePath, String encoding) {

    this.basePath = basePath;
    this.encoding = encoding;

  }

  @Override
  public boolean exist(String path) {

    String fullPath = basePath + path;
    URL url = ctxcl().getResource(fullPath);
    return (url != null) ? true : false;
    
  }

  @Override
  public StringBuffer load(String path) throws IOException {

    String fullPath = basePath + path;
    InputStream is = ctxcl().getResourceAsStream(fullPath);
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

  private static ClassLoader ctxcl() {
    
    return Thread.currentThread().getContextClassLoader();
    
  }
  
}
