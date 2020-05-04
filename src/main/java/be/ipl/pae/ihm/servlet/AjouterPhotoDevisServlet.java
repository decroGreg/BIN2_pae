package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;

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
  private TypeDAmenagementUcc typeDAmenagementUcc;
  private DevisUcc devisUcc;
  private ArrayList<AmenagementDto> amenagementsDevis = new ArrayList<>();
  private ArrayList<String> descriptionsTypeAmenagement = new ArrayList<>();

  public AjouterPhotoDevisServlet(PhotoDto photoDto, PhotoUcc photoUcc,
      AmenagementUcc amenagementUcc, TypeDAmenagementUcc typeDAmenagementUcc, DevisUcc devisUcc) {
    super();
    this.photoDto = photoDto;
    this.photoUcc = photoUcc;
    this.amenagementUcc = amenagementUcc;
    this.typeDAmenagementUcc = typeDAmenagementUcc;
    this.devisUcc = devisUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Va chercher tous les types d'amenagement du devis

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idDevis = Integer.parseInt(data.get("idDevis").toString());


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


      if (token != null) {
        String amenagementsData = genson.serialize(amenagementsDevis);
        String typesAmenagementData = genson.serialize(descriptionsTypeAmenagement);
        String json = "{\"success\":\"true\", \"amenagementsData\":" + amenagementsData
            + ", \"typesAmenagementData\":" + typesAmenagementData + "}";
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


  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Rajoute une photo au devis

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idAmenagement = Integer.parseInt(data.get("idAmenagement").toString());
      int visible = Integer.parseInt(data.get("visible").toString());
      String urlPhoto = data.get("urlPhoto").toString();
      System.out.println("ID am = " + idAmenagement);
      AmenagementDto amenagementDto = null;
      boolean photoVisible = false;
      System.out.println("VISIBLE = " + visible);

      try {

        for (AmenagementDto a : amenagementUcc.voirAmenagement()) {
          if (a.getIdAmenagement() == idAmenagement) {
            amenagementDto = a;
            break;
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

      // Je renvoie le devis pour revenir sur la page detailsDevis
      if (amenagementDto != null) {
        if (visible == 1) {
          photoVisible = true;
          System.out.println("photo vis = " + photoVisible);
        }
        photoUcc.ajouterPhotoApresAmenagement(amenagementDto, urlPhoto, photoVisible);
        DevisDto devisDto = null;
        for (DevisDto d : devisUcc.voirDevis()) {
          if (amenagementDto.getIdDevis() == d.getIdDevis()) {
            devisDto = d;
            break;
          }
        }
        String devisData = genson.serialize(devisDto);
        String json = "{\"success\":\"true\", \"message\":\"" + "Photo ajoutee"
            + "\", \"devisData\":" + devisData + "}";
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



}
