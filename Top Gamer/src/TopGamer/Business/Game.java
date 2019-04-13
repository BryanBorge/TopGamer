package TopGamer.Business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Game 
{
	private String m_gameName;
	private Platform m_platform;
	
	/**
	 * Game default constructor 
	 * 
	 * Set name and platform to default values
	 */
	public Game()
	{
		m_gameName = "N/A";
		m_platform = new Platform();
	}
	
	/**
	 * Game constructor with parameters
	 * @param name - Name of the game
	 * @param platform - Name of the platform
	 */
	public Game(String name, String platform)
	{
		m_gameName = name;
		m_platform.SetPlatformName(platform);
	}
	
	/**
	 * SetGameName
	 * Sets gameName member variable
	 * @param name - Name of the game
	 */
	public void SetGameName(String name)
	{
		m_gameName = name;
	}
	
	/**
	 * GetGameNamed
	 * Returns value of gameName member variable
	 * @return gameName
	 */
	public String GetGameName()
	{
		return m_gameName;
	}
	
	/**
	 * SetGamePlatform
	 * Sets platform member variable
	 * @param platform - Platform of the game
	 */
	public void SetGamePlatform(String platform)
	{
		m_platform.SetPlatformName(platform);
	}

	public Platform GetPlatform()
	{
		return m_platform;
	}
	
	public void LoadGameData(int id)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		Statement statement = null;
		ResultSet result;
		
		String gameQry = "select GameName, PlatformID from tblGames where GameID =" + id;
	
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(gameQry);
			while(result.next())
			{
				m_gameName = result.getString("GameName");
				m_platform.LoadPlatformData(result.getInt("PlatformID"));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
