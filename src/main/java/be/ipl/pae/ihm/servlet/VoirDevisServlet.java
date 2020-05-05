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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VoirDevisServlet extends HttpServlet {

  private DevisUcc devisUcc;
  private ClientUcc clientUcc;
  private List<DevisDto> listeDevisDto;

  /**
   * Cree un VoirDevisServlet.
   * 
   * @param devisUcc un devisUcc
   * @param userDto un userDto
   */
  public VoirDevisServlet(DevisUcc devisUcc, ClientUcc clientUcc) {
    super();
    this.devisUcc = devisUcc;
    this.clientUcc = clientUcc;
    this.listeDevisDto = new ArrayList<>();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      listeDevisDto = devisUcc.voirDevis();
      Genson genson = new Genson();
      String token = req.getHeader("Authorization");
      List<ClientDto> listeClientsDto = new ArrayList<>();

      if (token != null) {
        for (DevisDto de : listeDevisDto) {
          for (ClientDto cl : clientUcc.getClients()) {
            if (de.getIdClient() == cl.getIdClient()) {
              listeClientsDto.add(cl);
            }
          }
        }
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
