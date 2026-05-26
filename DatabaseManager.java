import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/barangaydb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM credentials WHERE username = ? AND password = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean isAdmin(String username, String password) {
        String query = "SELECT * FROM credentials WHERE username = ? AND password = ? AND role = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "admin");
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public ArrayList<Resident> getResidents() {
        ArrayList<Resident> residentList = new ArrayList<>();
        String query = "SELECT * FROM residents";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Resident resident = new Resident();

                    resident.setResidentID(resultSet.getString(2));
                    resident.setLastName(resultSet.getString(3));
                    resident.setFirstName(resultSet.getString(4));
                    resident.setMiddleName(resultSet.getString(5));
                    resident.setSex(resultSet.getString(6));
                    resident.setDOB(resultSet.getDate(7).toLocalDate());
                    resident.setPOB(resultSet.getString(8));
                    resident.setHouseholdID(resultSet.getInt(9));
                    resident.setCivilStatus(resultSet.getString(10));
                    resident.setCitizenship(resultSet.getString(11));
                    resident.setOccupation(resultSet.getString(12));
                    resident.setResidencyStart(resultSet.getDate(13).toLocalDate());

                    residentList.add(resident);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentList;
    }

    public boolean residentExists(String residentID) {
        String query = "SELECT * FROM residents WHERE residentID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                preparedStatement.setString(1, residentID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean residentAccountExists(String residentID) {
        String query = "SELECT * FROM credentials WHERE residentID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                preparedStatement.setString(1, residentID);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addCredentials(String username, String password, String residentID, String role) {
        String query = "INSERT INTO credentials (username, password, residentID, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, residentID);
                preparedStatement.setString(4, role);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
             
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateResidentInfo(Resident resident) {
        String query = "UPDATE residents SET lname = ?, fname = ?, mname = ?, sex = ?, dob = ?, pob = ?, address = ?, civilstatus = ?, citizenship = ?, occupation = ? WHERE residentID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setString(1, resident.getLastName());
            preparedStatement.setString(2, resident.getFirstName());
            preparedStatement.setString(3, resident.getMiddleName());
            preparedStatement.setString(4, resident.getSex());
            preparedStatement.setDate(5, Date.valueOf(resident.getDOB()));
            preparedStatement.setString(6, resident.getPOB());
            preparedStatement.setInt(7, resident.getHouseholdID());
            preparedStatement.setString(8, resident.getCivilStatus());
            preparedStatement.setString(9, resident.getCitizenship());
            preparedStatement.setString(10, resident.getOccupation());
            preparedStatement.setString(11, resident.getResidentID());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int fileBlotterReport(Report report) {
        String query = "INSERT INTO report (reporterResidentID, nReport, iDate, iTime, iMeridiem, iHouseNo, iStreet, iPurok, iBarangay, iCity, description, mediation, status, dtFiled) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, report.getReporterResidentID());
            ps.setString(2, report.getNatureofReport());
            ps.setString(3, report.getIncidentDate());
            ps.setString(4, report.getIncidentTime());
            ps.setString(5, report.getIncidentMeridiem());
            ps.setString(6, report.getIncidentHouseNo());
            ps.setString(7, report.getIncidentStreet());
            ps.setString(8, report.getIncidentPurok());
            ps.setString(9, report.getIncidentBarangay());
            ps.setString(10, report.getIncidentCity());
            ps.setString(11, report.getDesc());
            ps.setBoolean(12, report.getMed());
            ps.setString(13, report.getStatus());

            int rowsAffected = ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1); // return blotterID
            }
            return -1; //failed

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public boolean addRespondent(Respondent respondent){
        String query = "INSERT INTO respondent (blotterID, respondent, residentID) VALUES (?,?,?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, respondent.getBlotterID());
            ps.setString(2, respondent.getRespondent());
            if (respondent.getResidentID() != null) {
                ps.setString(3, respondent.getResidentID());
            } else {
                ps.setNull(3, java.sql.Types.VARCHAR);
            }
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Report> getBlotterReports() {
        ArrayList<Report> bReportList = new ArrayList<>();
        String query = "SELECT * FROM report";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    Report report = new Report(rs.getInt(1), rs.getString(15));

                    report.setReporterResidentID(rs.getString(2));
                    report.setNatureofReport(rs.getString(3));
                    report.setIncidentDate(rs.getString(4));
                    report.setIncidentTime(rs.getString(5));
                    report.setIncidentMeridiem(rs.getString(6));
                    report.setIncidentHouseNo(rs.getString(7));
                    report.setIncidentStreet(rs.getString(8));
                    report.setIncidentPurok(rs.getString(9));
                    report.setIncidentBarangay(rs.getString(10));
                    report.setIncidentCity(rs.getString(11));
                    report.setDesc(rs.getString(12));
                    report.setMed(rs.getBoolean(13));
                    report.setStatus(rs.getString(14));

                    bReportList.add(report);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bReportList;
    }

    public ArrayList<Respondent> getRespondents(int blotterID){
        ArrayList<Respondent> respondentList = new ArrayList<>();
        String query = "SELECT * FROM respondent WHERE blotterID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, blotterID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Respondent respondent = new Respondent(rs.getInt(1));

                    respondent.setRespondent(rs.getString(3));
                    respondent.setResidentID(rs.getString(4));
                    respondentList.add(respondent);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return respondentList;
    }

    public boolean updateBlotterStatus(int blotterID, String status){
        String query = "UPDATE report SET status = ? WHERE blotterID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, status);
            ps.setInt(2, blotterID);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Resident> findResident(String lastName) {
        ArrayList<Resident> residentList = new ArrayList<>();
        String query = "SELECT * FROM residents WHERE lname = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, lastName);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Resident resident = new Resident();
                    resident.setResidentID(rs.getString(2));
                    resident.setLastName(rs.getString(3));
                    resident.setFirstName(rs.getString(4));
                    resident.setMiddleName(rs.getString(5));
                    residentList.add(resident);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return residentList;
    }

    public String getResidentIDByUsername(String username) {
        String query = "SELECT residentID FROM credentials WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("residentID");
                }
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
