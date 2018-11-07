package sk.upjs.ics.evidencia_sprostredkovatela.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Predaj {
	private Long id;
	private Long idZakaznika;
	private String menoZakaznika;
	private String priezviskoZakaznika;
	private LocalDateTime datumPredaja;
	private double sumaCelkova;
	private double zlava;
	private double sumaVysledna;
	private List<PolozkaPredaja> polozky;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdZakaznika() {
		return idZakaznika;
	}

	public void setIdZakaznika(Long idZakaznika) {
		this.idZakaznika = idZakaznika;
	}

	public String getMenoZakaznika() {
		return menoZakaznika;
	}

	public void setMenoZakaznika(String menoZakaznika) {
		this.menoZakaznika = menoZakaznika;
	}

	public String getPriezviskoZakaznika() {
		return priezviskoZakaznika;
	}

	public void setPriezviskoZakaznika(String priezviskoZakaznika) {
		this.priezviskoZakaznika = priezviskoZakaznika;
	}

	public LocalDateTime getDatumPredaja() {
		return datumPredaja;
	}

	public void setDatumPredaja(LocalDateTime datumPredaja) {
		this.datumPredaja = datumPredaja;
	}

	public double getSumaCelkova() {
		return sumaCelkova;
	}

	public void setSumaCelkova(double sumaCelkova) {
		this.sumaCelkova = sumaCelkova;
	}

	public double getZlava() {
		return zlava;
	}

	public void setZlava(double zlava) {
		this.zlava = zlava;
	}

	public double getSumaVysledna() {
		return sumaVysledna;
	}

	public void setSumaVysledna(double sumaVysledna) {
		this.sumaVysledna = sumaVysledna;
	}

	public List<PolozkaPredaja> getPolozky() {
		return polozky;
	}

	public void setPolozky(List<PolozkaPredaja> polozky) {
		this.polozky = polozky;
	}
}
