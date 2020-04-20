package be.ipl.pae.ihm.servlet;

import org.eclipse.jetty.servlet.DefaultServlet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexServlet extends DefaultServlet {

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    System.out.println();
    if (!req.getRequestURI().equals("/"))
      super.doGet(req, resp);
    else {
      try {


        resp.setContentType("text/html;charset=UTF-8");
        String indexFile = new String(Files.readAllBytes(Paths.get("./view/index.html")));
        String confirmerInscriptionFile =
            new String(Files.readAllBytes(Paths.get("./view/html/confirmerInscription.html")));
        String connexionFile =
            new String(Files.readAllBytes(Paths.get("./view/html/connexion.html")));
        String introduireDevisFile =
            new String(Files.readAllBytes(Paths.get("./view/html/introduireDevis.html")));
        String voirClientsFile =
            new String(Files.readAllBytes(Paths.get("./view/html/voirClients.html")));
        String voirDevisFile =
            new String(Files.readAllBytes(Paths.get("./view/html/voirDevis.html")));
        String voirDetailsDevisFile =
            new String(Files.readAllBytes(Paths.get("./view/html/voirDetailsDevis.html")));
        String voirDetailsDevisDCFile =
            new String(Files.readAllBytes(Paths.get("./view/html/voirDetailsDevisDC.html")));
        String voirDetailsDevisDDIFile =
            new String(Files.readAllBytes(Paths.get("./view/html/voirDetailsDevisDDI.html")));
        String voirDevisClientFile =
            new String(Files.readAllBytes(Paths.get("./view/html/voirDevisClient.html")));
        String voirUtilisateursFile =
            new String(Files.readAllBytes(Paths.get("./view/html/voirUtilisateurs.html")));
        /*
         * resp.getOutputStream() .println(indexFile + connexionFile + introduireDevisFile +
         * confirmerInscriptionFile);
         */

        resp.getOutputStream()
            .println(indexFile + connexionFile + introduireDevisFile + confirmerInscriptionFile
                + voirClientsFile + voirDevisFile + voirDetailsDevisFile + voirDetailsDevisDCFile
                + voirDetailsDevisDDIFile + voirDevisClientFile + voirUtilisateursFile);

      } catch (Exception exce) {

      }
    }



  }

}
