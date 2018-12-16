package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Order;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;

class MysqlOrderItemDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	private OrderDao orderDao = DaoFactoryTest.INSTANCE.getOrderDao();
	private OrderItemDao orderItemDao = DaoFactoryTest.INSTANCE.getOrderItemDao();
	private GroupDao groupDao = DaoFactoryTest.INSTANCE.getGroupDao();
	private ProductDao productDao = DaoFactoryTest.INSTANCE.getProductDao();
	private Customer customer;
	private Order order;
	private OrderItem orderItem;
	private Group group;
	private Product product;

	public MysqlOrderItemDaoTest() {
		customer = new Customer();
		customer.setName("Geralt");
		customer.setSurname("z Rivii");
		customer.setMoreDetails("wiedźmin");

		order = new Order();
		order.setCreateDate(LocalDateTime.now());

		group = new Group();
		group.setName("Eliksir");

		product = new Product();
		product.setName("Jaskółka");
		product.setGroupId(group.getId());
		product.setPrice(10);
		product.setQuantity(5);

		orderItem = new OrderItem();
		orderItem.setPricePiece(product.getPrice());
		orderItem.setQuantity(3);
		orderItem.setPriceTotal(orderItem.getPricePiece() * orderItem.getQuantity());
	}

	@Test
	void testGetAll() {
		setup();
		orderItem = orderItemDao.add(orderItem);

		List<OrderItem> list = orderItemDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);

		orderItemDao.delete(orderItem.getId());
		cleanup();
	}

	@Test
	void testAddOrderItem() {
		setup();
		orderItem = orderItemDao.add(orderItem);

		List<OrderItem> list = orderItemDao.getAll();
		OrderItem latestOrderItem = list.get(list.size() - 1);
		assertEquals(orderItem.getOrderId(), latestOrderItem.getOrderId());
		assertEquals(orderItem.getProductId(), latestOrderItem.getProductId());
		assertEquals(orderItem.getQuantity(), latestOrderItem.getQuantity());
		assertEquals(orderItem.getPricePiece(), latestOrderItem.getPricePiece());
		assertEquals(orderItem.getPriceTotal(), latestOrderItem.getPriceTotal());

		orderItemDao.delete(latestOrderItem.getId());
		cleanup();
	}

	@Test
	void testOrderCustomer() {
		setup();
		orderItem = orderItemDao.add(orderItem);

		OrderItem modifiedOrderItem = new OrderItem();
		modifiedOrderItem.setId(orderItem.getId());
		modifiedOrderItem.setOrderId(orderItem.getOrderId());
		modifiedOrderItem.setProductId(orderItem.getProductId());
		modifiedOrderItem.setQuantity(4);
		modifiedOrderItem.setPricePiece(orderItem.getPricePiece());
		modifiedOrderItem.setPriceTotal(modifiedOrderItem.getPricePiece() * modifiedOrderItem.getQuantity());
		orderItemDao.save(modifiedOrderItem);

		List<OrderItem> list = orderItemDao.getAll();
		OrderItem latestOrderItem = list.get(list.size() - 1);
		assertNotEquals(orderItem.getQuantity(), latestOrderItem.getQuantity());
		assertNotEquals(orderItem.getPriceTotal(), latestOrderItem.getPriceTotal());

		assertEquals(modifiedOrderItem.getQuantity(), latestOrderItem.getQuantity());
		assertEquals(modifiedOrderItem.getPriceTotal(), latestOrderItem.getPriceTotal());

		orderItemDao.delete(latestOrderItem.getId());
		cleanup();
	}
	
	@Test
	void testDeleteSaleItem() {
		setup();
		orderItem = orderItemDao.add(orderItem);
		
		List<OrderItem> listBefore = orderItemDao.getAll();
		orderItemDao.delete(orderItem.getId());
		List<OrderItem> listAfter = orderItemDao.getAll();
		
		assertNotEquals(listBefore.size(), listAfter.size());
		cleanup();
	}

	void setup() {
		customer = customerDao.add(customer);
		order.setCustomerId(customer.getId());
		order = orderDao.add(order);
		group = groupDao.add(group);
		product.setGroupId(group.getId());
		product = productDao.add(product);
		orderItem.setOrderId(order.getId());
		orderItem.setProductId(product.getId());
	}

	void cleanup() {
		productDao.delete(product.getId());
		groupDao.delete(group.getId());
		orderDao.delete(order.getId());
		customerDao.delete(customer.getId());
	}

}
