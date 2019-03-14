package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteException;

import java.sql.*;

public class SQLConnection {
	
	Connection connection;
	
	String fName;
    String lName;
    String email;
    String userName;
    String pass;
    
    /**
     * SQLConnection Constructor 
     * 
     * Calls connect each time an object is created
     */
    public SQLConnection()
    {
    	connection = null;
    	connect();
    }
    
    /**
     * Establish a connection to the database
     */
	public void connect() 
	{      
        try {
        	//location of the database file
        	String databaseLocation = "jdbc:sqlite:TestDataBase.db";
            
        	//create a connection to the database
            connection = DriverManager.getConnection(databaseLocation);
            
            System.out.println("Connection to SQLite has been established.\n");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	/**
	 * Adds a user info from registration form to the database
	 * 
	 * @param f First Name
	 * @param l Last Name
	 * @param e Email
	 * @param u UserName
	 * @param p Password
	 */
	public void AddUser(String f, String l, String e, String u, String p)
	{
		//adds user info from the registration form
		String query = "INSERT INTO User (FirstName, LastName, Email, UserName,Password) " +
		              "Values ( \'" + f + "\', \'" + l + "\', \'" + e + "\', \'" + u + "\', \'" + p +"\');";
		
		//test query to add any data
		String q =  "INSERT INTO User (FirstName, LastName, Email, UserName,Password) " +
	              "Values ( 'f', 'l', 'email', 'a', 'p');";
		
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("User added successfully");
			statement.close();
			connection.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void PrintUser()
	{
		Statement query = null;
		   try {
		     connect();
		      query = connection.createStatement();
		      ResultSet result = query.executeQuery( "SELECT * FROM User" );
		      
		      while ( result.next() ) {
		         String fName = result.getString("FirstName");
		         String lName = result.getString("LastName");
		         String email  = result.getString("Email");
		         String userName = result.getString("UserName");
		         String pass = result.getString("Password");
		         
		         System.out.printf("%s %s %s %s %s \n", fName, lName, email,userName,pass);  
		      }
		      result.close();
		      query.close();
		      connection.close();
		   } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		   }
	}
	

}
