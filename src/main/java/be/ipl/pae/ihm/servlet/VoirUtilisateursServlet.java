package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    // creation de 2 usersDTO dans ma liste pour tester si mon html se remplit
    user1.setPseudo("mrbrg");
    user1.setPrenom("M");
    user1.setNom("B");
    user1.setEmail("mrbrg@live.fr");
    user1.setVille("Bruxdells");
    user1.setStatut('c');
    user1.setDateInscription(Timestamp.valueOf(LocalDateTime.now()));
    user1.setMotDePasse("mdp");
    user2.setPseudo("jbe");
    user2.setPrenom("Mdef");
    user2.setNom("Bdd");
    user2.setEmail("mrbrdddddddg@live.fr");
    user2.setVille("Brudddxdells");
    user2.setStatut('e');
    user2.setDateInscription(Timestamp.valueOf(LocalDateTime.now()));
    user2.setMotDePasse("fgr");

    utilisateursDTO.add(user1);
    utilisateursDTO.add(user2);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      // utilisateursDTO = userUCC.getUtilisateurs();
      System.out.println(utilisateursDTO.toString());
      System.out.println("passage");
      Genson genson = new Genson();
      // Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      // String token = data.get("token").toString();
      String token = req.getHeader("Authorization");
      System.out.println(token);
      if (token != null) {
        System.out.println("test");
        String usersData = genson.serialize(utilisateursDTO);
        String json = "{\"success\":\"true\", \"usersData\":" + usersData + "}";
        System.out.println("UsersData : " + usersData);
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

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doPost(req, resp);
  }

}
