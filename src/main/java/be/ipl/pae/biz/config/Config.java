package be.ipl.pae.biz.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Config {

  private Properties props;

  public Config() {
    super();
    props = new Properties();
    try {
      props.load(new FileInputStream("./dependencies.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Object getConfigPropertyClass(String property) {
    Object aRenvoyer;
    try {
      String prop = props.getProperty(property);
      Class<?> cls = Class.forName(prop);
      aRenvoyer = cls.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      e.printStackTrace();
      throw new InternalError();
    }
    return aRenvoyer;
  }

  public String getConfigPropertyAttribute(String property) {
    String prop;
    try {
      prop = props.getProperty(property);
    } catch (Exception e) {
      throw new InternalError();
    }
    return prop;
  }

}
