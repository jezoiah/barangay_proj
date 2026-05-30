package src;
import java.util.ArrayList;
import java.util.Scanner;

public class ReportManager {
    private DatabaseManager dbManager;

    public ReportManager(DatabaseManager dbManager){
        this.dbManager = dbManager;
    }

    public void fileReport(Scanner sc, String residentID){
        Report report = new Report(0, "");

        System.out.println("\n BLOTTER REPORT");
        
        System.out.println("Nature of Report:");
        System.out.println("1] Noise Complaint");
        System.out.println("2] Dispute");
        System.out.println("3] Verbal Threats/Harassment");
        System.out.println("4] Minor Physical Altercations");
        System.out.println("5] Theft");
        System.out.println("6] Domestic Disputes");
        System.out.println("7] Vehicular Incidents");
        System.out.println("8] Animal-Related Incidents");
        System.out.println("9] Public Disturbance or Vandalism");
        System.out.println("10] Missing Person Report");
        System.out.print("Choose between 1-10: ");
        String choice = sc.nextLine();

        String natureOfReport = "";
        switch(choice){
            case "1":
                natureOfReport = "Noise Complaint";
                break;
            case "2":
                natureOfReport = "Dispute";
                break;
            case "3":
                natureOfReport = "Verbal Threats/Harassment";
                break;
            case "4":
                natureOfReport = "Minor Physical Altercations";
                break;
            case "5":
                natureOfReport = "Theft";
                break;
            case "6":
                natureOfReport = "Domestic Disputes";
                break;
            case "7":
                natureOfReport = "Vehicular Incidents";
                break;
            case "8":
                natureOfReport = "Animal-Related Incidents";
                break;
            case "9":
                natureOfReport = "Public Disturbance or Vandalism";
                break;
            case "10":
                natureOfReport = "Missing Person Report";
                break;
            default:
                System.out.println("Invalid choice.\n");
                return;
        }

        System.out.print("\nIncident Date (YYYY-MM-DD): ");
        String incidentDate = sc.nextLine();
        System.out.print("Incident Time (HH:MM): ");
        String incidentTime = sc.nextLine();
        System.out.print("AM or PM: ");
        String incidentMeridiem = sc.nextLine();

        System.out.println("\nIncident Address:");
        System.out.print("House No. / Lot No.: ");
        String incidentHouseNo = sc.nextLine();
        System.out.print("Street.: ");
        String incidentStreet = sc.nextLine();
        System.out.print("Purok: ");
        String incidentPurok = sc.nextLine();
        System.out.print("Barangay: ");
        String incidentBarangay = sc.nextLine();
        System.out.print("City/Municipality: ");
        String incidentCity = sc.nextLine();

        System.out.print("\nStatement: ");
        String description = sc.nextLine();

        System.out.print("\nRequest Mediation (yes/no)? ");
        boolean mediation = sc.nextLine().equalsIgnoreCase("yes");
        if (mediation) {
            report.setStatus("In-Progress");
        } else {
            report.setStatus("Pending");
        }

        ArrayList<Respondent> respondents = new ArrayList<>();
        boolean addAnother = true;
        while (addAnother) {
            System.out.print("Enter Respondent Last Name: ");
            String respondent = sc.nextLine();

            ArrayList<Resident> matches = dbManager.findResident(respondent);
            String foundResidentID = null;

            Respondent r = new Respondent(0);
            if (matches.isEmpty()){
                System.out.println("No resident found. Storing as unregistered.");
            }else {
                for (int i = 0; i < matches.size(); i++) {
                    Resident res = matches.get(i);
                    System.out.println((i+1) + "] " + res.getFirstName() + " " + res.getLastName());
                }
                System.out.print("Select resident (or 0 to store as unregistered): ");
                int pick;
                try {
                    pick = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Storing as unregistered.");
                    pick = 0;
                }
                if (pick != 0) {
                    foundResidentID = matches.get(pick - 1).getResidentID();
                    r.setRespondent(matches.get(pick - 1).getFirstName() + " " + matches.get(pick - 1).getMiddleName() + " " + matches.get(pick - 1).getLastName());
                    System.out.println("Resident linked!");
                }
            }
            
            if (foundResidentID == null) {
                r.setRespondent(respondent); 
            }
            r.setResidentID(foundResidentID); 
            respondents.add(r); 

            System.out.print("\nAdd another respondent (yes/no)? ");
            boolean addAns = sc.nextLine().equalsIgnoreCase("yes");

            addAnother = addAns;         
        }

        report.setReporterResidentID(residentID);
        report.setNatureofReport(natureOfReport);
        report.setIncidentDate(incidentDate);
        report.setIncidentTime(incidentTime);
        report.setIncidentMeridiem(incidentMeridiem);
        report.setIncidentHouseNo(incidentHouseNo);
        report.setIncidentStreet(incidentStreet);
        report.setIncidentPurok(incidentPurok);
        report.setIncidentBarangay(incidentBarangay);
        report.setIncidentCity(incidentCity);
        report.setDesc(description);
        report.setMed(mediation);

        int blotterID = dbManager.fileBlotterReport(report);
        if (blotterID != -1) {
            for (Respondent r : respondents) {
                r.setBlotterID(blotterID);
                dbManager.addRespondent(r);
                }
            System.out.println("\nBlotter report filed successfully! Blotter ID: " + blotterID);
        } else {
            System.out.println("Failed to file blotter report.");
        }
    }

    public void viewBlotterReports(Scanner sc){        
        while(true){
            ArrayList<Report> reports = dbManager.getBlotterReports();
            System.out.println("\n--- Blotter Reports ---");
            for (Report report : reports){
                System.out.println("Blotter ID: " + report.getBlotterID() + " | Nature of Report: " + report.getNatureofReport() + " | Incident Date: " + report.getIncidentDate() + " | Reporter: " + report.getReporterResidentID() + " | Status: " + report.getStatus());
            }

            System.out.println("\n1. View Specific Blotter Report");
            System.out.println("2. Update Blotter Status");
            System.out.println("3. Exit");
            System.out.print("Choose within options: ");
            String choice = sc.nextLine();

            switch (choice){
                case "1":
                    viewSpecificReport(sc, reports);
                    break;
                case "2":
                    updateBlotterStatus(sc, reports);
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Choose within option only.");
                    return;
            }
        }
    } 

    public void viewSpecificReport(Scanner sc, ArrayList<Report> reports){
        System.out.print("\nEnter Blotter ID: ");
        int blotterInput;
        
        try {
            blotterInput = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid Blotter ID.");
            return;
        }
        Report selectedReport = null;
        for (Report report : reports){
            if (report.getBlotterID() == (blotterInput)){
                selectedReport = report;
                break;
            }
        }

        if (selectedReport != null){
            System.out.println("\n-------------------------------------------------------------------------------------------------");
            
            System.out.println("\nDate: " + selectedReport.getDateFiled());
            System.out.println("Blotter ID: " + selectedReport.getBlotterID());
            System.out.println("Reported by: " + selectedReport.getReporterResidentID());
            ArrayList<Respondent> respondents = dbManager.getRespondents(selectedReport.getBlotterID());
            for (Respondent r : respondents){
                System.out.println("Respondent: " + r.getRespondent());
            }
            System.out.println("\nNature of incident: " + selectedReport.getNatureofReport());

            System.out.println("\nStatement: " + selectedReport.getDesc());
            System.out.println("Date of Incident: " + selectedReport.getIncidentDate());
            System.out.println("Time of Incident: " + selectedReport.getIncidentTime() + selectedReport.getIncidentMeridiem());
            System.out.println("Address of Incident: House/Lot No. " + selectedReport.getIncidentHouseNo() + ", " + selectedReport.getIncidentStreet() + " St., Purok " + selectedReport.getIncidentPurok() + ", Brgy. " + selectedReport.getIncidentBarangay() + ", " + selectedReport.getIncidentCity() + " City/Municipality");
            System.out.println("\n-------------------------------------------------------------------------------------------------");
        } else {
            System.out.println("\nBlotter ID not found.");
        }
    }

    public void updateBlotterStatus(Scanner sc, ArrayList<Report> reports){
        System.out.print("\nEnter Blotter ID: ");
        int blotterInput;

        try {
            blotterInput = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid Blotter ID.");
            return;
        }

        Report selectedReport = null;
        for (Report report : reports){
            if (report.getBlotterID() == (blotterInput)){
                selectedReport = report;
                break;
            }
        }

        if (selectedReport != null){
                System.out.println("Current Status: " + selectedReport.getStatus());
                
                String newStatus = "";
                if (selectedReport.getMed()) {
                    System.out.println("1] Settled");
                    System.out.println("2] Unsettled");
                    System.out.print("Choose a status update: ");
                    String statusChoice = sc.nextLine();
                    switch (statusChoice) {
                        case "1": 
                            newStatus = "Settled"; 
                            break;
                        case "2": 
                            newStatus = "Unsettled"; 
                            break;
                        default: 
                            System.out.println("Invalid choice."); 
                            return;
                    }
                } else {
                    System.out.println("1] Recorded");
                    System.out.println("2] Cancel");
                    System.out.print("Choose a status update: ");
                    String statusChoice = sc.nextLine();
                    switch (statusChoice) {
                        case "1": 
                            newStatus = "Recorded"; 
                            break;
                        case "2": 
                            System.out.println("Cancelled update."); 
                            return;
                        default: 
                            System.out.println("Invalid choice."); 
                            return;
                    }
                }

                if (dbManager.updateBlotterStatus(selectedReport.getBlotterID(), newStatus)) {
                    System.out.println("Status updated successfully!");
                } else {
                    System.out.println("Failed to update status.");
                }
        } else {
            System.out.println("Blotter ID not found.");
        }
    }
}
