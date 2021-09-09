package Controller;

import java.awt.event.MouseEvent;
import java.beans.EventHandler;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import DAO.Student;
import Model.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditStudentController implements Initializable {
    @FXML
    private TextField fullnameTextField;
    @FXML
    private DatePicker dateOfBirthDatePicker;
    @FXML
    private ChoiceBox majorChoiceBox;
    @FXML
    private ChoiceBox programChoiceBox;
    @FXML
    private Button saveButton;
    @FXML
    private Button resetButton;
    @FXML
    private Label saveMessage;

    private Student currentStudent;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        createMajorChoiceBox();
        createProgramChoiceBox();
        setDurationDate();

    }

    @FXML
    private void saveButtonOnAction(ActionEvent event) {
        if (fullnameTextField.getText().isBlank()) {
            saveMessage.setText("Student's full name shouldn't be empty");

        } else {
            saveData();
            saveButton.getScene().getWindow().hide();
        }

    }

    private void saveData() {
        try {
            String updateStudentQuery = "UPDATE Student SET fullname = ?, dateOfBirth = ?, major = ?, program = ? WHERE studentID = ?";
            PreparedStatement preparedStatement = connectDatabase().prepareStatement(updateStudentQuery);
            preparedStatement.setString(1, fullnameTextField.getText());
            preparedStatement.setDate(2, Date.valueOf(dateOfBirthDatePicker.getValue()));
            preparedStatement.setString(3, majorChoiceBox.getValue().toString());
            preparedStatement.setString(4, programChoiceBox.getValue().toString());
            preparedStatement.setInt(5, currentStudent.getStudentID());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    private void resetButtonOnAction(ActionEvent event) {
        displayStudentInformation();
    }

    public void initData(Student selectedStudent) {
        currentStudent = selectedStudent;
        displayStudentInformation();

    }

    public void displayStudentInformation() {
        LocalDate dateOfBirth = currentStudent.getDateOfBirth().toLocalDate();
        fullnameTextField.setText(currentStudent.getFullname());
        dateOfBirthDatePicker.setValue(dateOfBirth);
        majorChoiceBox.valueProperty().set(currentStudent.getMajor());
        programChoiceBox.valueProperty().set(currentStudent.getProgram());
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
