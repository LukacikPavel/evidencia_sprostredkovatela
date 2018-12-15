package sk.upjs.ics.evidencia_sprostredkovatela.persistent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Product;

public class MysqlProductDao implements ProductDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlProductDao(JdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Product add(Product product) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("product");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("code", "name", "price", "group_id", "quantity", "validity");
		Map<String, Object> values = new HashMap<>();
		values.put("code", product.getCode());
		values.put("name", product.getName());
		values.put("price", product.getPrice());
		values.put("group_id", product.getGroupId());
		values.put("quantity", product.getQuantity());
		values.put("validity", product.isValidity());
		Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
		product.setId(id);
		return product;
	}
	@Override
	public List<Product> getAllValid() {
		String sql = "SELECT id, code, name, price, group_id, quantity, validity FROM product WHERE validity = 1";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
	}

	@Override
	public List<Product> getAll() {
		String sql = "SELECT id, code, name, price, group_id, quantity, validity FROM product";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
	}

	
	@Override
	public void save(Product product) {
		if (product == null) {
			throw new NullPointerException("Customer cannot be null");
		}
		String sql = "UPDATE product SET code = ?, name = ?, price = ?, group_id = ?,"
				+ " quantity = ?, validity = ? WHERE id = ?";
		jdbcTemplate.update(sql, product.getCode(), product.getName(), product.getPrice(),product.getGroupId(), 
				product.getQuantity(), product.isValidity(), product.getId());
	}
	
	@Override
	public void decreaseQuantity(int quantity, Long productId) {
		String sql = "UPDATE product SET quantity = quantity - ? WHERE id = ?";
		jdbcTemplate.update(sql, quantity, productId);
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
		if (deleted == 0) {
			throw new ProductNotFoundException(id);
		}
	}

	@Override
	public void setNotValid(long id) {
		int disabled = jdbcTemplate.update("UPDATE product SET validity = 0 WHERE id = ?", id);
		if (disabled == 0) {
			throw new ProductNotFoundException(id);
		}
	}

	
	
}







