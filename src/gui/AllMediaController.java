package gui;

import function.LibraryManager;
import function.Medium;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AllMediaController {
    @FXML
    private Button closeButton;

    @FXML
    private TextArea mediaShowed;

    @FXML
    private Button borrowedMediaButton;

    @FXML
    private Button sortTitleButton;

    /**
     * Die gemeinsam genutzte Instanz des LibraryManagers
     * Wird nicht hier erzeugt, sondern von außen übergeben -> "Dependency Injection"
     */
    private LibraryManager libraryManager;

    /**
     * Setzt libraryManager für den aktuellen Controller
     *
     * @param libraryManager gemeinsam genutze Instanz -> darf nicht 0 sein
     */
    public void setLibraryManager(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }


    /**
     * Schließt das aktuelle Fenster
     *
     * @param event Actionevent das durch closeButton ausgelöst wurde
     */
    public void closing(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Ändert das fxml File auf home.fxml
     *
     * @param event Actionevent das durch einen Button ausgelöst wird
     * @throws IOException wirft eine IOException bei Fehlern
     */
    public void goHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        // LibraryManager an HomeController übergeben
        HomeController controller = loader.getController();
        controller.setLibraryManager(libraryManager);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Zeigt alle Medien der Liste<function.Medium> im Textfield listedMediunms an
     *
     * @param event Actionevent das durch einen Button ausgelöst wurde
     * @throws IOException
     */
    public void showAllMedia(ActionEvent event) throws IOException {
        //TODO: schöne Ausgabe pro Attribut
        mediaShowed.clear(); //damit es leer ist
        for (Medium medium : this.libraryManager.media) {
            mediaShowed.appendText(medium.toString() + "\n");
        }
    }

    /**
     * Zeigt nur aktuell ausgeliehene Medien an.
     *
     * @param event ausgelöst durch einen Button
     */
    public void showBorrowedMedia(ActionEvent event) {
        mediaShowed.clear();

        for (Medium medium : libraryManager.getMedia()) {
            if (medium.isBorrowed()) {
                mediaShowed.appendText(medium.toString() + "\n");
            }
        }
    }

    /**
     * Blendet die Sortier- und Filteroptionen ein oder aus.
     *
     * @param event ausgelöst durch einen Button
     */
    public void showSortOptions(ActionEvent event) {
        boolean sichtbar = borrowedMediaButton.isVisible();

        borrowedMediaButton.setVisible(!sichtbar);
        borrowedMediaButton.setManaged(!sichtbar);

        sortTitleButton.setVisible(!sichtbar);
        sortTitleButton.setManaged(!sichtbar);
    }

    /**
     * Sortiert die Medienliste alphabetisch nach Titel
     * und aktualisiert die Anzeige.
     *
     * @param event ausgelöst durch einen Button
     */
    public void sortByTitle(ActionEvent event) {

        libraryManager.sortByTitle();

        mediaShowed.clear();

        for (Medium medium : libraryManager.getMedia()) {
            mediaShowed.appendText(medium.toString() + "\n");
        }
    }
}
