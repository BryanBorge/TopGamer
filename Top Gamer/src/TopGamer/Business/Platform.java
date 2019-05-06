package TopGamer.Business;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

public class Platform 
{
	private String m_platformName;
	//private int m_platformID;
	
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
		return m_platformName;
	}
	
	public void LoadPlatform(String gameName)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		ResultSet result;
		String platformQry = "Select PlatformName from tblPlatform p JOIN tblGames g on p.PlatformID = g.PlatformID where g.GameID = (select GameID from tblGames where GameName = ?)";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(platformQry);
			preparedStatement.setString(1, gameName);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				this.m_platformName = result.getString("PlatformName");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
	public void LoadPlatformData(String platformName)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		ResultSet result;
		String platformQry = "Select PlatformName from tblPlatform where PlatformName = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(platformQry);
			preparedStatement.setString(1, platformName);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				this.m_platformName = result.getString("PlatformName");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
