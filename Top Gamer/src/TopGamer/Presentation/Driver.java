package TopGamer.Presentation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.transform.Templates;

import TopGamer.Business.*;
import javafx.application.Application;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		//Application.launch(TopGamerGUI.class,args);
		
		
		// Connect to database
        String hostName = "topgamer1.database.windows.net";
        String dbName = "topgamer1";
        String user = "topgamer0505@topgamer1";
        String password = "Password0505";
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;"
            + "hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
        Connection connection = null;
		
		
		try {
			// get connection
			connection = DriverManager.getConnection(url);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("Failed to create connection to database.");
		}
		if (connection != null) 
			System.out.println("Successfully created connection to database.");
		
		
		
		/*
		SQLConnection sql = new SQLConnection();
		ArrayList<String> test = new ArrayList<String>();
		test = sql.LoadAllOpenTeams();
		for(String t : test)
		{
			System.out.println(t);
		}*/
		
	}
}
