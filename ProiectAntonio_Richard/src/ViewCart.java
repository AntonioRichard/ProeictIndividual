import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

public class ViewCart extends JFrame{
private JTable table_1;
	
	List<Product> produse = new ArrayList<>();
	List<Cart> cos = new ArrayList<>();
	
	String[] columnsNames= {"Produs","Pret(ron)"};
	
public  ViewCart() {
		
	double totalSuma=0;
	
	ImageIcon image = new ImageIcon("index.jpg");
	this.setIconImage(image.getImage());
	
	
	
	JButton removeFromCart = new JButton("Scoate din cos");
	JButton checkout = new JButton("Efectuati achizitia");
	JButton refresh = new JButton("Reactualizare cos");
	
	
	removeFromCart.setBackground(Color.RED);
	removeFromCart.setForeground(Color.BLACK);
	removeFromCart.setFont(new Font("Verdana", Font.BOLD, 12));
	removeFromCart.setCursor(new Cursor(HAND_CURSOR));
	removeFromCart.setFocusable(false);
	
	checkout.setBackground(new Color(0, 191, 255));
	checkout.setForeground(Color.BLACK);
	checkout.setFont(new Font("Verdana", Font.BOLD, 12));
	checkout.setCursor(new Cursor(HAND_CURSOR));
	checkout.setFocusable(false);

	refresh.setBackground(new Color(0, 191, 255));
	refresh.setForeground(Color.BLACK);
	refresh.setFont(new Font("Verdana", Font.BOLD, 12));
	refresh.setCursor(new Cursor(HAND_CURSOR));
	refresh.setFocusable(false);
	
	
	
	setVisible(true);
	getContentPane().add(removeFromCart, BorderLayout.NORTH);
	
	JPanel pane = new JPanel();
	pane.setLayout(new BoxLayout(pane,BoxLayout.LINE_AXIS));
	
	pane.add(refresh);
	pane.add(checkout);
	
	getContentPane().add(pane,BorderLayout.SOUTH);
	pack();
	
	setResizable(false);
	setTitle("Cos");
	setForeground(Color.WHITE);
	setBackground(Color.WHITE);
	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	setSize(410,600);
	setLocationRelativeTo(null);
	
	try {
		Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
		Statement statement = connection.createStatement();
	
		ResultSet resultSet = statement.executeQuery("select * from receipt");
	
		while(resultSet.next()) {
			Cart c1=new Cart(resultSet.getString("name"),resultSet.getDouble("price"),resultSet.getInt("ID"));
			cos.add(c1);
		}}
		catch (Exception e) {
			e.printStackTrace();
		}
	
	
	
	Object[][] produs=new Object[cos.size()][];
	
	int i=0;
	for(Cart c:cos) {
		produs[i]=new Object[2];
		produs[i][0]=c.getNume();
		produs[i][1]=c.getPret();
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
	            default: return Object.class;
	        }
	    }
	};
	
	JTable table = new JTable(model);
	JScrollPane scrollPane = new JScrollPane(table);
	
	scrollPane.getViewport().setBackground(Color.lightGray);
	table.setColumnSelectionAllowed(true);
	table.setCellSelectionEnabled(true);
	table.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	table.setBackground(Color.lightGray);
	table.setSize(380, 580);
	table.setRowHeight(80);
	table.setFont(new Font("Arial", Font.PLAIN, 15));
	table.setBorder(new MatteBorder(1, 1, 0, 1, Color.BLACK));
	getContentPane().add(scrollPane, BorderLayout.CENTER);
	table.setCellSelectionEnabled(false);
	table.setRowSelectionAllowed(true);
	
	for(Cart c:cos) {
		totalSuma=totalSuma+c.getPret();
	}
	
	JButton total = new JButton("Total:"+String.format("%.2f", totalSuma));
	pane.add(total);
	total.setBackground(new Color(0, 191, 255));
	total.setForeground(Color.BLACK);
	total.setFont(new Font("Verdana", Font.BOLD, 12));
	total.setCursor(new Cursor(HAND_CURSOR));
	total.setFocusable(false);
	total.setHorizontalAlignment(SwingConstants.LEFT);
	
	removeFromCart.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
//			int row = table.getSelectedRow();
//			if(row==-1) {
//				JOptionPane.showMessageDialog(rootPane,"Selectati un produs","Mesaj",1);
//			}else {
//				String tableClick = (table.getModel().getValueAt(row, 0).toString());
//				
//				if(tableClick.equals("apa")) {
//	        	for(Cart c:cos) {
//	        		if(c.getNume().equals("apa")==true) {
//	        				int response = JOptionPane.showConfirmDialog(rootPane, "Eliminati produsul din cos?","Confirmare",JOptionPane.YES_NO_OPTION);
//	        				if(response==0) {
//	        					try {
//	        						Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
//	        						Statement statement = connection.createStatement();
//	        						for(Cart cs:cos) {
//	        							if(cs.getNume().equals("apa")==true) {
//	        							statement.executeUpdate("UPDATE `magazin_online`.`product` SET `Qty` = `Qty`+1 WHERE (`idProduct` = '1') and `Qty`>='0'");	 
//	        							statement.executeUpdate("DELETE FROM receipt WHERE (`ID` = "+cs.getId()+")");	
//	        							break;
//	        							}
//	        						}
//	        					}catch(Exception ex) {
//	        						ex.printStackTrace();
//	        					}
//	        						dispose();
//	        						new ViewCart();
//	        						break;
//	        					}
//	        			
//	        		}
//	        		
//				}
//	        	
//	        }
//			if(tableClick.equals("suc")) {
//		       	for(Cart c:cos) {
//		       		if(c.getNume().equals("suc")==true) {
//		       				int response = JOptionPane.showConfirmDialog(rootPane, "Eliminati produsul din cos?","Confirmare",JOptionPane.YES_NO_OPTION);
//		       				if(response==0) {
//		       					try {
//		       						Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
//	        						Statement statement = connection.createStatement();
//	        						for(Cart cs:cos) {		        							
//	        							if(cs.getNume().equals("suc")==true) {
//		        						statement.executeUpdate("UPDATE `magazin_online`.`product` SET `Qty` = `Qty`+1 WHERE (`idProduct` = '2') and `Qty`>='0'");	 
//		        						statement.executeUpdate("DELETE FROM receipt WHERE (`ID` = "+cs.getId()+")");	
//		        						break;
//		        						}
//	        						}
//		        				}catch(Exception ex) {
//		        					ex.printStackTrace();
//		        				}
//		        					dispose();
//	        						new ViewCart();
//		        					break;
//		        				}	
//		       		}	
//		       	}	
//		      }
//		}
			int selectedRow=table.getSelectedRow()+1;
	    	int row=table.getSelectedRow();
	    	if(row==-1) {
				JOptionPane.showMessageDialog(rootPane,"Selectati un produs","Mesaj",1);
			}
	    	else {
	    	String tableClick = (table.getModel().getValueAt(row, 0).toString());
	        	for(Cart c:cos) {
	        					try {
	        						Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
	        						Statement statement = connection.createStatement();
	        						if(c.getNume().equals(tableClick)==true) {
	        							int response = JOptionPane.showConfirmDialog(rootPane, "Scoateti produsul din cos?","Confirmare",JOptionPane.YES_NO_OPTION);
	        							if(response==0) {
	        								statement.executeUpdate("UPDATE `magazin_online`.`product` SET `Qty` = `Qty`+1 WHERE (`Name` = '"+tableClick+"')");	 
			        						statement.executeUpdate("DELETE FROM receipt WHERE (`ID` = "+c.getId()+")");	
	        								}}else continue;
	        					}catch(Exception ex) {
	        						ex.printStackTrace();
	        					}
	        					dispose();
        						new ViewCart();
        						break;
				}
		}
		}
	});
	double aux=totalSuma;
	checkout.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			JLabel label = new JLabel("Efectuati achizitia?\nTOTAL: "+aux+"ron",SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.BOLD, 14));
			int response = JOptionPane.showConfirmDialog(rootPane, label,"Confirmare",JOptionPane.YES_NO_OPTION);
			if(response==0) {
				try {
					Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
					Statement statement = connection.createStatement();
				
					ResultSet resultSet = statement.executeQuery("select * from receipt");
				
					statement.executeUpdate("TRUNCATE TABLE `receipt`");
					ImageIcon icon = new ImageIcon("test2.jpg");
					JOptionPane.showMessageDialog(rootPane,"Va multumim!","Mesaj",1,icon);
					dispose();
				}
					
				catch (Exception ex) {
						ex.printStackTrace();
					}
			}
		}
	});
	
	refresh.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
			new ViewCart();
			
		}
	});
	
 }
}