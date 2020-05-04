package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.interfaces.ClientUcc;

import com.owlike.genson.Genson;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VilleServlet extends HttpServlet {
  private ClientUcc clientUcc;

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
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
      } else {
        String json = "{\"success\":\"false\", \"message\":\"non connecte\"}";
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
      }
    } catch (Exception exc) {
      exc.printStackTrace();
      String json = "{\"error\":\"false\"}";
      System.out.println(json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }

  }


}
