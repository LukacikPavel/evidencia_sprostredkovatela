package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;

public interface CustomerDao {
	
	List<Customer> getAll();
	
}
