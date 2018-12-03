package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;

public class AddCustomerController {

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
	
	private CustomerDao customerDao;
	private CustomerFxModel customerModel;

	public AddCustomerController() {
		customerDao = DaoFactory.INSTANCE.getCustomerDao();
		customerModel = new CustomerFxModel();
	}

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
	}
	
	@FXML
	void saveButtonClicked(ActionEvent event) {
		Customer customer = customerModel.getCustomer();
		customerDao.add(customer);
		saveButton.getScene().getWindow().hide();
	}
	
	@FXML
    void disableButtonClicked(ActionEvent event) {
		
    }

	@FXML
	void initialize() {
		nameTextField.textProperty().bindBidirectional(customerModel.nameProperty());
		surnameTextField.textProperty().bindBidirectional(customerModel.surnameProperty());
		emailTextField.textProperty().bindBidirectional(customerModel.emailProperty());
		numberTextField.textProperty().bindBidirectional(customerModel.numberProperty());
		moreDetailsTextField.textProperty().bindBidirectional(customerModel.moreDetailsProperty());
	}
}
