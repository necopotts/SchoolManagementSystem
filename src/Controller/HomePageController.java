package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
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

        Control control = (Control) event.getSource();
        changeClickedButtonColor(control.getId());
        // System.out.println(control.getId() + " clicked");
    }

    public void teacherPaneButtonOnAction(ActionEvent event) {

        createPage("/View/TeacherPane.fxml");

        Control control = (Control) event.getSource();
        changeClickedButtonColor(control.getId());
        // System.out.println(control.getId() + " clicked");

    }

    public void informationPaneButtonOnAction(ActionEvent event) {
        Control control = (Control) event.getSource();
        changeClickedButtonColor(control.getId());
        // System.out.println(control.getId() + " clicked");

    }

    public void settingPaneButtonOnAction(ActionEvent event) {
        Control control = (Control) event.getSource();
        changeClickedButtonColor(control.getId());
        // System.out.println(control.getId() + " clicked");

    }

    public void changeClickedButtonColor(String clickedButtonString) {
        HashMap<String, Button> buttons = new HashMap<String, Button>();
        buttons.put("studentPaneButton", studentPaneButton);
        buttons.put("teacherPaneButton", teacherPaneButton);
        buttons.put("informationPaneButton", informationPaneButton);
        buttons.put("settingPaneButton", settingPaneButton);

        buttons.get(clickedButtonString).setStyle("-fx-background-color: #081439");
        buttons.remove(clickedButtonString);
        for (Button button : buttons.values()) {
            button.setStyle("-fx-background-color: #112b5f");

        }

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
