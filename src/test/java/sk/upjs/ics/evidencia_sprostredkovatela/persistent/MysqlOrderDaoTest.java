package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Order;

class MysqlOrderDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	private OrderDao orderDao = DaoFactoryTest.INSTANCE.getOrderDao();
	private Customer customer;
	private Order order;
	
	public MysqlOrderDaoTest() {
		customer = new Customer();
		customer.setName("Obi-Wan");
		customer.setSurname("Kenobi");
		customer.setMoreDetails("general");
		
		order = new Order();
		order.setCreateDate(LocalDateTime.now());
	}
	
	@Test
	void testGetAll() {
		setup();
		order = orderDao.add(order);
		
		List<Order> list = orderDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);
		
		orderDao.delete(order.getId());
		cleanup();
	}
	
	@Test
	void testAddOrder() {
		setup();
		order = orderDao.add(order);
		
		List<Order> list = orderDao.getAll();
		Order latestOrder = list.get(list.size() - 1);
		assertEquals(order.getId(), latestOrder.getId());
		
		orderDao.delete(order.getId());
		cleanup();
	}
	
	@Test
	void testSaveOrder() {
		setup();
		order = orderDao.add(order);
		
		Order modifiedOrder = new Order();
		modifiedOrder.setId(order.getId());
		modifiedOrder.setCustomerId(order.getCustomerId());
		modifiedOrder.setCreateDate(order.getCreateDate());
		String str = "1986-04-08 12:30";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
		modifiedOrder.setShippingDate(dateTime);
		orderDao.save(modifiedOrder);
		
		List<Order> list = orderDao.getAll();
		Order latestOrder = list.get(list.size() - 1);
		assertNotEquals(order.getShippingDate(), latestOrder.getShippingDate());
		
		assertEquals(modifiedOrder.getShippingDate(), latestOrder.getShippingDate());
		assertEquals(modifiedOrder.getId(), latestOrder.getId());
		assertEquals(modifiedOrder.getCustomerId(), latestOrder.getCustomerId());
		
		orderDao.delete(order.getId());
		cleanup();
	}
	
	@Test
	void testDeleteOrder() {
		setup();
		order = orderDao.add(order);
		
		List<Order> listBefore = orderDao.getAll();
		orderDao.delete(order.getId());
		List<Order> listAfter = orderDao.getAll();
		
		assertNotEquals(listBefore.size(), listAfter.size());
		
		cleanup();
	}
	
	void setup() {
		customer = customerDao.add(customer);
		order.setCustomerId(customer.getId());
	}
	
	void cleanup() {
		customerDao.delete(customer.getId());
	}

}
