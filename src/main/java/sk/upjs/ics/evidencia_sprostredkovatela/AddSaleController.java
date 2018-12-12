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
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Sale;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.SaleDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.SaleItemDao;

public class AddSaleController {

	private SaleItemDao saleItemDao = DaoFactory.INSTANCE.getSaleItemDao();
	private SaleDao saleDao = DaoFactory.INSTANCE.getSaleDao();
	private ObservableList<SaleItem> saleItemsList = FXCollections.observableArrayList();
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<SaleItem> selectedSaleItem = new SimpleObjectProperty<>();
	private Customer customer;
	private double totalPrice, discount, finalPrice;

	@FXML
	private TextField nameTextField;

	@FXML
	private TableView<SaleItem> saleItemsTableView;

	@FXML
	private Button addProductButton;

	@FXML
	private Button deleteProductButton;

	@FXML
	private Button changeQuantityButton;

	@FXML
	private Button createSaleButton;

	@FXML
	private Button closeSaleButton;

	@FXML
	private TextField totalPriceTextField;

	@FXML
	private TextField discountTextField;

	@FXML
	private TextField finalPriceTextField;

	public AddSaleController() {
	}

	public AddSaleController(Customer customer) {
		this.customer = customer;
	}

	@FXML
	void addProductButtonClicked(ActionEvent event) {
		ProductListController controller = new ProductListController();
		App.showModalWindow(controller, "ProductsList.fxml", "Tovary");
		Product selectedProduct = controller.getSelectedProduct();
		if (selectedProduct != null) {
			SaleItem saleItem = new SaleItem();
			saleItem.setProductId(selectedProduct.getId());
			saleItem.setProductName(selectedProduct.getName());
			saleItem.setPricePiece(selectedProduct.getPrice());
			saleItem.setQuantity(1);
			saleItem.setPriceTotal(selectedProduct.getPrice());
			saleItemsList.add(saleItem);
			saleItemsTableView.setItems(saleItemsList);
			calculatePrice();
		}
	}

	@FXML
	void deleteProductButtonClicked(ActionEvent event) {
		saleItemsList.remove(selectedSaleItem.get());
		saleItemsTableView.setItems(saleItemsList);
	}

	@FXML
	void changeQuantityButtonClicked(ActionEvent event) {
		ChangeQuantityController controller = new ChangeQuantityController(selectedSaleItem.get());
		App.showModalWindow(controller, "ChangeQuantity.fxml", "Množstvo");
		int quantity = controller.getQuantity();
		if (quantity > 0) {
			int index = saleItemsList.indexOf(selectedSaleItem.get());
			SaleItem saleItem = selectedSaleItem.get();
			saleItem.setQuantity(quantity);
			saleItem.setPriceTotal(quantity * saleItem.getPricePiece());
			saleItemsList.set(index, saleItem);
			calculatePrice();
		}
	}

	@FXML
	void closeSaleButtonClicked(ActionEvent event) {
		App.changeScene(new CustomersListController(), "CustomersList.fxml", "Zákazníci");
	}

	@FXML
	void createSaleButtonClicked(ActionEvent event) {
		Sale sale = new Sale();
		sale.setCustomerId(customer.getId());
		sale.setSaleDate(LocalDateTime.now());
		sale.setTotalPrice(totalPrice);
		sale.setDiscount(discount);
		sale.setFinalPrice(finalPrice);
		sale = saleDao.add(sale);

		for (SaleItem si : saleItemsList) {
			si.setSaleId(sale.getId());
			saleItemDao.add(si);
		}
		App.changeScene(new CustomersListController(), "CustomersList.fxml", "Zákazníci");
	}

	@FXML
	void initialize() {

		discountTextField.setText("0");
		discount = 0;

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

		if (customer != null) {
			nameTextField.setText(customer.getName() + " " + customer.getSurname());
		}

		saleItemsTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SaleItem>() {

			@Override
			public void changed(ObservableValue<? extends SaleItem> observable, SaleItem oldValue, SaleItem newValue) {
				if (newValue == null) {
					changeQuantityButton.setDisable(true);
					deleteProductButton.setDisable(true);
				} else {
					changeQuantityButton.setDisable(false);
					deleteProductButton.setDisable(false);
				}
				selectedSaleItem.set(newValue);
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
		for (SaleItem si : saleItemsList) {
			price += si.getPriceTotal();
		}
		totalPrice = price;
		totalPriceTextField.setText(String.valueOf(totalPrice));
		finalPrice = totalPrice - (totalPrice * (discount / 100));
		finalPriceTextField.setText(String.valueOf(finalPrice));
	}
}