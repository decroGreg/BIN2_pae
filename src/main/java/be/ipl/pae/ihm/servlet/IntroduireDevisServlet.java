package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IntroduireDevisServlet extends HttpServlet {
  private UserDto userDto;
  private UserUcc userUcc;

  public IntroduireDevisServlet(UserDto userDto, UserUcc userUcc) {
    super();
    this.userDto = userDto;
    this.userUcc = userUcc;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      Genson genson = new Genson();
      String token = req.getHeader("Authorization");
      if (token != null) {

        // String typeAmenagements = genson.serialize("");// replacer "" par la liste d'aménagement

        System.out.println("test");
      }


    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\false\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);

    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      Genson genson = new Genson();
      Map<String, Map<String, String>> data = genson.deserialize(req.getReader(), Map.class);
      System.out.println(data.get("dataQuote").toString());
      try {
        System.out.println("en attendant");
        // faire appel a la methode de l'ucc pour introduire un user et un devis

      } catch (Exception e) {
        e.printStackTrace();
        String json = "{\"success\":\false\"}";
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write(json);
        return;
      }
      String json = "{\"success\":\"true\"}";
      System.out.println("JSON generated :" + json);
      resp.setContentType("application/json");

      resp.setCharacterEncoding("UTF-8");

      resp.setStatus(HttpServletResponse.SC_OK);;
      resp.getWriter().write(json);

    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\false\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);


    }
  }

}
