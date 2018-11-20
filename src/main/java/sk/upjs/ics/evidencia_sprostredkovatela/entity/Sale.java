package sk.upjs.ics.evidencia_sprostredkovatela.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Sale {
	private Long id;
	private Long customerId;
	private String customerName;
	private String customerSurname;
	private LocalDateTime saleDate;
	private double totalPrice;
	private double discount;
	private double finalPrice;
	private List<SaleItem> items;

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

	public String getCustomerSurname() {
		return customerSurname;
	}

	public void setCustomerSurname(String customerSurname) {
		this.customerSurname = customerSurname;
	}

	public LocalDateTime getDateOfSale() {
		return saleDate;
	}

	public void setDateOfSale(LocalDateTime dateOfSale) {
		this.saleDate = dateOfSale;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}

	public List<SaleItem> getItems() {
		return items;
	}

	public void setItems(List<SaleItem> items) {
		this.items = items;
	}
}
