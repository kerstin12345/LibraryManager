package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import function.LibraryManager;

public class Main extends Application {

    private LibraryManager libraryManager = new LibraryManager();


    public static void main(String[] args) {
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
