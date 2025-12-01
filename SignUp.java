/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication19;

/**
 *
 * @author A S P I R E 7
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SignUp extends JFrame implements ActionListener {
    private JLabel nameLabel, emailLabel, passwordLabel, confirmPasswordLabel;
    private JTextField tfname, emailTextField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton signupButton, cancelButton;
    public static String username;

    SignUp() {
        setTitle("Signup Form");
        setBounds(400, 50, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        nameLabel = new JLabel("Username:");
        nameLabel.setBounds(200, 50, 100, 30);
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(nameLabel);

        tfname = new JTextField();
        tfname.setBounds(300, 50, 300, 30);
        add(tfname);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(230, 100, 100, 30);
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(emailLabel);

        emailTextField = new JTextField();
        emailTextField.setBounds(300, 100, 300, 30);
        add(emailTextField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 150, 100, 30);
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 150, 300, 30);
        add(passwordField);

        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(145, 200, 150, 30);
        confirmPasswordLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
        add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(300, 200, 300, 30);
        add(confirmPasswordField);

        signupButton = new JButton("Signup");
        signupButton.setBounds(300, 270, 120, 30);
        signupButton.setBackground(new Color(30, 144, 255));
        signupButton.setForeground(Color.WHITE);
        signupButton.addActionListener(this);
        add(signupButton);

        cancelButton = new JButton("Back");
        cancelButton.setBounds(480, 270, 120, 30);
        cancelButton.setBackground(Color.GRAY);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        add(cancelButton);

        setVisible(true);
	
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == signupButton) {
            String username = tfname.getText();
            String email = emailTextField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Validate inputs
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.");
                return;
            }

            // Store in database
            if (registerUser(username, email, password)) {
                JOptionPane.showMessageDialog(this, "Signup Successful!");
                setVisible(false);
                new Rules(username); // Open login form
		
            } else {
                JOptionPane.showMessageDialog(this, "Signup Failed. Try again.");
            }
        } else if (ae.getSource() == cancelButton) {
	   
            new Login(); 
            
        }
    }

    // Database Connection and User Registration
    private boolean registerUser(String username, String email, String password) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizdb", "root", "")) {
            // Check if email already exists
            PreparedStatement checkUser = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            checkUser.setString(1, email);
            ResultSet rs = checkUser.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Email already registered!");
                return false;
            }

            // Insert user into database
            String query = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setString(3, password); 
	    
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
        return false;
    }

    public static void main(String[] args) {
        new SignUp();
	
    }
}
