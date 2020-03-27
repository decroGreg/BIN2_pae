package be.ipl.pae.main;

import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.biz.ucc.ClientUccImpl;
import be.ipl.pae.biz.ucc.UserUccImpl;
import be.ipl.pae.dal.impl.DAOServicesImpl;
import be.ipl.pae.dal.impl.UserDAOImpl;
import be.ipl.pae.dal.interfaces.DAOServices;
import be.ipl.pae.dal.interfaces.UserDAO;
import be.ipl.pae.ihm.servlet.ConfirmationRegisterServlet;
import be.ipl.pae.ihm.servlet.LoginServlet;
import be.ipl.pae.ihm.servlet.RegisterServlet;
import be.ipl.pae.ihm.servlet.VoirDevisClientServlet;
import be.ipl.pae.ihm.servlet.VoirDevisServlet;
import be.ipl.pae.ihm.servlet.VoirUtilisateursServlet;

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
    ClientDto clientDto = factory.getClientDto();
    ClientUcc clientUcc = new ClientUccImpl(factory, userDao);

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

    HttpServlet listeUsersServlet = new VoirUtilisateursServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(listeUsersServlet), "/listeUsers");

    HttpServlet listeDevisServlet = new VoirDevisServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(listeDevisServlet), "/listeDevis");

    HttpServlet listeDevisClientServlet = new VoirDevisClientServlet(clientUcc, clientDto);
    context.addServlet(new ServletHolder(listeDevisClientServlet), "/listeDevisClient");

    context.setResourceBase("view");
    server.setHandler(context);
    server.start();
  }

}
