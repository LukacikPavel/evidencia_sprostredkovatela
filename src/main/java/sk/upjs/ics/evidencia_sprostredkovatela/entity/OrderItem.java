package sk.upjs.ics.evidencia_sprostredkovatela.entity;
import java.time.LocalDateTime;


public class OrderItem {

	private Long id;
	private Long orderId;
	private LocalDateTime createDate;
	private Long customerId;
	private String customerName;
	private String customerSurname;
	private Long productId;
	private String productName;
	private int quantity;
	private double pricePiece;
	private double priceTotal;

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

	public void setCreateDate(LocalDateTime dateOfOrder) {
		this.createDate = dateOfOrder;
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

	public String getCustomerSurname() {
		return customerSurname;
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
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

	public double getPricePiece() {
		return pricePiece;
	}

	public void setPricePiece(double piecePrice) {
		this.pricePiece = piecePrice;
	}

	public double getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(double totalPrice) {
		this.priceTotal = totalPrice;
	}
}
