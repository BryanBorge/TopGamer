/*
 * Team class definition
 *
 * 
 * @author Talha
 */

package TopGamer.Business;

public class Team {
	
	private String m_TeamName;
	private int m_Wins;
	private int m_Losses;
	private int m_TournamentID;
	private int m_TeamID;
	
		
	/*
	 * constructor for class team
	 * 
	 * sets member variables to default values
	 */
	
	public Team() {
	
		m_TeamName = "N/A";
		m_Wins = 0;
		m_Losses = 0;
		m_TournamentID = 1;
		m_TeamID = 1;
		}
	
	/*
	 * sets TeamName member
	 * 
	 * @param TeamName
	 */
	
	public void setTeamName(String TeamName)
	{
		m_TeamName = TeamName;
	}
		

	/*
	 * gets TeamName member
	 * @returns TeamName
	 */
	
	public String getTeamName() 
	{
	   return m_TeamName;
	}

	/*
	 * sets Wins member
	 * @param Wins 
	 */
	public void setWins(int Wins) 
	{
		m_Wins = Wins;
	}
	
	/*
	 * gets wins member variable
	 * @returns wins 
	 */
	
	public int getWins() 
	{
	   return m_Wins;  	
	}
	
	/*
	 * sets Losses member variable
	 * 
	 * @param Losses
	 */
	
	public void setLosses(int Losses)
	{
	   m_Losses = Losses;
	}

	
	/*
	 * gets Losses Member Variable
	 * 
	 * @returns Losses
	 */
	
	public int getLosses() 
	{
	   return m_Losses;
	}

	/*
	 * sets TournamentID Member Variable
	 * 
	 * @param TournamentID
	 */
	
	public void setTournamentID(int TournamentID) 
	{
		m_TournamentID = TournamentID;
	}

	/*
	 * gets TournamentID Member Variable
	 * 
	 * @returns TournamentID
	 */
	
	public int getTournamentID() 
	{
		return m_TournamentID;
	}

	/*
	 * sets TeamID Member Variable
	 * 
	 * @param TeamID
	 */
	
	public void setTeamID(int TeamID) 
	{
		m_TeamID = TeamID;
	}

	/*
	 * gets TeamID Member Variable
	 * 
	 * @returns TeamID
	 */
	
	public int getTeamID() 
	{
		return m_TeamID;
	}

}


