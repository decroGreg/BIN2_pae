package be.ipl.pae.main;

import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.biz.ucc.UserUccImpl;
import be.ipl.pae.dal.impl.DAOServicesImpl;
import be.ipl.pae.dal.impl.UserDAOImpl;
import be.ipl.pae.dal.interfaces.DAOServices;
import be.ipl.pae.dal.interfaces.UserDAO;
import be.ipl.pae.ihm.servlet.ConfirmationRegisterServlet;
import be.ipl.pae.ihm.servlet.LoginServlet;
import be.ipl.pae.ihm.servlet.RechercheUtilisateursServlet;
import be.ipl.pae.ihm.servlet.RegisterServlet;

public class Main {
  public static void main(String[] args) throws Exception {

    // Lecture du fichier properties
    Config conf = new Config();

    // Creation de la dépendance
    Factory factory = (Factory) conf.getConfigPropertyClass("factory.Factory");

    DAOServices daoService = new DAOServicesImpl();
    UserDAO userDao = new UserDAOImpl();
    UserUcc userUcc = new UserUccImpl(factory, userDao);
    UserDto userDto = factory.getUserDto();

    Server server = new Server(8080);
    WebAppContext context = new WebAppContext();



    System.out.println(context.getContextPath());
    context.setContextPath("/");

    System.out.println("test");
    // regarder à quoi ça sert vraimant
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");
    HttpServlet confirmation = new ConfirmationRegisterServlet(userUcc, userDto);


    HttpServlet serv = new LoginServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(serv), "/login");

    HttpServlet registerServlet = new RegisterServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(registerServlet), "/register");

    HttpServlet listUsersServlet = new RechercheUtilisateursServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(listUsersServlet), "/listUsers");

    context.setResourceBase("view");
    server.setHandler(context);
    server.start();
  }

}
