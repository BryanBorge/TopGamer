package TopGamer.Presentation;


import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.NodeOrientation;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
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
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.transform.Templates;

import org.controlsfx.control.textfield.TextFields;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;
import org.sqlite.SQLiteException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import TopGamer.Business.*;


public class TopGamerGUI extends Application
{
	
	User currentUser = new User();
	Tournament testTourney = new Tournament();
	boolean loggedIn = false;
	
	Stage window,gameStage;
	Scene loginScene, registerScene, mainDashboardScene;
	Scene fortniteScene, codScene, haloScene;

	Scene codTourneyScene, fortniteTourneyScene, haloTourneyScene;
	
	Scene createTeam, joinTeam, viewRegisteredTeams;
	
	ImageView backArrow = new ImageView(new Image("back arrow.png"));
	
	StackPane mainDashStack = new StackPane();
	StackPane registerStack = new StackPane();
	
	//Used for user login s
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
	Tournament codTournament;
	Tournament fortniteTournament;
	Tournament haloTournament;



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

		window.getIcons().add(new Image("icon.png"));
		window.setTitle("Top Gamer");
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
		registerStack.getChildren().add(ap);
		registerScene = new Scene(registerStack,600,400);
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
				
				//set current user to the registered user
				currentUser.Login(txtUserName.getText(), txtPass.getText());
				
				String logOutTitle = "Registration Successful";
				String logOutContent = "Welcome!";
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text(logOutTitle));
				dialogContent.setBody(new Text(logOutContent));
				JFXDialog dialog = new JFXDialog();
				JFXButton button = new JFXButton("Okay");
				button.setOnAction(ev->{
					dialog.close(); 
					OpenMainDashboard();
					txtFName.setText("");
					txtLName.setText("");
					txtEmail.setText("");
					txtUserName.setText("");
					txtPass.setText("");
				});
				dialog.setContent(dialogContent);
				dialog.getChildren().add(button);
				dialog.setDialogContainer(registerStack);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(button);
				dialog.show();
				
				

			} catch (SQLException e) {
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
	
	
	public void CreateProfileButton()
	{
		btnProfile = new JFXButton("Profile");
		btnEditProfile = new JFXButton("Edit profile");
		btnLogOut= new JFXButton("Log out");
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
		StackPane testStack = new StackPane();
		btnProfile = new JFXButton("Profile");
		btnEditProfile = new JFXButton("Edit profile");
		btnLogOut= new JFXButton("Log out");
		
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e ->{ 		
			OpenLoginScene();
		});
	
		btnLogOut.setOnAction(e -> {
	
			String logOutTitle = "Logging out...";
			String logOutContent = "Are you sure you want to log out?";
			JFXDialogLayout dialogContent = new JFXDialogLayout();
			dialogContent.setHeading(new Text(logOutTitle));
			dialogContent.setBody(new Text(logOutContent));
			JFXDialog dialog = new JFXDialog();
			JFXButton btnlogOut = new JFXButton("Log out");
			JFXButton btncancel = new JFXButton("Cancel");
			btnlogOut.setOnAction(ev->{ 
				loggedIn = false;
				btnProfile.setText("Profile");
				currentUser.SetFirstName("");
				currentUser.SetLastName("");
				currentUser.SetEmail("");
				currentUser.SetUsername("");
				dialog.close(); 
				OpenLoginScene();
			});
			btncancel.setOnAction(ev->dialog.close());
			dialog.setContent(dialogContent);
			dialog.getChildren().add(btnlogOut);
			dialog.setDialogContainer(mainDashStack);
			dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
			dialogContent.setActions(btnlogOut,btncancel);
			dialog.show();
			
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
		
		JFXButton codTourney = new JFXButton("4v4 CoD Tournament");
		codTourney.setOnAction(e -> OpenCodTourney());
		JFXButton fortniteTourney = new JFXButton("4v4 Fortnite Tournament");
		fortniteTourney.setOnAction(e->OpenFortniteTourney());
		JFXButton haloTourney = new JFXButton("4v4 Halo Tournament");
		haloTourney.setOnAction(e->OpenHaloTourney());
		
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
		AnchorPane.setTopAnchor(codTourney, 300.0);
		AnchorPane.setLeftAnchor(codTourney, 14.0);
		AnchorPane.setTopAnchor(fortniteTourney, 400.0);
		AnchorPane.setLeftAnchor(fortniteTourney, 14.0);
		AnchorPane.setTopAnchor(haloTourney, 500.0);
		AnchorPane.setLeftAnchor(haloTourney, 14.0);
		
		//this goes in the anchorHeader
		AnchorPane.setTopAnchor(nodeList, 6.0);
		AnchorPane.setRightAnchor(nodeList, 14.0);
		
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
	
		//add images to anchor pane
		anchorHeader.getChildren().addAll(nodeList,btnReturn);
		anchorScroll.getChildren().addAll(lblFeaturedGames,fortniteLogo,codLogo,haloLogo,codTourney,fortniteTourney,haloTourney);
		mainScroll.setContent(anchorScroll);
		mainVbox.getChildren().addAll(anchorHeader,mainScroll);
		mainDashStack.getChildren().add(mainVbox);
		mainDashboardScene = new Scene(mainDashStack,600,400);
	
	}
	
	
	/**
	 * Create/Add controls to the fortniteScene
	 */
	public void CreateFortniteScene()
	{
		fortniteTournament = new Tournament();
		fortniteTournament.LoadTournamentData(6);
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Fortnite"); 
		lblTitle.setFont(new Font(24));
		Label lblDesc = new Label("Epic Games");
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenMainDashboard());
		JFXButton btnTournament1 = new JFXButton("Friday Fortnite");
		btnTournament1.setOnAction(e->OpenFortniteTourney());
		
		
		ImageView fortniteLogo = new ImageView(new Image("Fortnite.jpg"));
		fortniteLogo.setFitWidth(104);
		fortniteLogo.setFitHeight(148);
		
		//leader board table view
		TableView<Team> leaderBoardTable = new TableView<>();
		leaderBoardTable.setPrefHeight(200);
		leaderBoardTable.setPrefWidth(300);
		leaderBoardTable.setPlaceholder(new Label("No tournaments have been played for this game yet"));
				
		//team name column
		TableColumn<Team, String> teamNameCol = new TableColumn<>("Team Name");
		teamNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		teamNameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().GetTeamName()));
				
		//wins column
		TableColumn<Team, String> winsCol = new TableColumn<>("Wins");
		winsCol.setCellFactory(TextFieldTableCell.forTableColumn());
		winsCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().WinsToString()));
				
		//losses column
		TableColumn<Team, String> lossesCol = new TableColumn<>("Losses");
		lossesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lossesCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().LossesToString()));
				
		//add columns to the list view
		leaderBoardTable.getColumns().addAll(teamNameCol,winsCol,lossesCol);
				

		//load leader board to hold data for that tournament
		Leaderboard fortniteLeaderboard = new Leaderboard();
		fortniteLeaderboard.LoadLeaderboardData(fortniteTournament);
				
		//add each team to the list view
		for(Team t : fortniteLeaderboard.GetTeams())
		{
			leaderBoardTable.getItems().add(t);
		}
				
		AnchorPane.setTopAnchor(fortniteLogo, 25.0);
		AnchorPane.setLeftAnchor(fortniteLogo, 50.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 15.0);
		AnchorPane.setLeftAnchor(lblTitle, 175.0);
		
		AnchorPane.setTopAnchor(lblDesc, 50.0);
		AnchorPane.setLeftAnchor(lblDesc, 175.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 200.0);
		AnchorPane.setLeftAnchor(btnTournament1, 62.0);
		
		AnchorPane.setTopAnchor(leaderBoardTable, 115.0);
		AnchorPane.setLeftAnchor(leaderBoardTable, 175.0);
		
		ap.getChildren().addAll(fortniteLogo,lblTitle,lblDesc,btnTournament1,btnReturn,leaderBoardTable);
	
		fortniteScene = new Scene(ap,600,400);
	
	}
	/**
	 * Set main window the the fortniteScene
	 */
	public void OpenFortniteScene()
	{
		CreateFortniteScene();
		window.setScene(fortniteScene);
	}
	
	/**
	 * Create/Add controls to the codScene
	 */
	public void CreateCODScene()
	{
		codTournament = new Tournament();
		codTournament.LoadTournamentData(3);
		
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Call Of Duty: \nAdvanced Warefare"); 
		lblTitle.setFont(new Font(24));
		Label lblDesc = new Label("Activision");
		JFXButton btnReturn = new JFXButton("<-");

		btnReturn.setOnAction(e-> OpenMainDashboard());
		JFXButton btnTournament1 = new JFXButton("4v4 Cod Tourney");

		
		btnTournament1.setOnAction(e->OpenCodTourney());
		
		ImageView codLogo = new ImageView(new Image("Cod.jpg"));
		codLogo.setFitWidth(104);
		codLogo.setFitHeight(148);
	
		//leader board table view
		TableView<Team> leaderBoardTable = new TableView<>();
		leaderBoardTable.setPrefHeight(250);
		leaderBoardTable.setPrefWidth(350);
		leaderBoardTable.setPlaceholder(new Label("No tournaments have been played for this game yet"));
				
		//team name column
		TableColumn<Team, String> teamNameCol = new TableColumn<>("Team Name");
		teamNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		teamNameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().GetTeamName()));
			
		//wins column
		TableColumn<Team, String> winsCol = new TableColumn<>("Wins");
		winsCol.setCellFactory(TextFieldTableCell.forTableColumn());
		winsCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().WinsToString()));
		
		//losses column
		TableColumn<Team, String> lossesCol = new TableColumn<>("Losses");
		lossesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lossesCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().LossesToString()));
			
		//add columns to the list view
		leaderBoardTable.getColumns().addAll(teamNameCol,winsCol,lossesCol);
		
		//load leader board to hold data for that tournament
		Leaderboard codLeaderboard = new Leaderboard();
		codLeaderboard.LoadLeaderboardData(codTournament);
		
		//add each team to the list view
		for(Team t : codLeaderboard.GetTeams())
		{
			leaderBoardTable.getItems().add(t);
		}

		AnchorPane.setTopAnchor(codLogo, 25.0);
		AnchorPane.setLeftAnchor(codLogo, 50.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 15.0);
		AnchorPane.setLeftAnchor(lblTitle, 175.0);
		
		AnchorPane.setTopAnchor(lblDesc, 85.0);
		AnchorPane.setLeftAnchor(lblDesc, 175.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 200.0);
		AnchorPane.setLeftAnchor(btnTournament1, 62.0);
		
		AnchorPane.setTopAnchor(leaderBoardTable, 115.0);
		AnchorPane.setLeftAnchor(leaderBoardTable, 175.0);
		
		
		ap.getChildren().addAll(codLogo,lblTitle,lblDesc,btnTournament1,btnReturn,leaderBoardTable);
	
		codScene = new Scene(ap,600,400);
	}
	/**
	 * Set main window to the codScene
	 */
	public void OpenCodScene()
	{
		CreateCODScene();
		window.setScene(codScene);
	}
	
	
	/**
	 * Create/Add controls to the haloScene
	 */
	public void CreateHaloScene()
	{
		haloTournament = new Tournament();
		haloTournament.LoadTournamentData(11);
		
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Halo 5"); 
		lblTitle.setFont(new Font(24));
		Label lblDesc = new Label("Bungee");
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenMainDashboard());
		

		JFXButton btnTournament1 = new JFXButton("4v4 Team Slayer");
		btnTournament1.setOnAction(e->OpenHaloTourney());

		
		ImageView haloLogo = new ImageView(new Image("Halo.jpg"));
		haloLogo.setFitWidth(104);
		haloLogo.setFitHeight(148);
		
		
		//leader board table view
		TableView<Team> leaderBoardTable = new TableView<>();
		leaderBoardTable.setPrefHeight(200);
		leaderBoardTable.setPrefWidth(300);
		leaderBoardTable.setPlaceholder(new Label("No tournaments have been played for this game yet"));
		
		//team name column
		TableColumn<Team, String> teamNameCol = new TableColumn<>("Team Name");
		teamNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		teamNameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().GetTeamName()));
		
		//wins column
		TableColumn<Team, String> winsCol = new TableColumn<>("Wins");
		winsCol.setCellFactory(TextFieldTableCell.forTableColumn());
		winsCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().WinsToString()));
		
		//losses column
		TableColumn<Team, String> lossesCol = new TableColumn<>("Losses");
		lossesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lossesCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().LossesToString()));
		
		//add columns to the list view
		leaderBoardTable.getColumns().addAll(teamNameCol,winsCol,lossesCol);
		

		//load leader board to hold data for that tournament
		Leaderboard haloLeaderboard = new Leaderboard();
		haloLeaderboard.LoadLeaderboardData(haloTournament);
		
		//add each team to the list view
		for(Team t : haloLeaderboard.GetTeams())
		{
			leaderBoardTable.getItems().add(t);
		}
		
		
		AnchorPane.setTopAnchor(haloLogo, 25.0);
		AnchorPane.setLeftAnchor(haloLogo, 50.0);

		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 15.0);
		AnchorPane.setLeftAnchor(lblTitle, 175.0);
		
		AnchorPane.setTopAnchor(lblDesc, 45.0);
		AnchorPane.setLeftAnchor(lblDesc, 175.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 200.0);
		AnchorPane.setLeftAnchor(btnTournament1, 62.0);
		
		AnchorPane.setTopAnchor(leaderBoardTable, 75.0);
		AnchorPane.setLeftAnchor(leaderBoardTable, 175.0);
		
		
		ap.getChildren().addAll(haloLogo,lblTitle,lblDesc,btnTournament1,btnReturn,leaderBoardTable);
	
		haloScene = new Scene(ap,600,400);
	}
	/**
	 * Set main window to the haloScene
	 */
	public void OpenHaloScene()
	{
		CreateHaloScene();
		window.setScene(haloScene);
	}

	public void OpenHaloTourney() {
	
	CreateHaloTourneyScene();
	window.setScene(haloTourneyScene);
}
	public void CreateHaloTourneyScene(){// tom
		
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnViewRegisteredTeams= new JFXButton("View Registered Teams"); // Tom

		Leaderboard leaderboard = new Leaderboard();
		leaderboard.LoadLeaderboardData(haloTournament);
		
		btnJoinTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to join a team");
				alert.showAndWait();
				return;
				}
			if(!currentUser.GetPlatform().GetPlatformName().equals(haloTournament.GetGame().GetPlatform().GetPlatformName())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Incompatiable Systems");
				alert.setContentText("You play on " + currentUser.GetPlatform().GetPlatformName() + ". The tournament is on " + haloTournament.GetGame().GetPlatform().GetPlatformName());
				alert.showAndWait();
				return;
			}
			for(Team team : haloTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					System.out.println("Youre on a team");
					return;
				}
			}
			OpenJoinTeamHalo();
			});
		btnViewRegisteredTeams.setOnAction(e->OpenViewRegisteredTeams(haloTournament));
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenHaloScene()); // open halo?
		
		//checks what scene we are coming from and returning to it
		if(haloScene == window.getScene()) {
			btnReturn.setOnAction(e-> OpenHaloScene());
		}
		else
			btnReturn.setOnAction(e-> OpenMainDashboard());
				
		btnCreateTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to create a team");
				alert.showAndWait();
				return;
				}
			if(!currentUser.GetPlatform().GetPlatformName().equals(haloTournament.GetGame().GetPlatform().GetPlatformName())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Incompatiable Systems");
				alert.setContentText("You play on " + currentUser.GetPlatform().GetPlatformName() + ". The tournament is on " + haloTournament.GetGame().GetPlatform().GetPlatformName());
				alert.showAndWait();
				return;
			}
			for(Team team : haloTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					System.out.println("Youre on a team");
					return;
				}
			}
			if(haloTournament.GetTeamsJoined() < haloTournament.GetBrackSize())
				OpenCreateTeamTournamentHalo();	
			else {		
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Cannot create team");
				alert.setContentText("Bracket size has been reached");
				alert.showAndWait();
				return;
			}			
			
	});
		
		Label lblTitle = new Label(haloTournament.GetTournamentName() + "(" + haloTournament.GetGame().GetPlatform().GetPlatformName() + ")");
		lblTitle.setFont(new Font(24));
		Label lblLocation = new Label(haloTournament.GetLocation() + " " + haloTournament.GetDate());
		Label lblPrize = new Label("Prize");
		Label lblBracketSize = new Label("Bracket Size");
		Label lblTeamsJoined = new Label("Teams Joined");
		
		Label lblPrizeAmt = new Label("$" + haloTournament.GetPrize());
		Label lblBracketAmt = new Label(String.valueOf(haloTournament.GetBrackSize()));
		Label lblTeamsJoinedVal = new Label(String.valueOf(haloTournament.GetTeamsJoined()));
		
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
		
		haloTourneyScene = new Scene(ap, 600,400);
	}
	
	/**
	 * Create a scene to join fortnite tournament
	 */
	public void CreateFortniteTourneyScene() { // tom
		
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnViewRegisteredTeams= new JFXButton("View Registered Teams"); // Tom
	
		btnJoinTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to join a team");
				alert.showAndWait();
				return;
				}
			for(Team team : haloTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					System.out.println("Youre on a team");
					return;
				}
			}
			OpenJoinTeamFortnite();
			});
		btnViewRegisteredTeams.setOnAction(e->OpenViewRegisteredTeams(fortniteTournament));
		JFXButton btnReturn = new JFXButton("/<-");
		
		//checks what scene we are coming from and returning to it
		if(fortniteScene == window.getScene()) {
			btnReturn.setOnAction(e-> OpenFortniteScene());
		}
		else
			btnReturn.setOnAction(e-> OpenMainDashboard());
		

		btnCreateTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to create a team");
				alert.showAndWait();
				return;
				}
			for(Team team : fortniteTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					System.out.println("Youre on a team");
					return;
				}
			}
			if(fortniteTournament.GetTeamsJoined() < fortniteTournament.GetBrackSize())
				OpenCreateTeamTournamentFortnite();		
			else {		
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Cannot create team");
				alert.setContentText("Bracket size has been reached");
				alert.showAndWait();
				return;
			}	
			
	});
		
		Label lblTitle = new Label(fortniteTournament.GetTournamentName() + "(" + fortniteTournament.GetGame().GetPlatform().GetPlatformName() + ")");
		lblTitle.setFont(new Font(24));
		Label lblLocation = new Label(fortniteTournament.GetLocation() + " " + fortniteTournament.GetDate());
		Label lblPrize = new Label("Prize");
		Label lblBracketSize = new Label("Bracket Size");
		Label lblTeamsJoined = new Label("Teams Joined");
		
		Label lblPrizeAmt = new Label("$" + fortniteTournament.GetPrize());
		Label lblBracketAmt = new Label(String.valueOf(fortniteTournament.GetBrackSize()));
		Label lblTeamsJoinedVal = new Label(String.valueOf(fortniteTournament.GetTeamsJoined()));
		
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
		
		fortniteTourneyScene = new Scene(ap, 600,400);
	}
	public void OpenFortniteTourney() 
	{
		CreateFortniteTourneyScene();
		window.setScene(fortniteTourneyScene);
	}
	
	
	/**
	 * Set main window to the codTourney scene
	 */
	public void OpenCodTourney()
	{
		CreateCodTourneyScene();
		window.setScene(codTourneyScene);
	}
	/**
	 * Creates scene to join Call Of Duty tournament
	 */
	public void CreateCodTourneyScene()
	{
		
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnViewRegisteredTeams= new JFXButton("View Registered Teams"); // Tom

		btnJoinTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to join a team");
				alert.showAndWait();
				return;
				}
			if(!currentUser.GetPlatform().GetPlatformName().equals(codTournament.GetGame().GetPlatform().GetPlatformName())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Incompatiable Systems");
				alert.setContentText("You play on " + currentUser.GetPlatform().GetPlatformName() + ". The tournament is on " + codTournament.GetGame().GetPlatform().GetPlatformName());
				alert.showAndWait();
				return;
			}
			for(Team team : haloTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					System.out.println("Youre on a team");
					return;
				}
			}
			OpenJoinTeamCOD();
			});

		btnViewRegisteredTeams.setOnAction(e->OpenViewRegisteredTeams(codTournament));
		JFXButton btnReturn = new JFXButton("<-");

		//checks what scene we are coming from and returning to it
		if(codScene == window.getScene()) {
			btnReturn.setOnAction(e-> OpenCodScene());
		}
		else
			btnReturn.setOnAction(e-> OpenMainDashboard());
		

		btnCreateTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to create a team");
				alert.showAndWait();
				return;
				}
			if(!currentUser.GetPlatform().GetPlatformName().equals(codTournament.GetGame().GetPlatform().GetPlatformName())) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Incompatiable Systems");
				alert.setContentText("You play on " + currentUser.GetPlatform().GetPlatformName() + ". The tournament is on " + codTournament.GetGame().GetPlatform().GetPlatformName());
				alert.showAndWait();
				return;
			}
			if(codTournament.GetTeamsJoined() < codTournament.GetBrackSize())
				OpenCreateTeamTournamentCOD();			
			else {		
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Error");
				alert.setHeaderText("Cannot create team");
				alert.setContentText("Bracket size has been reached");
				alert.showAndWait();
				return;
			}
			for(Team team : haloTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					System.out.println("Youre on a team");
					return;
				}
			}
			
	});
		
		Label lblTitle = new Label(codTournament.GetTournamentName() + "(" + codTournament.GetGame().GetPlatform().GetPlatformName() + ")");
		lblTitle.setFont(new Font(24));
		Label lblLocation = new Label(codTournament.GetLocation() + " " + codTournament.GetDate());
		Label lblPrize = new Label("Prize");
		Label lblBracketSize = new Label("Bracket Size");
		Label lblTeamsJoined = new Label("Teams Joined");
		
		Label lblPrizeAmt = new Label("$" + codTournament.GetPrize());
		Label lblBracketAmt = new Label(String.valueOf(codTournament.GetBrackSize()));
		Label lblTeamsJoinedVal = new Label(String.valueOf(codTournament.GetTeamsJoined()));
		
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
		
		codTourneyScene = new Scene(ap, 600,400);
	}

	
	public void CreateTeamTournamentCOD() // CreateTeamTournament1()
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
			codTournament.CreateTeam(txtTeamName.getText(), currentUser.GetUsername(),txtMember1.getText(), txtMember2.getText(), txtMember3.getText());
			codTournament.AddTeamToTournament(txtTeamName.getText());
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
		});
		
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenCodTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		//load all users not currently on a team
		ArrayList<String> users = new ArrayList<String>();
		users = codTournament.LoadAllAvailavleUsernames(currentUser.GetUsername());
		TextFields.bindAutoCompletion(txtMember1, users);
		TextFields.bindAutoCompletion(txtMember2, users);
		TextFields.bindAutoCompletion(txtMember3, users);
		
		aPane.getChildren().addAll(txtTeamName,txtMember1,txtMember2,txtMember3,btnCreateTeam,btnReturn);
		
		createTeam = new Scene(aPane);
	}	
	public void OpenCreateTeamTournamentCOD()// OpenCreateTeamTournament1()
	{
		CreateTeamTournamentCOD();
		window.setScene(createTeam);
	}
	public void CreateTeamTournamentFornite() {// tom

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
		
	
		btnCreateTeam.setOnAction(e->{
			fortniteTournament.CreateTeam(txtTeamName.getText(), currentUser.GetUsername(),txtMember1.getText(), txtMember2.getText(), txtMember3.getText());
			fortniteTournament.AddTeamToTournament(txtTeamName.getText()); 
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Team Created");
			alert.setHeaderText("Team created");
			alert.setContentText("Team has been successfully created");
			alert.showAndWait();
			txtTeamName.setText("");
			txtMember1.setText("");
			txtMember2.setText("");
			txtMember3.setText("");
			OpenFortniteTourney();	
		});
		
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e->OpenFortniteTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		//load all users not currently on a team
		ArrayList<String> users = new ArrayList<String>();
		users = fortniteTournament.LoadAllAvailavleUsernames(currentUser.GetUsername());
		TextFields.bindAutoCompletion(txtMember1, users);
		TextFields.bindAutoCompletion(txtMember2, users);
		TextFields.bindAutoCompletion(txtMember3, users);
		
		aPane.getChildren().addAll(txtTeamName,txtMember1,txtMember2,txtMember3,btnCreateTeam,btnReturn);
		
		createTeam = new Scene(aPane);
		
	}
	public void OpenCreateTeamTournamentFortnite() {//tom
		CreateTeamTournamentFornite();
		window.setScene(createTeam);
	}
	public void CreateTeamTournamentHalo() {// tom

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
		
		
		
		btnCreateTeam.setOnAction(e->{
			haloTournament.CreateTeam(txtTeamName.getText(), currentUser.GetUsername(),txtMember1.getText(), txtMember2.getText(), txtMember3.getText());
			haloTournament.AddTeamToTournament(txtTeamName.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Team Created");
			alert.setHeaderText("Team created");
			alert.setContentText("Team has been successfully created");
			alert.showAndWait();
			txtTeamName.setText("");
			txtMember1.setText("");
			txtMember2.setText("");
			txtMember3.setText("");
			OpenHaloTourney();	
		});
		
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenHaloTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		//load all users not currently on a team
		ArrayList<String> users = new ArrayList<String>();
		users = haloTournament.LoadAllAvailavleUsernames(currentUser.GetUsername());
		TextFields.bindAutoCompletion(txtMember1, users);
		TextFields.bindAutoCompletion(txtMember2, users);
		TextFields.bindAutoCompletion(txtMember3, users);
		
		aPane.getChildren().addAll(txtTeamName,txtMember1,txtMember2,txtMember3,btnCreateTeam,btnReturn);
		
		createTeam = new Scene(aPane);
		
	}
	public void OpenCreateTeamTournamentHalo()//tom
	{
		CreateTeamTournamentHalo();
		window.setScene(createTeam);
	}

	
	public void CreateJoinTeamCOD()//CreateJoinTeam()
	{
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
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
			codTournament.JoinTeam(currentUser.GetUsername(),txtTeamName.getText());
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
		openTeams = codTournament.LoadAllOpenTeams();
		TextFields.bindAutoCompletion(txtTeamName, openTeams);
		
		aPane.getChildren().addAll(txtTeamName,btnJoinTeam,btnReturn);
		
		joinTeam = new Scene(aPane);
	}
	public void OpenJoinTeamCOD()//OpenJoinTeam()
	{
		CreateJoinTeamCOD();
		window.setScene(joinTeam);
	}
	public void CreateJoinTeamFortnite()//tom
	{
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
			
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
			fortniteTournament.JoinTeam(currentUser.GetUsername(),txtTeamName.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Teamed Joined");
			alert.setHeaderText("Team Joined");
			alert.setContentText("You have joined the team: Team");
			alert.showAndWait();
			
			OpenFortniteTourney();
			
			});
		
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenFortniteTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		ArrayList<String> openTeams = new ArrayList<String>();
		openTeams = fortniteTournament.LoadAllOpenTeams();
		TextFields.bindAutoCompletion(txtTeamName, openTeams);
		
		aPane.getChildren().addAll(txtTeamName,btnJoinTeam,btnReturn);
		
		joinTeam = new Scene(aPane);
	}
	public void OpenJoinTeamFortnite()//tom
	{
		CreateJoinTeamFortnite();
		window.setScene(joinTeam);
	}
	public void CreateJoinTeamHalo()//tom
	{
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);		
		
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
			haloTournament.JoinTeam(currentUser.GetUsername(),txtTeamName.getText());
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Teamed Joined");
			alert.setHeaderText("Team Joined");
			alert.setContentText("You have joined the team: Team");
			alert.showAndWait();
			
			OpenHaloTourney();
			
			});
		
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenHaloTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		ArrayList<String> openTeams = new ArrayList<String>();
		openTeams = haloTournament.LoadAllOpenTeams();
		TextFields.bindAutoCompletion(txtTeamName, openTeams);
		
		aPane.getChildren().addAll(txtTeamName,btnJoinTeam,btnReturn);
		
		joinTeam = new Scene(aPane);
	}
	public void OpenJoinTeamHalo()//tom
	{
		CreateJoinTeamHalo();
		window.setScene(joinTeam);
	}
	
	
	public void CreateViewRegisteredTeams(Tournament t){ 	
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
		ArrayList<Team> registered = new ArrayList<Team>();
		registered =  t.ViewRegisterdTeams(t.GetID());
		
		ListView<Team> teamlist= new ListView <Team>();	
		teamlist.setPrefHeight(200);
		teamlist.setPrefWidth(200);
		teamlist.setLayoutX(40.0);
		teamlist.setLayoutY(90.0);
		ObservableList<Team> teamitems = FXCollections.observableArrayList();
		teamlist.setItems(teamitems);
		
		for(Team team : registered) {
			teamitems.add(team);
		}
		
		JFXButton btnReturn = new JFXButton("<");
		btnReturn.setOnAction(e->OpenCodTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		aPane.getChildren().addAll(btnReturn, teamlist);
		viewRegisteredTeams = new Scene(aPane);
	}
	public void OpenViewRegisteredTeams(Tournament t)	
	{
		CreateViewRegisteredTeams(t);
		window.setScene(viewRegisteredTeams);
	}







}
