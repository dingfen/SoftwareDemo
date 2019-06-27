package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class MainController implements Initializable{
	@FXML private MediaView mv;
	private MediaPlayer mp;
	private Media media;
	@FXML Slider volumeSlider; 
	@FXML Button playbtn;
	@FXML Button previousbtn;
	@FXML Button nextbtn;
	@FXML Button fullScreenbtn;
	@FXML TreeView<String> treeview;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		String path = new File("src/media/video.mp4").getAbsolutePath();
		media = new Media(new File(path).toURI().toString());
		mp = new MediaPlayer(media);
		mv.setMediaPlayer(mp);
		
		DoubleProperty width = mv.fitWidthProperty();
		DoubleProperty height = mv.fitHeightProperty();
		width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
		height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));
		
		Image playImage = new Image(getClass().getResourceAsStream("../resource/play.png"));
		ImageView playImageView = new ImageView(playImage);
		playImageView.setFitHeight(40);
		playImageView.setFitWidth(40);
		playbtn.setGraphic(playImageView);
		
		volumeSlider.setValue(mp.getVolume()*100);
		volumeSlider.valueProperty().addListener(new InvalidationListener() {
			
			@Override
			public void invalidated(Observable arg0) {
				mp.setVolume(volumeSlider.getValue()/100);
			}
		});
		
		TreeItem<String> rootItem = new TreeItem<String>("My Music");
		rootItem.setExpanded(true);
		for(int i=0;i<6;i++) {
			TreeItem<String> item = new TreeItem<String>("Music" + i);
			rootItem.getChildren().add(item);
		}
		treeview.setRoot(rootItem);
	}
	
	public void play(ActionEvent event) {
		mp.play();
		mp.setRate(1);
	}
	
	public void pause(ActionEvent event) {
		mp.pause();
	}
	
	public void fast(ActionEvent event) {
		mp.setRate(2);
	}
	
	public void slow(ActionEvent event) {
		mp.setRate(0.5);
	}
	
	public void reload(ActionEvent event) {
		mp.seek(mp.getStartTime());
		mp.play();
	}
	
	public void start(ActionEvent event) {
		mp.seek(mp.getStartTime());
		mp.stop();
	}
	
	public void last(ActionEvent event) {
		mp.seek(mp.getTotalDuration());
		mp.stop();
	}
	
	public void fullScreen(ActionEvent event) {
		
	}
}
