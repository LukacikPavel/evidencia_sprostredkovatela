package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;

public class MainWindowController {

	@FXML
	private Button customersListButton;

	@FXML
	private Button productsListButton;

	@FXML
	private Button salesListButton;
	
	@FXML
    private Button ordersListButton;

	@FXML
	void initialize() {

	}

	@FXML
	void customersListButtonClicked(ActionEvent event) {
		Parent parent = customersListButton.getParent();
		parent.idProperty().set("main");
		App.changeScene(new CustomersListController(parent), "CustomersList.fxml", "Zákazníci");

	}

	@FXML
	void productsListButtonClicked(ActionEvent event) {
		App.changeScene(new ProductListController(), "ProductsList.fxml", "Tovary");

	}

	@FXML
	void salesListButtonClicked(ActionEvent event) {
		Parent parent = salesListButton.getParent();
		parent.idProperty().set("main");
		App.changeScene(new SalesHistoryController(parent), "SalesHistory.fxml", "Predaje");
	}
	
	@FXML
	void ordersListButtonClicked(ActionEvent event) {
		Parent parent = ordersListButton.getParent();
		parent.idProperty().set("main");
		App.changeScene(new OrdersHistoryController(parent), "OrdersHistory.fxml", "Objednávky");
	}


}
