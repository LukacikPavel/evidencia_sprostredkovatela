package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;

class MysqlProductDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	private Customer customer;
	
	public MysqlProductDaoTest() {
		customer = new Customer();
		customer.setName("John");
		customer.setSurname("Wick");
		customer.setMoreDetails("Баба Яга");
	}

}
