package view;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;


import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import java.util.Optional;

import application.Album;
import application.Photo;
import application.PhotoLibrary;
import application.Tag;
import application.User;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class UserSystemController {
	private User user; 
	private PhotoLibrary library;
	private StackPane lastClicked;
	
	@FXML Button createAlbum;
	@FXML Button deleteAlbum;
	@FXML Button Display;
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
	@FXML VBox VContainer;
	@FXML ScrollPane S;
	@FXML Button left;
	@FXML Button right;
	@FXML ImageView slideImage;
	ImageView selectImage;
	int count = 0;
	Album a;
	public String name;
	public String value;
	
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
	
		StackPane sp = new StackPane();
		sp.setPrefSize(200, 150);
		VBox vb = new VBox();
		vb.setPrefSize(200, 150);
		vb.setStyle("-fx-background-color: white;");
		tilePane.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(false);
			renameAlbum.setVisible(false);
		});
		TextField tv1 = new TextField();
		tv1.setPrefSize(200, 50);
		tv1.setText(albumTitle);
		tv1.setAlignment(Pos.CENTER);
		tv1.setFont(Font.font ("System", 24));
		tv1.setEditable(false);
		tv1.setStyle("-fx-background-color: transparent;");
		tv1.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(true);
			renameAlbum.setVisible(true);
			lastClicked = (StackPane)tv1.getParent().getParent();
		});
		
		TextField tv2 = new TextField("0 Photos");
		tv2.setAlignment(Pos.CENTER);
		tv2.setPrefSize(200, 50);
		tv2.setEditable(false);
		tv2.setStyle("-fx-background-color: transparent;");
		tv2.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(true);
			renameAlbum.setVisible(true);
			lastClicked = (StackPane)tv1.getParent().getParent();
		});
		TextField tv3 = new TextField("Date Range");
		tv3.setPrefSize(200, 50);
		tv3.setOnMouseClicked(e -> {
			deleteAlbum.setVisible(true);
			renameAlbum.setVisible(true);
			lastClicked = (StackPane)tv1.getParent().getParent();
		});
		
		tv3.setEditable(false);
		tv3.setAlignment(Pos.CENTER);
		tv3.setStyle("-fx-background-color: transparent;");
		Button button = new Button("OPEN");
		button.setPrefSize(200, 30);
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
		PhotoLibrary.writeApp(this.library);
		
		Platform.runLater(() -> sp.requestFocus());
		Platform.runLater(() -> vb.requestFocus());
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
			    	Display.setVisible(false);
			    	SlideShow.setVisible(false);
			    }
			    else{
			    	createAlbum.setVisible(false);
			    	deleteAlbum.setVisible(false);
			    	renameAlbum.setVisible(false);
			    	Add.setVisible(true);
			    	Delete.setVisible(false);
			    	Search.setVisible(true);
			    	Caption.setVisible(false);
			    	Tag.setVisible(false);
			    	Copy.setVisible(false);
			    	Move.setVisible(false);
			    	SlideShow.setVisible(true);
			    	Display.setVisible(false);
			    }
			  }
			});
		
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
			    	Display.setVisible(false);
			    }
			    else{
			    	createAlbum.setVisible(false);
			    	deleteAlbum.setVisible(false);
			    	renameAlbum.setVisible(false);
			    	Add.setVisible(true);
			    	Delete.setVisible(false);
			    	Search.setVisible(true);
			    	Caption.setVisible(false);
			    	Tag.setVisible(false);
			    	Copy.setVisible(false);
			    	Move.setVisible(false);
			    	SlideShow.setVisible(true);
			    	Display.setVisible(false);
			    }
			  }
			});
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
		alert.setHeaderText("Album " + title + " will be deleted.");
		alert.setContentText("Are you sure you want to delete this album?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    user.deleteAlbumByIndex(item);
		    deleteAlbum.setVisible(false);
			renameAlbum.setVisible(false);
		    PhotoLibrary.writeApp(this.library);
		    tilePane.getChildren().remove(item);
		    
		    for(int i = 0; i < tabPane.getTabs().size(); i++){
		    	if(tabPane.getTabs().get(i).getText().equals(title)){
		    		tabPane.getTabs().remove(i);
		    	}
		    }
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
	
		root.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
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
		VBox.setVgrow(Tile, Priority.ALWAYS);

		Tile.setStyle("-fx-background-color: teal;");
		for(int i=0;i<tabPane.getTabs().size();i++){
			if(AlbumName.equals(tabPane.getTabs().get(i).getText())){
				tabPane.getTabs().remove(i);
			}
		}

		
		tabPane.getTabs().add(tab);
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		selectionModel.select(tab);
		for(int i=0; i<this.user.getAlbums().size(); i++){
			if(AlbumName.equals(this.user.getAlbums().get(i).getTitle())){
				for(int j=0; j<this.user.getAlbums().get(i).getPhotos().size(); j++){
				
				Photo P=this.user.getAlbums().get(i).getPhotos().get(j);

				ArrayList<Tag> tag=P.getTags();
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
				iv.setOnMouseClicked(f -> {
					selectImage=iv;
					Delete.setVisible(true);
			    	Caption.setVisible(true);
			    	Tag.setVisible(true);
			    	Copy.setVisible(true);
			    	Move.setVisible(true);
			    	Display.setVisible(true);
				});
				
				TextField tv1 = new TextField(caption);
				tv1.setEditable(false);
				
				TextField tv2 = new TextField(P.getDateString());
				tv2.setEditable(false);
				
				/*ArrayList<String> names=new ArrayList<String>();
				ArrayList<String> values=new ArrayList<String>();
				
				for(int k=0; k<P.getTags().size();k++){
					names.add(P.getTags().get(k).getTagName());
					values.add(P.getTags().get(k).getTagValue());
				}
				TableColumn c1=new TableColumn();
				TableColumn c2=new TableColumn();
				c1.set*/
				
				
				vb.getChildren().add(iv);
				vb.getChildren().add(tv1);
				vb.getChildren().add(tv2);
				//vb.getChildren().add(tv3);
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
				PhotoLibrary.writeApp(this.library);
				
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
					selectImage=iv;
					Delete.setVisible(true);
			    	Caption.setVisible(true);
			    	Tag.setVisible(true);
			    	Copy.setVisible(true);
			    	Move.setVisible(true);
			    	Display.setVisible(true);
				});
				
				TextField tv1 = new TextField(P.getCaption());
				tv1.setEditable(false);
				tv1.setOnMouseClicked(e -> {
					
				});
				TextField tv2 = new TextField(P.getDateString());

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
		Platform.runLater(() -> tabPane.requestFocus());
		
	}
	
	@FXML
	private void deletePicture(ActionEvent E) throws IOException{
		int aLocation=0;
		for(int i=0;i<user.getAlbums().size();i++){
			String AlbumName=tabPane.getSelectionModel().getSelectedItem().getText();
			if(AlbumName.equals(user.getAlbums().get(i).getTitle())){
				aLocation=i;
			}
		}
		
		VBox dBox=(VBox)selectImage.getParent();
		StackPane dStack=(StackPane)dBox.getParent();
		TilePane dTile=(TilePane)dStack.getParent();
		int pLocation=0;
		for(int i=0; i<dTile.getChildren().size(); i++){
			if(dStack.equals(dTile.getChildren().get(i))){
				pLocation=i;
				dTile.getChildren().remove(i);
			}
		}
				
				user.getAlbums().get(aLocation).getPhotos().remove(pLocation);
	    		for(int j=0; j<this.library.getUsers().size();j++){
	    			if(user.getUsername().equals(this.library.getUsers().get(j).getUsername())){
	    				this.library.getUsers().remove(j);
	    				this.library.getUsers().add(user);
	    				
	    			}
	    		}
	    		
	    		PhotoLibrary.writeApp(this.library);
	    		
	    	}
	@FXML
	private void captionPhoto(ActionEvent E) throws IOException{
		String Caption="";
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Enter Caption");
		dialog.setHeaderText("Please Enter Caption for the Photo");
		Optional<String> result = dialog.showAndWait();
		
		while(dialog.getResult().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error Dialog");
    		alert.setHeaderText("Incorrect Entry");
    		alert.setContentText("Must enter a caption");
    		alert.showAndWait();
    		dialog.setTitle("Enter Caption");
    		dialog.setHeaderText("Please Enter Caption for the Photo");
    		result = dialog.showAndWait();
		}
        if (result.isPresent()) {
            Caption = result.get();
        }
		
		Album dAlbum=null;
		int aLocation=0;
		for(int i=0;i<user.getAlbums().size();i++){
			String AlbumName=tabPane.getSelectionModel().getSelectedItem().getText();
			if(AlbumName.equals(user.getAlbums().get(i).getTitle())){
				aLocation=i;
				dAlbum=user.getAlbums().get(i);
			}
		}
		
		VBox dBox=(VBox)selectImage.getParent();
		StackPane dStack=(StackPane)dBox.getParent();
		TilePane dTile=(TilePane)dStack.getParent();
		int pLocation=0;
		for(int i=0; i<dTile.getChildren().size(); i++){
			if(dStack.equals(dTile.getChildren().get(i))){
				TextField caption=(TextField)dBox.getChildren().get(1);
				caption.setText(Caption);
				pLocation=i;
			}
		}
				
				user.getAlbums().get(aLocation).getPhotos().get(pLocation).setCaption(Caption);;
	    		for(int j=0; j<this.library.getUsers().size();j++){
	    			if(user.getUsername().equals(this.library.getUsers().get(j).getUsername())){
	    				this.library.getUsers().remove(j);
	    				this.library.getUsers().add(user);
	    				
	    			}
	    		}
	    		
	    		PhotoLibrary.writeApp(this.library);
		
	}
	
	@FXML 
	private void tagPhoto(ActionEvent E){
		Album dAlbum=null;
		int aLocation=0;
		for(int i=0;i<user.getAlbums().size();i++){
			String AlbumName=tabPane.getSelectionModel().getSelectedItem().getText();
			if(AlbumName.equals(user.getAlbums().get(i).getTitle())){
				aLocation=i;
				dAlbum=user.getAlbums().get(i);
			}
		}
		
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Tag Creation");
		dialog.setHeaderText("Set Name and Value for Tag");
		
		ButtonType loginButtonType = new ButtonType("Enter", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField namefield = new TextField();
		namefield.setPromptText("Name");
		PasswordField valuefield = new PasswordField();
		valuefield.setPromptText("Value");

		grid.add(new Label("Name:"), 0, 0);
		grid.add(namefield, 1, 0);
		grid.add(new Label("Value:"), 0, 1);
		grid.add(valuefield, 1, 1);
		
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		dialog.getDialogPane().setContent(grid);

		
		Platform.runLater(() -> namefield.requestFocus());

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		    	if(!namefield.getText().isEmpty() && !valuefield.getText().isEmpty()){
		    		name=namefield.getText();
		    		value=valuefield.getText();
		    		return new Pair<>(namefield.getText(), valuefield.getText());
		    	}
		    	else{
		    		Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Dialog");
		    		alert.setHeaderText("Incorrect Entry");
		    		alert.setContentText("Must enter both a name and a value");
		    		
		    		alert.showAndWait();
		    		return null;
		    	}
		    }
			return null;
		} );
	
		Optional<Pair<String, String>> result = dialog.showAndWait();
		if(name==null || value==null)
			return;
		
		Tag newTag=new Tag(name, value);
		boolean tagAdded=false;
		VBox dBox=(VBox)selectImage.getParent();
		StackPane dStack=(StackPane)dBox.getParent();
		TilePane dTile=(TilePane)dStack.getParent();
		int pLocation=0;
		for(int i=0; i<dTile.getChildren().size(); i++){
			if(dStack.equals(dTile.getChildren().get(i))){
				tagAdded=user.getAlbums().get(aLocation).getPhotos().get(i).addTag(newTag);
				if(tagAdded){
					
				}
				else{
					Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Dialog");
		    		alert.setHeaderText("Incorrect Entry");
		    		alert.setContentText("Tag Name Value already exists for this photo");
		    		
		    		alert.showAndWait();
		    		return;
					
				}
			}
		}
		
		if(tagAdded){
			for(int j=0; j<this.library.getUsers().size();j++){
    			if(user.getUsername().equals(this.library.getUsers().get(j).getUsername())){
    				this.library.getUsers().remove(j);
    				this.library.getUsers().add(user);
    				
    			}
    		}
    		
    		try {
				PhotoLibrary.writeApp(this.library);
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		}

		
	}
	
	@FXML
	private void transfer(ActionEvent E) throws IOException{
		Button b=(Button)E.getSource();
		String copyMove=b.getText();
		if(copyMove.equals("Copy")){
			String albumTitle = "";
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Copy Picture");
			dialog.setHeaderText("Enter Album to Copy Picture into");
			Optional<String> result = dialog.showAndWait();
			
			while(dialog.getResult().isEmpty()){
				Alert alert = new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Dialog");
	    		alert.setHeaderText("Incorrect Entry");
	    		alert.setContentText("Must enter an album");
	    		alert.showAndWait();
	    		dialog.setTitle("Copy Picture");
	    		dialog.setHeaderText("Enter Album to Copy Picture into");
	    		result = dialog.showAndWait();
			}
	        if (result.isPresent()) {
	            albumTitle = result.get();
	        }
	        if(!albumTitle.equals("")){
				for(int i = 0; i < user.getAlbums().size(); i++){
					if(albumTitle.equals(user.getAlbums().get(i).getTitle())){
						int aLocation=0;
						for(int k=0;k<user.getAlbums().size();k++){
							String AlbumName=tabPane.getSelectionModel().getSelectedItem().getText();
							if(AlbumName.equals(user.getAlbums().get(k).getTitle())){
								aLocation=k;
							}
						}
						
						VBox dBox=(VBox)selectImage.getParent();
						StackPane dStack=(StackPane)dBox.getParent();
						TilePane dTile=(TilePane)dStack.getParent();
						int pLocation=0;
						for(int j=0; j<dTile.getChildren().size(); j++){
							if(dStack.equals(dTile.getChildren().get(j))){
								pLocation=j;
							}
						}
								user.getAlbums().get(i).addPhoto(user.getAlbums().get(aLocation).getPhotos().get(pLocation));
								for(int j=0; j<this.library.getUsers().size();j++){
					    			if(user.getUsername().equals(this.library.getUsers().get(j).getUsername())){
					    				this.library.getUsers().remove(j);
					    				this.library.getUsers().add(user);
					    				
					    			}
					    		}
					    		
					    		PhotoLibrary.writeApp(this.library);
						}
	        
				}
	        }
		}
	        else{
	        	String albumTitle = "";
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Move Picture");
				dialog.setHeaderText("Enter Album to Move Picture into");
				Optional<String> result = dialog.showAndWait();
				
				while(dialog.getResult().isEmpty()){
					Alert alert = new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Dialog");
		    		alert.setHeaderText("Incorrect Entry");
		    		alert.setContentText("Must enter an album");
		    		alert.showAndWait();
		    		dialog.setTitle("Move Picture");
		    		dialog.setHeaderText("Enter Album to Move Picture into");
		    		result = dialog.showAndWait();
				}
		        if (result.isPresent()) {
		            albumTitle = result.get();
		        }
		        if(!albumTitle.equals("")){
					for(int i = 0; i < user.getAlbums().size(); i++){
						if(albumTitle.equals(user.getAlbums().get(i).getTitle())){
							int aLocation=0;
							for(int k=0;k<user.getAlbums().size();k++){
								String AlbumName=tabPane.getSelectionModel().getSelectedItem().getText();
								if(AlbumName.equals(user.getAlbums().get(k).getTitle())){
									aLocation=k;
								}
							}
							
							VBox dBox=(VBox)selectImage.getParent();
							StackPane dStack=(StackPane)dBox.getParent();
							TilePane dTile=(TilePane)dStack.getParent();
							int pLocation=0;
							for(int j=0; j<dTile.getChildren().size(); j++){
								if(dStack.equals(dTile.getChildren().get(j))){
									dTile.getChildren().remove(j);
									pLocation=j;
								}
							}
									user.getAlbums().get(i).addPhoto(user.getAlbums().get(aLocation).getPhotos().get(pLocation));
									user.getAlbums().get(aLocation).getPhotos().remove(pLocation);
									for(int j=0; j<this.library.getUsers().size();j++){
						    			if(user.getUsername().equals(this.library.getUsers().get(j).getUsername())){
						    				this.library.getUsers().remove(j);
						    				this.library.getUsers().add(user);
						    				
						    			}
						    		}
						    		
						    		PhotoLibrary.writeApp(this.library);
							}
		        
					}
		        }
	        	
			
		}
	
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
	
	@FXML
	private void slideShow(ActionEvent E) throws IOException{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("photoview.fxml"));
		AnchorPane root = (AnchorPane)fxmlLoader.load();
		Scene userScene=new Scene(root, 810, 620);
		Stage userStage=(Stage) ((Node) E.getSource()).getScene().getWindow();
		userStage.hide();
	    userStage.setTitle(user.getUsername());
	    userStage.setResizable(false);
		userStage.setScene(userScene);
		userStage.show();
        
        int aLocation=0;
		for(int i=0;i<user.getAlbums().size();i++){
			String AlbumName=tabPane.getSelectionModel().getSelectedItem().getText();
			if(AlbumName.equals(user.getAlbums().get(i).getTitle())){
				aLocation=i;
			}
		}
        
		a = user.getAlbums().get(aLocation);
		System.out.print(aLocation);
		File file = (a.getPhotos().get(0).getImage());
		Image I = new Image(new FileInputStream(file));
		
		slideImage = new ImageView(I);
	
        Platform.runLater(() -> tilePane.requestFocus());
	}
	
	
	@FXML
	private void goLeft(ActionEvent E) throws IOException{
		if(!(count == 0) && count < a.getNumPhotos() - 1){
    		BufferedImage br = null;
			try {
				br = ImageIO.read(a.getPhotos().get(count - 1).getImage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		Image im =convertBuffered(br);
    		slideImage.setImage(im);
    		count--;
    	}
	}
	
	@FXML
	private void goRight(ActionEvent E) throws IOException{
		if(!(count == a.getNumPhotos())){
    		BufferedImage br = null;
			try {
				br = ImageIO.read(a.getPhotos().get(count + 1).getImage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		Image im =convertBuffered(br);
    		slideImage.setImage(im);
    		count++;
    	}
	}
	
	public void start(Stage mainStage){ 
		ArrayList<Album> orig=this.user.getAlbums();
		titleLabel.setText(this.user.getUsername().toUpperCase().charAt(0) + this.user.getUsername().substring(1) +"'s "+"Photos");
		VBox.setVgrow(tilePane, Priority.ALWAYS);
		for(int i = 0; i < user.getAlbums().size(); i++){
			StackPane sp = new StackPane();
			sp.setPrefSize(200, 150);
			VBox vb = new VBox();
			vb.setPrefSize(200, 150);
			vb.setStyle("-fx-background-color: white;");
			tilePane.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(false);
				renameAlbum.setVisible(false);
			});
			TextField tv1 = new TextField();
			tv1.setPrefSize(200, 50);
			tv1.setText(user.getAlbums().get(i).getTitle());
			tv1.setAlignment(Pos.CENTER);
			tv1.setFont(Font.font ("System", 24));
			tv1.setEditable(false);
			tv1.setStyle("-fx-background-color: transparent;");
			tv1.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
				lastClicked = (StackPane)tv1.getParent().getParent();
			});
			
			TextField tv2 = new TextField(user.getAlbums().get(i).getNumPhotos() + " Photos");
			tv2.setAlignment(Pos.CENTER);
			tv2.setPrefSize(200, 50);
			tv2.setEditable(false);
			tv2.setStyle("-fx-background-color: transparent;");
			tv2.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
				lastClicked = (StackPane)tv1.getParent().getParent();
			});
			TextField tv3 = new TextField("Date Range");
			tv3.setPrefSize(200, 50);
			tv3.setOnMouseClicked(e -> {
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
				lastClicked = (StackPane)tv1.getParent().getParent();
			});
			
			if(user.getAlbums().get(i).getNumPhotos() > 0){
				tv3 = new TextField(user.getAlbums().get(i).dateRange());
			}
			tv3.setEditable(false);
			tv3.setAlignment(Pos.CENTER);
			tv3.setStyle("-fx-background-color: transparent;");
			Button button = new Button("OPEN");
			button.setPrefSize(200, 30);
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
			
			Platform.runLater(() -> sp.requestFocus());
			Platform.runLater(() -> vb.requestFocus());
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
				    	Display.setVisible(false);
				    }
				    else{
				    	createAlbum.setVisible(false);
				    	deleteAlbum.setVisible(false);
				    	renameAlbum.setVisible(false);
				    	Add.setVisible(true);
				    	Delete.setVisible(false);
				    	Search.setVisible(true);
				    	Caption.setVisible(false);
				    	Tag.setVisible(false);
				    	Copy.setVisible(false);
				    	Move.setVisible(false);
				    	SlideShow.setVisible(true);
				    	Display.setVisible(false);
				    }
				  }
				});
		}
	}
}
