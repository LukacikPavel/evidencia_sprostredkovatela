package sk.upjs.ics.evidencia_sprostredkovatela;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.OrderItemDao;

public class OrdersHistoryController {

	private OrderItemDao orderItemDao = DaoFactory.INSTANCE.getOrderItemDao();
	private ObservableList<OrderItem> orderItemsList;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private Customer customer;
	private Product product;
	private Parent parent;

	@FXML
	private TableView<OrderItem> orderItemsTableView;

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
	private DatePicker startShippingDatePicker;

	@FXML
	private DatePicker endShippingDatePicker;
	
	@FXML
	private Button selectProductButton;

	@FXML
	private Button selectCustomerButton;

	@FXML
	private TextField quantityAllTextField;

	@FXML
	private TextField priceAllTextField;
	
    @FXML
    private CheckBox orderedCheckBox;


	public OrdersHistoryController(Parent parent) {
		this.parent = parent;
		customer = new Customer();
		customer.setId(Long.MIN_VALUE);
	}

	public OrdersHistoryController(Parent parent, Customer customer) {
		this.parent = parent;
		this.customer = customer;
	}

	@FXML
	void backButtonClicked(ActionEvent event) {
		backButton.getScene().setRoot(parent);
	}

	@FXML
	void selectProductButtonClicked(ActionEvent event) {
		Parent parent = selectCustomerButton.getParent();
		parent.idProperty().set("selesct");
		ProductListController controller = new ProductListController(parent);
		App.showModalWindow(controller, "ProductsList.fxml", "Tovary");
		product = controller.getSelectedProduct();
		if (product != null) {
			productTextField.setText(product.getName());
		}

		
	}
	
	@FXML
    void selectCustomerButtonClicked(ActionEvent event) {
		Parent parent = selectCustomerButton.getParent();
		parent.idProperty().set("select");
		CustomersListController controller = new CustomersListController(parent);
		App.showModalWindow(controller, "CustomersList.fxml", "Zákazníci");
		customer = controller.getSelectedCustomer();
		if (customer != null) {
			nameTextField.setText(customer.getName() + " " + customer.getSurname());
		}
  }

	@FXML
	void initialize() {

		TableColumn<OrderItem, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		orderItemsTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());

	TableColumn<OrderItem, String> customerNameCol = new TableColumn<>("Zákazník");
		customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerFullName"));
		orderItemsTableView.getColumns().add(customerNameCol);
		columnsVisibility.put("Zákazník", customerNameCol.visibleProperty());



		TableColumn<OrderItem, String> productNameCol = new TableColumn<>("Produkt");
		productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
		orderItemsTableView.getColumns().add(productNameCol);
		columnsVisibility.put("Produkt", productNameCol.visibleProperty());

		TableColumn<OrderItem, Integer> quantityCol = new TableColumn<>("Počet");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		orderItemsTableView.getColumns().add(quantityCol);
		columnsVisibility.put("Počet", quantityCol.visibleProperty());

		TableColumn<OrderItem, Double> pricePieceCol = new TableColumn<>("Cena za kus");
		pricePieceCol.setCellValueFactory(new PropertyValueFactory<>("pricePiece"));
		orderItemsTableView.getColumns().add(pricePieceCol);
		columnsVisibility.put("Cena za kus", pricePieceCol.visibleProperty());

		TableColumn<OrderItem, Double> priceTotalCol = new TableColumn<>("Cena celkom");
		priceTotalCol.setCellValueFactory(new PropertyValueFactory<>("priceTotal"));
		orderItemsTableView.getColumns().add(priceTotalCol);
		columnsVisibility.put("Cena celkom", priceTotalCol.visibleProperty());
//
		TableColumn<OrderItem, LocalDateTime> orderedDateCol = new TableColumn<>("Dátum predaja");
		orderedDateCol.setCellFactory((TableColumn<OrderItem, LocalDateTime> param) -> {
			return new TableCell<OrderItem, LocalDateTime>() {
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
		orderedDateCol.setCellValueFactory(param -> {
			return new SimpleObjectProperty<>(param.getValue().getShippingDate());
		});  

		orderItemsTableView.getColumns().add(orderedDateCol);
		columnsVisibility.put("Dátum predaja", orderedDateCol.visibleProperty());	
		
//		
		TableColumn<OrderItem, LocalDateTime> orderDateCol = new TableColumn<>("Dátum objednávky");
		orderDateCol.setCellFactory((TableColumn<OrderItem, LocalDateTime> param) -> {
			return new TableCell<OrderItem, LocalDateTime>() {
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
		orderDateCol.setCellValueFactory(param -> {
			return new SimpleObjectProperty<>(param.getValue().getCreateDate());
		});
		orderItemsTableView.getColumns().add(orderDateCol);
		columnsVisibility.put("Dátum objednávky", orderDateCol.visibleProperty());

		//fillTableView();

                orderItemsList = FXCollections.observableArrayList(orderItemDao.getAll());
		FilteredList<OrderItem> filteredList = filterOrderItems();
		orderItemsTableView.setItems(filteredList);
		
		if (parent.idProperty().getValue().equals("main")) {
			updateQuantityAndPriceAll(filteredList);

		}
		
		filteredList.addListener((ListChangeListener<OrderItem>) c -> {
			updateQuantityAndPriceAll(filteredList);
		});
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
		orderItemsTableView.setContextMenu(contextMenu);
	}

	private void updateQuantityAndPriceAll(List<OrderItem> list) {
		int quantityAll = 0;
		double priceAll = 0;
		for (OrderItem si : list) {
			quantityAll += si.getQuantity();
			priceAll += si.getPriceTotal();

		}
		priceAll = priceAll*100;
		priceAll = Math.round(priceAll);
		priceAll = priceAll /100;
		quantityAllTextField.setText(String.valueOf(quantityAll));
		priceAllTextField.setText(String.valueOf(priceAll));
	}

	

	private FilteredList<OrderItem> filterOrderItems() {
		
		System.out.println(orderItemsList);
		FilteredList<OrderItem> filteredOrderItems = new FilteredList<>(orderItemsList);

		ObjectProperty<Predicate<OrderItem>> productFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<OrderItem>> dateFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<OrderItem>> dateOrderedFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<OrderItem>> customerNameFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<OrderItem>> customerIdFilter = new SimpleObjectProperty<>();


		productFilter
				.bind(Bindings
						.createObjectBinding(
								() -> orderItem -> orderItem.getProductName().toLowerCase()
										.contains(productTextField.getText().toLowerCase()),
								productTextField.textProperty()));

		dateFilter.bind(Bindings.createObjectBinding(() -> {
			LocalDate minDate = startDatePicker.getValue();
			LocalDate maxDate = endDatePicker.getValue();

			final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
			final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;


			return ti -> !finalMin.isAfter(ti.getCreateDate().toLocalDate())
					&& !finalMax.isBefore(ti.getCreateDate().toLocalDate());
		}, startDatePicker.valueProperty(), endDatePicker.valueProperty()));


		dateOrderedFilter.bind(Bindings.createObjectBinding(() -> {
			LocalDate minDate = startShippingDatePicker.getValue();
			LocalDate maxDate = endShippingDatePicker.getValue();

			final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
			final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;


			return ti -> !finalMin.isAfter(ti.getShippingDate().toLocalDate())
					&& !finalMax.isBefore(ti.getShippingDate().toLocalDate());
		}, startShippingDatePicker.valueProperty(), endShippingDatePicker.valueProperty()));

		
		customerNameFilter
				.bind(Bindings
						.createObjectBinding(
								() -> orderItem -> orderItem.getCustomerFullName().toLowerCase()
										.contains(nameTextField.getText().toLowerCase()),
								nameTextField.textProperty()));

		customerIdFilter
				.bind(Bindings.createObjectBinding(() -> orderItem -> customer.getId().equals(orderItem.getCustomerId())
						|| customer.getId().equals(Long.MIN_VALUE)));

		filteredOrderItems.predicateProperty()
				.bind(Bindings.createObjectBinding(
						() -> productFilter.get()
								.and(dateFilter.get().and(dateOrderedFilter.get().and(customerNameFilter.get().and(customerIdFilter.get())))),
						productFilter, dateFilter, dateOrderedFilter, customerNameFilter));


		return filteredOrderItems;
		
	}
}