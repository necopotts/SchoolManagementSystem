package Model;

import DAO.Student;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        return studentList;
    }

    public ObjectProperty<Student> getCurrentStudent() {
        return currentStudent;
    }

}
