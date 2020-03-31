package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.ClientUcc;

public class VoirClientsServlet extends HttpServlet {

  private ClientUcc clientUcc;
  private List<ClientDto> clientsDto;
  private UserDto userDto;

  public VoirClientsServlet(ClientUcc clientUcc, UserDto userDto) {
    super();
    this.clientUcc = clientUcc;
    this.userDto = userDto;
    this.clientsDto = new ArrayList<>();

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      clientsDto = clientUcc.getClients();
      System.out.println(clientsDto.toString());
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      // token = "t";
      if (token != null) {
        String clientsData = genson.serialize(clientsDto);
        String json = "{\"success\":\"true\", \"clientsData\":" + clientsData + "}";
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);

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
