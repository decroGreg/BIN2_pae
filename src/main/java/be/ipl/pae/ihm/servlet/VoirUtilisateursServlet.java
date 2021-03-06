package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.ihm.response.Response;

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

  /**
   * Cree un VoirUtilisateursServlet.
   * 
   * @param userUcc un userUcc
   * 
   */
  public VoirUtilisateursServlet(UserUcc userUcc) {
    super();
    this.userUcc = userUcc;
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
        Response.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      Response.errorServer(resp, ex);
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

        System.out.println(utilisateursDto.toString());
        String usersData = genson.serialize(utilisateursDto);
        String json = "{\"success\":\"true\", \"usersData\":" + usersData + "}";
        System.out.println("UsersData : " + usersData);
        Response.success(resp, json);

      } else {
        String json = "{\"success\":\"false\", \"token\":\"" + token
            + "\", \"message\":\"vous devez etre connecte pour pouvoir effectuer cette action\"}";
        Response.raterRequete(resp, json);
      }

    } catch (Exception exce) {
      exce.printStackTrace();
      Response.errorServer(resp, exce);


    }
  }

}
