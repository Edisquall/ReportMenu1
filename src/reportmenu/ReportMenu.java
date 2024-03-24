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

/**
 *
 * @author Edi
 */
public class ReportMenu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Reports reports = new Reports();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Options:");
            System.out.println("1. Perform action 1");
            System.out.println("2. Connect to MySQL");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    performAction1(scanner);
                    break;
                case 2:
                    connectToMySQL();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        connection.close();
    }

    private static void performAction1(Scanner scanner) {
        System.out.println("Choose a report:");
        System.out.println("1. Course Report");
        System.out.println("2. Student Report");
        System.out.println("3. Lecturer Report");
        System.out.print("Enter your choice: ");
        int reportChoice = scanner.nextInt();

        switch (reportChoice) {
            case 1:
                generateReport("course", scanner);
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
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

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
            while (resultSet.next()) {
                // Customize this part based on your table structure
                writer.println(resultSet.getString("column_name1") + "\t" + resultSet.getString("column_name2"));
            }
            System.out.println("Text file generated successfully.");
        } catch (IOException | SQLException e) {
            System.out.println("Failed to generate text file: " + e.getMessage());
        }
    }

    private static void generateCSVFile(ResultSet resultSet, String tableName) {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(tableName + "_report.csv"))) {
            // Write header
            for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                writer.print(resultSet.getMetaData().getColumnName(i));
                if (i < resultSet.getMetaData().getColumnCount()) {
                    writer.print(",");
                }
            }
            writer.println();

            // Write data
            while (resultSet.next()) {
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    writer.print(resultSet.getString(i));
                    if (i < resultSet.getMetaData().getColumnCount()) {
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
            while (resultSet.next()) {
                // Customize this part based on your table structure
                System.out.println(resultSet.getString("column_name1") + "\t" + resultSet.getString("column_name2"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to print console output: " + e.getMessage());
        }
    }

    private static void connectToMySQL() {
        try {
            DBConnector.getConnection();
            System.out.println("Connected to MySQL successfully!");
//            connection.close();
        } catch (SQLException e) {
            System.out.println("Failed to connect to MySQL: " + e.getMessage());
        }
    }
}
