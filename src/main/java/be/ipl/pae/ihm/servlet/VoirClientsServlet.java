package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;

public class VoirClientsServlet extends HttpServlet {

  private UserUcc userUCC;
  private List<ClientDto> clientsDto;
  private UserDto userDto;

  public VoirClientsServlet(UserUcc userUCC, UserDto userDto) {
    super();
    this.userUCC = userUCC;
    this.userDto = userDto;
    this.clientsDto = new ArrayList<>();

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      // clientsDto = userUCC.getClients();
      System.out.println(clientsDto.toString());
      Genson genson = new Genson();
      // Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      // String token = data.get("token").toString();
      String token = req.getHeader("Authorization");
      System.out.println(token);
      if (token != null) {
        for (ClientDto client : clientsDto) {
          String clientsData = genson.serialize(client);
          String json = "{\"success\":\"true\", \"userData\":" + clientsData + "}";
          System.out.println("UsersData : " + clientsData);
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


}
