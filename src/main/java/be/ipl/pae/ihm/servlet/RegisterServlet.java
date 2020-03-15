package be.ipl.pae.ihm.servlet;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.owlike.genson.Genson;
import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.interfaces.UserUCC;

public class RegisterServlet extends HttpServlet {
  private UserUCC userUcc;
  private UserDTO userDto;

  public RegisterServlet(UserUCC userUcc, UserDTO userDto) {
    super();
    this.userUcc = userUcc;
    this.userDto = userDto;

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    try {
      Genson genson = new Genson();
      Map<String, String> data = genson.deserialize(req.getReader(), Map.class);


    } catch (Exception e) {

    }
  }

}
