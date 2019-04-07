/**
 * User class definition
 * 
 * 
 * @author Bryan 
 */

package TopGamer.Business;

public class User 
{
	private String m_firstName;
	private String m_lastName;
	private String m_email;
	private String m_username;
	private String m_password;
	
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
	
	public User(String fName, String lName, String uName) {
		m_firstName = fName;
		m_lastName = lName;
		m_username = uName;
	}
	
	
	/**
	 * Sets first name member
	 * @param firstName - value from gui
	 */
	public void SetFirstName(String firstName)
	{
		m_firstName = firstName;
	}
	
	/**
	 * Gets first name member
	 * @return firstName
	 */
	public String GetFirstName()
	{
		return m_firstName;
	}
	
	/**
	 * Set last name member
	 * @param lastName - value from gui
	 */
	public void SetLastName(String lastName)
	{
		m_lastName = lastName;
	}
	
	/**
	 * Gets last name member
	 * @return lastname
	 */
	public String GetLastName()
	{
		return m_lastName;
	}
	
	/**
	 * Sets email member
	 * @param email - value from gui
	 */
	public void SetEmail(String email)
	{
		m_email = email;
	}
	
	/**
	 * Gets email member
	 * @return email
	 */
	public String GetEmail()
	{
		return m_email;
	}
	
	/**
	 * Sets username member
	 * @param username - value from gui
	 */
	public void SetUsername(String username)
	{
		m_username = username;
	}
	
	/**
	 * Gets user name member
	 * @return username
	 */
	public String GetUsername()
	{
		return m_username;
	}
	
	
	
	
}
