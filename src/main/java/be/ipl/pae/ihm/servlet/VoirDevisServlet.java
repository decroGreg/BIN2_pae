package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;

public class VoirDevisServlet extends HttpServlet {

  private UserUcc userUCC;
  private UserDto userDto;
  private List<DevisDto> listeDevisDto;
  Factory f = new FactoryImpl();
  private DevisDto devis1 = f.getDevisDto();
  private DevisDto devis2 = f.getDevisDto();

  public VoirDevisServlet(UserUcc userUCC, UserDto userDto) {
    super();
    this.userUCC = userUCC;
    this.userDto = userDto;
    this.listeDevisDto = new ArrayList<>();
    // devis1.setId= 1;
    devis1.setDate(Timestamp.valueOf(LocalDateTime.now()));
    devis1.setDureeTravaux("5 jours");
    devis1.setEtat(Etat.DDI);
    devis1.setIdClient(1);
    devis1.setMontant(3900);
    // devis2.setId= 2;
    devis2.setDate(Timestamp.valueOf(LocalDateTime.now()));
    devis2.setDureeTravaux("2 semaines");
    devis2.setEtat(Etat.DDI);
    devis2.setIdClient(1);
    devis2.setMontant(8120);

    listeDevisDto.add(devis1);
    listeDevisDto.add(devis2);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      // listeDevisDto = userUCC.getDevis();
      Genson genson = new Genson();
      // Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      // String token = data.get("token").toString();
      String token = "t";

      if (token != null) {
        String devisData = genson.serialize(listeDevisDto);
        String json = "{\"success\":\"true\", \"devisData\":" + devisData + "}";
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
