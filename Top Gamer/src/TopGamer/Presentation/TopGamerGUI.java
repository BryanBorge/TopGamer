package TopGamer.Presentation;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.Event;
import java.io.IOException;
import java.awt.Desktop.Action;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import javax.lang.model.AnnotatedConstruct;

import org.controlsfx.control.textfield.TextFields;
import org.sqlite.SQLiteException;
import org.w3c.dom.css.ElementCSSInlineStyle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import TopGamer.Business.*;


public class TopGamerGUI extends Application
{
	
	User currentUser = new User();
	Tournament testTourney = new Tournament();
	boolean loggedIn = false;
	
	Stage window,gameStage;
	Scene loginScene, registerScene, mainDashboardScene;
	Scene fortniteScene, codScene, haloScene;
	Scene codTourney;
	Scene createTeam, joinTeam, viewRTeam;
	
	ImageView backArrow = new ImageView(new Image("back arrow.png"));
	
	
	//Used for user login y
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
	RadioButton rbXbox;
	RadioButton rbPS4;
	RadioButton rbPC;
	RadioButton rbSelectedPlatform;
	String platformName;
	ToggleGroup platformGroup;
	
	
	//Profile drop down
	JFXButton btnProfile = new JFXButton("Profile");
	JFXButton btnEditProfile = new JFXButton("Settings");
	JFXButton btnLogOut = new JFXButton("Edit Profile");
	JFXNodesList nodeList = new JFXNodesList();
	
	//Labels to display if login info isnt valid
	Label lblValidLoginUser,lblValidLoginPass;
	
	
	//Tournaments 
	Tournament codSNDTourney;
	
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
		CreateTeamTournament1();
		CreateJoinTeam();
		
		window.getIcons().add(new Image("icon.png"));
		window.setTitle("Top Gamer");
		//Parent root = FXMLLoader.load(getClass().getResource(("LoginScreen.fxml")));
		//Scene scene = new Scene(root);
		//window.setScene(scene);
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
		txtLoginName.setPromptText("Username");
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
					try
					{
						Valid = currentUser.Login(txtLoginName.getText(), txtLoginPass.getText());	
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
	 * Userna)me cannot be blank and must be a string (will allow users to add numbers later on)
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
		
		HBox platformHbox = new HBox();
		platformHbox.setSpacing(5);
		platformGroup = new ToggleGroup();
		rbXbox = new RadioButton("Xbox One");
		rbXbox.setToggleGroup(platformGroup);
		rbPS4 = new RadioButton("PS4");
		rbPS4.setToggleGroup(platformGroup);
		rbPC = new RadioButton("PC");
		rbPC.setToggleGroup(platformGroup);
		platformHbox.getChildren().addAll(rbXbox, rbPS4, rbPC);

				
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
		
		AnchorPane.setLeftAnchor(platformHbox, 220.0);
		AnchorPane.setTopAnchor(platformHbox, 335.0);
		
		AnchorPane.setLeftAnchor(btnSignUp, 255.0);
		AnchorPane.setTopAnchor(btnSignUp, 360.0);
		
		ap.getChildren().addAll(btnReturn,txtFName,txtLName,txtEmail,txtUserName,txtPass,btnSignUp,lblValidFirstName,lblValidLastName,lblValidEmail,lblValidUserName,lblValidPass,platformHbox);
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
				rbSelectedPlatform = (RadioButton)platformGroup.getSelectedToggle();
				s.AddUser(txtFName.getText(), txtLName.getText(), txtEmail.getText(), txtUserName.getText(), txtPass.getText(),rbSelectedPlatform.getText());
				btnProfile.setText(txtUserName.getText());
				txtFName.setText("");
				txtLName.setText("");
				txtEmail.setText("");
				txtUserName.setText("");
				txtPass.setText("");
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("User Registration");
				alert.setHeaderText("Registration successful");
				alert.setContentText("Welcome!");
				alert.showAndWait();
				window.setScene(mainDashboardScene);
			} catch (SQLiteException e) {
				System.out.println("Error saving user to database");
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
		if(Empty(txtFName))
		{
			lblValidFirstName.setText("First cannot be empty");
			lblValidFirstName.setTextFill(Color.RED);
			return false;
		}
		if(Empty(txtLName))
		{
			lblValidLastName.setText("Last name cannot be empty");
			lblValidLastName.setTextFill(Color.RED);
			return false;
		}
		if(Empty(txtEmail))
		{
			lblValidEmail.setText("Email cannot be empty");
			lblValidEmail.setTextFill(Color.RED);
			return false;
		}
		if(Empty(txtUserName))
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
		loggedIn = true;
		return true;
	}
	
	
	
	
	/**
	 * Sets main window to the mainDashboard scene
	 */
	public void OpenMainDashboard()
	{
		if(loggedIn)
			btnProfile.setText(currentUser.GetUsername());
		 System.out.println(currentUser.GetFirstName() + " " + currentUser.GetLastName() + " " + currentUser.GetEmail());
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
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e ->{ 		
			OpenLoginScene();
		});
	
		btnLogOut.setOnAction(e -> {
			loggedIn = false;
			btnProfile.setText("Profile");
			currentUser.SetFirstName("");
			currentUser.SetLastName("");
			currentUser.SetEmail("");
			currentUser.SetUsername("");
			String logOutTitle = "Logged Out";
			String logOutContent = "You have successfully logged out";
			
			JFXDialogLayout dialogContent = new JFXDialogLayout();
			dialogContent.setHeading(new Text(logOutTitle));
			dialogContent.setBody(new Text(logOutContent));
			StackPane stackPane = new StackPane();
			stackPane.autosize();
			JFXDialog dialog = new JFXDialog(stackPane,dialogContent, JFXDialog.DialogTransition.LEFT);
			JFXButton btn = new JFXButton("Okay");
			dialog.show();
			
			
			OpenLoginScene();
			
		});
		
		nodeList = new JFXNodesList();
		nodeList.setRotate(90);
		nodeList.setSpacing(30.0);
		nodeList.addAnimatedNode(btnProfile);
		nodeList.addAnimatedNode(btnLogOut);
		nodeList.addAnimatedNode(btnEditProfile);
		
		VBox mainVbox = new VBox();
		ScrollPane mainScroll = new ScrollPane();
		mainScroll.setLayoutX(0.0);
		mainScroll.setLayoutY(40.0);
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
		
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
	
		//add images to anchor pane
		anchorHeader.getChildren().addAll(nodeList,btnReturn);
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
		JFXButton btnTournament1 = new JFXButton("Tournament 1");
		JFXButton btnTournament2 = new JFXButton("Tournament 2");
		
		
		ImageView fortniteLogo = new ImageView(new Image("Fortnite.jpg"));
		fortniteLogo.setFitWidth(104);
		fortniteLogo.setFitHeight(148);
		
		AnchorPane.setTopAnchor(fortniteLogo, 25.0);
		AnchorPane.setLeftAnchor(fortniteLogo, 50.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 15.0);
		AnchorPane.setLeftAnchor(lblTitle, 175.0);
		
		AnchorPane.setTopAnchor(lblDesc, 50.0);
		AnchorPane.setLeftAnchor(lblDesc, 175.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 228.0);
		AnchorPane.setLeftAnchor(btnTournament1, 230.0);
		
		AnchorPane.setTopAnchor(btnTournament2, 264.0);
		AnchorPane.setLeftAnchor(btnTournament2, 230.0);
		
	
		ap.getChildren().addAll(fortniteLogo,lblTitle,lblDesc,btnTournament1,btnTournament2,btnReturn);
	
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
		JFXButton btnTournament1 = new JFXButton("Tournament 1");
		JFXButton btnTournament2 = new JFXButton("Tournament 2");
		
		
		ImageView codLogo = new ImageView(new Image("Cod.jpg"));
		codLogo.setFitWidth(104);
		codLogo.setFitHeight(148);

		AnchorPane.setTopAnchor(codLogo, 25.0);
		AnchorPane.setLeftAnchor(codLogo, 50.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 15.0);
		AnchorPane.setLeftAnchor(lblTitle, 175.0);
		
		AnchorPane.setTopAnchor(lblDesc, 85.0);
		AnchorPane.setLeftAnchor(lblDesc, 175.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 228.0);
		AnchorPane.setLeftAnchor(btnTournament1, 230.0);
		
		AnchorPane.setTopAnchor(btnTournament2, 264.0);
		AnchorPane.setLeftAnchor(btnTournament2, 230.0);
		
	
		ap.getChildren().addAll(codLogo,lblTitle,lblDesc,btnTournament1,btnTournament2,btnReturn);
	
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
		
		JFXButton btnTournament1 = new JFXButton("Tournament 1");
		JFXButton btnTournament2 = new JFXButton("Tournament 2");
		
		ImageView haloLogo = new ImageView(new Image("Halo.jpg"));
		haloLogo.setFitWidth(104);
		haloLogo.setFitHeight(148);
		
		AnchorPane.setTopAnchor(haloLogo, 25.0);
		AnchorPane.setLeftAnchor(haloLogo, 50.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 15.0);
		AnchorPane.setLeftAnchor(lblTitle, 175.0);
		
		AnchorPane.setTopAnchor(lblDesc, 45.0);
		AnchorPane.setLeftAnchor(lblDesc, 175.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 228.0);
		AnchorPane.setLeftAnchor(btnTournament1, 230.0);
		
		AnchorPane.setTopAnchor(btnTournament2, 264.0);
		AnchorPane.setLeftAnchor(btnTournament2, 230.0);
		
		ap.getChildren().addAll(haloLogo,lblTitle,lblDesc,btnTournament1,btnTournament2,btnReturn);
	
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
		//Tournament tourn = new Tournament(); // Tom
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnViewRegisteredTeams= new JFXButton("View Registered Teams"); // Tom
		btnJoinTeam.setOnAction(e->OpenJoinTeam());
		btnViewRegisteredTeams.setOnAction(e->OpenViewRegisteredTeams()); // Tom
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenMainDashboard());
		
		codSNDTourney = new Tournament();
		codSNDTourney.LoadTournamentData(3);

		btnCreateTeam.setOnAction(e->{
			if(codSNDTourney.GetTeamsJoined() < codSNDTourney.GetBrackSize())
				OpenCreateTeamTournament1();			
			else {		
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Cannot create team");
				alert.setContentText("Bracket size has been reached");
				alert.showAndWait();
				return;
			}			
	});
		
		Label lblTitle = new Label(codSNDTourney.GetTournamentName() + "(" + codSNDTourney.GetGame().GetPlatform().GetPlatformName() + ")");
		lblTitle.setFont(new Font(24));
		Label lblLocation = new Label(codSNDTourney.GetLocation() + " " + codSNDTourney.GetDate());
		Label lblPrize = new Label("Prize");
		Label lblBracketSize = new Label("Bracket Size");
		Label lblTeamsJoined = new Label("Teams Joined");
		
		Label lblPrizeAmt = new Label("$" + codSNDTourney.GetPrize());
		Label lblBracketAmt = new Label(String.valueOf(codSNDTourney.GetBrackSize()));
		Label lblTeamsJoinedVal = new Label(String.valueOf(codSNDTourney.GetTeamsJoined()));
		
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 42.0);
		AnchorPane.setLeftAnchor(lblTitle, 137.0);
		
		AnchorPane.setTopAnchor(lblLocation, 79.0);
		AnchorPane.setLeftAnchor(lblLocation, 210.0);
		
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
		
		AnchorPane.setTopAnchor(btnViewRegisteredTeams, 310.0); // Tom
		AnchorPane.setLeftAnchor(btnViewRegisteredTeams, 120.0); // Tom
		
		ap.getChildren().addAll(btnReturn,lblTitle, lblLocation,lblPrize,lblBracketSize,lblTeamsJoined,lblPrizeAmt,lblBracketAmt,lblTeamsJoinedVal,btnJoinTeam,btnCreateTeam, btnViewRegisteredTeams); // Tom
		
		codTourney = new Scene(ap, 600,400);
	}

	/**
	 * Set main window to the codTourney scene
	 */
	public void OpenCodTourney()
	{
		CreateCodTourneyScene();
		window.setScene(codTourney);
	}

	
	public void CreateTeamTournament1() 
	{
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
		
		JFXTextField txtTeamName = new JFXTextField();
		txtTeamName.setLabelFloat(true);
		txtTeamName.setPromptText("Team name");
		txtTeamName.setLayoutX(211.0);
		txtTeamName.setLayoutY(78.0);
		
		JFXTextField txtMember1 = new JFXTextField();
		txtMember1.setLabelFloat(true);
		txtMember1.setPromptText("Team member 1");
		txtMember1.setLayoutX(211.0);
		txtMember1.setLayoutY(125.0);
		
		JFXTextField txtMember2 = new JFXTextField();
		txtMember2.setLabelFloat(true);
		txtMember2.setPromptText("Team member 2");
		txtMember2.setLayoutX(211.0);
		txtMember2.setLayoutY(166.0);
		
		JFXTextField txtMember3 = new JFXTextField();
		txtMember3.setLabelFloat(true);
		txtMember3.setLabelFloat(true);
		txtMember3.setPromptText("Team member 3");
		txtMember3.setLayoutX(211.0);
		txtMember3.setLayoutY(212.0);
		
		JFXButton btnCreateTeam = new JFXButton("Create team");
		btnCreateTeam.setLayoutX(220.0);
		btnCreateTeam.setLayoutY(279.0);
		btnCreateTeam.prefWidth(135.0);
		
		
		//if user is not logged in/not registered they cannot create a team
		btnCreateTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to create a team");
				alert.showAndWait();
			}else {
				
				if(Empty(txtTeamName)) {
					System.out.println("Team name empty");
					e.consume();
				}
				else {
					SQLConnection sqlConnection = new SQLConnection();
					sqlConnection.CreateTeam(txtTeamName.getText(), currentUser.GetUsername(),txtMember1.getText(), txtMember2.getText(), txtMember3.getText());
					sqlConnection.AddTeamToTournament(txtTeamName.getText(), 3);
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Team Created");
					alert.setHeaderText("Team created");
					alert.setContentText("Team has been successfully created");
					alert.showAndWait();
					txtTeamName.setText("");
					txtMember1.setText("");
					txtMember2.setText("");
					txtMember3.setText("");
					OpenCodTourney();
				}
			}			
		});
		
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenCodTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		//load all users not currently on a team
		ArrayList<String> users = new ArrayList<String>();
		users = codSNDTourney.LoadAllAvailavleUsernames();
		TextFields.bindAutoCompletion(txtMember1, users);
		TextFields.bindAutoCompletion(txtMember2, users);
		TextFields.bindAutoCompletion(txtMember3, users);
		
		aPane.getChildren().addAll(txtTeamName,txtMember1,txtMember2,txtMember3,btnCreateTeam,btnReturn);
		
		createTeam = new Scene(aPane);
	}	
	public void OpenCreateTeamTournament1()
	{
		window.setScene(createTeam);
	}


	public void CreateJoinTeam()
	{
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
		Tournament codSNDTourney = new Tournament();
		codSNDTourney.LoadTournamentData(1);
		SQLConnection se = new SQLConnection();
		
		JFXTextField txtTeamName = new JFXTextField();
		txtTeamName.setLabelFloat(true);
		txtTeamName.setPromptText("Team Name");
		txtTeamName.setLayoutX(211.0);
		txtTeamName.setLayoutY(166.0);
		
		JFXButton btnJoinTeam = new JFXButton("Join Team");
		btnJoinTeam.setLayoutX(220.0);
		btnJoinTeam.setLayoutY(279.0);
		btnJoinTeam.prefWidth(135.0);
		btnJoinTeam.setOnAction(e->{
			se.JoinTeam(currentUser.GetUsername(),txtTeamName.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Teamed Joined");
			alert.setHeaderText("Team Joined");
			alert.setContentText("You have joined the team: Team");
			alert.showAndWait();
			
			OpenCodTourney();
			
			});
		
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenCodTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		ArrayList<String> openTeams = new ArrayList<String>();
		SQLConnection sqlConnection = new SQLConnection();
		openTeams = sqlConnection.LoadAllOpenTeams();
		TextFields.bindAutoCompletion(txtTeamName, openTeams);
		
		aPane.getChildren().addAll(txtTeamName,btnJoinTeam,btnReturn);
		
		joinTeam = new Scene(aPane);
	}
	public void OpenJoinTeam()
	{
		CreateJoinTeam();
		window.setScene(joinTeam);
	}


	public void CreateViewRegisteredTeams(){ 	//tom
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
		Tournament codSNDTourney = new Tournament();
		codSNDTourney.LoadTournamentData(3);
		
		Team registered= new Team();
		registered=codSNDTourney.ViewRegisterdTeams(codSNDTourney.GetID());
		System.out.println(registered.GetTeamName());
		for (User u: registered.GetAllTeamMembers() ) {
			System.out.println(u.GetUsername());
			
		}
		
		/*JFXTextField txtTeamName = new JFXTextField();
		txtTeamName.setLabelFloat(true);
		txtTeamName.setPromptText("Team Name");
		txtTeamName.setLayoutX(211.0);
		txtTeamName.setLayoutY(166.0);
		*/
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenCodTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		aPane.getChildren().addAll(btnReturn);
		// need to use viewregisterdteams function from tournamnet, however it request a team id as input so if hardcoded in this 
		// function it wont work properly, also you cant just leave it in herre for when you call the function it has no paramaters
		// after you will use the viewregisterdteams function it will read in and return the value , should it be returning to a 
		// array list paramter? then use a grid pane to display?
		
		viewRTeam= new Scene(aPane);
	}

	public void OpenViewRegisteredTeams()	//tom
	{
		CreateViewRegisteredTeams();
		window.setScene(viewRTeam);
	}







}
