package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ErrorCantSaveController {

    @FXML
    private Button OkButton;

    @FXML
    void OkButtonClicked(ActionEvent event) {
    	OkButton.getScene().getWindow().hide();
    }

    @FXML
    void initialize() {
    }
}
