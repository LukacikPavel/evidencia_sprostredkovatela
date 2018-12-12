package sk.upjs.ics.evidencia_sprostredkovatela;

import java.time.LocalDateTime;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;

public class SaleItemFxModel {
	private Long id;
	private Long saleId;
	private LocalDateTime saleDate;
	private Long customerId;
	private StringProperty customerName = new SimpleStringProperty();
	private StringProperty customerSurname = new SimpleStringProperty();
	private Long productId;
	private StringProperty productName = new SimpleStringProperty();
	private int quantity;
	private double pricePiece;
	private double priceTotal;

	public void setSaleItem(SaleItem saleItem) {
		setId(saleItem.getId());
		setSaleId(saleItem.getSaleId());
		setSaleDate(saleItem.getSaleDate());
		setCustomerId(saleItem.getCustomerId());
		setCustomerName(saleItem.getCustomerName());
		setCustomerSurname(saleItem.getCustomerSurname());
		setProductId(saleItem.getProductId());
		setProductName(saleItem.getProductName());
		setQuantity(saleItem.getQuantity());
		setPricePiece(saleItem.getPricePiece());
		setPriceTotal(saleItem.getPriceTotal());
	}
	
	public SaleItem getSaleItem() {
		SaleItem saleItem = new SaleItem();
		saleItem.setId(getId());
		saleItem.setSaleId(getSaleId());
		saleItem.setSaleDate(getSaleDate());
		saleItem.setCustomerId(getCustomerId());
		saleItem.setCustomerName(getCustomerName());
		saleItem.setCustomerSurname(getCustomerSurname());
		saleItem.setProductId(getProductId());
		saleItem.setProductName(getProductName());
		saleItem.setQuantity(getQuantity());
		saleItem.setPricePiece(getPricePiece());
		saleItem.setPriceTotal(getPriceTotal());
		return saleItem;
	}

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

	public void setSaleDate(LocalDateTime saleDate) {
		this.saleDate = saleDate;
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
