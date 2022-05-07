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
        for (int i = 1; i <= 60; i++)
            nrLocuriDisponibileList.add(String.valueOf(i));
        this.nrLocuriDisponibileComboBox.getItems().setAll(nrLocuriDisponibileList);
        this.nrLocuriDisponibileComboBox.getSelectionModel().select(0);
        updateModelSpectacole();
    }

    public void loadSpectacolDetails() {
        if (spectacoleTableView.getSelectionModel().getSelectedItem() != null) {
            Spectacol spectacol = spectacoleTableView.getSelectionModel().getSelectedItem();
            titluTextField.setText(spectacol.getTitlu());
            dataDatePicker.getEditor().setText(spectacol.getData());
            nrLocuriDisponibileComboBox.getSelectionModel().select(spectacol.getNrLocuriDisponibile() - 1);
        }
    }

    public void adaugaSpectacol() {
        try {
            if (dataDatePicker.getValue() != null) {
                String titlu = titluTextField.getText();
                String data = dataDatePicker.getValue().toString();
                int nrLocuriDisponibile = Integer.parseInt(nrLocuriDisponibileComboBox.getValue());
                service.addNewSpectacol(titlu, data, nrLocuriDisponibile);
                updateModelSpectacole();
                reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Operation was completed successfully!");
                alert.setContentText("Show was saved!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Operation failed!");
                alert.setContentText("Please enter the date of the show!");
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

    public void stergeSpectacol() {
        try {
            if (titluTextField.getText() != null && !titluTextField.getText().equals("")) {
                String titluForSearch = spectacoleTableView.getSelectionModel().getSelectedItem().getTitlu();
                Spectacol spectacolToDelete = service.getSpectacolByTitlu(titluForSearch);
                service.removeExistingSpectacol(spectacolToDelete.getId());
                reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Operation was completed successfully!");
                alert.setContentText("Show was removed!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Operation failed!");
                alert.setContentText("No show to delete with the given title!");
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

    public void modificaSpectacol() {
        try {
            if (dataDatePicker.getValue() != null) {
                String titluForSearch = spectacoleTableView.getSelectionModel().getSelectedItem().getTitlu();
                Spectacol newSpectacol = service.getSpectacolByTitlu(titluForSearch);
                String titlu = titluTextField.getText();
                String data = dataDatePicker.getValue().toString();

                newSpectacol.setTitlu(titlu);
                newSpectacol.setData(data);
                service.modifyExistingSpectacol(newSpectacol);
                reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Operation was completed successfully!");
                alert.setContentText("Show was updated!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Operation failed!");
                alert.setContentText("Please enter the new date for the show!");
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

    public void logout() {
        application.changeToMain();
        reset();
    }

    @Override
    public void reset() {
        titluTextField.clear();
        dataDatePicker.getEditor().clear();
        updateModelSpectacole();
    }
}
