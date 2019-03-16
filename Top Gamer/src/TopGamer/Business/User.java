/**
 * User class definition
 * 
 * 
 * @author Bryan 
 */

package TopGamer.Business;

public class User 
{
	public String m_firstName;
	public String m_lastName;
	public String m_email;
	public String m_username;
	public String m_password;
	
	/**
	 * User constructor
	 * 
	 * Sets all members to default value: N/A
	 */
	public User() {
		
		m_firstName = "N/A";
		m_lastName = "N/A";
		m_email = "N/A";
		m_username = "N/A";
		m_password = "N/A";
	}
	
	public void SetFirstName(String firstName)
	{
		m_firstName = firstName;
	}
	
	public String GetFirstName()
	{
		return m_firstName;
	}
	
	public void SetLastName(String lastName)
	{
		m_lastName = lastName;
	}
	
	public String GetLastName()
	{
		return m_lastName;
	}
	
	public void SetEmail(String email)
	{
		m_email = email;
	}
	
	public String SetEmail()
	{
		return m_email;
	}
	
	public void SetUsername(String username)
	{
		m_username = username;
	}
	
	public String GetUsername()
	{
		return m_username;
	}
	
	
	
	
}
