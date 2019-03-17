package TopGamer.Business;



public class Platform 
{
	/**
	 * Enum definition for PlatformType
	 * @author Bryan
	 *
	 */
	enum PlatformType
	{
		NOTAVAILABLE,PC,XBOX,PS4
	}
	
	
	private PlatformType m_platform;
	
	/**
	 * Platform default constructor
	 * 
	 * Sets platform member to default value
	 */
	public Platform()
	{
		m_platform = PlatformType.valueOf("NOTAVAILABLE");
	}
	
	/**
	 * SetPlatform
	 * Sets platform member variable
	 * @param platform - Platform value
	 */
	public void SetPlatform(String platform)
	{
		m_platform = PlatformType.valueOf(platform);
	}
	
	/**
	 * GetPlatform
	 * Returns value of platform member
	 * @return value of platform
	 */
	public String GetPlatform()
	{
		return m_platform.toString();
	}
}
