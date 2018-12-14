package sk.upjs.ics.evidencia_sprostredkovatela.persistent;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Sale;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;

class MysqlSaleItemDaoTest {

	private CustomerDao customerDao = DaoFactoryTest.INSTANCE.getCustomerDao();
	private SaleDao saleDao = DaoFactoryTest.INSTANCE.getSaleDao();
	private SaleItemDao saleItemDao = DaoFactoryTest.INSTANCE.getSaleItemDao();
	private Customer customer;
	private Sale sale;
	private SaleItem saleItem;
	
	public MysqlSaleItemDaoTest() {
		customer = new Customer();
		customer.setName("Geralt");
		customer.setSurname("z Rivii");
		customer.setMoreDetails("wiedźmin");

		sale = new Sale();
		sale.setSaleDate(LocalDateTime.now());
		sale.setTotalPrice(100);
		sale.setDiscount(20);
		sale.setFinalPrice(80);
		
		saleItem = new SaleItem();
		saleItem.setSaleId(sale.getId());
		
	}

}
