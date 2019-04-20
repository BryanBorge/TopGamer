/**
 * Tournament class definition
 * 
 * @author Talha
 * 
 */

package TopGamer.Business;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;



public class Tournament 
{
	private String m_tournamentName;
	private int m_prize;
	private String m_date;
	private String m_location;
	private int m_tournamentID;
	private int m_bracketSize;
	private int m_teamsJoined;
	private Game m_game;
	private ArrayList<Team> m_teams;
	
	/**
	 *Tournament default constructor
	 * 
	 * sets member variables to default value
	 */
	public Tournament() 
	{
		m_tournamentName = "N/A";
		m_teams = new ArrayList<Team>();
		m_game = new Game();
		m_prize = 0;
		m_date = "0/0/0";
		m_location = "N/A";
		m_bracketSize = 0;
		m_teamsJoined = 0;
	}
	
	public void SetDate(String date)
	{
		m_date = date;
	}
	
	public String GetDate() {
		return m_date;
	}
	
	public void SetLocation(String loc) {
		m_location = loc;
	}
	
	public String GetLocation()
	{
		return m_location;
	}
	
	
	/**
	 * Sets the tournamentID member
	 * @param id
	 */
	public void SetID(int id) {
		m_tournamentID = id;
	}
	/**
	 * Returns the tournamentID member
	 * @return
	 */
	public int GetID() {
		return m_tournamentID;
	}
	
	/**
	 * Returns number of teams joined
	 * @return 
	 */
	public int GetTeamsJoined() {
		return m_teamsJoined;
	}
	/**
	 * Setst the number of teams joined
	 * @param n
	 */
	public void SetTeamsJoined(int n) {
		m_teamsJoined = n;
	}
	
	/**
	 * Sets the bracket size
	 * @return
	 */
	public int GetBrackSize() {
		return m_bracketSize;
	}
	
	/**
	 * Returns the brack size
	 * @param b
	 */
	public void SetBrackSize(int b) {
		m_bracketSize = b;
	}
	
	
	/**
	 * Sets prize member variable
	 * @param p
	 */
	public void SetPrize(int p) {
		m_prize = p;
	}
	
	/**
	 * Returns prize member variable
	 * @return
	 */
	public int GetPrize() {
		return m_prize;
	}
	
	/**
	 * Sets TournamentName Member Variable
	 * 
	 * @param TournamentName
	 */
	public void SetTournamentName(String TournamentName) 
	{
		m_tournamentName = TournamentName;
	}

	/**
	 * Gets TournamentName Member Variable
	 * 
	 * @returns TournamentName
	 */
	public String GetTournamentName() 
	{
		return m_tournamentName;
	}

	/**
	 * Returns entire teamName arraylist
	 * 
	 * @return teamName arrayList
	 */
	public ArrayList<Team> GetTeams()
	{
		return m_teams;
	}
	
	/**
	 * Returns single team if it exists in the tournament
	 * @param targetTeam = team being searched for
	 * @return team name if found, null otherwise
	 */
	public Team GetTeam(String targetTeam)
	{
		boolean found = false;
		Team foundTeam = new Team();
		for(Team t : m_teams)
		{
			if(t.GetTeamName().equals(targetTeam)) {
				found = true;
				foundTeam = t;
			}
		}
		if(found)
			return foundTeam;
		else
			return null;
	}
	
	public Game GetGame()
	{
		return m_game;
	}
	
	/**
	 * Loads in all tournament data
	 * Calls LoadGameData and LoadPlatformData
	 * Will call LoadTeamData when it is written
	 * @param id
	 */
	public void LoadTournamentData(int id)
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String tournamentQry = "select TournamentID,TournamentName, GameID, Prize, BracketSize, CONVERT(VARCHAR(10),(Select Date from tblTournaments where TournamentID = " + id + "), 110) as Date, Location from tblTournaments where TournamentID = " + id;
		String countQry = "select count(TournamentID) as num from tblTeams where TournamentID = " + id;
		
		Connection connection = dbConnection.connect();
		Statement statement = null;
		ResultSet result;

		int dbGameID = 0;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(tournamentQry);
			while(result.next())
			{
				this.SetID(result.getInt("TournamentID"));
				this.SetTournamentName(result.getString("TournamentName"));
				dbGameID = result.getInt("GameID");
				this.SetPrize(result.getInt("Prize"));
				this.SetBrackSize(result.getInt("BracketSize"));
				this.SetLocation(result.getString("Location"));
				this.SetDate(result.getString("Date"));
			}
			this.m_game.LoadGameData(dbGameID);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(countQry);
			while(result.next())
			{
				this.SetTeamsJoined(result.getInt("num"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads usernames of all players not currently on a team into an array for use with autocompletion
	 * @return
	 */
	public ArrayList<String> LoadAllAvailavleUsernames()
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		
		String query = "select UserName from tblUsers where TeamID is NULL and PlatformID = " + m_game.GetPlatform().GetID();
		ArrayList<String> users = new ArrayList<String>();
	
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(query);
			while(result.next())
			{
				users.add(result.getString("UserName"));
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * Returns the registered teams for a tournament 
	 * @param tournament id
	 * @return the registerd teams for the tournament
	 */
	 public ArrayList<Team> ViewRegisterdTeams(int tournamentID) {
		 
		 SQLConnection dbConnection = new SQLConnection();
		 String teamNameQry = "select DISTINCT TeamName from tblTeams team  JOIN tblTournaments t ON team.TournamentID = t.TournamentID JOIN tblUsers u on u.TeamID = team.TeamID where t.TournamentID = " + tournamentID;
		 String registeredteamsQry= "select  UserName  from tblTeams team  JOIN tblTournaments t ON team.TournamentID = t.TournamentID JOIN tblUsers u on u.TeamID = team.TeamID where t.TournamentID = " + tournamentID  ;
		 Connection connection =dbConnection.connect();
		 Statement statment =null;
		 ResultSet result;
		 
		 ArrayList <Team>  returnTeam = new ArrayList<Team>();

		 //loads team data from the database

		 Team loadTeam;
		 
		 String dbTeamName = null;
		 
		 try {
			 statment=connection.createStatement();
			 result=statment.executeQuery(teamNameQry);
			 while(result.next())
			 {
				loadTeam = new Team();
				loadTeam.SetTeamName(result.getString("TeamName"));
				loadTeam.LoadTeamData(result.getString("TeamName"));
				returnTeam.add(loadTeam);
			 }
			 return returnTeam;
			 
			 
		 } catch (SQLException e) {
				 e.printStackTrace();
			 }
		 return returnTeam;
		 
	 }


}

 


	