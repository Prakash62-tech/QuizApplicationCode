package javaapplication19;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    
    JButton login, signup;
    JTextField tfname;
    JPasswordField tfpassword;
    JCheckBox check;
    
    Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
	setBounds(100, 10, 1367, 768);
        
	ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon1/login_1.jpeg"));
        JLabel image = new JLabel(i1);
        image.setBounds(200,40, 400,450);
        add(image);
	
        JLabel heading = new JLabel("Simple Quiz");
        heading.setBounds(750, 60, 300, 45);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 40));
        heading.setForeground(new Color(30, 144, 254));
        add(heading);
	
        
        JLabel name = new JLabel("User Name");
        name.setBounds(820, 150, 300, 20);
        name.setFont(new Font("", Font.BOLD, 18));
        name.setForeground(new Color(30, 144, 254));
        add(name);
        
        JLabel password = new JLabel("Password");
        password.setBounds(820, 240, 300, 20);
        password.setFont(new Font("", Font.BOLD, 18));
        password.setForeground(new Color(30, 144, 254));
        add(password);
        
        tfname = new JTextField();
        tfname.setBounds(735, 200, 300, 25);
        tfname.setFont(new Font("", Font.BOLD, 15));
        add(tfname);
        
        tfpassword = new JPasswordField();
        tfpassword.setBounds(735, 270, 300, 25);
        tfpassword.setFont(new Font("", Font.BOLD, 15));
        add(tfpassword);
        
        check = new JCheckBox("");
        check.setBounds(800, 300, 20, 20);
        add(check);
        
        JLabel label = new JLabel("Show Password");
        label.setBounds(820, 300, 500, 20);
        label.setFont(new Font("", Font.BOLD, 15));
        label.setForeground(new Color(30, 144, 254));
        add(label);
        
        check.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check.isSelected()) {
                    tfpassword.setEchoChar((char) 0);
                } else {
                    tfpassword.setEchoChar('*');
                }
            }
        });
        
        login = new JButton("Login");
        login.setBounds(735, 340, 120, 25);
        login.setBackground(new Color(30, 144, 254));
        login.setForeground(Color.WHITE);
        login.addActionListener(this);
        add(login);
        
        signup = new JButton("SignUp");
        signup.setBounds(915, 340, 120, 25);
        signup.setBackground(new Color(30, 144, 254));
        signup.setForeground(Color.WHITE);
        signup.addActionListener(this);
        add(signup);
        

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == login) {
            String username = tfname.getText();
            String password = new String(tfpassword.getPassword());

            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(this, "✅ Login Successful!");
                new Rules(username); 
                this.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(this, "❌ Invalid Username or Password. Try Again!");
            }
        } else if (ae.getSource() == signup) {
            new SignUp(); 
            this.setVisible(false);
        }
    }

    
    
    private boolean validateLogin(String username, String password) {
        Connection conn = null;
        try {
            String url = "jdbc:mysql://localhost:3306/quizdb"; 
            String user = "root"; // MySQL Username
            String pass = ""; // MySQL Password

            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, pass);

            String query = "SELECT * FROM users WHERE name = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next(); // 

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new Login();
    }
}
