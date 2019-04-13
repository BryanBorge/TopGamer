package TopGamer.Presentation;

import java.awt.event.MouseWheelEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.xml.transform.Templates;

import TopGamer.Business.*;
import javafx.application.Application;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Application.launch(TopGamerGUI.class,args);
		
		/*Team test = new Team();
		test.LoadTeamData("Test");
		for(User u : test.GetAllTeamMembers()) {
			System.out.println(u.GetUsername());*/
		}
		
	}

