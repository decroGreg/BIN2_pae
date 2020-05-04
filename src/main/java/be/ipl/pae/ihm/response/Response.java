package be.ipl.pae.ihm.response;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface Response {
  public static void success(HttpServletResponse resp, Map<String, String> data) {}

  public static void test(HttpServletResponse resp, String message) {}

  public static void errorJson(HttpServletResponse resp, Exception exc) {}
}
