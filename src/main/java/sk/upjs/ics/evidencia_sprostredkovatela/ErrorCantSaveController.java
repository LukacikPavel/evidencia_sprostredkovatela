package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ErrorCantSaveController {

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
    	situationLabel.textProperty().set("Nepodarilo sa uložiť");
		requestLabel.textProperty().set("Prosím vyplňte povinné údaje");
    }
}
