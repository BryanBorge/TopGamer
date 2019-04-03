package TopGamer.Presentation;

import javax.xml.transform.Templates;

import TopGamer.Business.*;
import javafx.application.Application;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Application.launch(TopGamerGUI.class,args);
		
		/*
		Tournament testT = new Tournament();
		SQLConnection se = new SQLConnection();
		se.connect();
		testT = se.LoadTournamentData();	
		System.out.println(testT.GetTournamentName());
	*/
	}
}
