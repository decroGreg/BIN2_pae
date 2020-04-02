package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;

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

  public IntroduireDevisServlet(DevisUcc devisUcc, ClientDto clientDto, DevisDto devisDto,
      TypeDAmenagementUcc type) {
    super();
    this.devisUcc = devisUcc;
    this.clientDto = clientDto;
    this.devisDto = devisDto;
    this.typeUcc = type;
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
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);
      }


    } catch (Exception exce) {
      exce.printStackTrace();
      String json = "{\"error\":\"false\",\"message\":\"" + exce.getMessage() + "\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);

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

        Map<String, String> dataUser = data.get("dataUser");
        Map<String, String> dataQuote = data.get("dataQuote");
        if (dataUser != null) {
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
          throw new IllegalArgumentException("veuillez introduire une date");
        }

        int idClient = 0;
        if (!dataQuote.get("client").toString().equals("")) {
          idClient = Integer.parseInt(dataQuote.get("client").toString());
        } else {
          idClient = -1;
        }
        if (idClient == -1 && dataUser == null) {// si aucun client est lier et si il n'y a pas de
                                                 // nouvaux client introduit
          throw new IllegalArgumentException(
              "veuillez introduire lie un client ou un nouveau client");
        }
        System.out.println(dataQuote.get("image"));

        devisUcc.introduireDevis(clientDto, idClient, devisDto, (List<String>) data.get("type"));

      } catch (Exception exce) {
        exce.printStackTrace();
        String json = "{\"success\":\"false\",\"message\":\"" + "echec de l introduction du devis: "
            + exce.getMessage() + "\"}";
        System.out.println("JSON:" + json);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);
        return;
      }
      String json =
          "{\"success\":\"true\",\"message\":\"" + "l introduction du devis reussit" + "\"}";
      System.out.println("JSON generated :" + json);
      resp.setContentType("application/json");

      resp.setCharacterEncoding("UTF-8");

      resp.setStatus(HttpServletResponse.SC_OK);
      resp.getWriter().write(json);

    } catch (Exception exce) {
      exce.printStackTrace();

      String json = "{\"error\":\"false\",\"message\":\"" + exce.getMessage() + "\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);


    }
  }

}
