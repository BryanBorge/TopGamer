package TopGamer.Business;

import java.util.ArrayList;

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
		m_platform.SetPlatform(platform);
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
	 * GetGameName
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
		m_platform.SetPlatform(platform);
	}
	
	
	
}
