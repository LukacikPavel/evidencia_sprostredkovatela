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
import javafx.scene.Parent;
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
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ProductDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.OrderDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.OrderItemDao;

public class AddOrderController {

	private OrderItemDao orderItemDao = DaoFactory.INSTANCE.getOrderItemDao();
	private OrderDao orderDao = DaoFactory.INSTANCE.getOrderDao();
	private ObservableList<OrderItem> orderItemsList = FXCollections.observableArrayList();
	private ProductDao productDao = DaoFactory.INSTANCE.getProductDao();

	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<OrderItem> selectedOrderItem = new SimpleObjectProperty<>();
	private Parent parent;
	private Customer customer;
	private double totalPrice;

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
	private Button selectCustomerButton;

	public AddOrderController(Parent parent) {
		this.parent = parent;
	}

	public AddOrderController(Parent parent, Customer customer) {
		this.parent = parent;
		this.customer = customer;
	}

	@FXML
	void addProductButtonClicked(ActionEvent event) {
		Parent parent = addProductButton.getParent();
		parent.idProperty().set("add");
		ProductListController controller = new ProductListController(parent);
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
		OrderItem orderItem = selectedOrderItem.get();
		ChangeQuantityOController controller = new ChangeQuantityOController(selectedOrderItem.get());
		App.showModalWindow(controller, "ChangeQuantity.fxml", "Množstvo");

		App.showModalWindow(controller, "ChangeQuantity.fxml", "Množstvo");
		int index = orderItemsList.indexOf(orderItem);
		orderItem.setPriceTotal(orderItem.getQuantity() * orderItem.getPricePiece());
		orderItemsList.set(index, orderItem);
		calculatePrice();

	}

	@FXML
	void closeOrderButtonClicked(ActionEvent event) {
		closeOrderButton.getScene().setRoot(parent);
	}

	@FXML
	void createOrderButtonClicked(ActionEvent event) {
		if (customer != null && !orderItemsList.isEmpty()) {
			Order order = new Order();
			order.setCustomerId(customer.getId());
			order.setCreateDate(LocalDateTime.now());
			order.setTotalPrice(totalPrice);
			order = orderDao.add(order);

			for (OrderItem si : orderItemsList) {
				si.setOrderId(order.getId());
				orderItemDao.add(si);
				productDao.decreaseQuantity(si.getQuantity(), si.getProductId());

			}
			createOrderButton.getScene().setRoot(parent);
		} else if (customer == null){
			App.showModalWindow(new ErrorInvalidCustomerController(), "Error.fxml", "Error");
		} else {
			App.showModalWindow(new ErrorNonProductSelectedController(), "Error.fxml", "Error");
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
			public void changed(ObservableValue<? extends OrderItem> observable, OrderItem oldValue,
					OrderItem newValue) {
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

	}

	void calculatePrice() {
		double price = 0;
		for (OrderItem si : orderItemsList) {
			price += si.getPriceTotal();
		}
		totalPrice = price;
		totalPriceTextField.setText(String.valueOf(totalPrice));
	}
}
