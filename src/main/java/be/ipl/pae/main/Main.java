package be.ipl.pae.main;

import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import be.ipl.pae.ihm.servlet.LoginServlet;

public class Main {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    WebAppContext context = new WebAppContext();



    System.out.println(context.getContextPath());
    context.setContextPath("/");

    System.out.println("test");
    // regarder à quoi ça sert vraimant
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");


    HttpServlet serv = new LoginServlet();
    context.addServlet(new ServletHolder(serv), "/login");



    context.setResourceBase("view");

    server.setHandler(context);
    server.start();

  }

}
