package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;


public class MysqlGroupDao implements GroupDao{
	private JdbcTemplate jdbcTemplate;

	public MysqlGroupDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Group add(Group group) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
		simpleJdbcInsert.withTableName("group");
		simpleJdbcInsert.usingGeneratedKeyColumns("id");
		simpleJdbcInsert.usingColumns("name");
		Map<String, Object> values = new HashMap<>();
		values.put("name", group.getName());
		Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
		group.setId(id);
		return group;
	}

	@Override
	public List<Group> getAllEnabled() {
		String sql = "SELECT id, name FROM `group` WHERE validity = 1";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Group.class));
	}

	@Override
	public List<Group> getAll() {
		String sql = "SELECT id, name FROM `group`";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Group.class));
	}

	@Override
	public void save(Group group) {
		if (group == null) {
			throw new NullPointerException("Group cannot be null");
		}
		String sql = "UPDATE `group` SET name = ? WHERE id = ?";
		jdbcTemplate.update(sql, group.getName(), group.getId());
	}

	@Override
	public void delete(long id) {
		int deleted = jdbcTemplate.update("DELETE FROM `group` WHERE id = ?", id);
		if (deleted == 0) {
			throw new CustomerNotFoundException(id);
		}
	}

	@Override
	public void disable(long id) {
		int disabled = jdbcTemplate.update("UPDATE `group` SET validity = 0 WHERE id = ?", id);
		if (disabled == 0) {
			throw new CustomerNotFoundException(id);
		}
	}
}

