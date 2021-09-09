package Controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Model.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.sql.Connection;

public class AddStudentController implements Initializable {
    @FXML
    private TextField fullnameTextField;
    @FXML
    private DatePicker dateOfBirthDatePicker;
    @FXML
    private ChoiceBox majorChoiceBox;
    @FXML
    private ChoiceBox programChoiceBox;
    @FXML
    private Button addButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label addMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        createMajorChoiceBox();
        createProgramChoiceBox();
        setDurationDate();
    }

    private void setDurationDate() {
        LocalDate minDate = LocalDate.of(1990, 1, 1);
        LocalDate maxDate = LocalDate.now();
        dateOfBirthDatePicker.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate) || item.isBefore(minDate));
            }
        });

    }

    public void addButtonOnAction(ActionEvent event) {
        if (fullnameTextField.getText().isBlank()) {
            addMessage.setText("Please input student full name");
        }
        addStudent();

    }

    private void addStudent() {
        String studentName = fullnameTextField.getText();
        Date studentDateOfBirth = Date.valueOf(dateOfBirthDatePicker.getValue());
        String studentMajor = majorChoiceBox.getValue().toString();
        String studentProgram = programChoiceBox.getValue().toString();
        try {
            PreparedStatement preparedStatement = connectDatabase().prepareStatement(
                    "INSERT INTO Student(fullname,dateOfBirth,major,program) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, studentName);
            preparedStatement.setDate(2, studentDateOfBirth);
            preparedStatement.setString(3, studentMajor);
            preparedStatement.setString(4, studentProgram);
            preparedStatement.executeUpdate();
            addMessage.setText("Student added");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void resetButtonOnAction(ActionEvent event) {
        fullnameTextField.setText("");
        dateOfBirthDatePicker.setValue(null);
        majorChoiceBox.valueProperty().set(null);
        programChoiceBox.valueProperty().set(null);
    }

    private void createMajorChoiceBox() {
        majorChoiceBox.getItems().add("IT");
        majorChoiceBox.getItems().add("EE");
        majorChoiceBox.getItems().add("BA");
        majorChoiceBox.getItems().add("MA");
        majorChoiceBox.getItems().add("FT");
        majorChoiceBox.getItems().add("AR");
        majorChoiceBox.getItems().add("BT");
    }

    private void createProgramChoiceBox() {
        programChoiceBox.getItems().add("IU");
        programChoiceBox.getItems().add("UN");
        programChoiceBox.getItems().add("UWE");
        programChoiceBox.getItems().add("DK");
    }

    public Connection connectDatabase() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        return connectDatabase;
    }

}
