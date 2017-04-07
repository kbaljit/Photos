package view;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class AdminSystemController {
	@FXML ListView<String> UserDisplay;
	@FXML PieChart AdminChart;
	@FXML MenuItem Logout;
	@FXML MenuItem Create;
	@FXML MenuItem Delete;
	@FXML MenuItem About;
	@FXML MenuBar Menu;
	
	@FXML 
	private void Logout(ActionEvent E) throws IOException{
		Parent logoutParent=FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene logoutScene=new Scene(logoutParent, 450, 350);
		Stage logoutStage=(Stage) Menu.getScene().getWindow();
		logoutStage.hide();
	    logoutStage.setTitle("Photos App Login");
	    logoutStage.setResizable(false);
		logoutStage.setScene(logoutScene);
		logoutStage.show();
		
	}
	
public void start(Stage mainStage){    
		

	}

}
