package be.ipl.pae.main;

import be.ipl.pae.biz.config.Config;
import be.ipl.pae.biz.dto.ClientDto;
import be.ipl.pae.biz.dto.DevisDto;
import be.ipl.pae.biz.dto.PhotoDto;
import be.ipl.pae.biz.dto.UserDto;
import be.ipl.pae.biz.interfaces.AmenagementUcc;
import be.ipl.pae.biz.interfaces.ClientUcc;
import be.ipl.pae.biz.interfaces.DevisUcc;
import be.ipl.pae.biz.interfaces.Factory;
import be.ipl.pae.biz.interfaces.PhotoUcc;
import be.ipl.pae.biz.interfaces.TypeDAmenagementUcc;
import be.ipl.pae.biz.interfaces.UserUcc;
import be.ipl.pae.biz.ucc.AmenagementUccImpl;
import be.ipl.pae.biz.ucc.ClientUccImpl;
import be.ipl.pae.biz.ucc.DevisUccImpl;
import be.ipl.pae.biz.ucc.PhotoUccImpl;
import be.ipl.pae.biz.ucc.TypeDAmenagementUccImpl;
import be.ipl.pae.biz.ucc.UserUccImpl;
import be.ipl.pae.dal.daoservices.DaoServicesImpl;
import be.ipl.pae.dal.impl.AmenagementDaoImpl;
import be.ipl.pae.dal.impl.ClientDaoImpl;
import be.ipl.pae.dal.impl.DevisDaoImpl;
import be.ipl.pae.dal.impl.PhotoDaoImpl;
import be.ipl.pae.dal.impl.TypeDAmenagementDaoImpl;
import be.ipl.pae.dal.impl.UserDaoImpl;
import be.ipl.pae.dal.interfaces.AmenagementDao;
import be.ipl.pae.dal.interfaces.ClientDao;
import be.ipl.pae.dal.interfaces.DevisDao;
import be.ipl.pae.dal.interfaces.PhotoDao;
import be.ipl.pae.dal.interfaces.TypeDAmenagementDao;
import be.ipl.pae.dal.interfaces.UserDao;
import be.ipl.pae.ihm.servlet.AjouterPhotoDevisServlet;
import be.ipl.pae.ihm.servlet.AmenagementServlet;
import be.ipl.pae.ihm.servlet.ChangementEtatDevisServlet;
import be.ipl.pae.ihm.servlet.ChoisirPhotoPrefereeServlet;
import be.ipl.pae.ihm.servlet.ConfirmationRegisterServlet;
import be.ipl.pae.ihm.servlet.DetailsDevisServlet;
import be.ipl.pae.ihm.servlet.IndexServlet;
import be.ipl.pae.ihm.servlet.IntroduireDevisServlet;
import be.ipl.pae.ihm.servlet.LoginServlet;
import be.ipl.pae.ihm.servlet.PhotosClientServlet;
import be.ipl.pae.ihm.servlet.RechercheDevisServlet;
import be.ipl.pae.ihm.servlet.RegisterServlet;
import be.ipl.pae.ihm.servlet.VoirClientsServlet;
import be.ipl.pae.ihm.servlet.VoirDevisClientServlet;
import be.ipl.pae.ihm.servlet.VoirDevisServlet;
import be.ipl.pae.ihm.servlet.VoirTypesAmenagementServlet;
import be.ipl.pae.ihm.servlet.VoirUtilisateursServlet;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.http.HttpServlet;

public class Main {

  /**
   * Main.
   * 
   * @param args les arguments
   * @throws Exception une exception
   */
  public static void main(String[] args) throws Exception {
    // Lecture du fichier properties
    Config conf = new Config();

    // Creation de la dépendance
    Factory factory = (Factory) conf.getConfigPropertyClass("factory.Factory");
    DaoServicesImpl daoServices = new DaoServicesImpl();

    UserDao userDao = new UserDaoImpl(daoServices, factory);
    AmenagementDao amenagementDao = new AmenagementDaoImpl(daoServices, factory);
    ClientDao clientDao = new ClientDaoImpl(daoServices, factory);
    DevisDao devisDao = new DevisDaoImpl(daoServices, factory);
    TypeDAmenagementDao typeAmenagementDao = new TypeDAmenagementDaoImpl(daoServices, factory);
    PhotoDao photoDao = new PhotoDaoImpl(daoServices, factory);

    UserUcc userUcc = new UserUccImpl(factory, userDao, daoServices);
    PhotoDto photoDto = factory.getPhotoDto();
    AmenagementUcc amenagementUcc = new AmenagementUccImpl(amenagementDao, factory, daoServices);
    PhotoUcc photoUcc = new PhotoUccImpl(photoDao, devisDao, amenagementDao, factory, daoServices);

    UserDto userDto = factory.getUserDto();
    ClientDto clientDto = factory.getClientDto();
    ClientUcc clientUcc = new ClientUccImpl(factory, clientDao, daoServices);
    DevisUcc devisUcc =
        new DevisUccImpl(factory, devisDao, userDao, clientDao, amenagementDao, daoServices);
    DevisDto devisDto = factory.getDevisDto();
    TypeDAmenagementUcc typeAmenagmentUcc =
        new TypeDAmenagementUccImpl(factory, typeAmenagementDao, daoServices);
    Server server = new Server(8080);
    WebAppContext context = new WebAppContext();

    System.out.println(context.getContextPath());
    context.setContextPath("/");

    // regarder à quoi ça sert vraimant
    context.setInitParameter("cacheControl", "no-store,no-cache,must-revalidate");

    HttpServlet index = new IndexServlet();
    context.addServlet(new ServletHolder(index), "/");

    HttpServlet serv = new LoginServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(serv), "/login");

    HttpServlet registerServlet = new RegisterServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(registerServlet), "/register");

    HttpServlet listeUsersServlet = new VoirUtilisateursServlet(userUcc, userDto);
    context.addServlet(new ServletHolder(listeUsersServlet), "/listeUsers");

    HttpServlet listeDevisServlet = new VoirDevisServlet(devisUcc, clientUcc, userDto);
    context.addServlet(new ServletHolder(listeDevisServlet), "/listeDevis");

    HttpServlet listeDevisClientServlet =
        new VoirDevisClientServlet(clientUcc, devisUcc, clientDto);
    context.addServlet(new ServletHolder(listeDevisClientServlet), "/listeDevisClient");

    HttpServlet listeClientsServlet = new VoirClientsServlet(clientUcc, userDto);
    context.addServlet(new ServletHolder(listeClientsServlet), "/listeClients");

    HttpServlet introDevisServlet =
        new IntroduireDevisServlet(devisUcc, clientDto, devisDto, typeAmenagmentUcc, photoUcc);
    context.addServlet(new ServletHolder(introDevisServlet), "/introduireServlet");

    HttpServlet confirmationServlet = new ConfirmationRegisterServlet(userUcc, userDto, clientDto);
    context.addServlet(new ServletHolder(confirmationServlet), "/confirmation");


    HttpServlet detailsDevis = new DetailsDevisServlet(devisUcc, clientUcc, amenagementUcc,
        typeAmenagmentUcc, devisDto, photoUcc);
    context.addServlet(new ServletHolder(detailsDevis), "/detailsDevis");

    HttpServlet changementEtatDevis = new ChangementEtatDevisServlet(devisDto, devisUcc, clientUcc,
        amenagementUcc, typeAmenagmentUcc);
    context.addServlet(new ServletHolder(changementEtatDevis), "/changementEtatDevis");

    HttpServlet rechercheDevis = new RechercheDevisServlet(devisUcc, devisDto, clientUcc);
    context.addServlet(new ServletHolder(rechercheDevis), "/rechercheDevis");

    HttpServlet ajouterPhoto = new AjouterPhotoDevisServlet(photoDto, photoUcc, amenagementUcc,
        typeAmenagmentUcc, devisUcc);
    context.addServlet(new ServletHolder(ajouterPhoto), "/ajouterPhoto");

    HttpServlet photoPreferee = new ChoisirPhotoPrefereeServlet(devisUcc, devisDto, photoUcc);
    context.addServlet(new ServletHolder(photoPreferee), "/photoPreferee");

    HttpServlet selectionnerTypeAmenagement =
        new VoirTypesAmenagementServlet(typeAmenagmentUcc, photoUcc, amenagementUcc);
    context.addServlet(new ServletHolder(selectionnerTypeAmenagement), "/voirTypesAmenagement");

    HttpServlet photosClient = new PhotosClientServlet(clientUcc, devisUcc, photoUcc);
    context.addServlet(new ServletHolder(photosClient), "/mesPhotos");

    HttpServlet amenagementServlet = new AmenagementServlet(typeAmenagmentUcc);
    context.addServlet(new ServletHolder(amenagementServlet), "/amenagements");

    context.setWelcomeFiles(new String[] {"index.html"});
    context.setResourceBase("view");
    server.setHandler(context);

    server.start();

  }

}
