/**
 * User class definition
 * 
 * 
 * @author Bryan 
 */

package TopGamer.Business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User 
{
	private String m_firstName;
	private String m_lastName;
	private String m_email;
	private String m_username;
	
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
	}
	
	public User(String fName, String lName, String uName, String email) {
		m_firstName = fName;
		m_lastName = lName;
		m_username = uName;
		m_email = email;
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
	
	/**
	 * Returns true and loads user data if login exists and false otherwise
	 * @param userName From registration form
	 * @param password From registration form
	 * @return true/false
	 * @throws SQLException
	 */
	public boolean Login(String userName, String password) throws SQLException
	{
		
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		Boolean validLogin = false;
		String query = "select * from tblUsers where UserName= \'" + userName + "\' and Password= \'" + password + "\'  ";
		
		Statement statement = null;
		ResultSet result;
		
		String dbUserName = null,dbPassword= null,dbFirstName = null, dbLastName = null,dbEmail = null;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(query);
			while(result.next())
			{
				dbFirstName = result.getString("FirstName");
				dbLastName = result.getString("LastName");
				dbEmail = result.getString("Email");
				dbUserName = result.getString("UserName");
				dbPassword = result.getString("Password");
			}
			if(userName.equals(dbUserName) && password.equals(dbPassword))
			{
				//if successful, load all data to user
				System.out.println("User login successful");
				this.SetFirstName(dbFirstName);
				this.SetLastName(dbLastName);
				this.SetEmail(dbEmail);
				this.SetUsername(dbUserName);
				validLogin = true;
			}
			if(!userName.equals(dbUserName) || !password.equals(dbPassword))
			{
				validLogin = false;
			}
			return validLogin;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	
	public void LoadUserData(String userName) 
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String userQry = "select UserName, FirstName, LastName, Email from tblUsers where UserName = \'" + userName + "\'";
	
		Connection connection = dbConnection.connect();
		Statement statement = null;
		ResultSet result;
		
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(userQry);
			while(result.next())
			{
				this.SetFirstName(result.getString("FirstName"));
				this.SetLastName(result.getString("LastName"));
				this.SetUsername(result.getString("UserName"));
				this.SetEmail(result.getString("Email"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
