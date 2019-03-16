package TopGamer.Presentation;


import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.sql.SQLException;

import TopGamer.Business.*;


public class TopGamerGUI extends Application
{
	Stage window,gameStage;
	Scene welcomeScene,loginScene, registerScene, mainDashboardScene;
	Scene fortniteScene, apexScene, fifaScene, codScene, haloScene;
	
	//Used on welcome scene
	Button btnLogin, btnRegister, btnContinue;
	
	//Used for user login - DESIGNED BUT NOT WORKING
	Label lblLoginName, lblLoginPass;
	TextField txtLoginName;
	PasswordField txtLoginPass;
	Button btnUserLogin,btnBackToWelcome;
	Button btnSignUp;
	
	//Registration
	Label lblFName;
	Label lblLName;
	Label lblEmail;
	Label lblUserName;
	Label lblPass;
	TextField txtFName;
	TextField txtLName;
	TextField txtEmail;
	TextField txtUserName;
	TextField txtPass;

	Label lblValidPass;
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{		
		window = primaryStage;
		window.setResizable(false);
		
		CreateWelcomeScene();
		CreateLoginScene();
		CreateRegisterScene();
		CreateMainDashboard();
		CreateFortniteScene();
		CreateApexScene();
		CreateCODScene();
		CreateFifaScene();
		CreateHaloScene();
		
		btnRegister.setOnAction(e -> OpenRegisterScene());
		btnLogin.setOnAction( e -> OpenLoginScene());
		//btnBackToWelcome.setOnAction(e -> BackToWelcomeScreen());
		btnContinue.setOnAction(e -> OpenMainDashboard());
		
		window.setScene(welcomeScene);
		window.show();
		
	}
	
	//Welcome Screen
	void BackToWelcomeScreen()
	{
		window.setScene(welcomeScene);
	}
	void CreateWelcomeScene()
	{
		AnchorPane ap = new AnchorPane();
		
//		ImageView imageView = new ImageView();
//		Image img = new Image("TopGamer/Presentation/stadium.jpg");
//		imageView.setImage(img);
		
		btnLogin = new Button("Login");
		btnLogin.setPrefWidth(173.0);
		btnLogin.setPrefHeight(43.0);
		btnRegister = new Button("Register");
		btnRegister.setPrefWidth(173.0);
		btnRegister.setPrefHeight(43.0);
		btnContinue = new Button("Continue without registering");
		btnContinue.setPrefWidth(600.0);
		btnContinue.setPrefHeight(20.0);
		
		AnchorPane.setLeftAnchor(btnLogin, 214.0);
		AnchorPane.setTopAnchor(btnLogin, 110.0);
		
		AnchorPane.setLeftAnchor(btnRegister,214.0);
		AnchorPane.setTopAnchor(btnRegister, 188.0);
		
		AnchorPane.setLeftAnchor(btnContinue,0.0);
		AnchorPane.setTopAnchor(btnContinue, 374.0);
		
		ap.getChildren().addAll(btnLogin,btnRegister,btnContinue);

		welcomeScene = new Scene(ap,600,400);
	
	}
	
	
	//Login
	void OpenLoginScene()
	{
		window.setScene(loginScene);
	}
	void CreateLoginScene()
	{
		AnchorPane ap = new AnchorPane();
		
		txtLoginName = new TextField();
		txtLoginName.setPrefWidth(160.0);
		txtLoginName.setPrefHeight(25.0);
		txtLoginName.setPromptText("Username or email address");
		txtLoginPass = new PasswordField();
		txtLoginPass.setPrefWidth(160.0);
		txtLoginPass.setPrefHeight(25.0);
		txtLoginPass.setPromptText("Password");
		btnUserLogin = new Button("Login");
		btnUserLogin.setPrefWidth(160.0);
		btnUserLogin.setPrefHeight(25.0);
		btnUserLogin.setOnAction(e-> Login());
		Button btnReturn = new Button("<-");
		btnReturn.setOnAction(e -> window.setScene(welcomeScene));
		btnSignUp = new Button("Dont have an account? Sign up");
		btnSignUp.setPrefWidth(160.0);
		btnSignUp.setPrefHeight(25.0);
		btnSignUp.setFont(Font.font(10));
		btnSignUp.setOnAction(e -> OpenRegisterScene());
		
		lblValidPass = new Label();
		
		AnchorPane.setLeftAnchor(txtLoginName, 226.0);
		AnchorPane.setTopAnchor(txtLoginName, 134.0);
		
		AnchorPane.setLeftAnchor(txtLoginPass, 226.0);
		AnchorPane.setTopAnchor(txtLoginPass, 188.0);
		
		AnchorPane.setLeftAnchor(lblValidPass, 226.0);
		AnchorPane.setTopAnchor(lblValidPass, 225.0);
		
		AnchorPane.setLeftAnchor(btnUserLogin, 226.0);
		AnchorPane.setTopAnchor(btnUserLogin, 246.0);
		
		AnchorPane.setLeftAnchor(btnUserLogin, 226.0);
		AnchorPane.setTopAnchor(btnUserLogin, 246.0);
		
		AnchorPane.setLeftAnchor(btnUserLogin, 226.0);
		AnchorPane.setTopAnchor(btnUserLogin, 246.0);
		
		AnchorPane.setLeftAnchor(btnSignUp, 226.0);
		AnchorPane.setTopAnchor(btnSignUp, 278.0);
		
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		
		ap.getChildren().addAll(txtLoginName,txtLoginPass,btnUserLogin,lblValidPass,btnSignUp,btnReturn);

		loginScene = new Scene(ap,600,400);
	}
	void Login()
	{
		if(!LoginValidation())
			return;
		else
		{
			boolean Valid = false;
		    SQLConnection dbLogin = new SQLConnection();
					try
					{
						Valid = dbLogin.Login(txtLoginName.getText(), txtLoginPass.getText());
					}
						catch(SQLException e)
					{
						
					}
			if(Valid)
				OpenMainDashboard();
			else
			{
				System.out.println("Try again");
			}
		}
	}
	
	//The coloring of the textboxes needs to be moved to a different event
	boolean LoginValidation()
	{
		lblValidPass.setText(null);
		
		if((txtLoginName.getText() == ""))
		{
			System.out.println("Username cannot be blank");
			txtLoginName.setStyle(".text-field.error  -fx-text-box-border: red ; -fx-focus-color: red ;}");
			return false;
		}
		else
			txtLoginName.setStyle(".text-field.error  -fx-text-box-border: green ; -fx-focus-color: green ;}");
		
		if((txtLoginPass.getText() == ""))
		{
			System.out.println("Password cannot be blank");
			txtLoginPass.setStyle(".text-field.error  -fx-text-box-border: red ; -fx-focus-color: red ;}");
			return false;
		}
		else
			txtLoginPass.setStyle(".text-field.error  -fx-text-box-border: green ; -fx-focus-color: green ;}");
		
		if(!txtLoginName.getText().matches("[a-zA-Z]*"))
		{
			System.out.println("Username must be a string");
			txtLoginName.setStyle(".text-field.error  -fx-text-box-border: red ; -fx-focus-color: red ;}");
			return false;
		}
		else
			txtLoginName.setStyle(".text-field.error  -fx-text-box-border: green ; -fx-focus-color: green ;}");
		if(!txtLoginPass.getText().matches("[a-zA-Z]*"))
		{
			System.out.println("Password must be a string");
			lblValidPass.setText("Password must be a string");
			lblValidPass.setTextFill(Color.RED);
			txtLoginPass.setStyle(".text-field.error  -fx-text-box-border: red ; -fx-focus-color: red ;}");
			return false;		
		}
		else
			txtLoginPass.setStyle(".text-field.error  -fx-text-box-border: green ; -fx-focus-color: green ;}");
	 
		
		txtLoginName.setStyle(".text-field.error  -fx-text-box-border: green ; -fx-focus-color: green ;}");
		txtLoginPass.setStyle(".text-field.error  -fx-text-box-border: green ; -fx-focus-color: green ;}");
		return true;
		
	
		
		
	}

	//Registration
	void OpenRegisterScene()
	{
		window.setScene(registerScene);
	}
	void CreateRegisterScene()
	{
		AnchorPane ap = new AnchorPane();
		Button btnReturn = new Button("<-");
		btnReturn.setOnAction(e ->BackToWelcomeScreen());
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		
		lblFName = new Label("First Name");
		lblLName = new Label("Last Name");
		lblEmail = new Label("Email Address");
		lblUserName = new Label("Username");
		lblPass = new Label("Password");
		txtFName = new TextField();
		txtLName = new TextField();
		txtEmail = new TextField();
		txtUserName = new TextField();
		txtPass = new TextField();
		btnSignUp = new Button("Sign up");
		btnSignUp.setOnAction(e -> RegisterUser());
		
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		
		AnchorPane.setLeftAnchor(lblFName, 226.0);
		AnchorPane.setTopAnchor(lblFName, 47.0);
		AnchorPane.setLeftAnchor(txtFName, 226.0);
		AnchorPane.setTopAnchor(txtFName, 70.0);
		
		AnchorPane.setLeftAnchor(lblLName, 226.0);
		AnchorPane.setTopAnchor(lblLName, 108.0);
		AnchorPane.setLeftAnchor(txtLName, 226.0);
		AnchorPane.setTopAnchor(txtLName, 125.0);
		

		AnchorPane.setLeftAnchor(lblEmail, 226.0);
		AnchorPane.setTopAnchor(lblEmail, 164.0);
		AnchorPane.setLeftAnchor(txtEmail, 226.0);
		AnchorPane.setTopAnchor(txtEmail, 181.0);
		
		AnchorPane.setLeftAnchor(lblUserName, 226.0);
		AnchorPane.setTopAnchor(lblUserName, 221.0);
		AnchorPane.setLeftAnchor(txtUserName, 226.0);
		AnchorPane.setTopAnchor(txtUserName, 238.0);
		
		AnchorPane.setLeftAnchor(lblPass, 226.0);
		AnchorPane.setTopAnchor(lblPass, 279.0);
		AnchorPane.setLeftAnchor(txtPass, 226.0);
		AnchorPane.setTopAnchor(txtPass, 296.0);
		
		AnchorPane.setLeftAnchor(btnSignUp, 226.0);
		AnchorPane.setTopAnchor(btnSignUp, 344.0);
		
		ap.getChildren().addAll(btnReturn,lblFName,txtFName,txtLName,txtEmail,txtUserName,txtPass,lblLName,lblEmail,lblUserName,lblPass,btnSignUp);
		registerScene = new Scene(ap,600,400);
	}
	void RegisterUser()
	{
		SQLConnection s = new SQLConnection();
		s.AddUser(txtFName.getText(), txtLName.getText(), txtEmail.getText(), txtUserName.getText(), txtPass.getText());
	}
	
	//Main Dashboard
	void OpenMainDashboard()
	{
		 window.setScene(mainDashboardScene);
	}	
	void CreateMainDashboard()
	{
		//use this vbox to only make games scroll
		VBox test = new VBox();
		test.setStyle("-fx-focus-color: transparent;");
	//	test.setStyle("-fx-focus-color: transparent;");
		ScrollPane mainScroll = new ScrollPane();
		mainScroll.setPannable(true);
		mainScroll.setFitToHeight(true);
		mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		AnchorPane ap = new AnchorPane();
		Button btnReturn = new Button("<-");
		btnReturn.setOnAction(e-> window.setScene(welcomeScene));
		Label lblFeaturedGames = new Label("Featured Games");
		
		//Load images and set their size
		ImageView fortniteLogo = new ImageView(new Image("TopGamer/Presentation/Images/Fortnite.jpg"));
		fortniteLogo.setFitWidth(133);
		fortniteLogo.setFitHeight(202);
		ImageView apexLogo = new ImageView(new Image("TopGamer/Presentation/Images/Apex.jpg"));
		apexLogo.setFitWidth(133);
		apexLogo.setFitHeight(202);
		ImageView codLogo = new ImageView(new Image("TopGamer/Presentation/Images/Cod.jpg"));
		codLogo.setFitWidth(133);
		codLogo.setFitHeight(202);
		ImageView fifaLogo = new ImageView(new Image("TopGamer/Presentation/Images/fifa.jpg"));
		fifaLogo.setFitWidth(133);
		fifaLogo.setFitHeight(202);
		ImageView haloLogo = new ImageView(new Image("TopGamer/Presentation/Images/Halo.jpg"));
		haloLogo.setFitWidth(133);
		haloLogo.setFitHeight(202);
		
		//mouse click events for game pictures
		fortniteLogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> { fortniteScene.setCursor(Cursor.DEFAULT); OpenFortniteScene();  });
		fortniteLogo.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event ->{mainDashboardScene.setCursor(Cursor.HAND);});
		fortniteLogo.addEventHandler(MouseEvent.MOUSE_EXITED, event ->{ mainDashboardScene.setCursor(Cursor.DEFAULT);});
		
		apexLogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {apexScene.setCursor(Cursor.DEFAULT); OpenApexScene();});
		apexLogo.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event ->{mainDashboardScene.setCursor(Cursor.HAND);});
		apexLogo.addEventHandler(MouseEvent.MOUSE_EXITED, event ->{ mainDashboardScene.setCursor(Cursor.DEFAULT);});
		
		codLogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {codScene.setCursor(Cursor.DEFAULT); OpenCodScene();});
		codLogo.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event ->{mainDashboardScene.setCursor(Cursor.HAND);});
		codLogo.addEventHandler(MouseEvent.MOUSE_EXITED, event ->{ mainDashboardScene.setCursor(Cursor.DEFAULT);});
		
		fifaLogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {fifaScene.setCursor(Cursor.DEFAULT); OpenFifaScene();});
		fifaLogo.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event ->{mainDashboardScene.setCursor(Cursor.HAND);});
		fifaLogo.addEventHandler(MouseEvent.MOUSE_EXITED, event ->{ mainDashboardScene.setCursor(Cursor.DEFAULT);});
		
		haloLogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {haloScene.setCursor(Cursor.DEFAULT); OpenHaloScene();});
		haloLogo.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event ->{mainDashboardScene.setCursor(Cursor.HAND);});
		haloLogo.addEventHandler(MouseEvent.MOUSE_EXITED, event ->{ mainDashboardScene.setCursor(Cursor.DEFAULT);});
		
		//Set image position on anchor pane
		//AnchorPane.setTopAnchor(lblFeaturedGames, 14.0);
		//AnchorPane.setLeftAnchor(lblFeaturedGames, 24.0);
		AnchorPane.setTopAnchor(fortniteLogo, 35.0);
		AnchorPane.setLeftAnchor(fortniteLogo, 24.0);
		AnchorPane.setTopAnchor(apexLogo, 35.0);
		AnchorPane.setLeftAnchor(apexLogo, 167.0);
		AnchorPane.setTopAnchor(codLogo, 35.0);
		AnchorPane.setLeftAnchor(codLogo, 308.0);
		AnchorPane.setTopAnchor(fifaLogo, 35.0);
		AnchorPane.setLeftAnchor(fifaLogo, 453.0);
		AnchorPane.setTopAnchor(haloLogo, 35.0);
		AnchorPane.setLeftAnchor(haloLogo, 601.0);
		AnchorPane.setTopAnchor(btnReturn, 300.0);
		//AnchorPane.setLeftAnchor(btnReturn, 35.0);
		
		//add images to anchor pane
		ap.getChildren().addAll(lblFeaturedGames,btnReturn,fortniteLogo,apexLogo,codLogo,fifaLogo,haloLogo);
	
		//add anchor pane to the scroll pane
		mainScroll.setContent(ap);
		
		test.getChildren().addAll(lblFeaturedGames,mainScroll, btnReturn);

		mainDashboardScene = new Scene(test,600,400);
	}
	
	//Fortnite Scene
	void CreateFortniteScene()
	{
		Label lblDesc = new Label("Fortnite Battle Royale is the FREE 100-player PvP mode in Fortnite. One giant map. A battle bus. Fortnite building skills and destructible environments combined with intense PvP combat. The last one standing wins. Available on PC, PlayStation 4, Xbox One & Mac. ");
		lblDesc.setPrefHeight(77);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Fortnite 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Fortnite 4v4 - Boston - Sponsored by Razer");
		Button btnReg1 = new Button("Register");
		Button btnReg2 = new Button("Register");
		Button back = new Button("<-");
		Label lblLeaderboard = new Label("Leaderboards - Coming Soon");
		ImageView fortniteHeader = new ImageView(new Image("TopGamer/Presentation/Images/FortniteHeader.png"));
		fortniteHeader.setFitHeight(70);
		fortniteHeader.setFitWidth(255);
		
		AnchorPane ap = new AnchorPane();
		
		AnchorPane.setTopAnchor(back, 14.0);
		AnchorPane.setLeftAnchor(back, 14.0);
		
		AnchorPane.setTopAnchor(fortniteHeader, 69.0);
		AnchorPane.setLeftAnchor(fortniteHeader, 14.0);
		
		AnchorPane.setTopAnchor(lblDesc, 150.0);
		AnchorPane.setLeftAnchor(lblDesc, 14.0);
		
		AnchorPane.setTopAnchor(lblUpcoming, 247.0);
		AnchorPane.setLeftAnchor(lblUpcoming, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney1, 273.0);
		AnchorPane.setLeftAnchor(lblTourney1, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney2, 310.0);
		AnchorPane.setLeftAnchor(lblTourney2, 14.0);
		
		AnchorPane.setTopAnchor(lblLeaderboard,350.0);
		AnchorPane.setLeftAnchor(lblLeaderboard, 14.0);
		
		AnchorPane.setTopAnchor(btnReg1, 269.0);
		AnchorPane.setLeftAnchor(btnReg1, 336.0);
		
		AnchorPane.setTopAnchor(btnReg2, 306.0);
		AnchorPane.setLeftAnchor(btnReg2, 336.0);
		
		ap.getChildren().addAll(back,fortniteHeader,lblDesc, lblUpcoming, lblTourney1, lblTourney2, lblLeaderboard, btnReg1, btnReg2);
		
		
		fortniteScene = new Scene(ap,600,400);
		
		back.setOnAction(e -> window.setScene(mainDashboardScene));
	}
	void OpenFortniteScene()
	{
		window.setScene(fortniteScene);
	}
	
	//ApexScene
	void CreateApexScene()
	{
		Label lblDesc = new Label("Explore a growing roster of powerful Legends, each with their own unique personality, strengths, and abilities. Choose your Legend, team up with two other players, and combine your unique skills to be the last squad standing. Master your Legend’s abilities, make strategic calls on the fly, and use your team’s strengths to your advantage in vicious 60-player matches.  ");
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Apex 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Apex 4v4 - Boston - Sponsored by Razer");
		Button btnReg1 = new Button("Register");
		Button btnReg2 = new Button("Register");
		Button back = new Button("<-");
		Label lblLeaderboard = new Label("Leaderboard - Coming soon");
		ImageView apexHeader = new ImageView(new Image("TopGamer/Presentation/Images/ApexHeader.png"));
		apexHeader.setFitHeight(70);
		apexHeader.setFitWidth(255);
		
		AnchorPane ap = new AnchorPane();
		
		AnchorPane.setTopAnchor(back, 14.0);
		AnchorPane.setLeftAnchor(back, 14.0);
		
		AnchorPane.setTopAnchor(apexHeader, 69.0);
		AnchorPane.setLeftAnchor(apexHeader, 14.0);
		
		AnchorPane.setTopAnchor(lblDesc, 150.0);
		AnchorPane.setLeftAnchor(lblDesc, 14.0);
		
		AnchorPane.setTopAnchor(lblUpcoming, 247.0);
		AnchorPane.setLeftAnchor(lblUpcoming, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney1, 273.0);
		AnchorPane.setLeftAnchor(lblTourney1, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney2, 310.0);
		AnchorPane.setLeftAnchor(lblTourney2, 14.0);
		
		AnchorPane.setTopAnchor(btnReg1, 269.0);
		AnchorPane.setLeftAnchor(btnReg1, 336.0);
		
		AnchorPane.setTopAnchor(btnReg2, 306.0);
		AnchorPane.setLeftAnchor(btnReg2, 336.0);
		
		AnchorPane.setTopAnchor(lblLeaderboard,350.0);
		AnchorPane.setLeftAnchor(lblLeaderboard, 14.0);
		
		ap.getChildren().addAll(back,apexHeader,lblDesc, lblUpcoming, lblTourney1, lblTourney2,lblLeaderboard, btnReg1, btnReg2);
		
		apexScene = new Scene(ap, 600,400);
		
		back.setOnAction(e -> window.setScene(mainDashboardScene));
	}
	void OpenApexScene()
	{
		window.setScene(apexScene);
	}
	
	//Cod Scene
	void CreateCODScene()
	{
		Label lblDesc = new Label("Apart from the Exo Movement, Advanced Warfare's multiplayer retains certain similarities to previous Call of Duty titles. The Pick 10 system in Black Ops II returns as Pick 13, allowing players to pick weapons, attachments, perks and score-streaks within a total of 13 allocation points.");
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Call Of Duty 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Call Of Duty 4v4 - Boston - Sponsored by Razer");
		Button btnReg1 = new Button("Register");
		Button btnReg2 = new Button("Register");
		Button back = new Button("<-");
		Label lblLeaderboard = new Label("Leaderboard - Coming soon");
		ImageView codHeader = new ImageView(new Image("TopGamer/Presentation/Images/CodHeader.png"));
		codHeader.setFitHeight(70);
		codHeader.setFitWidth(255);
		
		AnchorPane ap = new AnchorPane();
		
		AnchorPane.setTopAnchor(back, 14.0);
		AnchorPane.setLeftAnchor(back, 14.0);
		
		AnchorPane.setTopAnchor(codHeader, 69.0);
		AnchorPane.setLeftAnchor(codHeader, 14.0);
		
		AnchorPane.setTopAnchor(lblDesc, 150.0);
		AnchorPane.setLeftAnchor(lblDesc, 14.0);
		
		AnchorPane.setTopAnchor(lblUpcoming, 247.0);
		AnchorPane.setLeftAnchor(lblUpcoming, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney1, 273.0);
		AnchorPane.setLeftAnchor(lblTourney1, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney2, 310.0);
		AnchorPane.setLeftAnchor(lblTourney2, 14.0);
		
		AnchorPane.setTopAnchor(btnReg1, 269.0);
		AnchorPane.setLeftAnchor(btnReg1, 345.0);
		
		AnchorPane.setTopAnchor(btnReg2, 306.0);
		AnchorPane.setLeftAnchor(btnReg2, 345.0);
		
		AnchorPane.setTopAnchor(lblLeaderboard,350.0);
		AnchorPane.setLeftAnchor(lblLeaderboard, 14.0);
		
		ap.getChildren().addAll(back,codHeader,lblDesc, lblUpcoming, lblTourney1, lblTourney2, lblLeaderboard, btnReg1, btnReg2);
		codScene = new Scene(ap,600,400);
		
		back.setOnAction(e -> window.setScene(mainDashboardScene));
	}
	void OpenCodScene()
	{
		window.setScene(codScene);
	}
	
	//Fifa Scene
	void CreateFifaScene()
	{
		Label lblDesc = new Label("Get ready for the return of the UEFA Champions League with amazing new content in FIFA 19. With special Champions League kits and player items, as well as your choice of a Neymar Jr, Kevin De Bruyne, or Paulo Dybala 10 match loan item for your team, there's no better time to get started in FIFA 19!\r\n"); 
				
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Fifa - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Fifa - Boston - Sponsored by Razer");
		Button btnReg1 = new Button("Register");
		Button btnReg2 = new Button("Register");
		Button back = new Button("<-");
		Label lblLeaderboard = new Label("Leaderboard - Coming Soon");
		ImageView fifaHeader = new ImageView(new Image("TopGamer/Presentation/Images/FifaHeader.png"));
		fifaHeader.setFitHeight(70);
		fifaHeader.setFitWidth(255);
		
		AnchorPane ap = new AnchorPane();
		
		AnchorPane.setTopAnchor(back, 14.0);
		AnchorPane.setLeftAnchor(back, 14.0);
		
		AnchorPane.setTopAnchor(fifaHeader, 69.0);
		AnchorPane.setLeftAnchor(fifaHeader, 14.0);
		
		AnchorPane.setTopAnchor(lblDesc, 150.0);
		AnchorPane.setLeftAnchor(lblDesc, 14.0);
		
		AnchorPane.setTopAnchor(lblUpcoming, 247.0);
		AnchorPane.setLeftAnchor(lblUpcoming, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney1, 273.0);
		AnchorPane.setLeftAnchor(lblTourney1, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney2, 310.0);
		AnchorPane.setLeftAnchor(lblTourney2, 14.0);
		
		AnchorPane.setTopAnchor(btnReg1, 269.0);
		AnchorPane.setLeftAnchor(btnReg1, 336.0);
		
		AnchorPane.setTopAnchor(btnReg2, 306.0);
		AnchorPane.setLeftAnchor(btnReg2, 336.0);
		
		AnchorPane.setTopAnchor(lblLeaderboard,350.0);
		AnchorPane.setLeftAnchor(lblLeaderboard, 14.0);

		ap.getChildren().addAll(back,fifaHeader,lblDesc, lblUpcoming, lblTourney1, lblTourney2,lblLeaderboard, btnReg1, btnReg2);
		
		fifaScene = new Scene(ap,600,400);
		back.setOnAction(e -> window.setScene(mainDashboardScene));
	}
	void OpenFifaScene()
	{
		window.setScene(fifaScene);
	}
	
	//Halo Scene
	void CreateHaloScene()
	{
		Label lblDesc = new Label("Four years after the events of Halo 3, the Master Chief returns in this award-winning first-person shooter. Battle through the campaign and explore the Forerunner planet, Requiem, as the Chief faces off with an ancient evil. Test your skills against familiar Covenant foes and new Promethean enemies in solo mode or with up to three friends on split-screen. With Xbox Live Gold Membership, access the online multiplayer and Spartan Ops missions.");
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Halo 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Halo 4v4 - Boston - Sponsored by Razer");
		Button btnReg1 = new Button("Register");
		Button btnReg2 = new Button("Register");
		Button back = new Button("<-");
		Label lblLeaderboard = new Label("Leaderboard - Coming soon");
		ImageView haloHeader = new ImageView(new Image("TopGamer/Presentation/Images/HaloHeader.png"));
		haloHeader.setFitHeight(70);
		haloHeader.setFitWidth(255);
		
		AnchorPane ap = new AnchorPane();
		
		AnchorPane.setTopAnchor(back, 14.0);
		AnchorPane.setLeftAnchor(back, 14.0);
		
		AnchorPane.setTopAnchor(haloHeader, 69.0);
		AnchorPane.setLeftAnchor(haloHeader, 14.0);
		
		AnchorPane.setTopAnchor(lblDesc, 150.0);
		AnchorPane.setLeftAnchor(lblDesc, 14.0);
		
		AnchorPane.setTopAnchor(lblUpcoming, 247.0);
		AnchorPane.setLeftAnchor(lblUpcoming, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney1, 273.0);
		AnchorPane.setLeftAnchor(lblTourney1, 14.0);
		
		AnchorPane.setTopAnchor(lblTourney2, 310.0);
		AnchorPane.setLeftAnchor(lblTourney2, 14.0);
		
		AnchorPane.setTopAnchor(btnReg1, 269.0);
		AnchorPane.setLeftAnchor(btnReg1, 345.0);
		
		AnchorPane.setTopAnchor(btnReg2, 306.0);
		AnchorPane.setLeftAnchor(btnReg2, 345.0);
		
		AnchorPane.setTopAnchor(lblLeaderboard,350.0);
		AnchorPane.setLeftAnchor(lblLeaderboard, 14.0);
		
		ap.getChildren().addAll(back,haloHeader,lblDesc, lblUpcoming, lblTourney1, lblTourney2,lblLeaderboard, btnReg1, btnReg2);
		haloScene = new Scene(ap,600,400);
		
		back.setOnAction(e -> window.setScene(mainDashboardScene));
	}
	void OpenHaloScene()
	{
		window.setScene(haloScene);
	}
}
