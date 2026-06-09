package gui;

import function.Medium;
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
import java.nio.file.Files;
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
    private Label extraLabel1;
    @FXML
    private Label extraLabel2;
    @FXML
    private TextField extraField1;
    @FXML
    private TextField extraField2;

    /**
     * Die gemeinsam genutzte Instanz des LibraryManagers
     * Wird nicht hier erzeugt, sondern von außen übergeben -> "Dependency Injection"
     */
    private function.LibraryManager libraryManager;

    /**
     * Setzt libraryManager für den aktuellen Controller
     *
     * @param libraryManager gemeinsam genutze Instanz -> darf nicht 0 sein
     */
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        // LibraryManager an HomeController übergeben, damit die Liste erhalten bleibt
        HomeController controller = loader.getController();
        controller.setLibraryManager(libraryManager);

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Ändert den Labeltext von selectedMedium zum Inhalt text
     *
     * @param text String der ins Label geschrieben wird
     */
//    public void setLabeltext(String text) {
//        selectedMedium.setText(text);
//    }

    public void setLabeltext(String text) {
        selectedMedium.setText(text);

        if (text.equalsIgnoreCase("Book")) {
            extraLabel1.setText("Autor");
            extraLabel2.setText("ISBN");

        } else if (text.equalsIgnoreCase("CD")) {
            extraLabel1.setText("Artist");
            extraLabel2.setText("Album");

        } else if (text.equalsIgnoreCase("DVD")) {
            extraLabel1.setText("Director");
            extraLabel2.setText("FSK");
        }

        extraField1.clear();
        extraField2.clear();
    }

    //Nur Titel beim Entfernen und Ausborgen und Zurückgeben
    public void removeMedium(ActionEvent event) throws IOException {
        if (libraryManager == null) {
            System.out.println("libraryManager is null");
            return;
        }
        String titleText = title.getText().trim();
        if (titleText.isEmpty()) {
            System.out.println("Kein Titel eingegeben.");
            return;
        }
        libraryManager.removeMedium(titleText);
        // Felder leeren nach dem Entfernen
        clearFields();
        System.out.println("Medium wurde erfolgreich entfernt");
    }

    /**
     * Leert alle Eingabefelder um neuen Eintrag zu machen
     */
    private void clearFields() {
        title.clear();
        published.clear();
        category.clear();
        originalLanguage.clear();
        extraField1.clear();
        extraField2.clear();
    }

    public void addMedium(ActionEvent event) throws IOException {

        String medium = selectedMedium.getText();

        List<String> attributes = new ArrayList<>();
        attributes.add(medium);
        attributes.add(title.getText());
        attributes.add(published.getText());
        attributes.add(category.getText());
        attributes.add(originalLanguage.getText());

        attributes.add("false");
        attributes.add("0");
        attributes.add("1");

        attributes.add(extraField1.getText());
        attributes.add(extraField2.getText());

        String line = String.join(";", attributes);

        libraryManager.addMedium(line);

        clearFields();
        System.out.println("Medium wurde erfolgreich hinzugefügt");
    }

    public void borrowMedium(ActionEvent event) throws IOException {
        String whichMedium = selectedMedium.getText();
        //TODO: attribut ausgeborgt auf true setzen
            if (libraryManager == null) {
                System.out.println("libraryManager is null");
                return;
            }

            String titleText = title.getText().trim();

            //wenn kein text gefunden, methode beenden
            if (titleText.isEmpty()) {
                System.out.println("Kein Titel eingegeben.");
                return;
            }

            for (Medium medium : libraryManager.getMedia()) {
                if (medium.getTitle().equalsIgnoreCase(titleText)) {


                    if (medium.isBorrowed()) {
                        System.out.println(whichMedium+" ist bereits ausgeliehen.");
                        return;
                    }

                    medium.setBorrowed(true);
                    System.out.println(whichMedium+" wurde ausgeliehen.");
                    clearFields();
                    return;
                }
            }

            System.out.println(whichMedium+" nicht gefunden.");
        }

    public void returnMedium(ActionEvent event) throws IOException {
        //TODO: attribut ausgeborgt auf false setzen
    }

}
