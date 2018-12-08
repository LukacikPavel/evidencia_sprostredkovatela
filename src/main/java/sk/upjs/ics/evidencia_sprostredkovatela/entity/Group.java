package sk.upjs.ics.evidencia_sprostredkovatela.entity;

public class Group {
	private Long id;
	private String name;
	private boolean validity;

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

	public boolean isValidity() {
		return validity;
	}

	public boolean getValidity() {
		return validity;
	}

	public void setValidity(boolean validity) {
		this.validity = validity;
	}
}
