package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.impl.DevisImpl.Etat;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangementEtatDevisServlet extends HttpServlet {

  private DevisDto devisDto;
  private DevisUcc devisUcc;
  private ClientUcc clientUcc;

  /**
   * Cree un objet ChangementEtatDevisServlet.
   * 
   * @param devisDto un devisDto
   * @param devisUcc un devisUcc
   */
  public ChangementEtatDevisServlet(DevisDto devisDto, DevisUcc devisUcc, ClientUcc clientUcc) {
    super();
    this.devisDto = devisDto;
    this.devisUcc = devisUcc;
    this.clientUcc = clientUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idDevis = Integer.parseInt(data.get("idDevis").toString());

      DevisDto devis = null;
      ClientDto clientDto = null;
      try {
        for (DevisDto e : devisUcc.voirDevis()) {
          if (e.getIdDevis() == idDevis) {
            devis = e;
            // requete pour avoir tous les types d'amenagements
          }

        }
        for (ClientDto c : clientUcc.getClients()) {
          if (c.getIdClient() == devis.getIdClient()) {
            clientDto = c;
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

      if (devis != null && clientDto != null) {

        // Si on a une nouvelle date de début travaux, on change la valeur dans le devis
        if (data.get("dateDebutTravaux") != null) {
          DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
          LocalDate ld = LocalDate.parse(data.get("dateDebutTravaux").toString(), dateformatter);

          System.out.println("Date = " + Timestamp.valueOf(ld.atStartOfDay()));
          devis.setDateDebutTravaux(Timestamp.valueOf(ld.atStartOfDay()));
        }
        String etatDevis = data.get("etatDevis").toString();
        switch (etatDevis) {
          case "I":
            devis.setEtat(Etat.I);
            break;
          case "FD":
            // devisUcc.confirmerCommande(devis);
            devis.setEtat(Etat.FD);
            break;
          case "DC":
            devisUcc.confirmerDateDebut(devis);
            devis.setEtat(Etat.DC);
            break;
          case "A":
            // devisUcc.annulerDevis(devis);
            devis.setEtat(Etat.A);
            break;
          case "FM":
            // devisUcc.factureMilieuChantierEnvoyee(devis);
            devis.setEtat(Etat.FM);
            break;
          case "FF":
            // devisUcc.factureFinChantierEnvoyee(devis);
            devis.setEtat(Etat.FF);
            break;
          case "V":
            // devisUcc.rendreVisible(devis);
            devis.setEtat(Etat.V);
            break;
          default:
            break;
        }

        // Je vais chercher le devisDTO mis a jour
        for (DevisDto e : devisUcc.voirDevis()) {
          if (e.getIdDevis() == idDevis) {
            devis = e;
            // requete pour avoir tous les types d'amenagements
          }
        }

        String devisData;
        String clientData;
        if (etatDevis != "A") {
          devisData = genson.serialize(devis);
          clientData = genson.serialize(clientDto);
        } else {
          List<DevisDto> listeDevis = devisUcc.voirDevis();
          devisData = genson.serialize(listeDevis);
          clientData = "";
        }

        String json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":"
            + devisData + ", \"clientData\":" + clientData + "}";
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
