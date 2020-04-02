package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VoirUtilisateursServlet extends HttpServlet {

  private static final String JWTSECRET = new Config().getConfigPropertyAttribute("jwt.secret");
  private UserUcc userUcc;
  private List<UserDto> utilisateursDto;
  private UserDto userDto;

  /**
   * Cree un VoirUtilisateursServlet
   * 
   * @param userUCC
   * @param userDto
   */
  public VoirUtilisateursServlet(UserUcc userUCC, UserDto userDto) {
    super();
    this.userUcc = userUCC;
    this.userDto = userDto;
    this.utilisateursDto = new ArrayList<>();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      utilisateursDto = userUcc.getUtilisateurs();
      System.out.println("passage = " + utilisateursDto);
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      System.out.println(token);
      if (token != null) {
        String usersData = genson.serialize(utilisateursDto);
        String json = "{\"success\":\"true\", \"usersData\":" + usersData + "}";
        System.out.println("UsersData : " + usersData);
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      String json = "{\"error\":\"false\"}";
      System.out.println(json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }

  }

}
