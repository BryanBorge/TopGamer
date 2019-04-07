package TopGamer.Presentation;

import java.util.ArrayList;

import javax.xml.transform.Templates;

import TopGamer.Business.*;
import javafx.application.Application;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Application.launch(TopGamerGUI.class,args);
		
		/*
		Tournament test = new Tournament();
		SQLConnection sqlConnection = new SQLConnection();
		test = sqlConnection.LoadTournamentData();
		System.out.println(test.GetID());
		System.out.println(test.GetTournamentName());
		System.out.println(test.GetPrize());
		*/
	}
}
