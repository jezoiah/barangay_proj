package src;
public class Respondent{
    private int respondentID;
    private int blotterID;
    private String respondent;
    private String residentID;

    public void setBlotterID(int blotterID){
        this.blotterID = blotterID;
    }

    public void setRespondent(String respondent){
        this.respondent = respondent;
    }

    public void setResidentID(String residentID){
        this.residentID = residentID;
    }

    public Respondent(int respondentID) {
        this.respondentID = respondentID;
    }

    public int getRespondentID(){
        return respondentID;
    }
    
    public int getBlotterID(){
        return blotterID;
    }

    public String getRespondent(){
        return respondent;
    }

    public String getResidentID(){
        return residentID;
    }
}