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
	
		
	/*
	 * constructor for class team
	 * 
	 * sets member variables to default values
	 */
	
	public Team() {
	
		m_TeamName = "N/A";
		m_Wins = 0;
		m_Losses = 0;
		
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

	
}


