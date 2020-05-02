package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.interfaces.AmenagementUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AmenagementServlet extends HttpServlet {
  private AmenagementUcc amenagementUcc;

  public AmenagementServlet(AmenagementUcc amenagementUcc) {
    this.amenagementUcc = amenagementUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      Genson genson = new Genson();
      String description =
          genson.deserialize(req.getReader(), Map.class).get("description").toString();
      String token = req.getHeader("Authorization");
      if (token != null) {
        // methode
        String json = "{\"success\":\"true\", \"token\":\"" + token + "\"}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

      } else {
        String json = "{\"success\":\"false\", \"token\":\"" + token
            + "\",\"message\":\"vous n'etes pas connecté\"}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
      }

    } catch (Exception exce) {
      exce.printStackTrace();
      String json = "{\"error\":\"false\"}";
      System.out.println(json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);

    }

  }

}
