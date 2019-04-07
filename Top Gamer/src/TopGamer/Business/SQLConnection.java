package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
     * Establishes a connection to the database
     */
	public void connect() 
	{      
        try {
        	//location of the database file
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
	 * Returns a tournament instance containing values from the database
	 * @return Tournament instance
	 */
	public Tournament LoadTournamentData() { 
	
		Tournament tempTourney = new Tournament();
		String query = "select TournamentName, Prize from Tournament";
		
		Statement statement = null;
		ResultSet result;
		
		
		String dbTourneyName = null, dbPrize = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while(result.next())
			{
				dbTourneyName = result.getString("TournamentName");
				dbPrize = result.getString("Prize");
			}
			tempTourney.SetTournamentName(dbTourneyName);
			tempTourney.SetPrize(dbPrize);
			return tempTourney;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Loads all usernames into an array for use with autocompletion
	 * @return
	 */
	public ArrayList<String> LoadAllUsernames()
	{
		String query = "select UserName from Users where TeamID is NULL";
		ArrayList<String> users = new ArrayList<String>();
		Statement statement = null;
		ResultSet result;
	
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while(result.next())
			{
				users.add(result.getString("UserName"));
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void CreateTeam(String TeamName, String user1, String user2, String user3, String user4)
	{
		String createTeamQuery = "INSERT into Team (TeamName) VALUES(\"" + TeamName + "\")";
		String addUser1 = "Update Users set TeamID = (select TeamID from Team where TeamName = \"" + TeamName + "\") where UserName = \"" + user1 + "\"";
		String addUser2 = "Update Users set TeamID = (select TeamID from Team where TeamName = \"" + TeamName + "\") where UserName = \"" + user2 + "\"";
		String addUser3 = "Update Users set TeamID = (select TeamID from Team where TeamName = \"" + TeamName + "\") where UserName = \"" + user3 + "\"";
		String addUser4 = "Update Users set TeamID = (select TeamID from Team where TeamName = \"" + TeamName + "\") where UserName = \"" + user4 + "\"";
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(createTeamQuery);
			statement.executeUpdate(addUser1);
			statement.executeUpdate(addUser2);
			statement.executeUpdate(addUser3);
			statement.executeUpdate(addUser4);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * loads team data such as teamname and users into team object
	 * returns team instance using data from database
	 * @return Team
	 */
	public Team loadTeamData(String OpTic) 
	{
		Team teamObj = new Team();
		String teamData = "select * from Team";
		String userData = "select UserID, FirstName, LastName, Email, UserName from Users" + "WHERE teamID = 1"; 
		
		Statement statement = null;
		ResultSet result;
		
		String dbFirstName = "NULL";
		String dbLastName = " NULL";
		String dbEmail = "NULL";
		String dbUserName = "NULL";
		String dbTeamID = "0";
		String dbUserID = "0";
		
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(teamData);
			while(result.next()) 
			{
				dbFirstName = result.getString("FirstName");
				dbLastName = result.getString("LastName");
				dbEmail = result.getString("Email");
				dbUserName = result.getString("UserName");
				dbTeamID = result.getString("TeamID");		
			}
				teamObj.SetTeamName(dbTeamID);
				//teamObj.GetSpecificTeamMember();
				return teamObj;
		
		} catch (SQLException e ) {
			e.printStackTrace();
			
		}
		return teamObj;
	}
	
	
	
}
