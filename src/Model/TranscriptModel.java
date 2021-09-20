package Model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import DAO.Transcript;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TranscriptModel {
    // create list of transcript which is an observableList<DAO> object
    private ObservableList<Transcript> transcriptList = FXCollections.observableArrayList();
    // create objectProperty to get current data
    private final ObjectProperty<Transcript> currentTranscript = new SimpleObjectProperty<>(null);

    public ObjectProperty<Transcript> getCurrentTranscriptProperty() {
        return currentTranscript;
    }

    public final Transcript getCurrentTranscript() {
        return getCurrentTranscriptProperty().get();
    }

    public final void setCurrentTranscript(Transcript transcript) {
        getCurrentTranscriptProperty().set(transcript);
    }

    public ObservableList<Transcript> getTranscriptList() {
        loadData();
        return transcriptList;
    }

    public void loadData() {

        String studentRetrieveDataQuery = "SELECT * FROM Transcript";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection()
                    .prepareStatement(studentRetrieveDataQuery);
            ResultSet transcriptData = preparedStatement.executeQuery();

            while (transcriptData.next()) {
                // Retrieve student in each row and add it to the student list
                Transcript student = new Transcript(transcriptData.getInt("transcriptID"),
                        transcriptData.getDate("createdDate"));
                transcriptList.add(student);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("cannot access to the database");
            e.printStackTrace();
        }
    }

    public void saveData(Integer transcriptID, Date dateCreated) {
        try {
            String updateTranscriptQuery = "UPDATE Tracript SET dateCreated = ? WHERE transcriptID = ?";
            PreparedStatement preparedStatement = DatabaseConnection.getConnection()
                    .prepareStatement(updateTranscriptQuery);
            preparedStatement.setDate(1, dateCreated);
            preparedStatement.setInt(2, transcriptID);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deleteData(Integer transcriptID) {
        String deleteStudentQuery = "DELETE FROM Transcript WHERE transcriptID = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection()
                    .prepareStatement(deleteStudentQuery);
            preparedStatement.setInt(1, transcriptID);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static int addTranscript() {
        String addQuery = "INSERT INTO Transcript(dateCreated) VALUES (?)";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(addQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
            preparedStatement.executeUpdate();
            ResultSet transcriptID = preparedStatement.getGeneratedKeys();
            int key = transcriptID.next() ? transcriptID.getInt(1) : 0;
            return key;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public static Transcript searchTranscript(Integer transcriptID) {
        String searchQuery = "SELECT * FROM Transcript WHERE transcriptID = ?";
        try {
            PreparedStatement preparedStatement = DatabaseConnection.getConnection().prepareStatement(searchQuery);
            preparedStatement.setInt(1, transcriptID);
            ResultSet currentTranscriptData = preparedStatement.executeQuery();
            while (currentTranscriptData.next()) {
                Transcript transcript = new Transcript(currentTranscriptData.getInt("transcriptID"),
                        currentTranscriptData.getDate("dateCreated"));
                return transcript;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
