package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.ihm.response.ResponseImpl;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NomClientsServlet extends HttpServlet {

  private UserUcc userUcc;

  /**
   * Cree un objet NomClientsServlet.
   * 
   * @param userUcc l'user Ucc
   */
  public NomClientsServlet(UserUcc userUcc) {
    this.userUcc = userUcc;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      String token = req.getHeader("Authorization");
      if (token != null) {
        List<String> noms = userUcc.getNomUtilisateurs();
        Genson genson = new Genson();
        String nomsString = genson.serialize(noms);
        System.err.println("************************************");
        String json =
            "{\"success\":\"true\", \"token\":\"" + token + "\", \"noms\":" + nomsString + "}";
        ResponseImpl.success(resp, json);
        return;
      } else {
        ResponseImpl.raterRequete(resp, "token est null");
      }
    } catch (Exception exc) {
      exc.printStackTrace();
      ResponseImpl.errorServer(resp, exc);
    }
  }

}
