import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class BarangayApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        DatabaseManager DB = new DatabaseManager();
        LoginManager LM = new LoginManager(DB);
        ReportManager RM = new ReportManager(DB);
        
        boolean runAdminMenu = false;
        boolean runUserMenu = false;
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n--- Welcome to BarangayONE!\n");
            System.out.println("LOGIN");
            Credentials creds = LM.login(input);

            if (creds != null) {
                boolean isAdmin = DB.isAdmin(creds.getUsername(), creds.getPassword());

                if (isAdmin == true) {
                    runAdminMenu = true;
                } else {
                    runUserMenu = true;
                }
            } else {
                isRunning = false;
            }

            while (runAdminMenu) {
                System.out.println("\n--- MENU ---");
                System.out.println("1] Register an account");
                System.out.println("2] View Residents");
                System.out.println("3] View Document Requests");
                System.out.println("4] View Issuances");
                System.out.println("5] View Reports");
                System.out.println("6] Log out");
                System.out.println("7] Exit");
                System.out.print("Enter your choice [1-7]: ");
                String choice = input.nextLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter resident ID: ");
                        String residentID = input.nextLine();

                        if (DB.residentExists(residentID)) {
                            if (DB.userAccountExists(residentID)) {
                                System.out.println("Resident already has an account.");
                            } else {
                                String accountType;

                                System.out.print("Enter username: ");
                                String username = input.nextLine();
                                System.out.print("Enter password: ");
                                String password = input.nextLine();

                                while (true) {
                                    System.out.print("Enter account type (1 - Admin, 2 - User): ");
                                    String role = input.nextLine();

                                    if (role.equals("1")) {
                                        accountType = "admin";
                                        break;
                                    } else if (role.equals("2")) {
                                        accountType = "user";
                                        break;
                                    } else {
                                        System.out.println("Invalid choice. Please enter 1 or 2.");
                                    }
                                }

                                if (DB.addCredentials(username, password, residentID, accountType)) {
                                    System.out.println("Resident account registered successfully.");
                                } else {
                                    System.out.println("Failed to register account.");
                                }
                            }
                        } else {
                            System.out.println("Resident does not exist. Cannot create account.");
                        }

                        break;
                        
                    case "2":
                        ArrayList<Resident> residents = DB.getResidents();

                        while (true) {
                            System.out.println("\n--- Barangay Residents ---");
                            for (Resident resident : residents) {
                                System.out.println("ID: " + resident.getResidentID() + " | Name: " + resident.getLastName() + ", " + resident.getFirstName() + " " + resident.getMiddleName());
                            }

                            System.out.print("\nEnter Resident ID to view details or type 'back' to exit: ");
                            String userInput = input.nextLine();

                            if (userInput.equalsIgnoreCase("back")) {
                                break;
                            } else {
                                Resident selectedResident = null;
                                for (Resident resident : residents) {
                                    if (resident.getResidentID().equals(userInput)) {
                                        selectedResident = resident;
                                        break;
                                    }
                                }

                                if (selectedResident != null) {

                                    selectedResident.displayResidentInfo();
                                    boolean runResMenu = true;

                                    while (runResMenu) {
                                        System.out.println("\nActions:");
                                        System.out.println("1] Update Record");
                                        System.out.println("2] Back");
                                        System.out.print("Enter choice [1/2]: ");
                                        String option = input.nextLine();

                                        switch (option) {
                                            case "1":
                                                System.out.println("\nUPDATING BARANGAY INHABITANT RECORD");
                                                System.out.println("Enter new information or leave blank to keep previous.");

                                                System.out.print("Last Name: ");
                                                String lastName = input.nextLine();
                                                if (!lastName.isEmpty()) {
                                                    selectedResident.setLastName(lastName);
                                                }
                                                System.out.print("First Name: ");
                                                String firstName = input.nextLine();
                                                if (!firstName.isEmpty()) {
                                                    selectedResident.setFirstName(firstName);
                                                }
                                                System.out.print("Middle Name: ");
                                                String middleName = input.nextLine();
                                                if (!middleName.isEmpty()) {
                                                    selectedResident.setMiddleName(middleName);
                                                }
                                                System.out.print("Sex: ");
                                                String sex = input.nextLine();
                                                if (!sex.isEmpty()) {
                                                    selectedResident.setSex(sex);
                                                }
                                                System.out.print("Birthdate [YYYY-MM-DD]: ");
                                                String dob = input.nextLine();
                                                if (!dob.isEmpty()) {
                                                    selectedResident.setDOB(LocalDate.parse(dob));
                                                }
                                                System.out.print("Place of Birth: ");
                                                String pob = input.nextLine();
                                                if (!pob.isEmpty()) {
                                                    selectedResident.setPOB(pob);
                                                }
                                                System.out.print("Household ID: ");
                                                String houseID = input.nextLine();
                                                if (!houseID.isEmpty()) {
                                                    selectedResident.setHouseholdID(Integer.parseInt(houseID));
                                                }
                                                System.out.print("Civil Status: ");
                                                String civilStatus = input.nextLine();
                                                if (!civilStatus.isEmpty()) {
                                                    selectedResident.setCivilStatus(civilStatus);
                                                }
                                                System.out.print("Citizenship: ");
                                                String citizenship = input.nextLine();
                                                if (!citizenship.isEmpty()) {
                                                    selectedResident.setCitizenship(citizenship);
                                                }
                                                System.out.print("Occupation: ");
                                                String occupation = input.nextLine();
                                                if (!occupation.isEmpty()) {
                                                    selectedResident.setOccupation(occupation);
                                                }

                                                System.out.println();

                                                if (DB.updateResidentInfo(selectedResident)) {
                                                    System.out.println("Record updated successfully.");
                                                } else {
                                                    System.out.println("Failed to update record.");
                                                }

                                                break;

                                            case "2":
                                                runResMenu = false;
                                                break;

                                            default:
                                                System.out.println("Invalid input. Please enter 1 or 2 only.");
                                        }
                                    }

                                } else {
                                    System.out.println("Resident ID does not exist.");
                                    break;
                                }
                            }

                        }
                        break;

                    case "3":
                        ArrayList<Document> documents = DB.getDocuments("documents");
                        System.out.println("\n--- Document Requests ---");
                        if (!documents.isEmpty()) {
                            for (Document document : documents) {
                                System.out.println("ID: " + document.getID() + " | " + document.getResidentID() + " | " + document.getDocType() + " | Status: " + document.getStatus());
                            }
                            System.out.print("Enter Document ID: ");
                            int docID = input.nextInt();
                            input.nextLine();

                            int count = 0;
                            for (Document document : documents) {
                                if (document.getID() == docID) {
                                    System.out.println("\nID: " + document.getID() + " | " + document.getResidentID() + " | " + document.getDocType() + " | Status: " + document.getStatus());
                                    count++;
                                    break;
                                }
                            }
                            if (count == 0) {
                                System.out.println("Document with ID '" + docID + "' does not exist.");
                            } else {
                                System.out.println("Actions:");
                                System.out.println("1] Approve");
                                System.out.println("2] Mark as Ready for Pickup");
                                System.out.println("3] Reject");
                                System.out.print("Enter your choice [1-3]: ");
                                String statusChoice = input.nextLine();

                                switch (statusChoice) {
                                    case "1":
                                        if (DB.changeStatus("Approved", docID)) {
                                            System.out.println("Request approved.");
                                        } else {
                                            System.out.println("Failed to approve request.");
                                        }
                                        break;

                                    case "2":
                                        for (Document document : documents) {
                                            if (document.getID() == docID) {
                                                if (!document.getStatus().equalsIgnoreCase("Rejected")) {
                                                    if (DB.deleteRequest(docID)) {
                                                        if (DB.addIssuance(document.getResidentID(), document.getDocType(), "Ready")) {
                                                            System.out.println("Request ready for pickup.");
                                                        } else {
                                                            System.out.println("Failed to update request.");
                                                        }
                                                    } else {
                                                        System.out.println("Failed to update request.");
                                                    }
                                                } else {
                                                    System.out.println("Cannot mark a rejected request as ready for pickup.");
                                                }
                                                break;
                                            }
                                        }
                                        break;

                                    case "3":
                                        if (DB.changeStatus("Rejected", docID)) {
                                            System.out.println("Request rejected.");
                                        } else {
                                            System.out.println("Failed to reject request.");
                                        }
                                        break;

                                    default:
                                        System.out.println("Invalid input. Please enter a number from 1 to 3.");
                                }
                            }

                        } else {
                            System.out.println("No requests found.");
                        }
                        break;

                    case "4":
                        ArrayList<Document> issuances = DB.getDocuments("issuances");
                        System.out.println("\n--- Issuances ---");
                        if (!issuances.isEmpty()) {
                            for (Document issuance : issuances) {
                                System.out.println("ID: " + issuance.getID() + " | " + issuance.getResidentID() + " | " + issuance.getDocType() + " | Status: " + issuance.getStatus());
                            }
                        } else {
                            System.out.println("No issuances found.");
                        }
                        break;

                    case "5":
                        RM.viewBlotterReports(input);
                        break;

                    case "6":
                        System.out.println("Logging out...");
                        runAdminMenu = false;
                        break;

                    case "7":
                        System.out.println("\nExiting... Goodbye!");
                        runAdminMenu = false;
                        isRunning = false;
                        break;

                    default:
                        System.out.println("Invalid input. Please enter a number from 1 to 6.");
                }
            }

            while (runUserMenu) {
                String residentID = DB.getResidentID(creds.getUsername(), creds.getPassword());
                Resident resident = DB.getResident(residentID);

                System.out.println("\nHello, " + resident.getFirstName() + "!");
                System.out.println("\n--- MENU ---");
                System.out.println("1] Request a document");
                System.out.println("2] File a report");
                System.out.println("3] View Document Requests");
                System.out.println("4] Log out");
                System.out.println("5] Exit");
                System.out.print("Enter your choice [1-5]: ");
                String choice = input.nextLine();

                switch (choice) {
                    case "1":
                        boolean runDocMenu = true;
                        while (runDocMenu) {
                            System.out.println("\nDocuments:");
                            System.out.println("1] Barangay Clearance");
                            System.out.println("2] Barangay ID");
                            System.out.println("3] Certificate of Residency");
                            System.out.println("4] Certificate of Indigency");
                            System.out.println("5] Barangay Business Permit");
                            System.out.println("6] Barangay Building Clearance");
                            System.out.println("7] Back");
                            System.out.print("Enter your choice [1-7]: ");
                            String docChoice = input.nextLine();

                            switch (docChoice) {
                                case "1":
                                    Document barangayClearance = new BarangayCLR();
                                    barangayClearance.displayRequirements();

                                    while (true) {
                                        System.out.print("\nSubmit? [Y/N]: ");
                                        String submitChoice = input.nextLine();
                                        if (submitChoice.equalsIgnoreCase("Y")) {
                                            if (DB.addDocument(residentID, barangayClearance.getDocType(), "Pending")) {
                                                System.out.println("Request successful.");
                                            } else {
                                                System.out.println("Failed to request document.");
                                            }
                                            break;
                                        } else if (submitChoice.equalsIgnoreCase("N")) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input. Please enter Y or N.");
                                        }
                                    }
                                    break;

                                case "2":
                                    Document barangayID = new BarangayID();
                                    barangayID.displayRequirements();

                                    while (true) {
                                        System.out.print("\nSubmit? [Y/N]: ");
                                        String submitChoice = input.nextLine();
                                        if (submitChoice.equalsIgnoreCase("Y")) {
                                            if (DB.addDocument(residentID, barangayID.getDocType(), "Pending")) {
                                                System.out.println("Request successful.");
                                            } else {
                                                System.out.println("Failed to request document.");
                                            }
                                            break;
                                        } else if (submitChoice.equalsIgnoreCase("N")) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input. Please enter Y or N.");
                                        }
                                    }
                                    break;

                                case "3":
                                    Document cor = new BarangayCOR();
                                    cor.displayRequirements(resident);

                                    while (true) {
                                        System.out.print("\nSubmit? [Y/N]: ");
                                        String submitChoice = input.nextLine();
                                        if (submitChoice.equalsIgnoreCase("Y")) {
                                            if (DB.addDocument(residentID, cor.getDocType(), "Pending")) {
                                                System.out.println("Request successful.");
                                            } else {
                                                System.out.println("Failed to request document.");
                                            }
                                            break;
                                        } else if (submitChoice.equalsIgnoreCase("N")) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input. Please enter Y or N.");
                                        }
                                    }
                                    break;

                                case "4":
                                    Document coi = new BarangayCOI();
                                    coi.displayRequirements();

                                    while (true) {
                                        System.out.print("\nSubmit? [Y/N]: ");
                                        String submitChoice = input.nextLine();
                                        if (submitChoice.equalsIgnoreCase("Y")) {
                                            if (DB.addDocument(residentID, coi.getDocType(), "Pending")) {
                                                System.out.println("Request successful.");
                                            } else {
                                                System.out.println("Failed to request document.");
                                            }
                                            break;
                                        } else if (submitChoice.equalsIgnoreCase("N")) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input. Please enter Y or N.");
                                        }
                                    }
                                    break;

                                case "5":
                                    Document businessPermit = new BarangayBNP();
                                    businessPermit.displayRequirements();

                                    while (true) {
                                        System.out.print("\nSubmit? [Y/N]: ");
                                        String submitChoice = input.nextLine();
                                        if (submitChoice.equalsIgnoreCase("Y")) {
                                            if (DB.addDocument(residentID, businessPermit.getDocType(), "Pending")) {
                                                System.out.println("Request successful.");
                                            } else {
                                                System.out.println("Failed to request document.");
                                            }
                                            break;
                                        } else if (submitChoice.equalsIgnoreCase("N")) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input. Please enter Y or N.");
                                        }
                                    }
                                    break;

                                case "6":
                                    Document buildingClearance = new BarangayBDC();
                                    buildingClearance.displayRequirements();

                                    while (true) {
                                        System.out.print("\nSubmit? [Y/N]: ");
                                        String submitChoice = input.nextLine();
                                        if (submitChoice.equalsIgnoreCase("Y")) {
                                            if (DB.addDocument(residentID, buildingClearance.getDocType(), "Pending")) {
                                                System.out.println("Request successful.");
                                            } else {
                                                System.out.println("Failed to request document.");
                                            }
                                            break;
                                        } else if (submitChoice.equalsIgnoreCase("N")) {
                                            break;

                                        } else {
                                            System.out.println("Invalid input. Please enter Y or N.");
                                        }
                                    }
                                    break;

                                case "7":
                                    runDocMenu = false;
                                    break;

                                default:
                                    System.out.println("Invalid input. Please enter a number from 1 to 7.");
                            }
                        }
                        break;

                    case "2":
                        RM.fileReport(input, residentID);
                        break;

                    case "3":
                        System.out.println("\n--- Document Requests ---");

                        ArrayList<Document> readyDocuments = DB.getDocuments("issuances", residentID, "Ready");
                        ArrayList<Document> approvedDocuments = DB.getDocuments("documents", residentID, "Approved");
                        ArrayList<Document> pendingDocuments = DB.getDocuments("documents", residentID, "Pending");
                        ArrayList<Document> rejectedDocuments = DB.getDocuments("documents", residentID, "Rejected");

                        if (readyDocuments.isEmpty() && approvedDocuments.isEmpty() && pendingDocuments.isEmpty() && rejectedDocuments.isEmpty()) {
                            System.out.println("You haven't made any document requests yet.");
                        } else {
                            if (!readyDocuments.isEmpty()) {
                                System.out.println("\n[ READY FOR PICKUP ]");
                                for (Document document : readyDocuments) {
                                    System.out.println("ID: " + document.getID() + " | " + document.getDocType() + " | Status: " + document.getStatus());
                                }
                            }
                            if (!approvedDocuments.isEmpty()) {
                                System.out.println("\n[ APPROVED ]");
                                for (Document document : approvedDocuments) {
                                    System.out.println("ID: " + document.getID() + " | " + document.getDocType() + " | Status: " + document.getStatus());
                                }
                            }
                            if (!pendingDocuments.isEmpty()) {
                                System.out.println("\n[ PENDING ]");
                                for (Document document : pendingDocuments) {
                                    System.out.println("ID: " + document.getID() + " | " + document.getDocType() + " | Status: " + document.getStatus());
                                }
                            }
                            if (!rejectedDocuments.isEmpty()) {
                                System.out.println("\n[ REJECTED ]");
                                for (Document document : rejectedDocuments) {
                                    System.out.println("ID: " + document.getID() + " | " + document.getDocType() + " | Status: " + document.getStatus());
                                }
                            }
                        }
                        break;

                    case "4":
                        System.out.println("Logging out...");
                        runUserMenu = false;
                        break;

                    case "5":
                        System.out.println("\nExiting... Goodbye!");
                        runUserMenu = false;
                        isRunning = false;
                        break;

                    default:
                        System.out.println("Invalid input. Please enter a number from 1 to 4.");
                }
            }
        }
        input.close();
    }
}