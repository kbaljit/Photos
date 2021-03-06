package view;
import java.io.IOException;
import java.util.ArrayList;

import application.PhotoLibrary;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * 
 * @author Baljit Kaur
 * @author Milan Patel
 *
 * Controls application's Login page
 */
public class LoginController {
	@FXML Button Login;
	@FXML TextField username;
	@FXML PasswordField password;
	private PhotoLibrary library;
	
	/**
	 * Initializes an login control object for given photoLibrary
	 * @param library PhotoLibrary instance
	 */
	public LoginController(PhotoLibrary library){
		this.library=library;
	}
	
	/**
	 * Executes when login button is clicked
	 * @param E
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@FXML
	private void buttonAction(ActionEvent E) throws IOException, ClassNotFoundException{
		if(username.getText().equals("admin") && password.getText().equals("admin")){
			
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminSystem.fxml")); 
			loader.setController(new AdminSystemController(this.library));
		    AnchorPane root = (AnchorPane)loader.load();
		    AdminSystemController adminController =  loader.getController();
			
		    Scene loginScene=new Scene(root, 500, 500);
			Stage loginStage=(Stage) ((Node) E.getSource()).getScene().getWindow();
			adminController.start(loginStage);
			loginStage.hide();
			loginStage.setTitle("Admin System");
			loginStage.setResizable(false);
			loginStage.setScene(loginScene);
			loginStage.show();
		}
		else if((!username.getText().isEmpty()) && (!password.getText().isEmpty())){
			library = PhotoLibrary.readApp();
			users = library.getUsers();
			boolean userFound=false;
			for(int i = 0; i < users.size(); i++){
				if(username.getText().equals(users.get(i).getUsername())){
					if(password.getText().equals(users.get(i).getPassword())){
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/UserSystem.fxml")); 
						loader.setController(new UserSystemController(users.get(i), this.library));
					    AnchorPane root = (AnchorPane)loader.load();
					    
					    UserSystemController userController =  loader.getController();
						Scene userScene=new Scene(root, 810, 620);
						Stage userStage=(Stage) ((Node) E.getSource()).getScene().getWindow();
						userController.start(userStage);
						userStage.hide();
					    userStage.setTitle(users.get(i).getUsername());
					    userStage.setResizable(false);
						userStage.setScene(userScene);
						userStage.show();
						userFound=true;
					}else{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Incorrect Password");
						alert.setContentText("The password you have entered is incorrect. Please try again.");

						alert.showAndWait();
					}
				}
			}
			if(!userFound){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Incorrect Username");
			alert.setContentText("There is no account associated with the username you have entered. Please try again.");

			alert.showAndWait();
			}
		}
		else if((!username.getText().isEmpty()) && (password.getText().isEmpty())){
			for(int i = 0; i < users.size(); i++){
				if(username.getText().equals(users.get(i).getUsername())){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Dialog");
					alert.setHeaderText("No Password Entered");
					alert.setContentText("You must enter a password to log in to your account. Please try again.");

					alert.showAndWait();
					return;
				}
			}
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Incorrect Username");
			alert.setContentText("There is no account associated with the username you have entered. Please try again.");

			alert.showAndWait();
		}
		else if((username.getText().isEmpty()) && (password.getText().isEmpty())){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Missing Username and Password");
			alert.setContentText("You must log in with your username and password. Please try again.");

			alert.showAndWait();
		}
		
	}
	public ArrayList<User> users;
	public void start(Stage mainStage){    
		users=this.library.getUsers();
		

	}

}

