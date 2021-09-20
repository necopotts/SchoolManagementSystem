package DAO;

import java.util.HashMap;
import java.util.List;

public class TranscriptSubject {
    private Transcript transcript;
    private HashMap<Subject, Integer> listOfSubjectResult;

    public TranscriptSubject(Transcript transcript, HashMap<Subject, Integer> listOfSubjectResult) {
        this.transcript = transcript;
        this.listOfSubjectResult = listOfSubjectResult;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void setTranscript(Transcript transcript) {
        this.transcript = transcript;
    }

    public HashMap<Subject, Integer> getSubject() {
        return listOfSubjectResult;
    }

    public void setSubject(HashMap<Subject, Integer> listOfSubjectResult) {
        this.listOfSubjectResult = listOfSubjectResult;
    }

}
