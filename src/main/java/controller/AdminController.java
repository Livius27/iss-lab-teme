package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Manager;
import model.Spectacol;
import model.validators.ValidationException;
import service.ServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminController extends AbstractController {
    Manager loggedAccount;
    ObservableList<Spectacol> modelSpectacole = FXCollections.observableArrayList();

    @FXML
    Label loggedInLabel;
    @FXML
    TextField titluTextField;
    @FXML
    DatePicker dataDatePicker;
    @FXML
    ComboBox<String> nrLocuriDisponibileComboBox;
    @FXML
    TextField pretLocTextField;
    @FXML
    TableView<Spectacol> spectacoleTableView;
    @FXML
    TableColumn<Spectacol, String> titluSpectacolColumn, dataSpectacolColumn, locuriDisponibileSpectacolColumn;

    @FXML
    public void initialize() {
        titluSpectacolColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("titlu"));
        dataSpectacolColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("data"));
        locuriDisponibileSpectacolColumn.setCellValueFactory(new PropertyValueFactory<Spectacol, String>("nrLocuriDisponibile"));
        spectacoleTableView.setItems(modelSpectacole);
    }

    private void updateModelSpectacole() {
        List<Spectacol> allSpectacole = new ArrayList<Spectacol>();
        service.getAllSpectacole().forEach(allSpectacole::add);
        modelSpectacole.setAll(allSpectacole);
    }

    public void init(Manager loggedAccount) {
        this.loggedAccount = loggedAccount;
        loggedInLabel.setText("Logged in as: " + loggedAccount.getUsername());

        List<String> nrLocuriDisponibileList = new ArrayList<String>();
        for (int i = 1; i <= 16; i++)
            nrLocuriDisponibileList.add(String.valueOf(i));
        this.nrLocuriDisponibileComboBox.getItems().setAll(nrLocuriDisponibileList);
        this.nrLocuriDisponibileComboBox.getSelectionModel().select(0);
        updateModelSpectacole();
    }

    public void adaugaSpectacol() {
        try {
            if (!Objects.equals(pretLocTextField.getText(), "") && dataDatePicker.getValue() != null) {
                String titlu = titluTextField.getText();
                String data = dataDatePicker.getValue().toString();
                int nrLocuriDisponibile = Integer.parseInt(nrLocuriDisponibileComboBox.getValue());
                service.addNewSpectacol(titlu, data, nrLocuriDisponibile);
                updateModelSpectacole();
                reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Operation was completed successfully!");
                alert.setContentText("Spectacolul a fost salvat!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Operation failed!");
                alert.setContentText("Trebuie introduse data spectacolului si pretul unui loc!");
                alert.showAndWait();
            }
        } catch (ValidationException | ServiceException | NumberFormatException ex) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Operation failed!");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    public void sortSpectacole() {
        modelSpectacole.setAll(service.getAllSpectacoleSorted());
    }

    @Override
    public void reset() {
        titluTextField.clear();
        dataDatePicker.getEditor().clear();
        pretLocTextField.clear();
    }
}
