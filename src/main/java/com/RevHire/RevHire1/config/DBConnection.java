package com.RevHire.RevHire1.config;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class DBConnection {


	private static final String URL =
            "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private static final String USERNAME = "usr_rev";
    private static final String PASSWORD = "usr_rev";

     // utility method
        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
    
}



























//package com.RevHire.RevHire1;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//
//    private static final String URL =
//    		"jdbc:oracle:thin:@localhost:1521:XEPDB1";
//
//    private static final String USER = "SYSTEM";
//    private static final String PASSWORD = "1234567@!";
//
//    // Method to get database connection
//    public static Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}






//package com.RevHire.RevHire1;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

//public class DBConnection {
//	private static final String URL = "jdbc:mysql://localhost:3306/RevHireDB?serverTimezone=UTC";
//  private static final String USER = "root";
//  private static final String PASSWORD = "123456@!";
//
//  public static void main(String[] args) {
//      try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
//          if (connection != null) {
//              System.out.println("Successfully connected to the database!");
//          }
//      } catch (SQLException e) {
//          System.err.println("Connection failed!");
//          e.printStackTrace();
//      }
//  }
//}