package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;

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
  private List<TypeDAmenagementDto> typesAmenagements = new ArrayList<>();
  private List<PhotoDto> photos = new ArrayList<>();

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
      typesAmenagements = typeDAmenagementUcc.voirTypeDAmenagement();
      photos = photoUcc.voirPhotos();
      Genson genson = new Genson();

      String typesAmenagementData = genson.serialize(typesAmenagements);
      String photosData = genson.serialize(photos);
      String json = "{\"success\":\"true\", \"photosData\":" + photosData
          + ", \"typesAmenagementData\":" + typesAmenagementData + "}";
      System.out.println("JSON generated :" + json);

      resp.setContentType("application/json");

      resp.setCharacterEncoding("UTF-8");

      resp.setStatus(HttpServletResponse.SC_OK);
      resp.getWriter().write(json);


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
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Va chercher toutes les photos apres amenagements du type amenagement selectionne
    // Renvoie une liste de photos

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      int idTypeAmenagement = Integer.parseInt(data.get("idTypeAmenagement").toString());

      // Je vais d'abord chercher tous les amenagements qui ont l'idTypeAmenagement
      List<AmenagementDto> amenagements = new ArrayList<>();
      for (AmenagementDto am : amenagementUcc.voirAmenagement()) {
        if (am.getIdTypeAmenagement() == idTypeAmenagement) {
          amenagements.add(am);
        }
      }

      // Je vais chercher toutes les photos des amenagements
      for (AmenagementDto am : amenagements) {
        for (PhotoDto ph : photoUcc.voirPhotos()) {
          if (ph.getIdAmenagement() == am.getIdAmenagement()) {
            photos.add(ph);
          }
        }
      }

      // Si il n'y aucune photo pour ce type d'aménagement, je renvoie toutes les photos pour les
      // afficher dans le carrousel
      if (photos.isEmpty()) {
        photos = photoUcc.voirPhotos();
      }
      if (!photos.isEmpty()) {
        String photosData = genson.serialize(photos);
        String json = "{\"success\":\"true\", \"photosData\":" + photosData + "}";
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
