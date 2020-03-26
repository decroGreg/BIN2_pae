package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;

public class VoirUtilisateursServlet extends HttpServlet {

  private UserUcc userUCC;
  private List<UserDto> utilisateursDTO;
  private UserDto userDto;
  Factory f = new FactoryImpl();
  private UserDto user1 = f.getUserDto();
  private UserDto user2 = f.getUserDto();

  public VoirUtilisateursServlet(UserUcc userUCC, UserDto userDto) {
    super();
    this.userUCC = userUCC;
    this.userDto = userDto;
    this.utilisateursDTO = new ArrayList<>();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      utilisateursDTO = userUCC.getUtilisateurs();
      System.out.println(utilisateursDTO.toString());
      Genson genson = new Genson();
      // Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      // String token = data.get("token").toString();
      String token = req.getHeader("Authorization");

      if (token != null) {
        for (UserDto user : utilisateursDTO) {
          String usersData = genson.serialize(user);
          String json = "{\"success\":\"true\", \"userData\":" + usersData + "}";
          System.out.println("JSON generated :" + json);

          resp.setContentType("application/json");

          resp.setCharacterEncoding("UTF-8");

          resp.setStatus(HttpServletResponse.SC_OK);;
          resp.getWriter().write(json);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
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
    super.doPost(req, resp);
  }

}
