package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Sale;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;

class MysqlSaleItemDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	private SaleDao saleDao = DaoFactoryTest.INSTANCE.getSaleDao();
	private SaleItemDao saleItemDao = DaoFactoryTest.INSTANCE.getSaleItemDao();
	private GroupDao groupDao = DaoFactoryTest.INSTANCE.getGroupDao();
	private ProductDao productDao = DaoFactoryTest.INSTANCE.getProductDao();
	private Customer customer;
	private Sale sale;
	private SaleItem saleItem;
	private Group group;
	private Product product;

	public MysqlSaleItemDaoTest() {
		customer = new Customer();
		customer.setName("John");
		customer.setSurname("Wick");
		customer.setMoreDetails("баба яга");

		sale = new Sale();
		sale.setSaleDate(LocalDateTime.now());
		sale.setTotalPrice(10);
		sale.setDiscount(2);
		sale.setFinalPrice(8);

		group = new Group();
		group.setName("Pre zvierata");

		product = new Product();
		product.setName("Šampón pre psy");
		product.setGroupId(group.getId());
		product.setPrice(10);
		product.setQuantity(5);

		saleItem = new SaleItem();
		saleItem.setSaleId(sale.getId());
		saleItem.setProductId(product.getId());
		saleItem.setQuantity(2);
		saleItem.setPricePiece(10);
		saleItem.setPriceTotal(saleItem.getPricePiece() * saleItem.getQuantity());
	}

	@Test
	void testGetAll() {
		setup();
		saleItem = saleItemDao.add(saleItem);

		List<SaleItem> list = saleItemDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);

		saleItemDao.delete(saleItem.getId());
		cleanup();
	}

	@Test
	void testAddSaleItem() {
		setup();
		saleItem = saleItemDao.add(saleItem);

		List<SaleItem> list = saleItemDao.getAll();
		SaleItem latestSaleItem = list.get(list.size() - 1);
		assertEquals(saleItem.getId(), latestSaleItem.getId());
		assertEquals(saleItem.getSaleId(), latestSaleItem.getSaleId());
		assertEquals(saleItem.getProductId(), latestSaleItem.getProductId());
		assertEquals(saleItem.getQuantity(), latestSaleItem.getQuantity());
		assertEquals(saleItem.getPricePiece(), latestSaleItem.getPricePiece());
		assertEquals(saleItem.getPriceTotal(), latestSaleItem.getPriceTotal());

		saleItemDao.delete(latestSaleItem.getId());
		cleanup();
	}

	@Test
	void testSaveCustomer() {
		setup();
		saleItem = saleItemDao.add(saleItem);

		SaleItem modifiedSaleItem = new SaleItem();
		modifiedSaleItem.setId(saleItem.getId());
		modifiedSaleItem.setSaleId(saleItem.getSaleId());
		modifiedSaleItem.setProductId(saleItem.getProductId());
		modifiedSaleItem.setQuantity(3);
		modifiedSaleItem.setPricePiece(saleItem.getPricePiece());
		modifiedSaleItem.setPriceTotal(modifiedSaleItem.getPricePiece() * modifiedSaleItem.getQuantity());
		saleItemDao.save(modifiedSaleItem);

		List<SaleItem> list = saleItemDao.getAll();
		SaleItem latestSaleItem = list.get(list.size() - 1);
		assertNotEquals(saleItem.getQuantity(), latestSaleItem.getQuantity());
		assertNotEquals(saleItem.getPriceTotal(), latestSaleItem.getPriceTotal());

		assertEquals(modifiedSaleItem.getQuantity(), latestSaleItem.getQuantity());
		assertEquals(modifiedSaleItem.getPriceTotal(), latestSaleItem.getPriceTotal());

		saleItemDao.delete(modifiedSaleItem.getId());
		cleanup();
	}
	
	@Test
	void testDeleteSaleItem() {
		setup();
		saleItem = saleItemDao.add(saleItem);
		
		List<SaleItem> listBefore = saleItemDao.getAll();
		saleItemDao.delete(saleItem.getId());
		List<SaleItem> listAfter = saleItemDao.getAll();
		
		assertNotEquals(listBefore.size(), listAfter.size());
		cleanup();
		
	}

	void setup() {
		customer = customerDao.add(customer);
		sale.setCustomerId(customer.getId());
		sale = saleDao.add(sale);
		group = groupDao.add(group);
		product.setGroupId(group.getId());
		product = productDao.add(product);
		saleItem.setSaleId(sale.getId());
		saleItem.setProductId(product.getId());
	}

	void cleanup() {
		productDao.delete(product.getId());
		groupDao.delete(group.getId());
		saleDao.delete(sale.getId());
		customerDao.delete(customer.getId());
	}

}
