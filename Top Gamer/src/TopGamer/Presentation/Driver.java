package TopGamer.Presentation;

import java.util.ArrayList;

import javax.xml.transform.Templates;

import TopGamer.Business.*;
import javafx.application.Application;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Application.launch(TopGamerGUI.class,args);
		
		
		SQLConnection sql = new SQLConnection();
		ArrayList<String> test = new ArrayList<String>();
		test = sql.LoadAllOpenTeams();
		for(String t : test)
		{
			System.out.println(t);
		}
		
	}
}
