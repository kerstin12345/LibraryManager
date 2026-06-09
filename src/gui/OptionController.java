package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OptionController {
    @FXML
    private Button closeButton;
    @FXML
    private Label selectedMedium;
    @FXML
    private TextField title;
    @FXML
    private TextField published;
    @FXML
    private TextField category;
    @FXML
    private TextField originalLanguage;
    @FXML
    private TextField autor;
    @FXML
    private TextField isbn;
    @FXML
    private TextField director;
    @FXML
    private TextField fsk;
    @FXML
    private TextField artist;
    @FXML
    private TextField album;

    private function.LibraryManager libraryManager;

    public void setLibraryManager(function.LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    /**
     * Schließt das aktuelle Fenster in dem sich closeButton befindet
     *
     * @param event Actionevent das durch das Drücken von closeButton ausgelöst wird
     */
    public void closing(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Wechselt das fxml File zu home.fxml und kehrt zum Start zurück
     *
     * @param event Actionevent das durch Drücken eines Buttons ausgelöst wird
     * @throws IOException wirft eine IOException bei Fehlern
     */
    public void goHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Ändert den Labeltext von selectedMedium zum Inhalt text
     *
     * @param text String der ins Label geschrieben wird
     */
    public void setLabeltext(String text) {
        selectedMedium.setText(text);
    }

    //Nur Titel beim Entfernen und Ausborgen und Zurückgeben
    public void removeMedium(ActionEvent event) throws IOException {
        if (libraryManager == null) {
            System.out.println("libraryManager is null");
            return;
        }
        libraryManager.removeMedium(selectedMedium.getText());
    }

    public void addMedium(ActionEvent event) throws IOException {
        //Objekt wird ertsellt und in Liste ; gertrennt line
        String medium = selectedMedium.getText();
        List<String> attributes = new ArrayList<>();
        attributes.add(title.getText());
        attributes.add(published.getText());
        attributes.add(category.getText());
        attributes.add(originalLanguage.getText());

        if (medium.equalsIgnoreCase("DVD")) {
            attributes.add(director.getText());
            attributes.add(fsk.getText());
        } else if (medium.equalsIgnoreCase("CD")) {
            attributes.add(artist.getText());
            attributes.add(album.getText());
        } else if (medium.equalsIgnoreCase("Book")) {
            attributes.add(autor.getText());
            attributes.add(isbn.getText());
        } else {
            System.out.println("medium is not a valid medium");
        }

        String line = medium+";";
        while (attributes.size() > 0) {
            line += attributes.get(0);
            if (attributes.size() > 1) {
                line += ";";
            }
            attributes.remove(0);
        }

        libraryManager.addMedium(line);
    }
}
