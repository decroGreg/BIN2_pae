package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.AmenagementDto;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.TypeDAmenagementDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.exceptions.IhmException;
import be.ipl.pae.ihm.response.Response;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
  private DevisDto devisDto;
  private PhotoUcc photoUcc;


  /**
   * Cree un objet DetailsDevisServlet.
   * 
   * @param devisUcc le devis ucc.
   * @param clientUcc le client ucc.
   * @param amenagementUcc l'amenagement ucc.
   * @param typeDAmenagementUcc le type d'amenagement ucc.
   * @param devisDto le devis dto.
   * @param photoUcc le photo ucc.
   */
  public DetailsDevisServlet(DevisUcc devisUcc, ClientUcc clientUcc, AmenagementUcc amenagementUcc,
      TypeDAmenagementUcc typeDAmenagementUcc, DevisDto devisDto, PhotoUcc photoUcc) {
    super();
    this.devisUcc = devisUcc;
    this.clientUcc = clientUcc;
    this.amenagementUcc = amenagementUcc;
    this.typeDAmenagementUcc = typeDAmenagementUcc;
    this.devisDto = devisDto;
    this.photoUcc = photoUcc;
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      devisDto.setDateDebutTravaux(null);
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idDevis = Integer.parseInt(data.get("idDevis").toString());
      System.out.println("idDevis = " + idDevis);

      DevisDto devisDto = null;
      ClientDto clientDto = null;
      ArrayList<String> descriptionsTypeAmenagement = new ArrayList<>();
      ArrayList<AmenagementDto> amenagementsDevis = new ArrayList<>();
      PhotoDto photoPreferee = null;

      try {
        for (DevisDto e : devisUcc.voirDevis()) {
          if (e.getIdDevis() == idDevis) {
            devisDto = e;
            System.out.println("photo pref = " + devisDto.getIdPhotoPreferee());
          }
        }
        // Si le devis est visible, je vais chercher sa photo preferee
        if (devisDto.getEtat() == Etat.V) {
          System.out.println("ICI");
          photoPreferee = photoUcc.recupererPhotoPreferee(devisDto);
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
        Response.errorServer(resp, ex);
      }


      if (devisDto != null && clientDto != null) {
        String devisData = genson.serialize(devisDto);
        String clientData = genson.serialize(clientDto);
        String typesAmenagementData = genson.serialize(descriptionsTypeAmenagement);
        String json;

        if (photoPreferee != null) {
          System.out.println("ICI");
          String photoPrefereeData = genson.serialize(photoPreferee);
          json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":" + devisData
              + ", \"photoPrefereeData\":" + photoPrefereeData + ", \"clientData\":" + clientData
              + ", \"typesAmenagementData\":" + typesAmenagementData + "}";
        } else {

          json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":" + devisData
              + ", \"clientData\":" + clientData + ", \"typesAmenagementData\":"
              + typesAmenagementData + "}";
        }

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
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      String token = req.getHeader("Authorization");
      Map<String, String> data = genson.deserialize(req.getReader(), Map.class);
      try {
        System.out.println(data.get("idDevis"));
        int idDevis = Integer.parseInt(data.get("idDevis"));
        Etat etat = Etat.valueOf(data.get("etat"));
        System.err.println(etat.toString());
        switch (etat) {
          case FD:
            break;
          case DC:
            break;
          default:
            throw new IhmException("l etat du devis ne permet pas d efectuer cette action");
        }
        devisDto.setIdDevis(idDevis);
        devisDto.setEtat(etat);
        if (!data.get("date").toString().equals("")) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
          Date parsedDate = dateFormat.parse(data.get("date").toString() + " 00:00:00.000");
          Timestamp timestamp = new Timestamp(parsedDate.getTime());
          devisDto.setDateDebutTravaux(timestamp);
          System.out.println("idDevis" + idDevis + "  time=" + timestamp.toString());
          devisUcc.repousserDateDebut(devisDto);
        }

        String json = "{\"success\":\"true\", \"token\":\"" + token
            + "\",\"message\":\"la date a ete repoussee\"}";
        System.out.println(json);
        Response.success(resp, json);

      } catch (Exception exce) {


        exce.printStackTrace();
        // String json = "{\"success\":\"false\", \"token\":\"" + token
        // + "\", \"message\":\"message d'erreur\"}";
        Response.raterRequete(resp, "erreur");


      }

    } catch (Exception exc) {
      exc.printStackTrace();
      Response.errorServer(resp, exc);
    }

  }


}
