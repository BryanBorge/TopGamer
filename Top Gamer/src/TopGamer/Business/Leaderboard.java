package TopGamer.Business;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.sun.javafx.scene.layout.region.BorderImageWidthConverter;


public class Leaderboard {


	private ArrayList<Team> m_Teams;
	private Team loadTeam;
	
	/**
	 * Leaderboard constructor
	 */
	public Leaderboard()
	{
		m_Teams = new ArrayList<Team>();
	}
	
	public ArrayList<Team> GetTeams()
	{
		return m_Teams;
	}
	
	
	/**
	 * Load leader board data
	 */
	public void LoadLeaderboardData(Tournament t) 
	{
		SQLConnection dbConnection = new SQLConnection();
		
		String lbQry = "SELECT TeamName, Wins, Losses, Score from tblTeams team "
				+ "JOIN tblTournaments t on team.TournamentID = t.TournamentID where t.GameID = ?";
		
		Connection connection = dbConnection.connect();
		ResultSet result;

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(lbQry);
			preparedStatement.setInt(1,t.GetGame().GetID());
			result = preparedStatement.executeQuery();
			
			while(result.next())
			{
				loadTeam = new Team(result.getString("TeamName"),result.getInt("Wins"), result.getInt("Losses"), result.getInt("Score"));
				m_Teams.add(loadTeam);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}	
	}	
}
