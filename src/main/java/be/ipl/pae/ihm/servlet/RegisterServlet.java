package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.ihm.response.ResponseImpl;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
  private UserUcc userUcc;
  private UserDto userDto;

  /**
   * @param userDto l"user dto
   * @param userUcc l'user ucc
   */
  public RegisterServlet(UserUcc userUcc, UserDto userDto) {
    super();
    this.userUcc = userUcc;
    this.userDto = userDto;

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      Map<String, String> data = genson.deserialize(req.getReader(), Map.class);
      try {
        this.userDto.setEmail(data.get("mail"));
        this.userDto.setMotDePasse(data.get("mdp"));
        this.userDto.setPrenom(data.get("firstname"));
        this.userDto.setNom(data.get("lastname"));
        this.userDto.setVille(data.get("city"));
        this.userDto.setPseudo(data.get("pseudo"));

        this.userUcc.sinscrire(userDto);

        String json = "{\"success\":\"true\",\"message\":\"" + "inscription reussit" + "\"}";
        ResponseImpl.success(resp, json);
      } catch (Exception exce) {
        exce.printStackTrace();
        // String json = "{\"success\":\"false\",\"message\":\"" + exce.getMessage() + "\"}";
        ResponseImpl.raterRequete(resp, exce.getMessage());

      }
    } catch (Exception exce) {
      exce.printStackTrace();
      ResponseImpl.errorServer(resp, exce);
    }
  }

}
