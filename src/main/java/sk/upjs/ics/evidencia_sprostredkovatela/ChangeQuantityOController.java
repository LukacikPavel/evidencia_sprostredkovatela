package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.OrderItem;

public class ChangeQuantityOController {


	private OrderItem orderItem;

	@FXML
	private TextField quantityTextField;

	@FXML
	private Button cancelButton;

	@FXML
	private Button confirmButton;

	public ChangeQuantityOController(OrderItem orderItem) {
		this.orderItem = orderItem;
	}

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
	}

	@FXML
	void confirmButtonClicked(ActionEvent event) {
		if (!quantityTextField.getText().isEmpty()) {
			orderItem.setQuantity(Integer.parseInt(quantityTextField.getText()));
		}
		confirmButton.getScene().getWindow().hide();
	}

	@FXML
	void initialize() {
		quantityTextField.setText(String.valueOf(orderItem.getQuantity()));
		quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			quantityTextField.setText(newValue.replaceAll("[^0-9]", ""));
		});
	}
}
