package view;
import java.io.IOException;
import java.util.ArrayList;

import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
	@FXML Button Login;
	@FXML TextField username;
	@FXML PasswordField password;
	private ArrayList<User> users;
	
	public LoginController(ArrayList<User> users){
		this.users = users;
	}
	
	@FXML
	private void buttonAction(ActionEvent E) throws IOException{
		if(username.getText().equals("admin") && password.getText().equals("admin")){
			Parent loginParent=FXMLLoader.load(getClass().getResource("/view/AdminSystem.fxml"));
			Scene loginScene=new Scene(loginParent, 500, 500);
			Stage loginStage=(Stage) ((Node) E.getSource()).getScene().getWindow();
			loginStage.hide();
			loginStage.setTitle("Admin System");
			loginStage.setResizable(false);
			loginStage.setScene(loginScene);
			loginStage.show();
		}
		else if((!username.getText().isEmpty()) && (!password.getText().isEmpty())){
			for(int i = 0; i < users.size(); i++){
				if(username.getText().equals(users.get(i).getUsername())){
					if(password.getText().equals(users.get(i).getPassword())){
						Parent loginParent=FXMLLoader.load(getClass().getResource("/view/UserSystem.fxml"));
						Scene loginScene=new Scene(loginParent, 700, 700);
						Stage loginStage=(Stage) ((Node) E.getSource()).getScene().getWindow();
						loginStage.hide();
					    loginStage.setTitle("User System");
					    loginStage.setResizable(false);
						loginStage.setScene(loginScene);
						loginStage.show();
					}else{
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error Dialog");
						alert.setHeaderText("Incorrect Password");
						alert.setContentText("The password you have entered is incorrect. Please try again.");

						alert.showAndWait();
					}
				}
			}
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Incorrect Username");
			alert.setContentText("There is no account associated with the username you have entered. Please try again.");

			alert.showAndWait();
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
		}else if((username.getText().isEmpty()) && (password.getText().isEmpty())){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Missing Username and Password");
			alert.setContentText("You must log in with your username and password. Please try again.");

			alert.showAndWait();
		}
		
	}
	public void start(Stage mainStage){    
		
		

	}

}
