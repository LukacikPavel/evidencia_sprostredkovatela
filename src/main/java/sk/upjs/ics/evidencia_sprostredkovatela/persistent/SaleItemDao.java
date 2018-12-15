package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;

public interface SaleItemDao {
	
	SaleItem add(SaleItem saleItem);
	
	List<SaleItem> getAll();
	
	void save(SaleItem saleItem);

	void delete(long id);
	
}
