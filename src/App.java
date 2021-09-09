import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Int a root load the fxml file
        Parent root = FXMLLoader.load(getClass().getResource("/View/HomePageStage.fxml"));
        // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("IU management application");
        // String css =
        // this.getClass().getResource("/css/HomePageStyle.css").toExternalForm();
        // primaryStage.getScene().getStylesheets().add(css);

        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}