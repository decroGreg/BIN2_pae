package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.ClientUcc;

public class VoirDevisClientServlet extends HttpServlet {

  private ClientUcc clientUCC;
  private ClientDto clientDto;
  private List<DevisDto> listeDevisDto;

  public VoirDevisClientServlet(ClientUcc clientUCC, ClientDto clientDto) {
    super();
    this.clientUCC = clientUCC;
    this.clientDto = clientDto;
    this.listeDevisDto = new ArrayList<>();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      listeDevisDto = clientUCC.voirDevis(clientDto);
      Genson genson = new Genson();
      // Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      // String token = data.get("token").toString();
      String token = req.getHeader("Authorization");

      if (token != null) {
        for (DevisDto devis : listeDevisDto) {
          String devisData = genson.serialize(devis);
          String json = "{\"success\":\"true\", \"devisData\":" + devisData + "}";
          System.out.println("JSON generated :" + json);

          resp.setContentType("application/json");

          resp.setCharacterEncoding("UTF-8");

          resp.setStatus(HttpServletResponse.SC_OK);;
          resp.getWriter().write(json);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\"false\"}";
      System.out.println(json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }
  }

}
