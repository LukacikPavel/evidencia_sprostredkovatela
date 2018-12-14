package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactoryTest;

class MysqlCustomerDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	private Customer customer;

	public MysqlCustomerDaoTest() {
		customer = new Customer();
		customer.setName("Ezio");
		customer.setSurname("Auditore");
		customer.setMoreDetails("da Firenze");
	}
	
	@Test
	void testGetAll() {
		customer = customerDao.add(customer);

		List<Customer> list = customerDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);

		customerDao.delete(customer.getId());
	}

	@Test
	void testGetAllEnabled() {
		customer = customerDao.add(customer);
		customerDao.disable(customer.getId());

		List<Customer> all = customerDao.getAll();
		List<Customer> enabled = customerDao.getAllEnabled();
		assertNotNull(enabled);
		assertNotEquals(all.size(), enabled.size());

		customerDao.delete(customer.getId());
	}

	@Test
	void testAddCustomer() {
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
		customer = customerDao.add(customer);

		Customer modifiedCustomer = new Customer();
		modifiedCustomer.setId(customer.getId());
		modifiedCustomer.setName("Altaïr");
		modifiedCustomer.setSurname("Ibn-La'Ahad");
		modifiedCustomer.setMoreDetails("assassin");
		customerDao.save(modifiedCustomer);

		List<Customer> list = customerDao.getAll();
		Customer latestCustomer = list.get(list.size() - 1);
		assertNotEquals(customer.getName(), latestCustomer.getName());
		assertNotEquals(customer.getSurname(), latestCustomer.getSurname());
		assertNotEquals(customer.getMoreDetails(), latestCustomer.getMoreDetails());

		assertEquals(modifiedCustomer.getName(), latestCustomer.getName());
		assertEquals(modifiedCustomer.getSurname(), latestCustomer.getSurname());
		assertEquals(modifiedCustomer.getMoreDetails(), latestCustomer.getMoreDetails());

		customerDao.delete(modifiedCustomer.getId());
	}

	@Test
	void testDisableCustomer() {
		customer = customerDao.add(customer);

		customerDao.disable(customer.getId());

		List<Customer> list = customerDao.getAllEnabled();
		Customer latestCustomerEnabled = list.get(list.size() - 1);
		assertNotEquals(customer.getId(), latestCustomerEnabled.getId());

		customerDao.delete(customer.getId());
	}

	@Test
	void testDeleteCustomer() {
		customer = customerDao.add(customer);

		customerDao.delete(customer.getId());
		List<Customer> list = customerDao.getAll();
		Customer latestCustomer = list.get(list.size() - 1);
		assertNotEquals(customer.getId(), latestCustomer.getId());
	}

}
