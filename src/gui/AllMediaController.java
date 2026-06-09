package gui;

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



    /**
     * Schließt das aktuelle Fenster
     * @param event Actionevent das durch closeButton ausgelöst wurde
     */
    public void closing(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Ändert das fxml File auf home.fxml
     * @param event Actionevent das durch einen Button ausgelöst wird
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
     * Zeigt alle Medien der Liste<function.Medium> im Textfield listedMediunms an
     * @param event Actionevent das durch einen Button ausgelöst wurde
     * @throws IOException
     */
    public void showAllMedia(ActionEvent event) throws IOException {
        //TODO: schöne Ausgabe pro Attribut
/*
        List<String> allLines = ;
        for (String line : allLines) {
            mediaShowed.appendText(line + "\n");
        }*/
    }

}
