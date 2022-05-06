package main;

import controller.AbstractController;
import controller.AdminController;
import controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Manager;
import model.validators.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.*;
import repository.database.*;
import service.*;

import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    SuperService service;
    URL loginFXMLURL, mainFXMLURL, adminFXMLURL;
    Scene loginScene, mainScene, adminScene;
    Stage primaryStage;
    AdminController adminController;
    MainController mainController;
    private static SessionFactory sessionFactory;

    private static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        }
        catch (Exception ex) {
            System.err.println("Exception " + ex);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * Closes the application database session
     */
    static void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }

    private void initServices() {
        initialize();
        IAccountRepository accountRepo = new AccountORMRepository(sessionFactory);
        ILocRepository locRepo = new LocORMRepository(sessionFactory);
        IRezervareRepository rezervareRepo = new RezervareORMRepository(sessionFactory);
        ISpectacolRepository spectacolRepo = new SpectacolORMRepository(sessionFactory);
        ISpectatorRepository spectatorRepo = new SpectatorORMRepository(sessionFactory);

        AccountValidator accountValidator = new AccountValidator();
        LocValidator locValidator = new LocValidator();
        RezervareValidator rezervareValidator = new RezervareValidator();
        SpectacolValidator spectacolValidator = new SpectacolValidator();
        SpectatorValidator spectatorValidator = new SpectatorValidator();

        AccountService accountService = new AccountService(accountRepo, accountValidator);
        LocService locService = new LocService(locRepo, locValidator);
        RezervareService rezervareService = new RezervareService(rezervareRepo, rezervareValidator);
        SpectacolService spectacolService = new SpectacolService(spectacolRepo, spectacolValidator);
        SpectatorService spectatorService = new SpectatorService(spectatorRepo, spectatorValidator);

        this.service = new SuperService(accountService, locService, rezervareService, spectacolService, spectatorService);
    }

    private void initURLs() {
        loginFXMLURL = getClass().getResource("/views/login-view.fxml");
        mainFXMLURL = getClass().getResource("/views/main-view.fxml");
        adminFXMLURL = getClass().getResource("/views/admin-view.fxml");
    }

    private Scene initScene(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        AbstractController controller = loader.getController();
        controller.setService(service);
        controller.setApplication(this);
        return new Scene(parent);
    }

    private Scene initMainScene(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        mainController = loader.getController();
        mainController.setService(service);
        mainController.setApplication(this);
        return new Scene(parent);
    }

    private Scene initAdminScene(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        adminController = loader.getController();
        adminController.setService(service);
        adminController.setApplication(this);
        return new Scene(parent);
    }

    private void initScenes() throws IOException {
        loginScene = initScene(loginFXMLURL);
        adminScene = initAdminScene(adminFXMLURL);
        mainScene = initMainScene(mainFXMLURL);
    }

    @Override
    public void init() throws IOException {
        initServices();
        initURLs();
        initScenes();
    }

    public void changeToMain() {
        mainController.init();
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("RezervariLocuri");
        primaryStage.centerOnScreen();
    }

    public void changeToAdmin(Manager manager) {
        adminController.init(manager);
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("RezervariLocuri Administration");
        primaryStage.centerOnScreen();
    }

    public void changeToLogin() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("RezervariLocuri Admin login");
        primaryStage.centerOnScreen();
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setOnCloseRequest(t -> {
            close();
            Platform.exit();
            System.exit(0);
        });
        changeToMain();
        primaryStage.show();
    }
}
