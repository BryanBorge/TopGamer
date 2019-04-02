package TopGamer.Presentation;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.Event;
import java.awt.Desktop.Action;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import javax.lang.model.AnnotatedConstruct;

import org.controlsfx.control.textfield.TextFields;
import org.sqlite.SQLiteException;

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
	Scene codTourney;
	
	ImageView backArrow = new ImageView(new Image("back arrow.png"));
	
	
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
		
		backArrow.setFitHeight(20);
		backArrow.setFitWidth(20);
		
		CreateLoginScene();
		CreateRegisterScene();
		CreateMainDashboard();
		CreateFortniteScene();		
		CreateCODScene();	
		CreateHaloScene();
		CreateCodTourneyScene();
		
		window.getIcons().add(new Image("icon.png"));
		window.setTitle("Top Gamer");
		Parent root = FXMLLoader.load(getClass().getResource(("LoginScreen.fxml")));
		Scene scene = new Scene(root);
		//window.setScene(scene);
		window.setScene(loginScene);
		window.show();	
	}

	@FXML
	private JFXButton btnContinue1;
	
public void Continue(ActionEvent e) {
		window.setScene(mainDashboardScene);
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
			if(e.getCode() != KeyCode.ENTER)
				lblValidLoginPass.setText(null);
		});
		
		txtLoginPass.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER) 
				Login();
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
		Button btnReturn = new JFXButton();
		
		btnReturn.setGraphic(backArrow);
		
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
		
		txtPass.setOnKeyReleased(e -> {
			if(e.getCode() != KeyCode.ENTER)
				lblValidPass.setText(null);
		});
		
		txtPass.setOnKeyPressed(e->{
			if(e.getCode() == KeyCode.ENTER)
				RegisterUser();
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
		
			try {
				SQLConnection s = new SQLConnection();
				s.AddUser(txtFName.getText(), txtLName.getText(), txtEmail.getText(), txtUserName.getText(), txtPass.getText());
				btnProfile.setText(txtUserName.getText());
				txtFName.setText("");
				txtLName.setText("");
				txtEmail.setText("");
				txtUserName.setText("");
				txtPass.setText("");
				window.setScene(mainDashboardScene);
			} catch (SQLiteException e) {
				System.out.println("Error saving user to database. Make sure it is closed");
			}
			
			
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
	
		//this event should be changed in the future using the loggedIn bool to change the profile option text
		btnLogOut.setOnAction(e -> {
			loggedIn = false;
			btnProfile.setText("Profile");
			testUser.SetFirstName("");
			testUser.SetLastName("");
			testUser.SetEmail("");
			OpenLoginScene();}
		);
		nodeList = new JFXNodesList();
		nodeList.addAnimatedNode(btnProfile);
		nodeList.addAnimatedNode(btnEditProfile);
		nodeList.addAnimatedNode(btnLogOut);
		
		VBox mainVbox = new VBox();
		ScrollPane mainScroll = new ScrollPane();
		mainScroll.setLayoutX(0.0);
		mainScroll.setLayoutY(32.0);
		AnchorPane anchorScroll = new AnchorPane();
		AnchorPane anchorHeader = new AnchorPane();
		anchorHeader.setPrefHeight(47);
		anchorHeader.setPrefWidth(600);
		anchorHeader.setLayoutX(522.0);
		anchorHeader.setLayoutY(0.0);
	
		Label lblFeaturedGames = new Label("Featured Games");
		
		//Load images and set their size
		ImageView profileIcon = new ImageView(new Image("profileIcon.png"));
		profileIcon.setFitHeight(20);
		profileIcon.setFitWidth(20);
		btnProfile.setGraphic(profileIcon);
	
		ImageView fortniteLogo = new ImageView(new Image("Fortnite.jpg"));
		fortniteLogo.setFitWidth(133);
		fortniteLogo.setFitHeight(202);
		
		ImageView apexLogo = new ImageView(new Image("Apex.jpg"));
		apexLogo.setFitWidth(133);
		apexLogo.setFitHeight(202);
		
		ImageView codLogo = new ImageView(new Image("Cod.jpg"));
		codLogo.setFitWidth(133);
		codLogo.setFitHeight(202);
		
		ImageView fifaLogo = new ImageView(new Image("fifa.jpg"));
		fifaLogo.setFitWidth(133);
		fifaLogo.setFitHeight(202);
		
		ImageView haloLogo = new ImageView(new Image("Halo.jpg"));
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
		
		JFXButton testTourney = new JFXButton("4v4 CoD Tournament");
		testTourney.setOnAction(e -> OpenCodTourney());
		JFXButton testTourney1 = new JFXButton("4v4 Fortnite Tournament");
		JFXButton testTourney2 = new JFXButton("4v4 Halo Tournament");
		
		//Set image position on anchorScroll
		AnchorPane.setTopAnchor(fortniteLogo, 50.0);
		AnchorPane.setLeftAnchor(fortniteLogo, 24.0);
		AnchorPane.setTopAnchor(codLogo, 50.0);
		AnchorPane.setLeftAnchor(codLogo, 167.0);
		AnchorPane.setTopAnchor(haloLogo, 50.0);
		AnchorPane.setLeftAnchor(haloLogo, 308.0);
		AnchorPane.setTopAnchor(lblFeaturedGames, 14.0);
		AnchorPane.setLeftAnchor(lblFeaturedGames, 14.0);
		
		//set button position on the anchorScroll
		AnchorPane.setTopAnchor(testTourney, 300.0);
		AnchorPane.setLeftAnchor(testTourney, 14.0);
		AnchorPane.setTopAnchor(testTourney1, 400.0);
		AnchorPane.setLeftAnchor(testTourney1, 14.0);
		AnchorPane.setTopAnchor(testTourney2, 500.0);
		AnchorPane.setLeftAnchor(testTourney2, 14.0);
		
		//this goes in the anchorHeader
		AnchorPane.setTopAnchor(nodeList, 6.0);
		AnchorPane.setRightAnchor(nodeList, 14.0);
		
	
		//add images to anchor pane
		anchorHeader.getChildren().addAll(nodeList);
		anchorScroll.getChildren().addAll(lblFeaturedGames,fortniteLogo,codLogo,haloLogo,testTourney,testTourney1,testTourney2);
		mainScroll.setContent(anchorScroll);
		mainVbox.getChildren().addAll(anchorHeader,mainScroll);
		
		mainDashboardScene = new Scene(mainVbox,600,400);
	
	}
	
	/**
	 * Create/Add controls to the fortniteScene
	 */
	public void CreateFortniteScene()
	{
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Fortnite"); 
		lblTitle.setFont(new Font(24));
		Label lblDesc = new Label("Epic Games");
		JFXButton btnReturn = new JFXButton("<-");
		//btnReturn.setGraphic(backArrow);
		btnReturn.setOnAction(e-> OpenMainDashboard());
		JFXButton btnPS4 = new JFXButton("PS4");
		JFXButton btnXbox = new JFXButton("Xbox One");
		JFXButton btnPC = new JFXButton("PC");
		
		ImageView fortniteLogo = new ImageView(new Image("Fortnite.jpg"));
		fortniteLogo.setFitWidth(104);
		fortniteLogo.setFitHeight(148);
		
		
		ImageView psLogo = new ImageView(new Image("ps4Logo.png"));
		psLogo.setFitHeight(30);
		psLogo.setFitWidth(30);
		btnPS4.setGraphic(psLogo);
		ImageView xboxLogo = new ImageView(new Image("xboxLogo.png"));
		xboxLogo.setFitHeight(30);
		xboxLogo.setFitWidth(30);
		btnXbox.setGraphic(xboxLogo);
		ImageView pcLogo = new ImageView(new Image("pcIcon.png"));
		pcLogo.setFitHeight(30);
		pcLogo.setFitWidth(30);
		btnPC.setGraphic(pcLogo);
		
		AnchorPane.setTopAnchor(fortniteLogo, 64.0);
		AnchorPane.setLeftAnchor(fortniteLogo, 196.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 87.0);
		AnchorPane.setLeftAnchor(lblTitle, 320.0);
		
		AnchorPane.setTopAnchor(lblDesc, 118.0);
		AnchorPane.setLeftAnchor(lblDesc, 320.0);
		
		AnchorPane.setTopAnchor(btnPS4, 228.0);
		AnchorPane.setLeftAnchor(btnPS4, 230.0);
		
		AnchorPane.setTopAnchor(btnXbox, 264.0);
		AnchorPane.setLeftAnchor(btnXbox, 230.0);
		
		AnchorPane.setTopAnchor(btnPC, 300.0);
		AnchorPane.setLeftAnchor(btnPC, 230.0);
	
		ap.getChildren().addAll(fortniteLogo,lblTitle,lblDesc,btnPS4,btnXbox,btnPC,btnReturn);
	
		fortniteScene = new Scene(ap,600,400);
	
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
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Call Of Duty: \nAdvanced Warefare"); 
		lblTitle.setFont(new Font(24));
		Label lblDesc = new Label("Activision");
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenMainDashboard());
		JFXButton btnPS4 = new JFXButton("PS4");
		JFXButton btnXbox = new JFXButton("Xbox One");
		JFXButton btnPC = new JFXButton("PC");
		
		ImageView codLogo = new ImageView(new Image("Cod.jpg"));
		codLogo.setFitWidth(104);
		codLogo.setFitHeight(148);
		
		ImageView psLogo = new ImageView(new Image("ps4Logo.png"));
		psLogo.setFitHeight(30);
		psLogo.setFitWidth(30);
		btnPS4.setGraphic(psLogo);
		ImageView xboxLogo = new ImageView(new Image("xboxLogo.png"));
		xboxLogo.setFitHeight(30);
		xboxLogo.setFitWidth(30);
		btnXbox.setGraphic(xboxLogo);
		ImageView pcLogo = new ImageView(new Image("pcIcon.png"));
		pcLogo.setFitHeight(30);
		pcLogo.setFitWidth(30);
		btnPC.setGraphic(pcLogo);
		
		AnchorPane.setTopAnchor(codLogo, 64.0);
		AnchorPane.setLeftAnchor(codLogo, 196.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 87.0);
		AnchorPane.setLeftAnchor(lblTitle, 320.0);
		
		AnchorPane.setTopAnchor(lblDesc, 155.0);
		AnchorPane.setLeftAnchor(lblDesc, 320.0);
		
		AnchorPane.setTopAnchor(btnPS4, 228.0);
		AnchorPane.setLeftAnchor(btnPS4, 230.0);
		
		AnchorPane.setTopAnchor(btnXbox, 264.0);
		AnchorPane.setLeftAnchor(btnXbox, 230.0);
		
		AnchorPane.setTopAnchor(btnPC, 300.0);
		AnchorPane.setLeftAnchor(btnPC, 230.0);
	
		ap.getChildren().addAll(codLogo,lblTitle,lblDesc,btnPS4,btnXbox,btnPC,btnReturn);
	
		codScene = new Scene(ap,600,400);
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
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Halo 5"); 
		lblTitle.setFont(new Font(24));
		Label lblDesc = new Label("Bungee");
		JFXButton btnReturn = new JFXButton("<-");
		//btnReturn.setGraphic(backArrow);
		btnReturn.setOnAction(e-> OpenMainDashboard());
		
		JFXButton btnXbox = new JFXButton("Xbox One");
		
		ImageView haloLogo = new ImageView(new Image("Halo.jpg"));
		haloLogo.setFitWidth(104);
		haloLogo.setFitHeight(148);
	
		ImageView xboxLogo = new ImageView(new Image("xboxLogo.png"));
		xboxLogo.setFitHeight(30);
		xboxLogo.setFitWidth(30);
		btnXbox.setGraphic(xboxLogo);
		
		AnchorPane.setTopAnchor(haloLogo, 64.0);
		AnchorPane.setLeftAnchor(haloLogo, 196.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 87.0);
		AnchorPane.setLeftAnchor(lblTitle, 320.0);
		
		AnchorPane.setTopAnchor(lblDesc, 118.0);
		AnchorPane.setLeftAnchor(lblDesc, 320.0);
		
		AnchorPane.setTopAnchor(btnXbox, 228.0);
		AnchorPane.setLeftAnchor(btnXbox, 230.0);
		
	
		ap.getChildren().addAll(haloLogo,lblTitle,lblDesc,btnXbox,btnReturn);
	
		haloScene = new Scene(ap,600,400);
	}
	
	/**
	 * Set main window to the haloScene
	 */
	public void OpenHaloScene()
	{
		window.setScene(haloScene);
	}


	/**
	 * Creates scene to join Call Of Duty tournament
	 */
	public void CreateCodTourneyScene()
	{
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenMainDashboard());
		
		Label lblTitle = new Label("4v4 Search and Destroy 4/18");
		lblTitle.setFont(new Font(24));
		Label lblLocation = new Label("NY - 5 Teams");
		Label lblPrize = new Label("Prize");
		Label lblBracketSize = new Label("Bracket Size");
		Label lblTeamsJoined = new Label("Teams Joined");
		
		Label lblPrizeAmt = new Label("$1000");
		Label lblBracketAmt = new Label("5");
		Label lblTeamsJoinedVal = new Label("0");
		

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 42.0);
		AnchorPane.setLeftAnchor(lblTitle, 137.0);
		
		AnchorPane.setTopAnchor(lblLocation, 79.0);
		AnchorPane.setLeftAnchor(lblLocation, 258.0);
		
		AnchorPane.setTopAnchor(lblPrize, 149.0);
		AnchorPane.setLeftAnchor(lblPrize, 137.0);
		
		AnchorPane.setTopAnchor(lblBracketSize, 186.0);
		AnchorPane.setLeftAnchor(lblBracketSize, 137.0);
		
		AnchorPane.setTopAnchor(lblTeamsJoined, 223.0);
		AnchorPane.setLeftAnchor(lblTeamsJoined, 137.0);
		
		AnchorPane.setTopAnchor(lblPrizeAmt, 149.0);
		AnchorPane.setLeftAnchor(lblPrizeAmt, 342.0);
		
		AnchorPane.setTopAnchor(lblBracketAmt, 186.0);
		AnchorPane.setLeftAnchor(lblBracketAmt, 342.0);
		
		AnchorPane.setTopAnchor(lblTeamsJoinedVal, 223.0);
		AnchorPane.setLeftAnchor(lblTeamsJoinedVal, 342.0);

		AnchorPane.setTopAnchor(btnJoinTeam, 267.0);
		AnchorPane.setLeftAnchor(btnJoinTeam, 120.0);
		
		AnchorPane.setTopAnchor(btnCreateTeam, 267.0);
		AnchorPane.setLeftAnchor(btnCreateTeam, 300.0);
		
		ap.getChildren().addAll(btnReturn,lblTitle, lblLocation,lblPrize,lblBracketSize,lblTeamsJoined,lblPrizeAmt,lblBracketAmt,lblTeamsJoinedVal,btnJoinTeam,btnCreateTeam);
		
		codTourney = new Scene(ap, 600,400);
	}

	/**
	 * Set main window to the codTourney scene
	 */
	public void OpenCodTourney()
	{
		window.setScene(codTourney);
	}

















}
