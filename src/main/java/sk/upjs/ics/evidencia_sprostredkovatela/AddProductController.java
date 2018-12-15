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
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.GroupDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;


public class AddProductController {

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


	public AddProductController() {
		productDao = DaoFactory.INSTANCE.getProductDao();
		groupDao = DaoFactory.INSTANCE.getGroupDao();
		productModel = new ProductFxModel();

	}
	
	

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
	}

	
	@FXML
	void saveButtonClicked(ActionEvent event) {
		Product product = productModel.getProduct();
		product.setGroupId(selectedGroup.get().getId());
		productDao.add(product);
		saveButton.getScene().getWindow().hide();
	}

	@FXML
	void disableButtonClicked(ActionEvent event) {

	}

	@FXML
	void initialize() {
    	List<Group> group = groupDao.getAll();
    	groupComboBox.setItems(FXCollections.observableList(group));
    	
    	
    	groupComboBox.getSelectionModel().selectedItemProperty()
    		.addListener(new ChangeListener<Group>() {
    					@Override
				public void changed(ObservableValue<? extends Group> observable, 
						Group oldValue, Group newValue) {
					if (newValue != null) {
						selectedGroup.set(newValue);
					}
				}
			});

		nameTextField.textProperty().bindBidirectional(productModel.nameProperty());
		codeTextField.textProperty().bindBidirectional(productModel.codeProperty());
		priceTextField.textProperty().bindBidirectional(productModel.priceProperty(), new NumberStringConverter());
		quantityTextField.textProperty().bindBidirectional(productModel.quantityProperty(),
				new NumberStringConverter());
	}
}
