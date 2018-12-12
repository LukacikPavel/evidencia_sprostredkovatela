package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;

public class EditProductController {

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField codeTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private TextField numberTextField;

	@FXML
	private TextField quantityTextField;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button disableButton;

	private ProductDao productDao;
	private ProductFxModel productModel;

	public EditProductController(Product product) {
		productDao = DaoFactory.INSTANCE.getProductDao();
		this.productModel = new ProductFxModel(product);

	}
	

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
	}

	@FXML
	void saveButtonClicked(ActionEvent event) {
		Product product = productModel.getProduct();
		productDao.save(product);
		saveButton.getScene().getWindow().hide();
	}

	@FXML
    void disableButtonClicked(ActionEvent event) {
		DisableConfirmationController controller = new DisableConfirmationController(productModel.getId());
		App.showModalWindow(controller, "Confirmation.fxml", "Určite?");
		if (controller.wasDisabled()) {
			disableButton.getScene().getWindow().hide();
		}
		
	}		
		
		@FXML
		void initialize() {
			nameTextField.textProperty().bindBidirectional(productModel.nameProperty());
			codeTextField.textProperty().bindBidirectional(productModel.codeProperty());
			priceTextField.textProperty().bindBidirectional(productModel.priceProperty(), new NumberStringConverter());
			numberTextField.textProperty().bindBidirectional(productModel.groupIdProperty(), new NumberStringConverter());
			quantityTextField.textProperty().bindBidirectional(productModel.quantityProperty(),
					new NumberStringConverter());

    }


}

//


