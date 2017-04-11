package application;
	
import java.io.Serializable;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import view.LoginController;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Photos extends Application implements Serializable{
		@Override
		public void start(Stage primaryStage) 
				throws Exception {
					 
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
					  loader.setController(new LoginController(new ArrayList<User>()));  
				      AnchorPane root = (AnchorPane)loader.load();
				      LoginController loginController =  loader.getController();
				      
				      loginController.start(primaryStage);
				      Scene scene = new Scene(root, 450, 350);
				      primaryStage.setScene(scene);
				      primaryStage.setTitle("Photos App Login");
				      primaryStage.setResizable(false);
				      primaryStage.show(); 
	}
		
	
	public static void main(String[] args) {
		launch(args);
	}
}
