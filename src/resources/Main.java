package resources;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        //xml einbinden
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));

        //Stage/Scene
        Scene scene = new Scene(root);
        stage.setTitle("Bibliothek");
        stage.setScene(scene);
        stage.show();

    }
}
