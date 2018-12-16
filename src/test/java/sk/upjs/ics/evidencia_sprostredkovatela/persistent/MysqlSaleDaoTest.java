package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Sale;

class MysqlSaleDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	private SaleDao saleDao = DaoFactoryTest.INSTANCE.getSaleDao();
	private Customer customer;
	private Sale sale;

	public MysqlSaleDaoTest() {
		customer = new Customer();
		customer.setName("Felix");
		customer.setSurname("Kjellberg");
		customer.setMoreDetails("pewdiepie");

		sale = new Sale();
		sale.setSaleDate(LocalDateTime.now());
		sale.setTotalPrice(100);
		sale.setDiscount(20);
		sale.setFinalPrice(80);
	}

	@Test
	void testGetAll() {
		setup();
		sale = saleDao.add(sale);

		List<Sale> list = saleDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);

		saleDao.delete(sale.getId());
		cleanup();
	}

	@Test
	void testAddSale() {
		setup();
		sale = saleDao.add(sale);

		List<Sale> list = saleDao.getAll();
		Sale latestSale = list.get(list.size() - 1);
		assertEquals(sale.getId(), latestSale.getId());
		assertEquals(sale.getCustomerId(), latestSale.getCustomerId());
		assertEquals(sale.getTotalPrice(), latestSale.getTotalPrice());
		assertEquals(sale.getDiscount(), latestSale.getDiscount());
		assertEquals(sale.getFinalPrice(), latestSale.getFinalPrice());

		saleDao.delete(sale.getId());
		cleanup();
	}
	
	@Test
	void testSaveSale() {
		setup();
		sale = saleDao.add(sale);
		
		Sale modifieldSale = new Sale();
		modifieldSale.setId(sale.getId());
		modifieldSale.setCustomerId(sale.getCustomerId());
		modifieldSale.setSaleDate(LocalDateTime.now());
		modifieldSale.setTotalPrice(42);
		modifieldSale.setDiscount(0);
		modifieldSale.setFinalPrice(42);
		saleDao.save(modifieldSale);
		
		List<Sale> list = saleDao.getAll();
		Sale latestSale = list.get(list.size() - 1);
		assertNotEquals(sale.getTotalPrice(), latestSale.getTotalPrice());
		assertNotEquals(sale.getDiscount(), latestSale.getDiscount());
		assertNotEquals(sale.getFinalPrice(), latestSale.getFinalPrice());
		
		assertEquals(modifieldSale.getTotalPrice(), latestSale.getTotalPrice());
		assertEquals(modifieldSale.getDiscount(), latestSale.getDiscount());
		assertEquals(modifieldSale.getFinalPrice(), latestSale.getFinalPrice());
	
		saleDao.delete(modifieldSale.getId());
		cleanup();
	}

	@Test
	void testDeleteSale() {
		setup();
		sale = saleDao.add(sale);
		
		List<Sale> listBefore = saleDao.getAll();
		saleDao.delete(sale.getId());
		List<Sale> listAfter = saleDao.getAll();
		
		assertNotEquals(listBefore.size(), listAfter.size());
		
		cleanup();
	}
	
	void setup() {
		customer = customerDao.add(customer);
		sale.setCustomerId(customer.getId());
	}
	
	void cleanup() {
		customerDao.delete(customer.getId());
	}

}
