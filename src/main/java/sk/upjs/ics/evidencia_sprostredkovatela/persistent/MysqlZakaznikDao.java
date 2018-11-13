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
//		return jdbcTemplate.query(sql, new RowMapper<Zakaznik>() {
//
//			public Zakaznik mapRow(ResultSet rs, int rowNum) throws SQLException {
//				Zakaznik zakaznik = new Zakaznik();
//				zakaznik.setId(rs.getLong("id"));
//				zakaznik.setMeno(rs.getString("meno"));
//				zakaznik.setPriezvisko(rs.getString("priezvisko"));
//				zakaznik.setEmail(rs.getString("email"));
//				zakaznik.setTelCislo(rs.getString("tel_cislo"));
//				zakaznik.setDoplnujuceUdaje(rs.getString("doplnujuce_udaje"));
//
//				return zakaznik;
//			}
//
//		});
	}
}
