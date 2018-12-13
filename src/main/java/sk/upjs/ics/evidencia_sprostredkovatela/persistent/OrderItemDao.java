package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;

public interface OrderItemDao {
	
	OrderItem add(OrderItem orderItem);
	
	List<OrderItem> getAll();
	
	List<OrderItem> getByCustomer(Long id);
	
	void save(OrderItem orderItem);

	void delete(long id);
	
}
