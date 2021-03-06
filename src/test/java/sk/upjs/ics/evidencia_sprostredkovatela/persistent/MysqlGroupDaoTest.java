package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;

class MysqlGroupDaoTest {

	private GroupDao groupDao = DaoFactoryTest.INSTANCE.getGroupDao();
	private Group group;

	public MysqlGroupDaoTest() {
		group = new Group();
		group.setName("Mastičky");
	}

	@Test
	void testGetAll() {
		group = groupDao.add(group);

		List<Group> list = groupDao.getAll();
		assertNotNull(list);
		assertTrue(list.size() > 0);

		groupDao.delete(group.getId());
	}
	
	@Test
	void testGetAllValid() {
		group = groupDao.add(group);
		groupDao.setNotValid(group.getId());
		
		List<Group> all = groupDao.getAll();
		List<Group> enabled = groupDao.getAllValid();
		assertNotEquals(all.size(), enabled.size());
		
		groupDao.delete(group.getId());
	}

	@Test
	void testAddGroup() {
		group = groupDao.add(group);

		List<Group> list = groupDao.getAll();
		Group latestGroup = list.get(list.size() - 1);
		assertEquals(group.getId(), latestGroup.getId());
		assertEquals(group.getName(), latestGroup.getName());

		groupDao.delete(group.getId());
	}

	@Test
	void testSaveGroup() {
		group = groupDao.add(group);

		Group modifiedGroup = new Group();
		modifiedGroup.setId(group.getId());
		modifiedGroup.setName("Šampóny");
		groupDao.save(modifiedGroup);

		List<Group> list = groupDao.getAll();
		Group latestGroup = list.get(list.size() - 1);
		assertNotEquals(group.getName(), latestGroup.getName());

		assertEquals(group.getId(), latestGroup.getId());
		assertEquals(modifiedGroup.getId(), latestGroup.getId());
		assertEquals(modifiedGroup.getName(), latestGroup.getName());
		
		groupDao.delete(modifiedGroup.getId());
	}

	@Test
	void testsetNotValidGroup() {
		group = groupDao.add(group);

		groupDao.setNotValid(group.getId());
		List<Group> list = groupDao.getAllValid();
		Group latestGroup = list.get(list.size() - 1);

		assertNotEquals(group.getId(), latestGroup.getId());
		
		groupDao.delete(group.getId());
	}

	@Test
	void testDeleteSale() {
		group = groupDao.add(group);

		List<Group> listBefore = groupDao.getAll();
		groupDao.delete(group.getId());
		List<Group> listAfter = groupDao.getAll();

		assertNotEquals(listBefore.size(), listAfter.size());
	}

}
