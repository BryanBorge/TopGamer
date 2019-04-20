/*
 * Team class definition
 *
 * 
 * @author Talha
 */

package TopGamer.Business;

import java.util.ArrayList;

public class Team {
	
	private String m_teamName;
	private ArrayList<User> m_teamMembers;
	private int m_wins;
	private int m_losses;
	
		
	/**
	 * Team default constructor
	 * 
	 * sets member variables to default values
	 */
	
	public Team() {
	
		m_teamName = "N/A";
		m_teamMembers = new ArrayList<User>();
		m_wins = 0;
		m_losses = 0;	
	}
	
	public Team(String name, int wins, int losses)
	{
		m_teamName = name;
		m_wins = wins;
		m_losses = losses;
	}
	
	/**
	 * sets TeamName member variable
	 * 
	 * @param TeamName
	 */
	
	public void SetTeamName(String TeamName)
	{
		m_teamName = TeamName;
	}
		

	/**
	 * gets TeamName member variable
	 * @returns TeamName 
	 */
	
	public String GetTeamName() 
	{
	   return m_teamName;
	}
	
	/**
	 * Adds a team member to the teamMembers array
	 * @param teamMember
	 */
	public void AddTeamMember(User teamMember)
	{
		m_teamMembers.add(teamMember);
	}
	
	/**
	 * Returns entire teamMembers ArrayList
	 * @return teamMember ArrayList
	 */ 
	public ArrayList<User> GetAllTeamMembers()
	{
		return m_teamMembers;
	}
		
	/**
	 * Returns a specific team member that matches the parameter value
	 * @param teamMember - Team member being searched for
	 * @return teamMember if it exists, null otherwise
	 */
	public User GetSpecificTeamMember(User teamMember)
	{
		for(User u : m_teamMembers)
		{
			if(u.GetUsername().equals(teamMember.GetUsername()))
				return u;
		}
		return null;
	}
	
	/**
	 * sets Wins member variable
	 * @param Wins 
	 */
	public void SetWins(int Wins) 
	{
		m_wins = Wins;
	}
	
	/**
	 * gets wins member variable
	 * @returns wins 
	 */
	
	public int GetWins() 
	{
	   return m_wins;  	
	}
	
	/**
	 * sets Losses member variable
	 * 
	 * @param Losses
	 */
	
	public void SetLosses(int Losses)
	{
	   m_losses = Losses;
	}

	/**
	 *
	 * gets Losses Member Variable
	 * 
	 * @returns Losses
	 */
	public int GetLosses() 
	{
	   return m_losses;
	}


}