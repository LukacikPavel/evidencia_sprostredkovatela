package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

public class SaleNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 114144421223307771L;
	private long saleId;
	
	public SaleNotFoundException(long saleId) {
		this.saleId = saleId;
	}
	
	public long getSaleId() {
		return saleId;
	}
}
