/*
 * Tournament class definition
 * 
 * @author Talha
 * 
 * 
 */

package TopGamer.Business;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import java.sql.Connection;



public class Tournament 
{
	private String m_tournamentName;
	private String m_prize;
	private int m_tournamentID;
	private int m_bracketSize;
	private int m_teamsJoined;
	private Game m_game;
	private ArrayList<Team> m_teams;
	
	
	/**
	 * Loads in all tournament data
	 * Calls LoadGameData and LoadPlatformData
	 * Will call LoadTeamData when it is written
	 * @param id
	 */
	public void LoadTournamentData(int id)
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String tournamentQry = "select TournamentID,TournamentName, GameID, Prize, BracketSize from tblTournaments where TournamentID = " + id;
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
				this.SetPrize(result.getString("Prize"));
				this.SetBrackSize(result.getInt("BracketSize"));
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
	 *Tournament default constructor
	 * 
	 * sets member variables to default value
	 */
	public Tournament() 
	{
		m_tournamentName = "N/A";
		m_teams = new ArrayList<Team>();
		m_game = new Game();
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
	public void SetPrize(String p) {
		m_prize = p;
	}
	
	/**
	 * Returns prize member variable
	 * @return
	 */
	public String GetPrize() {
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
	public Team GetTeam(Team targetTeam)
	{
		boolean found = false;
		Team foundTeam = new Team();
		for(Team t : m_teams)
		{
			if(t.GetTeamName().equals(targetTeam.GetTeamName())) {
				found = true;
				foundTeam = targetTeam;
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
	
		
}

	