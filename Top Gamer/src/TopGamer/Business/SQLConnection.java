package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.SQLiteException;


import java.sql.*;

public class SQLConnection {
   

    private Connection connection;
    
    //Removed constructor - it called connect() every time an instance was created slowing down the program so i got rid of it
    // and moved all fucntions to other classes that call connect(). Speed was increased slightly
    
    
    
    /**
     * Establishes a connection to the database
     */
	public Connection connect() 
	{     
		// Connect to database
        String hostName = "topgamer1.database.windows.net";
        String dbName = "topgamer1";
        String user = "topgamer0505@topgamer1";
        String password = "Password0505";
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
            + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
		
		try {
			// get connection
			connection = DriverManager.getConnection(url); 
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Failed to create connection to database.");
			return null;
		}
		if (connection != null) {
			System.out.println("Successfully created connection to database.");
		}
		return connection;
			
    }

	//AddUser is staying in this class because im not sure where to put it that would make sense	
	/**
	 * Adds all user info from registration form to the database
	 * 
	 * @param firstName First Name
	 * @param lastName Last Name
	 * @param email Email
	 * @param userName UserName
	 * @param password Password
	 */
	public void AddUser(String firstName, String lastName, String email, String userName, String password, String platform) throws SQLiteException
	{
		//adds user info from the registration form
		String addUserQry = "INSERT INTO tblUsers (FirstName,LastName,Email,UserName,Password,PlatformID) " +
	              "Values ( ?, ?, ?, ?, ?, " + "(Select PlatformID from tblPlatform where PlatformName = ?))"; 
		Connection connection = connect();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(addUserQry);
			preparedStatement.setString(1, firstName);
			preparedStatement.setString(2, lastName);
			preparedStatement.setString(3, email);
			preparedStatement.setString(4, userName);
			preparedStatement.setString(5, password);
			preparedStatement.setString(6, platform);
			preparedStatement.executeUpdate();
			System.out.println("User added successfully");
			preparedStatement.close();
		}
		catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	

	

	

	
}
