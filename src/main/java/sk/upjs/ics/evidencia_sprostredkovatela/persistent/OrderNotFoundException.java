package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

public class OrderNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 114144421223307771L;
	private long orderId;
	
	public OrderNotFoundException(long orderId) {
		this.orderId = orderId;
	}
	
	public long getOrderId() {
		return orderId;
	}
}
