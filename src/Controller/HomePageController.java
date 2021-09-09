package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class HomePageController implements Initializable {
    @FXML
    private AnchorPane holderPane;
    @FXML
    private SplitPane splitPane;
    @FXML
    private Button studentPaneButton;
    @FXML
    private Button teacherPaneButton;
    @FXML
    private Button informationPaneButton;
    @FXML
    private Button settingPaneButton;

    private AnchorPane home;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub

    }

    public void studentPaneButtonOnAction(ActionEvent event) {

        createPage("/View/StudentPane.fxml");
    }

    public void teacherPaneButtonOnAction(ActionEvent event) {

        createPage("/View/TeacherPane.fxml");

    }

    public void informationPaneButtonOnAction(ActionEvent event) {

    }

    public void settingPaneButtonOnAction(ActionEvent event) {

    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();

    }

    private void createPage(String URL) {

        try {
            home = FXMLLoader.load(getClass().getResource(URL));
            setNode(home);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
