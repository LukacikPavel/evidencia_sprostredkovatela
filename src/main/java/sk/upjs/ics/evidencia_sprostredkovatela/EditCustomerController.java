package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;

public class EditCustomerController {

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
	private Button cancelButton;

	@FXML
	private Button disableButton;

	@FXML
	private Label nameLabel;

	@FXML
	private Label surnameLabel;

	@FXML
	private Label moreDetailsLabel;

	private CustomerDao customerDao;
	private CustomerFxModel customerModel;

	public EditCustomerController(Customer customer) {
		customerDao = DaoFactory.INSTANCE.getCustomerDao();
		this.customerModel = new CustomerFxModel(customer);
	}

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
	}

	@FXML
	void saveButtonClicked(ActionEvent event) {
		if (isAllFilled()) {
			Customer customer = customerModel.getCustomer();
			customerDao.save(customer);
			saveButton.getScene().getWindow().hide();
		} else {
			App.showModalWindow(new ErrorCantSaveController(), "ErrorCantSave.fxml", "Error");
		}
	}

	@FXML
	void disableButtonClicked(ActionEvent event) {
		DisableConfirmationController controller = new DisableConfirmationController(customerModel.getId());
		App.showModalWindow(controller, "Confirmation.fxml", "Určite?");
		if (controller.wasDisabled()) {
			disableButton.getScene().getWindow().hide();
		}
	}

	private boolean isAllFilled() {
		return nameTextField.getText() != null && surnameTextField.getText() != null
				&& moreDetailsTextField.getText() != null && !nameTextField.getText().isEmpty()
				&& !surnameTextField.getText().isEmpty() && !moreDetailsTextField.getText().isEmpty();
	}

	@FXML
	void initialize() {
		nameTextField.textProperty().bindBidirectional(customerModel.nameProperty());
		surnameTextField.textProperty().bindBidirectional(customerModel.surnameProperty());
		emailTextField.textProperty().bindBidirectional(customerModel.emailProperty());
		numberTextField.textProperty().bindBidirectional(customerModel.numberProperty());
		moreDetailsTextField.textProperty().bindBidirectional(customerModel.moreDetailsProperty());

		nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				nameLabel.setText("Nutné vyplniť");
			} else {
				nameLabel.setText("");
			}
		});
		surnameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				surnameLabel.setText("Nutné vyplniť");
			} else {
				surnameLabel.setText("");
			}
		});
		moreDetailsTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				moreDetailsLabel.setText("Nutné vyplniť");
			} else {
				moreDetailsLabel.setText("");
			}
		});

		disableButton.setVisible(true);
	}
}
