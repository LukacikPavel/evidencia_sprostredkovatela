package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application {
	
	public void start(Stage stage) throws Exception {
		UvodneOknoController controller = new UvodneOknoController();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("UvodneOkno.fxml"));
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
