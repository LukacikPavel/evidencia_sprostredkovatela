package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Sale;

public interface SaleDao {
	
	Sale add(Sale sale);
	
	List<Sale> getAll();
	
	void save(Sale sale);

	void delete(long id);
	
}
