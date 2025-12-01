package javaapplication19;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
public class Score extends JFrame implements ActionListener {
    
    private String name;
    private int score;
    private int heighestscore;
    
    private String[][] answers = {
        {"Q1", "JDB"},
        {"Q2", "int"},
        {"Q3", "java.util package"},
        {"Q4", "Marker Interface"},
        {"Q5", "Heap memory"},
        {"Q6", "Remote interface"},
        {"Q7", "import"},
        {"Q8", "Java Archive"},
        {"Q9", "java.lang.StringBuilder"},
        {"Q10", "Bytecode is executed by JVM"}
    };

    // Constructor
    Score(String name, int score) {
        this.name = name;
        this.score = score;
this.heighestscore = fetchHighestScore(name);
        setBounds(100, 10, 1367, 768);
        getContentPane().setBackground(Color.white); 
        setLayout(null);

        // Left Side Image
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icon1/score.png"));
        Image i2 = i1.getImage().getScaledInstance(300, 250, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        image.setBounds(0, 200, 300, 250);
        add(image);

        // Heading
        JLabel heading = new JLabel("Thank you " + name + " for playing Simple Quiz");
        heading.setBounds(45, 30, 700, 30);
        heading.setFont(new Font("Tahoma", Font.PLAIN, 26));
        heading.setForeground(new Color(70, 130, 180)); 
        add(heading);

        // Score Display
        JLabel lblscore = new JLabel("Your score is: " + score);
        lblscore.setBounds(350, 200, 300, 30);
        lblscore.setFont(new Font("Tahoma", Font.PLAIN, 26));
        lblscore.setForeground(new Color(34, 139, 34)); 
        add(lblscore);

        // Right-side Profile Section
        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(null);
        profilePanel.setBounds(1050, 50, 250, 450);
        profilePanel.setBackground(new Color(240, 240, 240));
        add(profilePanel);

        // Profile Picture
        ImageIcon profileIcon = new ImageIcon(ClassLoader.getSystemResource("icon1/profile.png"));
        Image profileImg = profileIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon resizedProfileIcon = new ImageIcon(profileImg);
        JLabel profilePic = new JLabel(resizedProfileIcon);
        profilePic.setBounds(75, 20, 100, 100);
        profilePanel.add(profilePic);

        // User Name
        JLabel userName = new JLabel(""+name);
        userName.setBounds(50, 130, 150, 30);
        userName.setFont(new Font("Arial", Font.BOLD, 18));
        userName.setForeground(Color.BLACK);
        userName.setHorizontalAlignment(SwingConstants.CENTER);
        profilePanel.add(userName);

        // User Score
        JLabel userScore = new JLabel(" Score: " +heighestscore);
        userScore.setBounds(50, 170, 150, 30);
        userScore.setFont(new Font("Arial", Font.BOLD, 16));
        userScore.setForeground(new Color(34, 139, 34));
        userScore.setHorizontalAlignment(SwingConstants.CENTER);
        profilePanel.add(userScore);

        // Logout Button (Inside Profile Panel)
        JButton logout = new JButton("Logout");
        logout.setBounds(75, 250, 100, 30);
        logout.setBackground(Color.RED);
        logout.setForeground(Color.WHITE);
        logout.setFont(new Font("Arial", Font.BOLD, 14));
        logout.setFocusPainted(false);
        logout.setBorder(BorderFactory.createLineBorder(Color.RED));
        logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    dispose();
                    new Login(); 
		    
		                    }
		
            }
	  
	    
        });
        profilePanel.add(logout);

        // "See Answers" Button
        JButton ansButton = new JButton("See Answers");
        ansButton.setBounds(350, 270, 140, 40);
        ansButton.setBackground(new Color(30, 144, 255));
        ansButton.setForeground(Color.WHITE);
        ansButton.setFont(new Font("Arial", Font.BOLD, 16));
        ansButton.setFocusPainted(false);
        ansButton.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255)));
        ansButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAnswers();
            }
        });
        add(ansButton);

        // Play Again Button
        JButton playAgain = new JButton("Play Again");
        playAgain.setBounds(550, 270, 120, 40);
        playAgain.setBackground(new Color(30, 144, 255));
        playAgain.setForeground(Color.WHITE);
        playAgain.setFont(new Font("Arial", Font.BOLD, 16));
        playAgain.addActionListener(this);
        playAgain.setFocusPainted(false);
        playAgain.setBorder(BorderFactory.createLineBorder(new Color(30, 144, 255)));
        add(playAgain);

        setVisible(true);
        setSize(1367, 768);
    }

    private int fetchHighestScore(String name) {
    int userScore=0;

    try {
        // Database connection
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizdb", "root", "");

        // SQL query to fetch the score for the given username
        String query = "SELECT userscore FROM score WHERE username = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, name);
        
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            userScore = rs.getInt("userscore");
        }

        // Close connections
        rs.close();
        stmt.close();
        con.close();

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return userScore;
}
    
    
    
    
    
    // ActionListener for the play again button
    public void actionPerformed(ActionEvent ae) {
        score = 0; 
        setVisible(false);
        new Quiz(name); 
	
    }

    // Method to show correct answers in a popup
    private void showAnswers() {
        StringBuilder answerText = new StringBuilder("<html><body>");
        for (String[] ans : answers) {
            answerText.append(ans[0]).append(": <b>").append(ans[1]).append("</b><br>");
        }
        answerText.append("</body></html>");

        JOptionPane.showMessageDialog(this, new JLabel(answerText.toString()), "Correct Answers", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new Score("User", 50); 
    }
}
