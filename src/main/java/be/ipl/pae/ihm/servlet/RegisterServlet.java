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

public class RegisterServlet extends HttpServlet {
  private UserUcc userUcc;
  private UserDto userDto;

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
      this.userDto.setEmail(data.get("mail"));
      this.userDto.setMotDePasse(data.get("mdp"));
      this.userDto.setPrenom(data.get("firstname"));
      this.userDto.setNom(data.get("lastname"));
      this.userDto.setVille(data.get("city"));
      this.userDto.setPseudo(data.get("pseudo"));


      try {// vérification
        this.userUcc.sinscrire(userDto);

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
