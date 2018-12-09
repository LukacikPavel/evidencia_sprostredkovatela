package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;

	private CustomerDao customerDao;
	private GroupDao groupDao;
	private SaleDao saleDao;
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
	
	public SaleDao getSaleDao() {
		if (saleDao == null) {
			saleDao = new MysqlSaleDao(getJdbcTemplate());
		}
		return saleDao;
	}

	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
//			dataSource.setUser("ht011400");
//			dataSource.setPassword("kfymukyq");
//			dataSource.setURL("jdbc:mysql://46.229.230.163/ht011400db?serverTimezone=Europe/Bratislava");
			dataSource.setUser("sprostredkovatel");
			dataSource.setPassword("paz1c");
			dataSource.setUrl(
					"jdbc:mysql://localhost/evidencia_tovarov_sprostredkovatela?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
