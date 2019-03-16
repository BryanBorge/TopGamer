package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteException;

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
		String query = "INSERT INTO Users (UserName,Password,Email,FirstName,LastName) " +
		              "Values ( \'" + u + "\', \'" + p + "\', \'" + e + "\', \'" + f + "\', \'" + l +"\');";
		
		//test query to add any data
		String q =  "INSERT INTO User (FirstName, LastName, Email, UserName,Password) " +
	              "Values ( 'f', 'l', 'email', 'a', 'p');";
		
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("User added successfully");
			statement.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
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
				System.out.println("Username or password is incorrect.");
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
