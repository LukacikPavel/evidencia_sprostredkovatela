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
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseDragEvent;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;;

public class ProductListController {

	private ProductDao productDao = DaoFactory.INSTANCE.getProductDao();
	private ObservableList<Product> productsList;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Product> selectedProduct = new SimpleObjectProperty<>();
	
    @FXML
    private TableView<Product> productTableView;

    @FXML
    private Button ulozButton;

    @FXML
    private ChoiceBox<Group> skupinaChoiceBox;

    @FXML
    private TextField tovarTextField;

    @FXML
    void vyber(MouseDragEvent event) {

    }

	@FXML
	void initialize() {
		productsList = FXCollections.observableArrayList(productDao.getAll());
				
		TableColumn<Product, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		productTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());

		TableColumn<Product, String> menoCol = new TableColumn<>("Názov");
		menoCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		productTableView.getColumns().add(menoCol);
		columnsVisibility.put("name", menoCol.visibleProperty());
		
		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);

}
		
		productTableView.setContextMenu(contextMenu);
		productTableView.setItems(productsList);


		//		productTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {

//			@Override
//			public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {
//				if (newValue == null) {
//					editCustomerButton.setDisable(true);
//				} else {
//					editCustomerButton.setDisable(false);
//				}
//				selectedCustomer.set(newValue);
//			}

		}
}
	

