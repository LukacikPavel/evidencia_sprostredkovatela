package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	
	private CustomerDao customerDao;
	private JdbcTemplate jdbcTemplate;
	
	public CustomerDao getCustomerDao() {
		if (customerDao == null) {
			customerDao = new MysqlCustomerDao(getJdbcTemplate());
		}
		return customerDao;

	}
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("sprostredkovatel");
			dataSource.setPassword("paz1c");
			dataSource.setUrl("jdbc:mysql://localhost/evidencia_tovarov_sprostredkovatela?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
