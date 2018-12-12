package sk.upjs.ics.evidencia_sprostredkovatela;
import com.google.protobuf.GeneratedMessageV3;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;
//import sk.upjs.ics.evidencia_sprostredkovatela.entity.Group;;

public class GroupOfGoodsFxModel {


	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private boolean validity;

	public GroupOfGoodsFxModel() {
		// TODO Auto-generated constructor stub
	}
	
	public GroupOfGoodsFxModel(Group group) {
		setGroup(group);
	}
	
	public void setGroup(Group group) {
		setId(group.getId());
		setName(group.getName());
		setValidity(group.getValidity());
	}

	public Group getGroup() {
		Group group = new Group();
		group.setId(getId());
		group.setName(getName());
		group.setValidity(isValidity());
		return group;
	}

	public boolean isValidity() {
		return validity;
	}

	public void setValidity(boolean validity) {
		this.validity = validity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);;
	}
	
	public StringProperty nameProperty() {
		return name;
	}



}
