package src;

public class Report {
    private int blotterID;
    private String reporterResidentID;
    private String natureOfReport;
    private String incidentDate, incidentTime, incidentMeridiem;
    private String incidentHouseNo, incidentStreet, incidentPurok, incidentBarangay, incidentCity;
    private String description;
    private boolean mediation;
    private String status;
    private String dateFiled;

    //Setters
    public void setReporterResidentID(String reporterResidentID){
        this.reporterResidentID = reporterResidentID;
    }

    public void setNatureofReport(String natureOfReport){
        this.natureOfReport = natureOfReport;
    }

    public void setIncidentDate(String incidentDate){
        this.incidentDate = incidentDate;
    }
    
    public void setIncidentTime(String incidentTime){
        this.incidentTime = incidentTime;
    }

    public void setIncidentMeridiem(String incidentMeridiem){
        this.incidentMeridiem = incidentMeridiem;
    }

    public void setIncidentHouseNo(String incidentHouseNo){
        this.incidentHouseNo = incidentHouseNo;
    }

    public void setIncidentStreet(String incidentStreet){
        this.incidentStreet = incidentStreet;
    }

    public void setIncidentPurok(String incidentPurok){
        this.incidentPurok = incidentPurok;
    }

    public void setIncidentBarangay(String incidentBarangay){
        this.incidentBarangay = incidentBarangay;
    }

    public void setIncidentCity(String incidentCity){
        this.incidentCity = incidentCity;
    }

    public void setDesc(String description){
        this.description = description;
    }   
    
    public void setMed(Boolean mediation){
        this.mediation = mediation;
    } 

    public void setStatus(String status){
        this.status = status;
    } 

    //Getters
    public int getBlotterID(){
        return blotterID;
    }
    
    public String getReporterResidentID(){
        return reporterResidentID;
    }

    public String getNatureofReport(){
        return natureOfReport;
    }

    public String getIncidentDate(){
        return incidentDate;
    }

    public String getIncidentTime(){
        return incidentTime;
    }

    public String getIncidentMeridiem(){
        return incidentMeridiem;
    }

    public String getIncidentHouseNo(){
        return incidentHouseNo;
    }

    public String getIncidentStreet(){
        return incidentStreet;
    }

    public String getIncidentPurok(){
        return incidentPurok;
    }

    public String getIncidentBarangay(){
        return incidentBarangay;
    }

    public String getIncidentCity(){
        return incidentCity;
    }

    public String getDesc(){
        return description;
    }   
    
    public Boolean getMed(){
        return mediation;
    } 

    public String getStatus(){
        return status;
    } 

    public String getDateFiled(){
        return dateFiled;
    }
    
    public Report(int blotterID, String dateFiled) {
        this.blotterID = blotterID;
        this.dateFiled = dateFiled;
    }
} 
