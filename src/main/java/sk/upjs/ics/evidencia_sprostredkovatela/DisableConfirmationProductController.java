package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;

public class DisableConfirmationProductController {

	@FXML
	private Button yesButton;

	@FXML
	private Button noButton;

	@FXML
	private Label textLabel;

	private long id;
	private ProductDao productDao;
	private boolean disabled = false;

	public DisableConfirmationProductController(long id) {
		this.id = id;
		productDao = DaoFactory.INSTANCE.getProductDao();
	}

	@FXML
	void noButtonClicked(ActionEvent event) {
		noButton.getScene().getWindow().hide();
	}

	@FXML
	void yesButtonClicked(ActionEvent event) {
		productDao.setNotValid(id);
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
