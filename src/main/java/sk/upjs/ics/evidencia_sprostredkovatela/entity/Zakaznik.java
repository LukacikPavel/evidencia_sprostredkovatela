package sk.upjs.ics.evidencia_sprostredkovatela.entity;

public class Zakaznik {
	private Long id;
	private String meno;
	private String priezvisko;
	private String email;
	private String telCislo;
	private String doplnujuceUdaje;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	public String getPriezvisko() {
		return priezvisko;
	}
	public void setPriezvisko(String priezvisko) {
		this.priezvisko = priezvisko;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelCislo() {
		return telCislo;
	}
	public void setTelCislo(String telCislo) {
		this.telCislo = telCislo;
	}
	public String getDoplnujuceUdaje() {
		return doplnujuceUdaje;
	}
	public void setDoplnujuceUdaje(String doplnujuceUdaje) {
		this.doplnujuceUdaje = doplnujuceUdaje;
	}
}
