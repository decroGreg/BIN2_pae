package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.ihm.response.ResponseImpl;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjouterPhotoDevisServlet extends HttpServlet {

  private PhotoUcc photoUcc;
  private AmenagementUcc amenagementUcc;
  private TypeDAmenagementUcc typeDAmenagementUcc;
  private DevisUcc devisUcc;

  public AjouterPhotoDevisServlet(PhotoUcc photoUcc, AmenagementUcc amenagementUcc,
      TypeDAmenagementUcc typeDAmenagementUcc, DevisUcc devisUcc) {
    super();
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
      ArrayList<AmenagementDto> amenagementsDevis = new ArrayList<>();
      ArrayList<String> descriptionsTypeAmenagement = new ArrayList<>();


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
        ResponseImpl.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      ResponseImpl.errorServer(resp, ex);

    }
  }


  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Rajoute une photo au devis

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      int idAmenagement = Integer.parseInt(data.get("idAmenagement").toString());
      int visible = Integer.parseInt(data.get("visible").toString());
      String urlPhoto = data.get("urlPhoto").toString();
      AmenagementDto amenagementDto = null;
      boolean photoVisible = false;

      try {

        for (AmenagementDto a : amenagementUcc.voirAmenagement()) {
          if (a.getIdAmenagement() == idAmenagement) {
            amenagementDto = a;
            break;
          }
        }


      } catch (Exception ex) {
        ResponseImpl.errorServer(resp, ex);

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
        ResponseImpl.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      ResponseImpl.errorServer(resp, ex);

    }
  }



}
