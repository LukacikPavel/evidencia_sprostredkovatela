package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;

public class ProductFxModel {

	private Long id;
	private StringProperty code = new SimpleStringProperty();
	private StringProperty name = new SimpleStringProperty();
	private DoubleProperty price = new SimpleDoubleProperty();
	private LongProperty groupId = new SimpleLongProperty();
	private IntegerProperty quantity = new SimpleIntegerProperty();
	private BooleanProperty validity = new SimpleBooleanProperty();

	public ProductFxModel() {
		// TODO Auto-generated constructor stub
	}

	public ProductFxModel(Product product) {
		setProduct(product);
	}

	public void setProduct(Product product) {
		setId(product.getId());
		setCode(product.getCode());
		setName(product.getName());
		setPrice(product.getPrice());
		setGroupId(product.getGroupId());
		setQuantity(product.getQuantity());
		setValidity(product.isValidity());

	}

	public Product getProduct() {
		Product product = new Product();
		product.setId(getId());
		product.setCode(getCode());
		product.setName(getName());
		product.setPrice(getPrice());
		product.setGroupId(getGroupId());
		product.setQuantity(getQuantity());
		product.setValidity(getValidity());
		return product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public StringProperty getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code.set(code);
	}
	
	public StringProperty codeProperty() {
		return code;
	}


	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public Double getPrice() {
		return price.get();
	}

	public void setPrice(Double price) {
		this.price.set(price);
	}

	public DoubleProperty priceProperty() {
		return price;
	}
	public Long getGroupId() {
		return groupId.get();
	}

	public void setGroupId(Long groupId) {
		this.groupId.set(groupId); 
	}
	
	public LongProperty groupIdProperty() {
		return groupId;
	}


	public Integer getQuantity() {
		return quantity.get();
	}

	public void setQuantity(Integer quantity) {
		this.quantity.set(quantity);
	
	}	
		public IntegerProperty quantityProperty() {
			return quantity;
		}

	

	public Boolean getValidity() {
		return validity.get();
	}

	public void setValidity(Boolean validity) {
		this.validity.set(validity);
	}
	
	public BooleanProperty validityProperty() {
		return validity;
	}


}
