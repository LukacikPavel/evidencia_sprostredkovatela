package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Order;

public class MysqlOrderDao implements OrderDao {
	private JdbcTemplate jdbcTemplate;
	
	public MysqlOrderDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Order add(Order order) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("`order`");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("customer_id", "create_date");
		Map<String, Object> values = new HashMap<>();
		values.put("customer_id", order.getCustomerId());
		values.put("create_date", order.getCreateDate());
		Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
		order.setId(id);
		return order;
	}

	@Override
	public List<Order> getAll() {
		String sql = "SELECT id, customer_id, create_date FROM `order`";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Order.class));
	}

	@Override
	public void save(Order order) {
		if (order == null) {
			throw new NullPointerException("Order cannot be null");
		}
		String sql = "UPDATE `order` SET customer_id = ?, create_date = ? WHERE id = ?";
		jdbcTemplate.update(sql, order.getCustomerId(), order.getCreateDate(),  order.getId());
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM `order` WHERE id = ?", id);
		if (deleted == 0) {
			throw new OrderNotFoundException(id);
		}
	}

}
