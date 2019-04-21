package TopGamer.Business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Game 
{
	private String m_gameName;
	private Platform m_platform;
	private int m_gameID;
	
	/**
	 * Game default constructor 
	 * 
	 * Set name and platform to default values
	 */
	public Game()
	{
		m_gameName = "N/A";
		m_platform = new Platform();
		m_gameID = -1;
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
	
	public void SetID(int i)
	{
		m_gameID = i;
	}
	
	public int GetID() {
		return m_gameID;
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
		ResultSet result;
		String gameQry = "select GameID,GameName from tblGames where GameID =?";
	
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(gameQry);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				m_gameName = result.getString("GameName");
				m_gameID = result.getInt("GameID");
				m_platform.LoadPlatformData(result.getInt("GameID"));
			}	
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
