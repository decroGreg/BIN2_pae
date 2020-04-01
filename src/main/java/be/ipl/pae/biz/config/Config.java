package be.ipl.pae.biz.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Config {

  private Properties props;

  /**
   * Cree un objet Config.
   */
  public Config() {
    super();
    props = new Properties();
    try {
      props.load(new FileInputStream("./dependencies.properties"));
    } catch (IOException ie) {
      ie.printStackTrace();
    }
  }

  public Object getConfigPropertyClass(String property) {
    Object objetRenvoye;
    try {
      String prop = props.getProperty(property);
      Class<?> cls = Class.forName(prop);
      objetRenvoye = cls.getDeclaredConstructor().newInstance();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new InternalError();
    }
    return objetRenvoye;
  }

  public String getConfigPropertyAttribute(String property) {
    String prop;
    try {
      prop = props.getProperty(property);
    } catch (Exception ex) {
      throw new InternalError();
    }
    return prop;
  }

}
