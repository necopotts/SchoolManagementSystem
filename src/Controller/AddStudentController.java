package Controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Model.DatabaseConnection;
import Model.StudentModel;
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
        addButton.getScene().getWindow().hide();

    }

    private void addStudent() {

        String studentName = fullnameTextField.getText();
        Date studentDateOfBirth = java.sql.Date.valueOf(dateOfBirthDatePicker.getValue());
        String studentMajor = majorChoiceBox.getValue().toString();
        String studentProgram = programChoiceBox.getValue().toString();

        StudentModel.addData(studentName, studentDateOfBirth, studentMajor, studentProgram);
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

}
