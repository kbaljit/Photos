package view;

import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import application.Album;
import application.PhotoLibrary;
import application.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class UserSystemController {
	private User user; 
	private PhotoLibrary library;
	
	@FXML Button createAlbum;
	@FXML Button deleteAlbum;
	@FXML Button renameAlbum;
	@FXML Button logout;
	@FXML Tab albums;
	@FXML TilePane tilePane;
	@FXML StackPane stackPane;
	@FXML ImageView image;
	@FXML VBox vbox;
	@FXML TextField albumTitle;
	@FXML TextField photoCount;
	@FXML TextField dateRange;
	@FXML Label titleLabel;
	
	public UserSystemController(User U, PhotoLibrary library){
		this.user=U;
		this.library=library;
		
	}
	
	@FXML 
	private void Logout(ActionEvent E) throws IOException{
		//Save changes to disk
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml")); 
		loader.setController(new LoginController(this.library));
	    AnchorPane root = (AnchorPane)loader.load();
	    LoginController loginController =  loader.getController();
		Scene logoutScene=new Scene(root, 600, 500);
		Stage logoutStage=(Stage) logout.getScene().getWindow();
		logoutStage.hide();
	    logoutStage.setTitle("Photos App Login");
	    logoutStage.setResizable(false);
		logoutStage.setScene(logoutScene);
		logoutStage.show();
		
	}
	
	@FXML
	private void createAlbum(ActionEvent E) throws IOException{
		String albumTitle = "";
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Album Creation");
		dialog.setHeaderText("Please Enter Album Title");
		Optional<String> result = dialog.showAndWait();
		
		while(dialog.getResult().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Incorrect Entry");
    		alert.setContentText("Must enter a title");
    		alert.showAndWait();
    		dialog.setTitle("Album Creation");
    		dialog.setHeaderText("Please Enter Album Title");
    		result = dialog.showAndWait();
		}
		
        if (result.isPresent()) {
            albumTitle = result.get();
        }
		
		if(!albumTitle.equals("")){
			for(int i = 0; i < user.getAlbums().size(); i++){
				if(albumTitle.equals(user.getAlbums().get(i).getTitle())){
					Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Dialog");
		    		alert.setHeaderText("Existing Album");
		    		alert.setContentText("Album not created. An album with this title already exists!");
		    		alert.showAndWait();
		    		return;
				}
			}
		}
		
		Album album = new Album(albumTitle);
		user.addAlbum(album);
		PhotoLibrary.writeApp(this.library);
		
		
		StackPane sp = new StackPane();
		ImageView iv = new ImageView();
		VBox vb = new VBox();
		TextField tv1 = new TextField();
		tv1.setText(albumTitle);
		TextField tv2 = new TextField("0 Photos");
		TextField tv3 = new TextField("Date Range");
		Button button = new Button("OPEN");
		vb.getChildren().add(tv1);
		vb.getChildren().add(tv2);
		vb.getChildren().add(tv3);
		vb.getChildren().add(button);
		sp.getChildren().add(iv);
		sp.getChildren().add(vb);
		tilePane.getChildren().add(sp);
		
		Platform.runLater(() -> tilePane.requestFocus());
	}
	
	@FXML 
	private void deleteAlbum(ActionEvent E) throws IOException{
		TextField tf = new TextField();
		//get title text field
		
		for(int i = 0; i < user.getAlbums().size(); i++){
			if(user.getAlbums().get(i).getTitle().equals(tf.getText())){
				user.deleteAlbumByIndex(i);
				return;
			}
		}
	}
	
	@FXML
	private void renameAlbum() throws IOException{
        String albumTitle = "";
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Rename Album");
		dialog.setHeaderText("Please Enter New Album Title");
		Optional<String> result = dialog.showAndWait();
		
		while(dialog.getResult().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Incorrect Entry");
    		alert.setContentText("Must enter a title");
    		alert.showAndWait();
    		dialog.setTitle("Album Creation");
    		dialog.setHeaderText("Please Enter Album Title");
    		result = dialog.showAndWait();
		}
		
        if (result.isPresent()) {
            albumTitle = result.get();
        }
		
		if(!albumTitle.equals("")){
			for(int i = 0; i < user.getAlbums().size(); i++){
				if(albumTitle.equals(user.getAlbums().get(i).getTitle())){
					Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Dialog");
		    		alert.setHeaderText("Existing Album");
		    		alert.setContentText("Cannot not rename. An album with this title already exists!");
		    		alert.showAndWait();
		    		return;
				}
			}
		}
		
		//update Album
		
		PhotoLibrary.writeApp(this.library);
	}
	
	@FXML
	private void openAlbum(){
		
	}
	
	@FXML
	private void uploadPicture(ActionEvent E) throws IOException{
		
	}
	
	@FXML
	private void deletePicture(ActionEvent E) throws IOException{
		
	}
	
	public void start(Stage mainStage){ 
		ArrayList<Album> orig=this.user.getAlbums();
		titleLabel.setText(this.user.getUsername().toUpperCase().charAt(0) + this.user.getUsername().substring(1) +"'s "+"Photos");
		
		for(int i = 0; i < user.getAlbums().size(); i++){
			StackPane sp = new StackPane();
			ImageView iv = new ImageView();
			VBox vb = new VBox();
			tilePane.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(false);
				renameAlbum.setVisible(false);
			});
			TextField tv1 = new TextField();
			tv1.setText(user.getAlbums().get(i).getTitle());
			tv1.setEditable(false);
			tv1.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
			});
			
			TextField tv2 = new TextField(user.getAlbums().get(i).getNumPhotos() + " Photos");
			tv2.setEditable(false);
			tv2.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
			});
			TextField tv3 = new TextField("Date Range");
			tv3.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
			});
			
			if(user.getAlbums().get(i).getNumPhotos() > 0){
				tv3 = new TextField(user.getAlbums().get(i).dateRange());
			}
			tv3.setEditable(false);
			Button button = new Button("OPEN");
			button.setOnAction(e -> {
				openAlbum();
			});
			vb.getChildren().add(tv1);
			vb.getChildren().add(tv2);
			vb.getChildren().add(tv3);
			vb.getChildren().add(button);
			sp.getChildren().add(iv);
			sp.getChildren().add(vb);
			tilePane.getChildren().add(sp);
			
			Platform.runLater(() -> tilePane.requestFocus());
		}

	}

}
