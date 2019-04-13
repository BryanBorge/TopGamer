package TopGamer.Business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Platform 
{
	private String m_platformName;
	
	/**
	 * Platform default constructor
	 * 
	 * Sets platform member to default value
	 */
	public Platform()
	{
		m_platformName = "N/A";
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
		return m_platformName.toString();
	}
	
	public void LoadPlatformData(int id)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		Statement statement = null;
		ResultSet result;
		
		String platformQry = "Select PlatformName from tblPlatform where PlatformID = " + id;
		String dbPlatformName = null;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(platformQry);
			while(result.next())
			{
				dbPlatformName = result.getString("PlatformName");
			}
			this.m_platformName = dbPlatformName;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
