package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
	
	private static Stage mainStage;
	
	public void start(Stage stage) throws Exception {
		MainWindowController controller = new MainWindowController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		fxmlLoader.setController(controller);
		Parent rootPane = fxmlLoader.load();
		Scene scene = new Scene(rootPane);
		stage.setTitle("Úvodne okno");
		stage.setScene(scene);
		stage.show();
	}
	
	

	public static void main( String[] args ){
    	launch(args);
    }
}
