package dad.javafx.ventanamemoria.userinterface;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VentanaApp extends Application {
	
	private VentanaController controller;
	private String ruta = System.getProperty("user.home");
	
	private void initialConfig(File fichero) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fichero));
		String config = "";
		config += "background.red=255\n";
		config += "background.green=255\n";
		config += "background.blue=255\n";
		config += "size.width=320\n";
		config += "size.height=200\n";
		config += "location.x=350\n";
		config += "location.y=250";
		
		writer.write(config);
		writer.close();
	}
	
	@Override
	public void init() throws Exception {
		
		Properties prop = new Properties();
		File fConfig = new File(ruta + "\\.VentanaConMemoria\\ventana.config");
		
		if (!fConfig.getParentFile().exists() && !fConfig.getParentFile().mkdirs())
			throw new IllegalStateException("No se pudo crear la carpeta");
		
		if (fConfig.createNewFile()) {
			initialConfig(fConfig);
		}
		
		prop.load(new FileInputStream(fConfig));
		controller = new VentanaController(prop);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(controller.getView(), controller.getModel().getWidth(), controller.getModel().getHeight());
		primaryStage.setTitle("Ventana con memoria");
		primaryStage.setScene(scene);
		primaryStage.setX(controller.getModel().getLocationX());
		primaryStage.setY(controller.getModel().getLocationY());
		primaryStage.show();
		
		controller.crearListenersStage(primaryStage);
	}
	
	@Override
	public void stop() throws Exception {
		Properties prop = new Properties();
		File configFile = new File(ruta + "\\.VentanaConMemoria\\ventana.config");
		VentanaModel model = controller.getModel();
		
		if (!configFile.getParentFile().exists() && !configFile.getParentFile().mkdirs())
			throw new IllegalStateException("No se pudo crear la carpeta");
		
		configFile.createNewFile();
		
		prop.setProperty("background.red", String.valueOf(model.getRed()));
		prop.setProperty("background.green", String.valueOf(model.getGreen()));
		prop.setProperty("background.blue", String.valueOf(model.getBlue()));
		prop.setProperty("size.width", String.valueOf(model.getWidth()));
		prop.setProperty("size.height", String.valueOf(model.getHeight()));
		prop.setProperty("location.x", String.valueOf(model.getLocationX()));
		prop.setProperty("location.y", String.valueOf(model.getLocationY()));
		
		prop.store(new FileWriter(configFile), null);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
