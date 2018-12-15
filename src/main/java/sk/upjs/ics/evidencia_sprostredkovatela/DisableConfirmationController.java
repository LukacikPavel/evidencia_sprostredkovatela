package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;

public class DisableConfirmationController {

    @FXML
    private Button yesButton;

    @FXML
    private Button noButton;
    
    @FXML
    private Label textLabel;

    private long id;
    private CustomerDao customerDao;
    private boolean disabled = false;
    
    public DisableConfirmationController(long id) {
		this.id = id;
		customerDao = DaoFactory.INSTANCE.getCustomerDao();
	}
    
    @FXML
    void noButtonClicked(ActionEvent event) {
    	noButton.getScene().getWindow().hide();
    }

    @FXML
    void yesButtonClicked(ActionEvent event) {
    	customerDao.disable(id);
    	disabled = true;
    	yesButton.getScene().getWindow().hide();
    }
    
    public boolean wasDisabled() {
    	return disabled;
    }

    @FXML
    void initialize() {

    }
}

