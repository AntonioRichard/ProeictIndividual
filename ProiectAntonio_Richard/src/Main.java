import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class Main {

	public static void main(String[] args) {
		
		ArrayList<Product> produse = new ArrayList<>();
		
		new MyFrame();
		try {
			Connection connection = DriverManager.getConnection(ConnectDB.url,ConnectDB.username,ConnectDB.password);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("select * from product");
			while(resultSet.next()) {
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
	}	
}
