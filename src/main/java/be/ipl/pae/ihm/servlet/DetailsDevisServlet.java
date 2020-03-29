package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsDevisServlet extends HttpServlet {

  private UserUcc userUCC;
  private UserDto userDto;
  Factory f = new FactoryImpl();
  private DevisDto devis1 = f.getDevisDto();
  private DevisUcc devisUcc;

  public DetailsDevisServlet(UserUcc userUCC, UserDto userDto, DevisUcc devisUcc) {
    super();
    this.userUCC = userUCC;
    this.userDto = userDto;
    this.devisUcc = devisUcc;
    devis1.setIdDevis(1);
    devis1.setDate(Timestamp.valueOf(LocalDateTime.now()));
    devis1.setDureeTravaux("5 jours");
    devis1.setEtat(Etat.DDI);
    devis1.setIdClient(1);
    devis1.setMontant(3900);
  }


  // @Override
  /*
   * protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
   * IOException { try { Genson genson = new Genson();
   * 
   * } catch (Exception e) { e.printStackTrace(); String json = "{\"error\":\"false\"}";
   * System.out.println(json); resp.setContentType("application/json");
   * resp.setCharacterEncoding("UTF-8");
   * resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); resp.getWriter().write(json); } }
   */


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {

      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = "t";
      int idDevis = Integer.parseInt(data.get("idDevis").toString());
      System.out.println("idDevis = " + idDevis);

      DevisDto devisDto = null;
      int idAmenagement;
      try {
        for (DevisDto e : devisUcc.voirDevis()) {
          if (e.getIdDevis() == idDevis) {
            devisDto = e;
            // requete pour avoir tous les types d'amenagements
          }

        }
        // DevisDto devisDto = userUCC.getDevisFromId(idDevis);
      } catch (Exception e) {
        e.printStackTrace();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String json = "{\"error\":\"false\"}";
        resp.getWriter().write(json);
      }


      if (devisDto != null) {
        String devisData = genson.serialize(devisDto);
        String json =
            "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":" + devisData + "}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);
      }

    } catch (Exception e) {
      e.printStackTrace();
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      String json = "{\"error\":\"false\"}";
      resp.getWriter().write(json);
    }
  }


  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println("ICI");
    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      System.out.println(data);
      // String token = data.get("token").toString();
      String token = "t";

      if (token != null) {
        String nouvelEtat = data.get("etatDevis").toString();
        System.out.println(nouvelEtat);

        // Changement d'état dans la db

        // DevisDto devisNouvelEtat = userUCC.updateEtatDevis(nouvelEtat);
        DevisDto devisNouvelEtat = devis1;
        devisNouvelEtat.setEtat(Etat.ANP);

        // Renvoi du nouveau DevisDTO
        String devisData = genson.serialize(devisNouvelEtat);
        String json = "{\"success\":\"true\", \"devisData\":" + devisData + "}";
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);

      }

    } catch (Exception e) {
      e.printStackTrace();
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      String json = "{\"error\":\"false\"}";
      resp.getWriter().write(json);
    }
  }


}
