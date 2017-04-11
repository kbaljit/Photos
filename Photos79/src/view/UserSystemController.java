package view;

import javafx.stage.Stage;
import application.User;
import javafx.fxml.FXML;
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
	
	public UserSystemController(User U){
		this.user=U;
		
	}
	
	
	public void start(Stage mainStage){    
		
		

	}

}
