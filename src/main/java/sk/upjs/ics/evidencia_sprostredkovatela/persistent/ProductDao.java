package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;

public interface ProductDao {

	Product add(Product product);

	 List<Product> getAllEnabled();

	List<Product> getAll();

    void save(Product product);
    
    public void decreaseQuantity(int quantity, Long productId);

	void delete(long id);

	void disable(long id);

	 
}
