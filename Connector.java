/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication19;
   import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    public static void main(String[] args) {
        // Connect to the database
        Connection conn = connect();

        if (conn != null) {
            System.out.println("Database connection established successfully!");
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            // Database URL, username, and password
            String url = "jdbc:mysql://localhost:3306/quizdb"; // Replace 'quizdb' with your database name
            String user = "root"; // Your MySQL username
            String password = ""; // Your MySQL password

            // Establish connection
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error: Unable to connect to the database.");
            e.printStackTrace();
        }
        return conn;
    }
}

