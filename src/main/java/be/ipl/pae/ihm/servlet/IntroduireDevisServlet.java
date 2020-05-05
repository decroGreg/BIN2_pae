package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.exceptions.IhmException;
import be.ipl.pae.ihm.response.Response;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IntroduireDevisServlet extends HttpServlet {
  private DevisUcc devisUcc;
  private ClientDto clientDto;
  private DevisDto devisDto;
  private TypeDAmenagementUcc typeUcc;
  private PhotoUcc photoUcc;

  /**
   * Cree un objet IntroduireDevisServlet.
   * 
   * @param devisUcc le devis ucc.
   * @param clientDto le clientDto.
   * @param devisDto le devisDto.
   * @param type le type d'amenagement ucc.
   * @param photoUcc photoUcc.
   * 
   */
  public IntroduireDevisServlet(DevisUcc devisUcc, ClientDto clientDto, DevisDto devisDto,
      TypeDAmenagementUcc type, PhotoUcc photoUcc) {
    super();
    this.devisUcc = devisUcc;
    this.clientDto = clientDto;
    this.devisDto = devisDto;
    this.typeUcc = type;
    this.photoUcc = photoUcc;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      Genson genson = new Genson();
      String token = req.getHeader("Authorization");
      String typeAmenagements = null;
      if (token != null) {

        typeAmenagements = genson.serialize(typeUcc.voirTypeDAmenagement());

        String json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"typeAmenagements\":"
            + typeAmenagements + "}";
        Response.success(resp, json);
      }


    } catch (Exception exce) {
      exce.printStackTrace();

      Response.raterRequete(resp, exce.getMessage());

    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      Genson genson = new Genson();
      Map<String, Map<String, String>> data = genson.deserialize(req.getReader(), Map.class);
      System.out.println(data.toString());
      try {
        Map<String, String> images = data.get("images");
        System.out.println(images);
        Map<String, String> dataUser = data.get("dataUser");
        Map<String, String> dataQuote = data.get("dataQuote");
        if (dataUser != null) {
          System.out.println("PASSAGE*******************");
          clientDto.setPrenom(dataUser.get("firstname").toString());
          clientDto.setNom(dataUser.get("lastname").toString());
          clientDto.setRue(dataUser.get("street").toString());
          clientDto.setNumero(dataUser.get("number").toString());
          clientDto.setBoite(dataUser.get("boite").toString());
          clientDto.setCodePostal(Integer.parseInt(dataUser.get("postalCode").toString()));
          clientDto.setVille(dataUser.get("city").toString());
          clientDto.setEmail(dataUser.get("mail").toString());
          clientDto.setTelephone(dataUser.get("phone").toString());
        }
        try {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
          Date parsedDate = dateFormat.parse(dataQuote.get("date").toString() + " 00:00:00.000");
          Timestamp timestamp = new Timestamp(parsedDate.getTime());
          devisDto.setDate(timestamp);
          devisDto.setMontant(Double.parseDouble(dataQuote.get("price").toString()));
          devisDto.setDureeTravaux(dataQuote.get("duration"));
        } catch (Exception exc) {
          exc.printStackTrace();
          throw new IhmException("veuillez introduire une date");
        }

        int idUser = 0;
        if (!dataQuote.get("user").toString().equals("")) {
          idUser = Integer.parseInt(dataQuote.get("user").toString());
        }

        if (!dataQuote.get("client").toString().equals("")) {
          devisDto.setIdClient(Integer.parseInt(dataQuote.get("client").toString()));
        }

        // si aucun client est lier et si il n'y a pas de nouvaux client introduit
        if (devisDto.getIdClient() == 0 && dataUser == null) {
          throw new IhmException("veuillez introduire lie un client ou un nouveau client");
        }

        int idDevis =
            devisUcc.introduireDevis(clientDto, idUser, devisDto, (List<String>) data.get("type"));

        if (images != null) {
          for (String s : images.values()) {
            photoUcc.ajouterPhotoAvantAmenagement(idDevis, s);// remplacer le 0 par l idDevis
          }
        }

      } catch (Exception exce) {
        exce.printStackTrace();
        // String json = "{\"success\":\"false\",\"message\":\"" + "echec de l introduction du
        // devis: "+ exce.getMessage() + "\"}";
        Response.raterRequete(resp, "echec de l introduction du devis: " + exce.getMessage());
        return;
      }
      String json = "{\"success\":\"true\",\"message\":\"" + "l introduction a reussi" + "\"}";
      Response.success(resp, json);

    } catch (Exception exce) {
      exce.printStackTrace();

      Response.errorServer(resp, exce);


    }
  }

}
