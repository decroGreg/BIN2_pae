package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.DevisDto;
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

  public RechercheDevisServlet(DevisUcc devisUcc, DevisDto devisDto) {
    // TODO Auto-generated constructor stub
    this.devisUcc = devisUcc;
    this.devisDto = devisDto;
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub

    try {
      Genson genson = new Genson();
      Map<String, Map<String, String>> send = genson.deserialize(req.getReader(), Map.class);
      Map<String, String> data = send.get("data");
      Map<String, String> amenagements = send.get("amenagements");
      List<DevisDto> listeDevisDto = new ArrayList<DevisDto>();


      Double min = 0.0;
      Double max = 0.0;
      String name;
      String token = req.getHeader("token");
      if (token != null) {
        if (data.get("min").toString() != "")
          min = Double.parseDouble(data.get("min").toString());
        if (data.get("max").toString() != "")
          max = Double.parseDouble(data.get("min").toString());
        if (data.get("date").toString() != "") {
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
          Date parsedDate = dateFormat.parse(data.get("date").toString() + " 00:00:00.000");
          Timestamp timestamp = new Timestamp(parsedDate.getTime());
          devisDto.setDate(timestamp);
        }
        name = data.get("name");

        if (data.containsKey("idUser")) {
          // nom !!!!!!!!!!!!!!!!
          // methode
          int test = Integer.parseInt((String) amenagements.values().toArray()[0]);
          // listeDevisDto = devisUcc.rechercheSurDevis(devisDto, min, max, test);

        } else {

          // devisUcc.rechercheSurDevis(devisDto, min, max, 1);
          // methode
        }
        String devisData = genson.serialize(listeDevisDto);
        String json = "{\"success\":\"true\", \"devisData\":" + devisData + "}";
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

      }
    } catch (Exception exc) {
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
