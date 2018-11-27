package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;

public class MysqlCustomerDao implements CustomerDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlCustomerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Customer add(Customer customer) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("customer");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("name", "surname", "email", "number", "more_details");
		Map<String, Object> values = new HashMap<>();
		values.put("name", customer.getName());
		values.put("surname", customer.getSurname());
		values.put("email", customer.getEmail());
		values.put("number", customer.getNumber());
		values.put("more_details", customer.getMoreDetails());
		Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
		customer.setId(id);
		return customer;
	}

	public List<Customer> getAll() {
		String sql = "SELECT id, name, surname, email, number, more_details FROM customer";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Customer.class));
	}


	@Override
	public void save(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM customer WHERE id = ?", id);
		if (deleted == 0) {
			throw new CustomerNotFoundException(id);
		}
	}
}
