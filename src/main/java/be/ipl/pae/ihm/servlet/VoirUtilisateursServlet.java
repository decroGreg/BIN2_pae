package be.ipl.pae.ihm.servlet;

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


  private UserUcc userUcc;
  private List<UserDto> utilisateursDto;
  private UserDto userDto;

  /**
   * Cree un VoirUtilisateursServlet.
   * 
   * @param userUCC un userUcc
   * @param userDto un userDto
   */
  public VoirUtilisateursServlet(UserUcc userUcc, UserDto userDto) {
    super();
    this.userUcc = userUcc;
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
      String token = req.getHeader("Authorization");
      System.out.println(token);
      if (token != null) {
        String usersData = genson.serialize(utilisateursDto);
        String json = "{\"success\":\"true\", \"usersData\":" + usersData + "}";
        System.out.println("UsersData : " + usersData);
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
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

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      String token = req.getHeader("Authorization");
      System.out.println(token);
      if (token != null) {
        Map<String, String> data = genson.deserialize(req.getReader(), Map.class);

        String nom = data.get("name");
        String ville = data.get("city");
        System.out.println("nom = " + nom);
        utilisateursDto = userUcc.rechercherUtilisateurs(nom, ville);


        String usersData = genson.serialize(utilisateursDto);
        String json = "{\"success\":\"true\", \"usersData\":" + usersData + "}";
        System.out.println("UsersData : " + usersData);
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

      } else {
        String json = "{\"success\":\"false\", \"token\":\"" + token
            + "\", \"message\":\"vous devez etre connecte pour pouvoir effectuer cette action\"}";
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
