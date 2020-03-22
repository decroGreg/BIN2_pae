package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.interfaces.UserUCC;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
  private UserUCC userUcc;
  private UserDTO userDto;

  public RegisterServlet(UserUCC userUcc, UserDTO userDto) {
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
      int i = 0;
      if (i < 3) {// vérification

      } else {
        String json = "{\"success\":\false\"}";
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write(json);

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

}
