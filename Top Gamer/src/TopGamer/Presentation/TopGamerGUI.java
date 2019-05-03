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
import javafx.stage.PopupWindow.AnchorLocation;

import java.awt.Event;
import java.awt.event.TextEvent;
import java.nio.channels.SelectableChannel;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.event.ChangeListener;
import javax.xml.transform.Templates;

import org.controlsfx.control.textfield.TextFields;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;
import org.sqlite.SQLiteException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXNodesList;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import TopGamer.Business.*;


public class TopGamerGUI extends Application
{
	
	User currentUser = new User();
	boolean loggedIn = false;
	
	
	//Main window
	Stage window;
	
	//Main scenes
	Scene loginScene, registerScene, mainDashboardScene;
	
	//Main games scenes
	Scene fortniteScene, codScene, haloScene;
	
	//Score reporting scenes
	Scene fortniteReportScore, codReportScore, haloReporScore;

	//Tournament scenes
	Scene codTourneyScene, fortniteTourneyScene, haloTourneyScene;
	
	Scene createTeam, joinTeam, viewRegisteredTeams;
	
	ImageView backArrow = new ImageView(new Image("back arrow.png"));
	
	//Used for JFXDialog
	StackPane mainDashStack = new StackPane();
	StackPane registerStack = new StackPane();
	
	//Login controls
	Label lblLoginName, lblLoginPass;
	JFXTextField txtLoginName;
	JFXPasswordField txtLoginPass;
	JFXButton btnUserLogin;
	JFXButton btnSignUp;                                                    
	JFXButton btnContinue;
	Label lblValidLoginUser,lblValidLoginPass;
	
	//Registration controls
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
	JFXButton btnViewProfile = new JFXButton("Settings");
	JFXButton btnLogOut = new JFXButton("Edit Profile");
	JFXNodesList nodeList = new JFXNodesList();

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
		CreateFortniteScene();		
		CreateCODScene();	
		CreateHaloScene();
		CreateMainDashboard();
		

		window.getIcons().add(new Image("icon.png"));
		window.setTitle("Top Gamer");
		window.setScene(loginScene);
		window.show();	
	}

	
	//************************************************
	// Helper functions to assist with form validation
	//************************************************
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
	*  Tests if PasswordField is empty or not
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
		
	
	//**********************************
	// Login
	//**********************************
	/**
	 * Create the login scene
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
	* Set main window to the loginScene
	*/
	public void OpenLoginScene()
	{
		window.setScene(loginScene);
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
						catch(SQLServerException e)
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

	

	//**********************************
	// Registration
	//**********************************
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
		txtFName.requestFocus();
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
		AnchorPane.setTopAnchor(lblValidUserName, 214.0);
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
				
				if(!s.AddUser(txtFName.getText(), txtLName.getText(), txtEmail.getText(), txtUserName.getText(), txtPass.getText(),rbSelectedPlatform.getText()))
				{
					lblValidUserName.setText("Username Already Exists");
					lblValidUserName.setTextFill(Color.RED);
					txtUserName.setText(null);
					txtUserName.requestFocus();
				}
				else {
					//set current user to the registered user
					currentUser.Login(txtUserName.getText(), txtPass.getText());
					
					String registerTitle = "Registration Successful";
					String registerContent = String.format("Welcome, %s!", currentUser.GetUsername());
					JFXDialogLayout dialogContent = new JFXDialogLayout();
					dialogContent.setHeading(new Text(registerTitle));
					dialogContent.setBody(new Text(registerContent));
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
						rbPC.setSelected(false);
						rbXbox.setSelected(false);
						rbPS4.setSelected(false);
						lblValidFirstName.setText(null);
						lblValidLastName.setText(null);
						lblValidEmail.setText(null);
						lblValidUserName.setText(null);
						lblValidPass.setText(null);
					});
					dialog.setContent(dialogContent);
					dialog.getChildren().add(button);
					dialog.setDialogContainer(registerStack);
					dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
					dialogContent.setActions(button);
					dialog.show();
				}
			} catch (SQLServerException e) {
				System.out.println("Duplicate Record");
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
	
	
	//**********************************
	// Main Dash board
	//**********************************
	/**
	 * Creates the main dash board scene
	 */
	public void CreateMainDashboard()
	{
		
		btnProfile = new JFXButton("Profile");
		btnViewProfile = new JFXButton("View profile");
		btnLogOut= new JFXButton("Log out");
		
		btnViewProfile.setOnAction(e->{
			if(!loggedIn) {
				String viewProfileTitle = "Not logged in";
				String viewProfileContent = "Please log in to view profile";
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text(viewProfileTitle));
				dialogContent.setBody(new Text(viewProfileContent));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnClose = new JFXButton("Close");
				JFXButton btnLogin = new JFXButton("Login");
				btnClose.setOnAction(ev->dialog.close());
				btnLogin.setOnAction(ev->{dialog.close(); OpenLoginScene();});
				dialog.setContent(dialogContent);
				dialog.setDialogContainer(mainDashStack);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnLogin,btnClose);
				dialog.show();
				return;
			}
			
			//load user data again to show team
			currentUser.LoadUserData(currentUser.GetUsername());
			
			VBox labelVbox = new VBox();
			Label lblFirstName = new Label("First Name: ");			
			Label lblLastName = new Label("Last Name: ");			
			Label lblEmail = new Label("Email: ");
			Label lblPlatform = new Label("Platform: ");
			labelVbox.getChildren().addAll(lblFirstName,lblLastName,lblEmail,lblPlatform);
			
			VBox textFieldVbox = new VBox();
			JFXTextField txtFirstName = new JFXTextField(currentUser.GetFirstName());
			txtFirstName.setEditable(false);
			JFXTextField txtLastName = new JFXTextField(currentUser.GetLastName());
			txtLastName.setEditable(false);
			JFXTextField txtEmail = new JFXTextField(currentUser.GetEmail());
			txtEmail.setEditable(false);
			JFXTextField txtPlatform = new JFXTextField(currentUser.GetPlatform().GetPlatformName());
			txtPlatform.setEditable(false);
			textFieldVbox.getChildren().addAll(txtFirstName,txtLastName,txtEmail,txtPlatform);
			
			
			
			VBox teamLabelVbox = new VBox();
			Label lblTeamName = new Label("Team Name: ");			
			Label lblMember1 = new Label("Member 1: ");			
			Label lblMember2 = new Label("Member 2: ");
			Label lblMember3 = new Label("Member 3: ");
			teamLabelVbox.getChildren().addAll(lblTeamName,lblMember1,lblMember2,lblMember3);
			
			VBox teamVbox = new VBox();
			ArrayList<String> teamMates = new ArrayList<String>();
			JFXTextField txtTeamName = new JFXTextField(currentUser.GetTeam().GetTeamName());
			txtTeamName.setEditable(false);
			JFXTextField txtTeamMate1 = new JFXTextField();
			txtTeamMate1.setEditable(false);
			JFXTextField txtTeamMate2 = new JFXTextField();
			txtTeamMate2.setEditable(false);
			JFXTextField txtTeamMate3 = new JFXTextField();
			txtTeamMate3.setEditable(false);
			
			//load team members
			for(User user : currentUser.GetTeam().GetAllTeamMembers())
			{
				if(!user.GetUsername().equals(currentUser.GetUsername()))
				{
					teamMates.add(user.GetUsername());
				}
			}
			
			int size = teamMates.size();
			
			if(size == 3) 
			{
				txtTeamMate1.setText(teamMates.get(0));
				txtTeamMate2.setText(teamMates.get(1));
				txtTeamMate3.setText(teamMates.get(2));
			}
			if(size == 2)
			{
				txtTeamMate1.setText(teamMates.get(0));
				txtTeamMate2.setText(teamMates.get(1));
			}
			if(size == 1)
				txtTeamMate1.setText(teamMates.get(0));
			
			teamVbox.getChildren().addAll(txtTeamName,txtTeamMate1,txtTeamMate2,txtTeamMate3);
			
			
			HBox profileHbox = new HBox();
			profileHbox.getChildren().addAll(labelVbox,textFieldVbox);
			profileHbox.setSpacing(15);
			
			HBox teamHbox = new HBox();
			teamHbox.getChildren().addAll(teamLabelVbox,teamVbox);
			teamHbox.setSpacing(15);
			
			
			VBox mainVbox = new VBox();
			mainVbox.setSpacing(15.0);
			mainVbox.getChildren().addAll(profileHbox,teamHbox);
			
			JFXDialog dialog = new JFXDialog();
			JFXButton btnClose = new JFXButton("Close");
			String viewProfileTitle = currentUser.GetUsername() + "'s profile";
			JFXDialogLayout dialogContent = new JFXDialogLayout();
			dialogContent.setHeading(new Text(viewProfileTitle));
			dialogContent.setBody(mainVbox);
			dialogContent.setActions(btnClose);
			btnClose.setOnAction(ev->dialog.close());
			dialog.setContent(dialogContent);
			dialog.setDialogContainer(mainDashStack);
			dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
			dialog.show();
			
			
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
				currentUser = new User();
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
		nodeList.addAnimatedNode(btnViewProfile);
		
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
		Label lblTournaments= new Label ("Featured Tournaments");
		Label lblCrossPlatform= new Label ("Cross Platform");
		Label lblXboxPlatform= new Label ("Xbox One");
		Label lblPS4Platform = new Label ("Playstation 4");
		lblTournaments.setStyle("-fx-font-weight: bold");
		lblFeaturedGames.setStyle("-fx-font-weight: bold");
		
		//Load images and set their size
		ImageView profileIcon = new ImageView(new Image("profileIcon.png"));
		profileIcon.setFitHeight(20);
		profileIcon.setFitWidth(20);
		btnProfile.setGraphic(profileIcon);
	
		ImageView fortniteLogo = new ImageView(new Image("Fortnite.jpg"));
		fortniteLogo.setFitWidth(133);
		fortniteLogo.setFitHeight(202);
	
		ImageView codLogo = new ImageView(new Image("Cod.jpg"));
		codLogo.setFitWidth(133);
		codLogo.setFitHeight(202);
		
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
		
		JFXButton codTourney = new JFXButton("Call of Duty Search and Destroy (" + codTournament.GetStatus() + ")");
	
		codTourney.setOnAction(e->{
			
			if(codTournament.GetStatus().equals("CLOSED"))
			{
				for(Team t: codTournament.GetTeams())
				{
					if(t == codTournament.GetWinner())
					{
						JFXDialogLayout dialogContent = new JFXDialogLayout();
						dialogContent.setHeading(new Text("Tournament Closed"));
						dialogContent.setBody(new Text(t.GetTeamName() + " is the winner"));
						JFXDialog dialog = new JFXDialog();
						JFXButton btnOkay = new JFXButton("Okay");
						dialog.setContent(dialogContent);
						dialog.getChildren().add(btnOkay);
						dialog.setDialogContainer(mainDashStack);
						dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
						dialogContent.setActions(btnOkay);
						btnOkay.setOnAction(ev->dialog.close());
						dialog.show();
						return;
					}
				}
			}
			else
				OpenCodTourney();
		});
		
		JFXButton fortniteTourney = new JFXButton("Fortnite Friday (" + fortniteTournament.GetStatus() + ")");
		fortniteTourney.setOnAction(e->{
			if(fortniteTournament.GetStatus().equals("CLOSED"))
			{
				for(Team t: fortniteTournament.GetTeams())
				{
					if(t == fortniteTournament.GetWinner())
					{
						JFXDialogLayout dialogContent = new JFXDialogLayout();
						dialogContent.setHeading(new Text("Tournament Closed"));
						dialogContent.setBody(new Text(t.GetTeamName() + " is the winner"));
						JFXDialog dialog = new JFXDialog();
						JFXButton btnOkay = new JFXButton("Okay");
						dialog.setContent(dialogContent);
						dialog.getChildren().add(btnOkay);
						dialog.setDialogContainer(mainDashStack);
						dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
						dialogContent.setActions(btnOkay);
						btnOkay.setOnAction(ev->dialog.close());
						dialog.show();
						return;
					}
				}
			}
			else
				OpenFortniteTourney();
		});
		JFXButton haloTourney = new JFXButton("Halo Team Slayer (" + haloTournament.GetStatus() + ")");
		haloTourney.setOnAction(e->{
			if(haloTournament.GetStatus().equals("CLOSED"))
			{
				for(Team t: haloTournament.GetTeams())
				{
					if(t.GetTeamName().equals(haloTournament.GetWinner().GetTeamName()))
					{
						JFXDialogLayout dialogContent = new JFXDialogLayout();
						dialogContent.setHeading(new Text("Tournament Closed"));
						dialogContent.setBody(new Text(t.GetTeamName() + " is the winner"));
						JFXDialog dialog = new JFXDialog();
						JFXButton btnOkay = new JFXButton("Okay");
						dialog.setContent(dialogContent);
						dialog.getChildren().add(btnOkay);
						dialog.setDialogContainer(mainDashStack);
						dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
						dialogContent.setActions(btnOkay);
						btnOkay.setOnAction(ev->dialog.close());
						dialog.show();
						return;
					}
				}
			}
			else
				OpenHaloTourney();
		});
		
		//Set image position on anchorScroll
		AnchorPane.setTopAnchor(fortniteLogo, 50.0);
		AnchorPane.setLeftAnchor(fortniteLogo, 24.0);
		AnchorPane.setTopAnchor(lblCrossPlatform, 250.0);
		AnchorPane.setLeftAnchor(lblCrossPlatform, 24.0);
		
		
		AnchorPane.setTopAnchor(codLogo, 50.0);
		AnchorPane.setLeftAnchor(codLogo, 167.0);
		AnchorPane.setTopAnchor(lblPS4Platform, 250.0);
		AnchorPane.setLeftAnchor(lblPS4Platform, 167.0);
		
		
		AnchorPane.setTopAnchor(haloLogo, 50.0);
		AnchorPane.setLeftAnchor(haloLogo, 308.0);
		AnchorPane.setTopAnchor(lblXboxPlatform, 250.0);
		AnchorPane.setLeftAnchor(lblXboxPlatform, 308.0);
		
		AnchorPane.setTopAnchor(lblFeaturedGames, 14.0);
		AnchorPane.setLeftAnchor(lblFeaturedGames, 14.0);
		AnchorPane.setTopAnchor(lblTournaments, 320.0);
		AnchorPane.setLeftAnchor(lblTournaments, 14.0);
		
		
		//set button position on the anchorScroll
		AnchorPane.setTopAnchor(codTourney, 350.0);
		AnchorPane.setLeftAnchor(codTourney, 14.0);
		AnchorPane.setTopAnchor(fortniteTourney, 400.0);
		AnchorPane.setLeftAnchor(fortniteTourney, 14.0);
		AnchorPane.setTopAnchor(haloTourney, 450.0);
		AnchorPane.setLeftAnchor(haloTourney, 14.0);
		
		//this goes in the anchorHeader
		AnchorPane.setTopAnchor(nodeList, 6.0);
		AnchorPane.setRightAnchor(nodeList, 14.0);
		
		//add images to anchor pane
		anchorHeader.getChildren().addAll(nodeList);
		anchorScroll.getChildren().addAll(lblFeaturedGames,fortniteLogo,codLogo,haloLogo,codTourney,fortniteTourney,haloTourney,lblTournaments, lblCrossPlatform, lblPS4Platform, lblXboxPlatform);
		mainScroll.setContent(anchorScroll);
		mainVbox.getChildren().addAll(anchorHeader,mainScroll);
		mainDashStack.getChildren().add(mainVbox);
		mainDashboardScene = new Scene(mainDashStack,600,400);
	
	}
	/**
	 * Sets main window to the main dash board scene
	 */
	public void OpenMainDashboard()
	{
		if(loggedIn)
			btnProfile.setText(currentUser.GetUsername());	
		window.setScene(mainDashboardScene);
	}	
	
	
	//**********************************
	// Fortnite Scenes
	//**********************************
	/**
	 * Create the main Fortnite scene
	 */
	public void CreateFortniteScene()
	{
		StackPane stackPane = new StackPane();
		fortniteTournament = new Tournament();
		fortniteTournament.LoadTournamentData(6);
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Fortnite"); 
		lblTitle.setFont(new Font(24));
		Label lblLeaderboard = new Label("Leaderboard");
		Label lblTournaments = new Label("Tournaments");
		lblLeaderboard.setStyle("-fx-font-weight: bold");
		lblTournaments.setStyle("-fx-font-weight: bold");
		
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenMainDashboard());
		JFXButton btnTournament1 = new JFXButton("Friday Fortnite (" + fortniteTournament.GetStatus() + ")");
		btnTournament1.setOnAction(e->{
			if(fortniteTournament.GetStatus().equals("CLOSED"))
			{
				for(Team t: fortniteTournament.GetTeams())
				{
					if(t == fortniteTournament.GetWinner())
					{
						JFXDialogLayout dialogContent = new JFXDialogLayout();
						dialogContent.setHeading(new Text("Tournament Closed"));
						dialogContent.setBody(new Text(t.GetTeamName() + " is the winner"));
						JFXDialog dialog = new JFXDialog();
						JFXButton btnOkay = new JFXButton("Okay");
						dialog.setContent(dialogContent);
						dialog.getChildren().add(btnOkay);
						dialog.setDialogContainer(stackPane);
						dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
						dialogContent.setActions(btnOkay);
						btnOkay.setOnAction(ev->dialog.close());
						dialog.show();
						return;
					}
				}
			}
			else
				OpenFortniteTourney();
		});
		
		
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
		TableColumn<Team, String> scoreCol = new TableColumn<>("Score");
		scoreCol.setCellFactory(TextFieldTableCell.forTableColumn());
		scoreCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().ScoreToString()));
				
		
		//add columns to the list view
		leaderBoardTable.getColumns().addAll(teamNameCol,scoreCol);
				

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
		
		AnchorPane.setTopAnchor(lblLeaderboard, 90.0);
		AnchorPane.setLeftAnchor(lblLeaderboard, 200.0);
		
		AnchorPane.setTopAnchor(lblTournaments, 175.0);
		AnchorPane.setLeftAnchor(lblTournaments, 50.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 190.0);
		AnchorPane.setLeftAnchor(btnTournament1, 50.0);
		
		AnchorPane.setTopAnchor(leaderBoardTable, 115.0);
		AnchorPane.setLeftAnchor(leaderBoardTable, 200.0);
		
		ap.getChildren().addAll(fortniteLogo,lblTournaments,lblTitle,lblLeaderboard,btnTournament1,btnReturn,leaderBoardTable);
		stackPane.getChildren().add(ap);
		fortniteScene = new Scene(stackPane,600,400);
	
	}
	/**
	* Set main window the the Fortnite scene
	*/
	public void OpenFortniteScene()
	{
		CreateFortniteScene();
		window.setScene(fortniteScene);
	}
	
	/**
	 * Create scene for Fortnite tournament
	 */
	public void CreateFortniteTourneyScene() { 
		
		//load data again so user cannot create/join a team
		fortniteTournament.LoadTournamentData(6);
		
		StackPane stackPane = new StackPane();
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnViewRegisteredTeams= new JFXButton("View Registered Teams"); // Tom
		btnViewRegisteredTeams.setOnAction(e->OpenViewRegisteredTeams(fortniteTournament));
		JFXButton btnReportScore = new JFXButton("Report scores");
		
		
		btnReportScore.setOnAction(e->{
			if(!loggedIn) {
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Cannot access this"));
				dialogContent.setBody(new Text("Must login to report scores"));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->dialog.close());
				dialog.show();
				e.consume();
				return;
			}
			if(fortniteTournament.GetTeamsJoined() < fortniteTournament.GetBrackSize())
			{
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Not enough teams"));
				dialogContent.setBody(new Text("Scores cannot be reported until the bracket is full"));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->dialog.close());
				dialog.show();
				return;
			}
			for(Team team : fortniteTournament.GetTeams())
			{
				if(team.GetTeamName().equals(currentUser.GetTeam().GetTeamName()))
				{
					if(team.GetScoreReported() == false)
						OpenFortniteScoreReport();
					else{
						JFXDialogLayout dialogContent = new JFXDialogLayout();
						dialogContent.setHeading(new Text("Cannot report scores"));
						dialogContent.setBody(new Text("Scores have already been reported"));
						JFXDialog dialog = new JFXDialog();
						JFXButton btnOkay = new JFXButton("Okay");
						dialog.setContent(dialogContent);
						dialog.getChildren().add(btnOkay);
						dialog.setDialogContainer(stackPane);
						dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
						dialogContent.setActions(btnOkay);
						btnOkay.setOnAction(ev->dialog.close());
						dialog.show();
						return;
					}
				}
			}
		});
		JFXButton btnReturn = new JFXButton("<-");
		
		btnJoinTeam.setOnAction(e->{
			if(!loggedIn) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error");
				alert.setHeaderText("Not logged in");
				alert.setContentText("Please log in to join a team");
				alert.showAndWait();
				return;
				}
			for(Team team : fortniteTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					JFXDialogLayout dialogContent = new JFXDialogLayout();
					dialogContent.setHeading(new Text("Cannot join team"));
					dialogContent.setBody(new Text("You are already on a team"));
					JFXDialog dialog = new JFXDialog();
					JFXButton btnOkay = new JFXButton("Okay");
					dialog.setContent(dialogContent);
					dialog.getChildren().add(btnOkay);
					dialog.setDialogContainer(stackPane);
					dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
					dialogContent.setActions(btnOkay);
					btnOkay.setOnAction(ev->dialog.close());
					dialog.show();
					return;
				}
			}
			OpenJoinTeamFortnite();
			});
		
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
					JFXDialogLayout dialogContent = new JFXDialogLayout();
					dialogContent.setHeading(new Text("Cannot create team"));
					dialogContent.setBody(new Text("You are already on a team"));
					JFXDialog dialog = new JFXDialog();
					JFXButton btnOkay = new JFXButton("Okay");
					dialog.setContent(dialogContent);
					dialog.getChildren().add(btnOkay);
					dialog.setDialogContainer(stackPane);
					dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
					dialogContent.setActions(btnOkay);
					btnOkay.setOnAction(ev->dialog.close());
					dialog.show();
					return;
				}
			}
			if(fortniteTournament.GetTeamsJoined() < fortniteTournament.GetBrackSize()) {
				OpenCreateTeamTournamentFortnite();		
				btnCreateTeam.setDisable(true);
			}
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
		Label lblTourneyDesc = new Label ("Each team will play a max of 4 games. Teams can earn up to 25 points each game.\n The first team to 100 points is the winner");
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
		
		AnchorPane.setTopAnchor(lblTourneyDesc, 105.0);
		AnchorPane.setLeftAnchor(lblTourneyDesc, 135.0);
		
		
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
		
		AnchorPane.setTopAnchor(btnReportScore, 310.0); 
		AnchorPane.setLeftAnchor(btnReportScore, 300.0);
		
		ap.getChildren().addAll(btnReturn,lblTitle,lblLocation,lblTourneyDesc,lblPrize,lblBracketSize,lblTeamsJoined,lblPrizeAmt,lblBracketAmt,lblTeamsJoinedVal,btnJoinTeam,btnCreateTeam, btnViewRegisteredTeams, btnReportScore); // Tom
		stackPane.getChildren().add(ap);
		fortniteTourneyScene = new Scene(stackPane, 600,400);
	}
	/**
	 * Set main window to the Fornite tournament scene
	 */
	public void OpenFortniteTourney() 
	{
		CreateFortniteTourneyScene();
		window.setScene(fortniteTourneyScene);
	}
	
	/**
	 * Create scene for Fortnite team creation
	 */
	public void CreateTeamTournamentFornite() {// tom

		StackPane stackPane = new StackPane();
		AnchorPane aPane= new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
		Label lblBlankTeamName = new Label();
		lblBlankTeamName.setLayoutX(211.0);		
		lblBlankTeamName.setLayoutY(55.0);		
		
		JFXTextField txtTeamName = new JFXTextField();
		txtTeamName.setLabelFloat(true);
		txtTeamName.setPromptText("Team name");
		txtTeamName.setLayoutX(211.0);
		txtTeamName.setLayoutY(78.0);
		txtTeamName.setOnKeyPressed(e-> lblBlankTeamName.setText(null));
		
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
			if(Empty(txtTeamName))
			{
				lblBlankTeamName.setText("Team name cannot be empty");
				lblBlankTeamName.setTextFill(Color.RED);
			}
			else {
				fortniteTournament.CreateTeam(txtTeamName.getText(), currentUser.GetUsername(),txtMember1.getText(), txtMember2.getText(), txtMember3.getText());
				fortniteTournament.AddTeamToTournament(txtTeamName.getText()); 
				String viewProfileTitle = "Team created";
				String viewProfileContent = "Team successfully created for" + fortniteTournament.GetTournamentName();
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text(viewProfileTitle));
				dialogContent.setBody(new Text(viewProfileContent));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				btnOkay.setOnAction(ev->{dialog.close(); OpenFortniteTourney();});
				dialog.setContent(dialogContent);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				dialog.show();
			}
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
		
		aPane.getChildren().addAll(lblBlankTeamName,txtTeamName,txtMember1,txtMember2,txtMember3,btnCreateTeam,btnReturn);
		stackPane.getChildren().add(aPane);
		createTeam = new Scene(stackPane);
		
	}
	/**
	 * Set main window to the Fortnite create team scene
	 */
	public void OpenCreateTeamTournamentFortnite() {//tom
		CreateTeamTournamentFornite();
		window.setScene(createTeam);
	}
	
	/**
	 * Create scene to join a Fortnite team
	 */
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
			alert.setContentText("You have joined the team: " + txtTeamName.getText());
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
	/**
	 * Set main window to the Fortnite join team scene
	 */
	public void OpenJoinTeamFortnite()//tom
	{
		CreateJoinTeamFortnite();
		window.setScene(joinTeam);
	}
	
	public void CreateFortniteScoreReport() {
		
		StackPane stackPane = new StackPane();
		AnchorPane aPane = new AnchorPane();
		//aPane.setPrefWidth(600);
		//aPane.setPrefHeight(400);
		
		Label lblTitle = new Label("Please enter your points for each game");
		lblTitle.setLayoutX(201);
		lblTitle.setLayoutY(104);
		
		Label lblGame1 = new Label("Game 1:");
		lblGame1.setLayoutX(201);
		lblGame1.setLayoutY(136);
		
		Label lblGame2 = new Label("Game 2:");
		lblGame2.setLayoutX(201);		
		lblGame2.setLayoutY(169);
		
		Label lblGame3 = new Label("Game 3:");
		lblGame3.setLayoutX(201);
		lblGame3.setLayoutY(198);
		
		Label lblGame4 = new Label("Game 4:");
		lblGame4.setLayoutX(201);
		lblGame4.setLayoutY(231);
		
		
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenFortniteTourney());
		btnReturn.setLayoutX(14.0);
		btnReturn.setLayoutY(14.0);
		
		JFXSlider sliderGame1 = new JFXSlider(0,25,0);
		sliderGame1.setShowTickLabels(true);
		sliderGame1.setShowTickMarks(true);
		sliderGame1.setLayoutX(251);
		sliderGame1.setLayoutY(137);

		
		JFXSlider sliderGame2 = new JFXSlider(0,25,0);
		sliderGame2.setLayoutX(251);
		sliderGame2.setLayoutY(170);
		
		JFXSlider sliderGame3 = new JFXSlider(0,25,0);
		sliderGame3.setLayoutX(251);
		sliderGame3.setLayoutY(199);
		
		JFXSlider sliderGame4 = new JFXSlider(0,25,0);
		sliderGame4.setLayoutX(251);
		sliderGame4.setLayoutY(232);
		
		JFXButton btnSubmit = new JFXButton("Submit");
		btnSubmit.setLayoutX(281);
		btnSubmit.setLayoutY(270);
		btnSubmit.setOnAction(e->{
		
		double sum = Math.round(sliderGame1.getValue() + sliderGame2.getValue() + sliderGame3.getValue() + sliderGame4.getValue());
		
		fortniteTournament.ReportScore(sum, currentUser.GetTeam());
		
		JFXDialogLayout dialogContent = new JFXDialogLayout();
		dialogContent.setHeading(new Text("Scores reported"));
		dialogContent.setBody(new Text("Scores have been saved successfully"));
		JFXDialog dialog = new JFXDialog();
		JFXButton btnOkay = new JFXButton("Okay");
		dialog.setContent(dialogContent);
		dialog.getChildren().add(btnOkay);
		dialog.setDialogContainer(stackPane);
		dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
		dialogContent.setActions(btnOkay);
		btnOkay.setOnAction(ev->OpenFortniteTourney());
		dialog.show();
		
		sliderGame1.setValue(0);
		sliderGame2.setValue(0);
		sliderGame3.setValue(0);
		sliderGame4.setValue(0);

		});
		
		aPane.getChildren().addAll(lblTitle,lblGame1,lblGame2,lblGame3,lblGame4,btnReturn,sliderGame1,sliderGame2,sliderGame3,sliderGame4,btnSubmit);
		stackPane.getChildren().add(aPane);
		fortniteReportScore = new Scene(stackPane,600,400);
	}
	public void OpenFortniteScoreReport() {
		CreateFortniteScoreReport();
		window.setScene(fortniteReportScore);
	}

	
	
	//**********************************
	// COD Scenes
	//**********************************
	/**
	 * Create the main COD scene
	 */
	public void CreateCODScene()
	{
		codTournament = new Tournament();
		codTournament.LoadTournamentData(3);
		
		StackPane stackPane = new StackPane();
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Call Of Duty: \nAdvanced Warefare"); 
		lblTitle.setFont(new Font(24));
		Label lblLeaderBoard = new Label("LeaderBoard");
		Label lblTournaments= new Label ("Tournaments");
		lblTournaments.setStyle("-fx-font-weight: bold");
		lblLeaderBoard.setStyle("-fx-font-weight: bold");
		JFXButton btnReturn = new JFXButton("<-");

		btnReturn.setOnAction(e-> OpenMainDashboard());
		JFXButton btnTournament1 = new JFXButton("4v4 Search and Destroy (" + codTournament.GetStatus() + ")");

		
		btnTournament1.setOnAction(e->{
			
			if(codTournament.GetStatus().equals("CLOSED"))
			{
				for(Team t: codTournament.GetTeams())
				{
					if(t == codTournament.GetWinner())
					{
						JFXDialogLayout dialogContent = new JFXDialogLayout();
						dialogContent.setHeading(new Text("Tournament Closed"));
						dialogContent.setBody(new Text(t.GetTeamName() + " is the winner"));
						JFXDialog dialog = new JFXDialog();
						JFXButton btnOkay = new JFXButton("Okay");
						dialog.setContent(dialogContent);
						dialog.getChildren().add(btnOkay);
						dialog.setDialogContainer(stackPane);
						dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
						dialogContent.setActions(btnOkay);
						btnOkay.setOnAction(ev->dialog.close());
						dialog.show();
						return;
					}
				}
			}
			else
				OpenCodTourney();
		});
		
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
		
		AnchorPane.setTopAnchor(lblLeaderBoard, 90.0);
		AnchorPane.setLeftAnchor(lblLeaderBoard, 205.0);
		
		AnchorPane.setTopAnchor(lblTournaments, 180.0);
		AnchorPane.setLeftAnchor(lblTournaments, 50.0);
		
		
		AnchorPane.setTopAnchor(btnTournament1, 215.0);
		AnchorPane.setLeftAnchor(btnTournament1, 50.0);
		
		AnchorPane.setTopAnchor(leaderBoardTable, 115.0);
		AnchorPane.setLeftAnchor(leaderBoardTable, 205.0);
		
		
		ap.getChildren().addAll(codLogo,lblTitle,lblLeaderBoard,btnTournament1,btnReturn,leaderBoardTable, lblTournaments);
		stackPane.getChildren().add(ap);
		codScene = new Scene(stackPane,600,400);
	}
	/**
	 * Set main window to the COD scene
	 */
	public void OpenCodScene()
	{
		CreateCODScene();
		window.setScene(codScene);
	}

	/**
	 * Creates scene for COD tournament
	 */
	public void CreateCodTourneyScene()
	{
		StackPane stackPane = new StackPane();
		
		//load in data again to show updated data
		codTournament.LoadTournamentData(3);
		
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnViewRegisteredTeams= new JFXButton("View Registered Teams"); // Tom
		JFXButton btnReportScores = new JFXButton("Report Scores");
		
		btnReportScores.setOnAction(e->{
			if(!currentUser.GetPlatform().GetPlatformName().equals(codTournament.GetGame().GetPlatform().GetPlatformName())) {
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Incompatiable Systems"));
				dialogContent.setBody(new Text("You play on " + currentUser.GetPlatform().GetPlatformName() + ". The tournament is on " + codTournament.GetGame().GetPlatform().GetPlatformName()));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->dialog.close());
				dialog.show();
				return;
			}
			if(codTournament.GetTeamsJoined() < codTournament.GetBrackSize())
			{
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Not enough teams"));
				dialogContent.setBody(new Text("Scores cannot be reported until the bracket is full"));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->dialog.close());
				dialog.show();
				return;
			}
			else
				OpenCODScoreReport();
			
		});
		
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
			for(Team team : codTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					JFXDialogLayout dialogContent = new JFXDialogLayout();
					dialogContent.setHeading(new Text("Cannot join team"));
					dialogContent.setBody(new Text("You are already on a team"));
					JFXDialog dialog = new JFXDialog();
					JFXButton btnOkay = new JFXButton("Okay");
					dialog.setContent(dialogContent);
					dialog.getChildren().add(btnOkay);
					dialog.setDialogContainer(stackPane);
					dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
					dialogContent.setActions(btnOkay);
					btnOkay.setOnAction(ev->dialog.close());
					dialog.show();
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
			for(Team team : codTournament.GetTeams())
			{
				if(currentUser.GetUsername().equals(team.GetSpecificTeamMember(currentUser).GetUsername())) {
					JFXDialogLayout dialogContent = new JFXDialogLayout();
					dialogContent.setHeading(new Text("Cannot create team"));
					dialogContent.setBody(new Text("You are already on a team"));
					JFXDialog dialog = new JFXDialog();
					JFXButton btnOkay = new JFXButton("Okay");
					dialog.setContent(dialogContent);
					dialog.getChildren().add(btnOkay);
					dialog.setDialogContainer(stackPane);
					dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
					dialogContent.setActions(btnOkay);
					btnOkay.setOnAction(ev->dialog.close());
					dialog.show();
					return;
				}
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
			
			
	});
		
		Label lblTitle = new Label(codTournament.GetTournamentName() + "(" + codTournament.GetGame().GetPlatform().GetPlatformName() + ")");
		lblTitle.setFont(new Font(24));
		Label lblLocation = new Label(codTournament.GetLocation() + " " + codTournament.GetDate());
		Label lblPrize = new Label("Prize");
		Label lblTournDesc= new Label("A one-sided game mode, the goal is for an attacking side to either eliminate the,\ndefending team or detonate either one of two bomb sites. This is a bracket style\ntournament with 4 teams, single elimination. ");
		
		Label lblBracketSize = new Label("Bracket Size");
		Label lblTeamsJoined = new Label("Teams Joined");
		
		Label lblPrizeAmt = new Label("$" + codTournament.GetPrize());
		Label lblBracketAmt = new Label(String.valueOf(codTournament.GetBrackSize()));
		Label lblTeamsJoinedVal = new Label(String.valueOf(codTournament.GetTeamsJoined()));
		
		AnchorPane.setTopAnchor(btnReturn, 14.0);
		AnchorPane.setLeftAnchor(btnReturn, 14.0);
		
		AnchorPane.setTopAnchor(lblTitle, 35.0);
		AnchorPane.setLeftAnchor(lblTitle, 137.0);
		
		AnchorPane.setTopAnchor(lblLocation, 72.0);
		AnchorPane.setLeftAnchor(lblLocation, 170.0);
		
		AnchorPane.setTopAnchor(lblTournDesc, 100.0);
		AnchorPane.setLeftAnchor(lblTournDesc, 40.0);
		
		AnchorPane.setTopAnchor(lblPrize, 189.0);
		AnchorPane.setLeftAnchor(lblPrize, 137.0);
		
		AnchorPane.setTopAnchor(lblBracketSize, 226.0);
		AnchorPane.setLeftAnchor(lblBracketSize, 137.0);
		
		AnchorPane.setTopAnchor(lblTeamsJoined, 270.0);
		AnchorPane.setLeftAnchor(lblTeamsJoined, 137.0);
		
		AnchorPane.setTopAnchor(lblPrizeAmt, 189.0);
		AnchorPane.setLeftAnchor(lblPrizeAmt, 342.0);
		
		AnchorPane.setTopAnchor(lblBracketAmt, 226.0);
		AnchorPane.setLeftAnchor(lblBracketAmt, 342.0);
		
		AnchorPane.setTopAnchor(lblTeamsJoinedVal, 270.0);
		AnchorPane.setLeftAnchor(lblTeamsJoinedVal, 342.0);

		AnchorPane.setTopAnchor(btnJoinTeam, 320.0);
		AnchorPane.setLeftAnchor(btnJoinTeam, 120.0);
		
		AnchorPane.setTopAnchor(btnCreateTeam, 320.0);
		AnchorPane.setLeftAnchor(btnCreateTeam, 300.0);
		
		AnchorPane.setTopAnchor(btnViewRegisteredTeams, 375.0);
		AnchorPane.setLeftAnchor(btnViewRegisteredTeams, 120.0); 
		
		AnchorPane.setTopAnchor(btnReportScores, 375.0);
		AnchorPane.setLeftAnchor(btnReportScores, 300.0); 
		
		
		ap.getChildren().addAll(btnReturn,lblTitle, lblLocation,lblPrize,lblBracketSize,lblTeamsJoined,lblPrizeAmt,lblBracketAmt,lblTeamsJoinedVal,btnJoinTeam,btnCreateTeam, btnViewRegisteredTeams, lblTournDesc,btnReportScores); // Tom
		
		stackPane.getChildren().add(ap);
		codTourneyScene = new Scene(stackPane, 600,400);
	}
	/**
	 * Set main window to the COD tournament scene
	 */
	public void OpenCodTourney()
	{
		CreateCodTourneyScene();
		window.setScene(codTourneyScene);
	}
	
	/**
	 * Create scene for COD team creation
	 */
	public void CreateTeamTournamentCOD()
	{
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
		Label lblBlankTeamName = new Label();
		lblBlankTeamName.setLayoutX(211.0);		
		lblBlankTeamName.setLayoutY(55.0);		
		
		JFXTextField txtTeamName = new JFXTextField();
		txtTeamName.setLabelFloat(true);
		txtTeamName.setPromptText("Team name");
		txtTeamName.setLayoutX(211.0);
		txtTeamName.setLayoutY(78.0);
		txtTeamName.setOnKeyPressed(e-> lblBlankTeamName.setText(null));
		
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
			if(Empty(txtTeamName))
			{
				lblBlankTeamName.setText("Team name cannot be empty");
				lblBlankTeamName.setTextFill(Color.RED);
			}
			else
			{
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
			}
			
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
		
		aPane.getChildren().addAll(txtTeamName,txtMember1,txtMember2,txtMember3,btnCreateTeam,btnReturn,lblBlankTeamName);
		
		createTeam = new Scene(aPane);
	}	
	/**
	 * Set main window to the COD create team scene
	 */
	public void OpenCreateTeamTournamentCOD()
	{
		CreateTeamTournamentCOD();
		window.setScene(createTeam);
	}
	
	
	/**
	 * Create scene to join a COD team
	 */
	public void CreateJoinTeamCOD()
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
	/**
	 * Set main window to the COD join team scene
	 */
	public void OpenJoinTeamCOD()
	{
		CreateJoinTeamCOD();
		window.setScene(joinTeam);
	}
	
	public void CreateCODScoreReport() {
		
		StackPane stackPane = new StackPane();
		AnchorPane aPane = new AnchorPane();

		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setLayoutX(14);
		btnReturn.setLayoutY(14);
		btnReturn.setOnAction(e->OpenCodTourney());
		
		JFXComboBox<String> cbGame1 = new JFXComboBox<String>();
		cbGame1.setLayoutX(254);
		cbGame1.setLayoutY(141);
		cbGame1.getItems().addAll(codTournament.GetTeams().get(0).GetTeamName(),codTournament.GetTeams().get(1).GetTeamName());
		
		JFXComboBox<String> cbGame2 = new JFXComboBox<String>();
		cbGame2.setLayoutX(254);
		cbGame2.setLayoutY(202);
		cbGame2.getItems().addAll(codTournament.GetTeams().get(2).GetTeamName(),codTournament.GetTeams().get(3).GetTeamName());
		JFXComboBox<String> cbFinal = new JFXComboBox<String>();
		cbFinal.setLayoutX(254);
		cbFinal.setLayoutY(266);
		
	
		cbGame1.setOnAction(e->{
			if(!cbFinal.getItems().contains(cbGame1.getValue()))
				cbFinal.getItems().add(cbGame1.getValue()); 
		});
		cbGame2.setOnAction(e->{ 
			if(!cbFinal.getItems().contains(cbGame2.getValue()))
				cbFinal.getItems().add(cbGame2.getValue()); 
		});
		Label lblHeader = new Label("Please select the winner for each game");
		lblHeader.setLayoutX(197);
		lblHeader.setLayoutY(100);
		Label lblGame1 =new Label("Game1");
		lblGame1.setLayoutX(201);
		lblGame1.setLayoutY(154);
		Label lblGame2 =new Label("Game2");
		lblGame2.setLayoutX(201);
		lblGame2.setLayoutY(219);
		Label lblFinal =new Label("Final");
		lblFinal.setLayoutX(204);
		lblFinal.setLayoutY(283);
		
		JFXButton btnSubmit = new JFXButton("Submit");
		btnSubmit.setLayoutX(274); 
		btnSubmit.setLayoutY(331);
		
		btnSubmit.setOnAction(e->{
			
			if(cbFinal.getValue().equals(cbGame1.getValue()) || cbFinal.getValue().equals(cbGame2.getValue()))
			{
				codTournament.ReportScore(cbGame1.getValue(), cbGame2.getValue(), cbFinal.getValue());
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Scores Reported"));
				dialogContent.setBody(new Text("Scores have been reported. Tournament is now closed"));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->{dialog.close(); OpenCodScene();});
				dialog.show();
			}
			else
			{
				System.out.println(cbFinal.getValue() + "is not in the final.");
				return;
			}
				
		});
		
		
		aPane.getChildren().addAll(lblHeader,lblGame1,lblGame2,lblFinal,cbGame1,cbGame2,cbFinal,btnSubmit,btnReturn);
		stackPane.getChildren().add(aPane);
		codReportScore = new Scene(stackPane,600,400);
		
	}
	
	public void OpenCODScoreReport()
	{
		CreateCODScoreReport();
		window.setScene(codReportScore);
	}
	
	
	//**********************************
	// Halo Scenes
	//**********************************
	/**
	 * Create the main Halo scene
	 */
	public void CreateHaloScene()
	{
		haloTournament = new Tournament();
		haloTournament.LoadTournamentData(11);
		
		StackPane stackPane = new StackPane();
		AnchorPane ap = new AnchorPane();
		
		Label lblTitle = new Label("Halo 5"); 
		lblTitle.setFont(new Font(24));
		Label lblLeaderboard = new Label("Leaderboards");
		Label lblTournaments = new Label ("Tournaments");
		lblLeaderboard.setStyle("-fx-font-weight: bold");
		lblTournaments.setStyle("-fx-font-weight: bold");
		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setOnAction(e-> OpenMainDashboard());
		

		JFXButton btnTournament1 = new JFXButton("4v4 Team Slayer (" + haloTournament.GetStatus() + ")");
	
		btnTournament1.setOnAction(e->{
			
			if(haloTournament.GetStatus().equals("CLOSED"))
			{
				for(Team t: haloTournament.GetTeams())
				{
					if(t.GetTeamName().equals(haloTournament.GetWinner().GetTeamName()))
					{
						JFXDialogLayout dialogContent = new JFXDialogLayout();
						dialogContent.setHeading(new Text("Tournament Closed"));
						dialogContent.setBody(new Text(t.GetTeamName() + " is the winner"));
						JFXDialog dialog = new JFXDialog();
						JFXButton btnOkay = new JFXButton("Okay");
						dialog.setContent(dialogContent);
						dialog.getChildren().add(btnOkay);
						dialog.setDialogContainer(stackPane);
						dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
						dialogContent.setActions(btnOkay);
						btnOkay.setOnAction(ev->dialog.close());
						dialog.show();
						return;
					}
				}
			}
			else
				OpenHaloTourney();
		});

		
		ImageView haloLogo = new ImageView(new Image("Halo.jpg"));
		haloLogo.setFitWidth(104);
		haloLogo.setFitHeight(148);
		
		
		//leader board table view
		TableView<Team> leaderBoardTable = new TableView<Team>();
		leaderBoardTable.setPrefHeight(200);
		leaderBoardTable.setPrefWidth(300);
		leaderBoardTable.setPlaceholder(new Label("No tournaments have been played for this game yet"));
		
		//team name column
		TableColumn<Team, String> teamNameCol = new TableColumn<>("Team Name");
		teamNameCol.setSortable(false);
		teamNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
		teamNameCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().GetTeamName()));
		
		//wins column
		TableColumn<Team, String> winsCol = new TableColumn<>("Wins");
		winsCol.setSortable(false);
		winsCol.setCellFactory(TextFieldTableCell.forTableColumn());
		winsCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().WinsToString()));
		
		//losses column
		TableColumn<Team, String> lossesCol = new TableColumn<>("Losses");
		lossesCol.setSortable(false);
		lossesCol.setCellFactory(TextFieldTableCell.forTableColumn());
		lossesCol.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().LossesToString()));
		

		//add columns to the list view
		leaderBoardTable.getColumns().addAll(teamNameCol,winsCol,lossesCol);
		leaderBoardTable.getSortOrder().add(winsCol);
		

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
		
		AnchorPane.setTopAnchor(lblLeaderboard, 55.0);
		AnchorPane.setLeftAnchor(lblLeaderboard, 175.0);
		
		AnchorPane.setTopAnchor(btnTournament1, 200.0);
		AnchorPane.setLeftAnchor(btnTournament1, 50.0);
		
		AnchorPane.setTopAnchor(lblTournaments, 175.0);
		AnchorPane.setLeftAnchor(lblTournaments, 50.0);
		
		AnchorPane.setTopAnchor(leaderBoardTable, 75.0);
		AnchorPane.setLeftAnchor(leaderBoardTable, 175.0);
		
		
		ap.getChildren().addAll(haloLogo,lblTitle,lblLeaderboard,btnTournament1,lblTournaments,btnReturn,leaderBoardTable);
		stackPane.getChildren().add(ap);
		haloScene = new Scene(stackPane,600,400);
	}
	/**
	 * Set main window to the main Halo scene
	 */
	public void OpenHaloScene()
	{
		CreateHaloScene();
		window.setScene(haloScene);
	}

	/**
	 * Create scene for Halo Tournament
	 */
	public void CreateHaloTourneyScene(){
		
		
		haloTournament.LoadTournamentData(11);
		StackPane stackPane = new StackPane();
		AnchorPane ap = new AnchorPane();
		JFXButton btnCreateTeam = new JFXButton("Create team");
		JFXButton btnJoinTeam = new JFXButton("Join team");
		JFXButton btnViewRegisteredTeams= new JFXButton("View Registered Teams"); // Tom
		JFXButton btnReportScores = new JFXButton("Report Scores");
		
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
					JFXDialogLayout dialogContent = new JFXDialogLayout();
					dialogContent.setHeading(new Text("Cannot join team"));
					dialogContent.setBody(new Text("You are already on a team"));
					JFXDialog dialog = new JFXDialog();
					JFXButton btnOkay = new JFXButton("Okay");
					dialog.setContent(dialogContent);
					dialog.getChildren().add(btnOkay);
					dialog.setDialogContainer(stackPane);
					dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
					dialogContent.setActions(btnOkay);
					btnOkay.setOnAction(ev->dialog.close());
					dialog.show();
					return;
				}
			}
			OpenJoinTeamHalo();
			});
		
		
		
		btnReportScores.setOnAction(e->{
			if(!currentUser.GetPlatform().GetPlatformName().equals(haloTournament.GetGame().GetPlatform().GetPlatformName())) {
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Incompatiable Systems"));
				dialogContent.setBody(new Text("You play on " + currentUser.GetPlatform().GetPlatformName() + ". The tournament is on " + haloTournament.GetGame().GetPlatform().GetPlatformName()));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->dialog.close());
				dialog.show();
				return;
			}
			if(haloTournament.GetTeamsJoined() < haloTournament.GetBrackSize())
			{
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Not enough teams"));
				dialogContent.setBody(new Text("Scores cannot be reported until the bracket is full"));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->dialog.close());
				dialog.show();
				return;
			}
			else
				OpenHaloScoreReport();
			
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
					JFXDialogLayout dialogContent = new JFXDialogLayout();
					dialogContent.setHeading(new Text("Cannot create team"));
					dialogContent.setBody(new Text("You are already on a team"));
					JFXDialog dialog = new JFXDialog();
					JFXButton btnOkay = new JFXButton("Okay");
					dialog.setContent(dialogContent);
					dialog.getChildren().add(btnOkay);
					dialog.setDialogContainer(stackPane);
					dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
					dialogContent.setActions(btnOkay);
					btnOkay.setOnAction(ev->dialog.close());
					dialog.show();
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
		Label lblTourneyDescription = new Label("Players are divided up into teams of 4 players. The goal is to kill as many players as possible. \nThis is a bracket style tournament with 4 teams, single elimination");
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
		
		AnchorPane.setTopAnchor(lblTourneyDescription, 100.0);
		AnchorPane.setLeftAnchor(lblTourneyDescription,95.0);
		
		AnchorPane.setTopAnchor(lblPrize, 170.0);
		AnchorPane.setLeftAnchor(lblPrize, 137.0);
		
		AnchorPane.setTopAnchor(lblBracketSize, 200.0);
		AnchorPane.setLeftAnchor(lblBracketSize, 137.0);
		
		AnchorPane.setTopAnchor(lblTeamsJoined, 230.0);
		AnchorPane.setLeftAnchor(lblTeamsJoined, 137.0);
		
		AnchorPane.setTopAnchor(lblPrizeAmt, 160.0);
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
		
		AnchorPane.setTopAnchor(btnReportScores, 310.0);
		AnchorPane.setLeftAnchor(btnReportScores, 300.0); 
		
		
		ap.getChildren().addAll(btnReturn,lblTitle, lblLocation,lblTourneyDescription,lblPrize,lblBracketSize,lblTeamsJoined,lblPrizeAmt,lblBracketAmt,lblTeamsJoinedVal,btnJoinTeam,btnCreateTeam, btnViewRegisteredTeams,btnReportScores); // Tom
		stackPane.getChildren().add(ap);
		haloTourneyScene = new Scene(stackPane, 600,400);
	}
	/**
	 * Set main window to the Halo tournament scene
	 */
	public void OpenHaloTourney() {
		
		CreateHaloTourneyScene();
		window.setScene(haloTourneyScene);
	}
	
	/**
	 * Create scene for Halo team creation
	 */
	public void CreateTeamTournamentHalo() {

		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
	
		Label lblBlankTeamName = new Label();
		lblBlankTeamName.setLayoutX(211.0);		
		lblBlankTeamName.setLayoutY(55.0);	
		
		JFXTextField txtTeamName = new JFXTextField();
		txtTeamName.setLabelFloat(true);
		txtTeamName.setPromptText("Team name");
		txtTeamName.setLayoutX(211.0);
		txtTeamName.setLayoutY(78.0);
		txtTeamName.setOnKeyPressed(e-> lblBlankTeamName.setText(null));
		
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
			if(Empty(txtTeamName))
			{
				lblBlankTeamName.setText("Team name cannot be empty");
				lblBlankTeamName.setTextFill(Color.RED);
			}
			else 
			{
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
			}
		
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
		
		aPane.getChildren().addAll(txtTeamName,txtMember1,txtMember2,txtMember3,btnCreateTeam,btnReturn,lblBlankTeamName);
		
		createTeam = new Scene(aPane);
		
	}
	/**
	 * Set main window to Halo team creation scene
	 */
	public void OpenCreateTeamTournamentHalo()
	{
		CreateTeamTournamentHalo();
		window.setScene(createTeam);
	}
	/**
	* Create scene to join a Halo team
	*/
	public void CreateJoinTeamHalo()
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
	/**
	 * Set main window to the Halo join team scene
	 */
	public void OpenJoinTeamHalo()
	{
		CreateJoinTeamHalo();
		window.setScene(joinTeam);
	}
		
	public void CreateHaloScoreReport() {
		
		StackPane stackPane = new StackPane();
		AnchorPane aPane = new AnchorPane();

		JFXButton btnReturn = new JFXButton("<-");
		btnReturn.setLayoutX(14);
		btnReturn.setLayoutY(14);
		btnReturn.setOnAction(e->OpenCodTourney());
		
		JFXComboBox<String> cbGame1 = new JFXComboBox<String>();
		cbGame1.setLayoutX(254);
		cbGame1.setLayoutY(141);
		cbGame1.getItems().addAll(haloTournament.GetTeams().get(0).GetTeamName(),haloTournament.GetTeams().get(1).GetTeamName());
		
		JFXComboBox<String> cbGame2 = new JFXComboBox<String>();
		cbGame2.setLayoutX(254);
		cbGame2.setLayoutY(202);
		cbGame2.getItems().addAll(haloTournament.GetTeams().get(2).GetTeamName(),haloTournament.GetTeams().get(3).GetTeamName());
		JFXComboBox<String> cbFinal = new JFXComboBox<String>();
		cbFinal.setLayoutX(254);
		cbFinal.setLayoutY(266);
		
	
		cbGame1.setOnAction(e->{
			if(!cbFinal.getItems().contains(cbGame1.getValue()))
				cbFinal.getItems().add(cbGame1.getValue()); 
		});
		cbGame2.setOnAction(e->{ 
			if(!cbFinal.getItems().contains(cbGame2.getValue()))
				cbFinal.getItems().add(cbGame2.getValue()); 
		});
		Label lblHeader = new Label("Please select the winner for each game");
		lblHeader.setLayoutX(197);
		lblHeader.setLayoutY(100);
		Label lblGame1 =new Label("Game1");
		lblGame1.setLayoutX(201);
		lblGame1.setLayoutY(154);
		Label lblGame2 =new Label("Game2");
		lblGame2.setLayoutX(201);
		lblGame2.setLayoutY(219);
		Label lblFinal =new Label("Final");
		lblFinal.setLayoutX(204);
		lblFinal.setLayoutY(283);
		
		JFXButton btnSubmit = new JFXButton("Submit");
		btnSubmit.setLayoutX(274); 
		btnSubmit.setLayoutY(331);
		
		btnSubmit.setOnAction(e->{
			
			if(cbFinal.getValue().equals(cbGame1.getValue()) || cbFinal.getValue().equals(cbGame2.getValue()))
			{
				haloTournament.ReportScore(cbGame1.getValue(), cbGame2.getValue(), cbFinal.getValue());
				JFXDialogLayout dialogContent = new JFXDialogLayout();
				dialogContent.setHeading(new Text("Scores Reported"));
				dialogContent.setBody(new Text("Scores have been reported. Tournament is now closed"));
				JFXDialog dialog = new JFXDialog();
				JFXButton btnOkay = new JFXButton("Okay");
				dialog.setContent(dialogContent);
				dialog.getChildren().add(btnOkay);
				dialog.setDialogContainer(stackPane);
				dialog.setTransitionType(JFXDialog.DialogTransition.CENTER);
				dialogContent.setActions(btnOkay);
				btnOkay.setOnAction(ev->{dialog.close(); OpenHaloScene();});
				dialog.show();
			}
			else
			{
				System.out.println(cbFinal.getValue() + "is not in the final.");
				return;
			}
				
		});
		
		
		aPane.getChildren().addAll(lblHeader,lblGame1,lblGame2,lblFinal,cbGame1,cbGame2,cbFinal,btnSubmit,btnReturn);
		stackPane.getChildren().add(aPane);
		haloReporScore = new Scene(stackPane,600,400);
		
	}

	public void OpenHaloScoreReport()
	{
		CreateHaloScoreReport();
		window.setScene(haloReporScore);
	}
	
	public void CreateViewRegisteredTeams(Tournament t){ 	
		AnchorPane	aPane = new AnchorPane();
		aPane.setPrefHeight(400);
		aPane.setPrefWidth(600);
		
		ArrayList<Team> registered = new ArrayList<Team>();
		registered =  t.ViewRegisterdTeams(t.GetTournamentName());
		
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
		
		JFXButton btnReturn = new JFXButton("<-");
		
		if(window.getScene() == codTourneyScene)
			btnReturn.setOnAction(e->OpenCodTourney());
		if(window.getScene() == fortniteTourneyScene)
			btnReturn.setOnAction(e->OpenFortniteTourney());
		if(window.getScene() == haloTourneyScene)
			btnReturn.setOnAction(e->OpenHaloTourney());
		
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
