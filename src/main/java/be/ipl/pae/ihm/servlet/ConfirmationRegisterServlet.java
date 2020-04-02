package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.ClientDto;
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
  private ClientDto clientDto;
  private static final String JWTSECRET = new Config().getConfigPropertyAttribute("jwt.secret");

  public ConfirmationRegisterServlet(UserUcc userUcc, UserDto userDto, ClientDto clientDto) {
    // TODO Auto-generated constructor stub
    super();
    this.userDto = userDto;
    this.userUcc = userUcc;
    this.clientDto = clientDto;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      String usersJson = null;
      if (token != null) {


        usersJson = genson.serialize(userUcc.voirUtilisateurEnAttente());
        // TO DO js-> tableau
        String json = "{\"success\":\"true\", \"usersData\":" + usersJson + "}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);
      } else {
        System.err.println("token est NULL");
        String json = "{\"success\":\false\"}";
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write(json);
      }
    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\"false\",\"message\":\"" + e.getMessage() + "\"}";
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
      Map<String, String> data = genson.deserialize(req.getReader(), Map.class);
      System.out.println("--------" + data.toString());
      try {
        int idClient = 0;
        int idUtilConfirm = Integer.parseInt(data.get("id"));
        String token = req.getHeader("Authorization");
        System.out.println(token);
        if (token != null) {
          try {
            idClient = Integer.parseInt(data.get("client"));
          } catch (Exception e) {
            e.printStackTrace();
          }

          userDto.setIdUser(idUtilConfirm);
          clientDto.setIdClient(idClient);


        System.out.println("XXXXX" + data.get("status"));
        char statut = Character.toUpperCase(data.get("status").charAt(0));
        userUcc.confirmerInscription(userDto, clientDto, statut);



          String json = "{\"success\":\"true\",\"message\":\"" + "Enregistrement confirmé" + "\"}";
          System.out.println("JSON generated :" + json);
          resp.setContentType("application/json");

          resp.setCharacterEncoding("UTF-8");

          resp.setStatus(HttpServletResponse.SC_OK);
          resp.getWriter().write(json);

        }
      } catch (Exception exc) {
        System.err.println("token est NULL");
        String json = "{\"success\":\false\",\"message\":\"" + "échec de la confirmation:"
            + exc.getMessage() + "\"}";
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
      }

    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\"false\",\"message\":\"" + e.getMessage() + "\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }
  }

}
