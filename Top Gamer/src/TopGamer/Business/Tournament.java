/**
 * Tournament class definition
 * 
 * @author Talha
 * 
 */

package TopGamer.Business;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.omg.CosNaming._BindingIteratorImplBase;

public class Tournament 
{
	private String m_tournamentName;
	private int m_prize;
	private String m_date;
	private String m_location;
	private int m_bracketSize;
	private int m_teamsJoined;
	private Game m_game;
	private Team m_winningTeam;
	private String m_status;
	private ArrayList<Team> m_teams;
	
	/**
	 *Tournament default constructor
	 * 
	 * sets member variables to default value
	 */
	public Tournament() 
	{
		m_tournamentName = "N/A";
		m_teams = new ArrayList<Team>();
		m_game = new Game();
		m_prize = 0;
		m_date = "0/0/0";
		m_location = "N/A";
		m_bracketSize = 0;
		m_teamsJoined = 0;
		m_winningTeam = new Team();
	}
	
	public void SetDate(String date)
	{
		m_date = date;
	}
	
	public String GetDate() {
		return m_date;
	}
	
	public void SetLocation(String loc) {
		m_location = loc;
	}
	
	public String GetLocation()
	{
		return m_location;
	}
	

	/**
	 * Returns number of teams joined
	 * @return 
	 */
	public int GetTeamsJoined() {
		return m_teamsJoined;
	}
	/**
	 * Setst the number of teams joined
	 * @param n
	 */
	public void SetTeamsJoined(int n) {
		m_teamsJoined = n;
	}
	
	/**
	 * Sets the bracket size
	 * @return
	 */
	public int GetBrackSize() {
		return m_bracketSize;
	}
	
	/**
	 * Returns the brack size
	 * @param b
	 */
	public void SetBrackSize(int b) {
		m_bracketSize = b;
	}
	
	
	/**
	 * Sets prize member variable
	 * @param p
	 */
	public void SetPrize(int p) {
		m_prize = p;
	}
	
	/**
	 * Returns prize member variable
	 * @return
	 */
	public int GetPrize() {
		return m_prize;
	}
	
	/**
	 * Sets TournamentName Member Variable
	 * 
	 * @param TournamentName
	 */
	public void SetTournamentName(String TournamentName) 
	{
		m_tournamentName = TournamentName;
	}

	/**
	 * Gets TournamentName Member Variable
	 * 
	 * @returns TournamentName
	 */
	public String GetTournamentName() 
	{
		return m_tournamentName;
	}

	
	public void SetTeam(Team t)
	{
		m_teams.add(t);
	}
	
	/**
	 * Returns entire teamName arraylist
	 * 
	 * @return teamName arrayList
	 */
	public ArrayList<Team> GetTeams()
	{
		return m_teams;
	}
	
	/**
	 * Returns single team if it exists in the tournament
	 * @param targetTeam = team being searched for
	 * @return team name if found, null otherwise
	 */
	public Team GetTeam(String targetTeam)
	{
		boolean found = false;
		Team foundTeam = new Team();
		for(Team t : m_teams)
		{
			if(t.GetTeamName().equals(targetTeam)) {
				found = true;
				foundTeam = t;
			}
		}
		if(found)
			return foundTeam;
		else
			return null;
	}
	
	public Game GetGame()
	{
		return m_game;
	}
	
	public void SetStatus(String s)
	{
		m_status = s;
	}
	
	public String GetStatus()
	{
		return m_status;
	}
	
	public void SetWinner(Team winner)
	{
		m_winningTeam = winner;
	}
	
	public Team GetWinner()
	{
		return m_winningTeam;
	}
	
	
	/**
	 * Loads in all tournament data
	 * Calls LoadGameData and LoadPlatformData
	 * Will call LoadTeamData when it is written
	 * @param id
	 */
	public void LoadTournamentData(int id)
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String tournamentQry = "select TournamentID,TournamentName, (Select GameName from tblTournaments t JOIN tblGames g on t.GameID = g.GameID where TournamentID = ?) as GameName, " + 
							   "Prize, BracketSize, CONVERT(VARCHAR(10),(Select Date from tblTournaments where TournamentID = ?), 110) as Date, " + 
							   "Location, Winner, Status from tblTournaments where TournamentID = ?";
		
		String countQry = "select count(TournamentID) as num from tblTeams where TournamentID = ?";
		String teamNameQry = "select TeamName from tblTeams teams JOIN tblTournaments t ON teams.TournamentID = t.TournamentID where t.TournamentID = ?";
		
		Team loadTeam;
		m_teams = new ArrayList<Team>();
		Connection connection = dbConnection.connect();		
		ResultSet result;

		String dbGameName = "";
		
		try {
			PreparedStatement prepStatement = connection.prepareStatement(tournamentQry);
			prepStatement.setInt(1, id);
			prepStatement.setInt(2, id);
			prepStatement.setInt(3, id);
			result = prepStatement.executeQuery();
			while(result.next())
			{
				this.SetTournamentName(result.getString("TournamentName"));
				dbGameName = result.getString("GameName");
				this.SetPrize(result.getInt("Prize"));
				this.SetBrackSize(result.getInt("BracketSize"));
				this.SetLocation(result.getString("Location"));
				this.SetDate(result.getString("Date"));
				this.SetStatus(result.getString("Status"));
				this.GetWinner().LoadTeamData(result.getInt("Winner"));
			}
			prepStatement.close();
			this.m_game.LoadGameData(dbGameName);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(countQry);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				this.SetTeamsJoined(result.getInt("num"));
			}
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement preparedStatement= connection.prepareStatement(teamNameQry);
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			while(result.next())
			{
				loadTeam = new Team();
				loadTeam.LoadTeamData(result.getString("TeamName"));
				this.SetTeam(loadTeam);
			}
			preparedStatement.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads usernames of all players not currently on a team into an array for use with autocompletion
	 * @return
	 */
	public ArrayList<String> LoadAllAvailavleUsernames(String currUser)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		PreparedStatement preparedStatement = null;
		String query = "select UserName from tblUsers where TeamID is NULL and PlatformID = (select PlatformID from tblPlatform where PlatformName = ?) and UserName <> ?";
		ArrayList<String> users = new ArrayList<String>();
	
		try {
			if(this.GetGame().GetPlatform().GetPlatformName().equals(this.GetGame().GetPlatform().GetPlatformName()))
			{
				String qry = "select UserName from tblUsers where TeamID is NULL and UserName <> ?";
				preparedStatement = connection.prepareStatement(qry);
				preparedStatement.setString(1, currUser);
			}
			else {
				preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, this.GetGame().GetPlatform().GetPlatformName());
				preparedStatement.setString(2, currUser);
			}
			
			ResultSet result = preparedStatement.executeQuery();
			while(result.next())
			{
				users.add(result.getString("UserName"));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return users;
	}
	
	public void AddTeamToTournament(String teamName)
	{
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		String addTeamQuery = "Update tblTeams set TournamentID = (select TournamentID from tblTournaments where TournamentName = ?) where TeamName = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(addTeamQuery);
			preparedStatement.setString(1, this.GetTournamentName());
			preparedStatement.setString(2, teamName);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Returns the registered teams for a tournament 
	 * @param tournament id
	 * @return the registerd teams for the tournament
	 */
	 public ArrayList<Team> ViewRegisterdTeams(String tournamentName) {
		 
		 SQLConnection dbConnection = new SQLConnection();
		 String teamNameQry = "select DISTINCT TeamName from tblTeams team JOIN tblTournaments t ON team.TournamentID = t.TournamentID " + 
				 			  "JOIN tblUsers u on u.TeamID = team.TeamID where t.TournamentID = (select TournamentID from tblTournaments where TournamentName = ?)";
		 
		 Connection connection = dbConnection.connect();
		 ResultSet result;
		 
		 ArrayList <Team>  returnTeam = new ArrayList<Team>();
		 Team loadTeam;
		
		 try {
			 PreparedStatement preparedStatement = connection.prepareStatement(teamNameQry);
			 preparedStatement.setString(1, tournamentName);
			 result=preparedStatement.executeQuery();
			 while(result.next())
			 {
				loadTeam = new Team();
				loadTeam.SetTeamName(result.getString("TeamName"));
				loadTeam.LoadTeamData(result.getString("TeamName"));
				returnTeam.add(loadTeam);
			 } 
			 preparedStatement.close();
		 } catch (SQLException e) {
				 e.printStackTrace();
			 }
		 
		 return returnTeam;
	 }
	 
	 public void CreateTeam(String TeamName, String user1, String user2, String user3, String user4)
		{
		 	SQLConnection sqlConnection = new SQLConnection();
			Connection connection = sqlConnection.connect();
			String createTeamQuery = "INSERT into tblTeams (TeamName) VALUES(\'" + TeamName + "\')";
			String addUser1 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user1 + "\'";
			String addUser2 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user2 + "\'";
			String addUser3 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user3 + "\'";
			String addUser4 = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = \'" + TeamName + "\') where UserName = \'" + user4 + "\'";
			
			try {
				Statement statement = connection.createStatement();
				statement.executeUpdate(createTeamQuery);
				statement.executeUpdate(addUser1);
				statement.executeUpdate(addUser2);
				statement.executeUpdate(addUser3);
				statement.executeUpdate(addUser4);
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	 
	 public void JoinTeam(String user, String teamName)
		{
		 	SQLConnection sqlConnection = new SQLConnection();
			String joinTeamQry = "Update tblUsers set TeamID = (select TeamID from tblTeams where TeamName = ?) where UserName = ?";
			Connection connection = sqlConnection.connect();
			try {
				PreparedStatement prepStatement = connection.prepareStatement(joinTeamQry);
				prepStatement.setString(1, teamName);
				prepStatement.setString(2, user);
				prepStatement.executeUpdate();
				prepStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
	 
	 public ArrayList<String>LoadAllOpenTeams()
		{
		 	SQLConnection sqlConnection = new SQLConnection();
		 	Connection connection = sqlConnection.connect();
		 	
			String query = "select TeamName, count(t.TeamID) as num from tblUsers u JOIN tblTeams t on u.TeamID = t.TeamID where t.TournamentID = (select TournamentID from tblTournaments where TournamentName = ?) group by TeamName HAVING count(t.TeamID) < 4";
			ArrayList<String> openTeams = new ArrayList<String>();
	
			ResultSet result;
		
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				preparedStatement.setString(1, this.GetTournamentName());
				result = preparedStatement.executeQuery();
				while(result.next())
				{
					openTeams.add(result.getString("TeamName"));
				}
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return openTeams;
		}
	 
	 public void ReportScore(double totalPoints, Team team)
		{
			SQLConnection dbConnection = new SQLConnection();
			
			String scoreQry = "update tblTeams set Score = ?, ScoreReported = 1 where TeamID = (select TeamID from tblTeams where TeamName = ?)";
			
			Connection connection = dbConnection.connect();
			
			try {
				PreparedStatement prepStatement = connection.prepareStatement(scoreQry);
				prepStatement.setDouble(1, totalPoints);
				prepStatement.setString(2, team.GetTeamName());
				int rowsAffected = prepStatement.executeUpdate();
				prepStatement.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			for(Team t : m_teams)
			{
				if(t.GetTeamName().equals(team.GetTeamName()))
				{
					t.SetScoreReported(true);
				}
			}
			
			if(totalPoints == 100)
				CloseTournament(team);
			
			
			
		}

	 public void ReportScore(String winnerFirstGame, String winnerSecondGame, String winnerFinal)
	 {
		 ArrayList<Team> winners = new ArrayList<>();
		 Team winningTeam = new Team();
		 
		if(m_teams.get(0).GetTeamName().equals(winnerFirstGame))
		{
			m_teams.get(0).SetWins(m_teams.get(0).GetWins() + 1);
			winners.add(m_teams.get(0));
			m_teams.get(1).SetLosses(m_teams.get(1).GetLosses() + 1);
		}
		
		if(m_teams.get(1).GetTeamName().equals(winnerFirstGame))
		{
			m_teams.get(1).SetWins(m_teams.get(1).GetWins() + 1);
			winners.add(m_teams.get(1));
			m_teams.get(0).SetLosses(m_teams.get(0).GetLosses() + 1);
		}
		
		if(m_teams.get(2).GetTeamName().equals(winnerSecondGame))
		{
			m_teams.get(2).SetWins(m_teams.get(2).GetWins() + 1);
			winners.add(m_teams.get(2));
			m_teams.get(3).SetLosses(m_teams.get(3).GetLosses() + 1);
		}
		
		if(m_teams.get(3).GetTeamName().equals(winnerSecondGame))
		{
			m_teams.get(3).SetWins(m_teams.get(3).GetWins() + 1);
			winners.add(m_teams.get(3));
			m_teams.get(2).SetLosses(m_teams.get(2).GetLosses() + 1);
		}
		
		for(Team team : winners)
		{
			if(team.GetTeamName().equals(winnerFinal))
			{
				team.SetWins(team.GetWins() + 1);
				winningTeam = team;
			}
			else
				team.SetLosses(team.GetLosses() + 1);
		}
		
		String updateTeamScore = "Update tblTeams set Wins = ?, Losses = ? where TeamName = ?";
		String updateTournamentWinner = "Update tblTournaments set Winner = (select TeamID from tblTeams where TeamName = ?), Status = ? where TournamentID = (select TournamentID from tblTournaments where TournamentName = ?";
		
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateTeamScore);
			
			for(Team teams : m_teams)
			{
				preparedStatement.setInt(1,teams.GetWins());
				preparedStatement.setInt(2,teams.GetLosses());
				preparedStatement.setString(3,teams.GetTeamName());
				int rowsAff = preparedStatement.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(updateTournamentWinner);
			preparedStatement.setString(1, winningTeam.GetTeamName());
			preparedStatement.setString(2,"CLOSED");
			preparedStatement.setString(3, this.GetTournamentName());
			int rowsAff = preparedStatement.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	 }
	 
	 public void CloseTournament(Team winningTeam)
	 {
		 String closeQry = "Update tblTournaments set Status = ?, Winner = (select TeamID from tblTeams where TeamName = ?) where TournamentID = (select TournamentID from tblTournaments where TournamentName = ?)";
		 String updateTeamQry = "Update tblTeams set Wins = ? where TeamID = (select TeamID from tblTeams where TeamName = ?)";
		 
		 
		 SQLConnection sqlConnection = new SQLConnection();
			Connection connection = sqlConnection.connect();
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(closeQry);
				
	
				preparedStatement.setString(1,"CLOSED");
				preparedStatement.setString(2,winningTeam.GetTeamName());
				preparedStatement.setString(3, this.GetTournamentName());
				int rowsAff = preparedStatement.executeUpdate();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				PreparedStatement preparedStatement = connection.prepareStatement(updateTeamQry);
		
				preparedStatement.setInt(1,winningTeam.GetWins());
				preparedStatement.setString(2, winningTeam.GetTeamName());
				int rowsAff = preparedStatement.executeUpdate();
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
	 }
}

 


	