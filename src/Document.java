package src;
import java.util.ArrayList;

public abstract class Document {
    private int id;
    private String documentType, residentID;
    private String status;
    private ArrayList<String> requirements = new ArrayList<>();

    Document() {
        requirements.add("Application Form");
        requirements.add("Valid Government ID");
        requirements.add("Community Tax Certificate (Cedula)");
    }

    public ArrayList<String> getRequirements() {
        return requirements;
    }
    public String getDocType() {
        return documentType;
    }
    public String getStatus() {
        return status;
    }
    public String getResidentID() {
        return residentID;
    }
    public int getID() {
        return id;
    }
    public void setDocType(String documentType) {
        this.documentType = documentType;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setResidentID(String residentID) {
        this.residentID = residentID;
    }
    public void setID(int id) {
        this.id = id;
    }
    
    public void displayRequirements() {
        System.out.println("\nRequirements for " + getDocType() + ":");
        for (String requirement : requirements) {
            System.out.println("- " + requirement);
        }
    }
    public void displayRequirements(Resident resident) {
        displayRequirements();
    }
}