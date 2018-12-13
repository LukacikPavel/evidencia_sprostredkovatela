package sk.upjs.ics.evidencia_sprostredkovatela.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactoryTest;

class MysqlCustomerDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	
	@Test
	void testGetAll() {
		List<Customer> list = customerDao.getAll();
		System.out.println(list);
		assertNotNull(list);
		assertTrue(list.size() > 0);
	}
	
	@Test
	void testAddCustomer() {
		Customer customer = new Customer();
		customer.setName("Pavel");
		customer.setSurname("Lukacik");
		customer.setMoreDetails("zakaznik");
		customer = customerDao.add(customer);
		
	}

//	public void testAddDepartment()
//    {
//        DepartmentEntity department = new DepartmentEntity("Information Technology");
//        departmentDAO.addDepartment(department);
//         
//        List<DepartmentEntity> departments = departmentDAO.getAllDepartments();
//        Assert.assertEquals(department.getName(), departments.get(0).getName());
//    }
	
}
