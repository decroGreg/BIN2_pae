package be.ipl.pae.main;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.biz.ucc.ClientUccImpl;
import be.ipl.pae.biz.ucc.DevisUccImpl;
import be.ipl.pae.biz.ucc.UserUccImpl;
import be.ipl.pae.dal.impl.ClientDaoImpl;
import be.ipl.pae.dal.impl.DaoServicesImpl;
import be.ipl.pae.dal.impl.UserDaoImpl;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DaoServices;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.ihm.servlet.ConfirmationRegisterServlet;
import be.ipl.pae.ihm.servlet.DetailsDevisServlet;
import be.ipl.pae.ihm.servlet.LoginServlet;
import be.ipl.pae.ihm.servlet.RegisterServlet;
import be.ipl.pae.ihm.servlet.VoirClientsServlet;
import be.ipl.pae.ihm.servlet.VoirDevisClientServlet;
import be.ipl.pae.ihm.servlet.VoirDevisServlet;
import be.ipl.pae.ihm.servlet.VoirUtilisateursServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServlet;

public class Main {
  public static void main(String[] args) throws Exception {

    // Lecture du fichier properties
    Config conf = new Config();

    // Creation de la dépendance
    Factory factory = (Factory) conf.getConfigPropertyClass("factory.Factory");

    DaoServices daoService = new DaoServicesImpl();
    UserDao userDao = new UserDaoImpl();
    ClientDao clientDao = new ClientDaoImpl();
    UserUcc userUcc = new UserUccImpl(factory, userDao);
    UserDto userDto = factory.getUserDto();
    ClientDto clientDto = factory.getClientDto();
    ClientUcc clientUcc = new ClientUccImpl(factory, clientDao);
    DevisUcc devisUcc = new DevisUccImpl(factory, clientDao);
    DevisDto devisDto = factory.getDevisDto();

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

    HttpServlet listeDevisClientServlet = new VoirDevisClientServlet(devisUcc, clientDto);
    context.addServlet(new ServletHolder(listeDevisClientServlet), "/listeDevisClient");

    HttpServlet listeClientsServlet = new VoirClientsServlet(clientUcc, userDto);
    context.addServlet(new ServletHolder(listeClientsServlet), "/listeClients");

    HttpServlet detailsDevisServlet = new DetailsDevisServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(detailsDevisServlet), "/detailsDevis");
    context.setResourceBase("view");
    server.setHandler(context);
    server.start();
  }

}
