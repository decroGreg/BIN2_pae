package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;

public class DetailsDevisServlet extends HttpServlet {

  private UserUcc userUCC;
  private UserDto userDto;


  public DetailsDevisServlet(UserUcc userUCC, UserDto userDto) {
    super();
    this.userUCC = userUCC;
    this.userDto = userDto;
  }


  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doGet(req, resp);
  }


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {

      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);

      String idDevis = data.get("idDevis").toString();
      System.out.println("idDevis = " + idDevis);

      /*
       * try { DevisDto devisDto = userUCC.getDevisFromId(idDevis); }catch(Exception e) {
       * e.printStackTrace(); }
       * 
       * if(devisDto!=null) {
       * 
       * }
       */

    } catch (Exception e) {
      e.printStackTrace();
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      String json = "{\"error\":\"false\"}";
      resp.getWriter().write(json);
    }
  }


}
