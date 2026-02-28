package com.RevHire.RevHire1;

import java.util.Scanner;
import com.RevHire.RevHire1.models.Users;
import com.RevHire.RevHire1.service.UsersService;

public class Main {
    private UsersService userService = new UsersService();
    private view consoleView = new view(); 
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new Main().start();
    }

    public void start() {
        while (true) {
            System.out.println("\n=== Welcome to RevHire ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password"); // Added Option
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1: handleRegistration(); break;
                    case 2: handleLogin(); break;
                    case 3: handleForgotPassword(); break; // New Case
                    case 4: System.out.println("Goodbye!"); System.exit(0);
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("❌ Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private void handleForgotPassword() {
        System.out.println("\n--- Forgot Password ---");
        System.out.print("Enter your registered Email: ");
        String email = scanner.nextLine();

        // 1. Fetch user by email to get their security question
        Users user = userService.getUserByEmail(email);

        if (user != null) {
            // 2. Display the question stored in DB
            System.out.println("Security Question: " + user.getSecurityQuestion());
            System.out.print("Your Answer: ");
            String answer = scanner.nextLine();

            // 3. Verify answer
            if (answer.equalsIgnoreCase(user.getSecurityAnswer())) {
                System.out.print("Enter New Password: ");
                String newPass = scanner.nextLine();
                
                boolean updated = userService.resetPassword(email, newPass);
                if (updated) {
                    System.out.println("✅ Password reset successful! You can now login.");
                } else {
                    System.out.println("❌ Failed to update password. Try again.");
                }
            } else {
                System.out.println("❌ Incorrect answer to security question.");
            }
        } else {
            System.out.println("❌ No user found with that email.");
        }
    }

    private void handleRegistration() {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String pass = scanner.nextLine();
        System.out.println("Select Role: 1. Job Seeker 2. Employer");
        int roleChoice = scanner.nextInt();
        scanner.nextLine();
        String role = (roleChoice == 1) ? "JOB_SEEKER" : "EMPLOYER";
        System.out.print("Security Question: ");
        String sq = scanner.nextLine();
        System.out.print("Answer: ");
        String sa = scanner.nextLine();

        System.out.println(userService.registerUser(email, pass, role, sq, sa));
    }

    private void handleLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String pass = scanner.nextLine();

        Users user = userService.login(email, pass);
        
        if (user != null) {
            System.out.println("\n✅ Login Successful!");
            consoleView.displayLoginMenuWithUser(user);
        } else {
            System.out.println("❌ Invalid email or password.");
        }
    }
}