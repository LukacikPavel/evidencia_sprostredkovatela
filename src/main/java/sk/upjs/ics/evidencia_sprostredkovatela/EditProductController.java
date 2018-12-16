package sk.upjs.ics.evidencia_sprostredkovatela;

import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.GroupDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;

public class EditProductController {
	
	@FXML
	private Label groupLabel;

	@FXML
	private Label nameLabel;

	@FXML
	private Label codeLabel;

	@FXML
	private Label priceLabel;

	@FXML
	private Label quantityLabel;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField codeTextField;

	@FXML
	private TextField priceTextField;

	@FXML
	private TextField quantityTextField;

	@FXML
	private Button saveButton;

	@FXML
	private Button cancelButton;

	@FXML
	private Button disableButton;

	@FXML
	private ComboBox<Group> groupComboBox;

	private ProductDao productDao;
	private GroupDao groupDao;
	private ProductFxModel productModel;
	private ObjectProperty<Group> selectedGroup = new SimpleObjectProperty<>();

	public EditProductController(Product product) {
		productDao = DaoFactory.INSTANCE.getProductDao();
		groupDao = DaoFactory.INSTANCE.getGroupDao();
		this.productModel = new ProductFxModel(product);

	}

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
	}

	@FXML
	void saveButtonClicked(ActionEvent event) {
		if (isAllFilled()) {
			Product product = productModel.getProduct();
			product.setGroupId(selectedGroup.get().getId());
			productDao.save(product);
			saveButton.getScene().getWindow().hide();
		} else {
			App.showModalWindow(new ErrorCantSaveController(), "Error.fxml", "Error");
		}
	}

	@FXML
	void disableButtonClicked(ActionEvent event) {
		DisableConfirmationProductController controller = new DisableConfirmationProductController(productModel.getId());
		App.showModalWindow(controller, "Confirmation.fxml", "Určite?");
		if (controller.wasDisabled()) {
			disableButton.getScene().getWindow().hide();
		}
	}

	@FXML
	void initialize() {
		List<Group> group = groupDao.getAll();
		groupComboBox.setItems(FXCollections.observableList(group));

		groupComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Group>() {
			@Override
			public void changed(ObservableValue<? extends Group> observable, Group oldValue, Group newValue) {
				if (newValue != null) {
					selectedGroup.set(newValue);
				}
			}
		});
		for (int i = 0; i < group.size(); i++) {
			if (group.get(i).getId().equals(productModel.getId())) {
				groupComboBox.getSelectionModel().select(i);
				break;
			}
		}

		nameTextField.textProperty().bindBidirectional(productModel.nameProperty());
		codeTextField.textProperty().bindBidirectional(productModel.codeProperty());
		priceTextField.textProperty().bindBidirectional(productModel.priceProperty(), new NumberStringConverter());
		quantityTextField.textProperty().bindBidirectional(productModel.quantityProperty(),
				new NumberStringConverter());
		
		nameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				nameLabel.setText("Nutné vyplniť");
			} else {
				nameLabel.setText("");
			}
		});

		codeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				codeLabel.setText("Nutné vyplniť");
			} else {
				codeLabel.setText("");
			}
		});

		priceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				priceLabel.setText("Nutné vyplniť");
			} else {
				priceTextField.setText(newValue.replaceAll("[^0-9.]", ""));
				priceLabel.setText("");
			}
		});

		quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				quantityLabel.setText("Nutné vyplniť");
			} else {
				quantityTextField.setText(newValue.replaceAll("[^0-9.]", ""));
				quantityLabel.setText("");
			}
		});
		
		disableButton.setVisible(true);
	}

	private boolean isAllFilled() {
		return nameTextField.getText() != null && codeTextField.getText() != null && priceTextField.getText() != null
				&& quantityTextField.getText() != null && groupComboBox.getValue() != null
				&& !nameTextField.getText().isEmpty() && !codeTextField.getText().isEmpty()
				&& !priceTextField.getText().isEmpty() && !quantityTextField.getText().isEmpty();
	}
}