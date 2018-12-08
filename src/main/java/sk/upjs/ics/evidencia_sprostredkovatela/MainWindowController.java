package sk.upjs.ics.evidencia_sprostredkovatela;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainWindowController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button customersListButton;

	@FXML
	void initialize() {
		
	}
	
	@FXML
	void openCustomersListButtonClicked(ActionEvent event) {
		CustomersListController controller = new CustomersListController();
		App.changeScene(controller, "CustomersList.fxml", "Zákazníci");
	}
}
