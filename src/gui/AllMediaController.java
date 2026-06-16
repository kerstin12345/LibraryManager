package gui;

import function.LibraryManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class AllMediaController {
    @FXML
    private Button closeButton;

    @FXML
    private TextArea mediaShowed;

    @FXML
    private Button borrowedMediaButton;

    @FXML
    private Button sortTitleButton;

    private LibraryManager libraryManager;

    /**
     * Setzt die gemeinsam genutzte Instanz des LibraryManagers.
     * @param libraryManager Instanz des LibraryManagers
     */
    public void setLibraryManager(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    /**
     * Schließt das aktuelle Fenster
     * @param event ActionEvent, ausgelöst durch den Close-Button
     */
    public void closing(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Wechselt zurück zur Startansicht (home.fxml).
     * Die aktuelle LibraryManager-Instanz wird an den HomeController übergeben.
     * @param event ActionEvent, ausgelöst durch einen Button
     * @throws IOException falls das Laden der FXML-Datei fehlschlägt
     */
    public void goHome(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.setLibraryManager(libraryManager);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Zeigt alle gespeicherten Medien im TextArea-Feld an.
     *
     * @param event ActionEvent, ausgelöst durch einen Button
     * @throws IOException kann bei Oberflächenoperationen auftreten
     */
    public void showAllMedia(ActionEvent event) throws IOException {
        mediaShowed.setText(libraryManager.getAllMediaAsText());
    }

    /**
     * Zeigt nur die aktuell ausgeliehenen Medien im TextArea-Feld an.
     * @param event ActionEvent, ausgelöst durch einen Button
     */
    public void showBorrowedMedia(ActionEvent event) {
        mediaShowed.setText(libraryManager.getBorrowedMediaAsText());
    }

    /**
     * Blendet die Sortier- und Filteroptionen ein oder aus.
     * Die Sichtbarkeit der Buttons wird dabei umgeschaltet.
     * @param event ActionEvent, ausgelöst durch den Sortier-Button
     */
    public void showSortOptions(ActionEvent event) {
        boolean sichtbar = borrowedMediaButton.isVisible();

        borrowedMediaButton.setVisible(!sichtbar);
        borrowedMediaButton.setManaged(!sichtbar);

        sortTitleButton.setVisible(!sichtbar);
        sortTitleButton.setManaged(!sichtbar);
    }

    /**
     * Sortiert alle Medien alphabetisch nach ihrem Titel
     * und aktualisiert anschließend die Anzeige.
     * @param event ActionEvent, ausgelöst durch einen Button
     */
    public void sortByTitle(ActionEvent event) {
        mediaShowed.setText(libraryManager.sortByTitleAndGetText());
    }
}
