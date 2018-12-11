package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;

public class AddProductController {

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

	@FXML
	void cancelButtonClicked(ActionEvent event) {

	}

	@FXML
	void disableButtonClicked(ActionEvent event) {

	}

	@FXML
	void saveButtonClicked(ActionEvent event) {

	}

	private ProductDao productDao;
	private ProductFxModel productModel;

	public AddProductController(ActionEvent event) {
		productDao = DaoFactory.INSTANCE.getProductDao();
		productModel = new ProductFxModel();

	}
	
	

//	@FXML
//	void cancelButtonClicked(ActionEvent event) {
//		cancelButton.getScene().getWindow().hide();
//	}
//
//	@FXML
//	void saveButtonClicked(ActionEvent event) {
//		Product product = productModel.getProduct();
//		ProductDao.add(product);
//		saveButton.getScene().getWindow().hide();
//	}
//
//	@FXML
//	void disableButtonClicked(ActionEvent event) {
//
//	}

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
