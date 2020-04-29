package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsDevisServlet extends HttpServlet {


  private DevisUcc devisUcc;
  private ClientUcc clientUcc;
  private AmenagementUcc amenagementUcc;
  private TypeDAmenagementUcc typeDAmenagementUcc;



  /**
   * Cree un objet DetailsDevisServlet.
   * 
   * @param userUcc un userUcc
   * @param userDto un userDto
   * @param devisUcc un devisUcc
   */
  public DetailsDevisServlet(DevisUcc devisUcc, ClientUcc clientUcc, AmenagementUcc amenagementUcc,
      TypeDAmenagementUcc typeDAmenagementUcc) {
    super();
    this.devisUcc = devisUcc;
    this.clientUcc = clientUcc;
    this.amenagementUcc = amenagementUcc;
    this.typeDAmenagementUcc = typeDAmenagementUcc;
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
      ArrayList<String> descriptionsTypeAmenagement = new ArrayList<>();
      ArrayList<AmenagementDto> amenagementsDevis = new ArrayList<>();

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

        // Liste des amenagements pour le devis
        for (AmenagementDto a : amenagementUcc.voirAmenagement()) {
          if (a.getIdDevis() == idDevis) {
            amenagementsDevis.add(a);
          }
        }


        // Je remplis une liste de descriptions du type amenagement de chaque amenagement
        for (int i = 0; i < amenagementsDevis.size(); i++) {
          for (TypeDAmenagementDto t : typeDAmenagementUcc.voirTypeDAmenagement()) {
            if (t.getId() == amenagementsDevis.get(i).getIdTypeAmenagement()) {
              descriptionsTypeAmenagement.add(t.getDescription());
            }
          }
        }



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
        String typesAmenagementData = genson.serialize(descriptionsTypeAmenagement);
        String json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":"
            + devisData + ", \"clientData\":" + clientData + ", \"typesAmenagementData\":"
            + typesAmenagementData + "}";
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


}
