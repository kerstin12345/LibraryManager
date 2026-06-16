package gui;

import function.LibraryManager;
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
    private Label extraLabel1;
    @FXML
    private Label extraLabel2;
    @FXML
    private TextField extraField1;
    @FXML
    private TextField extraField2;
    @FXML
    private Label aktionErfolgreich;

    private static final String FILE_PATH = "src/resources/media.csv";

    private LibraryManager libraryManager;

    /**
     * Setzt die gemeinsam genutzte Instanz des LibraryManagers.
     * @param libraryManager Instanz des LibraryManagers
     */
    public void setLibraryManager(LibraryManager libraryManager) {
        this.libraryManager = libraryManager;
    }

    /**
     * Speichert die aktuelle Medienliste und schließt das Fenster.
     * @param event ActionEvent, ausgelöst durch den Close-Button
     */
    public void closing(ActionEvent event) {
        if (libraryManager != null) {
            libraryManager.writeFile(FILE_PATH);
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Speichert die Medienliste und wechselt zurück zur Startansicht.
     * @param event ActionEvent, ausgelöst durch einen Button
     * @throws IOException falls das Laden der FXML-Datei fehlschlägt
     */
    public void goHome(ActionEvent event) throws IOException {
        libraryManager.writeFile(FILE_PATH);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        HomeController controller = loader.getController();
        controller.setLibraryManager(libraryManager);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Setzt den aktuell ausgewählten Medientyp und passt
     * die Beschriftungen der Zusatzfelder entsprechend an.
     * @param text ausgewählter Medientyp (Book, CD oder DVD)
     */
    public void setLabeltext(String text) {
        selectedMedium.setText(text);

        String[] labels = libraryManager.getExtraLabels(text);
        extraLabel1.setText(labels[0]);
        extraLabel2.setText(labels[1]);

        extraField1.clear();
        extraField2.clear();
    }

    /**
     * Entfernt ein Medium anhand des eingegebenen Titels
     * und des ausgewählten Medientyps.
     * @param event ActionEvent, ausgelöst durch den Remove-Button
     * @throws IOException kann bei Dateioperationen auftreten
     */
    public void removeMedium(ActionEvent event) throws IOException {
        String message = libraryManager.removeMediumWithMessage(title.getText(), selectedMedium.getText());
        setAktionErfolgreich(message);

        if (message.contains("erfolgreich")) {
            clearFields();
        }
    }

    /**
     * Erstellt ein neues Medium aus den eingegebenen Daten
     * und fügt es der Medienliste hinzu.
     * @param event ActionEvent, ausgelöst durch den Add-Button
     * @throws IOException kann bei Dateioperationen auftreten
     */
    public void addMedium(ActionEvent event) throws IOException {
        String message = libraryManager.addMedium(
                selectedMedium.getText(),
                title.getText(),
                published.getText(),
                category.getText(),
                originalLanguage.getText(),
                extraField1.getText(),
                extraField2.getText()
        );

        setAktionErfolgreich(message);

        if (message.contains("erfolgreich")) {
            clearFields();
        }
    }

    /**
     * Leiht das ausgewählte Medium aus.
     * @param event ActionEvent, ausgelöst durch den Borrow-Button
     * @throws IOException kann bei Dateioperationen auftreten
     */
    public void borrowMedium(ActionEvent event) throws IOException {
        String message = libraryManager.borrowMedium(title.getText(), selectedMedium.getText());
        setAktionErfolgreich(message);

        if (message.contains("erfolgreich")) {
            clearFields();
        }
    }

    /**
     * Gibt ein ausgeliehenes Medium zurück.
     * @param event ActionEvent, ausgelöst durch den Return-Button
     * @throws IOException kann bei Dateioperationen auftreten
     */
    public void returnMedium(ActionEvent event) throws IOException {
        String message = libraryManager.returnMedium(title.getText(), selectedMedium.getText());
        setAktionErfolgreich(message);

        if (message.contains("erfolgreich")) {
            clearFields();
        }
    }

    /**
     * Leert alle Eingabefelder der Oberfläche.
     */
    private void clearFields() {
        title.clear();
        published.clear();
        category.clear();
        originalLanguage.clear();
        extraField1.clear();
        extraField2.clear();
    }

    /**
     * Zeigt eine Statusmeldung auf der Oberfläche an.
     * @param text anzuzeigende Meldung
     */
    public void setAktionErfolgreich(String text) {
        aktionErfolgreich.setText(text);
    }
}
