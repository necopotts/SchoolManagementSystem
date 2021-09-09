package Controller;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import Model.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class LoginController implements Initializable {
    @FXML
    private Button cancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView logoImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

        // load logo image
        File logoFile = new File("src/Resource/IUlogo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

    }

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) {

        // check if username and password is empty
        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
            validateLogin();
        } else {
            if (usernameTextField.getText().isBlank() == true) {
                loginMessageLabel.setText("Please input username");
            } else {
                loginMessageLabel.setText("Please input password");
            }
        }

    }

    private void validateLogin() {
        // connect DTB
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDatabase = connectNow.getConnection();

        // select table inside dtb
        String verifyLoginQuery = "SELECT TOP (1) * FROM account where username = '" + usernameTextField.getText()
                + "' AND password = '" + passwordTextField.getText() + "'";

        try {
            Statement statement = connectDatabase.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLoginQuery);
            if (queryResult.next()) {
                loginMessageLabel.setText("Login success");
            } else {
                loginMessageLabel.setText("Login failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();

        }

    }

}
