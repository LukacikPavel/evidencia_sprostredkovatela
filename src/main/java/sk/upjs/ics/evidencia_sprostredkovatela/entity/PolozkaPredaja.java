package sk.upjs.ics.evidencia_sprostredkovatela.entity;

import java.time.LocalDateTime;

public class PolozkaPredaja {
	private Long idPredaja;
	private LocalDateTime datumPredaja;
	private Long idZakaznika;
	private String menoZakaznika;
	private String priezviskoZakaznika;
	private Long idTovaru;
	private String nazovTovaru;
	private int pocet;
	private double cenaKus;
	private double cenaCelkom;

	public Long getIdPredaja() {
		return idPredaja;
	}

	public void setIdPredaja(Long idPredaja) {
		this.idPredaja = idPredaja;
	}

	public LocalDateTime getDatumPredaja() {
		return datumPredaja;
	}

	public void setDatumPredaja(LocalDateTime datumPredaja) {
		this.datumPredaja = datumPredaja;
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

	public Long getIdTovaru() {
		return idTovaru;
	}

	public void setIdTovaru(Long idTovaru) {
		this.idTovaru = idTovaru;
	}

	public String getNazovTovaru() {
		return nazovTovaru;
	}

	public void setNazovTovaru(String nazovTovaru) {
		this.nazovTovaru = nazovTovaru;
	}

	public int getPocet() {
		return pocet;
	}

	public void setPocet(int pocet) {
		this.pocet = pocet;
	}

	public double getCenaKus() {
		return cenaKus;
	}

	public void setCenaKus(double cenaKus) {
		this.cenaKus = cenaKus;
	}

	public double getCenaCelkom() {
		return cenaCelkom;
	}

	public void setCenaCelkom(double cenaCelkom) {
		this.cenaCelkom = cenaCelkom;
	}
}
