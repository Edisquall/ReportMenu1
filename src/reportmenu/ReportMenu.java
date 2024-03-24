/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reportmenu;

import java.sql.SQLException;

/**
 *
 * @author Edi
 */
public class ReportMenu {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);  
        try {
            DBConnector.getConnection();
        } catch (SQLException e) {
            System.out.println("Failed to connect to MySQL: " + e.getMessage());
        }
    }
}
