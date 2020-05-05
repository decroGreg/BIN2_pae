package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.ihm.response.ResponseImpl;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VoirTypesAmenagementServlet extends HttpServlet {

  private TypeDAmenagementUcc typeDAmenagementUcc;
  private PhotoUcc photoUcc;
  private AmenagementUcc amenagementUcc;

  /**
   * Cree un VoirTypesAmenagementServlet.
   * 
   * @param typeDAmenagementUcc
   * @param photoUcc
   * @param amenagementUcc
   */
  public VoirTypesAmenagementServlet(TypeDAmenagementUcc typeDAmenagementUcc, PhotoUcc photoUcc,
      AmenagementUcc amenagementUcc) {
    super();
    this.typeDAmenagementUcc = typeDAmenagementUcc;
    this.photoUcc = photoUcc;
    this.amenagementUcc = amenagementUcc;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Va chercher les types d'amenagements pour remplir les categories


    try {
      Genson genson = new Genson();
      List<PhotoDto> photos = new ArrayList<>();
      List<TypeDAmenagementDto> typesAmenagements = typeDAmenagementUcc.voirTypeDAmenagement();
      List<TypeDAmenagementDto> typesAmenagementsPhotos;
      for (PhotoDto ph : photoUcc.voirPhotos()) {
        if (ph.getIdAmenagement() > 0 && ph.isVisible()) {
          photos.add(ph);

        }
      }
      typesAmenagementsPhotos = typesAmenagementPhotos(photos);
      String typesAmenagementPhotosData = genson.serialize(typesAmenagementsPhotos);
      String typesAmenagementData = genson.serialize(typesAmenagements);
      String photosData = genson.serialize(photos);
      String json = "{\"success\":\"true\", \"photosData\":" + photosData
          + ", \"typesAmenagementData\":" + typesAmenagementData
          + ", \"typesAmenagementPhotosData\":" + typesAmenagementPhotosData + "}";
      ResponseImpl.success(resp, json);


    } catch (Exception ex) {
      ex.printStackTrace();
      ResponseImpl.errorServer(resp, ex);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Va chercher toutes les photos apres amenagements du type amenagement selectionne
    // Renvoie une liste de photos

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      int idTypeAmenagement = Integer.parseInt(data.get("idTypeAmenagement").toString());
      List<PhotoDto> photos = new ArrayList<>();
      List<TypeDAmenagementDto> typesAmenagements = new ArrayList<>();


      TypeDAmenagementDto typeAmenagementDto = null;
      for (TypeDAmenagementDto type : typeDAmenagementUcc.voirTypeDAmenagement()) {
        if (type.getId() == idTypeAmenagement) {
          typeAmenagementDto = type;
        }
      }

      for (PhotoDto ph : photoUcc.voirPhotoParTypeAmenagement(typeAmenagementDto)) {
        if (ph.isVisible()) {
          photos.add(ph);
        }
      }


      // Si il n'y aucune photo pour ce type d'aménagement, je renvoie toutes les photos pour les
      // afficher dans le carrousel
      if (photos.isEmpty()) {
        for (PhotoDto ph : photoUcc.voirPhotos()) {
          if (ph.getIdAmenagement() > 0 && ph.isVisible()) {
            photos.add(ph);
          }
        }
      }
      if (!photos.isEmpty()) {
        typesAmenagements = typesAmenagementPhotos(photos);
        String typesAmenagementPhotosData = genson.serialize(typesAmenagements);
        String photosData = genson.serialize(photos);
        String json = "{\"success\":\"true\", \"photosData\":" + photosData
            + ", \"typesAmenagementPhotosData\":" + typesAmenagementPhotosData + "}";
        ResponseImpl.success(resp, json);
      }


    } catch (Exception ex) {
      ex.printStackTrace();
      ResponseImpl.errorServer(resp, ex);
    }

  }

  /**
   * Renvoie une liste des types d'amenagement correspondant a une liste de photos.
   * 
   * @param photos la liste de photos
   * @return la liste de types d'amenagement
   */
  public List<TypeDAmenagementDto> typesAmenagementPhotos(List<PhotoDto> photos) {
    List<TypeDAmenagementDto> typesAmenagements = new ArrayList<>();
    for (PhotoDto ph : photos) {
      for (AmenagementDto am : amenagementUcc.voirAmenagement()) {
        if (ph.getIdAmenagement() == am.getIdAmenagement()) {
          for (TypeDAmenagementDto ty : typeDAmenagementUcc.voirTypeDAmenagement()) {
            if (am.getIdTypeAmenagement() == ty.getId()) {
              typesAmenagements.add(ty);
            }
          }
        }
      }
    }
    return typesAmenagements;
  }



}
