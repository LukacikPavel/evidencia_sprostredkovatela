package sk.upjs.ics.evidencia_sprostredkovatela.entity;

import java.time.LocalDateTime;

public class SaleItem {
	private Long id;
	private Long saleId;
	private LocalDateTime saleDate;
	private Long customerId;
	private String customerFullName;
	//private String customerSurname;
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

	public Long getSaleId() {
		return saleId;
	}

	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}

	public LocalDateTime getSaleDate() {
		return saleDate;
	}

	public void setSaleDate(LocalDateTime dateOfSale) {
		this.saleDate = dateOfSale;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerName) {
		this.customerFullName = customerName;
	}

//	public String getCustomerSurname() {
//		return customerSurname;
//	}
//
//	public void setCustomerSurname(String customerSurname) {
//		this.customerSurname = customerSurname;
//	}

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
