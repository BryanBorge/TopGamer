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

	