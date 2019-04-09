package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.sqlite.SQLiteErrorCode;
import org.sqlite.SQLiteException;

import javafx.scene.control.Alert;

import java.sql.*;

public class SQLConnection {
   
	//used for joining an open team
	//select TeamName, count() as NUMOFPLAyERS from tblUsers u JOIN tblTeams t on u.TeamID = t.TeamID group by t.TeamID HAVING count() < 4

    Connection connection;
    
  
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
		String query = "select * from tblUsers where UserName= \"" + userName + "\" and Password= \"" + password + "\"  ";
		
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
		String query = "select TournamentID,TournamentName, Prize from tblTournaments";
		
		Statement statement = null;
		ResultSet result;
		
		
		String dbTourneyName = null, dbPrize = null;
		int dbTourneyID = 0;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while(result.next())
			{
				dbTourneyID = result.getInt("TournamentID");
				dbTourneyName = result.getString("TournamentName");
				dbPrize = result.getString("Prize");
			}
			tempTourney.SetID(dbTourneyID);
			tempTourney.SetTournamentName(dbTourneyName);
			tempTourney.SetPrize(dbPrize);
			return tempTourney;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Loads usernames of all players not currently on a team into an array for use with autocompletion
	 * @return
	 */
	public ArrayList<String> LoadAllAvailavleUsernames()
	{
		String query = "select UserName from tblUsers where TeamID is NULL";
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
	
	public ArrayList<String>LoadAllOpenTeams()
	{
		String query = "select TeamName, count() as NUMOFPLAyERS from tblUsers u JOIN tblTeams t on u.TeamID = t.TeamID group by t.TeamID HAVING count() < 4";
		ArrayList<String> openTeams = new ArrayList<String>();
		Statement statement = null;
		ResultSet result;
	
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while(result.next())
			{
				openTeams.add(result.getString("TeamName"));
			}
			return openTeams;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void CreateTeam(String TeamName, String user1, String user2, String user3, String user4)
	{
		String createTeamQuery = "INSERT into tblTeams (TeamName) VALUES(\"" + TeamName + "\")";
		String addUser1 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \"" + TeamName + "\") where UserName = \"" + user1 + "\"";
		String addUser2 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \"" + TeamName + "\") where UserName = \"" + user2 + "\"";
		String addUser3 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \"" + TeamName + "\") where UserName = \"" + user3 + "\"";
		String addUser4 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \"" + TeamName + "\") where UserName = \"" + user4 + "\"";
		
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
	
	public void AddTeamToTournament(String teamName, int tournamentID)
	{
		String addTeamQuery = "Update tblTeams set TournamentID = " + tournamentID + " where TeamName = \"" + teamName + "\"";
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(addTeamQuery);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads team members into teamMemebers array
	 * returns team instance using data from database
	 * @return Team
	 */
	public Team LoadTeamData(String teamName) 
	{
		Team teamObj = new Team();
		String teamQuery = "select FirstName, LastName, UserName from Users u JOIN Team t on u.TeamID = t.TeamID where u.TeamID = (select TeamID from Team where TeamName = \"" + teamName + "\")";

		Statement statement = null;
		ResultSet result;
		String dbFirstName = null;
		String dbLastName = null;
		String dbUsername = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(teamQuery);
			while(result.next()) 
			{
					dbFirstName = result.getString("FirstName");
					dbLastName = result.getString("LastName");
					dbUsername = result.getString("UserName");
					teamObj.AddTeamMember(new User(dbFirstName,dbLastName,dbUsername));
			}
			return teamObj;
		} catch (SQLException e ) {
			e.printStackTrace();
			
		}
		return teamObj;
	}
	
	
	
}
