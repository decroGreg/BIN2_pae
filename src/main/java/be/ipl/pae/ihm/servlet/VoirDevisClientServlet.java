package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
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

public class VoirDevisClientServlet extends HttpServlet {

  private ClientUcc clientUcc;
  private DevisUcc devisUcc;
  private ClientDto clientDto;

  /**
   * Cree un VoirDevisClientServlet.
   * 
   * @param clientUcc un clientUcc
   * @param devisUcc un devisUcc
   * @param clientDto un clientDto
   */
  public VoirDevisClientServlet(ClientUcc clientUcc, DevisUcc devisUcc, ClientDto clientDto) {
    super();
    this.clientUcc = clientUcc;
    this.devisUcc = devisUcc;
    this.clientDto = clientDto;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      List<DevisDto> listeDevisDto = new ArrayList<>();

      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idUtilisateur = -1;
      int idClient = -1;
      if (data.get("idUser") != null) {
        idUtilisateur = Integer.parseInt(data.get("idUser").toString());
      } else if (data.get("idClient") != null) {
        idClient = Integer.parseInt(data.get("idClient").toString());
      }
      System.out.println("idUtil = " + idUtilisateur);
      System.out.println("idClient  = " + idClient);

      for (ClientDto c : clientUcc.getClients()) {
        System.out.println("idUtil Client = " + c.getIdUtilisateur());
        System.out.println("idClient Client = " + c.getIdClient());
        if (c.getIdUtilisateur() == idUtilisateur || c.getIdClient() == idClient) {
          System.out.println("OK");
          clientDto = c;
          System.out.println(c.getPrenom());
          break;
        }
      }

      listeDevisDto = devisUcc.voirDevis(clientDto);
      System.out.println(clientDto.getPrenom());
      List<ClientDto> listeClientsDto = new ArrayList<>();
      for (int i = 0; i < listeDevisDto.size(); i++) {
        listeClientsDto.add(clientDto);

      }


      if (token != null) {
        String devisData = genson.serialize(listeDevisDto);
        String clientsData = genson.serialize(listeClientsDto);
        String json = "{\"success\":\"true\", \"token\":\"" + token + "\", \"devisData\":"
            + devisData + ", \"clientsData\":" + clientsData + "}";
        ResponseImpl.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      ResponseImpl.errorServer(resp, ex);
    }
  }

}
