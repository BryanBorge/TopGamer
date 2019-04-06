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
		ArrayList<String> users = new ArrayList<String>();
		SQLConnection se = new SQLConnection();
		users = se.LoadAllUsernames();
		for(String s : users)
		{
			System.out.println(s);
		}
		*/
	}
}
