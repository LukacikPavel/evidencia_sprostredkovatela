package sk.upjs.ics.evidencia_sprostredkovatela;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Customer;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.CustomerDao;

public class CustomersListController {

	private CustomerDao customerDao = DaoFactory.INSTANCE.getCustomerDao();
	private ObservableList<Customer> customersModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();

	@FXML
	private TextField nammeTextField;

	@FXML
	private TextField surnameTextField;

	@FXML
	private TextField moreDetailsTextField;

	@FXML
	private Button editCustomerButton;

	@FXML
	private Button addCustomerButton;

	@FXML
	private Button chooseСustomerButton;

	@FXML
	private TableView<Customer> customersTableView;

	@FXML
	void initialize() {
		customersModel = FXCollections.observableArrayList(customerDao.getAll());
		
		TableColumn<Customer, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		customersTableView.getColumns().add(idCol);
		columnsVisibility.put("ID", idCol.visibleProperty());
		
		TableColumn<Customer, String> menoCol = new TableColumn<>("Meno");
		menoCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		customersTableView.getColumns().add(menoCol);
		columnsVisibility.put("Meno", menoCol.visibleProperty());
		
		TableColumn<Customer, String> priezviskoCol = new TableColumn<>("Priezvisko");
		priezviskoCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
		customersTableView.getColumns().add(priezviskoCol);
		columnsVisibility.put("Priezvisko", priezviskoCol.visibleProperty());
		
		TableColumn<Customer, String> emailCol = new TableColumn<>("E-mail");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		customersTableView.getColumns().add(emailCol);
		columnsVisibility.put("E-mail", emailCol.visibleProperty());
		
		TableColumn<Customer, String> numberCol = new TableColumn<>("Tel. číslo");
		numberCol.setCellValueFactory(new PropertyValueFactory<>("number"));
		customersTableView.getColumns().add(numberCol);
		columnsVisibility.put("Tel. číslo", numberCol.visibleProperty());
		
		TableColumn<Customer, String> moreDetailsCol = new TableColumn<>("Dopl. údaje");
		moreDetailsCol.setCellValueFactory(new PropertyValueFactory<>("moreDetails"));
		customersTableView.getColumns().add(moreDetailsCol);
		columnsVisibility.put("Dopl. údaje", moreDetailsCol.visibleProperty());
		
		customersTableView.setItems(customersModel);
    	customersTableView.setEditable(true);
    	
    	ContextMenu contextMenu = new ContextMenu();
    	for (Entry<String, BooleanProperty> entry: columnsVisibility.entrySet()) {
	    	CheckMenuItem menuItem = new CheckMenuItem(entry.getKey());
	    	menuItem.selectedProperty().bindBidirectional(entry.getValue());
	    	contextMenu.getItems().add(menuItem);
    	}
    	customersTableView.setContextMenu(contextMenu);
	}
	
	@FXML
    void addCustomerButtonClicked(ActionEvent event) {
		AddCustomerController controller = new AddCustomerController();
		showModalWindow(controller, "AddCustomer.fxml", "Pridanie Zákazníka");
    }
	
	@FXML
    void editCustomerButtonClicked(ActionEvent event) {

    }

	private void showModalWindow(Object controller, String fxml, String title) {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
			fxmlLoader.setController(controller);
			Parent rootPane = fxmlLoader.load();
			Scene scene = new Scene(rootPane);

			Stage dialog = new Stage();
			dialog.setScene(scene);
			dialog.setTitle(title);
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
