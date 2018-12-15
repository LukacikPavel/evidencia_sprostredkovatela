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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
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
	private Parent parent;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField surnameTextField;

	@FXML
	private TextField moreDetailsTextField;

	@FXML
	private Button backButton;

	@FXML
	private Button editCustomerButton;

	@FXML
	private Button addCustomerButton;

	@FXML
	private Button salesHistoryButton;

	@FXML
	private Button ordersHistoryButton;

	@FXML
	private Button addSaleButton;

	@FXML
	private Button addOrderButton;

	@FXML
	private Button selectCustomerButton;

	@FXML
	private TableView<Customer> customersTableView;

	public CustomersListController(Parent parent) {
		this.parent = parent;
	}

	Customer getSelectedCustomer() {
		return selectedCustomer.get();
	}

	@FXML
	void initialize() {
		if (parent.idProperty().getValue().equals("select")) {
			selectCustomerButton.setVisible(true);
		}

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
					salesHistoryButton.setDisable(true);
					ordersHistoryButton.setDisable(true);
					addSaleButton.setDisable(true);
				} else {
					editCustomerButton.setDisable(false);
					salesHistoryButton.setDisable(false);
					ordersHistoryButton.setDisable(false);
					addSaleButton.setDisable(false);
				}
				selectedCustomer.set(newValue);
			}

		});

		FilteredList<Customer> filteredCustomers = new FilteredList<>(customersList);
		filteredCustomers.predicateProperty().bind(Bindings.createObjectBinding(
				() -> customer -> customer.getName().toLowerCase().contains(nameTextField.getText().toLowerCase())
						&& customer.getSurname().toLowerCase().contains(surnameTextField.getText().toLowerCase())
						&& customer.getMoreDetails().toLowerCase()
								.contains(moreDetailsTextField.getText().toLowerCase()),

				nameTextField.textProperty(), surnameTextField.textProperty(), moreDetailsTextField.textProperty()));

		customersTableView.setItems(filteredCustomers);
		customersTableView.setRowFactory(tv -> {
			TableRow<Customer> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (event.getClickCount() == 2 && (!row.isEmpty())) {
					if (selectCustomerButton.isVisible()) {
						selectCustomerButtonClicked(new ActionEvent());
					}
				}
			});
			return row;
		});
	}

	@FXML
	void selectCustomerButtonClicked(ActionEvent event) {
		selectCustomerButton.getScene().getWindow().hide();
	}

	@FXML
	void backButtonClicked(ActionEvent event) {
		selectedCustomer.set(null);
		if (parent.idProperty().getValue().equals("select")) {
			backButton.getScene().getWindow().hide();
		} else {
			backButton.getScene().setRoot(parent);			
		}
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
	void salesHistoryButtonClicked(ActionEvent event) {
		Parent parent = salesHistoryButton.getParent();
		parent.idProperty().set("select");
		SalesHistoryController controller = new SalesHistoryController(parent, selectedCustomer.get());
		App.changeScene(controller, "SalesHistory.fxml", "História predajov");
	}

	@FXML
	void ordersHistoryButtonClicked(ActionEvent event) {
		Parent parent = ordersHistoryButton.getParent();
		parent.idProperty().set("select");
		OrdersHistoryController controller = new OrdersHistoryController(parent, selectedCustomer.get());
		App.changeScene(controller, "OrdersHistory.fxml", "História objednávok");
	}

	@FXML
	void addSaleButtonClicked(ActionEvent event) {
		AddSaleController controller = new AddSaleController(addSaleButton.getParent(), selectedCustomer.get());
		App.changeScene(controller, "AddSale.fxml", "Nový predaj");
	}

	@FXML
	void addOrderButtonClicked(ActionEvent event) {
		AddOrderController controller = new AddOrderController(addOrderButton.getParent(), selectedCustomer.get());
		App.changeScene(controller, "AddOrder.fxml", "Nová objednávka");
	}
}
