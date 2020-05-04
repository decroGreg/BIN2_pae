package be.ipl.pae.ihm.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class ResponseImpl implements Response {


  public static void success(HttpServletResponse resp, String json) throws IOException {
    System.out.println("JSON generated : success");

    resp.setContentType("application/json");

    resp.setCharacterEncoding("UTF-8");

    resp.setStatus(HttpServletResponse.SC_OK);

    resp.getWriter().write(json);


  }

  public static void raterRequete(HttpServletResponse resp, String message) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(HttpServletResponse.SC_OK);
    String json = "{\"success\":\"false\",\"message\":\"" + message + "\"}";
    System.out.println(json);
    resp.getWriter().write(json);
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
