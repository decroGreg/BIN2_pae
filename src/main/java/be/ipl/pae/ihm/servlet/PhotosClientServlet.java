package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.ihm.response.Response;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PhotosClientServlet extends HttpServlet {

  private ClientUcc clientUcc;
  private DevisUcc devisUcc;
  private PhotoUcc photoUcc;

  /**
   * Cree un PhotosClientServlet.
   * 
   * @param clientUcc un clientUcc
   * @param devisUcc un devisUcc
   * @param photoUcc une photoUcc
   */
  public PhotosClientServlet(ClientUcc clientUcc, DevisUcc devisUcc, PhotoUcc photoUcc) {
    super();
    this.clientUcc = clientUcc;
    this.devisUcc = devisUcc;
    this.photoUcc = photoUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      int idUtilisateur = Integer.parseInt(data.get("idUser").toString());
      ClientDto clientDto = null;
      List<PhotoDto> listePhotos = new ArrayList<>();

      for (ClientDto c : clientUcc.getClients()) {
        if (c.getIdUtilisateur() == idUtilisateur) {
          clientDto = c;
        }
      }

      List<DevisDto> listeDevis = devisUcc.voirDevis(clientDto);
      for (PhotoDto ph : photoUcc.voirPhotos()) {
        for (DevisDto de : listeDevis) {
          if (de.getIdDevis() == ph.getIdDevis()) {
            listePhotos.add(ph);
          }
        }
      }


      if (listeDevis != null) {
        String photosData = genson.serialize(listePhotos);
        String json = "{\"success\":\"true\", \"photosData\":" + photosData + "}";
        Response.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      Response.errorServer(resp, ex);
    }
  }


}
