/*
 * Tournament class definition
 * 
 * @author Talha
 * 
 * 
 */

package TopGamer.Business;
import java.util.ArrayList;



public class Tournament 
{
	private String m_tournamentName;
	private String m_prize;
	private int m_bracketSize;
	private int m_teamJoined;
	private Game m_game;
	private ArrayList<Team> m_teamName;
	
	

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

	public void SetPrize(String p) {
		m_prize = p;
	}
	
	public String GetPrize() {
		return m_prize;
	}
	
	
	
	/**
	 * sets TournamentName Member Variable
	 * 
	 * @param TournamentName
	 */

	public void SetTournamentName(String TournamentName) 
	{
		m_tournamentName = TournamentName;
	}

	/**
	 * gets TournamentName Member Variable
	 * 
	 * @returns TournamentName
	 */

	public String GetTournamentName() 
	{
		return m_tournamentName;
	}

	
	/**
	 * returns entire teamName arraylist
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

	