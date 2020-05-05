package be.ipl.pae.ihm.response;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public interface Response {

  /**
   * success ecrit dans la requete resp en cas de succes.
   * 
   * @param resp l httpServletRexponse
   * @param json le json des donnees a renvoyer
   * @throws IOException exception lance par la methode write
   */
  static void success(HttpServletResponse resp, String json) throws IOException {
    System.out.println("JSON generated : success");

    resp.setContentType("application/json");

    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(HttpServletResponse.SC_OK);

    resp.getWriter().write(json);

  }

  /**
   * raterRequete ecrit dans la requete resp lorsque le resultat.
   * 
   * @param resp l httpServletRexponse
   * @param message le message a afficher
   * @throws IOException exception lance par la methode write
   */

  static void raterRequete(HttpServletResponse resp, String message) throws IOException {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(HttpServletResponse.SC_OK);
    String json = "{\"success\":\"false\",\"message\":\"" + message + "\"}";
    System.out.println(json);
    resp.getWriter().write(json);
  }

  /**
   * errroServer ecrit dans la requete resp lors d'une erreur Server.
   * 
   * @param resp l httpServletRexponse
   * @param exc l exception qui a ete lance
   */
  static void errorServer(HttpServletResponse resp, Exception exc) {
    resp.setContentType("application/json");
    resp.setCharacterEncoding("UTF-8");
    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    String json = "{\"error\":\"false\",\"message\":\""
        + HttpServletResponse.SC_INTERNAL_SERVER_ERROR + exc.getMessage() + "\"}";
    try {
      resp.getWriter().write(json);
    } catch (IOException exce) {
      // TODO Auto-generated catch block
      exce.printStackTrace();
    }
  }
}
