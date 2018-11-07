package sk.upjs.ics.evidencia_sprostredkovatela.entity;

import java.time.LocalDateTime;

public class Objednavka {
	private Long idZakaznika;
	private String menoZakaznika;
	private Long idTovaru;
	private String nazovTovaru;
	private int mnozstvo;
	private LocalDateTime datumVytvorenia;
	private LocalDateTime datumOdoslania;

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

	public int getMnozstvo() {
		return mnozstvo;
	}

	public void setMnozstvo(int mnozstvo) {
		this.mnozstvo = mnozstvo;
	}

	public LocalDateTime getDatumVytvorenia() {
		return datumVytvorenia;
	}

	public void setDatumVytvorenia(LocalDateTime datumVytvorenia) {
		this.datumVytvorenia = datumVytvorenia;
	}

	public LocalDateTime getDatumOdoslania() {
		return datumOdoslania;
	}

	public void setDatumOdoslania(LocalDateTime datumOdoslania) {
		this.datumOdoslania = datumOdoslania;
	}
}
