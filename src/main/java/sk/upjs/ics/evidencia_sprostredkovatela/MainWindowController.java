package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainWindowController {

	@FXML
	private Button customersListButton;

	@FXML
	private Button productsListButton;

	@FXML
	private Button salesListButton;

	@FXML
	void initialize() {

	}

	@FXML
	void customersListButtonClicked(ActionEvent event) {
		App.changeScene(new CustomersListController(false), "CustomersList.fxml", "Zákazníci");

	}

	@FXML
	void productsListButtonClicked(ActionEvent event) {
		App.changeScene(new ProductListController(), "ProductsList.fxml", "Tovary");

	}

	@FXML
	void salesListButtonClicked(ActionEvent event) {
		App.changeScene(new SalesHistoryController(), "SalesHistory.fxml", "Predaje");
	}
	
	@FXML
	void ordersListButtonClicked(ActionEvent event) {
		App.changeScene(new OrdersHistoryController(), "OrdersHistory.fxml", "Objednávky");
	}


}
