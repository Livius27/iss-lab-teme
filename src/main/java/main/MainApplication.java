package main;

import controller.AbstractController;
import controller.AdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Manager;
import model.validators.AccountValidator;
import model.validators.SpectacolValidator;
import repository.IAccountRepository;
import repository.ISpectacolRepository;
import repository.database.AccountDBRepository;
import repository.database.SpectacolDBRepository;
import service.AccountService;
import service.SpectacolService;
import service.SuperService;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class MainApplication extends Application {
    SuperService service;
    URL loginFXMLURL, mainFXMLURL, adminFXMLURL;
    Scene loginScene, mainScene, adminScene;
    Stage primaryStage;
    AdminController adminController;

    public static Properties setUpDBProperties() {
        Properties props = new Properties();
        try {
            props.load(new FileReader("db.config"));
        } catch (IOException e) {
            System.out.println("Cannot find db.config " + e);
        }
        return props;
    }

    public static void main(String[] args) {
        launch();
    }

    private void initServices() {
        Properties props = setUpDBProperties();
        IAccountRepository accountRepo = new AccountDBRepository(props);
        ISpectacolRepository spectacolRepo = new SpectacolDBRepository(props);

        AccountValidator accountValidator = new AccountValidator();
        SpectacolValidator spectacolValidator = new SpectacolValidator();

        AccountService accountService = new AccountService(accountRepo, accountValidator);
        SpectacolService spectacolService = new SpectacolService(spectacolRepo, spectacolValidator);

        this.service = new SuperService(accountService, spectacolService);
    }

    private void initURLs() {
        loginFXMLURL = getClass().getResource("/login-view.fxml");
        mainFXMLURL = getClass().getResource("/main-view.fxml");
        adminFXMLURL = getClass().getResource("/admin-view.fxml");
    }

    private Scene initScene(URL url) throws IOException {
        FXMLLoader loader = new FXMLLoader(url);
        Parent parent = loader.load();
        AbstractController controller = loader.getController();
        controller.setService(service);
        controller.setApplication(this);
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
        mainScene = initScene(mainFXMLURL);
    }

    @Override
    public void init() throws IOException {
        initServices();
        initURLs();
        initScenes();
    }

    public void changeToMain() {
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
        changeToMain();
        primaryStage.show();
    }
}