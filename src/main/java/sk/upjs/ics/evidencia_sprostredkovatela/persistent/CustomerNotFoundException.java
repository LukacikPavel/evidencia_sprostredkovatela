package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

public class CustomerNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 4073940985665119441L;
	private long customerId;

	public CustomerNotFoundException(long customerId) {
		this.customerId = customerId;
	}

	public long getCustomerId() {
		return customerId;
	}
	
}
