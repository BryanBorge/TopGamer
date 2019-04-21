package TopGamer.Business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import org.sqlite.SQLiteException;


import java.sql.*;

public class SQLConnection {
   

    private Connection connection;
    
  
    public SQLConnection() {
    	connect();
	}
    
    
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

	
	//all functions below here need to be moved into other classes which means they may need to have some changed made
	
	/**
	 * Adds a user info from registration form to the database
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
	
	public ArrayList<String>LoadAllOpenTeams()
	{
		//need TournamentID has a parameter? 
		String query = "select TeamName, count(t.TeamID) as num from tblUsers u JOIN tblTeams t on u.TeamID = t.TeamID group by TeamName HAVING count(t.TeamID) < 4";
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
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return openTeams;
	}
	
	public void JoinTeam(String user, String teamName)
	{
		String joinTeamQry = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = ?) where UserName = ?";
		
		try {
			PreparedStatement prepStatement = connection.prepareStatement(joinTeamQry);
			prepStatement.setString(1, teamName);
			prepStatement.setString(2, user);
			prepStatement.executeQuery();
			prepStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}
	
	public void CreateTeam(String TeamName, String user1, String user2, String user3, String user4)
	{
		String createTeamQuery = "INSERT into tblTeams (TeamName) VALUES(\'" + TeamName + "\')";
		String addUser1 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user1 + "\'";
		String addUser2 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user2 + "\'";
		String addUser3 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user3 + "\'";
		String addUser4 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user4 + "\'";
		
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
		String addTeamQuery = "Update tblTeams set TournamentID = " + tournamentID + " where TeamName = \'" + teamName + "\'";
		
		try {
			Statement statement = connection.createStatement();
			statement.executeUpdate(addTeamQuery);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	

	
}
