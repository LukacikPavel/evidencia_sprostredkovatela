package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Zakaznik;

public class ZakaznikFxModel {
	private Long id;
	private StringProperty meno = new SimpleStringProperty();
	private StringProperty priezvisko = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty telCislo = new SimpleStringProperty();
	private StringProperty doplnujuceUdaje = new SimpleStringProperty();

	public void setZakaznik(Zakaznik zakaznik) {
		id = zakaznik.getId();
		setMeno(zakaznik.getMeno());
		setPrizvisko(zakaznik.getPriezvisko());
		setEmail(zakaznik.getEmail());
		setTelCislo(zakaznik.getTelCislo());
		setDoplnujuceUdaje(zakaznik.getDoplnujuceUdaje());
	}

	public Zakaznik getZakaznik() {
		Zakaznik zakaznik = new Zakaznik();
		zakaznik.setMeno(getMeno());
		zakaznik.setPriezvisko(getPrizvisko());
		zakaznik.setEmail(getEmail());
		zakaznik.setTelCislo(getTelCislo());
		zakaznik.setDoplnujuceUdaje(getDoplnujuceUdaje());
		return zakaznik;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMeno() {
		return meno.get();
	}

	public void setMeno(String meno) {
		this.setMeno(meno);
	}

	public StringProperty menoProperty() {
		return meno;
	}

	public String getPrizvisko() {
		return priezvisko.get();
	}

	public void setPrizvisko(String prizvisko) {
		this.setPrizvisko(prizvisko);
		;
	}

	public StringProperty priezviskoProperty() {
		return priezvisko;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.setEmail(email);
	}

	public StringProperty emailProperty() {
		return email;
	}

	public String getTelCislo() {
		return telCislo.get();
	}

	public void setTelCislo(String telCislo) {
		this.setTelCislo(telCislo);
	}

	public StringProperty telCisloProperty() {
		return telCislo;
	}

	public String getDoplnujuceUdaje() {
		return doplnujuceUdaje.get();
	}

	public void setDoplnujuceUdaje(String doplnujuceUdaje) {
		this.setDoplnujuceUdaje(doplnujuceUdaje);
	}

	public StringProperty doplnujuceUdajeProperty() {
		return doplnujuceUdaje;
	}

}
