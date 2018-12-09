package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Sale;

public class MysqlSaleDao implements SaleDao {
	private JdbcTemplate jdbcTemplate;
	
	public MysqlSaleDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Sale add(Sale sale) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("sale");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("customer_id", "sale_date", "total_price", "discount", "final_price");
		Map<String, Object> values = new HashMap<>();
		values.put("customer_id", sale.getCustomerId());
		values.put("sale_date", sale.getSaleDate());
		values.put("total_price", sale.getTotalPrice());
		values.put("discount", sale.getDiscount());
		values.put("final_price", sale.getFinalPrice());
		Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
		sale.setId(id);
		return sale;
	}

	@Override
	public List<Sale> getAll() {
		String sql = "SELECT id, customer_id, sale_date, total_price, discount, final_price FROM sale";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Sale.class));
	}

	@Override
	public void save(Sale sale) {
		if (sale == null) {
			throw new NullPointerException("Sale cannot be null");
		}
		String sql = "UPDATe sale SET customer_id = ?, sale_date = ?, total_price = ?, "
				+ "discount = ?, final_price = ? WHERE id = ?";
		jdbcTemplate.update(sql, sale.getCustomerId(), sale.getSaleDate(), sale.getTotalPrice(), 
				sale.getDiscount(), sale.getFinalPrice(), sale.getId());
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM sale WHERE id = ?", id);
		if (deleted == 0) {
			throw new SaleNotFoundException(id);
		}
	}

}
