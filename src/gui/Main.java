package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import function.LibraryManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {
    private static LibraryManager libraryManager = new LibraryManager();


    /**
     * Startet den Manager
     * Liest beim media.csv ein
     * öffnet anschließend die Startoberfläche.
     *
     * @param args Kommandozeilenargumente
     */
    public static void main(String[] args) {
        try {
            //CSV Datei einlesen
            libraryManager.readFile("src/resources/media.csv");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        launch(args);
    }

    /**
     * Erstellt und zeigt das erste Fenster an
     * Die Startoberfläche wird aus der Datei home.fxml geladen
     * Die gemeinsame LibraryManager-Instanz an den HomeController übergeben
     *
     * @param stage Hauptfenster der Anwendung
     * @throws Exception bei Fehlern beim Laden der Oberfläche
     */
    public void start(Stage stage) throws Exception {
        //xml einbinden
        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
        Parent root = loader.load();

        // Controller holen
        HomeController controller = loader.getController();

        // function.LibraryManager übergeben
        controller.setLibraryManager(libraryManager);

        // Stage/Scene
        Scene scene = new Scene(root);
        stage.setTitle("Bibliothek");
        stage.setScene(scene);
        stage.show();

    }
}
