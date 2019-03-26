package TopGamer.Presentation;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import TopGamer.Business.*;


public class TopGamerGUI extends Application
{
	
	User testUser = new User();
	boolean loggedIn = false;
	
	Stage window,gameStage;
	Scene loginScene, registerScene, mainDashboardScene;
	Scene fortniteScene, codScene, haloScene;
	
	//Used for user login 
	Label lblLoginName, lblLoginPass;
	JFXTextField txtLoginName;
	JFXPasswordField txtLoginPass;
	JFXButton btnUserLogin;
	JFXButton btnSignUp;
	JFXButton btnContinue;
	
	//Registration
	Label lblValidFirstName;
	Label lblValidLastName;
	Label lblValidEmail;
	Label lblValidUserName;
	Label lblValidPass;
	JFXTextField txtFName;
	JFXTextField txtLName;
	JFXTextField txtEmail;
	JFXTextField txtUserName;
	JFXPasswordField txtPass;
	
	//Profile drop down
	JFXButton btnProfile = new JFXButton("Profile");
	JFXButton btnEditProfile = new JFXButton("Settings");
	JFXButton btnLogOut = new JFXButton("Edit Profile");
	JFXNodesList nodeList = new JFXNodesList();
	
	//Labels to display if login info isnt valid
	Label lblValidLoginUser,lblValidLoginPass;
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{		
		window = primaryStage;
		window.setResizable(false);
		
		CreateLoginScene();
		CreateRegisterScene();
		CreateMainDashboard();
		CreateFortniteScene();		
		CreateCODScene();	
		CreateHaloScene();
	
		window.setScene(loginScene);
		window.show();	
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
		
		txtLoginName.setOnKeyReleased(e -> {
			lblValidLoginPass.setText(null);
			lblValidLoginUser.setText(null);
		});
		
		txtLoginPass = new JFXPasswordField();
		txtLoginPass.setPrefWidth(160.0);
		txtLoginPass.setLabelFloat(true);
		txtLoginPass.setPrefHeight(25.0);
		txtLoginPass.setPromptText("Password");
		
		txtLoginPass.setOnKeyReleased(e -> {
			lblValidLoginPass.setText(null);
		});
		
		btnUserLogin = new JFXButton("Login");
		
		
		btnUserLogin.setPrefWidth(160.0);
		btnUserLogin.setPrefHeight(25.0);
		btnUserLogin.setOnAction(e-> Login());
		btnUserLogin.setStyle( "-jfx-button-type: RAISED; -fx-background-color: white; -fx-text-fill: black;");
		
		
		btnSignUp = new JFXButton("Dont have an account? Sign up");
		btnSignUp.setPrefWidth(160.0);
		btnSignUp.setPrefHeight(25.0);
		btnSignUp.setFont(Font.font(10));
		btnSignUp.setOnAction(e ->{
			txtLoginName.setText("");
	    	txtLoginPass.setText("");
	    	lblValidLoginUser.setText(null);
	    	lblValidLoginPass.setText(null);
			OpenRegisterScene();
		});
		btnSignUp.setStyle( "-jfx-button-type: RAISED; -fx-background-color: white; -fx-text-fill: black;");
		
		btnContinue = new JFXButton("Continue without registering");
		btnContinue.setPrefWidth(600.0);
		btnContinue.setPrefHeight(20.0);
		btnContinue.setStyle( "-jfx-button-type: RAISED; -fx-background-color: white; -fx-text-fill: black;");
		btnContinue.setOnAction(e -> OpenMainDashboard());
		
		lblValidLoginPass = new Label();
		lblValidLoginUser = new Label();
		
		AnchorPane.setLeftAnchor(txtLoginName, 226.0);
		AnchorPane.setTopAnchor(txtLoginName, 134.0);
		
		AnchorPane.setLeftAnchor(lblValidLoginUser, 226.0);
		AnchorPane.setTopAnchor(lblValidLoginUser, 160.0);
		
		AnchorPane.setLeftAnchor(txtLoginPass, 226.0);
		AnchorPane.setTopAnchor(txtLoginPass, 188.0);
		
		AnchorPane.setLeftAnchor(lblValidLoginPass, 226.0);
		AnchorPane.setTopAnchor(lblValidLoginPass, 215.0);
		
		AnchorPane.setLeftAnchor(btnUserLogin, 226.0);
		AnchorPane.setTopAnchor(btnUserLogin, 246.0);
		
		AnchorPane.setLeftAnchor(btnUserLogin, 226.0);
		AnchorPane.setTopAnchor(btnUserLogin, 246.0);
		
		AnchorPane.setLeftAnchor(btnUserLogin, 226.0);
		AnchorPane.setTopAnchor(btnUserLogin, 246.0);
		
		AnchorPane.setLeftAnchor(btnSignUp, 226.0);
		AnchorPane.setTopAnchor(btnSignUp, 278.0);
		
		
		AnchorPane.setLeftAnchor(btnContinue,0.0);
		AnchorPane.setTopAnchor(btnContinue, 374.0);
		
		ap.getChildren().addAll(txtLoginName,txtLoginPass,btnUserLogin,lblValidLoginUser,lblValidLoginPass,btnSignUp,btnContinue);

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
			{
				loggedIn = true;
				txtLoginName.setText("");
				txtLoginPass.setText("");
				OpenMainDashboard();
			}
				else
			{
				lblValidLoginPass.setText("Username or password invalid");
				lblValidLoginPass.setTextFill(Color.RED);
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
		lblValidLoginPass.setText(null);
		lblValidLoginUser.setText(null);
		
		if(Empty(txtLoginName) && Empty(txtLoginPass))
		{
			lblValidLoginUser.setText("Username cannot be blank");
			lblValidLoginUser.setTextFill(Color.RED);
			lblValidLoginPass.setText("Password cannot be blank");
			lblValidLoginPass.setTextFill(Color.RED);
			return false;
		}
		if(Empty(txtLoginName))
		{
			lblValidLoginUser.setText("Username cannot be blank");
			lblValidLoginUser.setTextFill(Color.RED);
			return false;
		}
		
		if(Empty(txtLoginPass))
		{
			lblValidLoginPass.setText("Password cannot be blank");
			lblValidLoginPass.setTextFill(Color.RED);
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
	 *  Tests if Password field is empty or not
	 *  
	 * @param text - JFXTextField Instance
	 * @return true is text is empty, false otherwise
	 */
	public boolean Empty(JFXPasswordField pass)
	{
		if(pass.getText() == null || pass.getText().isEmpty())
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
		btnReturn.setOnAction(e ->{
			txtFName.setText("");
			txtLName.setText("");
			txtEmail.setText("");
			txtUserName.setText("");
			txtPass.setText("");
			lblValidFirstName.setText(null);
			lblValidLastName.setText(null);
			lblValidEmail.setText(null);
			lblValidUserName.setText(null);
			lblValidPass.setText(null);
			OpenLoginScene();
		});
			
		lblValidFirstName = new Label("");
		lblValidLastName = new Label("");
		lblValidEmail = new Label("");
		lblValidUserName = new Label("");
		lblValidPass = new Label("");
		
		txtFName = new JFXTextField();
		txtFName.setLabelFloat(true);
		txtFName.setPromptText("First Name");
		txtFName.setOnKeyPressed(e -> {
			lblValidFirstName.setText(null);
		});
		
		txtLName = new JFXTextField();
		txtLName.setLabelFloat(true);
		txtLName.setPromptText("Last Name");
		txtLName.setOnKeyPressed(e -> {
			lblValidLastName.setText(null);
		});
		
		txtEmail = new JFXTextField();
		txtEmail.setLabelFloat(true);
		txtEmail.setPromptText("Email Address");
		txtEmail.setOnKeyPressed(e -> {
			lblValidEmail.setText(null);
		});
		
		txtUserName = new JFXTextField();
		txtUserName.setLabelFloat(true);
		txtUserName.setPromptText("Username");
		txtUserName.setOnKeyPressed(e -> {
			lblValidUserName.setText(null);
		});
		
		txtPass = new JFXPasswordField();
		txtPass.setLabelFloat(true);
		txtPass.setPromptText("Password");
		txtPass.setOnKeyPressed(e -> {
			lblValidPass.setText(null);
		});
		
		btnSignUp = new JFXButton("Sign up");
		btnSignUp.setStyle( "-jfx-button-type: RAISED; -fx-background-color: white; -fx-text-fill: black;");
		btnSignUp.setOnAction(e -> RegisterUser());
		
				
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		
		AnchorPane.setLeftAnchor(lblValidFirstName, 226.0);
		AnchorPane.setTopAnchor(lblValidFirstName, 47.0);
		AnchorPane.setLeftAnchor(txtFName, 226.0);
		AnchorPane.setTopAnchor(txtFName, 70.0);
		
		AnchorPane.setLeftAnchor(lblValidLastName, 226.0);
		AnchorPane.setTopAnchor(lblValidLastName, 108.0);
		AnchorPane.setLeftAnchor(txtLName, 226.0);
		AnchorPane.setTopAnchor(txtLName, 125.0);
		

		AnchorPane.setLeftAnchor(lblValidEmail, 226.0);
		AnchorPane.setTopAnchor(lblValidEmail, 164.0);
		AnchorPane.setLeftAnchor(txtEmail, 226.0);
		AnchorPane.setTopAnchor(txtEmail, 181.0);
		
		AnchorPane.setLeftAnchor(lblValidUserName, 226.0);
		AnchorPane.setTopAnchor(lblValidUserName, 221.0);
		AnchorPane.setLeftAnchor(txtUserName, 226.0);
		AnchorPane.setTopAnchor(txtUserName, 238.0);
		
		AnchorPane.setLeftAnchor(lblValidPass, 226.0);
		AnchorPane.setTopAnchor(lblValidPass, 279.0);
		AnchorPane.setLeftAnchor(txtPass, 226.0);
		AnchorPane.setTopAnchor(txtPass, 296.0);
		
		AnchorPane.setLeftAnchor(btnSignUp, 226.0);
		AnchorPane.setTopAnchor(btnSignUp, 344.0);
		
		ap.getChildren().addAll(btnReturn,txtFName,txtLName,txtEmail,txtUserName,txtPass,btnSignUp,lblValidFirstName,lblValidLastName,lblValidEmail,lblValidUserName,lblValidPass);
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
			btnProfile.setText(txtUserName.getText());
			window.setScene(mainDashboardScene);
			txtFName.setText("");
			txtLName.setText("");
			txtEmail.setText("");
			txtUserName.setText("");
			txtPass.setText("");
		}
	}
	
	/**
	 * Input validation for registration form 
	 * @return returns false if any text boxes are empty 
	 */
	public boolean RegisterValidation()
	{
		if(Empty(txtFName) && Empty(txtLName) && Empty(txtEmail) && Empty(txtUserName) && Empty(txtPass))
		{
			lblValidFirstName.setText("First cannot be empty");
			lblValidFirstName.setTextFill(Color.RED);
			lblValidLastName.setText("Last name cannot be empty");
			lblValidLastName.setTextFill(Color.RED);
			lblValidEmail.setText("Email cannot be empty");
			lblValidEmail.setTextFill(Color.RED);
			lblValidUserName.setText("Username cannot be empty");
			lblValidUserName.setTextFill(Color.RED);
			lblValidPass.setText("Password cannot be empty");
			lblValidPass.setTextFill(Color.RED);
			return false;
		}
		else if(Empty(txtFName))
		{
			lblValidFirstName.setText("First cannot be empty");
			lblValidFirstName.setTextFill(Color.RED);
			return false;
		}
		else if(Empty(txtLName))
		{
			lblValidLastName.setText("Last name cannot be empty");
			lblValidLastName.setTextFill(Color.RED);
			return false;
		}
		else if(Empty(txtEmail))
		{
			lblValidEmail.setText("Email cannot be empty");
			lblValidEmail.setTextFill(Color.RED);
			return false;
		}
		else if(Empty(txtUserName))
		{
			lblValidUserName.setText("Username cannot be empty");
			lblValidUserName.setTextFill(Color.RED);
			return false;
		}
		if(Empty(txtPass))
		{
			lblValidPass.setText("Password cannot be empty");
			lblValidPass.setTextFill(Color.RED);
			return false;
		}
		return true;
	}
	
	
	/**
	 * Sets main window to the mainDashboard scene
	 */
	public void OpenMainDashboard()
	{
		if(loggedIn)
			btnProfile.setText(testUser.GetUsername());
		 System.out.println(testUser.GetFirstName() + " " + testUser.GetLastName() + " " + testUser.GetEmail());
		 window.setScene(mainDashboardScene);
	}	
	
	/**
	 * Creates/Adds controls to the mainDashboard scene
	 */
	public void CreateMainDashboard()
	{
		btnProfile = new JFXButton("Profile");
		btnEditProfile = new JFXButton("Edit profile");
		btnLogOut= new JFXButton("Log out");
		btnLogOut.setOnAction(e -> OpenLoginScene());
		nodeList = new JFXNodesList();
		nodeList.addAnimatedNode(btnProfile);
		nodeList.addAnimatedNode(btnEditProfile);
		nodeList.addAnimatedNode(btnLogOut);
		
		
		//use this vbox to only make games scroll
		VBox mainDashVbox = new VBox();
		mainDashVbox.setSpacing(55);
		mainDashVbox.setStyle("-fx-focus-color: transparent;");
		AnchorPane ap = new AnchorPane();
		ap.setStyle("-fx-focus-color: transparent;");
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenLoginScene());
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
		
		codLogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {codScene.setCursor(Cursor.DEFAULT); OpenCodScene();});
		codLogo.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event ->{mainDashboardScene.setCursor(Cursor.HAND);});
		codLogo.addEventHandler(MouseEvent.MOUSE_EXITED, event ->{ mainDashboardScene.setCursor(Cursor.DEFAULT);});
		
		haloLogo.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {haloScene.setCursor(Cursor.DEFAULT); OpenHaloScene();});
		haloLogo.addEventHandler(MouseEvent.MOUSE_ENTERED_TARGET, event ->{mainDashboardScene.setCursor(Cursor.HAND);});
		haloLogo.addEventHandler(MouseEvent.MOUSE_EXITED, event ->{ mainDashboardScene.setCursor(Cursor.DEFAULT);});
		
		//Set image position on anchor pane	
		AnchorPane.setTopAnchor(fortniteLogo, 35.0);
		AnchorPane.setLeftAnchor(fortniteLogo, 24.0);
		AnchorPane.setTopAnchor(codLogo, 35.0);
		AnchorPane.setLeftAnchor(codLogo, 167.0);
		AnchorPane.setTopAnchor(haloLogo, 35.0);
		AnchorPane.setLeftAnchor(haloLogo, 308.0);
		AnchorPane.setTopAnchor(btnReturn, 300.0);
		AnchorPane.setTopAnchor(nodeList, 14.0);
		AnchorPane.setRightAnchor(nodeList, 14.0);
		//add images to anchor pane
		ap.getChildren().addAll(btnReturn,fortniteLogo,codLogo,haloLogo,nodeList);
	
		//add anchor pane to the scroll pane
		
		mainDashVbox.getChildren().addAll(ap, btnReturn);

		mainDashboardScene = new Scene(mainDashVbox,600,400);
	
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
