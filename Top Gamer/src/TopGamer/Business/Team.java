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
	 * constructor for class team
	 * 
	 * sets member variables to default values
	 */
	
	public Team() {
	
		m_teamName = "N/A";
		m_teamMembers = new ArrayList<User>();
		m_wins = 0;
		m_losses = 0;
		
		}
	
	/**
	 * sets TeamName member variable
	 * 
	 * @param TeamName
	 */
	
	public void setTeamName(String TeamName)
	{
		m_teamName = TeamName;
	}
		

	/**
	 * gets TeamName member variable
	 * @returns TeamName 
	 */
	
	public String getTeamName() 
	{
	   return m_teamName;
	}
	
	
	public void AddTeamMember(User teamMember)
	{
		//not sure what else to do with this just yet. Need to create user instances from the db to add to the list based on who the user chooses.
		m_teamMembers.add(teamMember);
	}
	
	
	

	/**
	 * sets Wins member variable
	 * @param Wins 
	 */
	public void setWins(int Wins) 
	{
		m_wins = Wins;
	}
	
	/**
	 * gets wins member variable
	 * @returns wins 
	 */
	
	public int getWins() 
	{
	   return m_wins;  	
	}
	
	/**
	 * sets Losses member variable
	 * 
	 * @param Losses
	 */
	
	public void setLosses(int Losses)
	{
	   m_losses = Losses;
	}

	
	/**
	 *
	 * gets Losses Member Variable
	 * 
	 * @returns Losses
	 */
	public int getLosses() 
	{
	   return m_losses;
	}

	
}


