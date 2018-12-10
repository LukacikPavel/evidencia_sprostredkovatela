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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;

public class CustomersListController {

	private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
	private ObservableList<Customer> customersList;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField surnameTextField;

	@FXML
	private TextField moreDetailsTextField;

	@FXML
	private Button editCustomerButton;

	@FXML
	private Button addCustomerButton;

	@FXML
	private Button selectCustomerButton;

	@FXML
	private TableView<Customer> customersTableView;

	@FXML
	void initialize() {
		customersList = FXCollections.observableArrayList(customerDao.getAllEnabled());

		TableColumn<Customer, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		customersTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());

		TableColumn<Customer, String> menoCol = new TableColumn<>("Meno");
		menoCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		customersTableView.getColumns().add(menoCol);
		columnsVisibility.put("Meno", menoCol.visibleProperty());

		TableColumn<Customer, String> priezviskoCol = new TableColumn<>("Priezvisko");
		priezviskoCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
		customersTableView.getColumns().add(priezviskoCol);
		columnsVisibility.put("Priezvisko", priezviskoCol.visibleProperty());

		TableColumn<Customer, String> emailCol = new TableColumn<>("E-mail");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		customersTableView.getColumns().add(emailCol);
		columnsVisibility.put("E-mail", emailCol.visibleProperty());

		TableColumn<Customer, String> numberCol = new TableColumn<>("Tel. číslo");
		numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
		customersTableView.getColumns().add(numberCol);
		columnsVisibility.put("Tel. číslo", numberCol.visibleProperty());

		TableColumn<Customer, String> moreDetailsCol = new TableColumn<>("Dopl. údaje");
		moreDetailsCol.setCellValueFactory(new PropertyValueFactory<>("moreDetails"));
		customersTableView.getColumns().add(moreDetailsCol);
		columnsVisibility.put("Dopl. údaje", moreDetailsCol.visibleProperty());
		
		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);
		}
		customersTableView.setContextMenu(contextMenu);

		customersTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {

			@Override
			public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
				if (newValue == null) {
					editCustomerButton.setDisable(true);
				} else {
					editCustomerButton.setDisable(false);
				}
				selectedCustomer.set(newValue);
			}

		});
		
		FilteredList<Customer> filteredCustomers = new FilteredList<>(customersList);
		filteredCustomers.predicateProperty().bind(Bindings.createObjectBinding(() -> 
		customer -> customer.getName().toLowerCase().contains(nameTextField.getText().toLowerCase()) 
				&& customer.getSurname().toLowerCase().contains(surnameTextField.getText().toLowerCase()) 
				&& customer.getMoreDetails().toLowerCase().contains(moreDetailsTextField.getText().toLowerCase()),
				
		nameTextField.textProperty(),
		surnameTextField.textProperty(),
		moreDetailsTextField.textProperty()
		));
		
		customersTableView.setItems(filteredCustomers);
	}

	@FXML
	void addCustomerButtonClicked(ActionEvent event) {
		AddCustomerController controller = new AddCustomerController();
		App.showModalWindow(controller, "AddCustomer.fxml", "Pridanie Zákazníka");
		customersList.setAll(customerDao.getAllEnabled());
	}

	@FXML
	void editCustomerButtonClicked(ActionEvent event) {
		EditCustomerController controller = new EditCustomerController(selectedCustomer.get());
		App.showModalWindow(controller, "AddCustomer.fxml", "Úprava Zákazníka");
		customersList.setAll(customerDao.getAllEnabled());
	}
	
	@FXML
    void selectCustomerButton(ActionEvent event) {
		HistoryOfSalesController controller = new HistoryOfSalesController(selectedCustomer.get());
		App.changeScene(controller, "HistoryOfSales.fxml", "História predajov");
    }
}
