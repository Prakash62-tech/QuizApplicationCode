
package javaapplication19;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.event.*;

public class Rules extends JFrame implements ActionListener{

    
    String name;
    JButton start, back;
    Rules(String name){
        this.name = name;
	 setBounds(400, 50, 800, 600);
	 
  
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        
         JLabel heading =new JLabel ("Welcome "  + name + " to Simple Quiz");
        heading.setBounds(200, 40, 700,30);
       heading.setFont(new Font("viner Hand ITC",Font.BOLD,28));
       heading.setForeground(new Color(30, 144, 254));
          add(heading);
          
          
           JLabel rules =new JLabel ();
        rules.setBounds(200, 10, 500,350);
       rules.setFont(new Font("Tahoma",Font.PLAIN,16));
       rules.setText(
       "<html>"+ 
                "1. There are 10 questions from different fields." +"<br><br>" +
                "2. select only one option and click next for the next question." +"<br><br>" +
                "3. if you didnt choose any option during the given time that question will be skipped."+"<br><br>" +
                
            "<html>"
       );
          add(rules);
         
          back =new JButton ("Exit");
          back.setBounds(250, 300, 100, 30);
          back.setBackground(new Color(30, 144, 254));
          back.setForeground(Color.WHITE);
           back.addActionListener(this);
          add(back);
          
          
           start =new JButton ("Start");
          start.setBounds(400, 300, 100, 30);
          start.setBackground(new Color(30, 144, 254));
          start.setForeground(Color.WHITE);
          start.addActionListener(this);
          add(start);
       
       

       
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == start){
            setVisible(false);
            new Quiz(name);
    }
	else if (ae.getSource() == back) {
            setVisible(false);
        }
	else{
            setVisible(false);
            
      }
                }

        public static void main(String[] args){
            new Rules("User");
        }
       }

