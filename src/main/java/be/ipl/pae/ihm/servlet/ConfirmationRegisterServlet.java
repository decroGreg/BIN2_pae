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

public class ConfirmationRegisterServlet extends HttpServlet {
  private UserUcc userUcc;
  private UserDto userDto;

  public ConfirmationRegisterServlet(UserUcc userUcc, UserDto userDto) {
    // TODO Auto-generated constructor stub
    super();
    this.userDto = userDto;
    this.userUcc = userUcc;

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = data.get("token").toString();
      String usersJson = null;
      if (token != null) {
        // usersJson=genson.serialize(.....);

        String json = "{\"success\":\"true\", \"usersData\":" + usersJson + "}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);
      }
    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\"false\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }

  }

}
