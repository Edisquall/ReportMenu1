package reportmenu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ReportMenu {

    private static UserManager userManager = new UserManager();
    private static ReportGenerator reportGenerator = new ReportGenerator();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                userLogin(scanner);
            }
        } finally {
            scanner.close();
        }
    }

    private static void userLogin(Scanner scanner) {
        System.out.println("To login, please enter your details:");
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (userManager.login(username, password)) {
            System.out.println("Login successful. Welcome, " + username + "!");
            displayUserMenu(scanner);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private static void displayUserMenu(Scanner scanner) {
        String role = userManager.getCurrentUserRole();

        switch (role) {
            case "Admin":
                adminMenu(scanner);
                break;
            case "Office":
                officeMenu(scanner);
                break;
            case "Lecturer":
                lecturerMenu(scanner);
                break;
            default:
                System.out.println("Invalid role.");
                userManager.logout();
                break;
        }
    }

    private static void generateReport(Scanner scanner) {
        /*
         * TODO: UPDATE THIS FUNCTION SO THAT IT CHECK USERS ROLES AND ONLY ALLOW SPECIFIC REPORTS PER ROLE
         * ADMIN CANT DO REPORTS
         * OFFICE CAN GENERATE ALL
         * LECTURER CAN ONLY DO LECTURES
         */
        
        System.out.println("Select the type of report to generate:");
        System.out.println("1. Course Report");
        System.out.println("2. Student Report");
        System.out.println("3. Lecturer Report");
        System.out.print("Enter your choice: ");
        int reportChoice = scanner.nextInt();

        String tableName;
        switch (reportChoice) {
            case 1:
                tableName = "courses";
                break;
            case 2:
                tableName = "students";
                break;
            case 3:
                tableName = "lecturer";
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
                return;
        }

        System.out.println("Choose the output format:");
        System.out.println("1. Text File (.txt)");
        System.out.println("2. CSV File (.csv)");
        System.out.println("3. Console Output");
        System.out.print("Enter your choice: ");
        int outputChoice = scanner.nextInt();

        try {
            Connection connection = DBConnector.getConnection(); // Open connection
            Statement statement = connection.createStatement(); // Create stream for the data
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName); // Execute query

            switch (outputChoice) {
                case 1:
                    reportGenerator.generateTextFile(resultSet, tableName);
                    break;
                case 2:
                    reportGenerator.generateCSVFile(resultSet, tableName);
                    break;
                case 3:
                    reportGenerator.printConsoleOutput(resultSet);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException | IOException e) {
            System.out.println("Failed to generate report: " + e.getMessage());
        }
    }

    private static void adminMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Admin Menu:");
            System.out.println("1. Add User");
            System.out.println("2. Delete User");
            System.out.println("3. Modify User Role");
            System.out.println("4. Logout");
            System.out.println("5. See all the users");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    addUser(scanner);
                    break;
                case 2:
                    deleteUser(scanner);
                    break;
                case 3:
                    modifyUserRole(scanner);
                    break;
                case 4:
                    userManager.logout();
                    return;
                case 5:
                    seeAllUsers();
                    break;
                    // TODO: ADD THE OPTION TO UPDATE OWN USERNAME AND PASSWORD
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    break;
            }
        }
    }

    private static void officeMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("Office Menu:");
            System.out.println("1. Generate Reports");
            System.out.println("2. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    generateReport(scanner);
                    break;
                case 2:
                    userManager.logout();
                    exit = true;
                    break;
                    // TODO: add option to edit own username and password
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void lecturerMenu(Scanner scanner) {
        System.out.println("Lecturer Menu:");
        System.out.println("1. Generate Lecturer Report");
        System.out.println("2. Logout");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        switch (choice) {
            case 1:
                System.out.println("Generating lecturer report...");
                // TODO: Implement report generation logic here
                break;
            case 2:
                userManager.logout();
                break;
                // TODO: add option to edit own username and password
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void addUser(Scanner scanner) {
        System.out.print("Enter new user's name: ");
        String username = scanner.next();
        System.out.print("Enter new user's password: ");
        String password = scanner.next();
        System.out.print("Enter new user's role: ");
        String role = scanner.next();
        userManager.addUser(username, password, role);
        System.out.println("User added successfully.");
    }

    private static void deleteUser(Scanner scanner) {
        System.out.print("Enter the username of the user to delete: ");
        String username = scanner.next();
        if (userManager.deleteUser(username)) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    private static void modifyUserRole(Scanner scanner) {
        System.out.print("Enter the username of the user to modify the role: ");
        String username = scanner.next();
        System.out.print("Enter new user role: ");
        String newRole = scanner.next();
        if (userManager.modifyUserRole(username, newRole)) {
            System.out.println("User role modified successfully.");
        } else {
            System.out.println("Username not found.");
        }
    }

    private static void seeAllUsers() {
        String[][] users = userManager.getUsers();
        for (String[] user : users) {
            System.out.println("Username: " + user[0] + ", Role: " + user[2]);
        }
    }
}
