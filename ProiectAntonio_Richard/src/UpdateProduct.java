import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.AttributeSet.ColorAttribute;

import java.sql.*;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage; 
public class UpdateProduct extends JFrame{
	
	private JTable table_1;
	
	JTextArea name =new JTextArea("Nume produs"); 
	JTextArea price =new JTextArea("Pret"); 
	JTextArea qty =new JTextArea("Cantitate");
	
	public static List<Product> cos = new ArrayList<>();
	
	public  UpdateProduct() {
		
		List<Product> produse = new ArrayList<>();
		
	
		String[] columnsNames= {"Nume","Pret(ron)","In stoc","Reprezentare"};
		
		ImageIcon image = new ImageIcon("index.jpg");
		this.setIconImage(image.getImage());
		
		JButton addToCart = new JButton("Actualizati produsul");
		addToCart.setBackground(Color.GREEN);
		addToCart.setForeground(Color.BLACK);
		addToCart.setFont(new Font("Verdana", Font.BOLD, 12));
		addToCart.setCursor(new Cursor(HAND_CURSOR));
		addToCart.setFocusable(false);
		
		
		setVisible(true);
		getContentPane().add(addToCart, BorderLayout.NORTH);
		
		setResizable(false);
		setTitle("Lista produse");
		setForeground(Color.WHITE);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(340,600);
		setLocationRelativeTo(null);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
            	//
            }
        });
		
		try {
			Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
			Statement statement = connection.createStatement();
		
			ResultSet resultSet = statement.executeQuery("select * from product");
			
			while(resultSet.next()) {
				
				Product p1=new Product(resultSet.getInt("idProduct"),resultSet.getString("name"),resultSet.getDouble("price"),resultSet.getInt("qty"),resultSet.getBytes("image"));
				produse.add(p1);
			}}
			catch (Exception e) {
				e.printStackTrace();
			}
		
			Object[][] produs = new Object[produse.size()][];
			int i=0;
			for(Product p:produse) {
				produs[i]=new Object[4];
				produs[i][0]=p.getName();
				produs[i][1]=p.getPrice();
				produs[i][2]=p.getQty();	
				
				byte[] byteArray=null;
				byteArray=p.getPic();
				ImageIcon imageIcon = new ImageIcon(byteArray);
				imageIcon.getImage();
				
				produs[i][3]=imageIcon;
				i++;
			}
		DefaultTableModel model = new DefaultTableModel(produs, columnsNames) {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
			@Override
			public Class<?> getColumnClass(int column) {
		        switch(column) {
		            case 0: return String.class;
		            case 1: return Double.class;
		            case 2: return Integer.class;
		            case 3: return ImageIcon.class;
		            default: return Object.class;
		        }
		    }
		};
		
		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(200, 580);
		scrollPane.getViewport().setBackground(Color.lightGray);
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		table.setBackground(Color.lightGray);
		table.setSize(200, 580);
		table.setRowHeight(80);
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		table.setBorder(new MatteBorder(1, 1, 0, 1, Color.BLACK));
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		table.setCellSelectionEnabled(false);
		table.setRowSelectionAllowed(true);
		
		JLabel l1 = new JLabel("Produs",SwingConstants.LEFT);
		JLabel l2 = new JLabel("Pret",SwingConstants.LEFT);
		JLabel l3 = new JLabel("Cantitate",SwingConstants.LEFT);
		
		//int row=table.getSelectedRow();
		
		name.setFont(new Font("Verdana", Font.PLAIN, 16));
		price.setFont(new Font("Verdana", Font.PLAIN, 16));
		qty.setFont(new Font("Verdana", Font.PLAIN, 16));
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
	        public void valueChanged(ListSelectionEvent event) {

	        	String tableClickName = (table.getModel().getValueAt(table.getSelectedRow(), 0).toString());
	    		String tableClickPrice = (table.getModel().getValueAt(table.getSelectedRow(), 1).toString());
	    		String tableClickQty = (table.getModel().getValueAt(table.getSelectedRow(), 2).toString());
	    		
	    		name.setText(tableClickName);
	    		price.setText(tableClickPrice);
	    		qty.setText(tableClickQty);
	        }
	    });
		
		JPanel container = new JPanel();
		container.setSize(300,400);
		container.add(name);
		container.add(price);
		container.add(qty);

		getContentPane().add(container,BorderLayout.SOUTH);
		pack();
		
		addToCart.addActionListener(new ActionListener() {
			
		    public void actionPerformed(ActionEvent e) {
		       
		    	int selectedRow=table.getSelectedRow()+1;
		    	int row=table.getSelectedRow();
		    	if(row==-1) {
					JOptionPane.showMessageDialog(rootPane,"Selectati un produs","Mesaj",1);
				} else {
		    	String tableClick = (table.getModel().getValueAt(row, 0).toString());
		    	
		        	for(Product p:produse) {
		        					try {
		        						Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
		        						Statement statement = connection.createStatement();
		        						if(p.getName().equals(tableClick)==true) {
		        							int response = JOptionPane.showConfirmDialog(rootPane, "Adaugati produsul in cos?","Confirmare",JOptionPane.YES_NO_OPTION);
		        							if(response==0) {
		        						statement.executeUpdate("UPDATE `magazin_online`.`product` SET `Qty` = `Qty`-1 WHERE (`idProduct` = "+selectedRow+") and `Qty`>='0'");	 
		        						statement.executeUpdate("INSERT INTO receipt (Name,Price,Qty)" + "VALUES ('"+p.getName()+"' , "+p.getPrice()+", 1)");	
		        							}}else continue;
		        					}catch(Exception ex) {
		        						ex.printStackTrace();
		        					}
		        						p.setQty(p.getQty()-1);
		        						JOptionPane.showMessageDialog(rootPane,"Produs adaugat cu succes!","Adaugare in cos",1);
		        			break;
					}
		    } 
		    }
		});
		model.fireTableDataChanged();
		table.repaint();
		}
		
		
	}



