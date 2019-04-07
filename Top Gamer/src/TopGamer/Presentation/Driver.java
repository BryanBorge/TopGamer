package TopGamer.Presentation;

import java.util.ArrayList;

import javax.xml.transform.Templates;

import TopGamer.Business.*;
import javafx.application.Application;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
	//	Application.launch(TopGamerGUI.class,args);
		
		
		Team test = new Team();
		SQLConnection se = new SQLConnection();
		test = se.LoadTeamData("the Team");
		for(User user : test.GetAllTeamMembers())
		{
			System.out.println(user.GetUsername());
		}
		
	}
}
