package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.factory.UserFactoryImpl;
import be.ipl.pae.biz.ucc.UserUCC;

public class LoginServlet extends HttpServlet {
  private static final String JWTSECRET = "mybigsecrete123";

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doGet(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);

      String mail = data.get("mail").toString();
      String mdp = data.get("mdp").toString();

      UserFactoryImpl factory = new UserFactoryImpl();
      UserDTO test = factory.getUserDTO();
      UserUCC user = new UserUCC(factory);

      if ((test = user.login(mail, mdp)) != null) {// verification du pseudo
        // verification du mdp

        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", UUID.randomUUID().toString());
        claims.put("ip", req.getRemoteAddr());
        Algorithm algorithm = Algorithm.HMAC256(JWTSECRET);
        String ltoken = JWT.create().withIssuer("auth0").sign(algorithm);
        String json = "{\"success\":\"true\", \"token\":\"" + ltoken + "\"}";
        System.out.println("JSON generated :" + json);
        resp.setContentType("application/json");

        resp.setCharacterEncoding("UTF-8");

        resp.setStatus(HttpServletResponse.SC_OK);;
        resp.getWriter().write(json);

      }

    } catch (Exception e) {
      e.printStackTrace();
      String json = "{\"success\":\"false\"}";
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");
      resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      resp.getWriter().write(json);
    }


  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doPut(req, resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    super.doDelete(req, resp);
  }

}
