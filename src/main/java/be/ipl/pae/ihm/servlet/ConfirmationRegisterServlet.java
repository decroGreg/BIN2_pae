package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.ihm.response.ResponseImpl;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmationRegisterServlet extends HttpServlet {
  private UserUcc userUcc;
  private UserDto userDto;
  private ClientDto clientDto;

  /**
   *
   * @param userUcc l'user ucc
   * @param clientDto l' client dto
   * @param userDto l'userDto
   */
  public ConfirmationRegisterServlet(UserUcc userUcc, UserDto userDto, ClientDto clientDto) {
    // TODO Auto-generated constructor stub
    super();
    this.userDto = userDto;
    this.userUcc = userUcc;
    this.clientDto = clientDto;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      String token = req.getHeader("Authorization");
      String usersJson = null;
      if (token != null) {


        usersJson = genson.serialize(userUcc.voirUtilisateurEnAttente());
        // TO DO js-> tableau
        String json = "{\"success\":\"true\", \"usersData\":" + usersJson + "}";
        ResponseImpl.success(resp, json);
      } else {
        System.err.println("token est NULL");
        ResponseImpl.raterRequete(resp, "token est Null");
      }
    } catch (Exception exce) {
      exce.printStackTrace();
      ResponseImpl.errorServer(resp, exce);
    }

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      Map<String, String> data = genson.deserialize(req.getReader(), Map.class);
      System.out.println("--------" + data.toString());
      try {
        int idClient = 0;
        int idUtilConfirm = Integer.parseInt(data.get("id"));
        String token = req.getHeader("Authorization");
        System.out.println(token);
        if (token != null) {
          try {
            idClient = Integer.parseInt(data.get("client"));
          } catch (Exception exce) {
            exce.printStackTrace();
          }

          userDto.setIdUser(idUtilConfirm);
          clientDto.setIdClient(idClient);


          System.out.println("XXXXX" + data.get("status"));
          char statut = Character.toUpperCase(data.get("status").charAt(0));
          userUcc.confirmerInscription(userDto, clientDto, statut);



          String json = "{\"success\":\"true\",\"message\":\"" + "Enregistrement confirmer" + "\"}";
          ResponseImpl.success(resp, json);

        }
      } catch (Exception exc) {
        System.err.println("token est NULL");
        ResponseImpl.raterRequete(resp, "echec de la confirmation" + exc.getMessage());
      }

    } catch (Exception exce) {
      exce.printStackTrace();
      ResponseImpl.errorServer(resp, exce);
    }
  }

}
