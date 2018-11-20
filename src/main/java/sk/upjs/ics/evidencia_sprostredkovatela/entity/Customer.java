package sk.upjs.ics.evidencia_sprostredkovatela.entity;

public class Customer {
	private Long id;
	private String name;
	private String surname;
	private String email;
	private String number;
	private String moreDetails;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getMoreDetails() {
		return moreDetails;
	}
	public void setMoreDetails(String moreDetails) {
		this.moreDetails = moreDetails;
	}
}
