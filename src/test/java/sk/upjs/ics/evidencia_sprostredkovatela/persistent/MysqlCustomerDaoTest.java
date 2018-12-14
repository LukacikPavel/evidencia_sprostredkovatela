package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactoryTest;

class MysqlCustomerDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();

	@Test
	void testGetAll() {
		List<Customer> list = customerDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}
	
	@Test
	void testGetAllEnabled() {
		Customer customer = new Customer();
		customer.setName("Ezio");
		customer.setSurname("Auditore");
		customer.setMoreDetails("da Firenze");
		customer = customerDao.add(customer);
		customerDao.disable(customer.getId());
		
		List<Customer> all = customerDao.getAll();
		List<Customer> enabled = customerDao.getAllEnabled();
		assertNotNull(enabled);
		assertNotEquals(all.size(), enabled.size());
	}

	@Test
	void testAddCustomer() {
		Customer customer = new Customer();
		customer.setName("Bilbo");
		customer.setSurname("Baggins");
		customer.setMoreDetails("hobbit");
		customer = customerDao.add(customer);

		List<Customer> list = customerDao.getAll();
		Customer latestCustomer = list.get(list.size() - 1);
		assertEquals(customer.getId(), latestCustomer.getId());
		assertEquals(customer.getName(), latestCustomer.getName());
		assertEquals(customer.getSurname(), latestCustomer.getSurname());
		assertEquals(customer.getMoreDetails(), latestCustomer.getMoreDetails());

		customerDao.delete(latestCustomer.getId());
	}
	
	@Test
	void testSaveCustomer() {
		Customer originalCustomer = new Customer();
		originalCustomer.setName("Vaas");
		originalCustomer.setSurname("Montenegro");
		originalCustomer.setMoreDetails("insane");
		originalCustomer = customerDao.add(originalCustomer);
		
		Customer modifiedCustomer = new Customer();
		modifiedCustomer.setId(originalCustomer.getId());
		modifiedCustomer.setName("Joseph");
		modifiedCustomer.setSurname("Seed");
		modifiedCustomer.setMoreDetails("father");
		customerDao.save(modifiedCustomer);
		
		List<Customer> list = customerDao.getAll();
		Customer latestCustomer = list.get(list.size() - 1);
		assertNotEquals(originalCustomer.getName(), latestCustomer.getName());
		assertNotEquals(originalCustomer.getSurname(), latestCustomer.getSurname());
		assertNotEquals(originalCustomer.getMoreDetails(), latestCustomer.getMoreDetails());
		
		assertEquals(modifiedCustomer.getName(), latestCustomer.getName());
		assertEquals(modifiedCustomer.getSurname(), latestCustomer.getSurname());
		assertEquals(modifiedCustomer.getMoreDetails(), latestCustomer.getMoreDetails());
		
		customerDao.delete(modifiedCustomer.getId());
	}

	@Test
	void testDisableCustomer() {
		Customer customer = new Customer();
		customer.setName("Triss");
		customer.setSurname("Merigold");
		customer.setMoreDetails("sorceress");
		customer = customerDao.add(customer);

		customerDao.disable(customer.getId());

		List<Customer> list = customerDao.getAllEnabled();
		Customer latestCustomerEnabled = list.get(list.size() - 1);
		assertNotEquals(customer.getId(), latestCustomerEnabled.getId());

		customerDao.delete(customer.getId());
	}

	@Test
	void testDeleteCustomer() {
		Customer customer = new Customer();
		customer.setName("Luke");
		customer.setSurname("Skywalker");
		customer.setMoreDetails("jedi");
		customer = customerDao.add(customer);

		customerDao.delete(customer.getId());
		List<Customer> list = customerDao.getAll();
		Customer latestCustomer = list.get(list.size() - 1);
		assertNotEquals(customer.getId(), latestCustomer.getId());
	}

}
