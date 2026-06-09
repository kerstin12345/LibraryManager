package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import function.LibraryManager;

import java.io.IOException;

public class HomeController {
    @FXML
    private Button closeButton;

    private LibraryManager libraryManager;

    public void setLibraryManager(LibraryManager manager) {
        this.libraryManager = manager;
    }

    /**
     * Schließt das aktuelle Fenster
     * @param event Actionevent das durch das Klicken von closeButton ausgelöst wird
     */
    public void closing(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Wechselt auf das fxml File options.fxml beim Drücken von einem der function.Medium Buttons
     * @param event Actionevent das durch einen der Medienbuttons ausgelöst wird
     * @throws IOException wirft eine IOException bei Fehlern
     */
    public void mediumPressed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("options.fxml"));
        Parent root = loader.load();

        Button button = (Button) event.getSource();
        String buttonText = button.getText();

        //Text des Labels wird zum gedrückten function.Medium geändert
        OptionController controller = loader.getController();
        controller.setLabeltext(buttonText);
        controller.setLibraryManager(libraryManager);

        Stage stage = (Stage) button.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void showAllMedia(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("allMedia.fxml"));

        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
