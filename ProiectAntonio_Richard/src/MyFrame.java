import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.SwingConstants;

public class MyFrame extends JFrame implements ActionListener{
	
	JButton buttonClient,buttonAdmin;
	JButton buttonBack,buttonBackClient;
	JButton buttonViewProductsClient,buttonViewCartClient;
	JPanel panelMain,panelClient,panelAdmin;
	boolean confirmare;
	MyFrame(){
		
		buttonClient = new JButton();
		buttonAdmin = new JButton();
		buttonBack = new JButton();
		buttonBackClient = new JButton();
		buttonViewProductsClient = new JButton();
		buttonViewCartClient = new JButton();
		
		JLabel label = new JLabel("Alegeti cum doriti sa va conectati:",SwingConstants.CENTER);
		JLabel labelClient = new JLabel("Bun venit!",SwingConstants.CENTER);
		JLabel labelClient2 = new JLabel("Alegeti o optiune",SwingConstants.CENTER);
		JLabel labelClientv2 = new JLabel("Bun venit!",SwingConstants.CENTER);
		JLabel labelClient2v2 = new JLabel("Alegeti o optiune",SwingConstants.CENTER);
		JLabel fundalL = new JLabel("",SwingConstants.CENTER);
		fundalL.setLocation(0, 0);
		JLabel fundalL2 = new JLabel("",SwingConstants.CENTER);
		JLabel fundalL3 = new JLabel("",SwingConstants.CENTER);
		JLabel mainGif = new JLabel("",SwingConstants.CENTER);
		JLabel pozaLista = new JLabel("",SwingConstants.CENTER);
		JLabel pozaCos = new JLabel("",SwingConstants.CENTER);
		
		mainGif.setLocation(13, 10);

		
		fundalL.setSize(700, 500);
		fundalL.setIcon(new ImageIcon("background.png"));
		fundalL2.setSize(700, 500);
		fundalL2.setIcon(new ImageIcon("background.png"));
		fundalL3.setSize(700, 500);
		fundalL3.setIcon(new ImageIcon("background.png"));
		mainGif.setSize(600,365);
		mainGif.setIcon(new ImageIcon("mainGIF2.gif"));

		
		pozaLista.setSize(570,360);
		pozaLista.setIcon(new ImageIcon("GIF4.png"));
		pozaLista.setLocation(20,15);
		
		
		panelMain = new JPanel();
		panelClient = new JPanel();
		panelAdmin = new JPanel();
		
		
		JTextArea nume = new JTextArea();
		JTextArea pret = new JTextArea();
		JTextArea cantitate = new JTextArea();
		
		nume.setBounds(127,67, 200, 20);
		nume.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		pret.setBounds(127,107, 200, 20);
		pret.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		cantitate.setBounds(127,147, 200, 20);
		cantitate.setFont(new Font("Verdana", Font.PLAIN, 14));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(127, 223, 80, 80);
		lblNewLabel.setBorder(new LineBorder(Color.BLACK));
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setForeground(Color.WHITE);
		panelAdmin.add(lblNewLabel);
		panelAdmin.add(nume);
		panelAdmin.add(pret);
		panelAdmin.add(cantitate);
		JLabel lblNewLabel_1 = new JLabel("Adaugare produs");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(-55, 10, 527, 47);
		panelAdmin.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Atasare poza(80x80)");
		btnNewButton.setFont(new Font("Verdana", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String name=nume.getText();
				
				String pr=pret.getText();
				double price = Double.parseDouble(pr);
				
				String q=cantitate.getText();
				int quantity=Integer.parseInt(q);
				
				if(name.equals("")==true || pr.equals("")==true || q.equals("")==true) {
					JOptionPane.showMessageDialog(rootPane,"Completati toate campurile","Mesaj",1);
				}
				
				JFileChooser jfile=new JFileChooser();
				jfile.setCurrentDirectory(new File(System.getProperty("user.home")));
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Image","jpg","png","jpeg");
				jfile.addChoosableFileFilter(filter);
				
				int result = jfile.showSaveDialog(null);
				File selectedFile = jfile.getSelectedFile();
				String filename = selectedFile.getName();
				if(filename.endsWith(".jpg")||filename.endsWith(".JPG")||filename.endsWith(".PNG")||filename.endsWith(".jpeg")||filename.endsWith(".JPEG")||filename.endsWith(".png")) {
					if(result==JFileChooser.APPROVE_OPTION) {
						String path = selectedFile.getAbsolutePath();
						ImageIcon myImage = new ImageIcon(path);
						Image img = myImage.getImage();
						Image newImage = img.getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), Image.SCALE_SMOOTH);
						
						ImageIcon image = new ImageIcon(newImage);
						lblNewLabel.setIcon(image);
						
						
						FileInputStream fis = null;
						try {
							Connection con = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
							fis = new FileInputStream(path);
							
							PreparedStatement pst = con.prepareStatement("INSERT INTO product (Name,Price,Qty,Image) values ('"+name+"',"+price+","+quantity+",?)");
							pst.setBinaryStream(1, fis);
							int response = JOptionPane.showConfirmDialog(rootPane, "Salvati imaginea si introduceti produsul nou?","Confirmare..",JOptionPane.YES_NO_OPTION);
							if(response == 0) {
								
								int record = pst.executeUpdate();
								if(record==1) {
									//Statement statement = con.createStatement();
						
									JOptionPane.showMessageDialog(rootPane, "Produs adaugat cu succes","Ok Done",1);
								}else {
									JOptionPane.showMessageDialog(rootPane,"Sorry..","Can't store the image",1);
								}
								
								if(response == 1) {
									JOptionPane.showMessageDialog(rootPane, "Cancelled by User..","Cancel..",1);
								}
								
							}
							
						}catch(Exception ex) {
							ex.getStackTrace();
						}
						
					}
				}
				else {
					JOptionPane.showMessageDialog(rootPane, "Va rugam selectati o imagine","Incercati din nou",1);
				}
					
			}
		});
		//btnNewButton.setAction(action);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(127, 188, 190, 25);
		btnNewButton.setFocusable(false);
		panelAdmin.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("Nume produs",SwingConstants.RIGHT);
		lblNewLabel_2.setBounds(24, 74, 93, 13);
		panelAdmin.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Pret",SwingConstants.RIGHT);
		lblNewLabel_3.setBounds(67, 114, 45, 13);
		panelAdmin.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Cantitate",SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(54, 154, 64, 13);
		panelAdmin.add(lblNewLabel_4);

	
	class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "Selectati poza(80x80)");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
	
		JButton buttonAdmin2=  new JButton("Modificare produse");
		buttonAdmin2.setBounds(400,324,200,30);
		buttonAdmin2.setFocusable(false);
		buttonAdmin2.setFont(new Font("Verdana",Font.BOLD, 16));
		buttonAdmin2.setBorder(BorderFactory.createEtchedBorder());
		buttonAdmin2.setBackground(Color.lightGray);
		buttonAdmin2.setForeground(Color.BLACK);
		buttonAdmin2.addActionListener(new ActionListener() {
			
		    public void actionPerformed(ActionEvent e) {
		        new UpdateProduct();
		        
		    }
		});
		panelAdmin.add(buttonAdmin2);
		
		buttonClient.setBounds(60, 120, 140, 60);
		buttonClient.addActionListener(this);
		buttonClient.setText(" Client");
		buttonClient.setFocusable(false);
		buttonClient.setFont(new Font("Verdana",Font.BOLD, 16));
		buttonClient.setBorder(BorderFactory.createEtchedBorder());
		buttonClient.setBackground(Color.lightGray);
		buttonClient.setForeground(Color.BLACK);
		buttonClient.setBorderPainted(true);
		buttonClient.setCursor(new Cursor(HAND_CURSOR));
		
		buttonAdmin.setBounds(420, 120, 140, 60);
		buttonAdmin.addActionListener(this);
		buttonAdmin.setText(" Admin");
		buttonAdmin.setFocusable(false);
		buttonAdmin.setFont(new Font("Verdana",Font.BOLD, 16));
		buttonAdmin.setBorder(BorderFactory.createEtchedBorder());
		buttonAdmin.setBackground(Color.lightGray);
		buttonAdmin.setForeground(Color.BLACK);
		buttonAdmin.setCursor(new Cursor(HAND_CURSOR));
		
		// --------------------------------------------------------------- CLIENT 
		
		buttonViewProductsClient.addActionListener(this);
		buttonViewProductsClient.setText(" Lista produse");
		buttonViewProductsClient.setFocusable(false);
		buttonViewProductsClient.setFont(new Font("Verdana",Font.BOLD, 16));
		buttonViewProductsClient.setBorder(BorderFactory.createEtchedBorder());
		buttonViewProductsClient.setBackground(Color.lightGray);
		buttonViewProductsClient.setForeground(Color.BLACK);
		buttonViewProductsClient.setBounds(20,130,190,30);
		buttonViewProductsClient.setHorizontalAlignment(SwingConstants.CENTER);
		buttonViewProductsClient.addActionListener(this);
		buttonViewProductsClient.setCursor(new Cursor(HAND_CURSOR));
		buttonViewProductsClient.addActionListener(new ActionListener() {
			
		    public void actionPerformed(ActionEvent e) {
		        new ViewProducts();
		        
		    }
		});
		
		buttonViewCartClient.addActionListener(this);
		buttonViewCartClient.setText(" Vezi cosul");
		buttonViewCartClient.setFocusable(false);
		buttonViewCartClient.setFont(new Font("Verdana",Font.BOLD, 16));
		buttonViewCartClient.setBorder(BorderFactory.createEtchedBorder());
		buttonViewCartClient.setBackground(Color.lightGray);
		buttonViewCartClient.setForeground(Color.BLACK);
		buttonViewCartClient.setBounds(400,130,190,30);
		buttonViewCartClient.setHorizontalAlignment(SwingConstants.CENTER);
		buttonViewCartClient.addActionListener(this);
		buttonViewCartClient.setCursor(new Cursor(HAND_CURSOR));
		buttonViewCartClient.addActionListener(new ActionListener() {
			
		    public void actionPerformed(ActionEvent e) {
		        new ViewCart();
		        
		    }
		});
		
		buttonBackClient.addActionListener(this);
		buttonBackClient.setText(" Inapoi");
		buttonBackClient.setFocusable(false);
		buttonBackClient.setFont(new Font("Verdana",Font.BOLD, 16));
		buttonBackClient.setBorder(BorderFactory.createEtchedBorder());
		buttonBackClient.setBackground(Color.lightGray);
		buttonBackClient.setForeground(Color.BLACK);
		buttonBackClient.setBounds(20,345,100,30); //20,324,100,30
		buttonBackClient.setHorizontalAlignment(SwingConstants.LEFT);
		buttonBackClient.addActionListener(this);
		buttonBackClient.setCursor(new Cursor(HAND_CURSOR));
		
		labelClient.setFont(new Font("Verdana", Font.BOLD, 20));
		labelClient.setVerticalAlignment(JLabel.TOP);
		labelClient.setForeground(Color.BLACK);
		labelClient.setBounds(130, 30, 360, 45);
		
		labelClient2.setFont(new Font("Verdana", Font.BOLD, 20));
		labelClient2.setVerticalAlignment(JLabel.TOP);
		labelClient2.setForeground(Color.BLACK);
		labelClient2.setBounds(130, 60, 360, 45);
		
		labelClientv2.setFont(new Font("Verdana", Font.BOLD, 20));
		labelClientv2.setVerticalAlignment(JLabel.TOP);
		labelClientv2.setForeground(Color.BLACK);
		labelClientv2.setBounds(130, 30, 360, 45);
		
		labelClient2v2.setFont(new Font("Verdana", Font.BOLD, 20));
		labelClient2v2.setVerticalAlignment(JLabel.TOP);
		labelClient2v2.setForeground(Color.BLACK);
		labelClient2v2.setBounds(130, 60, 360, 45);
		
		
		panelClient.setPreferredSize(new Dimension(100,100));	
		panelClient.setVisible(true);
		panelClient.setLayout(null);
		panelClient.add(labelClientv2);
		panelClient.add(labelClient2v2);
		panelClient.add(buttonViewProductsClient);
		panelClient.add(buttonViewCartClient);
		panelClient.add(buttonBackClient);
		panelClient.add(pozaLista);
		panelClient.add(fundalL3);
		
		panelClient.setBackground(Color.black);
		
		
		
		// --------------------------------------------------------------- CLIENT 
		
		
		// --------------------------------------------------------------- ADMIN
		
		buttonBack.addActionListener(this);
		buttonBack.setText(" Inapoi ");
		buttonBack.setFocusable(false);
		buttonBack.setFont(new Font("Verdana",Font.BOLD, 16));
		buttonBack.setBorder(BorderFactory.createEtchedBorder());
		buttonBack.setBackground(Color.lightGray);
		buttonBack.setForeground(Color.BLACK);
		buttonBack.setBounds(20,324,100,30);
		buttonBack.setHorizontalAlignment(SwingConstants.LEFT);
		buttonBack.addActionListener(this);
		buttonBack.setCursor(new Cursor(HAND_CURSOR));
		
		panelAdmin.setPreferredSize(new Dimension(600, 399));	
		panelAdmin.setVisible(true);
		panelAdmin.setLayout(null);
		panelAdmin.add(fundalL);
		panelAdmin.add(buttonBack);
		panelAdmin.add(fundalL);
		
		panelAdmin.setBackground(Color.black);
		
		// --------------------------------------------------------------- ADMIN
		
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.TOP);
		label.setFont(new Font("Verdana", Font.BOLD, 20));
		label.setVerticalAlignment(JLabel.TOP);
		label.setForeground(Color.BLACK);
		label.setBounds(115, 40, 400, 45);
		
		
		
		this.setSize(640,420);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setTitle("Magazin Online");		
		getContentPane().setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		ImageIcon image = new ImageIcon("index.jpg");
		this.setIconImage(image.getImage());
		
		
		panelMain.setPreferredSize(new Dimension(100,100));	
		panelMain.setVisible(true);
		panelMain.setLayout(null);
		panelMain.add(label);
		
		panelMain.add(buttonClient);
		panelMain.add(buttonAdmin);
		panelMain.add(mainGif);
		panelMain.add(fundalL2);
		
		panelMain.setBackground(Color.black);//250,170,84
		


		getContentPane().add(panelMain,BorderLayout.CENTER);
		this.revalidate();
		this.validate(); 
		this.repaint();
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==buttonClient) {
			panelMain.setVisible(false);
			panelAdmin.setVisible(false);
			panelClient.setVisible(true);
			getContentPane().add(panelClient,BorderLayout.CENTER);	
			this.revalidate();
			this.validate(); 
			this.repaint();	
		}
		
		if(e.getSource()==buttonAdmin) {
			Password pwd = new Password();
			if(confirmare==true) {									// 
				//System.out.println(pwd.isConfirmareParola());		// DE IMPLEMENTAT
				this.dispose();										//
			}
			this.dispose();
		}
		
		if(e.getSource()==buttonBack || e.getSource()==buttonBackClient) {
			panelClient.setVisible(false);
			panelAdmin.setVisible(false);
			panelMain.setVisible(true);
			getContentPane().add(panelMain,BorderLayout.CENTER);
			this.revalidate();
			this.validate(); 
			this.repaint();	
		}
		
	}

	public void setConfirmare(boolean confirmare) {
		this.confirmare = confirmare;
	}
}
