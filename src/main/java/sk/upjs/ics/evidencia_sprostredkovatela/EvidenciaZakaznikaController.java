package sk.upjs.ics.evidencia_sprostredkovatela;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Zakaznik;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.DaoFactory;
import sk.upjs.ics.evidencia_sprostredkovatela.persistent.ZakaznikDao;

public class EvidenciaZakaznikaController {

	private ZakaznikDao zakaznikDao = DaoFactory.INSTANCE.getZakaznikDao();
	private ObservableList<Zakaznik> zakazniciModel;
	private Map<String, BooleanProperty> columnsVisibility = new LinkedHashMap<>();
	private ObjectProperty<Zakaznik> zvolenyZakaznik = new SimpleObjectProperty<>();

	@FXML
	private TextField menoTextField;

	@FXML
	private TextField priezviskoTextField;

	@FXML
	private TextField blizsieUrcenieTextField;

	@FXML
	private Button upravButton;

	@FXML
	private Button pridajButton;

	@FXML
	private Button vyberButton;

	@FXML
	private TableView<Zakaznik> zakazniciTableView;

	@FXML
	void initialize() {
		zakazniciModel = FXCollections.observableArrayList(zakaznikDao.getAll());
		
		TableColumn<Zakaznik, Long> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		zakazniciTableView.getColumns().add(idCol);
		//columnsVisibility.put("id", idCol.visibleProperty());
		
		TableColumn<Zakaznik, String> menoCol = new TableColumn<>("Meno");
		menoCol.setCellValueFactory(new PropertyValueFactory<>("meno"));
		zakazniciTableView.getColumns().add(menoCol);
		//columnsVisibility.put("Meno", menoCol.visibleProperty());
		
		TableColumn<Zakaznik, String> priezviskoCol = new TableColumn<>("Priezvisko");
		priezviskoCol.setCellValueFactory(new PropertyValueFactory<>("priezvisko"));
		zakazniciTableView.getColumns().add(priezviskoCol);
		//columnsVisibility.put("Priezvisko", priezviskoCol.visibleProperty());
		
		zakazniciTableView.setItems(zakazniciModel);
    	zakazniciTableView.setEditable(true);
	}
	
	@FXML
    void pridajZakaznikaButton(ActionEvent event) {
		PridanieZakaznikaController pridanieZakaznikaController = new PridanieZakaznikaController();
		showModalWindow(pridanieZakaznikaController, "PridanieZakaznika.fxml", "Pridanie Zákazníka");
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
