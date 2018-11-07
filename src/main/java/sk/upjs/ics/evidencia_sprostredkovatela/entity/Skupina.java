package sk.upjs.ics.evidencia_sprostredkovatela.entity;

public class Skupina {
	private Long id;
	private String nazov;
	private boolean platnost;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNazov() {
		return nazov;
	}

	public void setNazov(String nazov) {
		this.nazov = nazov;
	}

	public boolean isPlatnost() {
		return platnost;
	}

	public void setPlatnost(boolean platnost) {
		this.platnost = platnost;
	}
}
