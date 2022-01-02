import java.sql.*;

public class ConnectDB {
	
	static String url = "jdbc:mysql://localhost:3306/magazin_online";
	static String username = "root";
	static String password = "fumunigehi39";
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		try {
		Connection connection = DriverManager.getConnection(url,username,password);
		Statement statement = connection.createStatement();
		
		ResultSet resultSet = statement.executeQuery("select * from product");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
