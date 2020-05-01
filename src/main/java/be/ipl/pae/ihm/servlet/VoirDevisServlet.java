package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.DevisUcc;

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
  private UserDto userDto;
  private List<DevisDto> listeDevisDto;

  /**
   * Cree un VoirDevisServlet.
   * 
   * @param devisUcc un devisUcc
   * @param userDto un userDto
   */
  public VoirDevisServlet(DevisUcc devisUcc, UserDto userDto) {
    super();
    this.devisUcc = devisUcc;
    this.userDto = userDto;
    this.listeDevisDto = new ArrayList<>();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      listeDevisDto = devisUcc.voirDevis();
      Genson genson = new Genson();

      String token = req.getHeader("Authorization");

      if (token != null) {
        String devisData = genson.serialize(listeDevisDto);
        String json = "{\"success\":\"true\", \"devisData\":" + devisData + "}";
        System.out.println("JSON generated :" + json);

        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(json);

      }

    } catch (Exception ex) {
      ex.printStackTrace();
      String json = "{\"error\":\"false\"}";
      System.out.println(json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }
  }



}
