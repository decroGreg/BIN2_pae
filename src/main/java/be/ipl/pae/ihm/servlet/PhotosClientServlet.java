package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
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

public class PhotosClientServlet extends HttpServlet {

  private ClientUcc clientUcc;
  private DevisUcc devisUcc;
  private PhotoUcc photoUcc;

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
      String token = req.getHeader("Authorization");
      int idUtilisateur = Integer.parseInt(data.get("idUser").toString());
      System.out.println("ID UTIL = " + idUtilisateur);
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


      if (token != null) {
        String photosData = genson.serialize(listePhotos);
        String json = "{\"success\":\"true\", \"photosData\":" + photosData + "}";
        ResponseImpl.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      ResponseImpl.errorServer(resp, ex);
    }
  }


}
