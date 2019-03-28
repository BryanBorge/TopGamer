/*
 * Tournament class definition
 * 
 * @author Talha
 * 
 * 
 */

package TopGamer.Business;

public class Tournament 
{
	private String m_tournamentName;

	/**
	 *constructor for Team class 
	 * 
	 * sets member variables to default value
	 */
	
	public Tournament() 
	{
		m_tournamentName = "N/A";
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



}

	