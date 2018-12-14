package sk.upjs.ics.evidencia_sprostredkovatela;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.SaleItemDao;

public class SalesHistoryController {

	private SaleItemDao saleItemDao = DaoFactory.INSTANCE.getSaleItemDao();
	private ObservableList<SaleItem> saleItemsList;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private Customer customer;
	private Product product;
	private String previousScene;

	@FXML
	private TableView<SaleItem> saleItemsTableView;

	@FXML
	private TextField nameTextField;

	@FXML
	private TextField productTextField;

	@FXML
	private Button backButton;

	@FXML
	private DatePicker startDatePicker;

	@FXML
	private DatePicker endDatePicker;

	@FXML
	private Button selectProductButton;

	@FXML
	private Button selectCustomerButton;

	public SalesHistoryController() {
		previousScene = "MainWindow";
		customer = new Customer();
		customer.setId(Long.MIN_VALUE);
	}

	public SalesHistoryController(Customer customer) {
		previousScene = "CustomersList";
		this.customer = customer;
	}

	@FXML
	void backButtonClicked(ActionEvent event) {
		if (previousScene.equals("CustomersList")) {
			App.changeScene(new CustomersListController(false), "CustomersList.fxml", "Zákazníci");
		}
		if (previousScene.equals("MainWindow")) {
			App.changeScene(new MainWindowController(), "MainWindow.fxml", "Hlavné okno");
		}
	}

	@FXML
	void selectProductButtonClicked(ActionEvent event) {
		ProductListController controller = new ProductListController();
		App.showModalWindow(controller, "ProductsList.fxml", "Tovary");
		product = controller.getSelectedProduct();
		if (product != null) {
			productTextField.setText(product.getName());
		}
	}

	@FXML
	void selectCustomerButtonClicked(ActionEvent event) {
		CustomersListController controller = new CustomersListController(true);
		App.showModalWindow(controller, "CustomersList.fxml", "Zákazníci");
		customer = controller.getSelectedCustomer();
		if (customer != null) {
			nameTextField.setText(customer.getName() + " " + customer.getSurname());
		}
	}

	@FXML
	void initialize() {

		TableColumn<SaleItem, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		saleItemsTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());

		TableColumn<SaleItem, String> customerNameCol = new TableColumn<>("Zákazník");
		customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerFullName"));
		saleItemsTableView.getColumns().add(customerNameCol);
		columnsVisibility.put("Zákazník", customerNameCol.visibleProperty());

		TableColumn<SaleItem, String> productNameCol = new TableColumn<>("Produkt");
		productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
		saleItemsTableView.getColumns().add(productNameCol);
		columnsVisibility.put("Produkt", productNameCol.visibleProperty());

		TableColumn<SaleItem, Integer> quantityCol = new TableColumn<>("Počet");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		saleItemsTableView.getColumns().add(quantityCol);
		columnsVisibility.put("Počet", quantityCol.visibleProperty());

		TableColumn<SaleItem, Double> pricePieceCol = new TableColumn<>("Cena za kus");
		pricePieceCol.setCellValueFactory(new PropertyValueFactory<>("pricePiece"));
		saleItemsTableView.getColumns().add(pricePieceCol);
		columnsVisibility.put("Cena za kus", pricePieceCol.visibleProperty());

		TableColumn<SaleItem, Double> priceTotalCol = new TableColumn<>("Cena celkom");
		priceTotalCol.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
		saleItemsTableView.getColumns().add(priceTotalCol);
		columnsVisibility.put("Cena celkom", priceTotalCol.visibleProperty());

		TableColumn<SaleItem, LocalDateTime> saleDateCol = new TableColumn<>("Dátum predaja");
		saleDateCol.setCellFactory((TableColumn<SaleItem, LocalDateTime> param) -> {
			return new TableCell<SaleItem, LocalDateTime>() {
				private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy HH:mm");

				@Override
				protected void updateItem(LocalDateTime item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null)
						setText("");
					else
						setText(formatter.format(item));
				}
			};
		});
		saleDateCol.setCellValueFactory(param -> {
			return new SimpleObjectProperty<>(param.getValue().getSaleDate());
		});
		saleItemsTableView.getColumns().add(saleDateCol);
		columnsVisibility.put("Dátum predaja", saleDateCol.visibleProperty());

		saleItemsList = FXCollections.observableArrayList(saleItemDao.getAll());
		saleItemsTableView.setItems(filterSaleItems());
		selectCustomerButton.setVisible(true);

		if (customer.getName() != null) {
			nameTextField.setText(customer.getName() + " " + customer.getSurname());
		}

		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);
		}
		saleItemsTableView.setContextMenu(contextMenu);
	}

	private FilteredList<SaleItem> filterSaleItems() {
		FilteredList<SaleItem> filteredSaleItems = new FilteredList<>(saleItemsList);

		ObjectProperty<Predicate<SaleItem>> productFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<SaleItem>> dateFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<SaleItem>> customerNameFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<SaleItem>> customerIdFilter = new SimpleObjectProperty<>();

		productFilter
				.bind(Bindings
						.createObjectBinding(
								() -> saleItem -> saleItem.getProductName().toLowerCase()
										.contains(productTextField.getText().toLowerCase()),
								productTextField.textProperty()));

		dateFilter.bind(Bindings.createObjectBinding(() -> {
			LocalDate minDate = startDatePicker.getValue();
			LocalDate maxDate = endDatePicker.getValue();

			final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
			final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

			return ti -> !finalMin.isAfter(ti.getSaleDate().toLocalDate())
					&& !finalMax.isBefore(ti.getSaleDate().toLocalDate());
		}, startDatePicker.valueProperty(), endDatePicker.valueProperty()));

		customerNameFilter
				.bind(Bindings
						.createObjectBinding(
								() -> saleItem -> saleItem.getCustomerFullName().toLowerCase()
										.contains(nameTextField.getText().toLowerCase()),
								nameTextField.textProperty()));

		customerIdFilter
				.bind(Bindings.createObjectBinding(() -> saleItem -> customer.getId().equals(saleItem.getCustomerId())
						|| customer.getId().equals(Long.MIN_VALUE)));

		filteredSaleItems.predicateProperty()
				.bind(Bindings.createObjectBinding(
						() -> productFilter.get().and(dateFilter.get().and(customerNameFilter.get().and(customerIdFilter.get()))), productFilter,
						dateFilter, customerNameFilter));

		return filteredSaleItems;
	}
}