package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

public class OrderItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9021874995949557445L;
	private long orderItemId;

	public OrderItemNotFoundException(long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public long getOrderItemId() {
		return orderItemId;
	}
}
