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



    public static void main(String[] args) {
        try {
            libraryManager.readFile("src/resources/media.csv");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        launch(args);
    }

    public void start(Stage stage) throws Exception {
        /*//xml einbinden
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        //Stage/Scene
        Scene scene = new Scene(root);
        stage.setTitle("Bibliothek");
        stage.setScene(scene);
        stage.show();*/
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
