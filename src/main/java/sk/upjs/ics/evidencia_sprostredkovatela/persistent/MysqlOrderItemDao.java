package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;

public class MysqlOrderItemDao implements OrderItemDao{
	private JdbcTemplate jdbcTemplate;
	
	public MysqlOrderItemDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public OrderItem add(OrderItem orderItem) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("order_item");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("order_id", "product_id", "quantity", "price_piece", "price_total");
		Map<String, Object> values = new HashMap<>();
		values.put("order_id", orderItem.getOrderId());
		values.put("product_id", orderItem.getProductId());
		values.put("quantity", orderItem.getQuantity());
		values.put("price_piece", orderItem.getPricePiece());
		values.put("price_total", orderItem.getPriceTotal());
		Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
		orderItem.setId(id);
		return orderItem;
	}

	@Override
	public List<OrderItem> getAll() {
		String sql = "SELECT si.id, p.name productName,concat_ws(' ', c.name, c.surname) customerFullName, c.id customerId," +
                     "si.quantity, si.price_piece, si.price_total, s.create_date FROM order_item si  " +
                   " JOIN product p ON (p.id = si.product_id) JOIN `order` s ON (si.order_id = s.id)"+
				" JOIN customer c ON (s.customer_id = c.id)";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderItem.class));
	}

	@Override
	public void save(OrderItem orderItem) {
		if (orderItem == null) {
			throw new NullPointerException("OrderItem cannot be null");
		}
		String sql = "UPDATE order_item SET order_id = ?, product_id = ?, quantity = ?, "
				+ "price_piece = ?, price_total = ? WHERE id = ?";
		jdbcTemplate.update(sql, orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity(), 
				orderItem.getPricePiece(), orderItem.getPriceTotal(), orderItem.getId());
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM orderItem WHERE id = ?", id);
		if (deleted == 0) {
			throw new OrderItemNotFoundException(id);
		}
	}

	@Override
	public List<OrderItem> getByCustomer(Long id) {
//		String sql = "SELECT si.id, p.name productName, si.quantity, si.price_piece, si.price_total, s.create_date "
//				+ "FROM order_item si JOIN product p ON (p.id = si.product_id) JOIN `order` s ON (si.order_id = s.id) "
//				+ "WHERE s.customer_id = " + id;
//		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderItem.class));
		String sql = "SELECT si.id, p.name productName, concat_ws(' ', c.name, c.surname) customerFullName," + 
				" si.quantity, si.price_piece, si.price_total, s.sale_date FROM order_item si" + 
				" JOIN product p ON (p.id = si.product_id) JOIN `order` s ON (si.order_id = s.id)" + 
				" JOIN customer c ON (s.customer_id = c.id) WHERE s.customer_id = " + id;
		List<OrderItem> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OrderItem.class));
                return list;
	}

}
