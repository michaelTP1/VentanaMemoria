package dad.javafx.ventanamemoria.userinterface;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VentanaController implements Initializable {
	
	// view
	@FXML
	private GridPane view;
	
	@FXML
	private Slider sliderRed;
	
	@FXML
	private Slider sliderGreen;
	
	@FXML
	private Slider sliderBlue;
	
	private Stage stage;
	
	// model
	private VentanaModel model = new VentanaModel();
	
	public VentanaController(Properties properties) throws IOException {
		model.setRed(Integer.valueOf((String) properties.get("background.red")));
		model.setGreen(Integer.valueOf((String) properties.get("background.green")));
		model.setBlue(Integer.valueOf((String) properties.get("background.blue")));
		model.setWidth(Double.valueOf((String) properties.get("size.width")));
		model.setHeight(Double.valueOf((String) properties.get("size.height")));
		model.setLocationX(Double.valueOf((String) properties.get("location.x")));
		model.setLocationY(Double.valueOf((String) properties.get("location.y")));
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/View.fxml"));
		loader.setController(this);
		loader.load();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		Bindings.bindBidirectional(sliderRed.valueProperty(), model.redProperty());
		Bindings.bindBidirectional(sliderGreen.valueProperty(), model.greenProperty());
		Bindings.bindBidirectional(sliderBlue.valueProperty(), model.blueProperty());
		
		sliderRed.valueProperty().addListener(e -> onSlideValueChange(e));
		sliderGreen.valueProperty().addListener(e -> onSlideValueChange(e));
		sliderBlue.valueProperty().addListener(e -> onSlideValueChange(e));
		
		view.setStyle("-fx-background-color: rgb(" + model.getRed() + "," + model.getGreen() + "," + model.getBlue() + ");");
	}
		
	private void onSlideValueChange(Observable e) {
		view.setStyle("-fx-background-color: rgb(" + model.getRed() + "," + model.getGreen() + "," + model.getBlue() + ");");
	}
	
	public void crearListenersStage(Stage primaryStage) {
		
		stage = primaryStage;
		
		stage.widthProperty().addListener(e -> onStageSizeChange(e));
		stage.heightProperty().addListener(e -> onStageSizeChange(e));
		
		stage.xProperty().addListener(e -> onStagePositionChange(e));
		stage.yProperty().addListener(e -> onStagePositionChange(e));
	}

	private void onStageSizeChange(Observable e) {
		model.setWidth(stage.getWidth());
		model.setHeight(stage.getHeight());
	}
	
	private void onStagePositionChange(Observable e) {
		model.setLocationX(stage.getX());
		model.setLocationY(stage.getY());
	}
	
	public GridPane getView() {
		return view;
	}

	public void setView(GridPane view) {
		this.view = view;
	}

	public VentanaModel getModel() {
		return model;
	}

	public void setModel(VentanaModel model) {
		this.model = model;
	}	

}
