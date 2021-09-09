package Controller;

import java.sql.Statement;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.spi.DirStateFactory.Result;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.gluonhq.charm.glisten.control.TextField;
import com.gluonhq.impl.charm.a.b.b.s;

import Model.DatabaseConnection;

public class RegisterController implements Initializable {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField rePasswordTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private ChoiceBox roleChoiceBox;
    @FXML
    private Button registerButton;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Label registerMessage;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        loadImage();

        registerMessage.setAlignment(Pos.CENTER); // can't set in Build scene

        createRoleChoiceBox();

    }

    private void loadImage() {
        File logoFile = new File("src/Resource/IUlogo.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);
    }

    private void createRoleChoiceBox() {
        roleChoiceBox.getItems().add("Staff");
        roleChoiceBox.getItems().add("Professor");
    }

    public void registerButtonOnAction(ActionEvent event) {

        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false
                && rePasswordTextField.getText().isBlank() == false && nameTextField.getText().isBlank() == false
                && roleChoiceBox.getValue().toString().isBlank() == false) {
            if (!passwordTextField.getText().equals(rePasswordTextField.getText())) {
                registerMessage.setText("The password and re-password doesn't match");
            } else {

                if (!checkUsernameInDatabase()) {
                    registerMessage.setText("This username had registered");
                } else {
                    registerMessage.setText("Success register");
                    createUser();
                }

            }

        } else {
            registerMessage.setText("Please input info");
        }

    }

    private Connection connectDatabase() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        return connectDatabase;
    }

    private void createUser() {
        Integer accountID = insertAccount(); // return accountID and add it to the relational table
        Integer staffID = insertStaff();
        insertStaffAccount(accountID, staffID);
    }

    private void insertStaffAccount(Integer accountID, Integer staffID) {
        try {
            PreparedStatement statement = connectDatabase().prepareStatement(
                    "INSERT INTO StaffAccount(accountID,staffID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, accountID.toString());
            statement.setString(2, staffID.toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private int insertStaff() {
        // chua add dateOfBirth
        try {
            PreparedStatement statement = connectDatabase().prepareStatement("INSERT INTO Staff(fullname) VALUES (?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, nameTextField.getText());

            statement.executeUpdate();
            ResultSet accountID = statement.getGeneratedKeys();
            int key = accountID.next() ? accountID.getInt(1) : 0;
            return key;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    private int insertAccount() {

        try {
            PreparedStatement statement = connectDatabase().prepareStatement(
                    "INSERT INTO Account(username,password,role) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, usernameTextField.getText());
            statement.setString(2, passwordTextField.getText());
            statement.setString(3, roleChoiceBox.getValue().toString());

            statement.executeUpdate();
            ResultSet accountID = statement.getGeneratedKeys();
            int key = accountID.next() ? accountID.getInt(1) : 0;
            return key;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    private Boolean checkUsernameInDatabase() {

        String selectQuery = "SELECT username FROM account WHERE username = '" + usernameTextField.getText() + "'";

        try {
            Statement statement = connectDatabase().createStatement();
            ResultSet queryResult = statement.executeQuery(selectQuery);
            if (queryResult.next()) {
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;

    }

}
