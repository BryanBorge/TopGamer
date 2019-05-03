/*
 * Team class definition
 *
 * 
 * @author Talha
 */

package TopGamer.Business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Team {
	
	private String m_teamName;
	private ArrayList<User> m_teamMembers;
	private int m_wins;
	private int m_losses;
	private int m_score;
	private Boolean m_scoredReported;
	
		
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
	
	public Team(String name, User user)
	{
		m_teamName = name;
		m_teamMembers.add(user);
	}
	
	public Team(String name, int w, int l, int s)
	{
		m_teamName = name;
		m_wins = w;
		m_losses = l;
		m_score = s;
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
		User returnUser = new User();
		for(User u : m_teamMembers)
		{
			if(u.GetUsername().equals(teamMember.GetUsername()))
				returnUser = u;
		}
		return returnUser;
	}
	
	public void SetScore(int s)
	{
		m_score = s;
	}
	
	public int GetScore() {
		return m_score;
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

	public void LoadTeamData(String teamName)
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String teamQry = "select ScoreReported, UserName, FirstName, LastName, Email from tblUsers u JOIN tblTeams t on u.TeamID = t.TeamID where t.TeamName = ?";
	
		Connection connection = dbConnection.connect();
		ResultSet result;
		
		this.m_teamName = teamName;
		
		try {
			PreparedStatement prepStatement = connection.prepareStatement(teamQry);
			prepStatement.setString(1, teamName);
			result = prepStatement.executeQuery();
			while(result.next())
			{
				m_scoredReported = result.getBoolean("ScoreReported");
				User user = new User(result.getString("FirstName"),result.getString("LastName"),result.getString("UserName"),result.getString("Email"));
				m_teamMembers.add(user);
			}
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public void LoadTeamData(int teamID)
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String teamQry = "select TeamName,ScoreReported, UserName, FirstName, LastName, Email from tblUsers u JOIN tblTeams t on u.TeamID = t.TeamID where t.TeamID = ?";
	
		Connection connection = dbConnection.connect();
		ResultSet result;
		
		try {
			PreparedStatement prepStatement = connection.prepareStatement(teamQry);
			prepStatement.setInt(1, teamID);
			result = prepStatement.executeQuery();
			while(result.next())
			{
				m_teamName = result.getString("TeamName");
				m_scoredReported = result.getBoolean("ScoreReported");
				User user = new User(result.getString("FirstName"),result.getString("LastName"),result.getString("UserName"),result.getString("Email"));
				m_teamMembers.add(user);
			}
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void SetScoreReported(Boolean r)
	{
		m_scoredReported = r;
	}
	
	public Boolean GetScoreReported()
	{
		return m_scoredReported;
	}
	
	public String WinsToString()
	{
		return String.valueOf(m_wins);
	}
	
	public String LossesToString()
	{
		return String.valueOf(m_losses);
	}
	
	public String ScoreToString()
	{
		return String.valueOf(m_score);
	}
	
	@Override
	public String toString()
	{
		String returnStr = this.GetTeamName();
		for(User u : this.GetAllTeamMembers())
		{
			returnStr += "\n" + u.GetUsername();
		}
		return returnStr;
	}

		 
	 
	 
	 
 }
	
	
	
