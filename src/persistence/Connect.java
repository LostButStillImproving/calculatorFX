package persistence;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author sqlitetutorial.net
 */
public class Connect {

    /*public static void createNewDatabase() {

        String url = "jdbc:sqlite:src/persistence/myDatabase.db" ;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/


    /*public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/persistence/myDatabase.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS results (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	result text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }*/
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:src/persistence/myDatabase.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static ArrayList<String> selectAll(){
        String sql = "SELECT result FROM results";
        ArrayList<String> results = new ArrayList<>();

        try (Connection conn = connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            while (rs.next()) {
                        results.add(rs.getString("result"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    public static void insert(String result) {
        String sql = "INSERT INTO results(result) VALUES(?)";
        System.out.println("here");
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, result);
            System.out.println("here");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) throws SQLException {
        //createNewDatabase();
        //createNewTable();
        //insert("5");
        selectAll().forEach(System.out::println);
    }
}