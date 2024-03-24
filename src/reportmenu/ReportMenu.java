/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reportmenu;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Edi
 */
public class ReportMenu {

    // Define user roles and their credentials
    private static Map<String, String> userCredentials = new HashMap<>();
    private static Map<String, String> userRoles = new HashMap<>();

    static {
        // Initially, only 'admin' exists with 'admin' role and password 'java'
        userCredentials.put("admin", "java");
        userRoles.put("admin", "Admin");
    }

    private static void initializeUserCredentials() {
        // Add more initial users and roles if needed
        userCredentials.put("office", "password");
        userRoles.put("office", "Office");
        userCredentials.put("lecturer", "password");
        userRoles.put("lecturer", "Lecturer");
    }

    private static String currentUser;

    private static void setCurrentUser(String username) {
        currentUser = username;
    }

    private static String getCurrentUser() {
        return currentUser;
    }

    private static void userLogin(Scanner scanner) {
        initializeUserCredentials();

        // Prompt for username and password
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        // Validate credentials
        if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
            setCurrentUser(username);
            System.out.println("Login successful. Welcome, " + username + "!");
            displayUserMenu(scanner); // After successful login, display the user menu
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void displayUserMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            String role = userRoles.get(getCurrentUser());

            // Display menu based on user role
            switch (role) {
                case "Admin":
                    // Admin menu
                    System.out.println("Admin Menu:");
                    System.out.println("1. Add User");
                    System.out.println("2. Modify User");
                    System.out.println("3. Delete User");
                    System.out.println("4. Change Password");
                    System.out.println("5. Change Username");
                    System.out.println("6. Logout");
                    break;
                case "Office":
                    // Office menu
                    System.out.println("Office Menu:");
                    System.out.println("1. Generate Course Report");
                    System.out.println("2. Generate Student Report");
                    System.out.println("3. Generate Lecturer Report");
                    System.out.println("4. Change Password");
                    System.out.println("5. Change Username");
                    System.out.println("6. Logout");
                    break;
                case "Lecturer":
                    // Lecturer menu
                    System.out.println("Lecturer Menu:");
                    System.out.println("1. Generate Lecturer Report");
                    System.out.println("2. Change Password");
                    System.out.println("3. Change Username");
                    System.out.println("4. Logout");
                    break;
            }

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (role) {
                case "Admin":
                    // Handle Admin menu options
                    switch (choice) {
                        case 1:
                            // Add User
                            // Implement add user functionality
                            break;
                        case 2:
                            // Modify User
                            // Implement modify user functionality
                            break;
                        case 3:
                            // Delete User
                            // Implement delete user functionality
                            break;
                        case 4:
                            // Change Password
                            // Implement change password functionality
                            break;
                        case 5:
                            // Change Username
                            // Implement change username functionality
                            break;
                        case 6:
                            exit = true; // Logout
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                    break;
                case "Office":
                    // Handle Office menu options
                    switch (choice) {
                        case 1:
                            // Generate Course Report
                            // Implement generate course report functionality
                            break;
                        case 2:
                            // Generate Student Report
                            // Implement generate student report functionality
                            break;
                        case 3:
                            // Generate Lecturer Report
                            // Implement generate lecturer report functionality
                            break;
                        case 4:
                            // Change Password
                            // Implement change password functionality
                            break;
                        case 5:
                            // Change Username
                            // Implement change username functionality
                            break;
                        case 6:
                            exit = true; // Logout
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                    break;
                case "Lecturer":
                    // Handle Lecturer menu options
                    switch (choice) {
                        case 1:
                            // Generate Lecturer Report
                            // Implement generate lecturer report functionality
                            break;
                        case 2:
                            // Change Password
                            // Implement change password functionality
                            break;
                        case 3:
                            // Change Username
                            // Implement change username functionality
                            break;
                        case 4:
                            exit = true; // Logout
                            break;
                        default:
                            System.out.println("Invalid choice. Please enter a valid option.");
                    }
                    break;
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection connection = null; // Declare connection variable
        Scanner scanner = new Scanner(System.in);

        try {
            boolean exit = false;
            while (!exit) {
                System.out.println("Options:");
                System.out.println("1. Generate Reports");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        generateReports(scanner);
                        break;
                    case 2:
                        userLogin(scanner); // Call user login function
                        exit = true; // Exit the loop after successful login
                        break;
                    case 3:
                        System.out.println("Exiting...");
                        exit = true; // Exit the loop when user chooses to exit
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            }
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection: " + e.getMessage());
                }
            }
            scanner.close();
        }
    }

    private static void generateReports(Scanner scanner) {
        System.out.println("Choose a report:");
        System.out.println("1. Course Report");
        System.out.println("2. Student Report");
        System.out.println("3. Lecturer Report");
        System.out.print("Enter your choice: ");
        int reportChoice = scanner.nextInt();

        switch (reportChoice) {
            case 1:
                generateReport("courses", scanner);
                break;
            case 2:
                generateReport("student", scanner);
                break;
            case 3:
                generateReport("lecturer", scanner);
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    private static void generateReport(String tableName, Scanner scanner) {
        System.out.println("Choose the output format:");
        System.out.println("1. Text File (.txt)");
        System.out.println("2. CSV File (.csv)");
        System.out.println("3. Console Output");
        System.out.print("Enter your choice: ");
        int outputChoice = scanner.nextInt();

        try {
            Connection connection = DBConnector.getConnection(); // Open connection
            Statement statement = connection.createStatement(); // Create stream for the data
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName); // Create SQL Query

            switch (outputChoice) {
                case 1:
                    generateTextFile(resultSet, tableName);
                    break;
                case 2:
                    generateCSVFile(resultSet, tableName);
                    break;
                case 3:
                    printConsoleOutput(resultSet);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to generate report: " + e.getMessage());
        }
    }

    private static void generateTextFile(ResultSet resultSet, String tableName) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(tableName + "_report.txt"))) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Write header
            for (int i = 1; i <= columnCount; i++) {
                writer.print(metaData.getColumnName(i));
                if (i < columnCount) {
                    writer.print("\t");
                }
            }
            writer.println();

            // Write data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.print(resultSet.getString(i));
                    if (i < columnCount) {
                        writer.print("\t");
                    }
                }
                writer.println();
            }
            System.out.println("Text file generated successfully.");
        } catch (IOException | SQLException e) {
            System.out.println("Failed to generate text file: " + e.getMessage());
        }
    }

    private static void generateCSVFile(ResultSet resultSet, String tableName) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(tableName + "_report.csv"))) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Write header
            for (int i = 1; i <= columnCount; i++) {
                writer.print(metaData.getColumnName(i));
                if (i < columnCount) {
                    writer.print(",");
                }
            }
            writer.println();

            // Write data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.print(resultSet.getString(i));
                    if (i < columnCount) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
            System.out.println("CSV file generated successfully.");
        } catch (IOException | SQLException e) {
            System.out.println("Failed to generate CSV file: " + e.getMessage());
        }
    }

    private static void printConsoleOutput(ResultSet resultSet) {
        try {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Write header
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(metaData.getColumnName(i));
                if (i < columnCount) {
                    System.out.print("\t");
                }
            }
            System.out.println();

            // Write data
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(resultSet.getString(i));
                    if (i < columnCount) {
                        System.out.print("\t");
                    }
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.out.println("Failed to print console output: " + e.getMessage());
        }
    }

    private static Connection connectToMySQL() {
        try {
            return DBConnector.getConnection();
        } catch (SQLException e) {
            System.out.println("Failed to connect to MySQL: " + e.getMessage());
            return null;
        }
    }
}
