package sk.upjs.ics.evidencia_sprostredkovatela;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;

public class OrderItemFxModel {
	private Long id;
	private Long orderId;
	private LocalDateTime orderDate;
	private Long customerId;
	private StringProperty customerName = new SimpleStringProperty();
	private StringProperty customerSurname = new SimpleStringProperty();
	private Long productId;
	private StringProperty productName = new SimpleStringProperty();
	private int quantity;
	private double pricePiece;
	private double priceTotal;

	public void setOrderItem(OrderItem orderItem) {
		setId(orderItem.getId());
		setOrderId(orderItem.getOrderId());
		setOrderDate(orderItem.getOrderDate());
		setCustomerId(orderItem.getOrderId());
		setCustomerName(orderItem.getCustomerName());
		setCustomerSurname(orderItem.getCustomerSurname());
		setProductId(orderItem.getProductId());
		setProductName(orderItem.getProductName());
		setQuantity(orderItem.getQuantity());
		setPricePiece(orderItem.getPricePiece());
		setPriceTotal(orderItem.getPriceTotal());
	}
	
	public OrderItem getOrderItem() {
		OrderItem orderItem = new OrderItem();
		orderItem.setId(getId());
		orderItem.setOrderId(getOrderId());
		orderItem.setOrderDate(getOrderDate());
		orderItem.setCustomerId(getCustomerId());
		orderItem.setCustomerName(getCustomerName());
		orderItem.setCustomerSurname(getCustomerSurname());
		orderItem.setProductId(getProductId());
		orderItem.setProductName(getProductName());
		orderItem.setQuantity(getQuantity());
		orderItem.setPricePiece(getPricePiece());
		orderItem.setPriceTotal(getPriceTotal());
		return orderItem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName.get();
	}

	public void setCustomerName(String customerName) {
		this.customerName.set(customerName);
	}

	public StringProperty customerNameProperty() {
		return customerName;
	}

	public String getCustomerSurname() {
		return customerSurname.get();
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname.set(customerSurname);
		;
	}

	public StringProperty customerSurnameProperty() {
		return customerSurname;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName.get();
	}

	public void setProductName(String productName) {
		this.productName.set(productName);
		;
	}

	public StringProperty productNameProperty() {
		return productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPricePiece() {
		return pricePiece;
	}

	public void setPricePiece(double pricePiece) {
		this.pricePiece = pricePiece;
	}

	public double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(double priceTotal) {
		this.priceTotal = priceTotal;
	}
}
