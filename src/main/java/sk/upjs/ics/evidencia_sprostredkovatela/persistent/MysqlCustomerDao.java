package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;

public class MysqlCustomerDao implements CustomerDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlCustomerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Customer> getAll() {
		String sql = "SELECT id, name, surname, email, number, more_details FROM customer";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
	}
}
