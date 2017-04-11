package view;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import application.Album;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class UserSystemController {
	private User user; 
	
	@FXML Button createAlbum;
	@FXML Button deleteAlbum;
	@FXML Button logout;
	@FXML Tab albums;
	@FXML TilePane tilePane;
	@FXML StackPane stackPane;
	@FXML ImageView image;
	@FXML VBox vbox;
	@FXML TextField albumTitle;
	@FXML TextField photoCount;
	@FXML TextField dateRange;
	@FXML Button Logout;
	
	public UserSystemController(User U){
		this.user=U;
		
	}
	
	@FXML 
	private void Logout(ActionEvent E) throws IOException{
		//Save changes to disk
		
		Parent logoutParent=FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
		Scene logoutScene=new Scene(logoutParent, 450, 350);
		Stage logoutStage=(Stage) Logout.getScene().getWindow();
		logoutStage.hide();
	    logoutStage.setTitle("Photos App Login");
	    logoutStage.setResizable(false);
		logoutStage.setScene(logoutScene);
		logoutStage.show();
		
	}
	
	@FXML
	private void createAlbum(ActionEvent E) throws IOException{

		
	}
	
	@FXML 
	private void deleteAlbum(ActionEvent E) throws IOException{
		
	}
	
	@FXML
	private void uploadPicture(ActionEvent E) throws IOException{
		
	}
	
	@FXML
	private void deletePicture(ActionEvent E) throws IOException{
		
	}
	
	public void start(Stage mainStage){ 
		ArrayList<Album> orig=this.user.getAlbums();
		tilePane.setOrientation(Orientation.HORIZONTAL);
		tilePane.setHgap(8.0);
		
		for(int i=0; i<orig.size(); i++){
			Button B=new Button();
			B.setText(orig.get(i).getTitle());
			tilePane.getChildren().add(B);
			
		}
		
		

	}

}
