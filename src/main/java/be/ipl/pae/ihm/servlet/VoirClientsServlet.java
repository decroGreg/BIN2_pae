package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
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

public class VoirClientsServlet extends HttpServlet {

  private ClientUcc clientUcc;
  private List<ClientDto> clientsDto;

  /**
   * Cree un VoirClientsServlet.
   * 
   * @param clientUcc un clientUcc
   *
   */
  public VoirClientsServlet(ClientUcc clientUcc) {
    super();
    this.clientUcc = clientUcc;
    this.clientsDto = new ArrayList<>();

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      clientsDto = clientUcc.getClients();
      System.out.println(clientsDto.toString());
      Genson genson = new Genson();

      String token = req.getHeader("Authorization");
      // token = "t";
      if (token != null) {
        String clientsData = genson.serialize(clientsDto);
        String json = "{\"success\":\"true\", \"clientsData\":" + clientsData + "}";
        ResponseImpl.success(resp, json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      ResponseImpl.errorServer(resp, ex);
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    System.out.println("ok");
    try {
      System.out.println(clientsDto.toString());
      Genson genson = new Genson();

      String token = req.getHeader("Authorization");
      if (token != null) {
        Map<String, String> data = genson.deserialize(req.getReader(), Map.class);
        String nom = data.get("name");
        String ville = data.get("city");
        int codePostal = 0;
        if (!data.get("postalCode").contentEquals("")) {
          codePostal = Integer.parseInt(data.get("postalCode"));
        }
        System.out.println(nom);
        clientsDto = clientUcc.rechercherClients(nom, ville, codePostal);
        System.out.println("passage = " + clientsDto);

        String clientsData = genson.serialize(clientsDto);
        String json = "{\"success\":\"true\", \"clientsData\":" + clientsData + "}";
        ResponseImpl.success(resp, json);

      }
    } catch (Exception exce) {
      exce.printStackTrace();
      ResponseImpl.errorServer(resp, exce);


    }
  }


}
