package sk.upjs.ics.evidencia_sprostredkovatela;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddCustomerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField numberTextField;

    @FXML
    private TextField moreDetailsTextField;

    @FXML
    private Button saveButton;

    @FXML
    void saveButtonClicked(ActionEvent event) {
    	
    }

    @FXML
    void initialize() {
    	
    }
}
