package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import java.util.List;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;

public interface GroupDao {

	Group add(Group group);

	List<Group> getAllValid();

	List<Group> getAll();

	void save(Group group);

	void delete(long id);

	void setNotValid(long id);

}
