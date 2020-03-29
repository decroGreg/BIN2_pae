package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.UserUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IntroduireDevisServlet extends HttpServlet {
  private UserUcc userUcc;
  private ClientDto clientDto;
  private DevisDto devisDto;

  public IntroduireDevisServlet(UserUcc userUcc, ClientDto clientDto, DevisDto devisDto) {
    super();
    this.userUcc = userUcc;
    this.clientDto = clientDto;
    this.devisDto = devisDto;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    try {
      Genson genson = new Genson();
      String token = req.getHeader("Authorization");
      if (token != null) {



        String json =
            "{\"success\":\"true\", \"token\":\"" + token + "\", \"typeAmenagements\":" + "" + "}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);
      }


    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\false\"}";
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
      System.out.println(data.get("dataQuote").toString());
      try {
        // faire de set

        userUcc.introduireDevis(clientDto, devisDto);


      } catch (Exception e) {
        e.printStackTrace();
        String json = "{\"success\":\false\"}";
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write(json);
        return;
      }
      String json = "{\"success\":\"true\"}";
      System.out.println("JSON generated :" + json);
      resp.setContentType("application/json");

      resp.setCharacterEncoding("UTF-8");

      resp.setStatus(HttpServletResponse.SC_OK);;
      resp.getWriter().write(json);

    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"error\":\false\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);


    }
  }

}
