package view;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
	@FXML Button Login;
	@FXML TextField username;
	@FXML PasswordField password;
	
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
			
			Parent loginParent=FXMLLoader.load(getClass().getResource("/view/UserSystem.fxml"));
			Scene loginScene=new Scene(loginParent, 700, 700);
			Stage loginStage=(Stage) ((Node) E.getSource()).getScene().getWindow();
			loginStage.hide();
		    loginStage.setTitle("User System");
		    loginStage.setResizable(false);
			loginStage.setScene(loginScene);
			loginStage.show();
			
			
		}
		else{
			
		}
		
		
	}
	public void start(Stage mainStage){    
		
		

	}

}
