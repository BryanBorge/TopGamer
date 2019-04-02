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
	private ArrayList<Team> m_teamName;
	
	
	/*
	 * Does this need teams and games as members? Or will we create another class/table for the game INSIDE of the tournament?
	 */

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

	
	/**
	 * sets TournamentName Member Variable
	 * 
	 * @param TournamentName
	 */

	public void setTournamentName(String TournamentName) 
	{
		m_tournamentName = TournamentName;
	}

	/**
	 * gets TournamentName Member Variable
	 * 
	 * @returns TournamentName
	 */

	public String getTournamentName() 
	{
		return m_tournamentName;
	}

	
	/*
	 * returns entire teamName arraylist
	 * 
	 * @return teamName arrayList
	 */
	
	public ArrayList<Team> getTeamName()
	{
		return m_teamName;
	}
	
	
}

	