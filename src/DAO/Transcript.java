package DAO;

import java.sql.Date;

public class Transcript {
    private Integer transcriptID;
    private Date dateCreated;

    public Transcript(Integer transcriptID, Date dateCreated) {
        this.transcriptID = transcriptID;
        this.dateCreated = dateCreated;
    }

    public Integer getTranscriptID() {
        return transcriptID;
    }

    public void setTranscriptID(Integer transcriptID) {
        this.transcriptID = transcriptID;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

}
