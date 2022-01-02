import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.Timer;


public class Password extends JFrame implements ActionListener{
	
	JButton submit,exit,back;
	JPasswordField input;
	JDialog error;
	
	String parola = "123";
	boolean confirmareParola;
	int nrIncercari=3;
	
	Password(){
		
		input = new JPasswordField();
		submit = new JButton("Submit ");
		exit = new JButton("Exit");
		back = new JButton("Inapoi");
		
		submit.setFocusable(false);
		submit.setFont(new Font("Verdana",Font.BOLD, 13));
		submit.setBorder(BorderFactory.createEtchedBorder());
		submit.setBackground(new Color(50,205,50));
		submit.setForeground(Color.BLACK);
		submit.addActionListener(this);
		submit.setCursor(new Cursor(HAND_CURSOR));
		
		
		exit.setFocusable(false);
		exit.setFont(new Font("Verdana",Font.BOLD, 13));
		exit.setBorder(BorderFactory.createEtchedBorder());
		exit.setBackground(Color.RED);
		exit.setForeground(Color.BLACK);
		exit.addActionListener(this);
		exit.setPreferredSize(new Dimension(70,25));
		exit.setCursor(new Cursor(HAND_CURSOR));

		back.setFocusable(false);
		back.setFont(new Font("Verdana",Font.BOLD, 13));
		back.setBorder(BorderFactory.createEtchedBorder());
		back.setBackground(Color.WHITE);
		back.setForeground(Color.BLACK);
		back.addActionListener(this);
		back.setPreferredSize(new Dimension(70,25));
		back.setCursor(new Cursor(HAND_CURSOR));
		
		input.setPreferredSize(new Dimension(250,30));
		input.setFont(new Font("Verdana",Font.PLAIN, 16));
		//input.setEchoChar('*');
		
		ImageIcon image = new ImageIcon("Parola.png");
		this.setIconImage(image.getImage());
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Autentificare");		
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setBackground(Color.black);
		this.add(submit);
		this.add(input);
		this.add(back);
		this.getRootPane().setDefaultButton(submit);
		input.requestFocusInWindow();
		this.pack();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==exit) {
			error.dispose();
		}
		
		if(e.getSource()==back) {
			MyFrame frame = new MyFrame();
		    frame.panelMain.setVisible(true);
	       	frame.panelClient.setVisible(false);
	       	frame.setLocationRelativeTo(null);
	       	frame.panelAdmin.setVisible(false);
	       	frame.add(frame.panelMain,BorderLayout.CENTER);
	       	frame.revalidate();
	       	frame.validate();
	       	frame.repaint();
		    this.dispose();
		}
		
		if(e.getSource()==submit) {
			
		  if(new String(input.getPassword()).equals(parola)==true) {
			MyFrame frame = new MyFrame();
			this.setConfirmareParola(true);
		    frame.panelMain.setVisible(false);
	       	frame.panelClient.setVisible(false);
	       	frame.setLocationRelativeTo(null);
	       	frame.panelAdmin.setVisible(true);
	       	frame.add(frame.panelAdmin,BorderLayout.CENTER);
	       	frame.revalidate();
	       	frame.validate();
	       	frame.repaint();
		    this.dispose();
		    frame.setConfirmare(true);
		  } else {
			  
			  nrIncercari=nrIncercari-1;
			  if(nrIncercari>0) {
				error = new JDialog(this,"EROARE");
			    JLabel l = new JLabel(" Parola incorecta! Mai aveti ("+nrIncercari+") incercari");
	            error.setLayout(new FlowLayout());
			    error.add(l);
	            error.setSize(240, 100);
	            error.setLocationRelativeTo(null);
	            error.setVisible(true);
	            error.add(exit);
	            input.setText("");
	            error.getRootPane().setDefaultButton(exit);
	            
			  }
			  
			  else {
				  System.exit(0);
			  }
	            
		  	}
		 }
	}

	public boolean isConfirmareParola() {
		return confirmareParola;
	}

	public void setConfirmareParola(boolean confirmareParola) {
		this.confirmareParola = confirmareParola;
	}

}
	

