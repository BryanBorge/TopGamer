package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import javafx.scene.control.Alert;

import java.sql.*;

public class SQLConnection {
   
	//testing branch stuff
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
          //String databaseLocation = "jdbc:sqlite:TestDataBase.db";
        	String databaseLocation = "jdbc:sqlite:TopGamerDB1.1.db";
            
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
	 * @param firstName First Name
	 * @param lastName Last Name
	 * @param email Email
	 * @param userName UserName
	 * @param password Password
	 */
	public void AddUser(String firstName, String lastName, String email, String userName, String password) throws SQLiteException
	{
		//adds user info from the registration form
		String query = "INSERT INTO Users (FirstName,LastName,Email,UserName,Password) " +
		              "Values ( \'" + firstName + "\', \'" + lastName + "\', \'" + email + "\', \'" + userName + "\', \'" + password +"\');";
		
		//test query to add any data
		String q =  "INSERT INTO User (FirstName, LastName, Email, UserName,Password) " +
	              "Values ( 'f', 'l', 'email', 'a', 'p');";
		
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("User added successfully");
			statement.close();
		}
		catch (SQLException e1) {
			throw new SQLiteException("cannot save user to database", SQLiteErrorCode.SQLITE_BUSY);
		}
	}
	
	/**
	 * Returns true and loads user data if login exists and false otherwise
	 * @param userName From registration form
	 * @param password From registration form
	 * @param test user instance
	 * @return true/false
	 * @throws SQLException
	 */
	public boolean Login(String userName, String password,User test) throws SQLException
	{
		//adds user info from the registration form
		//String query = "select UserName, Password from User where UserName= \"" + userName + "\" and Password= \"" + password + "\"  "; 
		String query = "select * from Users where UserName= \"" + userName + "\" and Password= \"" + password + "\"  ";
		
		Statement statement = null;
		ResultSet result;
		
		String dbUserName = null,dbPassword= null,dbFirstName = null, dbLastName = null,dbEmail = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while(result.next())
			{
				dbFirstName = result.getString("FirstName");
				dbLastName = result.getString("LastName");
				dbEmail = result.getString("Email");
				dbUserName = result.getString("UserName");
				dbPassword = result.getString("Password");
			}
			if(userName.equals(dbUserName) && password.equals(dbPassword))
			{
				//if successful, load all data to user
				System.out.println("User login successful");
				test.SetFirstName(dbFirstName);
				test.SetLastName(dbLastName);
				test.SetEmail(dbEmail);
				test.SetUsername(dbUserName);
				return true;
			}
			if(!userName.equals(dbUserName) || !password.equals(dbPassword))
			{
				return false;
			}
			else
				return true;
				
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			return false;
		}
		finally
		{
			statement.close();
		}
	
	}
	
	
	
	/**
	 * Just a test function that prints user from the database
	 */
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
