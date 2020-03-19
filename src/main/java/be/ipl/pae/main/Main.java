package be.ipl.pae.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import DAL.DAOServicesImpl;
import DAL.UserDAOImpl;
import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.UserDTO;
import be.ipl.pae.biz.factory.FactoryImpl;
import be.ipl.pae.biz.interfaces.DAOServices;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.UserDAO;
import be.ipl.pae.biz.interfaces.UserUCC;
import be.ipl.pae.biz.ucc.UserUCCImpl;
import be.ipl.pae.ihm.servlet.LoginServlet;
import be.ipl.pae.ihm.servlet.RegisterServlet;

public class Main {
  public static void main(String[] args) throws Exception {

	//Lecture du fichier properties
	Config conf = new Config();
	
	//Creation de la dépendance
	Factory factory = (Factory)conf.getConfigPropertyClass("factory.Factory");
	
	DAOServices daoService = new DAOServicesImpl();
    UserDAO userDao = new UserDAOImpl();
    UserUCC userUcc = new UserUCCImpl(factory, userDao);
    UserDTO userDto = factory.getUserDTO();

    Server server = new Server(8080);
    WebAppContext context = new WebAppContext();



    System.out.println(context.getContextPath());
    context.setContextPath("/");

    System.out.println("test");
    // regarder à quoi ça sert vraimant
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");


    HttpServlet serv = new LoginServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(serv), "/login");

    HttpServlet registerServlet = new RegisterServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(registerServlet), "/register");

    context.setResourceBase("view");
    server.setHandler(context);
    server.start();
  }

}
