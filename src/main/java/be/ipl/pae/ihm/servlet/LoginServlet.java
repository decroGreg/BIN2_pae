package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;

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
    Genson genson = new Genson();
    Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
    String mail = data.get("pseudo").toString();
    String mdp = data.get("mdp").toString();
    if (true) {// verification du pseudo
      if (true) {// verification du mdp
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("id", UUID.randomUUID().toString());
        claims.put("ip", req.getRemoteAddr());
        // String ltoken = new JWTSigner(JWTSECRET).sign(claims);
      }
    }

    System.out.println("test");
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
