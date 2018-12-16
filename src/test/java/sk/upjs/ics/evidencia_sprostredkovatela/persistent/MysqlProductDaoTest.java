package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;

class MysqlProductDaoTest {

	private GroupDao groupDao = DaoFactoryTest.INSTANCE.getGroupDao();
	private ProductDao productDao = DaoFactoryTest.INSTANCE.getProductDao();
	private Group group;
	private Product product;

	public MysqlProductDaoTest() {
		group = new Group();
		group.setName("Éterické oleje");

		product = new Product();
		product.setName("Eukalyptový olej");
		product.setPrice(10);
		product.setQuantity(5);
	}

	@Test
	void testGetAll() {
		setup();
		product = productDao.add(product);

		List<Product> list = productDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);

		productDao.delete(product.getId());
		cleanup();
	}

	@Test
	void testGetAllValid() {
		setup();
		product = productDao.add(product);
		productDao.setNotValid(product.getId());

		List<Product> all = productDao.getAll();
		List<Product> valid = productDao.getAllValid();
		assertNotEquals(all.size(), valid.size());

		productDao.delete(product.getId());
		cleanup();
	}

	@Test
	void testAddProduct() {
		setup();
		product = productDao.add(product);

		List<Product> list = productDao.getAll();
		Product latestProduct = list.get(list.size() - 1);
		assertEquals(product.getId(), latestProduct.getId());
		assertEquals(product.getName(), latestProduct.getName());
		assertEquals(product.getPrice(), latestProduct.getPrice());
		assertEquals(product.getGroupId(), latestProduct.getGroupId());
		assertEquals(product.getQuantity(), latestProduct.getQuantity());

		productDao.delete(product.getId());
		cleanup();
	}

	@Test
	void testSaveProduct() {
		setup();
		product = productDao.add(product);

		Product modifiedProduct = new Product();
		modifiedProduct.setId(product.getId());
		modifiedProduct.setName("Levanduľový olej");
		modifiedProduct.setPrice(7.25);
		modifiedProduct.setGroupId(product.getGroupId());
		modifiedProduct.setQuantity(7);
		productDao.save(modifiedProduct);

		List<Product> list = productDao.getAll();
		Product latestProduct = list.get(list.size() - 1);
		assertNotEquals(product.getName(), latestProduct.getName());
		assertNotEquals(product.getPrice(), latestProduct.getPrice());
		assertNotEquals(product.getQuantity(), latestProduct.getQuantity());

		assertEquals(modifiedProduct.getName(), latestProduct.getName());
		assertEquals(modifiedProduct.getPrice(), latestProduct.getPrice());
		assertEquals(modifiedProduct.getQuantity(), latestProduct.getQuantity());

		productDao.delete(modifiedProduct.getId());
		cleanup();
	}

	@Test
	void testSetNotValidProduct() {
		setup();
		product = productDao.add(product);

		productDao.setNotValid(product.getId());
		List<Product> list = productDao.getAllValid();

		Product latestProduct = list.get(list.size() - 1);
		assertNotEquals(product.getId(), latestProduct.getId());

		productDao.delete(product.getId());
		cleanup();
	}

	@Test
	void testDecreaseQuantity() {
		setup();
		product = productDao.add(product);

		int soldItemsQuantity = 3;
		productDao.decreaseQuantity(soldItemsQuantity, product.getId());
		List<Product> list = productDao.getAll();
		Product latestProduct = list.get(list.size() - 1);
		assertEquals(product.getQuantity() - soldItemsQuantity, latestProduct.getQuantity());

		productDao.delete(product.getId());
		cleanup();
	}

	@Test
	void testDeleteProduct() {
		setup();
		product = productDao.add(product);

		List<Product> listBefore = productDao.getAll();
		productDao.delete(product.getId());
		List<Product> listAfter = productDao.getAll();

		assertNotEquals(listBefore.size(), listAfter.size());

		cleanup();
	}
	
	void setup() {
		group = groupDao.add(group);
		product.setGroupId(group.getId());
	}
	
	void cleanup() {
		groupDao.delete(group.getId());
	}

}
