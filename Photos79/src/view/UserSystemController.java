package view;

import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;

import application.Album;
import application.Photo;
import application.PhotoLibrary;
import application.Tag;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class UserSystemController {
	private User user; 
	private PhotoLibrary library;
	
	@FXML Button createAlbum;
	@FXML Button deleteAlbum;
	@FXML Button Add;
	@FXML Button Delete;
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
	@FXML TabPane tabPane;
	
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
		Scene logoutScene=new Scene(root, 450, 350);
		Stage logoutStage=(Stage) logout.getScene().getWindow();
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
	
	@FXML
	private void selectAlbum(ActionEvent E) throws IOException{
		Tab tab=new Tab();
		Button b=(Button)E.getSource();
		VBox vb=(VBox) b.getParent();
		TextField AlbumField=(TextField) vb.getChildren().get(0);
		String AlbumName=AlbumField.getText();
		tab.setText(AlbumName);
		
		AnchorPane anchor=new AnchorPane();
		tab.setContent(anchor);
		TilePane tile=new TilePane();
		anchor.getChildren().add(tile);
		StackPane stack=new StackPane();
		tile.getChildren().add(stack);
		
		tabPane.getTabs().add(tab);
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		/*createAlbum.setVisible(false);
		deleteAlbum.setVisible(false);
		Add.setVisible(true);
		Delete.setVisible(true);*/
		selectionModel.select(tab);
		
		
		for(int i=0; i<this.user.getAlbums().size(); i++){
			if(AlbumName.equals(this.user.getAlbums().get(i))){
				for(int j=0; j<this.user.getAlbums().get(i).getPhotos().size(); j++){
				
				Photo P=this.user.getAlbums().get(i).getPhotos().get(j);
				ArrayList<Tag> Tag=P.getTags();
				Calendar date=P.getDate();
				String caption=P.getCaption();
				
				File F=P.getImage();
				BufferedImage image=ImageIO.read(F);
				WritableImage wi=new WritableImage(image.getWidth(), image.getHeight());
				PixelWriter pw=wi.getPixelWriter();
				for(int k=0; k<image.getWidth();k++){
					for(int l=0; l<image.getHeight(); l++){
						pw.setArgb(k, l, image.getRGB(k, l));
					}
				}
				ImageView iv=new ImageView(wi);
				stack.getChildren().add(iv);
				
				}
				
				
			}
		}
		
		
	}
	
	public void start(Stage mainStage){ 
		ArrayList<Album> orig=this.user.getAlbums();
		titleLabel.setText(this.user.getUsername()+" "+"Photos");
		tilePane=new TilePane();
		tilePane.setOrientation(Orientation.HORIZONTAL);
		tilePane.setHgap(8.0);
		
		for(int i=0; i<orig.size(); i++){
			Button B=new Button();
			B.setText(orig.get(i).getTitle());
			tilePane.getChildren().add(B);
			
		}
		tabPane.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal)->
				{ if(tabPane.getSelectionModel().getSelectedItem().equals(albums)){
					createAlbum.setVisible(true);
					deleteAlbum.setVisible(true);
					Add.setVisible(false);
					Delete.setVisible(false);
				}
					createAlbum.setVisible(false);
					deleteAlbum.setVisible(false);
					Add.setVisible(true);
					Delete.setVisible(true);
				
					
				});
		
		

	}
	

}
