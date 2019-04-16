package TopGamer.Business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Platform 
{
	private String m_platformName;
	private int m_platformID;
	
	/**
	 * Platform default constructor
	 * 
	 * Sets platform member to default value
	 */
	public Platform()
	{
		m_platformName = "N/A";
	}
	
	public void SetID(int id)
	{
		m_platformID = id;
	}
	
	public int GetID()
	{
		return m_platformID;
	}
	
	
	/**
	 * SetPlatform
	 * Sets platform member variable
	 * @param platform - Platform value
	 */
	public void SetPlatformName(String platform)
	{
		m_platformName = platform;
	}
	
	/**
	 * GetPlatform
	 * Returns value of platform member
	 * @return value of platform
	 */
	public String GetPlatformName()
	{
		return m_platformName;
	}
	
	public void LoadPlatformData(int gameID)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		Statement statement = null;
		ResultSet result;
		
		String q = "Select p.PlatformID,PlatformName from tblPlatform p JOIN tblGames g on p.PlatformID = g.PlatformID where g.GameID = " + gameID;
		String dbPlatformName = null;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(q);
			while(result.next())
			{
				this.m_platformID = result.getInt("PlatformID");
				this.m_platformName = result.getString("PlatformName");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
