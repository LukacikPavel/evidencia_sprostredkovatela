package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Order;

public interface OrderDao {
	
	Order add(Order order);
	
	List<Order> getAll();
	
	void save(Order order);

	void delete(long id);
	
}
