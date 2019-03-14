package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteException;

import java.sql.*;

public class SQLConnection {
	
	Connection c = null;
	
	String fName;
    String lName;
    String email;
    String userName;
    String pass;
	
    public void SetFirstName(String n) { fName = n;}
    public String GetFirstName() {return fName;}
    
    public void SetLastName(String n) { lName = n;}
    public String GetLastName() {return lName;}
    
    public void SetEmail(String e) { email = e;}
    public String GetEmail() {return email;}

    public void SetUserName(String n) { userName = n;}
    public String GetUserName() {return userName;}
    
    public void SetPassword(String p) { pass = p;}
    public String GetPassword() {return pass;}
    
    
	public void connect() 
	{      
        try {
          String url = "jdbc:sqlite:TestDataBase.db";
            
          // create a connection to the database
            c = DriverManager.getConnection(url);
            
            System.out.println("Connection to SQLite has been established.\n");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

	public void AddUser(String f, String l, String e, String u, String p)
	{
		String query = "INSERT INTO User (FirstName, LastName, Email, UserName,Password) " +
		              "Values ( \'" + f + "\', \'" + l + "\', \'" + e + "\', \'" + u + "\', \'" + p +"\');";
		
		String q =  "INSERT INTO User (FirstName, LastName, Email, UserName,Password) " +
	              "Values ( 'f', 'l', 'email', 'a', 'p');";
		
		
		try {
			Statement statement = c.createStatement();
			statement.executeUpdate(query);
			statement.close();
			c.close();
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
	}
	
	public void PrintUser()
	{
		Statement query = null;
		   try {
		     connect();
		      query = c.createStatement();
		      ResultSet result = query.executeQuery( "SELECT * FROM User" );
		      
		      while ( result.next() ) {
		         String fName = result.getString("FirstName");
		         String lName = result.getString("LastName");
		         String email  = result.getString("Email");
		         String userName = result.getString("UserName");
		         String pass = result.getString("Password");
		         
		         System.out.printf("%s %s %s %s %s", fName, lName, email,userName,pass);  
		      }
		      result.close();
		      query.close();
		      c.close();
		   } catch ( Exception e ) {
		      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		      System.exit(0);
		   }
	}
	

}
