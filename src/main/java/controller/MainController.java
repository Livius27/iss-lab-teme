package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Loc;
import model.Spectator;
import model.StareLoc;
import model.validators.ValidationException;
import service.ServiceException;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MainController extends AbstractController {
    ObservableList<String> modelSpectacole = FXCollections.observableArrayList();
    ObservableList<String> modelRezervari = FXCollections.observableArrayList();
    Set<Loc> seatsToBook = new HashSet<Loc>();
    String currentShowTitle;

    @FXML
    ListView<String> spectacoleListView;
    @FXML
    ListView<String> rezervariListView;
    @FXML
    GridPane seatsConfigurationGrid;
    @FXML
    TextField numeTextField, prenumeTextField, emailTextField;

    @FXML
    public void initialize() {
        spectacoleListView.setItems(modelSpectacole);
        rezervariListView.setItems(modelRezervari);
    }

    private void updateModelSpectacole() {
        List<String> allSpectacole = new ArrayList<String>();
        service.getAllSpectacole().forEach(spectacol -> allSpectacole
                .add(spectacol.getTitlu() + " | " + spectacol.getData() + " | "
                        + spectacol.getNrLocuriDisponibile() + " locuri dispinibile"));

        modelSpectacole.setAll(allSpectacole);
    }

    private void updateModelRezervari() {
        List<String> allRezervariFromSpectacol = new ArrayList<String>();

        if (!this.currentShowTitle.equals("")) {
            service.getAllRezervariFromSpectacol(this.currentShowTitle).forEach(rezervare -> {
                Spectator spectator = service.getSpectatorInfo(rezervare.getIdSpectator());
                Loc locRezervat = service.getLocInfo(rezervare.getIdLocRezervat());
                allRezervariFromSpectacol.add(spectator.getNume() + " " + spectator.getPrenume() + " | "
                        + "Nr. " + locRezervat.getNumar() + ", Rand: " + locRezervat.getRand() + ", Loja: " + locRezervat.getLoja());
            });

            modelRezervari.setAll(allRezervariFromSpectacol);
        }
    }

    public void generateTheatreSeatsConfigurationForShow() {
        if (spectacoleListView.getSelectionModel().getSelectedItem() != null) {
            List<Button> seatsList = new ArrayList<Button>();
            this.seatsToBook.clear();
            this.currentShowTitle = "";
            String selectedText = spectacoleListView.getSelectionModel().getSelectedItem().strip();
            String[] splitText = selectedText.split("[|]");
            this.currentShowTitle = splitText[0].strip();
            updateModelRezervari();

            service.getSpectacolByTitlu(this.currentShowTitle).getLocuri().forEach(loc -> {
                if (loc.getStareLoc().equals(StareLoc.LIBER)) {
                    Button seatButton = new Button("Nr. " + loc.getNumar() + "|Rand: " + loc.getRand() + "|Loja: " + loc.getLoja() + "\n" +
                            loc.getPret() + " RON" + " | " + loc.getStareLoc().toString());
                    seatButton.setDisable(false);
                    seatButton.setOnAction(event -> {
                        this.seatsToBook.add(loc);
                        for (Button btn : seatsList) {
                            if (btn.getText().equals(seatButton.getText()))
                                btn.setStyle("-fx-background-color: green");
                        }
                    });
                    seatsList.add(seatButton);
                }
                if (loc.getStareLoc().equals(StareLoc.REZERVAT)) {
                    Button seatButton = new Button("Nr. " + loc.getNumar() + "|Rand: " + loc.getRand() + "|Loja: " + loc.getLoja() + "\n" +
                            loc.getPret() + " RON" + " | " + loc.getStareLoc().toString());
                    seatButton.setDisable(true);
                    seatsList.add(seatButton);
                }
                if (loc.getStareLoc().equals(StareLoc.INDISPONIBIL)) {
                    Button seatButton = new Button("Nr. " + loc.getNumar() + "|Rand: " + loc.getRand() + "|Loja: " + loc.getLoja() + "\n" +
                            loc.getPret() + " RON" + " | " + loc.getStareLoc().toString());
                    seatButton.setDisable(true);
                    seatsList.add(seatButton);
                }
            });

            seatsConfigurationGrid.getChildren().clear();
            int row = 0;
            int col = 0;
            for (int i = 0; i < seatsList.size(); i++) {
                seatsConfigurationGrid.add(seatsList.get(i), col, row);
                col++;
                if ((i + 1) % 10 == 0) {
                    row++;
                    col = 0;
                }
            }
        }
    }

    public void rezervaLoc() {
        String nume = numeTextField.getText();
        String prenume = prenumeTextField.getText();
        String email = emailTextField.getText();

        if (!this.seatsToBook.isEmpty()) {
            try {
                Spectator saved = service.addNewSpectator(nume, prenume, email);
                for (Loc locRezervat : seatsToBook)
                    service.addNewRezervare(saved.getId(), locRezervat.getId(), this.currentShowTitle);
                service.getAllSpectacole().forEach(spectacol -> {
                    if (spectacol.getTitlu().equals(this.currentShowTitle)) {
                        spectacol.setNrLocuriDisponibile(spectacol.getNrLocuriDisponibile() - this.seatsToBook.size());
                        service.modifyExistingSpectacol(spectacol);
                    }
                });
                reset();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Operation was completed successfully!");
                alert.setContentText("Your booking was saved!");
                alert.showAndWait();
            } catch (ValidationException | ServiceException ex) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setHeaderText("Operation failed!");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setHeaderText("Operation failed!");
            alert.setContentText("No seats selected for booking!");
            alert.showAndWait();
        }
    }

    public void init() {
        updateModelSpectacole();
        seatsConfigurationGrid.getChildren().clear();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    reset();
                });
            }
        }, 25000, 25000);
    }

    public void openLoginWindow() {
        this.application.changeToLogin();
        reset();
    }

    @Override
    public void reset() {
        numeTextField.clear();
        prenumeTextField.clear();
        emailTextField.clear();
        updateModelSpectacole();
        modelRezervari.clear();
        seatsConfigurationGrid.getChildren().clear();
        this.seatsToBook.clear();
        this.currentShowTitle = "";
    }
}
