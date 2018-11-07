package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Zakaznik;

public interface ZakaznikDao {
	List<Zakaznik> getAll();
}
