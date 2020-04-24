package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.UserUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsDevisServlet extends HttpServlet {

  private UserUcc userUcc;
  private UserDto userDto;
  private DevisUcc devisUcc;
  private ClientUcc clientUcc;

  /**
   * Cree un objet DetailsDevisServlet.
   * 
   * @param userUcc un userUcc
   * @param userDto un userDto
   * @param devisUcc un devisUcc
   */
  public DetailsDevisServlet(UserUcc userUcc, UserDto userDto, DevisUcc devisUcc,
      ClientUcc clientUcc) {
    super();
    this.userUcc = userUcc;
    this.userDto = userDto;
    this.devisUcc = devisUcc;
    this.clientUcc = clientUcc;
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {

      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idDevis = Integer.parseInt(data.get("idDevis").toString());
      System.out.println("idDevis = " + idDevis);

      DevisDto devisDto = null;
      ClientDto clientDto = null;

      try {
        for (DevisDto e : devisUcc.voirDevis()) {
          if (e.getIdDevis() == idDevis) {
            devisDto = e;
          }
        }

        for (ClientDto c : clientUcc.getClients()) {
          if (c.getIdClient() == devisDto.getIdClient()) {
            clientDto = c;
          }
        }
        // DevisDto devisDto = userUCC.getDevisFromId(idDevis);
      } catch (Exception ex) {
        ex.printStackTrace();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        String json = "{\"error\":\"false\"}";
        resp.getWriter().write(json);
      }


      if (devisDto != null && clientDto != null) {
        String devisData = genson.serialize(devisDto);
        String clientData = genson.serialize(clientDto);
        String json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":"
            + devisData + ", \"clientData\":" + clientData + "}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
      }

    } catch (Exception ex) {
      ex.printStackTrace();
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      String json = "{\"error\":\"false\"}";
      resp.getWriter().write(json);
    }
  }


  /*
   * @Override protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws
   * ServletException, IOException { try { Genson genson = new Genson(); Map<String, Object> data =
   * genson.deserialize(req.getReader(), Map.class); String token = req.getHeader("Authorization");
   * DevisDto devis = null; if (token != null) { int idDevis =
   * Integer.parseInt(data.get("idDevis").toString()); System.out.println(idDevis); for (DevisDto e
   * : devisUcc.voirDevis()) { if (e.getIdDevis() == idDevis) { devis = e; } }
   * 
   * // Changement d'etat dans la db devisUcc.confirmerDateDebut(devis);
   * 
   * devis.setEtat(Etat.DC);
   * 
   * // Renvoi du nouveau DevisDTO String devisData = genson.serialize(devis); String json =
   * "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":" + devisData + "}";
   * 
   * System.out.println("JSON generated :" + json);
   * 
   * resp.setContentType("application/json");
   * 
   * resp.setCharacterEncoding("UTF-8");
   * 
   * resp.setStatus(HttpServletResponse.SC_OK); resp.getWriter().write(json); }
   * 
   * } catch (Exception ex) { ex.printStackTrace(); resp.setContentType("application/json");
   * resp.setCharacterEncoding("UTF-8");
   * resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); String json =
   * "{\"error\":\"false\"}"; resp.getWriter().write(json); } }
   */


}
