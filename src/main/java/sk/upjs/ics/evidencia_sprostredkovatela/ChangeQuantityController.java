package sk.upjs.ics.evidencia_sprostredkovatela;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.SaleItem;

public class ChangeQuantityController {
	private SaleItem saleItem;

	@FXML
	private Button cancelButton;

	@FXML
	private Button confirmButton;

	@FXML
	private Spinner<Integer> quantitySpinner;

	public ChangeQuantityController(SaleItem saleItem) {
		this.saleItem = saleItem;
	}

	@FXML
	void cancelButtonClicked(ActionEvent event) {
		cancelButton.getScene().getWindow().hide();
	}

	@FXML
	void confirmButtonClicked(ActionEvent event) {
		saleItem.setQuantity(quantitySpinner.getValue());
		confirmButton.getScene().getWindow().hide();
	}

	@FXML
	void initialize() {
		SpinnerValueFactory.IntegerSpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
				1, Integer.MAX_VALUE, saleItem.getQuantity());
		quantitySpinner.setValueFactory(spinnerValueFactory);
	}
}
