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
		ResultSet result;
		String platformQry = "Select p.PlatformID,PlatformName from tblPlatform p JOIN tblGames g on p.PlatformID = g.PlatformID where g.GameID = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(platformQry);
			preparedStatement.setInt(1, gameID);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				this.m_platformID = result.getInt("PlatformID");
				this.m_platformName = result.getString("PlatformName");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
}
	
	public void Load(int platformID)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		ResultSet result;
		String platformQry = "Select PlatformID,PlatformName from tblPlatform where PlatformID = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(platformQry);
			preparedStatement.setInt(1, platformID);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				this.m_platformID = result.getInt("PlatformID");
				this.m_platformName = result.getString("PlatformName");
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
}
