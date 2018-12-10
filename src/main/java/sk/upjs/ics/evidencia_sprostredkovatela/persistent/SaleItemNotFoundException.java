package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

public class SaleItemNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9021874995949557445L;
	private long saleItemId;

	public SaleItemNotFoundException(long saleItemId) {
		this.saleItemId = saleItemId;
	}

	public long getSaleItemId() {
		return saleItemId;
	}
}
