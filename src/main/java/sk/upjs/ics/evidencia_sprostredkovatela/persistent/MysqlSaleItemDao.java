package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;

public class MysqlSaleItemDao implements SaleItemDao{
	private JdbcTemplate jdbcTemplate;
	
	public MysqlSaleItemDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public SaleItem add(SaleItem saleItem) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("sale_item");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("sale_id", "product_id", "quantity", "price_piece", "price_total");
		Map<String, Object> values = new HashMap<>();
		values.put("sale_id", saleItem.getSaleId());
		values.put("product_id", saleItem.getProductId());
		values.put("quantity", saleItem.getQuantity());
		values.put("price_piece", saleItem.getPricePiece());
		values.put("price_total", saleItem.getPriceTotal());
		Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
		saleItem.setId(id);
		return saleItem;
	}

	@Override
	public List<SaleItem> getAll() {
		String sql = "SELECT si.id, p.name productName, concat_ws(' ', c.name, c.surname) customerFullName, c.id customerId," + 
				"si.quantity, si.price_piece, si.price_total, s.sale_date FROM sale_item si" + 
				" JOIN product p ON (p.id = si.product_id) JOIN sale s ON (si.sale_id = s.id)" + 
				"JOIN customer c ON (s.customer_id = c.id)";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SaleItem.class));
	}

	@Override
	public void save(SaleItem saleItem) {
		if (saleItem == null) {
			throw new NullPointerException("SaleItem cannot be null");
		}
		String sql = "UPDATE sale_item SET sale_id = ?, product_id = ?, quantity = ?, "
				+ "price_piece = ?, price_total = ? WHERE id = ?";
		jdbcTemplate.update(sql, saleItem.getSaleId(), saleItem.getProductId(), saleItem.getQuantity(), 
				saleItem.getPricePiece(), saleItem.getPriceTotal(), saleItem.getId());
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM saleItem WHERE id = ?", id);
		if (deleted == 0) {
			throw new SaleItemNotFoundException(id);
		}
	}

	@Override
	public List<SaleItem> getByCustomer(Long id) {
//		String sql = "SELECT si.id, p.name productName, si.quantity, si.price_piece, si.price_total, s.sale_date "
//				+ "FROM sale_item si JOIN product p ON (p.id = si.product_id) JOIN sale s ON (si.sale_id = s.id) "
//				+ "WHERE s.customer_id = " + id;
		String sql = "SELECT si.id, p.name productName, concat_ws(' ', c.name, c.surname) customerFullName," + 
				"si.quantity, si.price_piece, si.price_total, s.sale_date FROM sale_item si" + 
				" JOIN product p ON (p.id = si.product_id) JOIN sale s ON (si.sale_id = s.id)" + 
				"JOIN customer c ON (s.customer_id = c.id) WHERE s.customer_id = " + id;
		List<SaleItem> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SaleItem.class));
		return list;
	}

}
