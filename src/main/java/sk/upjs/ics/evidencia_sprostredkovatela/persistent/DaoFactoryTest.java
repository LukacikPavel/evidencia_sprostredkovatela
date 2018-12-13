package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactoryTest {
	INSTANCE;

	private CustomerDao customerDao;
	private GroupDao groupDao;
	private ProductDao productDao;
	private SaleDao saleDao;
	private SaleItemDao saleItemDao;
	private OrderDao orderDao;
	private OrderItemDao orderItemDao;
	private JdbcTemplate jdbcTemplate;

	public CustomerDao getCustomerDao() {
		if (customerDao == null) {
			customerDao = new MysqlCustomerDao(getJdbcTemplate());
		}
		return customerDao;
	}

	public GroupDao getGroupDao() {
		if (groupDao == null) {
			groupDao = new MysqlGroupDao(getJdbcTemplate());
		}
		return groupDao;
	}

	public ProductDao getProductDao() {
		if (productDao == null) {
			productDao = new MysqlProductDao(getJdbcTemplate());
		}
		return productDao;
	}
	

	public SaleDao getSaleDao() {
		if (saleDao == null) {
			saleDao = new MysqlSaleDao(getJdbcTemplate());
		}
		return saleDao;
	}
	
	public SaleItemDao getSaleItemDao() {
		if (saleItemDao == null) {
			saleItemDao = new MysqlSaleItemDao(getJdbcTemplate());
		}
		return saleItemDao;
	}

public OrderDao getOrderDao() {
	if (orderDao == null) {
		orderDao = new MysqlOrderDao(getJdbcTemplate());
	}
	return orderDao;
}

public OrderItemDao getOrderItemDao() {
	if (orderItemDao == null) {
		orderItemDao = new MysqlOrderItemDao(getJdbcTemplate());
	}
	return orderItemDao;
}


	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("tester");
			dataSource.setPassword("paz1ctester");
			dataSource.setUrl(
					"jdbc:mysql://localhost/evidencia_tovarov_sprostredkovatela_test?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
