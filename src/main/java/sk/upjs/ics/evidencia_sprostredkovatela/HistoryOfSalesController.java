package sk.upjs.ics.evidencia_sprostredkovatela;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Sale;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.SaleDao;

public class HistoryOfSalesController {
	
	private SaleDao saleDao = DaoFactory.INSTANCE.getSaleDao();
	private ObservableList<Sale> salesList;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();;
	
    @FXML
    private TableView<Sale> salesTableView;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    void initialize() {
        salesList = FXCollections.observableArrayList(saleDao.getAll()); // get podla id zakaznika?
        
        TableColumn<Sale, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		salesTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());
		
		TableColumn<Sale, Long> customerIdCol = new TableColumn<>("customerID");
		customerIdCol.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
		salesTableView.getColumns().add(customerIdCol);
		columnsVisibility.put("customerID", customerIdCol.visibleProperty());
		
		TableColumn<Sale, Double> totalPriceCol = new TableColumn<>("Plná cena");
		totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("total_price"));
		salesTableView.getColumns().add(totalPriceCol);
		columnsVisibility.put("Plná cena", totalPriceCol.visibleProperty());
		
		TableColumn<Sale, Double> discountCol = new TableColumn<>("Zľava");
		discountCol.setCellValueFactory(new PropertyValueFactory<>("discount"));
		salesTableView.getColumns().add(discountCol);
		columnsVisibility.put("Zľava", discountCol.visibleProperty());
		
		TableColumn<Sale, Double> finalPriceCol = new TableColumn<>("Finálna cena");
		finalPriceCol.setCellValueFactory(new PropertyValueFactory<>("final_price"));
		salesTableView.getColumns().add(finalPriceCol);
		columnsVisibility.put("Finálna cena", finalPriceCol.visibleProperty());
		
		ContextMenu contextMenu = new ContextMenu();
		for (Entry<String, BooleanProperty> entry : columnsVisibility.entrySet()) {
			CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
			menuItem.selectedProperty().bindBidirectional(entry.getValue());
			contextMenu.getItems().add(menuItem);
		}
		salesTableView.setContextMenu(contextMenu);
    }
}

