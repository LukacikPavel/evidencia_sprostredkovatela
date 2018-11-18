package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Zakaznik;

public class MysqlZakaznikDao implements ZakaznikDao {
	private JdbcTemplate jdbcTemplate;

	public MysqlZakaznikDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public List<Zakaznik> getAll() {
		String sql = "SELECT id, meno, priezvisko, email, tel_cislo, doplnujuce_udaje FROM zakaznici";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Zakaznik.class));
	}
}
