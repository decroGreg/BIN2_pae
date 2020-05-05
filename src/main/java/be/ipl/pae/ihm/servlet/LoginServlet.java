package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.exceptions.BizException;
import be.ipl.pae.ihm.response.ResponseImpl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {


  private static final String JWTSECRET = new Config().getConfigPropertyAttribute("jwt.secret");
  private UserUcc userUcc;
  private UserDto userDto;

  /**
   * constructeur
   * 
   * @param UserUcc l'user ucc
   * @param UserDto l'userDto
   */
  public LoginServlet(UserUcc userUcc, UserDto userDto) {
    super();
    this.userUcc = userUcc;
    this.userDto = userDto;
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {


    String token = req.getHeader("Authorization");
    token = token.replace("Bearer", "");

    System.out.println(token);
    DecodedJWT decode = JWT.decode(token);
    int id = (int) decode.getClaim("claims").asMap().get("id");
    try {
      userDto = userUcc.loginViaToken(id);

    } catch (Exception exce) {
      exce.printStackTrace();
      String json = "{\"success\":\"false\"}";
      System.out.println("JSON generated :" + json);
      resp.setContentType("application/json");
      resp.setCharacterEncoding("UTF-8");

      resp.setStatus(HttpServletResponse.SC_OK);
      resp.getWriter().write(json);
      return;
    }
    Genson genson = new Genson();
    String userData = genson.serialize(userDto);


    String json = "{\"success\":\"true\",\"userData\":" + userData + ",\"message\":\""
        + "login reussit" + "\"}";
    System.out.println("JSON generated :" + json);
    resp.setContentType("application/json");

    resp.setCharacterEncoding("UTF-8");

    resp.setStatus(HttpServletResponse.SC_OK);
    resp.getWriter().write(json);


  }

  @SuppressWarnings("unchecked")
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);

      String mail = data.get("mail").toString();
      String mdp = data.get("mdp").toString();

      try {
        userDto = userUcc.login(mail, mdp);

        // verification du pseudo
        // verification du mdp
        if (userDto != null) {
          Map<String, Object> claims = new HashMap<String, Object>();
          claims.put("id", userDto.getIdUser());
          claims.put("ip", req.getRemoteAddr());
          Algorithm algorithm = Algorithm.HMAC256(JWTSECRET);

          String ltoken =
              JWT.create().withIssuer("auth0").withClaim("claims", claims).sign(algorithm);

          String userData = genson.serialize(userDto);
          // *********************************************

          String json = "{\"success\":\"true\", \"token\":\"" + ltoken + "\", \"userData\":"
              + userData + ",\"message\":\"" + "login reussit" + "\"}";
          ResponseImpl.success(resp, json);

        }
      } catch (BizException biz) {
        // TODO: handle exception
        biz.printStackTrace();
        ResponseImpl.raterRequete(resp, "\"mdp incorrecte\"");


      }

    } catch (Exception exc) {
      exc.printStackTrace();
      ResponseImpl.errorServer(resp, exc);
    }



  }

}
