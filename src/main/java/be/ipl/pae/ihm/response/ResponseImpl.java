package be.ipl.pae.ihm.response;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public class ResponseImpl implements Response {


  public static void success(HttpServletResponse resp, Map<String, String> data)
      throws IOException {

    String json = "{\"success\":\"true\"";
    for (String key : data.keySet()) {
      json += ",\"" + key + "\":" + data.get(key) + "";
    }
    json += "}";
    System.out.println("JSON generated :" + json);

    resp.setContentType("application/json");

    resp.setCharacterEncoding("UTF-8");

    resp.setStatus(HttpServletResponse.SC_OK);

    resp.getWriter().write(json);


  }

  public static void raterRequete(HttpServletResponse resp, String message) {
    try {
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_OK);
      String json = "{\"success\":\"false\",\"message\":\"" + message + "\"}";
      System.out.println(json);
      resp.getWriter().write(json);
    } catch (IOException e) {
      e.printStackTrace();
      errorServer(resp, e);
    }
  }

  public static void errorServer(HttpServletResponse resp, Exception exc) {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    String json = "{\"error\":\"false\",\"message\":\""
        + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + exc.getMessage() + "\"}";
    try {
      resp.getWriter().write(json);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
