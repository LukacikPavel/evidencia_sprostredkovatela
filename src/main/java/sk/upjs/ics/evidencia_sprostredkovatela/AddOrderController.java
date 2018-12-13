package sk.upjs.ics.evidencia_sprostredkovatela;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Order;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.OrderDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.OrderItemDao;

public class AddOrderController {

	private OrderItemDao orderItemDao = DaoFactory.INSTANCE.getOrderItemDao();
	private OrderDao orderDao = DaoFactory.INSTANCE.getOrderDao();
	private ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<OrderItem> selectedOrderItem = new SimpleObjectProperty<>();
	private Customer customer;
	private double totalPrice, discount, finalPrice;

	@FXML
	private TextField nameTextField;

	@FXML
	private TableView<OrderItem> orderItemsTableView;

	@FXML
	private Button addProductButton;

	@FXML
	private Button deleteProductButton;

	@FXML
	private Button changeQuantityButton;

	@FXML
	private Button createOrderButton;

	@FXML
	private Button closeOrderButton;

	@FXML
	private TextField totalPriceTextField;

	@FXML
	private TextField discountTextField;

	@FXML
	private TextField finalPriceTextField;

	public AddOrderController() {
	}

	public AddOrderController(Customer customer) {
		this.customer = customer;
	}

	@FXML
	void addProductButtonClicked(ActionEvent event) {
		ProductListController controller = new ProductListController();
		App.showModalWindow(controller, "ProductsList.fxml", "Tovary");
		Product selectedProduct = controller.getSelectedProduct();
		if (selectedProduct != null) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(selectedProduct.getId());
			orderItem.setProductName(selectedProduct.getName());
			orderItem.setPricePiece(selectedProduct.getPrice());
			orderItem.setQuantity(1);
			orderItem.setPriceTotal(selectedProduct.getPrice());
			orderItemsList.add(orderItem);
			orderItemsTableView.setItems(orderItemsList);
			calculatePrice();
		}
	}

	@FXML
	void deleteProductButtonClicked(ActionEvent event) {
		orderItemsList.remove(selectedOrderItem.get());
		orderItemsTableView.setItems(orderItemsList);
	}

	@FXML
	void changeQuantityButtonClicked(ActionEvent event) {
		ChangeQuantityController controller = new ChangeQuantityController(selectedOrderItem.get());
		App.showModalWindow(controller, "ChangeQuantity.fxml", "Množstvo");
		int quantity = controller.getQuantity();
		if (quantity > 0) {
			int index = orderItemsList.indexOf(selectedOrderItem.get());
			OrderItem orderItem = selectedOrderItem.get();
			orderItem.setQuantity(quantity);
			orderItem.setPriceTotal(quantity * orderItem.getPricePiece());
			orderItemsList.set(index, orderItem);
			calculatePrice();
		}
	}

	@FXML
	void closeOrderButtonClicked(ActionEvent event) {
		App.changeScene(new CustomersListController(false), "CustomersList.fxml", "Zákazníci");
	}

	@FXML
	void createOrderButtonClicked(ActionEvent event) {
		Order order = new Order();
		order.setCustomerId(customer.getId());
		order.setOrderDate(LocalDateTime.now());
		order.setTotalPrice(totalPrice);
		order.setDiscount(discount);
		order.setFinalPrice(finalPrice);
		order = orderDao.add(order);

		for (OrderItem si : orderItemsList) {
			si.setOrderId(order.getId());
			orderItemDao.add(si);
		}
		App.changeScene(new CustomersListController(false), "CustomersList.fxml", "Zákazníci");
	}

	@FXML
	void initialize() {

		discountTextField.setText("0");
		discount = 0;

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

		if (customer != null) {
			nameTextField.setText(customer.getName() + " " + customer.getSurname());
		}

		orderItemsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<OrderItem>() {

			@Override
			public void changed(ObservableValue<? extends OrderItem> observable, OrderItem oldValue, OrderItem newValue) {
				if (newValue == null) {
					changeQuantityButton.setDisable(true);
					deleteProductButton.setDisable(true);
				} else {
					changeQuantityButton.setDisable(false);
					deleteProductButton.setDisable(false);
				}
				selectedOrderItem.set(newValue);
			}

		});

		discountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue == null || newValue.isEmpty()) {
				finalPrice = totalPrice;
				finalPriceTextField.setText(String.valueOf(finalPrice));
			} else {
				discount = Double.parseDouble(discountTextField.getText());
				finalPrice = totalPrice - (totalPrice * (discount / 100));
				finalPriceTextField.setText(String.valueOf(finalPrice));
			}

		});
	}

	void calculatePrice() {
		double price = 0;
		for (OrderItem si : orderItemsList) {
			price += si.getPriceTotal();
		}
		totalPrice = price;
		totalPriceTextField.setText(String.valueOf(totalPrice));
		finalPrice = totalPrice - (totalPrice * (discount / 100));
		finalPriceTextField.setText(String.valueOf(finalPrice));
	}
}
