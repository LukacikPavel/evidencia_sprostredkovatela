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
import javafx.util.converter.LocalDateStringConverter;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.OrderItemDao;

public class OrdersHistoryController {

	private OrderItemDao orderItemDao = DaoFactory.INSTANCE.getOrderItemDao();
	private ObservableList<OrderItem> orderItemsList;
	private ObservableList<OrderItem> lentak2;
	private ObservableList<OrderItem> lentak1;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private Customer customer;
	private String previousScene;

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
	private Button selectProductButton;

	@FXML
	private Button selectCustomerButton;

	public OrdersHistoryController() {
		previousScene = "MainWindow";
	}

	public OrdersHistoryController(Customer customer) {
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
		
	}
	
	@FXML
    void selectCustomerButtonClicked(ActionEvent event) {
		CustomersListController controller = new CustomersListController(true);
		App.showModalWindow(controller, "CustomersList.fxml", "Zákazníci");
		customer = controller.getSelectedCustomer();
		fillTableView();
    }

	@FXML
	void initialize() {

		TableColumn<OrderItem, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		orderItemsTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());

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

		TableColumn<OrderItem, LocalDateTime> orderDateCol = new TableColumn<>("Dátum predaja");
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
		columnsVisibility.put("Dátum predaja", orderDateCol.visibleProperty());

		fillTableView();

		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);
		}
		orderItemsTableView.setContextMenu(contextMenu);
	}
	
	private void fillTableView() {
		if (customer != null) {
			nameTextField.setText(customer.getName() + " " + customer.getSurname());
			orderItemsList = FXCollections.observableArrayList(orderItemDao.getByCustomer(customer.getId()));
		} else {
			orderItemsList = FXCollections.observableArrayList(orderItemDao.getAll());
			selectCustomerButton.setVisible(true);
		}

		orderItemsTableView.setItems(filterOrderItems());
	}

	private FilteredList<OrderItem> filterOrderItems() {
		
		System.out.println(orderItemsList);
		FilteredList<OrderItem> filteredOrderItems = new FilteredList<>(orderItemsList);

		ObjectProperty<Predicate<OrderItem>> productFilter = new SimpleObjectProperty<>();
		ObjectProperty<Predicate<OrderItem>> dateFilter = new SimpleObjectProperty<>();

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

			return ti -> !finalMin.isAfter(ti.getCreateDate()).toLocalDate())
					&& !finalMax.isBefore(ti.getCreateDate().toLocalDate());
		}, startDatePicker.valueProperty(), endDatePicker.valueProperty()));

		filteredOrderItems.predicateProperty().bind(Bindings
				.createObjectBinding(() -> productFilter.get().and(dateFilter.get()), productFilter, dateFilter));

		return filteredOrderItems;
		
	}
}