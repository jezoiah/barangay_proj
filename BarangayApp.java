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
                System.out.println("\n --- MENU ---");
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
                            if (DB.residentAccountExists(residentID)) {
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
                                        System.out.println("\n Actions:");
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
                        break;

                    case "4":
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
                System.out.println("\n --- MENU ---");
                System.out.println("1] Request a document");
                System.out.println("2] File a report");
                System.out.println("3] View Document Requests");
                System.out.println("4] Log out");
                System.out.println("5] Exit");
                System.out.print("Enter your choice [1-5]: ");
                String choice = input.nextLine();

                switch (choice) {
                    case "1":
                        break;

                    case "2":
                        String residentID = DB.getResidentIDByUsername(creds.getUsername());
                        RM.fileReport(input, residentID);
                        break;

                    case "3":
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
