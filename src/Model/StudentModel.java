package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.Student;
import DAO.Transcript;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentModel {

    private final ObservableList<Student> studentList = FXCollections.observableArrayList();
    private final ObjectProperty<Student> currentStudent = new SimpleObjectProperty<>(null); // This class provides a
                                                                                             // full implementation of a
                                                                                             // Property wrapping an
                                                                                             // arbitrary Object

    public ObjectProperty<Student> currentStudentProperty() {
        return currentStudent;
    }

    public final Student getCurrentPerson() {
        return currentStudentProperty().get();
    }

    public final void setCurrentPerson(Student student) {
        currentStudentProperty().set(student);
    }

    public ObservableList<Student> getStudentList() {
        loadData();
        return studentList;
    }

    public ObjectProperty<Student> getCurrentStudent() {
        return currentStudent;
    }

    public void loadData() {
        String studentRetrieveDataQuery = "SELECT * FROM Student";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection()
                    .prepareStatement(studentRetrieveDataQuery);
            ResultSet studentData = preparedStatement.executeQuery();

            while (studentData.next()) {
                // Retrieve student in each row and add it to the student list
                Student student = new Student(studentData.getInt("studentID"), studentData.getString("fullname"),
                        studentData.getDate("dateOfBirth"), studentData.getString("major"),
                        studentData.getString("program"), studentData.getInt("transcriptID"));
                studentList.add(student);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("cannot access to the database");
            e.printStackTrace();
        }
    }

    public static void saveData(Integer studentID, String studentName, Date studentDateOfBirth, String studentMajor,
            String studentProgram) {
        try {
            String updateStudentQuery = "UPDATE Student SET fullname = ?, dateOfBirth = ?, major = ?, program = ? WHERE studentID = ?";
            PreparedStatement preparedStatement = DatabaseConnection.getConnection()
                    .prepareStatement(updateStudentQuery);
            preparedStatement.setString(1, studentName);
            preparedStatement.setDate(2, studentDateOfBirth);
            preparedStatement.setString(3, studentMajor);
            preparedStatement.setString(4, studentProgram);
            preparedStatement.setInt(5, studentID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void deleteData(Integer studentID) {
        String deleteStudentQuery = "DELETE FROM Student WHERE studentID = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection()
                    .prepareStatement(deleteStudentQuery);
            preparedStatement.setInt(1, studentID);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ObservableList<Student> searchData(String searchKeyword) {
        String searchQuery;
        try {
            PreparedStatement preparedStatement;
            if (isNumeric(searchKeyword)) {
                searchQuery = "SELECT * FROM Student WHERE studentID = ?";
                preparedStatement = DatabaseConnection.getConnection().prepareStatement(searchQuery);
                preparedStatement.setInt(1, Integer.valueOf(searchKeyword));
            } else {
                searchQuery = "SELECT * FROM Student WHERE fullname LIKE ? OR major LIKE ? OR program LIKE ?";
                preparedStatement = DatabaseConnection.getConnection().prepareStatement(searchQuery);
                preparedStatement.setString(1, "%" + searchKeyword + "%");
                preparedStatement.setString(2, "%" + searchKeyword + "%");
                preparedStatement.setString(3, "%" + searchKeyword + "%");
            }

            ResultSet searchResult = preparedStatement.executeQuery();
            while (searchResult.next()) {
                // Retrieve student in each row and add it to the student list
                Student student = new Student(searchResult.getInt("studentID"), searchResult.getString("fullname"),
                        searchResult.getDate("dateOfBirth"), searchResult.getString("major"),
                        searchResult.getString("program"), searchResult.getInt("transcriptID"));
                studentList.add(student);
                return studentList;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return studentList;

    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?"); // match a number with optional '-' and decimal.
    }

    public static void addData(String studentName, Date studentDateOfBirth, String studentMajor,
            String studentProgram) {

        try {
            int transcriptID = TranscriptModel.addTranscript();
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(
                    "INSERT INTO Student(fullname,dateOfBirth,major,program,transcriptID) VALUES (?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, studentName);
            preparedStatement.setDate(2, studentDateOfBirth);
            preparedStatement.setString(3, studentMajor);
            preparedStatement.setString(4, studentProgram);
            preparedStatement.setInt(5, transcriptID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void getStudentTranscript(Integer transcriptID) {
        Transcript transcript = TranscriptModel.searchTranscript(transcriptID);

    }

}
