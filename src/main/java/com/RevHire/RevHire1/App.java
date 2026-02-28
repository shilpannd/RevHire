package com.RevHire.RevHire1;
import java.sql.Connection;
import com.RevHire.RevHire1.config.DBConnection;
import com.RevHire.RevHire1.config.DatabaseInitializer;

public class App {
    public static void main(String[] args) {
        System.out.println("Application started...");
        
        // 1. Initialize Tables First
        DatabaseInitializer.runScriptOnce();

        // 2. Verify Database Connection BEFORE starting the UI
        try (Connection connection = DBConnection.getConnection()) {
            if (connection != null) {
                System.out.println("✅ Successfully connected to the database!");
                
                // 3. NOW start the main menu
                Main gatekeeper = new Main();
                gatekeeper.start(); 
            }
        } catch (Exception e) {
            System.out.println("❌ Database connection failed! Check your DB credentials.");
            e.printStackTrace();
        }
    }
}



//package com.RevHire.RevHire1;
//
///**
//* Hello world!
//*/
//public class App {
//  public static void main(String[] args) {
//      System.out.println("Hello World!");
//  }
//}
