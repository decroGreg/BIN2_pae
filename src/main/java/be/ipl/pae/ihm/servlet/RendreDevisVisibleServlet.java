package be.ipl.pae.ihm.servlet;

import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.PhotoUcc;

import com.owlike.genson.Genson;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RendreDevisVisibleServlet extends HttpServlet {

  private DevisUcc devisUcc;
  private DevisDto devisDto;
  private PhotoUcc photoUcc;

  public RendreDevisVisibleServlet(DevisUcc devisUcc, DevisDto devisDto, PhotoUcc photoUcc) {
    super();
    this.devisUcc = devisUcc;
    this.devisDto = devisDto;
    this.photoUcc = photoUcc;
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Va chercher toutes les photos apres amenagement du devis

  }

  @Override
  protected void doPut(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    // Ajoute la photo preferee du devis

    try {
      Genson genson = new Genson();
      Map<String, Object> data = genson.deserialize(req.getReader(), Map.class);
      String token = req.getHeader("Authorization");
      int idDevis = Integer.parseInt(data.get("idDevis").toString());
      int idPhoto = Integer.parseInt(data.get("idPhoto").toString());
      for (DevisDto d : devisUcc.voirDevis()) {
        if (d.getIdDevis() == idDevis) {
          devisDto = d;
        }
      }

      if (token != null) {
        devisUcc.choisirPhotoPreferee(devisDto, idPhoto);
        String json = "{\"success\":\"true\",\"message\":\"" + "ajout de la photo reussi" + "\"}";
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
