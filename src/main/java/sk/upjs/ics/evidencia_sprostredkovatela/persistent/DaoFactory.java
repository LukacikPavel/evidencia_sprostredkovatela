package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import org.springframework.jdbc.core.JdbcTemplate;
import com.mysql.cj.jdbc.MysqlDataSource;

public enum DaoFactory {
	INSTANCE;
	
	private ZakaznikDao zakaznikDao;
	private JdbcTemplate jdbcTemplate;
	
	public ZakaznikDao getZakaznikDao() {
		if (zakaznikDao == null) {
			zakaznikDao = new MysqlZakaznikDao(getJdbcTemplate());
		}
		return zakaznikDao;

	}
	
	private JdbcTemplate getJdbcTemplate() {
		if (jdbcTemplate == null) {
			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("sprostredkovatel");
			dataSource.setPassword("paz1cProjekt");
			dataSource.setUrl("jdbc:mysql://localhost/evidencia_tovarov_sprostredkovatela?serverTimezone=Europe/Bratislava");
			jdbcTemplate = new JdbcTemplate(dataSource);
		}
		return jdbcTemplate;
	}
}
