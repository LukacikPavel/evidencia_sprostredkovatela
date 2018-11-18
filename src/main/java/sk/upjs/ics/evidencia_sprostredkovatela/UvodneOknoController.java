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

public class UvodneOknoController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button zakazniciButton;

	@FXML
	void initialize() {
		
	}
	
	@FXML
	void otvorZakazniciButton(ActionEvent event) {
		EvidenciaZakaznikaController evidenciaZakaznikaController = new EvidenciaZakaznikaController();
		showModalWindow(evidenciaZakaznikaController, "EvidenciaZakaznika.fxml", "Zákazníci");
	}
	
	private void showModalWindow(Object controller, String fxml, String title) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
			fxmlLoader.setController(controller);
			Parent rootPane;
			rootPane = fxmlLoader.load();
			Scene scene = new Scene(rootPane);

			Stage dialog = new Stage();
			dialog.setScene(scene);
			dialog.setTitle(title);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
