package de.robs.eltp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import de.robs.eltp.ld.ResourceLoader;
import de.robs.eltp.st.DateNode;
import de.robs.eltp.st.IterateNode;
import de.robs.eltp.st.NumberNode;
import de.robs.eltp.st.RootNode;
import de.robs.eltp.st.TextNode;
import de.robs.eltp.st.VarNode;

public class TemplateRepository {

  private static final Pattern CMD_SPLIT_PATTERN = Pattern.compile("[:,]");
  private static final String CMD_START = "#[";
  private static final String CMD_END = "]";
  
  private static final Map<String, RootNode> resourceCache = new HashMap<String, RootNode>();
  private final ResourceLoader resourceLoader;
  private int recursiveCount;
  
  public TemplateRepository(ResourceLoader resourceLoader) {

    this.resourceLoader = resourceLoader;
    
  }

  /**
   * This ist synchronized because sourceMap is static, to minimize
   * I/O traffic from cache misses for every new instance.
   */
  public synchronized RootNode get(String path) throws IOException {
    
    if (resourceCache.containsKey(path))
      return resourceCache.get(path);
    
    recursiveCount = 0;
    RootNode source = build(path);
    resourceCache.put(path, source);
    return source;
    
  }
  
  private RootNode build(String path) throws IOException {

    if (++recursiveCount > 25) throw new RuntimeException("too many recursions.");
    StringBuffer buffer = resourceLoader.load(path);

    RootNode rootNode = new RootNode();
    int start = 0, end = 0, last = 0;

    while ((start = buffer.indexOf(CMD_START, end)) > -1 &&
        (end = buffer.indexOf(CMD_END, start)) > start) {

      if (start > last) {
        String text = buffer.substring(last, start);
        rootNode.addChild(new TextNode(text));
      }

      last = end+1;
      String command = buffer.substring(start, last);
      String[] tokens = CMD_SPLIT_PATTERN.split(
          command.subSequence(2, command.length()-1));
      String instruction = tokens[0].trim();

      // TODO check number of arguments to prevent IndexOutOfBound Exceptions!
      if (instruction.equals("var")) rootNode.addChild(new VarNode(tokens[1].trim(), tokens[2].trim()));
      if (instruction.equals("date")) rootNode.addChild(new DateNode(tokens[1].trim(), tokens[2].trim()));
      if (instruction.equals("number")) rootNode.addChild(new NumberNode(tokens[1].trim(), tokens[2].trim()));
      if (instruction.equals("include")) rootNode.addChild(build(tokens[1].trim())); // add the RootNode of thr 
      if (instruction.equals("iterate")) rootNode.addChild(new IterateNode(tokens[1].trim(),
          tokens[2].trim(), tokens[3].trim(), build(tokens[4].trim())));
      
    }

    if (last < buffer.length()) {
      String text = buffer.substring(last, buffer.length());
      rootNode.addChild(new TextNode(text));
    }
    
    --recursiveCount;
    return rootNode;
    
  }
  
}
