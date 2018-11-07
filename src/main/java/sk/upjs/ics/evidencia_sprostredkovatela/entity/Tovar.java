package sk.upjs.ics.evidencia_sprostredkovatela.entity;

public class Tovar {
	private Long id;
	private String kod;
	private String nazov;
	private double cena;
	private Long idSkupiny;
	private int mnozstvo;
	private boolean platnost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKod() {
		return kod;
	}

	public void setKod(String kod) {
		this.kod = kod;
	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public Long getIdSkupiny() {
		return idSkupiny;
	}

	public void setIdSkupiny(Long idSkupiny) {
		this.idSkupiny = idSkupiny;
	}

	public int getMnozstvo() {
		return mnozstvo;
	}

	public void setMnozstvo(int mnozstvo) {
		this.mnozstvo = mnozstvo;
	}

	public boolean isPlatnost() {
		return platnost;
	}

	public void setPlatnost(boolean platnost) {
		this.platnost = platnost;
	}
}
