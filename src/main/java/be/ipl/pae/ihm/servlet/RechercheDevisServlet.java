package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RechercheDevisServlet extends HttpServlet {

  private DevisUcc devisUcc;
  private DevisDto devisDto;
  private ClientUcc clientUcc;

  public RechercheDevisServlet(DevisUcc devisUcc, DevisDto devisDto, ClientUcc clientUcc) {
    // TODO Auto-generated constructor stub
    this.devisUcc = devisUcc;
    this.devisDto = devisDto;
    this.clientUcc = clientUcc;
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub

    try {
      Genson genson = new Genson();
      Map<String, Map<String, String>> send = genson.deserialize(req.getReader(), Map.class);
      System.out.println(send.toString());
      Map<String, String> data = send.get("data");
      Map<String, String> amenagements = send.get("amenagements");
      List<DevisDto> listeDevisDto = new ArrayList<DevisDto>();
      System.out.println(data.toString());
      Double min = 0.0;
      Double max = 0.0;
      String name = null;
      String token = req.getHeader("Authorization");
      if (token != null) {
        if (!data.get("min").toString().equals(""))
          min = Double.parseDouble(data.get("min").toString());
        if (!data.get("max").toString().equals(""))
          max = Double.parseDouble(data.get("max").toString());
        if (!data.get("date").toString().equals("")) {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
          Date parsedDate = dateFormat.parse(data.get("date").toString() + " 00:00:00.000");
          Timestamp timestamp = new Timestamp(parsedDate.getTime());
          devisDto.setDate(timestamp);
        }
        if (!data.get("name").contentEquals(""))
          name = data.get("name");
        List<ClientDto> listeClientsDto = new ArrayList<>();

        // nom !!!!!!!!!!!!!!!!
        // methode
        int test = 0;
        if (amenagements.size() != 0)
          test = Integer.parseInt((String) amenagements.values().toArray()[0]);
        try {
          if (data.containsKey("idUser")) {

            int idClient = Integer.parseInt(data.get("idUser"));
            devisDto.setIdClient(idClient);
          }


          System.out.println("min=" + min + " max=" + max + " name=" + name + " date="
              + devisDto.getDate() + " idClient=" + devisDto.getIdClient());

          listeDevisDto = devisUcc.rechercheSurDevis(devisDto, min, max, test, name);
          // System.err.println(
          // listeDevisDto.get(0).getIdClient() + " " + listeDevisDto.get(1).getIdClient());

          for (DevisDto de : listeDevisDto) {
            for (ClientDto cl : clientUcc.getClients()) {
              if (de.getIdClient() == cl.getIdClient()) {
                listeClientsDto.add(cl);
              }
            }
          }

        } catch (Exception exc) {
          exc.printStackTrace();
          String json = "{\"success\":\"false\", \"message\":\"" + exc.getMessage() + "\"}";
          System.out.println("JSON generated :" + json);

          resp.setContentType("application/json");

          resp.setCharacterEncoding("UTF-8");

          resp.setStatus(HttpServletResponse.SC_OK);
          resp.getWriter().write(json);
        }


        String devisData = genson.serialize(listeDevisDto);
        String json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":"
            + devisData + ", \"clientsData\":" + genson.serialize(listeClientsDto) + "}";
        System.out.println("JSON generated :" + json);
        devisDto.setDate(null);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);


      }
    } catch (

    Exception exc) {
      exc.printStackTrace();
      String json = "{\"error\":\"false\"}";
      System.out.println(json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }
  }



}
