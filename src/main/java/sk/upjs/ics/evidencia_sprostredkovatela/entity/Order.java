package sk.upjs.ics.evidencia_sprostredkovatela.entity;

import java.time.LocalDateTime;

public class Order {
	private Long id;
	private Long customerId;
	private String customerName;
	private Long productId;
	private String productName;
	private int quantity;
	private LocalDateTime createDate;
	private LocalDateTime shippingDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(LocalDateTime shippingDate) {
		this.shippingDate = shippingDate;
	}
}
