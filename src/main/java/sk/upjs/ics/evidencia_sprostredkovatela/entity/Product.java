package sk.upjs.ics.evidencia_sprostredkovatela.entity;

public class Product {
	private Long id;
	private String code;
	private String name;
	private double price;
	private Long groupId;
	private int quantity;
	private boolean validity;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isValidity() {
		return validity;
	}
	public void setValidity(boolean availability) {
		this.validity = availability;
	}
}
