import java.time.LocalDate;

public class Resident {
    private String residentID;
    private String lastName, firstName, middleName;
    private String sex;
    private LocalDate dob, residencyStart;
    private String pob;
    private int householdID;
    private String civilStatus, citizenship, occupation;

    public void setResidentID(String residentID) {
        this.residentID = residentID;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public void setDOB(LocalDate dob) {
        this.dob = dob;
    }
    public void setResidencyStart(LocalDate residencyStart) {
        this.residencyStart = residencyStart;
    }
    public void setPOB(String pob) {
        this.pob = pob;
    }
    public void setHouseholdID(int householdID) {
        this.householdID = householdID;
    }
    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getResidentID() {
        return residentID;
    }
    public String getLastName() {
        return lastName;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public String getSex() {
        return sex;
    }
    public LocalDate getDOB() {
        return dob;
    }
    public LocalDate getResidencyStart() {
        return residencyStart;
    }
    public String getPOB() {
        return pob;
    }
    public int getHouseholdID() {
        return householdID;
    }
    public String getCivilStatus() {
        return civilStatus;
    }
    public String getCitizenship() {
        return citizenship;
    }
    public String getOccupation() {
        return occupation;
    }

    // add method for calculating years of residency and age

}