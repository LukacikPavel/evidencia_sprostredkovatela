package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;

public interface CustomerDao {
	
	Customer add(Customer customer);
	
	List<Customer> getAllEnabled();
	
	List<Customer> getAll();
	
	void save(Customer customer);

	void delete(long id);
	
	void disable(long id);
}
