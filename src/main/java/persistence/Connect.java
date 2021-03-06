package persistence;

import java.sql.*;
import java.util.ArrayList;

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
        String url = "jdbc:sqlite:src/main/java/persistence/myDatabase.db";
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
        try (Connection conn = connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, result);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}