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
	private ArrayList<Team> m_teamName;
	
	
	public void LoadTournamentData(int id)
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String tournamentQry = "select TournamentID,TournamentName, Prize, BracketSize from tblTournaments";
		String countQry = "select count(TournamentID) as num from tblTeams where TournamentID = " + id;
		
		Connection connection = dbConnection.connect();
		Statement statement = null;
		ResultSet result;

		String dbTourneyName = null, dbPrize = null;
		int dbTourneyID = 0, dbCount = 0, dbBracketSize = 0;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(tournamentQry);
			while(result.next())
			{
				dbTourneyID = result.getInt("TournamentID");
				dbTourneyName = result.getString("TournamentName");
				dbPrize = result.getString("Prize");
				dbBracketSize = result.getInt("BracketSize");
			}
			this.SetTeamsJoined(dbCount);
			this.SetID(dbTourneyID);
			this.SetTournamentName(dbTourneyName);
			this.SetPrize(dbPrize);
			this.SetBrackSize(dbBracketSize);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(countQry);
			while(result.next())
			{
				dbCount = result.getInt("num");
			}
			this.SetTeamsJoined(dbCount);

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
		m_teamName = new ArrayList<Team>();
	}
	
	public void SetID(int id) {
		m_tournamentID = id;
	}
	
	public int GetID() {
		return m_tournamentID;
	}
	
	public int GetTeamsJoined() {
		return m_teamsJoined;
	}
	public void SetTeamsJoined(int n) {
		m_teamsJoined = n;
	}
	
	public int GetBrackSize() {
		return m_bracketSize;
	}
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
	public ArrayList<Team> GetTeamName()
	{
		return m_teamName;
	}
	
	
	/**
	 * Returns single team if it exists in the tournament
	 * @param targetTeam = team being searched for
	 * @return team name if found, null otherwise
	 */
	public Team GetTeamName(Team targetTeam)
	{
		boolean found = false;
		Team foundTeam = new Team();
		for(Team t : m_teamName)
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
		
}

	