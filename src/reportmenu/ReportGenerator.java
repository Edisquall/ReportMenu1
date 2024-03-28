package reportmenu;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ReportGenerator {

    public void generateTextFile(ResultSet resultSet, String tableName) throws IOException, SQLException {
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

            // Write data rows
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
        }
    }

    public void generateCSVFile(ResultSet resultSet, String tableName) throws IOException, SQLException {
        try ( PrintWriter writer = new PrintWriter(new FileWriter(tableName + "_report.csv"))) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Write header
            for (int i = 1; i <= columnCount; i++) {
                writer.print("\"" + metaData.getColumnName(i) + "\"");
                if (i < columnCount) {
                    writer.print(",");
                }
            }
            writer.println();

            // Write data rows
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.print("\"" + resultSet.getString(i) + "\"");
                    if (i < columnCount) {
                        writer.print(",");
                    }
                }
                writer.println();
            }
            System.out.println("CSV file generated successfully.");
        }
    }

    public void printConsoleOutput(ResultSet resultSet) throws SQLException {
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

        // Write data rows
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(resultSet.getString(i));
                if (i < columnCount) {
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
    }
}
