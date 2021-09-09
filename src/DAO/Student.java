package DAO;

import java.util.Date;

public class Student {
    private Integer studentID;
    private String fullname;
    private Date dateOfBirth;
    private String major;
    private String program;

    public Student(Integer studentID, String fullname, Date dateOfBirth, String major, String program) {
        this.studentID = studentID;
        this.fullname = fullname;
        this.dateOfBirth = dateOfBirth;
        this.major = major;
        this.program = program;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

}
