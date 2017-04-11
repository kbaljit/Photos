package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.PhotoLibrary;
import application.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Pair;

public class AdminSystemController {
	private PhotoLibrary library;
	public String newUser;
	public String newPassword;
	
	@FXML ListView<String> userDisplay;
	@FXML PieChart AdminChart;
	@FXML MenuItem Logout;
	@FXML MenuItem Create;
	@FXML MenuItem Delete;
	@FXML MenuItem About;
	@FXML MenuBar Menu;
	@FXML Button List;
	
	public AdminSystemController(PhotoLibrary library){
		this.library=library;
	}
	
	@FXML 
	private void Logout(ActionEvent E) throws IOException{
		//Save changes to disk
		
		Parent logoutParent=FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene logoutScene=new Scene(logoutParent, 450, 350);
		Stage logoutStage=(Stage) Menu.getScene().getWindow();
		logoutStage.hide();
	    logoutStage.setTitle("Photos App Login");
	    logoutStage.setResizable(false);
		logoutStage.setScene(logoutScene);
		logoutStage.show();
		
	}
	
	@FXML
	private void createUser(ActionEvent E) throws IOException{
	
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("User Creation");
		dialog.setHeaderText("Please Enter New Username and Password");
		
		ButtonType loginButtonType = new ButtonType("Enter", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);
		
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		dialog.getDialogPane().setContent(grid);

		
		Platform.runLater(() -> username.requestFocus());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		    	if(!username.getText().isEmpty() && !password.getText().isEmpty()){
		    		newUser=username.getText();
		    		newPassword=password.getText();
		    		return new Pair<>(username.getText(), password.getText());
		    	}
		    	else{
		    		Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Dialog");
		    		alert.setHeaderText("Incorrect Entry");
		    		alert.setContentText("Must enter both a username and a password");
		    		
		    		alert.showAndWait();
		    		return null;
		    	}
		    }
			return null;
		} );
	
		Optional<Pair<String, String>> result = dialog.showAndWait();
		
		for(int i=0; i<users.size();i++){
			if(newUser.equals(users.get(i))){
				Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Dialog");
	    		alert.setHeaderText("Existing Username");
	    		alert.setContentText("The username you are trying to create already exists");
	    		alert.showAndWait();
	    		return;
			}
		}
		
		User U=new User(newUser, newPassword);
		Users.add(U);
		users.add(newUser);
		this.library.setUsers(Users);
		PhotoLibrary.writeApp(this.library);
	}
	
	@FXML 
	private void deleteUser(ActionEvent E) throws IOException{
		
	}
	
	@FXML
	private void listUsers(ActionEvent E) throws IOException{
		userDisplay.setItems(users);

		
	}
	
	@FXML
	private void helpDialog(ActionEvent E) throws IOException{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Usage Help");
		alert.setContentText("-Select File to Logout\n-Select Edit to Create or Delete Users\n-Select List to display all users");
		alert.showAndWait();
		
	}
	public ObservableList<String> users;
	public ArrayList<User> Users;
	
	public void start(Stage mainStage){
			Users=this.library.getUsers();
			users=FXCollections.observableArrayList();
			for(int i=0; i<Users.size(); i++){
				users.add(Users.get(i).getUsername());
			}
			

	}

}
