package sk.upjs.ics.evidencia_sprostredkovatela;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;

public class OrderItemFxModel {
	private Long id;
	private Long orderId;
	private LocalDateTime createDate;
	private Long customerId;
	private StringProperty customerName = new SimpleStringProperty();
	private StringProperty customerFullName = new SimpleStringProperty();
	private Long productId;
	private StringProperty productName = new SimpleStringProperty();
	private int quantity;
	private double pricePiece;
	private double priceTotal;

	public void setOrderItem(OrderItem orderItem) {
		setId(orderItem.getId());
		setOrderId(orderItem.getOrderId());
		setCreateDate(orderItem.getCreateDate());
		setCustomerId(orderItem.getOrderId());
		setCustomerName(orderItem.getCustomerName());
		setCustomerFullName(orderItem.getCustomerFullName());
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
		orderItem.setCreateDate(getCreateDate());
		orderItem.setCustomerId(getCustomerId());
		orderItem.setCustomerName(getCustomerName());
		orderItem.setCustomerFullname(getCustomerFullName());
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
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

	public String getCustomerFullName() {
		return customerFullName.get();
	}

	public void setCustomerFullName(String customerSurname) {
		this.customerFullName.set(customerSurname); // TO DO
			}

	public StringProperty customerSurnameProperty() {
		return customerFullName;
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
