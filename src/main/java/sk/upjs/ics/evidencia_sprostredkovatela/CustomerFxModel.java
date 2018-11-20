package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;

public class CustomerFxModel {
	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty surname = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty number = new SimpleStringProperty();
	private StringProperty moreDetails = new SimpleStringProperty();

	public void setZakaznik(Customer customer) {
		id = customer.getId();
		setName(customer.getName());
		setSurname(customer.getSurname());
		setEmail(customer.getEmail());
		setNumber(customer.getNumber());
		setMoreDetails(customer.getMoreDetails());
	}

	public Customer getZakaznik() {
		Customer zakaznik = new Customer();
		zakaznik.setName(getName());
		zakaznik.setSurname(getName());
		zakaznik.setEmail(getEmail());
		zakaznik.setNumber(getNumber());
		zakaznik.setMoreDetails(getMoreDetails());
		return zakaznik;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);;
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public String getSurname() {
		return surname.get();
	}

	public void setSurname(String surname) {
		this.surname.set(surname);;
	}
	
	public StringProperty surnameProperty() {
		return surname;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);;
	}
	
	public StringProperty emailProperty() {
		return email;
	}

	public String getNumber() {
		return number.get();
	}

	public void setNumber(String number) {
		this.number.set(number);;
	}
	
	public StringProperty numberProperty() {
		return number;
	}

	public String getMoreDetails() {
		return moreDetails.get();
	}

	public void setMoreDetails(String moreDetails) {
		this.moreDetails.set(moreDetails);;
	}
	
	public StringProperty moreDetailsProperty() {
		return moreDetails;
	}
}
