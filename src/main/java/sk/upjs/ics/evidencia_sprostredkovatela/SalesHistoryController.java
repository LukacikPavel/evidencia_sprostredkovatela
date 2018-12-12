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
import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.SaleItemDao;

public class SalesHistoryController {

	private SaleItemDao saleItemDao = DaoFactory.INSTANCE.getSaleItemDao();
	private ObservableList<SaleItem> saleItemsList;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private Customer customer;

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

	public SalesHistoryController(Customer customer) {
		this.customer = customer;
	}

	@FXML
	void backButtonClicked(ActionEvent event) {
		App.changeScene(new CustomersListController(), "CustomersList.fxml", "Zákazníci");
	}

	@FXML
	void selectButtonClicked(ActionEvent event) {
		System.out.println(startDatePicker.getValue() + " " + endDatePicker.getValue() + " "
				+ saleItemsList.get(0).getSaleDate().toLocalDate());
	}

	@FXML
	void initialize() {
		nameTextField.setText(customer.getName() + " " + customer.getSurname());

		saleItemsList = FXCollections.observableArrayList(saleItemDao.getByCustomer(customer.getId()));

		TableColumn<SaleItem, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		saleItemsTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());

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

		FilteredList<SaleItem> filteredSaleItems = new FilteredList<>(saleItemsList);
		
		ObjectProperty<Predicate<SaleItem>> productFilter = new SimpleObjectProperty<>();
        ObjectProperty<Predicate<SaleItem>> dateFilter = new SimpleObjectProperty<>();
        
        productFilter.bind(Bindings.createObjectBinding(() -> 
        saleItem -> saleItem.getProductName().toLowerCase().contains(productTextField.getText().toLowerCase()), 
        productTextField.textProperty()));

		dateFilter.bind(Bindings.createObjectBinding(() -> {
			LocalDate minDate = startDatePicker.getValue();
			LocalDate maxDate = endDatePicker.getValue();

			final LocalDate finalMin = minDate == null ? LocalDate.MIN : minDate;
			final LocalDate finalMax = maxDate == null ? LocalDate.MAX : maxDate;

			return ti -> !finalMin.isAfter(ti.getSaleDate().toLocalDate())
					&& !finalMax.isBefore(ti.getSaleDate().toLocalDate());
		}, startDatePicker.valueProperty(), endDatePicker.valueProperty()));
		
		filteredSaleItems.predicateProperty().bind(Bindings.createObjectBinding(
                () -> productFilter.get().and(dateFilter.get()), 
                productFilter, dateFilter));

		saleItemsTableView.setItems(filteredSaleItems);

		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);
		}
		saleItemsTableView.setContextMenu(contextMenu);
	}
}