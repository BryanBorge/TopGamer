/**
 * User class definition
 * 
 * 
 * @author Bryan 
 */

package TopGamer.Business;

import java.awt.dnd.DnDConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.microsoft.sqlserver.jdbc.SQLServerException;

public class User 
{
	private String m_firstName;
	private String m_lastName;
	private String m_email;
	private String m_username;
	private Team m_Team;
	private Platform m_platform;
	
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
		m_platform = new Platform();
		m_Team = new Team();
	}
	
	public User(String username) {
		m_username=username;
		
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
	 * SetGamePlatform
	 * Sets platform member variable
	 * @param platform - Platform of the game
	 */
	public void SetPlatform(String platform)
	{
		m_platform.SetPlatformName(platform);
	}

	public Platform GetPlatform()
	{
		return m_platform;
	}
	
	public Team GetTeam()
	{
		return m_Team;
	}
	
	
	/**
	 * Returns true and loads user data if login exists and false otherwise
	 * @param userName From registration form
	 * @param password From registration form
	 * @return true/false
	 * @throws SQLException
	 */
	public boolean Login(String userName, String password) throws SQLServerException
	{
		
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		Boolean validLogin = false;
		String query = "select UserName, Password from tblUsers where UserName= ? and Password= ?";
		
		ResultSet result;
		
		String dbUserName = null,dbPassword= null;
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				dbUserName = result.getString("UserName");
				dbPassword = result.getString("Password");
			}
			if(userName.equals(dbUserName) && password.equals(dbPassword))
			{
				//if successful, load all data to user
				System.out.println("User login successful");
				this.SetUsername(dbUserName);
				this.LoadUserData(dbUserName);
				validLogin = true;
			}
			if(!userName.equals(dbUserName) || !password.equals(dbPassword))
			{
				validLogin = false;
			}
			preparedStatement.close();
			return validLogin;
			
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public void LoadUserData(String userName) 
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String userQry = "select FirstName, LastName, Email, PlatformID from tblUsers where UserName = ?";
		//String teamQry = "select t.TeamID as ID from tblUsers u join tblTeams t on t.TeamID = u.TeamID where UserName = ?";
		String teamQry = "Select TeamID from tblUsers where UserName = ?";
		
		Connection connection = dbConnection.connect();
		ResultSet result;
		int dbPlatformID = -1;
		
		try {
			PreparedStatement prepStatement = connection.prepareStatement(userQry);
			prepStatement.setString(1, userName);
			result = prepStatement.executeQuery();
			while(result.next())
			{
				this.SetFirstName(result.getString("FirstName"));
				this.SetLastName(result.getString("LastName"));
				this.SetEmail(result.getString("Email"));
				dbPlatformID = result.getInt("PlatformID");
			}
			this.m_platform.Load(dbPlatformID);
			prepStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			PreparedStatement prepStatement = connection.prepareStatement(teamQry);
			prepStatement.setString(1, userName);
			result = prepStatement.executeQuery();
			while(result.next())
			{
				m_Team = new Team();
				m_Team.LoadTeamData(result.getInt("TeamID"));
			}
			prepStatement.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
