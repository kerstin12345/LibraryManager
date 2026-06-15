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
    @FXML
    private Label aktionErfolgreich;

    private static final String FILE_PATH = "src/resources/media.csv";

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
        if (libraryManager != null) {
            libraryManager.writeFile(FILE_PATH);
        }
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
        libraryManager.writeFile(FILE_PATH);
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
            throw new IllegalArgumentException("libraryManager is null");
        }
        String titleText = title.getText().trim();
        String type = selectedMedium.getText();
        if (titleText.isEmpty()) {
            System.out.println("Kein Titel eingegeben.");
            setAktionErfolgreich("Kein Titel eingegeben.");
            throw new IllegalArgumentException("Text is empty");
        }
        boolean removed = libraryManager.removeMedium(titleText, type);
        if (removed) {
            clearFields();
            System.out.println(type + " wurde erfolgreich entfernt.");
            setAktionErfolgreich(type+" wurde erfolgreich entfernt.");
        } else {
            System.out.println(type + " mit diesem Titel wurde nicht gefunden.");
            setAktionErfolgreich(type+" mit diesem Titel wurde nicht gefunden.");
        }
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

        String titleText = title.getText().trim();

        if (titleText.isEmpty()) {
            System.out.println("Kein Titel eingegeben.");
            setAktionErfolgreich("Kein Titel eingegeben.");
            throw new IllegalArgumentException("libraryManager is null");
        }
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
        setAktionErfolgreich(medium+" wurde erfolgreich hinzugefügt.");
    }

    public void borrowMedium(ActionEvent event) throws IOException {
        if (libraryManager == null) {
            System.out.println("libraryManager is null");
            throw new IllegalArgumentException("libraryManager is null");
        }

        String titleText = title.getText().trim();

        if (titleText.isEmpty()) {
            System.out.println("Kein Titel eingegeben.");
            setAktionErfolgreich("Kein Titel eingegeben.");
            throw new IllegalArgumentException("Text is empty");
        }

        for (Medium medium : libraryManager.getMedia()) {
            if (medium.getTitle().equalsIgnoreCase(titleText)) {

                if (medium.isBorrowed()) {
                    System.out.println("Medium ist bereits ausgeliehen.");
                    setAktionErfolgreich("Medium ist bereits ausgeliehen.");
                    return;
                }

                medium.setBorrowed(true);
                medium.setBorCount(medium.getBorCount() + 1);

                System.out.println("Medium wurde ausgeliehen.");
                setAktionErfolgreich("Medium wurde erfolgreich ausgeliehen.");
                clearFields();
                return;
            }
        }

        System.out.println("Medium nicht gefunden.");
        setAktionErfolgreich(selectedMedium+" nicht gefunden.");
    }

    public void returnMedium(ActionEvent event) throws IOException {
        if (libraryManager == null) {
            System.out.println("libraryManager is null");
            throw new IllegalArgumentException("libraryManager is null");
        }

        String titleText = title.getText().trim();

        if (titleText.isEmpty()) {
            System.out.println("Kein Titel eingegeben.");
            setAktionErfolgreich("Kein Titel eingegeben.");
            throw new IllegalArgumentException("Text is empty");
        }

        for (Medium medium : libraryManager.getMedia()) {
            if (medium.getTitle().equalsIgnoreCase(titleText)) {

                if (!medium.isBorrowed()) {
                    System.out.println("Medium ist nicht ausgeliehen.");
                    setAktionErfolgreich("Medium ist nicht ausgeliehen.");
                    return;
                }

                medium.setBorrowed(false);

                System.out.println("Medium wurde zurückgegeben.");
                setAktionErfolgreich("Medium wurde erfolgreich zurückgegeben!");
                clearFields();
                return;
            }
        }

        System.out.println("Medium nicht gefunden.");
        setAktionErfolgreich(selectedMedium+" nicht gefunden.");
    }

    public void setAktionErfolgreich(String text){
        aktionErfolgreich.setText(text);
    }


}
