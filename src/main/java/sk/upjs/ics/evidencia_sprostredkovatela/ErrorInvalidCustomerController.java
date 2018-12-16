package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ErrorInvalidCustomerController {

	@FXML
	private Label situationLabel;

	@FXML
	private Label requestLabel;

	@FXML
	private Button okButton;

	@FXML
	void okButtonClicked(ActionEvent event) {
		okButton.getScene().getWindow().hide();
	}

	@FXML
	void initialize() {
		situationLabel.textProperty().set("Neznámy zákazník");
		requestLabel.textProperty().set("Prosím vyberte zákazníka");
	}
}
