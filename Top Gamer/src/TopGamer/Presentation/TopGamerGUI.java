package TopGamer.Presentation;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.ArrayList;
import java.util.Observable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import TopGamer.Business.*;


public class TopGamerGUI extends Application
{
	// dfsdsdsdsds
	User testUser = new User();
	Stage window,gameStage;
	Scene welcomeScene,loginScene, registerScene, mainDashboardScene;
	Scene fortniteScene, apexScene, fifaScene, codScene, haloScene;
	
	//Used on welcome scene
	JFXButton btnLogin, btnRegister, btnContinue;
	
	//Used for user login - DESIGNED BUT NOT WORKING
	Label lblLoginName, lblLoginPass;
	JFXTextField txtLoginName;
	JFXPasswordField txtLoginPass;
	JFXButton btnUserLogin, btnBackToWelcome;
	JFXButton btnSignUp;
	
	//Registration
	Label lblFName;
	Label lblLName;
	Label lblEmail;
	Label lblUserName;
	Label lblPass;
	JFXTextField txtFName;
	JFXTextField txtLName;
	JFXTextField txtEmail;
	JFXTextField txtUserName;
	JFXPasswordField txtPass;

	//Labels to display if login info isnt valid
	Label lblValidUser,lblValidPass;
	
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
	
	/**
	 * Set main window the the welcomeScene
	 */
	public void BackToWelcomeScreen()
	{
		window.setScene(welcomeScene);
	}
	
	/**
	 * Create/Add all controls to the welcomeScene
	 */
	public void CreateWelcomeScene()
	{
		AnchorPane ap = new AnchorPane();
		
//		ImageView imageView = new ImageView();
//		Image img = new Image("TopGamer/Presentation/stadium.jpg");
//		imageView.setImage(img);
		
		btnLogin = new JFXButton("Login");
		btnLogin.setPrefWidth(173.0);
		btnLogin.setPrefHeight(43.0);
		btnRegister = new JFXButton("Register");
		btnRegister.setPrefWidth(173.0);
		btnRegister.setPrefHeight(43.0);
		btnContinue = new JFXButton("Continue without registering");
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
	
	/**
	 * Set main window to the loginScene
	 */
	public void OpenLoginScene()
	{
		window.setScene(loginScene);
	}
	
	/**
	 * Create/Add controls to the loginScene
	 */
	public void CreateLoginScene()
	{
		AnchorPane ap = new AnchorPane();
		
		txtLoginName = new JFXTextField();
		txtLoginName.setPrefWidth(160.0);
		txtLoginName.setPrefHeight(25.0);
		txtLoginName.setLabelFloat(true);
		txtLoginName.setPromptText("Username or email address");
		txtLoginPass = new JFXPasswordField();
		txtLoginPass.setPrefWidth(160.0);
		txtLoginPass.setLabelFloat(true);
		txtLoginPass.setPrefHeight(25.0);
		txtLoginPass.setPromptText("Password");
		
		btnUserLogin = new JFXButton("Login");
		
		
		btnUserLogin.setPrefWidth(160.0);
		btnUserLogin.setPrefHeight(25.0);
		btnUserLogin.setOnAction(e-> Login());
		//JFXButton btn = new JFXButton();
	    
		
		Button btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e -> window.setScene(welcomeScene));
		btnSignUp = new JFXButton("Dont have an account? Sign up");
		btnSignUp.setPrefWidth(160.0);
		btnSignUp.setPrefHeight(25.0);
		btnSignUp.setFont(Font.font(10));
		btnSignUp.setOnAction(e -> OpenRegisterScene());
		
		lblValidPass = new Label();
		lblValidUser = new Label();
		
		AnchorPane.setLeftAnchor(txtLoginName, 226.0);
		AnchorPane.setTopAnchor(txtLoginName, 134.0);
		
		AnchorPane.setLeftAnchor(lblValidUser, 226.0);
		AnchorPane.setTopAnchor(lblValidUser, 160.0);
		
		AnchorPane.setLeftAnchor(txtLoginPass, 226.0);
		AnchorPane.setTopAnchor(txtLoginPass, 188.0);
		
		AnchorPane.setLeftAnchor(lblValidPass, 226.0);
		AnchorPane.setTopAnchor(lblValidPass, 215.0);
		
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
		
		ap.getChildren().addAll(txtLoginName,txtLoginPass,btnUserLogin,lblValidUser,lblValidPass,btnSignUp,btnReturn);

		loginScene = new Scene(ap,600,400);
	}
	
	/**
	 * Creates an instance of SQLConnection and calls Login() 
	 * 
	 * If login is valid, open mainDashboard and load user data into object
	 */
	public void Login()
	{
		if(!LoginValidation())
			return;
		else
		{
			boolean Valid = false;
		    SQLConnection dbLogin = new SQLConnection();
					try
					{
						Valid = dbLogin.Login(txtLoginName.getText(), txtLoginPass.getText(),testUser);	
					}
						catch(SQLException e)
					{
						e.printStackTrace();
					}
			if(Valid)
				OpenMainDashboard();
			else
			{
				lblValidPass.setText("Username or password invalid");
				lblValidPass.setTextFill(Color.RED);
			}
		}
	}
	
	
	
	
	/**
	 * Login form validation
	 * Username cannot be blank and must be a string (will allow users to add numbers later on)
	 * Password cannot be blank and must be a string (will allow users to add numbers later on)
	 * @return boolean for valid login
	 */
	public boolean LoginValidation()
	{
		//The coloring of the textboxes needs to be moved to an event
		lblValidPass.setText(null);
		lblValidUser.setText(null);
		
		if(txtLoginName.getText().equals("") && txtLoginPass.getText().equals(""))
		{
			lblValidUser.setText("Username cannot be blank");
			lblValidUser.setTextFill(Color.RED);
			lblValidPass.setText("Password cannot be blank");
			lblValidPass.setTextFill(Color.RED);
			return false;
		}
		if(txtLoginName.getText().equals(""))
		{
			lblValidUser.setText("Username cannot be blank");
			lblValidUser.setTextFill(Color.RED);
			return false;
		}
		
		if(txtLoginPass.getText().equals(""))
		{
			lblValidPass.setText("Password cannot be blank");
			lblValidPass.setTextFill(Color.RED);
			txtLoginPass.setStyle(".text-field.error  -fx-text-box-border: red ; -fx-focus-color: red ;}");
			return false;
		}
	
		return true;		
	}

	/**
	 *  Tests if TextField is empty or not
	 *  
	 * @param text - JFXTextField Instance
	 * @return true is text is empty, false otherwise
	 */
	public boolean Empty(JFXTextField text)
	{
		if(text.getText() == null || text.getText().isEmpty())
			return true;
		else 
			return false;
	}
	
	
	
	/**
	 * Set main window to registerScene
	 */
	public void OpenRegisterScene()
	{
		window.setScene(registerScene);
	}
	/**
	 * Create/Add controls to the registerScene
	 */
	public void CreateRegisterScene()
	{
		AnchorPane ap = new AnchorPane();
		Button btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e ->BackToWelcomeScreen());
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		
		lblFName = new Label("First Name");
		lblLName = new Label("Last Name");
		lblEmail = new Label("Email Address");
		lblUserName = new Label("Username");
		lblPass = new Label("Password");
		
		txtFName = new JFXTextField();
		txtFName.setLabelFloat(true);
		txtFName.setPromptText("First Name");
		
		txtLName = new JFXTextField();
		txtLName.setLabelFloat(true);
		txtLName.setPromptText("Last Name");
		
		txtEmail = new JFXTextField();
		txtEmail.setLabelFloat(true);
		txtEmail.setPromptText("Email Address");
		
		
		txtUserName = new JFXTextField();
		txtUserName.setLabelFloat(true);
		txtUserName.setPromptText("Username");
		
		
		txtPass = new JFXPasswordField();
		txtPass.setLabelFloat(true);
		txtPass.setPromptText("Password");
		
		btnSignUp = new JFXButton("Sign up");
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
		
		ap.getChildren().addAll(btnReturn,txtFName,txtLName,txtEmail,txtUserName,txtPass,btnSignUp);
		registerScene = new Scene(ap,600,400);
	}
	/**
	 * 	Creates an instance of SQLConnection and calls AddUser()
	 *  using data from the register form's text fields
	 */
	public void RegisterUser()
	{
		if(RegisterValidation())
		{
			SQLConnection s = new SQLConnection();
			s.AddUser(txtFName.getText(), txtLName.getText(), txtEmail.getText(), txtUserName.getText(), txtPass.getText());
			window.setScene(mainDashboardScene);
		}
	}
	
	public boolean RegisterValidation()
	{
		if(txtFName.getText().equals("") && txtLName.getText().equals("") && txtEmail.getText().equals("") && txtUserName.getText().equals("") && txtPass.getText().equals(""))
		{
			System.out.println("All fields are empty");
			return false;
		}
		if(Empty(txtFName))
		{
			System.out.println("Firsttt name is empty");
			return false;
		}
		if(txtLName.getText().equals(""))
		{
			System.out.println("Last name is empty");
			return false;
		}
		if(txtEmail.getText().equals(""))
		{
			System.out.println("Email is empty");
			return false;
		}
		if(txtUserName.getText().equals(""))
		{
			System.out.println("User name is empty");
			return false;
		}
		if(txtPass.getText().equals(""))
		{
			System.out.println("Password is empty");
			return false;
		}
		return true;
	}
	
	
	/**
	 * Sets main window to the mainDashboard scene
	 */
	public void OpenMainDashboard()
	{
		 System.out.println(testUser.GetFirstName() + " " + testUser.GetLastName() + " " + testUser.GetEmail());
		 window.setScene(mainDashboardScene);
	}	
	
	/**
	 * Creates/Adds controls to the mainDashboard scene
	 */
	public void CreateMainDashboard()
	{
		//use this vbox to only make games scroll
		VBox test = new VBox();
		test.setStyle("-fx-focus-color: transparent;");
		ScrollPane mainScroll = new ScrollPane();
		mainScroll.setPannable(true);
		mainScroll.setFitToHeight(true);
		mainScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		AnchorPane ap = new AnchorPane();
		JFXButton btnReturn = new JFXButton("<-");
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
		
		//add images to anchor pane
		ap.getChildren().addAll(lblFeaturedGames,btnReturn,fortniteLogo,apexLogo,codLogo,fifaLogo,haloLogo);
	
		//add anchor pane to the scroll pane
		mainScroll.setContent(ap);
		
		test.getChildren().addAll(lblFeaturedGames,mainScroll, btnReturn);

		mainDashboardScene = new Scene(test,600,400);
	
	}
	
	
	/**
	 * Create/Add controls to the fortniteScene
	 */
	public void CreateFortniteScene()
	{
		Label lblDesc = new Label("Fortnite Battle Royale is the FREE 100-player PvP mode in Fortnite. One giant map. A battle bus. Fortnite building skills and destructible environments combined with intense PvP combat. The last one standing wins. Available on PC, PlayStation 4, Xbox One & Mac. ");
		lblDesc.setPrefHeight(77);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Fortnite 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Fortnite 4v4 - Boston - Sponsored by Razer");
		JFXButton btnReg1 = new JFXButton("Register");
		JFXButton btnReg2 = new JFXButton("Register");
		Button back = new JFXButton("<-");
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
	/**
	 * Set main window the the fortniteScene
	 */
	public void OpenFortniteScene()
	{
		window.setScene(fortniteScene);
	}
	
	
	/**
	 * Create/Add controls to the apexScene
	 */
	public void CreateApexScene()
	{
		Label lblDesc = new Label("Explore a growing roster of powerful Legends, each with their own unique personality, strengths, and abilities. Choose your Legend, team up with two other players, and combine your unique skills to be the last squad standing. Master your Legend’s abilities, make strategic calls on the fly, and use your team’s strengths to your advantage in vicious 60-player matches.  ");
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Apex 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Apex 4v4 - Boston - Sponsored by Razer");
		Button btnReg1 = new JFXButton("Register");
		Button btnReg2 = new JFXButton("Register");
		Button back = new JFXButton("<-");
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
	/**
	 * Set main window the the apexScene
	 */
	public void OpenApexScene()
	{
		window.setScene(apexScene);
	}
	
	
	/**
	 * Create/Add controls to the codScene
	 */
	public void CreateCODScene()
	{
		Label lblDesc = new Label("Apart from the Exo Movement, Advanced Warfare's multiplayer retains certain similarities to previous Call of Duty titles. The Pick 10 system in Black Ops II returns as Pick 13, allowing players to pick weapons, attachments, perks and score-streaks within a total of 13 allocation points.");
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Call Of Duty 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Call Of Duty 4v4 - Boston - Sponsored by Razer");
		Button btnReg1 = new JFXButton("Register");
		Button btnReg2 = new JFXButton("Register");
		Button back = new JFXButton("<-");
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
	/**
	 * Set main window to the codScene
	 */
	public void OpenCodScene()
	{
		window.setScene(codScene);
	}
	
	
	/**
	 * Create/Add controls to the fifaScene
	 */
	public void CreateFifaScene()
	{
		Label lblDesc = new Label("Get ready for the return of the UEFA Champions League with amazing new content in FIFA 19. With special Champions League kits and player items, as well as your choice of a Neymar Jr, Kevin De Bruyne, or Paulo Dybala 10 match loan item for your team, there's no better time to get started in FIFA 19!\r\n"); 
				
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Fifa - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Fifa - Boston - Sponsored by Razer");
		Button btnReg1 = new JFXButton("Register");
		Button btnReg2 = new JFXButton("Register");
		Button back = new JFXButton("<-");
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
	/**
	 * Set main window to the fifaScene
	 */
	public void OpenFifaScene()
	{
		window.setScene(fifaScene);
	}
	
	
	/**
	 * Create/Add controls to the haloScene
	 */
	public void CreateHaloScene()
	{
		Label lblDesc = new Label("Four years after the events of Halo 3, the Master Chief returns in this award-winning first-person shooter. Battle through the campaign and explore the Forerunner planet, Requiem, as the Chief faces off with an ancient evil. Test your skills against familiar Covenant foes and new Promethean enemies in solo mode or with up to three friends on split-screen. With Xbox Live Gold Membership, access the online multiplayer and Spartan Ops missions.");
		lblDesc.setPrefHeight(90);
		lblDesc.setPrefWidth(397);
		lblDesc.setWrapText(true);
		Label lblUpcoming= new Label("Upcoming Tournaments");
		lblUpcoming.setUnderline(true);
		Label lblTourney1 = new Label("March 15th - Halo 4v4 - NYC - Sponsored by Best-Buy");
		Label lblTourney2 = new Label("March 20th - Halo 4v4 - Boston - Sponsored by Razer");
		Button btnReg1 = new JFXButton("Register");
		Button btnReg2 = new JFXButton("Register");
		Button back = new JFXButton("<-");
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
	/**
	 * Set main window to the haloScene
	 */
	public void OpenHaloScene()
	{
		window.setScene(haloScene);
	}
}
