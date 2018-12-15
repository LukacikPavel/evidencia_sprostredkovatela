package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7775141321785111581L;
	private long productId;
	
	public ProductNotFoundException(long productId) {
		this.productId = productId;
	}
	
	public long getProductId() {
		return productId;
	}
}
