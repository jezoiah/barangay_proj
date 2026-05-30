package src;
import java.time.LocalDate;
import java.time.Period;

public class Resident {
    private String residentID;
    private String lastName, firstName, middleName;
    private String sex;
    private LocalDate dob, residencyStart;
    private String pob;
    private int householdID;
    private String civilStatus, citizenship, occupation;
    private String address;

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
    public void setAddress(String address) {
        this.address = address;
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
    public String getAddress() {
        return address;
    }

    public int calculateAge() {
        int age = java.time.Period.between(getDOB(), java.time.LocalDate.now()).getYears();
        return age;
    }

    public void displayResidentInfo() {
        System.out.println("-------------------------------------------------------------------------------------------------");

        System.out.println("  Resident ID: " + getResidentID());
        System.out.println("");

        System.out.printf("  %-30s %-35s %-25s%n", "Last Name: " + getLastName(), "First Name: " + getFirstName(), "Middle Name: " + getMiddleName());
        System.out.printf("  %-30s %-35s %-25s%n", "Sex: " + getSex(), "Age: " + calculateAge(), "Date of Birth: " + getDOB());
        System.out.printf("  %-30s %-35s %-25s%n", "Civil Status: " + getCivilStatus(), "Citizenship: " + getCitizenship(), "Occupation: " + getOccupation());

        System.out.println("");

        System.out.println("  Place of Birth: " + getPOB());
        System.out.println("  Current Address: " + getAddress());
        System.out.println("  Start of Residency: " + getResidencyStart());

        System.out.println("-------------------------------------------------------------------------------------------------");
    }

    public int calculateResidencyDuration() {
        LocalDate start = getResidencyStart();
        LocalDate current = LocalDate.now();

        Period period = Period.between(start, current);
        int totalDuration = (int) period.toTotalMonths();

        return totalDuration;
    }
}