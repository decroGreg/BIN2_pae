package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.ihm.response.Response;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChoisirPhotoPrefereeServlet extends HttpServlet {

  private DevisUcc devisUcc;
  private DevisDto devisDto;
  private PhotoUcc photoUcc;

  /**
   * Cree un ChoisirPhotoPrefereeServlet.
   * 
   * @param devisUcc un devisUcc
   * @param devisDto un devisDto
   * @param photoUcc une photoUcc
   */
  public ChoisirPhotoPrefereeServlet(DevisUcc devisUcc, DevisDto devisDto, PhotoUcc photoUcc) {
    super();
    this.devisUcc = devisUcc;
    this.devisDto = devisDto;
    this.photoUcc = photoUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Va chercher toutes les photos apres amenagement du devis
    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      ArrayList<PhotoDto> photosDevis = new ArrayList<>();
      int idDevis = Integer.parseInt(data.get("idDevis").toString());
      for (DevisDto d : devisUcc.voirDevis()) {
        if (d.getIdDevis() == idDevis) {
          devisDto = d;
        }
      }

      for (PhotoDto p : photoUcc.voirPhotos()) {
        // Si la photo a un idDevis et un idAmenagement,c'est une photo apres amenagement
        if (p.getIdDevis() == idDevis && p.getIdAmenagement() > 0) {
          System.out.println("idDevis = " + p.getIdDevis());
          photosDevis.add(p);
        }
      }

      if (token != null) {
        String photosData = genson.serialize(photosDevis);
        String json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"photosData\":"
            + photosData + "}";
        Response.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      Response.errorServer(resp, ex);
    }



  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Ajoute la photo preferee du devis

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idPhoto = Integer.parseInt(data.get("idPhoto").toString());
      int idDevis = 0;
      for (PhotoDto ph : photoUcc.voirPhotos()) {
        if (ph.getIdPhoto() == idPhoto) {
          idDevis = ph.getIdDevis();
        }
      }
      for (DevisDto d : devisUcc.voirDevis()) {
        if (d.getIdDevis() == idDevis) {
          devisDto = d;
        }
      }


      if (token != null) {
        devisUcc.choisirPhotoPreferee(devisDto, idPhoto);
        System.out.println("ID PHOTO PREF = " + idPhoto);
        PhotoDto photoPreferee = photoUcc.recupererPhotoPreferee(devisDto);
        String photoPrefereeData = genson.serialize(photoPreferee);
        String devisData = genson.serialize(devisDto);
        String json = "{\"success\":\"true\", \"message\":\""
            + "La photo preferee a ete selectionnee" + "\", \"devisData\":" + devisData
            + ", \"photoPrefereeData\":" + photoPrefereeData + "}";
        Response.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      Response.errorServer(resp, ex);
    }
  }



}
