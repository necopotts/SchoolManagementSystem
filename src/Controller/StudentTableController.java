package Controller;

import java.io.IOException;
import java.net.URL;
import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;

import DAO.Student;
import Model.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.converter.LocalDateStringConverter;

public class StudentTableController implements Initializable {

    @FXML
    private TableView<Student> studentTable;
    @FXML
    private TableColumn<Student, String> idColumn;
    @FXML
    private TableColumn<Student, String> fullnameColumn;
    @FXML
    private TableColumn<Student, String> dateOfBirthColumn;
    @FXML
    private TableColumn<Student, String> majorColumn;
    @FXML
    private TableColumn<Student, String> programColumn;
    @FXML
    private Button addStudentButton;
    @FXML
    private Button editStudentButton;
    @FXML
    private Button exportButton;
    @FXML
    private Text studentIDText;
    @FXML
    private Text fullnameText;
    @FXML
    private Text dateOfBirthText;
    @FXML
    private Text majorText;
    @FXML
    private Text programText;

    private Student currentStudent;
    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void studentClicked(MouseEvent event) {
        currentStudent = studentTable.getSelectionModel().getSelectedItem();
        studentIDText.setText(currentStudent.getStudentID().toString());
        fullnameText.setText(currentStudent.getFullname());
        dateOfBirthText.setText(currentStudent.getDateOfBirth().toString());
        majorText.setText(currentStudent.getMajor());
        programText.setText(currentStudent.getProgram());
        editStudentButton.setDisable(false);

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        editStudentButton.setDisable(true);
        loadData();
        // editStudentButton.setVisible(false);
        // studentList.addListener(new ListChangeListener<Student>() {

        // @Override
        // public void onChanged(Change<? extends Student> arg0) {
        // // TODO Auto-generated method stub
        // loadData();
        // }

        // });

    }

    public void openScene(String URL) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource(URL));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnHidden(evt -> {
                studentTable.getItems().clear();
                loadData();
            });
            stage.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addStudentButtonOnAction(ActionEvent event) {
        openScene("/View/AddStudentStage.fxml");

    }

    public void editStudentButtonOnAction(ActionEvent event) {

        // FXMLLoader loader = new FXMLLoader();
        // loader.setLocation(getClass().getResource("/View/EditStudentStage.fxml"));
        // try {
        // Node node = (Node) event.getSource();
        // Stage stage = (Stage) node.getScene().getWindow();
        // Parent studentTableParent = loader.load();
        // Scene editStudentScene = new Scene(studentTableParent);

        // // access to edit student controller
        // EditStudentController controller = loader.getController();
        // controller.initData(currentStudent);
        // loader.setController(controller);

        // stage.setScene(editStudentScene);
        // stage.show();

        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        try {

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EditStudentStage.fxml"));
            Parent root = loader.load();
            EditStudentController editStudentController = (EditStudentController) loader.getController();
            editStudentController.initData(currentStudent);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.setOnHidden(evt -> {
                studentTable.getItems().clear();
                loadData();
            });
            stage.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void exportButtonOnAction() {

    }

    private void loadData() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        fullnameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        dateOfBirthColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        programColumn.setCellValueFactory(new PropertyValueFactory<>("program"));

        String studentRetrieveDataQuery = "SELECT * FROM Student";
        try {
            PreparedStatement preparedStatement = connectDatabase().prepareStatement(studentRetrieveDataQuery);
            ResultSet studentData = preparedStatement.executeQuery();

            while (studentData.next()) {
                // Retrieve student in each row and add it to the student list
                Student student = new Student(studentData.getInt("studentID"), studentData.getString("fullname"),
                        studentData.getDate("dateOfBirth"), studentData.getString("major"),
                        studentData.getString("program"));
                studentList.add(student);
                // Set it to each row of the table
                studentTable.setItems(studentList);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("cannot access to the database");
            e.printStackTrace();
        }

    }

    public Connection connectDatabase() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDatabase = connectNow.getConnection();
        return connectDatabase;
    }

}
