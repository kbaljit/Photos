package view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;

import java.util.Optional;

import application.Album;
import application.Photo;
import application.PhotoLibrary;
import application.Tag;
import application.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
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
	private StackPane lastClicked;
	
	@FXML Button createAlbum;
	@FXML Button deleteAlbum;
	@FXML Button Add;
	@FXML Button Delete;
	@FXML Button Search;
	@FXML Button Caption;
	@FXML Button Tag;
	@FXML Button Copy;
	@FXML Button Move;
	@FXML Button SlideShow;
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
		Scene logoutScene=new Scene(root, 600, 500);
		Stage logoutStage=(Stage) logout.getScene().getWindow();
		loginController.start(logoutStage);
		logoutStage.hide();
	    logoutStage.setTitle("Photos App Login");
	    logoutStage.setResizable(false);
		logoutStage.setScene(logoutScene);
		logoutStage.show();
		PhotoLibrary.writeApp(this.library);
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
		for(int i=0; i<this.library.getUsers().size();i++){
			if(user.getUsername().equals(this.library.getUsers().get(i).getUsername())){
				this.library.getUsers().remove(i);
				this.library.getUsers().add(user);
			}
		}
		PhotoLibrary.writeApp(this.library);
		
		tilePane.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(false);
			renameAlbum.setVisible(false);
		});
		
		StackPane sp = new StackPane();
		VBox vb = new VBox();
		TextField tv1 = new TextField();
		tv1.setEditable(false);
		tv1.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(true);
			renameAlbum.setVisible(true);
		});
		
		tv1.setText(albumTitle);
		TextField tv2 = new TextField("0 Photos");
		tv2.setEditable(false);
		tv2.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(true);
			renameAlbum.setVisible(true);
		});
		
		TextField tv3 = new TextField("Date Range");
		tv3.setEditable(false);
		tv3.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(true);
			renameAlbum.setVisible(true);
		});
		
		Button button = new Button("OPEN");
		button.setOnAction(e -> {
			try {
				openAlbum(e);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		vb.getChildren().add(tv1);
		vb.getChildren().add(tv2);
		vb.getChildren().add(tv3);
		vb.getChildren().add(button);
		sp.getChildren().add(vb);
		tilePane.getChildren().add(sp);
		
		Platform.runLater(() -> tilePane.requestFocus());
	}
	
	@FXML 
	private void deleteAlbum(ActionEvent E) throws IOException{
		String title = "";
		VBox vb = (VBox)lastClicked.getChildren().get(0);
		TextField tf = (TextField)vb.getChildren().get(0);
		title = tf.getText();
		
		int item = -1;
		
		for(int i = 0; i < tilePane.getChildren().size(); i++){
			if(lastClicked.equals(tilePane.getChildren().get(i))){
				item = i;
				continue;
			}
		}
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Album" + title + " will be deleted.");
		alert.setContentText("Are you sure you want to delete this album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    user.deleteAlbumByIndex(item);
		    PhotoLibrary.writeApp(this.library);
		    tilePane.getChildren().remove(item);
		    Platform.runLater(() -> tilePane.requestFocus());
		} else {
			Platform.runLater(() -> tilePane.requestFocus());
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
    		dialog.setTitle("Rename Album");
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
		
		int item = -1;
		
		for(int i = 0; i < tilePane.getChildren().size(); i++){
			if(lastClicked.equals(tilePane.getChildren().get(i))){
				item = i;
				continue;
			}
		}
		
		
		user.getAlbums().get(item).setTitle(albumTitle);
		PhotoLibrary.writeApp(this.library);
		
		VBox vb = (VBox)lastClicked.getChildren().get(0);
		TextField tf = (TextField)vb.getChildren().get(0);
		tf.setText(albumTitle);
		
		Platform.runLater(() -> tf.requestFocus());
		Platform.runLater(() -> tilePane.requestFocus());
	}
	
	@FXML
	private void openAlbum(ActionEvent e) throws IOException{
		ScrollPane root=new ScrollPane();
		root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		Tab tab=new Tab();
		Button b=(Button)e.getSource();
		VBox v=(VBox) b.getParent();
		TextField AlbumField=(TextField) v.getChildren().get(0);
		String AlbumName=AlbumField.getText();
		tab.setText(AlbumName);
		TilePane Tile=new TilePane();
		root.setContent(Tile);
		tab.setContent(root);
		Tile.setStyle("-fx-background-color: #009688");
		
		tabPane.getTabs().add(tab);
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(tab);
		for(int i=0; i<this.user.getAlbums().size(); i++){
			if(AlbumName.equals(this.user.getAlbums().get(i).getTitle())){
				for(int j=0; j<this.user.getAlbums().get(i).getPhotos().size(); j++){
				
				Photo P=this.user.getAlbums().get(i).getPhotos().get(j);
				ArrayList<Tag> Tag=P.getTags();
				Calendar date=P.getDate();
				String caption=P.getCaption();
				File F=P.getImage();
				BufferedImage image=ImageIO.read(F);
				Image I=convertBuffered(image);
				
				ScrollPane scroll=(ScrollPane) tabPane.getSelectionModel().getSelectedItem().getContent();
				TilePane tile=(TilePane) scroll.getContent();
				
				StackPane sp = new StackPane();
				VBox vb = new VBox();
				ImageView iv=new ImageView(I);
				iv.setFitHeight(200.0);
				iv.setFitWidth(200.0);
				
				TextField tv1 = new TextField(caption);
				tv1.setEditable(false);
				
				TextField tv2 = new TextField(date.toString());
				tv2.setEditable(false);
				vb.getChildren().add(iv);
				vb.getChildren().add(tv1);
				vb.getChildren().add(tv2);
				sp.getChildren().add(vb);
				tile.getChildren().add(sp);
				Platform.runLater(() -> tilePane.requestFocus());
				
				
				
				
				}
				
				
			}
		}
		
	}
	
	@FXML
	private void uploadPicture(ActionEvent E) throws IOException{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		Stage stage=new Stage();
		File f=fileChooser.showOpenDialog(stage);
		BufferedImage B=ImageIO.read(f);
		Image I=convertBuffered(B);
		Photo P=new Photo(f);
		String Album=tabPane.getSelectionModel().getSelectedItem().getText();
		for(int i=0; i<user.getAlbums().size();i++){
			if(Album.equals(user.getAlbums().get(i).getTitle())){
				user.getAlbums().get(i).addPhoto(P);
				ScrollPane scroll=(ScrollPane) tabPane.getSelectionModel().getSelectedItem().getContent();
				TilePane tile=(TilePane) scroll.getContent();
				tile.setOnMouseClicked(e -> {
					
				});
				
				StackPane sp = new StackPane();
				VBox vb = new VBox();
				ImageView iv=new ImageView(I);
				iv.setFitHeight(200.0);
				iv.setFitWidth(200.0);
				iv.setOnMouseClicked(e -> {
					
				});
				
				TextField tv1 = new TextField(P.getCaption());
				tv1.setEditable(false);
				tv1.setOnMouseClicked(e -> {
					
				});
				
				TextField tv2 = new TextField(P.getDate()+"");
				tv2.setEditable(false);
				tv2.setOnMouseClicked(e -> {
					
				});
				vb.getChildren().add(iv);
				vb.getChildren().add(tv1);
				vb.getChildren().add(tv2);
				sp.getChildren().add(vb);
				tile.getChildren().add(sp);
				Platform.runLater(() -> tilePane.requestFocus());
				
			}
		}
		
		
	}
	
	@FXML
	private void deletePicture(ActionEvent E) throws IOException{
		
	}
	
	public Image convertBuffered(BufferedImage image){
		WritableImage wi=new WritableImage(image.getWidth(), image.getHeight());
		PixelWriter pw=wi.getPixelWriter();
		for(int k=0; k<image.getWidth();k++){
			for(int l=0; l<image.getHeight(); l++){
				pw.setArgb(k, l, image.getRGB(k, l));
			}
		}
		return wi;
	}
	
	public void start(Stage mainStage){ 
		ArrayList<Album> orig=this.user.getAlbums();
		titleLabel.setText(this.user.getUsername().toUpperCase().charAt(0) + this.user.getUsername().substring(1) +"'s "+"Photos");
		
		for(int i = 0; i < user.getAlbums().size(); i++){
			StackPane sp = new StackPane();
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
				lastClicked = (StackPane)tv1.getParent().getParent();
			});
			
			TextField tv2 = new TextField(user.getAlbums().get(i).getNumPhotos() + " Photos");
			tv2.setEditable(false);
			tv2.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
				lastClicked = (StackPane)tv1.getParent().getParent();
			});
			TextField tv3 = new TextField("Date Range");
			tv3.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
				lastClicked = (StackPane)tv1.getParent().getParent();
			});
			
			if(user.getAlbums().get(i).getNumPhotos() > 0){
				tv3 = new TextField(user.getAlbums().get(i).dateRange());
			}
			tv3.setEditable(false);
			Button button = new Button("OPEN");
			button.setOnAction(e -> {
				try {
					openAlbum(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			vb.getChildren().add(tv1);
			vb.getChildren().add(tv2);
			vb.getChildren().add(tv3);
			vb.getChildren().add(button);
			sp.getChildren().add(vb);
			tilePane.getChildren().add(sp);
			
			Platform.runLater(() -> tilePane.requestFocus());
			
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				  @Override public void changed(ObservableValue<? extends Tab> tab, Tab oldTab, Tab newTab) {
				    if(newTab.equals(albums)){
				    	createAlbum.setVisible(true);
				    	Add.setVisible(false);
				    	Delete.setVisible(false);
				    	Search.setVisible(false);
				    	Caption.setVisible(false);
				    	Tag.setVisible(false);
				    	Copy.setVisible(false);
				    	Move.setVisible(false);
				    	SlideShow.setVisible(false);
				    }
				    else{
				    	createAlbum.setVisible(false);
				    	deleteAlbum.setVisible(false);
				    	renameAlbum.setVisible(false);
				    	Add.setVisible(true);
				    	Delete.setVisible(true);
				    	Search.setVisible(true);
				    	Caption.setVisible(true);
				    	Tag.setVisible(true);
				    	Copy.setVisible(true);
				    	Move.setVisible(true);
				    	SlideShow.setVisible(true);
				    }
				  }
				});
		}

	

	}
}
