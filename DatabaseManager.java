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

}
