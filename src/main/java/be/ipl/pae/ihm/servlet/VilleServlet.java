package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.ihm.response.Response;

import com.owlike.genson.Genson;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VilleServlet extends HttpServlet {
  private ClientUcc clientUcc;


  /**
   * Cree un objet VilleServlet.
   * 
   * @param clientUcc le client ucc
   * 
   */
  public VilleServlet(ClientUcc clientUcc) {
    // TODO Auto-generated constructor stub
    this.clientUcc = clientUcc;

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      String token = req.getHeader("Authorization");
      if (token != null) {
        System.out.println("passage");
        Genson genson = new Genson();
        String villes = genson.serialize(clientUcc.getVilles());
        String json =
            "{\"success\":\"true\", \"token\":\"" + token + "\", \"villes\":" + villes + "}";
        Response.success(resp, json);
      } else {
        // String json = "{\"success\":\"false\", \"message\":\"non connecte\"}";
        Response.raterRequete(resp, "token est null");
      }
    } catch (Exception exc) {
      exc.printStackTrace();
      Response.errorServer(resp, exc);
    }

  }


}
