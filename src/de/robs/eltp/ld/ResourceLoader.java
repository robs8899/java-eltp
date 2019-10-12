package de.robs.eltp.ld;

import java.io.IOException;

/**
 * Standard Interface for Resource Loaders.
 * @author Robert Schoch
 */
public interface ResourceLoader {

  public boolean exist(String path);
  public StringBuffer load(String path) throws IOException;
  
}
