package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Manager;
import model.validators.ValidationException;
import service.ServiceException;

public class LoginController extends AbstractController {
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordTextField;

    public void login() {
        try {
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            Manager loggedAccount = service.login(username, password);
            application.changeToAdmin(loggedAccount);
            reset();
        } catch (ValidationException | ServiceException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login failed!");
            alert.setHeaderText("Invalid credentials!");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void close() {
        application.changeToMain();
        reset();
    }

    @Override
    public void reset() {
        usernameTextField.clear();
        passwordTextField.clear();
    }
}
