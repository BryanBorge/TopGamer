package TopGamer.Business;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Leaderboard {
	// asfnskfn

	private ArrayList<Team> m_teams;
	
	/**
	 * Load leader board data
	 */
	public void LoadLeaderboardData() 
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String lbQry = "SELECT TeamName, Wins, Losses from tblTeams team "
				+ "JOIN tblTournaments t on team.TournamentID = t.TournamentID where t.GameID = 3";
		Connection connection = dbConnection.connect();
		Statement statement = null;
		ResultSet result;
	
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(lbQry);
			
			while(result.next())
			{
				m_teams.add(new Team(result.getString("TeamName"), result.getInt("Wins"), result.getInt("Losses")));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
}
