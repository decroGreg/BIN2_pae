package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjouterPhotoDevisServlet extends HttpServlet {

  private PhotoDto photoDto;
  private PhotoUcc photoUcc;
  private AmenagementUcc amenagementUcc;
  private ArrayList<AmenagementDto> amenagementsDevis = new ArrayList<>();

  public AjouterPhotoDevisServlet(PhotoDto photoDto, PhotoUcc photoUcc,
      AmenagementUcc amenagementUcc) {
    super();
    this.photoDto = photoDto;
    this.photoUcc = photoUcc;
    this.amenagementUcc = amenagementUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Va chercher tous les types d'amenagement du devis

    // Besoin d'une methode qui renvoie une liste de tous les AmenagementDTO
    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idDevis = Integer.parseInt(data.get("idDevis").toString());

      /*
       * for (AmenagementDto a : amenagementUcc.voirAmenagements()) { if (a.getIdDevis() == idDevis)
       * { amenagementsDevis.add(a); } }
       */

      if (token != null) {
        String amenagementsData = genson.serialize(amenagementsDevis);
        String json = "{\"success\":\"true\", \"amenagementsData\":" + amenagementsData + "}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      String json = "{\"error\":\"false\"}";
      System.out.println(json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }
  }


  // @Override
  /*
   * protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
   * IOException { // Rajoute une photo au devis
   * 
   * try { Genson genson = new Genson(); Map<String, Object> data =
   * genson.deserialize(req.getReader(), Map.class); String token = req.getHeader("Authorization");
   * int idAmenagement = Integer.parseInt(data.get("idAmenagement").toString()); String urlPhoto =
   * data.get("urlPhoto").toString(); AmenagementDto amenagementDto = null;
   * 
   * try { for (AmenagementDto a : amenagementUcc.voirAmenagements()) { if (a.getIdAmenagement() ==
   * idAmenagement) { amenagementDto = a; break; } }
   * 
   * } catch (Exception ex) { ex.printStackTrace(); resp.setContentType("application/json");
   * resp.setCharacterEncoding("UTF-8");
   * resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); String json =
   * "{\"error\":\"false\"}"; resp.getWriter().write(json); }
   * 
   * if (amenagementDto != null) { photoUcc.ajouterPhotoApresAmenagement(amenagementDto, urlPhoto);
   * String json = "{\"success\":\"true\",\"message\":\"" + "ajout de la photo réussi" + "\"}";
   * System.out.println("JSON generated :" + json); resp.setContentType("application/json");
   * resp.setCharacterEncoding("UTF-8"); resp.setStatus(HttpServletResponse.SC_OK);
   * resp.getWriter().write(json);
   * 
   * }
   * 
   * } catch (Exception ex) { ex.printStackTrace(); String json = "{\"error\":\"false\"}";
   * System.out.println(json); resp.setContentType("application/json");
   * resp.setCharacterEncoding("UTF-8");
   * resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); resp.getWriter().write(json); } }
   */


}
