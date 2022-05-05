package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainController extends AbstractController {
    ObservableList<String> modelSpectacole = FXCollections.observableArrayList();

    @FXML
    ListView<String> spectacoleListView;

    @FXML
    public void initialize() {
        spectacoleListView.setItems(modelSpectacole);
    }

    private void updateModelSpectacole() {
        List<String> allSpectacole = new ArrayList<String>();
        service.getAllSpectacole().forEach(spectacol -> allSpectacole
                .add(spectacol.getTitlu() + " | " + spectacol.getData() + " | " + spectacol.getNrLocuriDisponibile() + " locuri dispinibile"));

        modelSpectacole.setAll(allSpectacole);
    }

    public void init() {
        updateModelSpectacole();
    }

    public void openLoginWindow() {
        this.application.changeToLogin();
    }
}
