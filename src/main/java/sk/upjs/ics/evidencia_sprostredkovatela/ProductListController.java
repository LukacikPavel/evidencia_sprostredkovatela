package sk.upjs.ics.evidencia_sprostredkovatela;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;;

public class ProductListController {

	private ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
	private ObservableList<Product> productsList;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Product> selectedProduct = new SimpleObjectProperty<>();
	private Parent parent;

	@FXML
	private TableView<Product> productTableView;

	@FXML
	private Button ulozButton;

	@FXML
	private Button addProductButton;

	@FXML
	private Button editProductButton;

	@FXML
	private TextField nameTextField;

	@FXML
	private ChoiceBox<Group> skupinaChoiceBox;

	@FXML
	private TextField tovarTextField;

	@FXML
	private Button selectProductButton;

	@FXML
	private Button backButton;

	@FXML
	private TextField codeTextFielD;

	public ProductListController(Parent parent) {
		this.parent = parent;
	}

	Product getSelectedProduct() {
		return selectedProduct.get();
	}

	@FXML
	void selectProductButtonClicked(ActionEvent event) {
		selectProductButton.getScene().getWindow().hide();
	}

	@FXML
	void addProductClicked(ActionEvent event) {
		AddProductController controller = new AddProductController();
		App.showModalWindow(controller, "AddProduct.fxml", "Pridanie Tovaru");
		productsList.setAll(productDao.getAllValid());

	}

	@FXML
	void editProductClicked(ActionEvent event) {
		EditProductController controller = new EditProductController(selectedProduct.get());
		App.showModalWindow(controller, "AddProduct.fxml", "Pridanie Tovaru");
		productsList.setAll(productDao.getAllValid());

	}

	@FXML
	void backButtonClicked(ActionEvent event) {
		selectedProduct.set(null);
		if (parent.idProperty().getValue().equals("add")) {
			backButton.getScene().getWindow().hide();
		} else {
			backButton.getScene().setRoot(parent);
		}
	}

	@FXML
	void initialize() {
		if (parent.idProperty().getValue().equals("main")) {
			selectProductButton.setVisible(false);
		}

		productsList = FXCollections.observableArrayList(productDao.getAllValid());

		TableColumn<Product, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		productTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());

		TableColumn<Product, String> codeCol = new TableColumn<>("Kód");
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
		productTableView.getColumns().add(codeCol);
		columnsVisibility.put("Kód", codeCol.visibleProperty());

		TableColumn<Product, String> menoCol = new TableColumn<>("Názov");
		menoCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		productTableView.getColumns().add(menoCol);
		columnsVisibility.put("Názov", menoCol.visibleProperty());

		TableColumn<Product, Double> priceCol = new TableColumn<>("Cena");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		productTableView.getColumns().add(priceCol);
		columnsVisibility.put("Cena", priceCol.visibleProperty());

		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);

		}
		productTableView.setContextMenu(contextMenu);

		productTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {

			@Override
			public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
				if (newValue == null) {
					editProductButton.setDisable(true);
				} else {
					editProductButton.setDisable(false);
				}
				selectedProduct.set(newValue);
			}
		});
		FilteredList<Product> filteredProducts = new FilteredList<>(productsList);
		filteredProducts.predicateProperty().bind(Bindings.createObjectBinding(
				() -> product -> product.getName().toLowerCase().contains(nameTextField.getText().toLowerCase()) 
				&& product.getCode().contains(codeTextFielD.getText()),
				nameTextField.textProperty(), codeTextFielD.textProperty()));
		productTableView.setItems(filteredProducts);

		productTableView.setRowFactory(tv -> {
			TableRow<Product> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					if (selectProductButton.isVisible()) {
						selectProductButtonClicked(new ActionEvent());
					}
				}
			});
			return row;
		});
	}
}
