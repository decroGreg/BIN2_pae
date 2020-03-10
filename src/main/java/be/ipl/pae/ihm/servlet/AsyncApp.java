package be.ipl.pae.ihm.servlet;

import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

public class AsyncApp {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    WebAppContext context = new WebAppContext();



    System.out.println(context.getContextPath());
    context.setContextPath("/");

    // regarder à quoi ça sert vraimant
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");


    HttpServlet serv = new LoginServlet();
    context.addServlet(new ServletHolder(serv), "/login");



    context.setResourceBase("view");

    server.setHandler(context);
    server.start();

  }

}
