package TopGamer.Business;



public class Platform 
{
	enum PlatformType
	{
		NOTAVAILABLE,PC,XBOX,PS4
	}
	
	private PlatformType m_platform;
	
	public Platform()
	{
		m_platform = PlatformType.valueOf("NOTAVAILABLE");
	}
	
	public void SetPlatform(String platform)
	{
		m_platform = PlatformType.valueOf(platform);
	}
	
	public String GetPlatform()
	{
		return m_platform.toString();
	}
}
