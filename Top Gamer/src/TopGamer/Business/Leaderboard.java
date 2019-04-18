package TopGamer.Business;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Leaderboard {
	// asfnskfn

	private ArrayList<Team> m_leaderBoard;
	
	private Team m_team;
	
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
		
		String dbTeamName = null;
		int dbWins = 0; 
		int dbLosses = 0;
		try {
			statement = connection.createStatement();
			result = statement.executeQuery(lbQry);
			
			while(result.next())
			{
				dbTeamName = result.getString("TeamName");
				dbWins = result.getInt("Wins");
				dbLosses = result.getInt("Losses");
			}// ends while result.next() loop
			
			m_team.SetTeamName(dbTeamName);
			m_team.SetWins(dbWins);
			m_team.SetLosses(dbLosses);
		}
		catch(SQLException e){
			e.printStackTrace();
		}// ends try catch for connection query

		
	}// ends load leader board data
	
}// end class Leader board
